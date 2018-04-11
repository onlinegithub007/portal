<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>总部门店库存调拨管理</title>
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
                <a class="layui-btn layui-btn-small" href="master_stock_dispatch3.html"><i class="layui-icon">&#xe65c;</i><cite>返回</cite></a>
                <a name="color-design">门店库存调拨 -> 查看明细</a>
                </legend>
            </fieldset>
            <blockquote class="layui-elem-quote">
	            <i class="larry-icon" style="font-size:16pt;color:#009688">&#xe835;</i> 
	            调出门店：${sessionScope.CurrentPurchaseOrders.fromStoreName}，联系人：${sessionScope.FromStoreMaster.userName}，联系电话：${sessionScope.FromStoreMaster.userPhone}, 取货地址：${sessionScope.FromStore.detailAddress}。<br>
            </blockquote>
            <blockquote class="layui-elem-quote">
            	<i class="larry-icon" style="font-size:16pt;color:#009688">&#xe89d;</i> 
	            调入门店：${sessionScope.ToStore.groupName}，联系人：${sessionScope.ToStoreMaster.userName}，联系电话：${sessionScope.ToStoreMaster.userPhone}, 取货地址：${sessionScope.ToStore.detailAddress}。
            </blockquote>
            <c:if test="${sessionScope.CurrentPurchaseOrders.orderStatus == 0}">
            <blockquote class="layui-elem-quote">
            	<i class="larry-icon" style="font-size:16pt;color:#009688">&#xe885;</i> 
	            供应商：${sessionScope.CurrentPurchaseOrders.providerName}，联系人：${sessionScope.CurrentPurchaseOrders.providerLinkman}，联系电话：${sessionScope.CurrentPurchaseOrders.providerLinkmanPhone}。
            </blockquote>
            </c:if>
    	</div>

    	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="stockDispatchTables" lay-filter="stockDispatchTables"></table>
    		</div>
    		<div style="text-align:right">
    			<a class="layui-btn layui-btn-normal" id="prntBtn" href="#"><i class="larry-icon">&#xe89a;</i> <cite>打印</cite></a>
				<c:if test="${sessionScope.CurrentPurchaseOrders.orderStatus == 1}">
		       		<a class="layui-btn" id="ackOutDispachBtn" href="#"><i class="larry-icon">&#xe88f;</i> <cite>我已核对出库商品数量，确认出库</cite></a>
				</c:if>
	        </div>
    	</div>
    	
    </div>
</div>
<script type="text/html" id="groupsbar">
  <a class="layui-btn layui-btn-mini" lay-event="view">查看明细</a>
</script>

<script type="text/html" id="currentStockCountTpl">
  {{d.currentStockCount}}{{d.measurUnit}}
</script>

<script type="text/html" id="dispatchStockCountTpl">
  {{d.dispatchStockCount}}{{d.measurUnit}}
</script>

<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/stocks/master_stock_dispatch2.js"></script> 
</body>
</html>