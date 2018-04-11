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
				field: 'createDateText',
				width: 160,
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
				field: 'memo',
				width: 300,
				title: '备注',
				align: 'left'
			}]

		],
		url: 'master_stock_dispatch2_data.html',
		page: false,
		even: true
	});

	$("#executeDispachBtn").click(function(){
		
		layer.confirm('确认审核通过该库存调拨计划吗？', function(index) {
			
			$("#executeDispachBtn").attr("disabled","disabled");
			$("#executeDispachBtn").addClass("layui-disabled");
			
			$.post('master_stock_dispatch2_action.html', null, function(json){
				
				layer.close(index);
				
				var oks =  $.parseJSON(json); 
				if (oks.success == 1)
				{
					// 更新失败
					layer.alert(oks.msg, {icon: 5});
					
					$("#executeDispachBtn").removeAttr("disabled","disabled");
					$("#executeDispachBtn").removeClass("layui-disabled");
				}
				else if (oks.success == 0)
				{
					layer.msg(oks.msg, {icon: 6}, function(index){
						window.location.href = "master_stock_dispatch1.html";
					});
				}
			});
			
		});
		
	});
	
	$("#ackOutDispachBtn").click(function(){
		
		layer.confirm('温馨提醒：请认真核对出库商品数量，您继续操作确认出库吗？', function(index) {
			
			$("#ackOutDispachBtn").attr("disabled","disabled");
			$("#ackOutDispachBtn").addClass("layui-disabled");
			
			$.post('master_stock_dispatch3_action.html', null, function(json){
				
				layer.close(index);
				
				var oks =  $.parseJSON(json); 
				if (oks.success == 1)
				{
					// 更新失败
					layer.alert(oks.msg, {icon: 5});
					
					$("#ackOutDispachBtn").removeAttr("disabled","disabled");
					$("#ackOutDispachBtn").removeClass("layui-disabled");
				}
				else if (oks.success == 0)
				{
					layer.msg(oks.msg, {icon: 6}, function(index){
						window.location.href = "master_stock_dispatch3.html";
					});
				}
			});
			
		});
		
	});
	
	$("#acceptDispachBtn").click(function(){
		
		layer.confirm('温馨提醒：请认真核对出库商品数量，您继续操作确认出库吗？', function(index) {
			
			$("#acceptDispachBtn").attr("disabled","disabled");
			$("#acceptDispachBtn").addClass("layui-disabled");
			
			$.post('master_stock_dispatch4_action.html', null, function(json){
				
				layer.close(index);
				
				var oks =  $.parseJSON(json); 
				if (oks.success == 1)
				{
					// 更新失败
					layer.alert(oks.msg, {icon: 5});
					
					$("#acceptDispachBtn").removeAttr("disabled","disabled");
					$("#acceptDispachBtn").removeClass("layui-disabled");
				}
				else if (oks.success == 0)
				{
					layer.msg(oks.msg, {icon: 6}, function(index){
						window.location.href = "master_stock_dispatch4.html";
					});
				}
			});
			
		});
		
	});
	
	$("#prntBtn").click(function(){
		window.print();
	});

});