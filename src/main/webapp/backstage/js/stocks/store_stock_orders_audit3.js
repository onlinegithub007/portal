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
    var status = -1;
    var viewGoodsPrmpIndex;
    var viewOrderPrmpIndex;
    
    var currentStockOrderDetailId;
    
    var viewPurchaseOrderGoodsTables;
    var tableStoreGoodsStock;
    
    var selectedGoodsCount;
		
    // 日期控件
    laydate.render({
	  elem: '#stockOrderDate' //指定元素
	});
	
	var tableStockGoods = table.render({
		elem: '#stockOrderGoodsTables',
		cols: [
			[
			{
				field: 'goodsTypeName',
				width: 130,
				title: '商品分类',
				align: 'left',
			},
			{
				field: 'goodsCode',
				width: 180,
				title: '商品编码',
				align: 'left',
			},
			{
				field: 'goodsName',
				width: 200,
				title: '商品名称',
				align: 'left',
			},
			{
				field: 'goodsCount',
				width: 100,
				title: '进货数量',
				align: 'right'
			},
			{
				field: 'providerUnit',
				width: 100,
				title: '单位',
				align: 'right',
				templet:'#providerUnitTpl'
			}
			,{
				field: 'goodsTotalPriceText',
				width: 130,
				title: '商品总成本(元)',
				align: 'right',
			}
			,{
				field: 'toProviderName',
				width: 300,
				title: '供应商',
				align: 'left'
			}
			,{
				field: 'providerCostText',
				width: 120,
				title: '采购价格(元)',
				align: 'right',
				templet: '#providerCostTextTpl'
			}
			]

		],
		url: 'store_stock_order_goods_data_audit.html',
		page: false,
		even: true
	});
	
	var tableProviderPurchase = table.render({
		elem: '#providerPurchaseTables',
		cols: [
			[
			{
				field: 'providerName',
				width: 250,
				title: '供货商',
				align: 'left',
			},
			{
				field: 'providerLinkman',
				width: 250,
				title: '供货商联系人',
				align: 'left',
			},
			{
				field: 'providerLinkmanPhone',
				width: 150,
				title: '手机联系方式',
				align: 'left',
			},
			{
				field: 'providerLinkmanEmail',
				width: 200,
				title: '电子邮件',
				align: 'left',
			},
			{
				field: 'goodsAmount',
				width: 100,
				title: '商品总件数',
				align: 'right'
			}
			,{
				field: 'goodsTotalPriceText',
				width: 130,
				title: '商品总价(元)',
				align: 'right'
			},
			{
				title: '常用操作',
				width: 200,
				align: 'center',
				toolbar: '#viewGoodsTpl',
				fixed:"right"
			}
			]
		],
		url: 'store_stock_order_list_providers.html?dealType=0&orderStatus=0',
		page: false,
		even: true
	});
	
	var tableStorePurchase = table.render({
		elem: '#storePurchaseTables',
		cols: [
			[
			{
				field: 'providerName',
				width: 250,
				title: '调拨分店',
				align: 'left',
			},
			{
				field: 'providerLinkman',
				width: 250,
				title: '调拨分店联系人',
				align: 'left',
			},
			{
				field: 'providerLinkmanPhone',
				width: 150,
				title: '手机联系方式',
				align: 'left',
			},
			{
				field: 'providerLinkmanEmail',
				width: 200,
				title: '电子邮件',
				align: 'left',
			},
			{
				field: 'goodsAmount',
				width: 100,
				title: '商品总件数',
				align: 'left'
			},
			{
				title: '常用操作',
				width: 200,
				align: 'center',
				toolbar: '#viewStoreGoodsTpl',
				fixed:"right"
			}
			]
		],
		url: 'store_stock_order_list_providers.html?dealType=1&orderStatus=0',
		page: false,
		even: true
	});
	
	//监听工具条
	table.on('tool(providerPurchaseTables)', function(obj) {
		var data = obj.data;
		
		if (obj.event == 'viewOrder') 
		{
			viewGoodsPrmpIndex = layer.open({
				type:2,
				maxmin: true,
				title:'查看采购单 [' + data.providerLinkman + '] ',
				area: ['900px', '500px'],
				content: "store_purchase_order_view.html?purchaseOrderId=" + data.purchaseOrderId
			});
		}
		else if (obj.event === 'save') 
		{
			var stockOrderDetailId = data.stockOrderDetailId;
			var providerCost = $("#goodsCost" + stockOrderDetailId).val();
			var providerId = $("#providerId" + stockOrderDetailId).val();
			
			var params = {
				'stockOrderDetailId' : stockOrderDetailId,
				'providerCost' : providerCost,
				'toProviderId' : providerId
			};
			
			// 保存到平台
			$.post('set_goods_provider_info.html', params, function(json){
				var oks =  $.parseJSON(json); 
				if (oks.success == 1)
				{
					layer.msg(oks.msg, {icon: 5});
				}
				else
				{
					layer.msg('更新采购价格成功', {icon: 6});
				}
			});
		}
		else if (obj.event === 'store') 
		{
			var dealType = $("#dealType"+data.stockOrderDetailId).val();
			if (dealType == 0)
			{
				return;
			}
			
			selectedGoodsCount = data.goodsCount;
			
			selectStorePrmpIndex = layer.open({
				type:1,
				title:'为商品 [' + goodsName + '] 选择可以调拨的门店',
				area: ['450px', '200px'],
				content: "<div class=\"user-tables\"><table id=\"allStoreGoodsStockTables\" lay-filter=\"allStoreGoodsStockTables\"></table></div>"
			});
			
			tableStoreGoodsStock = table.render({
				elem: '#allStoreGoodsStockTables',
				cols: [
					[
					{
						field: 'groupName',
						width: 250,
						title: '分店',
						align: 'left'
					}
					,{
						field: 'stockAmount',
						width: 100,
						title: '库存',
						align: 'left'
					}
					, {
						title: '选择',
						width: 80,
						align: 'center',
						fixed:"right",
						toolbar:'#selectStoreTpl'
					}]
				],
				url: 'select_allstore_goods_stock.html',
				where :{
					'goodsId':data.goodsId
				},
				page: false,
				even: true
			});
		}
	});
	
	//监听工具条
	table.on('tool(storePurchaseTables)', function(obj) {
		var data = obj.data;
		
		if (obj.event == 'viewOrder') 
		{
			viewGoodsPrmpIndex = layer.open({
				type:2,
				maxmin: true,
				title:'查看调拨单 [' + data.providerLinkman + '] ',
				area: ['900px', '500px'],
				content: "store_purchase_order_view.html?purchaseOrderId=" + data.purchaseOrderId
			});
		}
	});
	
	table.on('tool(selectGoodsProviderTables)', function(obj) {
		var data = obj.data;
		var goodsName = data.goodsName;
		var userName = data.userName + '[' + data.merName + ']';
		var goodsCost = data.goodsCost;
		var dealType = $("#dealType" + currentStockOrderDetailId).val();
		
		if (obj.event === 'selectProvider') 
		{
			var providerNameObj = $("#providerName" + currentStockOrderDetailId);
			var providerCost = $("#goodsCost" + currentStockOrderDetailId);
			var providerIdObj = $("#providerId" + currentStockOrderDetailId);
			
			providerNameObj.text(userName);
			providerCost.val(goodsCost);
			providerIdObj.val(data.merUserId);
			
			var params = {
				'stockOrderDetailId' : currentStockOrderDetailId,
				'toProviderId' : data.merUserId,
				'providerCost' : data.goodsCost,
				'dealType' : dealType,
				'toProviderName' : userName
			};
			
			// 保存到平台
			$.post('set_goods_provider_info.html', params, function(json){
				  layer.close(selectProviderPrmpIndex);
			});
			
		}
	});
	
	table.on('tool(allStoreGoodsStockTables)', function(obj) {
		var data = obj.data;
		
		var groupName = data.groupName;
		var stockAmount = data.stockAmount;
		
		var dealType = $("#dealType" + currentStockOrderDetailId).val();
		
		if (obj.event === 'selectStore') 
		{
			if (stockAmount < selectedGoodsCount)
			{
				layer.msg('当前门店库存不足', {icon: 5});
				return;
			}

			var storeNameObj = $("#storeName" + currentStockOrderDetailId);
			storeNameObj.text(groupName);
			
			var params = {
				'stockOrderDetailId' : currentStockOrderDetailId,
				'fromStoreId' : data.merGroupId,
				'dealType' : dealType,
				'fromStoreName' : groupName
			};
			
			// 保存到平台
			$.post('set_stock_order_whichstore.html', params, function(json){
				  layer.close(selectStorePrmpIndex);
			});
			
		}
	});
	
	$("#stockOrderSaveBtn").click(function(){
		
		var index = layer.confirm('确认采购单无误，且完成下达采购单吗？', function(index) {
			
			var param = {
			    'pass' : true
			  };
			
			$.post('saveStockOrderAudit3.html', param, function(json){
				  
				layer.close(index);
				var oks =  $.parseJSON(json); 
				  
				if (oks.success == 0)
				{
					layer.alert(oks.msg, {icon: 6}, function(){
						window.location.href = 'store_stock_orders_audit.html?stockOrderStatus=3';
					});
				}
				else
				{
					layer.msg(oks.msg, {icon: 5});
				}
			  });
		});
		
	});
	
	$("#printBtn").click(function(){
		window.print();
	});
	
	form.on('submit(stockOrderInfoSubmit)',function(data){

		var datas = data.field;
		
		$.post('save_store_stock_orders.html', datas, function(json){
			  var oks =  $.parseJSON(json); 
			  
			  if (oks.success == 1)
			  {
				  // 更新失败
				  layer.msg(oks.msg, {icon: 5});
			  }
			  else if (oks.success == 0)
			  {
				  layer.alert(oks.msg, {icon: 6});
				  window.location.href = 'store_stock_orders.html';
			  }
			  else if (oks.success == 2)
			  {
				  layer.msg(oks.msg, {icon: 6});
			  }
		  });
		
    	return false;
	});
	
	// 这个放在form事件的后面！
	form.verify({
		linkMan: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '请输入联系人姓名';
	    	}
		},
		linkManPhone: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '请输入联系人电话';
	    	}
		},
		applyAddress: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '请输入收货地址';
	    	}
		}
	}); 
	
	
});