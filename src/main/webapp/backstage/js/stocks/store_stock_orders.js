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
				field: 'ackGoodsCount',
				width: 120,
				title: '收货确认数量',
				align: 'right',
			}
			,{
				field: 'stockOrderStatus',
				width: 120,
				title: '订单状态',
				align: 'left',
				templet: '#stockOrderStatusTpl'
			}
			, {
				title: '常用操作',
				width: 350,
				align: 'center',
				toolbar: '#goodsbar',
				fixed:"right"
			}]

		],
		url: 'store_stock_orders_data.html',
		where :{
			'stockOrderStatus':'0'
		},
		page: true,
		even: true
	});

	//监听工具条
	table.on('tool(goodsTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event === 'edit') 
		{
			var stockOrderStatus = data.stockOrderStatus;
			if (stockOrderStatus != 0)
			{
				layer.msg('该进货单已经进入审批处理流程，不允许再次修改。', {icon: 6});
				return;
			}
			
			var stockOrderId = data.stockOrderId;
			window.location.href = 'store_stock_order_info.html?stockOrderId=' + stockOrderId;
		}
		else if (obj.event == 'goods') 
		{
			var stockOrderId = data.stockOrderId;
			window.location.href = 'store_stock_order_goods.html?stockOrderId=' + stockOrderId;
		}
		else if (obj.event == 'view')
		{
			var stockOrderId = data.stockOrderId;
			window.location.href = 'store_stock_orders_view.html?stockOrderId=' + stockOrderId;
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
		else if (obj.event === 'del') {
			layer.confirm('真的删除 当前[' + data.stockOrderSn + ']号进货单信息吗？', function(index) {

				var param = {
					'stockOrderId':data.stockOrderId
				};
				
				$.post('delete_store_stock_orders.html', param, function(json){
					  
					  var oks =  $.parseJSON(json); 
					  
					  if (oks.success == 0)
					  {
						  obj.del();
						  layer.close(index);
					  }
				  });
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
	
	form.on('checkbox(againInputChecked)',function(data){
		againInputChecked = data.elem.checked; //是否被选中，true或者false
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