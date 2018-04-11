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
				field: 'status',
				width: 100,
				title: '状态',
				align: 'left',
				templet: '#titleTpl'
			},
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
				width: 200,
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
				width: 250,
				align: 'center',
				toolbar: '#userbar',
				fixed:"right"
			}]

		],
		url: 'merchant_goods_providers_data.html',
		page: false,
		even: true
	});

	//监听工具条
	table.on('tool(userTables)', function(obj) {
		var data = obj.data;
		if (obj.event === 'edit') 
		{
			var merUserId = data.merUserId;
			window.location.href = 'merchant_gps_info.html?merUserId=' + merUserId;
		}
		else if (obj.event === 'goods') 
		{
			var merUserId = data.merUserId;
			window.location.href = 'merchant_provider_goods.html?merUserId=' + merUserId;
		}
		else if (obj.event === 'disable') 
		{
			layer.confirm('真的禁用 当前用户 ['+data.userName+'] 信息吗？', function(index) {
				var datas = {
					'merUserId' : data.merUserId,
					'status' : 1
				};
					
				// 禁用用户  change_mer_user_status
				$.post('change_mer_user_status.html', datas, function(json){
					  
					  var oks =  $.parseJSON(json); 
					  
					  layer.close(index);

					  if (oks.success == 0)
					  {
						  window.location.reload();
					  }
					  else{
						  layer.msg(oks.msg, {icon: 5});
					  }
				  });
			});
		}
		else if (obj.event === 'enable') 
		{
			// 激活用户
			layer.confirm('真的激活 当前用户 ['+data.userName+'] 信息吗？', function(index) {
				var datas = {
					'merUserId' : data.merUserId,
					'status' : 0
				};
					
				// 禁用用户  change_mer_user_status
				$.post('change_mer_user_status.html', datas, function(json){
					  
					  var oks =  $.parseJSON(json); 
					  
					  layer.close(index);

					  if (oks.success == 0)
					  {
						  window.location.reload();
					  }
					  else{
						  layer.msg(oks.msg, {icon: 5});
					  }
				  });
			});
		}
		else if (obj.event === 'del') {
			layer.confirm('真的删除 当前用户 ['+data.userName+'] 信息吗？', function(index) {

				$.post('delete_merchant_gps.html', data, function(json){
					  
					  var oks =  $.parseJSON(json); 
					  
					  if (oks.success == 0)
					  {
						  obj.del();
						  layer.close(index);
					  }
					  else
					  {
						  layer.close(index);
						  layer.msg(oks.msg, {icon: 5});
					  }
				  });
			});
		}
	});
	
	form.on('select(selectGroupId)', function(data){
	    
		var merGroupId = data.value;
	    
	    tableIns.reload({
	    	where :{
	    		'merGroupId':merGroupId
	    	}
	    });
	});
	
	form.on('submit(merGpsSubmit)',function(data){

		var datas = data.field;
		
		$("#merGpsSubmitBtn").addClass("layui-disabled");
		$("#merGpsSubmitBtn").attr("disabled" , "disabled");
		
		$.post('save_merchant_gps.html', datas, function(json){
			  var oks =  $.parseJSON(json); 
			  
			  if (oks.success == 1)
			  {
				  // 更新失败
				  layer.msg(oks.msg, {icon: 5});
				  
				  $("#merGpsSubmitBtn").removeClass("layui-disabled");
				  $("#merGpsSubmitBtn").removeAttr("disabled" , "disabled");
			  }
			  else if (oks.success == 0)
			  {
				  window.location.href = 'merchant_providers.html';
			  }
		  });
		
    	return false;
	});
	
	// 这个放在form事件的后面！
	form.verify({
		userName:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '供应商联系人不能为空';
	    	}
		}
		,userAccount:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '供应商账号不能为空，可以采用供应商手机号或邮件作为账号';
	    	}
		}
		,merName:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '供应商联单位不能为空';
	    	}
		}
		,mobilePhone:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '用户手机号不能为空';
	    	}
		}
		,userEmail:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '用户邮箱账号不能为空';
	    	}
		}
	});
});