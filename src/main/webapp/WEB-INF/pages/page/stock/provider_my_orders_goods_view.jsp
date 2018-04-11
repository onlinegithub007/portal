<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>我的订单确认</title>
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
    	<div class="layui-col-lg8 layui-col-md10 layui-col-sm12 layui-col-xs12">
    		<fieldset class="layui-elem-field layui-field-title site-title">
                <legend>
                <a class="layui-btn layui-btn-small" href="provider_my_orders.html"><i class="layui-icon">&#xe65c;</i><cite>返回</cite></a>
                <a name="color-design">供应商订单</a>
                </legend>
            </fieldset>
            <blockquote class="layui-elem-quote">
	            <i class="larry-icon" style="font-size:16pt;color:#009688">&#xe740;</i> 
	            进货门店：${sessionScope.ApplyStore.groupName}，联系人：${sessionScope.CurrentStockOrder.linkMan}，联系电话：${sessionScope.CurrentStockOrder.linkManPhone}, 收货地址：${sessionScope.CurrentStockOrder.applyAddress}。<br>
            </blockquote>
    	</div>

    	<div class="layui-col-lg8 layui-col-md10 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="myOrdersGoodsTablesView" lay-filter="myOrdersGoodsTablesView"></table>
    		</div>
    		<blockquote class="layui-elem-quote" style="text-align:right">
           		<button class="layui-btn layui-btn-normal" id="providerAckPrintBtn"><i class="larry-icon">&#xe89a;</i> <cite>打印</cite></button>
            </blockquote>
    	</div>
    </div>
</div>
<script type="text/html" id="goodsbar">
</script>

<script type="text/html" id="providerAckAmountTpl">
{{# if(d.providerAckAmount != d.goodsAmount){ }}
<span style="color:#FF5722">{{d.providerAckAmount}}</span>
{{#  } else { }}
{{d.providerAckAmount}}
{{#  } }}
</script>

<script type="text/html" id="dealTypeTpl">
{{# if(d.dealType == 0){ }}
	采购单
{{#  } else { }}
	门店调拨单
{{#  } }}
</script>

<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/stocks/provider_my_orders.js"></script> 
</body>
</html>