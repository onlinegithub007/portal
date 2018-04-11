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
		
    // 日期控件
    laydate.render({
	  elem: '#stockOrderDate' //指定元素
	});
    
	var tableIns = table.render({
		elem: '#goodsTables',
		cols: [
			[{
				field: 'stockOrderSn',
				width: 60,
				title: '编号',
				align: 'left'
			},
			{
				field: 'createDateText',
				width: 170,
				title: '创建时间',
				align: 'left',
			},
			{
				field: 'creator',
				width: 80,
				title: '创建者',
				align: 'left',
			},
			{
				field: 'goodsCount',
				width: 100,
				title: '采购数量',
				align: 'right',
			},
			{
				field: 'goodsTotalPriceText',
				width: 130,
				title: '采购总价值(元)',
				align: 'right',
			},
			{
				field: 'ackGoodsCount',
				width: 120,
				title: '收货确认数量',
				align: 'right',
			},
			{
				field: 'ackGoodsTotalPriceText',
				width: 150,
				title: '收货确认总价值',
				align: 'right',
			}
			,{
				field: 'stockOrderStatus',
				width: 160,
				title: '订单状态',
				align: 'left',
				templet: '#stockOrderStatusTpl'
			}
			, {
				title: '常用操作',
				width: 150,
				align: 'center',
				toolbar: '#goodsbar',
				fixed:"right"
			}]

		],
		url: 'store_stock_orders_data.html',
		where :{
			'stockOrderStatus':'4'
		},
		page: true,
		even: true
	});
	
	var deliverAckTables1 = table.render({
		elem: '#deliverAckTables1',
		cols: [
			[{
				field: 'orderSn',
				width: 60,
				title: '编号',
				align: 'left'
			},
			{
				field: 'goodsAmount',
				width: 200,
				title: '计划供货数量',
				align: 'left',
			},
			{
				field: 'auditAmount',
				width: 200,
				title: '实际验货数量',
				align: 'left',
			},
			{
				field: 'orderStatus',
				width: 100,
				title: '验货状态',
				align: 'left',
				templet: '#orderStatusTpl'
			}
			, {
				title: '常用操作',
				width: 150,
				align: 'center',
				toolbar: '#goodsbar',
				fixed:"right"
			}]

		],
		url: 'store_stock_order_list_providers.html?dealType=0',
		page: false,
		even: true
	});
	
	var deliverAckTables2 = table.render({
		elem: '#deliverAckTables2',
		cols: [
			[{
				field: 'orderSn',
				width: 60,
				title: '编号',
				align: 'left'
			},
			{
				field: 'providerName',
				width: 300,
				title: '供应商名称',
				align: 'left',
			},
			{
				field: 'goodsAmount',
				width: 200,
				title: '计划供货数量',
				align: 'left',
			},
			{
				field: 'auditAmount',
				width: 200,
				title: '验货数量',
				align: 'left',
			},
			{
				field: 'orderStatus',
				width: 100,
				title: '验货状态',
				align: 'left',
				templet: '#orderStatusTpl'
			}
			, {
				title: '常用操作',
				width: 150,
				align: 'center',
				toolbar: '#goodsbar',
				fixed:"right"
			}]

		],
		url: 'store_stock_order_list_providers.html?dealType=1&storeAck=1',
		page: false,
		even: true
	});

	//监听工具条
	table.on('tool(goodsTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event === 'ack') 
		{
			var stockOrderId = data.stockOrderId;
			window.location.href = 'store_stock_orders_ack.html?stockOrderId=' + stockOrderId;
		}
	});
	
	//监听工具条
	table.on('tool(deliverAckTables1)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event === 'ack_detail') 
		{
			layer.open({
				type:2,
				maxmin: true,
				title:'确认商品明细 [' + data.providerName + '] ',
				area: ['980px', '650px'],
				content: "store_stock_orders_ack_goods.html?purchaseOrderId=" + data.purchaseOrderId,
				cancel: function(){ 
					deliverAckTables1.reload();
				}
			});
		}
	});
	
	//监听工具条
	table.on('tool(deliverAckTables2)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event === 'ack_detail') 
		{
			layer.open({
				type:2,
				maxmin: true,
				title:'确认商品明细 [' + data.providerName + '] ',
				area: ['900px', '650px'],
				content: "store_stock_orders_ack_goods.html?purchaseOrderId=" + data.purchaseOrderId,
				cancel: function(){ 
					deliverAckTables2.reload();
				}
			});
		}
	});
	
	form.on('submit(stockOrderQuerySubmit)',function(data){

		var datas = data.field;
		
		tableIns.reload({
	    	where : datas
	    });
		
    	return false;
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
	
	$("#stockOrderAckBtn").click(function(){
		
		layer.confirm('请检查商品进货单信息是否全部验货，确认完成验收吗？', function(index) {
			
			$("#stockOrderAckBtn").attr("disabled","disabled");
			$("#stockOrderAckBtn").addClass("layui-disabled");
			
			$.post('store_stock_orders_ack_success.html', null, function(json){
				layer.close(index);
				
				var oks =  $.parseJSON(json); 
				
				if (oks.success == 1)
				{
					layer.msg(oks.msg, {icon: 5});
					$("#stockOrderAckBtn").removeAttr("disabled","disabled");
					$("#stockOrderAckBtn").removeClass("layui-disabled");
				}
				else
				{
					layer.alert(oks.msg, {icon: 6}, function(){
						window.location.href = 'store_stock_orders_ack.html';
					});
				}
			  });
		});
	});
	
	
	// 这个放在form事件的后面！
	form.verify({
		orderTitle: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '请输入进货单名称或描述';
	    	}
		},
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