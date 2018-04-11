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
		
	var tableIns = table.render({
		elem: '#goodsStockOutTables',
		cols: [
			[
			{
				field: 'orderSn',
				width: 100,
				title: '调拨单编号',
				align: 'left',
			},
			{
				field: 'groupName',
				width: 200,
				title: '调拨请求门店',
				align: 'left',
			},
			{
				field: 'linkMan',
				width: 100,
				title: '联系人',
				align: 'left',
			},
			{
				field: 'linkManPhone',
				width: 120,
				title: '联系人电话',
				align: 'left',
			},
			{
				field: 'createDateText',
				width: 200,
				title: '发起时间',
				align: 'left',
			},
			{
				field: 'goodsAmount',
				width: 120,
				title: '调拨数量',
				align: 'left',
			},
			{
				title: '常用操作',
				width: 120,
				align: 'center',
				toolbar: '#viewTpl',
				fixed:"right"
			}]

		],
		url: 'store_goods_stock_out_data.html',
		page: false,
		even: true
	});
	
	//监听工具条
	table.on('tool(goodsStockOutTables)', function(obj) {
		
		var data = obj.data;
		
		window.location.href = 'store_goods_stock_out_goods.html?purchaseOrderId=' + data.purchaseOrderId;
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
	
	var tableIns3 = table.render({
		elem: '#goodsStockOutGoodsTables',
		cols: [
			[
			{
				field: 'goodsCode',
				width: 150,
				title: '商品条码',
				align: 'left',
			},
			{
				field: 'goodsName',
				width: 300,
				title: '商品名称',
				align: 'left',
			},
			{
				field: 'stockAmount',
				width: 100,
				title: '目前库存',
				align: 'left',
			},
			{
				field: 'goodsAmount',
				width: 100,
				title: '调拨数量',
				align: 'left',
			}]
		],
		url: 'store_goods_stock_out_goods_data.html',
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
	
	$("#printBtn").click(function(){
		window.print();
	});
	
	$("#stockOutOrderAckBtn").click(function(){
		$.post('store_goods_stock_out_ack.html', null, function(data){
			
			var oks =  $.parseJSON(data); 
			  
			if (oks.success == 0)
			{
				layer.msg(oks.msg, {icon: 6}, function(){
					window.location.href = "store_goods_stock_out.html";
				});
			}
			else
			{
				layer.msg(oks.msg, {icon: 5});
			}
		});
	});
	
	form.on('submit(goodsQuerySubmit)',function(data){

		var datas = data.field;
		
		parentTypeId = datas.parentTypeId;
		goodsTypeId = datas.goodsTypeId;
		stockAmountParam = datas.stockAmountParam;
		
		tableIns.reload({
	    	where :{
	    		'parentTypeId':parentTypeId,
	    		'goodsTypeId':goodsTypeId,
	    		'stockAmountParam':stockAmountParam
	    	}
	    });
		
    	return false;
	});

});