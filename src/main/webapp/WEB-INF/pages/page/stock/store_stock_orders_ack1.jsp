<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>门店收货确认</title>
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
    		<fieldset class="layui-elem-field">
                <legend>
                	<a class="layui-btn layui-btn-small" href="store_stock_orders_ack.html"><i class="layui-icon">&#xe65c;</i><cite>返回</cite></a>
                	<a name="color-design">进货单收货确认</a>
                </legend>
                <div class="layui-field-box">
				    <ol class="ui-step ui-step-blue ui-step-6">
						<li class="step-start ${sessionScope.CurrentStockOrder.getStepByStatus(1)}">
							<div class="ui-step-line"></div>
							<div class="ui-step-cont">
								<span class="ui-step-cont-number">1</span>
								<span class="ui-step-cont-text">新建进货单</span>
							</div>
						</li>
						<li class="${sessionScope.CurrentStockOrder.getStepByStatus(2)}">
							<div class="ui-step-line"></div>
							<div class="ui-step-cont">
								<span class="ui-step-cont-number">2</span>
								<span class="ui-step-cont-text">分配供应商(一审)</span>
							</div>
						</li>
						<li class="${sessionScope.CurrentStockOrder.getStepByStatus(3)}">
							<div class="ui-step-line"></div>
							<div class="ui-step-cont">
								<span class="ui-step-cont-number">3</span>
								<span class="ui-step-cont-text">运营主管确认(二审)</span>
							</div>
						</li>
						<li class="${sessionScope.CurrentStockOrder.getStepByStatus(4)}">
							<div class="ui-step-line"></div>
							<div class="ui-step-cont">
								<span class="ui-step-cont-number">4</span>
								<span class="ui-step-cont-text">下采购单</span>
							</div>
						</li>
						<li class="${sessionScope.CurrentStockOrder.getStepByStatus(5)}">
							<div class="ui-step-line"></div>
							<div class="ui-step-cont">
								<span class="ui-step-cont-number">5</span>
								<span class="ui-step-cont-text">门店收货确认</span>
							</div>
						</li>
						<li class="step-end ${sessionScope.CurrentStockOrder.getStepByStatus(6)}">
							<div class="ui-step-line"></div>
							<div class="ui-step-cont">
								<span class="ui-step-cont-number">6</span>
								<span class="ui-step-cont-text">完成</span>
							</div>
						</li>
					</ol>
				</div>
            </fieldset>
            <blockquote class="layui-elem-quote">
	            <i class="larry-icon" style="font-size:16pt;color:#009688">&#xe740;</i> 
	            进货门店：${sessionScope.ApplyStore.groupName}，联系人：${sessionScope.CurrentStockOrder.linkMan}，联系电话：${sessionScope.CurrentStockOrder.linkManPhone}, 收货地址：${sessionScope.CurrentStockOrder.applyAddress}。<br>
	            <hr>
                <pre>${sessionScope.CurrentStockOrder.stockOrderMemo}</pre>
            </blockquote>
    	</div>

    	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<fieldset class="layui-elem-field layui-field-title site-title">
                <legend><a name="color-design">下面是进货单对应的收货确认单，请仔细核对后再确认收货</a></legend>
            </fieldset>
            
            <c:if test="${sessionScope.CurrentStockOrder.providerCount > 0}">
	            <blockquote class="layui-elem-quote">供应商收货确认</blockquote>
	    		<div class="user-tables">
	    			<table id="deliverAckTables1" lay-filter="deliverAckTables1"></table>
	    		</div>
    		</c:if>
    		
    		<c:if test="${sessionScope.CurrentStockOrder.storeCount > 0}">
	    		<blockquote class="layui-elem-quote">从其他门店调拨收货确认</blockquote>
	    		<div class="user-tables">
	    			<table id="deliverAckTables2" lay-filter="deliverAckTables2"></table>
	    		</div>
    		</c:if>
    		
    		<c:if test="${sessionScope.CurrentStockOrder.stockOrderStatus == 4}">
	    		<blockquote class="layui-elem-quote" style="text-align:right">
	           		<a class="layui-btn" id="stockOrderAckBtn" href="#"><i class="larry-icon">&#xe88f;</i> <cite>标记当前进货单全部收货确认</cite></a>
	            </blockquote>
            </c:if>
    	</div>
    </div>
</div>
<script type="text/html" id="goodsbar">
<a class="layui-btn layui-btn-normal layui-btn-mini" lay-event="ack_detail">商品明细验货</a>
</script>

<script type="text/html" id="orderStatusTpl">
{{# if(d.orderStatus == 2){ }}
	已验货
{{#  } else { }}
	未验货
{{#  } }}
</script>

<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/stocks/store_stock_orders_ack.js"></script> 
</body>
</html>