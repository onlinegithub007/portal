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
    var purchaseOrderData;
		
    // 日期控件
    laydate.render({
	  elem: '#productDate' //指定元素
	});
    
    laydate.render({
  	  elem: '#expiredDate' //指定元素
  	});
    
	var tableIns = table.render({
		elem: '#deliverAckGoodsTables',
		cols: [
			[{
				field: 'goodsCode',
				width: 150,
				title: '商品条码',
				align: 'left'
			},
			{
				field: 'goodsName',
				width: 250,
				title: '商品名称',
				align: 'left',
			},
			{
				field: 'providerAckAmount',
				width: 100,
				title: '订单数量',
				align: 'left',
			},
			{
				field: 'providerUnit',
				width: 80,
				title: '单位',
				align: 'left',
			},
			{
				width: 120,
				title: '实收数量',
				align: 'left',
				templet: '#goodsAckAmountTpl'
			},
			{
				field: 'ackStatus',
				width: 100,
				title: '收货状态',
				align: 'left',
				templet: '#ackStatusTpl'
			},
			{
				title: '常用操作',
				width: 150,
				align: 'center',
				toolbar: '#goodsbar',
				fixed:"right"
			}]

		],
		url: 'store_purchase_order_view_goods.html',
		where :{
			'purchaseOrderId':purchaseOrderId
		},
		page: false,
		even: true,
		done: function(res, curr, count){
			// 引用返回表格中所有数据，为后面批量设定价格做准备
			purchaseOrderData = res.data;
		}
	});

	//监听工具条
	table.on('tool(deliverAckGoodsTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event === 'ack_goods') 
		{
			var purchaseDetailId = data.purchaseDetailId;
			
			layer.open({
				type:1,
				btn: ['货物验收', '取消'],
				title:'确认商品明细 ' + data.goodsName,
				area: ['600px', '350px'],
				content: $("#goodsConfirm"),
				yes: function(index, layero){
					
					var goodsAckAmount = $("#goodsAckAmount").val();
					
					if (goodsAckAmount > data.providerAckAmount)
					{
						layer.msg('实际验收数量不能大于订单商品数量', {icon: 5});
						return false;
					}
					
					if (goodsAckAmount < 0)
					{
						layer.msg('实际验收数量不能小于0', {icon: 5});
						return false;
					}
					
					// 发起验收流程
					var params = {
						'purchaseDetailId' : purchaseDetailId,
						'goodsId' : data.goodsId,
						'goodsAckAmount' : $("#goodsAckAmount").val(),
						'productDateText' : $("#productDate").val(),
						'hasQualityPeriod' : data.hasQualityPeriod
					};
					
					$.post('store_stock_orders_ack_goods2.html', params, function(json){
						var oks =  $.parseJSON(json); 
						  
						if (oks.success == 0)
						{
							layer.close(index);
							$("#goodsConfirm").addClass("layui-hide");
							
							layer.alert(oks.msg, {icon: 6}, function(index){
								layer.close(index);
								tableIns.reload({
									where :{
										'purchaseOrderId':purchaseOrderId
									},
								});
							});
						}
						else
						{
							layer.msg(oks.msg, {icon: 5});
						}
					});
				},
				btn2: function(index, layero){
					$("#goodsConfirm").addClass("layui-hide");
					layer.close(index);
				}
			});
			$("#goodsConfirm").removeClass("layui-hide");
			$("#ackGoodsName").text(data.goodsName);
			$("#ackGoodsAmount").text(data.providerAckAmount);
			// 缺省填上采购数量
			$("#goodsAckAmount").val(data.providerAckAmount);
			
			if (data.hasQualityPeriod == 0)
			{
				// 没有保质期属性
				$("#productDateInput").addClass("layui-hide");
			}
			else
			{
				// 有保质期属性
				$("#productDateInput").removeClass("layui-hide");
			}
			
			$("#productDate").val('');
		}
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
	
	// 批量收货验收
	$("#stockOrderAckAllBtn").click(function(){
		// 先采集批量的参数
		var purchaseDetailIdStr = "";
		var goodsAckAmountStr = "";
		
		for (var i = 0; i < purchaseOrderData.length; i++)
		{
			if (purchaseOrderData[i].ackStatus == 0)
			{
				purchaseDetailIdStr += purchaseOrderData[i].purchaseDetailId + ",";
				goodsAckAmountStr += $("#purchaseDetailId"+purchaseOrderData[i].purchaseDetailId).val() + ",";
			}
		}
		
		// 发起验收流程
		var params = {
			'purchaseDetailIdStr' : purchaseDetailIdStr,
			'goodsAckAmountStr' : goodsAckAmountStr
		};
		
		$.post('store_stock_orders_ackall_goods.html', params, function(json){
			var oks =  $.parseJSON(json); 
			  
			if (oks.success == 0)
			{
				layer.alert(oks.msg, {icon: 6}, function(index){
					$("#stockOrderAckAllBtn").addClass("layui-hide");
					layer.close(index);
					tableIns.reload({
						where :{
							'purchaseOrderId':purchaseOrderId
						},
					});
				});
			}
			else
			{
				layer.alert(oks.msg, {icon: 5});
			}
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