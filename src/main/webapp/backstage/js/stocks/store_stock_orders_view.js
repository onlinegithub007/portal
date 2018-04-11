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
    var selectProviderPrmpIndex;
    var selectStorePrmpIndex;
    
    var currentStockOrderDetailId;
    
    var tableGoodsProvider;
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
				width: 150,
				title: '商品分类',
				align: 'left',
			},
			{
				field: 'goodsCode',
				width: 140,
				title: '商品编码',
				align: 'left',
			},
			{
				field: 'goodsName',
				width: 250,
				title: '商品名称',
				align: 'left',
			},
			{
				field: 'goodsCount',
				width: 80,
				title: '数量',
				align: 'right'
			},
			{
				field: 'providerUnit',
				width: 100,
				title: '进货单位',
				align: 'right',
				templet:'#providerUnitTpl'
			}
			,{
				field: 'goodsTotalPriceText',
				width: 130,
				title: '商品总成本(元)',
				align: 'right',
			},
			{
				field: 'ackGoodsCount',
				width: 100,
				title: '实收数量',
				align: 'right',
				templet: '#ackGoodsCountTpl'
			}
			,{
				field: 'ackGoodsTotalPriceText',
				width: 130,
				title: '实收总成本(元)',
				align: 'right',
				templet: '#ackGoodsTotalPriceTextTpl'
			}
			]

		],
		url: 'store_stock_order_goods_data_audit.html',
		page: false,
		even: true,
		done: function(res, curr, count){
			form.render();
		}
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
				width: 100,
				title: '联系人',
				align: 'left',
			},
			{
				field: 'providerLinkmanPhone',
				width: 120,
				title: '手机联系方式',
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
				field: 'auditAmount',
				width: 120,
				title: '签收总件数',
				align: 'right',
				templet: '#auditAmountTpl'
			}
			,{
				field: 'auditTotalPriceText',
				width: 150,
				title: '签收商品总价(元)',
				align: 'right',
				templet: '#auditTotalPriceTpl'
			}
			]
		],
		url: 'store_stock_order_list_providers.html?dealType=0&orderStatus=',
		page: false,
		even: true
	});
	
	//监听工具条
	table.on('tool(stockOrderGoodsTables)', function(obj) {
		var data = obj.data;
		var goodsName = data.goodsName;
		
		currentStockOrderDetailId = data.stockOrderDetailId;
		
		if (obj.event === 'provider') 
		{
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
		
		var index = layer.confirm('在确认审核完成之前，请检查商品进货单信息是否设置完整，确认完成审核吗？', function(index) {
			
			var param = {
			    'pass' : true
			  };
			
			$.post('saveStockOrderAudit2.html', param, function(json){
				  
				layer.close(index);
				var oks =  $.parseJSON(json); 
				  
				if (oks.success == 0)
				{
					layer.msg(oks.msg, {icon: 6}, function(){
						window.location.href = 'store_stock_orders_audit.html?stockOrderStatus=2';
					});
				}
				else
				{
					layer.msg(oks.msg, {icon: 5});
				}
			  });
		});
		
	});
	
	$("#stockOrderBackBtn").click(function(){
		
		layer.prompt({
		  formType: 2,
		  value: '',
		  title: '请输入退回的理由，限于200个字',
		  area: ['250px', '100px'] //自定义文本域宽高
		}, function(value, index, elem){
		  layer.close(index);
		  
		  if (value.length > 0)
		  {
			  var param = {
			    'pass' : false,
			    'desc' : value
			  };
			  
			  $.post('saveStockOrderAudit2.html', param, function(json){

				layer.close(index);
				var oks =  $.parseJSON(json); 
				  
				if (oks.success == 0)
				{
					layer.msg(oks.msg, {icon: 6}, function(){
						window.location.href = 'store_stock_orders_audit.html?stockOrderStatus=2';
					});
				}
				else
				{
					layer.msg(oks.msg, {icon: 5});
				}
			  });
		  }
		  
		});
		
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
				  layer.msg(oks.msg, {icon: 6});
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
	
	$("#printBtn").click(function(){
		window.print();
	});
	
});