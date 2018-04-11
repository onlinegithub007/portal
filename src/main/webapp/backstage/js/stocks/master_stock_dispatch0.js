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
				field: 'stockDispatchId',
				checkbox: true
			},
			{
				field: 'createDateText',
				width: 180,
				title: '创建时间',
				align: 'left',
			},
			{
				field: 'goodsName',
				width: 180,
				title: '商品名称',
				align: 'left',
			},
			{
				field: 'fromStoreName',
				width: 200,
				title: '所在门店',
				align: 'left',
			},
			{
				field: 'currentStockCount',
				width: 100,
				title: '当前库存',
				align: 'right',
				templet:'#currentStockCountTpl'
			},
			{
				field: 'dispatchStockCount',
				width: 100,
				title: '调拨库存',
				align: 'right',
				templet:'#dispatchStockCountTpl'
			},
			{
				field: 'toStoreName',
				width: 200,
				title: '调拨至门店',
				align: 'left'
			},
			{
				field: 'providerName',
				width: 300,
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
				field: 'productDateText',
				width: 120,
				title: '生产日期',
				align: 'left',
			}
			,{
				field: 'expiredDateText',
				width: 120,
				title: '保质日期',
				align: 'left',
			}
			, {
				title: '常用操作',
				width: 150,
				align: 'center',
				toolbar: '#groupsbar',
				fixed:"right"
			}]

		],
		url: 'master_stock_dispatch0_data.html',
		page: false,
		even: true
	});

	//监听工具条
	table.on('tool(stockDispatchTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event === 'undispatch') 
		{
			var params = {
				'stockDispatchId' : data.stockDispatchId
			};
			
			layer.confirm('确认移除当前商品的库存调拨计划吗？', function(index) {

				$.post('master_stock_dispatch0_cancel.html', params, function(json){
   					
					layer.close(index);
					  
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
	
	form.on('submit(storeSubmit)',function(data){

		$("#storeSubmit").addClass("layui-disabled");
		
		var datas = data.field;
		
		$.post('save_merchant_store.html', datas, function(json){
			  var oks =  $.parseJSON(json); 
			  
			  if (oks.success == 1)
			  {
				  // 更新失败
				  layer.msg(oks.msg, {icon: 5});
				  $("#storeSubmit").removeClass("layui-disabled");
			  }
			  else if (oks.success == 0)
			  {
				  window.location.href = 'merchant_stores.html';
			  }
		  });
		
    	return false;
	});

});