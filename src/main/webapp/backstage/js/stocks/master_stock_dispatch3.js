layui.use(['layer', 'form', 'table', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		common = layui.common;

	var tableIns = table.render({
		elem: '#stockDispatchTables',
		id: 'stockDispatchTables',
		cols: [
			[ 
			{
				field: 'orderSn',
				width: 80,
				title: '编号',
				align: 'left',
			},
			{
				field: 'createDateText',
				width: 180,
				title: '创建时间',
				align: 'left',
			},
			{
				field: 'fromStoreName',
				width: 200,
				title: '所在门店',
				align: 'left',
			},
			{
				field: 'groupName',
				width: 200,
				title: '调拨至门店',
				align: 'left'
			},
			{
				field: 'orderStatus',
				width: 200,
				title: '状态',
				align: 'left',
				templet: '#orderStatusTpl'
			}
			, {
				title: '常用操作',
				width: 150,
				align: 'center',
				toolbar: '#groupsbar',
				fixed:"right"
			}]

		],
		url: 'master_stock_dispatch1_data.html',
		where :{
			'orderStatus' : 1,
			'fromStoreId' :'yes'
		},
		page: true,
		even: true
	});

	//监听工具条
	table.on('tool(stockDispatchTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event === 'view') 
		{
			window.location.href = "master_stock_dispatch_details.html?purchaseOrderId=" + data.purchaseOrderId + "&url=master_stock_dispatch3_details";
		}
	});
	
	$("#agreeDispachBtn").click(function(){
		var checkStatus = table.checkStatus('stockDispatchTables');
		if(checkStatus.data.length == 0)
		{
			layer.alert('您还没有选择任何一项库存调拨的计划', {icon: 5});
			return;
		}
		
		var dispatchIds = "";
		for (i = 0; i < checkStatus.data.length; i++)
		{
			dispatchIds += checkStatus.data[i].stockDispatchId + ",";
		}
		
		var params = {
			'dispatchIds' : dispatchIds
		};
		
		$.post('master_stock_dispatch0_action.html', params, function(json){
		  var oks =  $.parseJSON(json); 
		  
		  if (oks.success == 1)
		  {
			  // 更新失败
			  layer.alert(oks.msg, {icon: 5});
		  }
		  else if (oks.success == 0)
		  {
			  layer.alert(oks.msg, {icon: 6}, function(index){
				  layer.close(index);
				  tableIns.reload();
			  });
		  }
		  
	  });
	});
	
	form.on('submit(queryDispatchOrders)',function(data){
		
		var datas = data.field;
		
		tableIns.reload({
			where :{
				'orderStatus' : datas.orderStatus,
				'fromStoreId' :'yes'
			}
		});
		
    	return false;
	});

});