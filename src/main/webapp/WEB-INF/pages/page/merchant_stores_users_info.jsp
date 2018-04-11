<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>店员信息</title>
	<meta name="keywords" content="" />
	<meta name="Author" content="larry" />
	<meta name="renderer" content="webkit">	
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">	
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">	
	<meta name="apple-mobile-web-app-capable" content="yes">	
	<meta name="format-detection" content="telephone=no">	
	<!-- load css -->
	<link rel="stylesheet" type="text/css" href="../common/frame/layui/css/layui.css" media="all">
    <link rel="stylesheet" type="text/css" href="../common/css/gobal.css" media="all">
    <link rel="stylesheet" type="text/css" href="../common/css/animate.css" media="all">
    <link rel="stylesheet" type="text/css" href="css/common.css" media="all">
    <link rel="stylesheet" type="text/css" href="css/mypanel.css" media="all">
</head>
</head>
<body>
<div class="larry-fluid larry-wrapper">
    <div class="layui-row lay-col-space20">
    	<div class="layui-cos-xs12 layui-col-sm12 layui-col-md12 layui-col-lg12">
    		<section class="larry-body">
    			<div class="larry-body-header">
    				<span class="tit">新增店员</span>
    			</div>
    			<div class="larry-body-content clearfix">
    				<form class="layui-form" action="" method="post">
				        <div class="layui-form-item">
					        <label class="layui-form-label">用户账号</label>
					        <div class="layui-input-block">  
						        <input type="hidden" name="merUserId" value="">
						        <input type="text" name="userAccount" required lay-verify="userAccount" autocomplete="off"  class="layui-input" value="" placeholder="输入用户账号">
					        </div>
				        </div>
				        <div class="layui-form-item">
				        	<label class="layui-form-label">真实姓名</label>
				        	<div class="layui-input-block">
				        		<input type="text" name="userName" required lay-verify="userName" autocomplete="off" class="layui-input" value="" placeholder="输入真实姓名">
				        	</div>
				        </div>
				        <div class="layui-form-item">
				        	<label class="layui-form-label">手机号码</label>
				        	<div class="layui-input-block">
				        		<input type="text" name="userPhone" required lay-verify="userPhone"  autocomplete="off" class="layui-input" value="" placeholder="输入手机号">
				        	</div>
				        </div>
				
				<div class="layui-form-item">
					<div class="layui-input-block">
						<button class="layui-btn" id="merStoreUserSubmit" lay-submit lay-filter="merStoreUserSubmit">立即提交</button>
						<a class="layui-btn layui-btn-primary" href="merchant_stores_users.html?merGroupId=${sessionScope.CurrentStore.merGroupId}">取消</a>
					</div>
				</div>
			</form>
    			</div>
    		</section>
    	</div>
    </div>
</div>
<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
var storeGroupId = '${sessionScope.CurrentStore.merGroupId}';
</script>
<script type="text/javascript" src="js/stores_users.js"></script>
</body>
</html>