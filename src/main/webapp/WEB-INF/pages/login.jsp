<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${sessionScope.SystemTitle} - 登陆</title>
    <meta name="renderer" content="webkit">	
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">	
	<meta name="Author" content="larry" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">	
	<meta name="apple-mobile-web-app-capable" content="yes">	
	<meta name="format-detection" content="telephone=no">	
	<link rel="Shortcut Icon" href="../favicon.ico" />
	<!-- load css -->
	<link rel="stylesheet" type="text/css" href="../common/frame/layui/css/layui.css" media="all">
	<link rel="stylesheet" type="text/css" href="../common/css/gobal.css" media="all">
	<link rel="stylesheet" type="text/css" href="../common/css/animate.css" media="all">
	<link rel="stylesheet" type="text/css" href="css/login.css" media="all">
</head>
<body>
<div class="larry-main layui-layout larry-delay2" id="larry_login">
	<div class="title">${sessionScope.SystemTitle}</div>
	<div class="user-info">
		<div class="avatar"><img src="images/photo/admin.png" alt=""></div>
		<form class="layui-form" id="larry_form">
			 <div class="layui-form-item">
			 	  <label class="layui-form-label">用户名:</label>
			 	  <input type="text" name="userAccount" required  lay-verify="userAccount" autocomplete="off" class="layui-input larry-input" placeholder="请输入您的用户名">
			 </div>
			 <div class="layui-form-item" id="password">
			 	  <label class="layui-form-label">密码:</label>
			 	  <input type="password" name="userPassword" required lay-verify="userPassword" autocomplete="off" class="layui-input larry-input" placeholder="请输入您的登录密码">
			 </div>
			 <div class="layui-form-item larry-verfiy-code" id="larry_code">
			 	   <input type="text" name="verifyCode" required lay-verify="verifyCode" autocomplete="off" class="layui-input larry-input" placeholder="输入验证码">
			 	   <div class="code">
			 	   	   <div class="arrow"></div>
			 	   	   <div class="code-img"><img src="verifyCode.jpg" alt="" id="codeimage" disabled="disabled"></div>
			 	   	   <a id="changeCode" class="change" title="看不清,点击更换验证码"><i></i></a>
			 	   </div>
			 </div>
			 <div class="layui-form-item">
			 	 <button class="layui-btn larry-btn" lay-filter="submit" lay-submit>立即登录</button>
			 </div>
		</form>
	</div>
	<div class="copy-right">${sessionScope.SystemCopyright}</div>
</div>
	<!-- 加载js文件-->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="../common/js/gobal.js"></script>
<script type="text/javascript" src="js/login.js"></script>
</body>
</html>