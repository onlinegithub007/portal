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
		elem: '#goodsStockOrderReportsTables',
		cols: [
			[{
				field: 'stockOrderSn',
				width: 60,
				title: '编号',
				align: 'left'
			},
			{
				field: 'groupName',
				width: 180,
				title: '门店',
				align: 'left',
			},
			{
				field: 'createDateText',
				width: 170,
				title: '创建时间',
				align: 'left',
			},
			{
				field: 'linkMan',
				width: 80,
				title: '联系人',
				align: 'left',
			},
			{
				field: 'linkManPhone',
				width: 120,
				title: '联系电话',
				align: 'left',
			},
			{
				field: 'goodsCount',
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
				field: 'ackGoodsCount',
				width: 120,
				title: '收货商品数量',
				align: 'right',
				templet: '#ackGoodsCountTpl'
			},
			{
				field: 'ackGoodsTotalPriceText',
				width: 150,
				title: '收货总价值(元)',
				align: 'right',
				templet: '#ackGoodsTotalPriceTextTpl'
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
				width: 100,
				align: 'center',
				toolbar: '#goodsbar',
				fixed:"right"
			}]

		],
		url: 'store_stock_orders_reports_data.html',
		page: true,
		even: true
	});

	//监听工具条
	table.on('tool(goodsStockOrderReportsTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event == 'view')
		{
			var stockOrderId = data.stockOrderId;
			
			// 打开新窗口
			layer.open({
				type:2,
				maxmin: true,
				title:'查看进货单详细信息',
				area: ['800px', '500px'],
				content: 'store_stock_orders_view.html?stockOrderId=' + stockOrderId+"&adminMode=true"
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