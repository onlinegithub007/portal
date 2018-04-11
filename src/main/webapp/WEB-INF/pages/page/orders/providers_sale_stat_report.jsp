<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>供应商销售报表</title>
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
                <legend>
                <a class="layui-btn layui-btn-small" href="providers_sale_stat_select.html"><i class="layui-icon">&#xe65c;</i><cite>返回</cite></a>
                <a name="color-design">供应商销售报表</a>
                </legend>
            </fieldset>
    	</div>
		<form class="layui-form">
          	<div class="layui-form-item layui-input-inline">
           		<input type="text" id="createDateText" name="createDateText" autocomplete="off"  class="layui-input" placeholder="请选择开始交易日期">
           	</div>
           	<div class="layui-form-item layui-input-inline">
           		<input type="text" id="endDateText" name="endDateText" autocomplete="off"  class="layui-input" placeholder="请选择结束交易日期">
           	</div>
           	<div class="layui-form-item layui-input-inline">
           		<a class="layui-btn" lay-submit lay-filter="storeOrdersQuerySubmit"><i class="larry-icon">&#xe896;</i><cite>查询</cite></a>
           	</div>
        </form>
    	<div class="layui-col-lg10 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="userTables" lay-filter="userTables"></table>
    		</div>
    	</div>
    </div>
</div>

<script type="text/html" id="userbar">
  <a class="layui-btn layui-btn-mini" lay-event="sell">销售报表</a>
</script>

<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/orders/providers_sale_stat_report.js"></script> 
</body>
</html>