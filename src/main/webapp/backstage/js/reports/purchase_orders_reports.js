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
		elem: '#purchaseOrderReportsTables',
		cols: [
			[{
				field: 'orderSn',
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
				field: 'groupName',
				width: 250,
				title: '下单门店',
				align: 'left',
			},
			{
				field: 'providerName',
				width: 250,
				title: '供应商',
				align: 'left',
			},
			{
				field: 'providerLinkman',
				width: 100,
				title: '联系人',
				align: 'left',
			},
			{
				field: 'goodsAmount',
				width: 120,
				title: '计划采购数量',
				align: 'right',
			},
			{
				field: 'goodsTotalPriceText',
				width: 150,
				title: '原订单总价值(元)',
				align: 'right',
			},
			{
				width: 120,
				title: '收货商品数量',
				align: 'right',
				templet: '#auditAmountTpl'
			},
			{
				width: 150,
				title: '收货总价值(元)',
				align: 'right',
				templet: '#auditTotalPriceTpl'
			}
			, {
				title: '常用操作',
				width: 120,
				align: 'center',
				toolbar: '#goodsbar',
				fixed:"right"
			}]

		],
		url: 'purchase_orders_reports_data.html',
		page: true,
		even: true
	});

	//监听工具条
	table.on('tool(purchaseOrderReportsTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event == 'view')
		{
			// 打开新窗口
			layer.open({
				type:2,
				maxmin: true,
				title:'查看进货单详细信息',
				area: ['800px', '650px'],
				content: 'store_purchase_order_view.html?purchaseOrderId='+data.purchaseOrderId
			});
		}
	});
	
	
	form.on('submit(purchaseOrderQuerySubmit)',function(data){

		var datas = data.field;
		
		tableIns.reload({
	    	where : datas
	    });
		
    	return false;
	});
	
	$("#RefreshData").click(function(){
		
		$.post('purchase_orders_reports_data_update.html', null, function(json){
			  
			  var oks =  $.parseJSON(json); 
			  
			  if (oks.success == 0)
			  {
				  layer.msg(oks.msg, {icon: 6});
				  tableIns.reload();
			  }
			  else
			  {
				  layer.msg(oks.msg, {icon: 5});
			  }
		  });
		
	});

});