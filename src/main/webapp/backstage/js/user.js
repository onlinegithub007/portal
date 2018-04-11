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
				field: 'userAccount',
				width: 150,
				title: '账号',
				align: 'left',
			},
			{
				field: 'merCode',
				width: 150,
				title: '备用账号',
				align: 'left',
			},
			{
				field: 'userName',
				width: 100,
				title: '用户姓名',
				align: 'left',
			}, 
			{
				field: 'userRoleName',
				width: 160,
				title: '用户身份',
				align: 'left',
			}, 
			{
				field: 'groupName',
				width: 150,
				title: '所属部门',
				align: 'left',
			}, {
				field: 'userPhone',
				width: 120,
				title: '手机号',
				align: 'center',
			}, {
				field: 'userEmail',
				width: 250,
				title: '邮箱',
				align: 'left',
			}, {
				field: 'lastLoginIp',
				width: 150,
				title: '最后一次登录ip',
				align: 'center',
			}, {
				field: 'lastLoginDateText',
				width: 180,
				title: '上一次登录时间',
				align: 'center',
			}, {
				title: '常用操作',
				width: 200,
				align: 'center',
				toolbar: '#userbar',
				fixed:"right"
			}]

		],
		url: 'merchant_users_data.html',
		page: false,
		even: true
	});

	//监听工具条
	table.on('tool(userTables)', function(obj) {
		var data = obj.data;
		if (obj.event === 'edit') 
		{
			var merUserId = data.merUserId;
			window.location.href = 'merchant_users_info.html?merUserId=' + merUserId;
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

				$.post('delete_merchant_users.html', data, function(json){
					  
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
	
	form.on('submit(merUserSubmit)',function(data){

		var datas = data.field;
		
		$.post('save_merchant_user.html', datas, function(json){
			  var oks =  $.parseJSON(json); 
			  
			  if (oks.success == 1)
			  {
				  // 更新失败
				  layer.msg(oks.msg, {icon: 5});
			  }
			  else if (oks.success == 0)
			  {
				  window.location.href = 'merchant_users.html';
			  }
		  });
		
    	return false;
	});
	
	// 这个放在form事件的后面！
	form.verify({
		account: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '用户账号不能为空';
	    	}
		    
		    if(!new RegExp("^[a-zA-Z0-9_@\\s·]+$").test(value)){
				return '用户账号不能有中文及特殊字符';
		    }
		}
		,userName:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '用户姓名不能为空';
	    	}
		}
		,mobilePhone:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '用户手机号不能为空';
	    	}
		}
	});
});