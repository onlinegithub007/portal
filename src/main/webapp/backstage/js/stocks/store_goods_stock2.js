layui.use(['layer', 'form', 'upload', 'table', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		common = layui.common;
		upload = layui.upload;

    var parentTypeId = null;
    var goodsTypeId = null;
    var stockAmountParam = -1;
    var goodsCode = null;
		
	var tableIns = table.render({
		elem: '#goodsStockTables',
		cols: [
			[
			{
				field: 'goodsTypeName',
				width: 150,
				title: '分类',
				align: 'left',
			},
			{
				field: 'goodsCode',
				width: 200,
				title: '商品条码',
				align: 'left',
			},
			{
				field: 'goodsName',
				width: 300,
				title: '商品名称',
				align: 'left',
			}
			,{
				field: 'stockAmount',
				width: 200,
				title: '商品库存',
				align: 'left',
			},
			{
				title: '常用操作',
				width: 200,
				align: 'center',
				toolbar: '#viewTpl',
				fixed:"right"
			}]

		],
		url: 'store_goods_stock_check_data.html',
		where:{
			'stockAmountParam':stockAmountParam
		},
		page: true,
		even: true
	});
	
	//监听工具条
	table.on('tool(goodsStockTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event == 'view') 
		{
			var goodsId = data.goodsId;
			layer.open({
				type:2,
				maxmin: true,
				title:'查看商品库存明细',
				area: ['1000px', '500px'],
				content: 'store_goods_stock_detail.html?goodsId=' + goodsId
			});
		}
		else if (obj.event == 'changeStock')
		{
			var stockAmount = data.stockAmount;
			
			layer.prompt({
				value: stockAmount,
				title: '请输入微调后的库存值',
			}, function(value, index, elem){
				layer.close(index);
				
				if (value == stockAmount)
				{
					return false;
				}
				
				var params = {
					'goodsStockId':data.goodsStockId,
					'stockAmount':value,
					'stockAmount0':stockAmount,
					'goodsId':data.goodsId
				};
				
				$.post('store_goods_stock_change.html', params, function(data){
					var oks =  $.parseJSON(data); 
					if (oks.success == 0)
					{
						layer.alert(oks.msg, {icon: 6}, function(index){
							layer.close(index);
							tableIns.reload({
						    	where :{
						    		'parentTypeId':parentTypeId,
						    		'goodsTypeId':goodsTypeId,
						    		'stockAmountParam':stockAmountParam,
						    		'goodsCode':goodsCode
						    	}
						    });
						});
					}
					else
					{
						layer.msg(oks.msg, {icon: 5});
					}
				});
			});
		}
	});
	
	var tableIns2 = table.render({
		elem: '#goodsStockDetailTables',
		cols: [
			[
			{
				field: 'goodsStockDetailId',
				width: 80,
				title: '编码',
				align: 'left',
			},
			{
				field: 'stockCount0',
				width: 100,
				title: '入库库存',
				align: 'left',
			},
			{
				field: 'createDateText',
				width: 160,
				title: '入库时间',
				align: 'left',
			},
			{
				field: 'stockCount',
				width: 100,
				title: '剩余库存',
				align: 'left',
			},
			{
				field: 'productDateText',
				width: 120,
				title: '生产日期',
				align: 'left'
			},
			{
				field: 'expiredDateText',
				width: 120,
				title: '有效日期',
				align: 'left'
			},
			{
				field: 'stockBillType',
				width: 100,
				title: '库存类型',
				align: 'left',
				templet: '#stockTypeTpl'
			},
			{
				title: '常用操作',
				width: 100,
				align: 'center',
				toolbar: '#viewTpl',
				fixed:"right"
			}]
		],
		url: 'store_goods_stock_data_detail.html',
		page: false,
		even: true
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
		stockAmountParam = datas.stockAmountParam;
		goodsCode = datas.goodsCode;
		
		tableIns.reload({
	    	where :{
	    		'parentTypeId':parentTypeId,
	    		'goodsTypeId':goodsTypeId,
	    		'stockAmountParam':stockAmountParam,
	    		'goodsCode':goodsCode
	    	}
	    });
		
    	return false;
	});

});