layui.use(['layer', 'form', 'table', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		common = layui.common;

	var tableIns = table.render({
		elem: '#userTables',
		cols: [
			[{
				field: 'status',
				width: 100,
				title: '状态',
				align: 'left',
				templet: '#titleTpl'
			},
			{
				field: 'userAccount',
				width: 200,
				title: '账号',
				align: 'left',
			},
			{
				field: 'userName',
				width: 100,
				title: '店员姓名',
				align: 'left',
			},
			{
				field: 'userRoleName',
				width: 160,
				title: '用户身份',
				align: 'left',
			},
			{
				field: 'userPhone',
				width: 160,
				title: '手机号',
				align: 'center',
			}, 
			{
				title: '常用操作',
				width: 200,
				align: 'center',
				toolbar: '#userbar',
				fixed:"right"
			}]
		],
		url: 'merchant_stores_users_data.html?merGroupId=' + storeGroupId,
		page: false,
		even: true
	});
	
	//监听工具条
	table.on('tool(userTables)', function(obj) {
		var data = obj.data;
		if (obj.event === 'edit') 
		{
			var merUserId = data.merUserId;
			window.location.href = 'merchant_stores_users_info.html?merUserId=' + merUserId;
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
			
			var param = {
				'merUserId' : data.merUserId
			};
			
			layer.confirm('真的删除 当前店员 ['+data.userName+'] 信息吗？', function(index) {

				$.post('delete_merchant_store_user.html', param, function(json){
					  
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
	
	form.on('submit(merStoreUserSubmit)',function(data){

		$("#merStoreUserSubmit").addClass("layui-disabled");
		
		var datas = data.field;
		
		$.post('save_merchant_store_user.html', datas, function(json){
			  var oks =  $.parseJSON(json); 
			  
			  if (oks.success == 1)
			  {
				  // 更新失败
				  $("#merStoreUserSubmit").removeClass("layui-disabled");
				  layer.msg(oks.msg, {icon: 5});
			  }
			  else if (oks.success == 0)
			  {
				  window.location.href = 'merchant_stores_users.html?merGroupId=' + storeGroupId;
			  }
		  });
		
    	return false;
	});
	
	// 这个放在form事件的后面！
	form.verify({
		userAccount : function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '请输入店员账号';
	    	}
		}
		,userName : function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '请输入店员姓名';
	    	}
		}
		,userPhone : function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '请输入店员手机号';
	    	}
		}
	}); 

});