layui.use(['jquery','common','layer','form','larryMenu'],function(){
    var $ = layui.$,
        layer = layui.layer,
        form = layui.form,
        common = layui.common;
    // 页面上下文菜单
    var larryMenu = layui.larryMenu();

    var mar_top = ($(document).height()-$('#larry_login').height())/2.5;
    $('#larry_login').css({'margin-top':mar_top});
    
    //common.larryCmsSuccess('用户名：larry 密码：larry 无须输入验证码，输入正确后直接登录后台!','larryMS后台帐号登录提示',20);

    var placeholder = '';
    $("#larry_form input[type='text'],#larry_form input[type='password']").on('focus',function(){
          placeholder = $(this).attr('placeholder');
          $(this).attr('placeholder','');
    });
    
    $("#larry_form input[type='text'],#larry_form input[type='password']").on('blur',function(){
          $(this).attr('placeholder',placeholder);
    });
    
    common.larryCmsLoadJq('../common/plus/jquery.supersized.min.js', function() {
        $.supersized({
            // 功能
            slide_interval: 3000,
            transition: 1,
            transition_speed: 1000,
            performance: 1,
            // 大小和位置
            min_width: 0,
            min_height: 0,
            vertical_center: 1,
            horizontal_center: 1,
            fit_always: 0,
            fit_portrait: 1,
            fit_landscape: 0,
            // 组件
            slide_links: 'blank',
            slides: [{
                image: 'images/login/1.jpg'
            }, {
                image: 'images/login/2.jpg'
            }, {
                image: 'images/login/3.jpg'
            }]
        });
    });

    form.on('submit(submit)',function(data){
    	
    	var datas = data.field;
		
		// 提交登录
		  $.post('login.html', datas, function(json){
			  var oks =  $.parseJSON(json); 
			  
			  if (oks.success == -1 || oks.success == -2 || oks.success == 1)
			  {
					// 登录失败
					layer.msg(oks.msg, {icon: 5});
			  }
			  else if (oks.success == 2 || oks.success == 0)
			  {
				  window.location.href = oks.msg;
			  }
		  });
	    
	    // 拦截页面跳转
	    return false;
    });
    
    // 这个放在form事件的后面！
	form.verify({
		userAccount: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '用户账号不能为空';
	    	}
			
			if(!new RegExp("^[a-zA-Z0-9_@\u4e00-\u9fa5\\s·]+$").test(value)){
				return '用户名不能有特殊字符';
		    }
		    
			if(/(^\_)|(\__)|(\_+$)/.test(value)){
		      return '用户名首尾不能出现下划线\'_\'';
		    }
		},
		//我们既支持上述函数式的方式，也支持下述数组的形式
		//数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
		userPassword: [
		    /^[\S]{6,12}$/
		    ,'密码必须6到12位，且不能出现空格'
		],
		verifyCode: function(value, item){
			if(value.length == 0)
	    	{
	    		return '请输入图形验证码';
	    	}
		}
	}); 
	
	$("#changeCode").click(function(){
		
		var d = new Date();
		var url = "verifyCode.jpg?" + d.valueOf();
		
		$("#codeimage").attr('src',url); 
	});
    
});
    