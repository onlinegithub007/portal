<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>供应商商品管理</title>
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
                <legend><a name="color-design">供应商商品管理 -> ${sessionScope.CurrentGpsUser.merName} -> ${sessionScope.CurrentGpsUser.userName}</a></legend>
            </fieldset>
            <blockquote class="layui-elem-quote">在[ <i class="larry-icon">&#xe8e8;</i> 商品信息管理 ]中导出指定分类的商品清单后，用EXCEL打开,只保留该供应商的商品，在最后一列【采购价】内录入采购价格，保存之后再导入进来。切记只需要删掉不需要的商品数据行，不要更改表格结构，EXCEL保存为xls扩展名。<br>
            重复导入的商品会被覆盖。
            </blockquote>
            <form class="layui-form " action="">
            	<div class="layui-form-item layui-input-inline">
	                <a class="layui-btn" href="merchant_providers.html"><i class="layui-icon">&#xe65c;</i><cite>返回</cite></a>
            	</div>
            	<div class="layui-form-item layui-input-inline">
	                <a id="uploadGoodsTemp" class="layui-btn" href="#"><i class="layui-icon">&#xe61f;</i><cite>导入供应商供货清单</cite></a>
            	</div>
            </form>
            
    	</div>

    	<div class="layui-col-lg10 layui-col-md10 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="goodsTables" lay-filter="goodsTables"></table>
    		</div>
    	</div>
    </div>
</div>
<script type="text/html" id="goodsbar">
  <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>

{{#  if(d.gpStatus == 0){ }}
  <a class="layui-btn layui-btn-warm layui-btn-mini" lay-event="disable">暂停供货</a>
{{#  } else { }}
  <a class="layui-btn layui-btn-mini" lay-event="enable">恢复供货</a>
{{#  } }}
</script>

<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/merchant_provider_goods.js"></script> 
</body>
</html>