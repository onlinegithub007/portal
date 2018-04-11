<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>个人信息</title>
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
    				<span class="tit">修改用户信息</span>
    			</div>
    			<div class="larry-body-content clearfix">
    				<form class="layui-form" action="" method="post">
				        <div class="layui-form-item">
					        <label class="layui-form-label">用户账号</label>
					        <div class="layui-input-block">  
						        <input type="hidden" name="merUserId" value="${sessionScope.CurrMerUser.merUserId}">
						          <input type="text" name="userAccount" required lay-verify="account" autocomplete="off" readonly="readonly" class="layui-input" value="${sessionScope.CurrMerUser.userAccount}" placeholder="输入用户账号">
					        </div>
				        </div>
				        <div class="layui-form-item">
				        	<label class="layui-form-label">所属部门</label>
				        	<div class="layui-input-block">
				        		<select name="merGroupId" id="groupId">
									<option value="">请选择部门</option>
									<c:forEach var="group" items="${MerchantsGroups}">
										<option value="${group.merGroupId}" ${group.selected}>${group.groupName}</option>
									</c:forEach>
								</select>
				        	</div>
				        </div>
				        <div class="layui-form-item">
				        	<label class="layui-form-label">真实姓名</label>
				        	<div class="layui-input-block">
				        		<input type="text" name="userName" required lay-verify="userName" autocomplete="off" class="layui-input" value="${sessionScope.CurrMerUser.userName}" placeholder="输入真实姓名">
				        	</div>
				        </div>
				        <div class="layui-form-item">
				        	<label class="layui-form-label">手机号码</label>
				        	<div class="layui-input-block">
				        		<input type="text" name="userPhone" required lay-verify="mobilePhone"  autocomplete="off" class="layui-input" value="${sessionScope.CurrMerUser.userPhone}" placeholder="输入手机号">
				        	</div>
				        </div>
				        
				        <div class="layui-form-item">
				        	<label class="layui-form-label">邮箱账号</label>
				        	<div class="layui-input-block">
				        		<input type="text" name="userEmail"  autocomplete="off" class="layui-input" value="${sessionScope.CurrMerUser.userEmail}">
				        	</div>
				        </div>
				        
				        <div class="layui-form-item">
				        	<label class="layui-form-label">用户角色</label>
				        	<div class="layui-input-block">
				        		<select name="userRole">
									<c:forEach var="userRole" items="${MerchantUserRole}">
										<option value="${userRole.merUserRoleId}" ${userRole.selected}>${userRole.userRoleName}</option>
									</c:forEach>
								</select>
				        	</div>
				        </div>

						<div class="layui-form-item">
				        	<label class="layui-form-label">备注</label>
				        	<div class="layui-input-block">
				        		<input type="text" name="userMemo"  autocomplete="off" class="layui-input" value="${sessionScope.CurrMerUser.userMemo}">
				        	</div>
				        </div>
				
				<div class="layui-form-item">
					<div class="layui-input-block">
						<button class="layui-btn" lay-submit lay-filter="merUserSubmit">立即提交</button>
						<a class="layui-btn layui-btn-primary" href="merchant_users.html">取消</a>
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
<script type="text/javascript" src="js/user.js"></script>
</body>
</html>