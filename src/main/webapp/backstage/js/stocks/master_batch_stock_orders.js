layui.use(['layer', 'form', 'table', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		common = layui.common;

	var tableIns = table.render({
		elem: '#groupsTables',
		cols: [
			[ 
			{
				field: 'createDateText',
				width: 200,
				title: '创建时间',
				align: 'left',
			},
			{
				field: 'creator',
				width: 100,
				title: '创建人',
				align: 'left',
			},
			{
				field: 'goodsCount',
				width: 100,
				title: '商品数量',
				align: 'left',
			},
			{
				field: 'storesCount',
				width: 100,
				title: '门店数量',
				align: 'left',
			},
			{
				field: 'batchOrderStatus',
				width: 100,
				title: '状态',
				align: 'left',
				templet: '#activeTpl'
			},
			{
				title: '常用操作',
				width: 300,
				align: 'left',
				toolbar: '#groupsbar',
				fixed:"right"
			}
			]

		],
		url: 'master_batch_stock_ordes_data.html',
		page: true,
		even: true
	});

	//监听工具条
	table.on('tool(groupsTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event == 'delete') 
		{
			var batchStockOrderId = data.batchStockOrderId;
			
			layer.confirm('真的删除 当前批量采购单信息吗吗？', function(index) {
				
				var param = {
					'batchStockOrderId' : batchStockOrderId
				};
				
				$.post('master_batch_stock_ordes_delete.html', param, function(json){
					  
					  var oks =  $.parseJSON(json); 
					  
					  if (oks.success == 0)
					  {
						  layer.close(index);
						  tableIns.reload();
					  }
				  });
			});
		}
		else if (obj.event == 'goods') 
		{
			var batchStockOrderId = data.batchStockOrderId;
			window.location.href = 'master_batch_stock_order_goods.html?batchStockOrderId=' + batchStockOrderId;
		}
		else if (obj.event == 'stores') 
		{
			var batchStockOrderId = data.batchStockOrderId;
			window.location.href = 'master_batch_stock_orders_stores.html?batchStockOrderId=' + batchStockOrderId;
		}
		else if (obj.event == 'batch') 
		{
			var param = {
				'batchStockOrderId' : data.batchStockOrderId
			};
			
			layer.confirm('确认提交当前批量采购单信息吗？', function(index) {
				$.post('master_batch_stock_orders_submit.html', param, function(json){
					  var oks =  $.parseJSON(json); 
					  
					  if (oks.success == 1)
					  {
						  // 更新失败
						  layer.msg(oks.msg, {icon: 5});
					  }
					  else if (oks.success == 0)
					  {
						  layer.alert(oks.msg, {icon: 6});
						  tableIns.reload();
					  }
				  });
			});
		}
	});
	
	form.on('submit(stockOrderCreateSubmit)',function(data){
		
		$.post('master_batch_stock_ordes_create.html', null, function(json){
			  var oks =  $.parseJSON(json); 
			  
			  if (oks.success == 1)
			  {
				  // 更新失败
				  layer.msg(oks.msg, {icon: 5});
			  }
			  else if (oks.success == 0)
			  {
				  tableIns.reload();
			  }
		  });
		
    	return false;
	});
	
	form.on('checkbox(getUserAccount)', function(data){
		if(data.elem.checked)
		{
			var userAccount = $("#userAccount").val();
			var userName = $("#userName").val();
			
			if (userAccount.length == 0)
			{
				if (userName.length > 0)
				{
					var datas = {
						'chineseChars' : userName
					};
					
					$.post('pinyin.html', datas, function(json){
						  var oks =  $.parseJSON(json); 
						  
						  if (oks.success == 0)
						  {
							  $("#userAccount").val(oks.msg);
						  }
					  });
				}
			}
		}
	});
	
	// 这个放在form事件的后面！
	form.verify({
		groupName: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '门店名称不能为空';
	    	}
		},
		groupCode: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '门店编号不能为空';
	    	}
		},
		detailAddress:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '请输入门店的详细地址';
	    	}
		},
		userName:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '请输入店长姓名';
	    	}
		},
		userAccount:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '请输入店长账号';
	    	}
		},
		userPhone:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '请输入店长手机号';
	    	}
		}
	}); 

});