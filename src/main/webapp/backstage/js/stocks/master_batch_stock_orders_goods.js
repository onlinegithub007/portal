layui.use(['layer', 'form', 'table', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		common = layui.common;

	var parentTypeId = null;
    var goodsTypeId = null;
    var status = -1;
    var goodsData;
    var selectProviderPrmpIndex;
    var batchStockGoodsId;
	
	var tableIns = table.render({
		elem: '#groupsTables',
		cols: [
			[ 
			{
				field: 'goodsCode',
				width: 180,
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
				field: 'goodsCount',
				width: 120,
				title: '采购数量',
				align: 'right',
				templet: '#goodsCountTpl'
			},
			{
				width: 300,
				title: '选择供应商',
				align: 'left',
				templet: '#providerTpl'
			},
			{
				title: '常用操作',
				width: 100,
				align: 'center',
				toolbar: '#groupsbar',
				fixed:"right"
			}
			]

		],
		url: 'master_batch_stock_order_goods_data.html',
		page: false,
		even: true,
		done: function(res, curr, count){
			// 引用返回表格中所有数据，为后面批量设定价格做准备
			goodsData = res.data;
		}
	});

	//监听工具条
	table.on('tool(groupsTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event == 'delete') 
		{
			layer.confirm('真的删除 当前采购的商品吗？', function(index) {
				
				$.post('master_batch_stock_order_goods_del.html', data, function(json){
					  
					  var oks =  $.parseJSON(json); 
					  
					  if (oks.success == 0)
					  {
						  layer.close(index);
						  tableIns.reload();
					  }
				  });
			});
		}
		else if (obj.event == 'goods') 
		{
			var batchStockOrderId = data.batchStockOrderId;
			window.location.href = 'master_batch_stock_order_goods.html?batchStockOrderId=' + batchStockOrderId;
		}
		else if (obj.event == 'expired') 
		{
			var merGroupId = data.merGroupId;
			window.location.href = 'store_goods_expired_stock.html?merGroupId=' + merGroupId;
		}
		else if (obj.event == 'provider') 
		{
			var goodsName = data.goodsName;
			batchStockGoodsId = data.batchStockGoodsId;
			
			selectProviderPrmpIndex = layer.open({
				type:1,
				title:'为商品 [' + goodsName + '] 选择供应商',
				area: ['650px', '200px'],
				content: "<div class=\"user-tables\"><table id=\"selectGoodsProviderTables\" lay-filter=\"selectGoodsProviderTables\"></table></div>"
			});
			
			tableGoodsProvider = table.render({
				elem: '#selectGoodsProviderTables',
				cols: [
					[
					{
						field: 'userName',
						width: 100,
						title: '联系人',
						align: 'left'
					},
					{
						field: 'merName',
						width: 300,
						title: '供应商',
						align: 'left'
					}
					,{
						field: 'goodsCost',
						width: 120,
						title: '采购价格(分)',
						align: 'left'
					}
					, {
						title: '选择',
						width: 80,
						align: 'center',
						fixed:"right",
						toolbar:'#selectProviderTpl'
					}]
				],
				url: 'select_goods_provider.html',
				where :{
					'goodsId':data.goodsId
				},
				page: false,
				even: true
			});
		}
	});
	
	table.on('tool(selectGoodsProviderTables)', function(obj) {
		var data = obj.data;
		var goodsName = data.goodsName;
		var userName = data.userName + '[' + data.merName + ']';
		var goodsCost = data.goodsCost;
		
		if (obj.event == 'selectProvider') 
		{
			var providerNameObj = $("#providerName" + batchStockGoodsId);
			var providerCost = $("#goodsCost" + batchStockGoodsId);
			var providerIdObj = $("#providerId" + batchStockGoodsId);
			
			providerNameObj.text(userName);
			providerCost.val(goodsCost);
			providerIdObj.val(data.merUserId);
//			
			var params = {
				'batchStockGoodsId' : batchStockGoodsId,
				'providerId' : data.merUserId,
				'providerCost' : data.goodsCost,
//				'dealType' : dealType,
				'providerName' : userName
			};
//			
			// 保存到平台
			$.post('master_batch_stock_set_goods_provider.html', params, function(json){
				  layer.close(selectProviderPrmpIndex);
			});
			
		}
	});
	
	var tableIns2 = table.render({
		elem: '#selectGoodsTables',
		id: 'selectGoodsTable',
		cols: [
			[
			{
				field: 'goodsId',
				checkbox: true
			},
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
			}
			]
		],
		url: 'merchant_goods_data_nopage.html',
		page: false,
		even: true
	});
	
	form.on('submit(stockOrderGoodsSubmit)',function(data){
		
		$("#selectGoodsPop").removeClass("layui-hide");
		
		tableIns2.reload({
	    	where :{
	    		'seeAllGoods':0
	    	}
	    });
		
		layer.open({
			type:1,
			btn: ['确定','取消'],
			maxmin: true,
			title:'选择要采购的商品',
			area: ['750px', '450px'],
			content: $("#selectGoodsPop"),
			yes: function(index, layero){ 
				
				var checkStatus = table.checkStatus('selectGoodsTable');
				if(checkStatus.data.length == 0)
				{
					layer.msg('还没有选择要采购的商品', {icon: 5});
					return;
				}
				
				var goodIdList = "";
				for (i = 0; i < checkStatus.data.length; i++)
				{
					goodIdList += checkStatus.data[i].goodsId + ",";
				}
				
				var params = {
					'goodsList' : goodIdList
				};
				
				$.post('master_batch_stock_init_goods.html', params, function(json){
				  var oks =  $.parseJSON(json); 
				  
				  if (oks.success == 1)
				  {
					  // 更新失败
					  layer.msg(oks.msg, {icon: 5});
				  }
				  else if (oks.success == 0)
				  {
					  tableIns.reload();
				  }
				  
			  });
				
				layer.close(index);
				$("#selectGoodsPop").addClass("layui-hide");
			},
			btn2: function(index, layero){ 
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
	
	form.on('submit(goodsQuerySubmit)',function(data){

		var datas = data.field;
		
		parentTypeId = datas.parentTypeId;
		goodsTypeId = datas.goodsTypeId;
		status = datas.status;
		
		tableIns2.reload({
	    	where :{
	    		'parentTypeId':parentTypeId,
	    		'goodsTypeId':goodsTypeId,
	    		'seeAllGoods':1,
	    		'show':'true'
	    	}
	    });
		
    	return false;
	});
	
	// 暂存数量
	$("#stockOrderCountBtn").click(function(){
		
		// 先采集批量的参数
		var batchStockGoodsIdStr = "";
		var goodsCountStr = "";
		
		for (var i = 0; i < goodsData.length; i++)
		{
			batchStockGoodsIdStr += goodsData[i].batchStockGoodsId + ",";
			goodsCountStr += $("#batchStockGoodsId"+goodsData[i].batchStockGoodsId).val() + ",";
		}
		
		var params = {
			'batchStockGoodsIdStr':batchStockGoodsIdStr,
			'goodsCountStr':goodsCountStr
		};
		
		$.post('master_batch_stock_order_goods_count.html', params, function(json){
			  
			  var oks =  $.parseJSON(json); 
			  
			  if (oks.success == 0)
			  {
				  layer.alert(oks.msg, {icon: 6});
			  }
			  else
			  {
				  layer.alert(oks.msg, {icon: 5});
			  }
		  });
	});
	
	// 这个放在form事件的后面！
	form.verify({
		groupName: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '门店名称不能为空';
	    	}
		},
		groupCode: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '门店编号不能为空';
	    	}
		},
		detailAddress:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '请输入门店的详细地址';
	    	}
		},
		userName:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '请输入店长姓名';
	    	}
		},
		userAccount:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '请输入店长账号';
	    	}
		},
		userPhone:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '请输入店长手机号';
	    	}
		}
	}); 

});