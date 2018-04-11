<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>门店出库确认单</title>
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
    	<div class="layui-col-lg10 layui-col-md10 layui-col-sm10 layui-col-xs10">
    		<fieldset class="layui-elem-field layui-field-title site-title">
                <legend>
                	<a class="layui-btn layui-btn-small" href="store_goods_stock_out.html"><i class="layui-icon">&#xe65c;</i><cite>返回</cite></a>
                	<a name="color-design">门店库存调拨出库确认单 -> 调拨商品明细</a>
                </legend>
            </fieldset>
            <blockquote class="layui-elem-quote">
	            <i class="larry-icon" style="font-size:16pt;color:#009688">&#xe740;</i> 
	            收货信息：联系人：${sessionScope.CurrentPurchaseOrdersForOut.linkMan}，${sessionScope.CurrentPurchaseOrdersForOut.linkManPhone}<br>
	            ${sessionScope.CurrentPurchaseOrdersForOut.applyAddress}<br>
	            订单号：${sessionScope.CurrentPurchaseOrdersForOut.orderSn}，
	            下单时间：${sessionScope.CurrentPurchaseOrdersForOut.createDateText}<br>
	            下面是库存调拨申请单中的商品清单，请按界面提示操作完成库存调拨。
	        </blockquote>
    	</div>

    	<div class="layui-col-lg10 layui-col-md10 layui-col-sm10 layui-col-xs12">
    		<div class="user-tables">
    			<table id="goodsStockOutGoodsTables" lay-filter="goodsStockOutGoodsTables"></table>
    		</div>
    		
    		<blockquote class="layui-elem-quote" style="text-align:right">
    			<a class="layui-btn layui-btn-warm" id="printBtn" href="#"><i class="larry-icon">&#xe89a;</i> <cite>打印</cite></a>
           		<a class="layui-btn" id="stockOutOrderAckBtn" href="#"><i class="larry-icon">&#xe88f;</i> <cite>确认当前商品库存调拨</cite></a>
            </blockquote>
    	</div>
    </div>
</div>

<script type="text/html" id="viewTpl">
   <a class="layui-btn layui-btn-mini" lay-event="view">调拨商品</a>
</script>

<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/store_goods_stock_out.js"></script> 
</body>
</html>