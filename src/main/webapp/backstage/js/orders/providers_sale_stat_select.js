layui.use(['layer', 'form', 'table', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		common = layui.common;
	
	var tableIns = table.render({
		elem: '#userTables',
		cols: [
			[
			{
				field: 'userName',
				width: 100,
				title: '供应商姓名',
				align: 'left',
			}, 
			{
				field: 'merName',
				width: 250,
				title: '所属单位',
				align: 'left',
			}, {
				field: 'userPhone',
				width: 120,
				title: '手机号',
				align: 'center',
			}, {
				field: 'userEmail',
				width: 300,
				title: '邮箱',
				align: 'left',
			}, {
				field: 'userMemo',
				width: 200,
				title: '备注',
				align: 'left',
			},
			{
				title: '常用操作',
				width: 100,
				align: 'center',
				toolbar: '#userbar',
				fixed:"right"
			}]

		],
		url: 'merchant_goods_providers_data.html?status=0',
		page: false,
		even: true
	});

	//监听工具条
	table.on('tool(userTables)', function(obj) {
		var data = obj.data;
		if (obj.event == 'sell') 
		{
			var merUserId = data.merUserId;
			window.location.href = 'providers_sale_stat_report.html?merUserId=' + merUserId;
		}
	});
	
//	form.on('select(selectGroupId)', function(data){
//	    
//		var merGroupId = data.value;
//	    
//	    tableIns.reload({
//	    	where :{
//	    		'merGroupId':merGroupId
//	    	}
//	    });
//	});
//	
//	form.on('submit(merGpsSubmit)',function(data){
//
//		var datas = data.field;
//		
//		$("#merGpsSubmitBtn").addClass("layui-disabled");
//		$("#merGpsSubmitBtn").attr("disabled" , "disabled");
//		
//		$.post('save_merchant_gps.html', datas, function(json){
//			  var oks =  $.parseJSON(json); 
//			  
//			  if (oks.success == 1)
//			  {
//				  // 更新失败
//				  layer.msg(oks.msg, {icon: 5});
//				  
//				  $("#merGpsSubmitBtn").removeClass("layui-disabled");
//				  $("#merGpsSubmitBtn").removeAttr("disabled" , "disabled");
//			  }
//			  else if (oks.success == 0)
//			  {
//				  window.location.href = 'merchant_providers.html';
//			  }
//		  });
//		
//    	return false;
//	});
//	
//	// 这个放在form事件的后面！
//	form.verify({
//		userName:function(value, item){ //value：表单的值、item：表单的DOM对象
//		    if(value.length == 0)
//	    	{
//	    		return '供应商联系人不能为空';
//	    	}
//		}
//		,userAccount:function(value, item){ //value：表单的值、item：表单的DOM对象
//		    if(value.length == 0)
//	    	{
//	    		return '供应商账号不能为空，可以采用供应商手机号或邮件作为账号';
//	    	}
//		}
//		,merName:function(value, item){ //value：表单的值、item：表单的DOM对象
//		    if(value.length == 0)
//	    	{
//	    		return '供应商联单位不能为空';
//	    	}
//		}
//		,mobilePhone:function(value, item){ //value：表单的值、item：表单的DOM对象
//		    if(value.length == 0)
//	    	{
//	    		return '用户手机号不能为空';
//	    	}
//		}
//		,userEmail:function(value, item){ //value：表单的值、item：表单的DOM对象
//		    if(value.length == 0)
//	    	{
//	    		return '用户邮箱账号不能为空';
//	    	}
//		}
//	});
});