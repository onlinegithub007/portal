layui.use(['layer', 'form', 'laydate', 'upload', 'table', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		common = layui.common,
		laydate = layui.laydate,
		upload = layui.upload;

    var parentTypeId = null;
    var goodsTypeId = null;
    var layIndex;
    
	var tableIns = table.render({
		elem: '#storeChartTables',
		cols: [
			[{
				field: 'storeName',
				width: 300,
				title: '门店名称',
				align: 'left'
			},
			{
				field: 'stockCostText',
				width: 150,
				title: '库存成本(元)',
				align: 'right',
			},
			{
				field: 'stockCount',
				width: 150,
				title: '库存数量',
				align: 'right',
			}
			]
		],
		url: 'store_stock_chart_data.html',
		page: false,
		even: true
	});
	
	var tableIns2 = table.render({
		elem: '#selectGoodsTables',
		cols: [
			[
			{
				field: 'goodsTypeName',
				width: 120,
				title: '分类',
				align: 'left',
			},
			{
				field: 'goodsCode',
				width: 150,
				title: '商品条码',
				align: 'left',
			},
			{
				field: 'goodsName',
				width: 350,
				title: '商品名称',
				align: 'left',
			},
			{
				title: '常用操作',
				width: 120,
				align: 'center',
				toolbar: '#goodsbar',
				fixed:"right"
			}
			]
		],
		url: 'store_stock_chart_goods_data.html',
		where:{
			'show':false
		},
		page: false,
		even: true
	});
	
	//监听工具条
	table.on('tool(selectGoodsTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event === 'select') {
			tableIns.reload({
				where :{
					'action' : 'goodsId',
					'goodsId' : data.goodsId,
					'goodsName': data.goodsName
				},
				done:function()
				{
					layer.close(layIndex);
					$("#selectGoodsPop").addClass("layui-hide");
					
					// 紧接着查询CHART数据
					showChart();
				}
			});
		}
		
	});
	
	form.on('select(selectTopGoodsType)', function(data){
		
		parentTypeId = data.value;
		
		if (parentTypeId.length > 0)
		{
			var params = {
				'parentTypeId' : parentTypeId
			};
			
			$.post('goods_sub_types.html', params, function(data){
				var result = $.parseJSON(data); 
				
				if(result.count > 0)
				{
					var subTypeHtml = "<option value=\"\">请选择商品小类[全部]</option>";
					var jsonData = eval(result.data);
					for (var i = 0; i < jsonData.length; i++)
		    		{
						subTypeHtml += "<option value='" + jsonData[i].goodsTypeId + "'>" + jsonData[i].goodsTypeName + "</option>";
		    		}
					
					$("#subGoodsType").html(subTypeHtml);
					form.render('select');
				}
				else
				{
					$("#subGoodsType").html("<option value=\"\">请选择商品小类[全部]</option>");
					form.render('select');
				}
			});
		}
		else
		{
			$("#subGoodsType").html("<option value=\"\">请选择商品小类[全部]</option>");
			form.render('select');
		}
	});
	
	form.on('select(selectSubGoodsType)', function(data){
		goodsTypeId = data.value;
	});
	
	function showChart()
	{
		$.post('store_stock_chart_last_data.html', null, function(json){
			  
			  var data =  $.parseJSON(json); 
			  
			  myChart.setOption({
				    title: {
						text: data.title
					},
				  	xAxis: {
			            data: data.storeName
			        },
			        series: [{
			            name:'库存数量',
			            yAxisIndex: 0,
			            data: data.stockCount
			        },
			        {
			            name:'库存金额',
			            type:'bar',
			            yAxisIndex: 1,
			            data:data.stockCost
			        }]
			    });
		  });
	}
	
	// 查询整体库存
	form.on('submit(viewTotalStock)',function(data){
		
		tableIns.reload({
			where :{
				'action' : 'all'
			},
			done:function()
			{
				// 紧接着查询CHART数据
				showChart();
			}
		});
		
		return false;
	});
	
	form.on('submit(viewGoodsTypeStock)',function(data){

		var datas = data.field;
		if (datas.parentTypeId.length == 0)
		{
			layer.msg('请先选择一个分类', {icon: 5});
			return false;
		}
		
		tableIns.reload({
	    	where : {
	    		'action':'goodsType',
	    		'parentTypeId':datas.parentTypeId,
	    		'goodsTypeId':datas.goodsTypeId
	    	},
	    	done:function()
			{
				// 紧接着查询CHART数据
				showChart();
			}
	    });
		
    	return false;
	});
	
	
	form.on('submit(viewGoodsStock)',function(data){

		var datas = data.field;
		if (datas.parentTypeId.length == 0)
		{
			layer.msg('请先选择一个分类', {icon: 5});
			return false;
		}
		
		tableIns2.reload({
	    	where : {
	    		'show':true,
	    		'parentTypeId':datas.parentTypeId,
	    		'goodsTypeId':datas.goodsTypeId
	    	}
	    });
		
		$("#selectGoodsPop").removeClass("layui-hide");
		// 要查询某一个商品
		layIndex = layer.open({
			type:1,
			btn: ['取消且关闭'],
			maxmin: true,
			title:'选择商品',
			area: ['750px', '450px'],
			content: $("#selectGoodsPop"),
			yes: function(index, layero){ 
				
				layer.close(index);
				$("#selectGoodsPop").addClass("layui-hide");
			},
			cancel: function(index, layero){ 
				layer.close(index);
				$("#selectGoodsPop").addClass("layui-hide");
			}
		});
		
		
    	return false;
	});

});