<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>采购单预览</title>
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
    <link type="text/css" href="../css/iF.step.css" rel="stylesheet">
</head>
</head>
<body>
<div class="layui-fluid larry-wrapper">
    <div class="layui-row">
    	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
           	<fieldset class="layui-elem-field layui-field-title site-title">
                <legend>
	           		<a name="color-design">采购单预览</a>
	           	</legend>
	        </fieldset>
	        <blockquote class="layui-elem-quote">
	        	<i class="larry-icon" style="font-size:16pt;color:#009688">&#xe740;</i> 
	        	采购单编号：${sessionScope.CurrentPurchaseOrders.orderSn}，门店进货单编号：${sessionScope.CurrentPurchaseOrders.stockOrderId}<br>
	        	商品总件数：${sessionScope.CurrentPurchaseOrders.goodsAmount}，采购总金额：${sessionScope.CurrentPurchaseOrders.goodsTotalPriceText}元
	        </blockquote>
            <blockquote class="layui-elem-quote">
	            <i class="larry-icon" style="font-size:16pt;color:#009688">&#xe740;</i> 
	            收货信息：联系人：${sessionScope.CurrentPurchaseOrders.linkMan}，${sessionScope.CurrentPurchaseOrders.linkManPhone}<br>
	            ${sessionScope.CurrentPurchaseOrders.applyAddress}<br>
	            <c:if test="${sessionScope.CurrentPurchaseOrders.orderStatus == 1}">订单号：${sessionScope.CurrentPurchaseOrders.orderSn}，</c:if>
	            下单时间：${sessionScope.CurrentPurchaseOrders.createDateText}
            </blockquote>
            <blockquote class="layui-elem-quote">
	            <i class="larry-icon" style="font-size:16pt;color:#009688">&#xe740;</i> 
	            供应商：${sessionScope.CurrentPurchaseOrders.providerName}<br>
	            联系人：${sessionScope.CurrentPurchaseOrders.providerLinkman}，${sessionScope.CurrentPurchaseOrders.providerLinkmanPhone}，${sessionScope.CurrentPurchaseOrders.providerLinkmanEmail}
            </blockquote>
    	</div>
    	
    	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
   		  <div class="user-tables">
   			<table id="purchaseOrderGoodsTables" lay-filter="purchaseOrderGoodsTables"></table>
   		  </div>
		  
   		  <blockquote class="layui-elem-quote" style="text-align:right">
           		<a class="layui-btn layui-btn-warm" id="printBtn" href="#"><i class="larry-icon">&#xe89a;</i> <cite>打印</cite></a>
            </blockquote>
    	</div>
    </div>
</div>

<script type="text/html" id="viewGoodsTpl">
  <a class="layui-btn layui-btn-mini" lay-event="viewOrder">订单预览</a>
</script>

<script type="text/html" id="selectStoreTpl">
  <a class="layui-btn layui-btn-mini" lay-event="selectStore">选择</a>
</script>

<script type="text/html" id="goodsCostTextTpl">
  {{d.goodsCostText}}/{{d.providerUnit}}
</script>

<script type="text/html" id="processTypeTpl">
<div class="layui-input-inline" >
   <div  style="margin-top:-5px;">
{{#  if(d.dealType == 1){ }}
	门店调拨
{{#  } else { }}
	供应商采购
{{#  } }}
	</div>
</div>
</script>

<script type="text/javascript">
var purchaseOrderId = '${sessionScope.CurrentPurchaseOrders.purchaseOrderId}';
</script>

<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/stocks/store_purchase_order_view.js"></script> 
</body>
</html>