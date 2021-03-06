<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>会员信息管理</title>
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
    <link rel="stylesheet" type="text/css" href="css/user.css" media="all">
</head>
</head>
<body>
<div class="layui-fluid larry-wrapper">
    <div class="layui-row">
    	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<fieldset class="layui-elem-field layui-field-title site-title">
                <legend><a name="color-design">会员信息管理</a></legend>
            </fieldset>
             <form class="layui-form " action="">
             	<div class="layui-form-item layui-input-inline">
             		<a class="layui-btn"  href="member_info.html"><i class="layui-icon">&#xe61f;</i><cite>新建会员</cite></a>
             	</div>
             	<div class="layui-form-item layui-input-inline">
					<select name="interest" id="groupId" lay-filter="selectMemberLevel">
						<option value="">请选择会员级别</option>
						<c:forEach var="clientLevel" items="${MemberClientLevel}">
							<option value="${clientLevel.clientLevelId}">${clientLevel.levelName}</option>
						</c:forEach>
					</select>
				</div>
				<div class="layui-form-item layui-input-inline">
             		<input type="text" id="memberCode" name="memberCode"  autocomplete="off"  class="layui-input" value="">
             	</div>
             	<div class="layui-form-item layui-input-inline">
             		<button class="layui-btn larry-tj" lay-submit lay-filter="memberQuerySubmit">查询</button>
             	</div>
             </form>
    	</div>

    	<div class="layui-col-lg10 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="membersTables" lay-filter="membersTables"></table>
    		</div>
    	</div>
    </div>
</div>

<script type="text/html" id="userbar">
  <a class="layui-btn layui-btn-mini" lay-event="edit">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
</script>

<script type="text/html" id="sexTpl">
	{{#  if(d.gender == 0){ }}
    女
	{{#  } else if(d.gender == 1){ }}
	男
	{{#  } }}
</script>
<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/members.js"></script> 
</body>
</html>