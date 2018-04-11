layui.use(['jquery', 'layer', 'form', 'upload'], function() {
	var $ = layui.$,
		layer = layui.layer,
		upload = layui.upload,
		form = layui.form;

	var uploadInst = upload.render({
		elem: '#larry_photo' //绑定元素
			,
		url: '/upload/' //上传接口
			,
		done: function(res) {
			//上传完毕回调
		},
		error: function() {
			//请求异常回调
		}
	});
	
	
	form.on('submit(merUserSubmit)',function(data){

		var datas = data.field;
		
		$.post('save_merUser_personal.html', datas, function(json){
			  var oks =  $.parseJSON(json); 
			  
			  if (oks.success == -1)
			  {
				  // 更新失败
				  layer.msg(oks.msg, {icon: 5});
			  }
			  else if (oks.success == 0)
			  {
				  layer.msg(oks.msg, {icon: 6});
			  }
		  });
		
    	return false;
	});
	
	form.on('submit(passwordSubmit)',function(data){

		var datas = data.field;
		
		$.post('save_password.html', datas, function(json){
			  var oks =  $.parseJSON(json); 
			  
			  if (oks.success == -1)
			  {
				  // 更新失败
				  layer.msg(oks.msg, {icon: 5});
			  }
			  else if (oks.success == 0)
			  {
				  layer.msg(oks.msg, {icon: 6});
			  }
		  });
		
    	return false;
	});
	
	// 这个放在form事件的后面！
	form.verify({
		userName: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '用户姓名不能为空';
	    	}
		    
			if(/(^\_)|(\__)|(\_+$)/.test(value)){
		      return '用户名首尾不能出现下划线\'_\'';
		    }
		}
		,oldPwd:[
		    /^[\S]{6,12}$/
		    ,'请输入老密码，密码必须6到12位，且不能出现空格'
		]
		,newPwd1:[
		    /^[\S]{6,12}$/
		    ,'请输入新密码，密码必须6到12位，且不能出现空格'
		]
		,newPwd2:function(value, item){
			if(value.length == 0)
	    	{
	    		return '请再次输入新密码';
	    	}
			
			// 检查新老密码是否相同
			var newPwd1 = $("#newPwd1").val();
			if (newPwd1 != value)
			{
				return '两次输入新密码不相同，请重新检查后再次输入';
			}
		}
	}); 
});