<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>店员管理</title>
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
                <legend><a name="color-design"><i class="icon larry-icon" style="font-size:14pt">&#xe96f;</i>[${sessionScope.CurrentStore.groupName}] 店员管理</a></legend>
            </fieldset>
             <form class="layui-form " action="">
             	<div class="layui-form-item layui-input-inline">
             		<a class="layui-btn"  href="merchant_stores_users_info.html"><i class="layui-icon">&#xe61f;</i><cite>增加店员</cite></a>
             		<a class="layui-btn"  href="merchant_stores.html"><i class="icon larry-icon">&#xe604;</i><cite>返回</cite></a>
             	</div>
             </form>
    	</div>

    	<div class="layui-col-lg9 layui-col-md11 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="userTables" lay-filter="userTables"></table>
    		</div>
    	</div>
    </div>
</div>
<script type="text/html" id="userbar">
  <a class="layui-btn layui-btn-mini" lay-event="edit">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>

  {{#  if(d.status == 0){ }}
  <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="disable">禁用</a>
  {{#  } else { }}
  <a class="layui-btn layui-btn-mini" lay-event="enable">激活</a>
  {{#  } }}
</script>

<script type="text/html" id="titleTpl">
	{{#  if(d.status == 0){ }}
    <i class="icon larry-icon" style="font-size:14pt;color:#5FB878">&#xe990;</i><span style="color:#5FB878">已激活</span>
	{{#  } else { }}
	<i class="icon larry-icon" style="font-size:14pt;color:#FF5722">&#xe7e4;</i><span style="color:#FF5722">已禁用</span>
	{{#  } }}
</script>
<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript">
var storeGroupId = '${sessionScope.CurrentStore.merGroupId}';
</script>
<script type="text/javascript" src="js/stores_users.js"></script> 
</body>
</html>