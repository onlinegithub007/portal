<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>会员信息</title>
	<meta name="keywords" content="" />
	<meta name="renderer" content="webkit">	
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">	
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">	
	<meta name="apple-mobile-web-app-capable" content="yes">	
	<meta name="format-detection" content="telephone=no">	
	<link rel="Shortcut Icon" href="/favicon.ico" />
	<!-- load css -->
	<link rel="stylesheet" type="text/css" href="../common/frame/layui/css/layui.css" media="all">
    <link rel="stylesheet" type="text/css" href="../common/css/gobal.css" media="all">
    <link rel="stylesheet" type="text/css" href="../common/css/animate.css" media="all">
    <link rel="stylesheet" type="text/css" href="css/common.css" media="all">
    <link rel="stylesheet" type="text/css" href="css/mypanel.css" media="all">
</head>
</head>
<body>
<div class="layui-fluid larry-wrapper">
    <div class="layui-row lay-col-space20">
    	<div class="layui-cos-xs12 layui-col-sm12 layui-col-md12 layui-col-lg12">
    		<section class="larry-body">
                <div class="larry-body-header">
    				<span class="tit">会员信息</span>
    			</div>
    			<div class="larry-body-content clearfix changepwd">
                    <form id="goodsInfoForm" class="layui-form layui-col-lg8 layui-col-md12 layui-col-sm12 layui-col-xs12 " method="post" action="">
			 	        
			 	        <div class="layui-form-item">
				        	<label class="layui-form-label">会员级别</label>
				        	<div class="layui-input-block">
					        	<div class="layui-input-inline">
					        		<select name="clientLevelId">
										<option value="">请选择会员级别</option>
										<c:forEach var="clientLevel" items="${CurrMemberClientLevel}">
											<option value="${clientLevel.clientLevelId}" ${clientLevel.selected}>${clientLevel.levelName}</option>
										</c:forEach>
									</select>
					        	</div>
				        	</div>
				        </div>
			 	        
			 	        <div class="layui-form-item">
				        	<label class="layui-form-label">会员手机号</label>
				        	<div class="layui-input-block">
				        	  	<input type="hidden" name="memberId" value="${sessionScope.CurrentMember.memberId}">
				        	  	<c:if test="${sessionScope.CurrentMember.memberId == null}">
				        	  		<input type="text" id="memberCode" name="memberCode" required lay-verify="memberCode"  autocomplete="off"  class="layui-input" value="" placeholder="请输入会员手机号码">
				        	  	</c:if>
				        	  	<c:if test="${sessionScope.CurrentMember.memberId != null}">
				        	  		<input type="text" id="memberCode" name="memberCode" required lay-verify="memberCode" readonly="readonly" autocomplete="off"  class="layui-input" value="${sessionScope.CurrentMember.memberCode}" placeholder="请输入会员手机号码">
				        	  	</c:if>
				        	</div>
				        </div>
				        <div class="layui-form-item">
				        	<label class="layui-form-label">会员姓名</label>
				        	<div class="layui-input-block">  
				        	  	<input type="text" id="memberName" name="memberName" required lay-verify="memberName"  autocomplete="off"  class="layui-input" value="${sessionScope.CurrentMember.memberName}" placeholder="请输入会员姓名">
				        	</div>
				        </div>
				        
				        <div class="layui-form-item change-submit">
				        	<div class="layui-input-block">
				        		<button class="layui-btn larry-tj" id="memberInfoSubmit" lay-submit lay-filter="memberInfoSubmit">立即提交</button>
				        		<a class="layui-btn layui-btn-primary" href="members.html">取消</a>
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
<script type="text/javascript" src="js/members.js"></script> 
</body>
</html>