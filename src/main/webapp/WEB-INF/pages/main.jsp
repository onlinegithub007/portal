<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>首页</title>
	<meta name="keywords" content="" />
	<meta name="Author" content="larry" />
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
	<link rel="stylesheet" type="text/css" href="css/common.css" media="all">
	<link rel="stylesheet" type="text/css" href="css/main.css" media="all">
</head>
</head>
<body>
<div class="layui-fluid larry-wrapper">
	<div class="layui-row layui-col-space20" id="infoSwitch">
		    <div class="layui-col-xs12  layui-col-sm12  layui-col-md12  layui-col-lg12">
		    	<blockquote class="layui-elem-quote  head-con">
                <div><i class="larry-icon" style="font-size:14pt">&#xe68e;</i><b>${sessionScope.CurrentMerUser.merName}</b>  欢迎您，${sessionScope.CurrentMerUser.userName}</div>
	        </blockquote>
	    </div>	
	</div>
	

	<div class="layui-row layui-col-space20">
        
	</div>

</div>
<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/main.js"></script> 
<!--[if lt IE 9]>
  <script src="/common/js/html5.min.js"></script>
  <script src="/common/js/respond.min.js"></script>
<![endif]-->
</body>
</html>