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
    
    laydate.render({
  	  elem: '#stockOrderStartDate' //指定元素
  	});
    
    laydate.render({
  	  elem: '#stockOrderEndDate' //指定元素
  	});
    
	var tableIns = table.render({
		elem: '#myOrdersTables',
		cols: [
			[{
				field: 'orderSn',
				width: 60,
				title: '编号',
				align: 'left'
			},
			{
				field: 'dealType',
				width: 100,
				title: '订单类型',
				align: 'left',
				templet: '#dealTypeTpl'
			},
			{
				field: 'createDateText',
				width: 170,
				title: '创建时间',
				align: 'left',
			},
			{
				field: 'groupName',
				width: 200,
				title: '采购门店',
				align: 'left',
			},
			{
				field: 'linkMan',
				width: 100,
				title: '门店联系人',
				align: 'left',
			},
			{
				field: 'goodsAmount',
				width: 100,
				title: '采购总数量',
				align: 'right',
			},
			{
				field: 'goodsTotalPriceText',
				width: 130,
				title: '采购总价值(元)',
				align: 'right',
			},
			{
				field: 'providerName',
				width: 300,
				title: '供应商',
				align: 'left'
			},
			{
				field: 'providerLinkman',
				width: 80,
				title: '联系人',
				align: 'left'
			},
			{
				field: 'providerAckStatus',
				width: 200,
				title: '供应商确认状态',
				align: 'left',
				templet: '#providerAckStatusTpl'
			},
			{
				title: '常用操作',
				width: 100,
				align: 'center',
				toolbar: '#goodsbar',
				fixed:"right"
			}]

		],
		url: 'provider_orders_audit_data.html',
		page: true,
		even: true
	});
	
	var tableIns2 = table.render({
		elem: '#myOrdersGoodsTables',
		cols: [
			[
			{
				field: 'goodsCode',
				width: 150,
				title: '商品编码',
				align: 'left',
			},
			{
				field: 'goodsName',
				width: 300,
				title: '商品名称',
				align: 'left',
			},
			{
				field: 'goodsAmount',
				width: 120,
				title: '进货数量',
				align: 'right'
			},
			{
				field: 'providerUnit',
				width: 90,
				title: '进货单位',
				align: 'right',
				templet:'#providerUnitTpl'
			},
			{
				width: 130,
				title: '供应商确认数量',
				align: 'right',
				templet: '#providerAckAmountTpl'
			}]
		],
		url: 'provider_my_orders_goods_data.html',
		page: false,
		even: true
	});
	
	var tableIns3 = table.render({
		elem: '#myOrdersGoodsTablesView',
		cols: [
			[
			{
				field: 'goodsCode',
				width: 150,
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
				field: 'goodsAmount',
				width: 100,
				title: '进货数量',
				align: 'right'
			},
			{
				field: 'providerUnit',
				width: 90,
				title: '进货单位',
				align: 'right',
				templet:'#providerUnitTpl'
			},
			{
				width: 100,
				title: '确认数量',
				align: 'right',
				templet: '#providerAckAmountTpl'
			}
			]
		],
		url: 'provider_my_orders_goods_data.html',
		page: false,
		even: true
	});

	//监听工具条
	table.on('tool(myOrdersTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event == 'ack' || obj.event == 'view')
		{
			var purchaseOrderId = data.purchaseOrderId;
			window.location.href = 'provider_purchase_orders_goods_audit.html?purchaseOrderId=' + purchaseOrderId+'&action='+obj.event;
		}
		else if (obj.event == 'submit') 
		{
			layer.confirm('确认要提交 当前[' + data.stockOrderSn + ']号进货单信息吗？', function(index) {
				var param = {
					'stockOrderId':data.stockOrderId
				};
				
				$.post('store_stock_orders_submit.html', param, function(json){
					  
					  var oks =  $.parseJSON(json); 
					  
					  if (oks.success == 0)
					  {
						  layer.msg(oks.msg, {icon: 6}, function(){
							  tableIns.reload();
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
	
	form.on('submit(stockOrderQuerySubmit)',function(data){

		var datas = data.field;
		
		tableIns.reload({
	    	where : datas
	    });
		
    	return false;
	});
	
	$("#providerAckAuditSaveBtn").click(function(){
		
		layer.confirm('确认同意且提交供应商的供货信息吗？', function(index) {
			$("#providerAckAuditSaveBtn").attr("disabled","disabled");
			$("#providerAckAuditSaveBtn").addClass("layui-disabled");
			
			$.post('provider_orders_audit_ack.html', null, function(json){
				var oks =  $.parseJSON(json); 
				
				if (oks.success == 1)
				{
					// 更新失败
					layer.msg(oks.msg, {icon: 5});
					$("#providerAckAuditSaveBtn").removeAttr("disabled","disabled");
					$("#providerAckAuditSaveBtn").removeClass("layui-disabled");
				}
				else if (oks.success == 0)
				{
					layer.msg(oks.msg, {icon: 6}, function(){
						window.location.href = 'provider_purchase_orders_audit.html';
					});
				}
			});
		});
		
		
    	return false;
	});
	
	$("#providerAckPrintBtn").click(function(){
		window.print();
	});

});