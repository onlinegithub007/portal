<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>供应商用户</title>
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
    				<span class="tit">供应商信息</span>
    			</div>
    			<div class="larry-body-content clearfix">
    				<form class="layui-form" action="" method="post">
				        <div class="layui-form-item">
				        	<label class="layui-form-label">供应商联系人</label>
				        	<div class="layui-input-block">
				        		<input type="hidden" name="merUserId" value="${sessionScope.CurrentGpsUser.merUserId}">
				        		<input type="text" name="userName" required lay-verify="userName" autocomplete="off" class="layui-input" value="${sessionScope.CurrentGpsUser.userName}" placeholder="输入供应商联系人姓名">
				        	</div>
				        </div>
				        
				        <!-- 
				        <div class="layui-form-item">
				        	<label class="layui-form-label">系统登录账号</label>
				        	<div class="layui-input-block">
				        		<input type="text" name="userAccount" required lay-verify="userAccount"  autocomplete="off" class="layui-input" value="${sessionScope.CurrentGpsUser.userAccount}" placeholder="输入登录账号">
				        	</div>
				        </div>
				         -->
				        
				        <div class="layui-form-item">
				        	<label class="layui-form-label">供应商单位</label>
				        	<div class="layui-input-block">
				        		<input type="text" name="merName" required lay-verify="merName"   autocomplete="off" class="layui-input" value="${sessionScope.CurrentGpsUser.merName}" placeholder="输入供应商单位名称">
				        	</div>
				        </div>
				        
				        <div class="layui-form-item">
				        	<label class="layui-form-label">手机号码</label>
				        	<div class="layui-input-block">
				        		<input type="text" name="userPhone" required lay-verify="mobilePhone"  autocomplete="off" class="layui-input" value="${sessionScope.CurrentGpsUser.userPhone}" placeholder="输入手机号">
				        	</div>
				        </div>
				        
				        <div class="layui-form-item">
				        	<label class="layui-form-label">邮箱账号</label>
				        	<div class="layui-input-block">
				        		<input type="text" name="userEmail" required lay-verify="userEmail"  autocomplete="off" class="layui-input" value="${sessionScope.CurrentGpsUser.userEmail}" placeholder="输入邮箱账号">
				        	</div>
				        </div>

						<div class="layui-form-item">
				        	<label class="layui-form-label">备注</label>
				        	<div class="layui-input-block">
				        		<input type="text" name="userMemo"  autocomplete="off" class="layui-input" value="${sessionScope.CurrentGpsUser.userMemo}">
				        	</div>
				        </div>
				
				<div class="layui-form-item">
					<div class="layui-input-block">
						<button class="layui-btn" id="merGpsSubmitBtn" lay-submit lay-filter="merGpsSubmit">立即提交</button>
						<a class="layui-btn layui-btn-primary" href="merchant_providers.html">取消</a>
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
<script type="text/javascript" src="js/merchant_providers.js"></script>
</body>
</html>