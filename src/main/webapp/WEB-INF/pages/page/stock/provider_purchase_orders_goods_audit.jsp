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
    <link type="text/css" href="../css/iF.step.css" rel="stylesheet">
</head>
</head>
<body>
<div class="layui-fluid larry-wrapper">
    <div class="layui-row">
    	<div class="layui-col-lg10 layui-col-md10 layui-col-sm12 layui-col-xs12">
    		<fieldset class="layui-elem-field ">
                <legend>
                <a class="layui-btn layui-btn-small" href="provider_purchase_orders_audit.html"><i class="layui-icon">&#xe65c;</i><cite>返回</cite></a>
                <a name="color-design">供应商订单复审</a>
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
            	<pre>${sessionScope.CurrentStockOrder.stockOrderMemo}</pre>
            </blockquote>
            <blockquote class="layui-elem-quote">
            	<i class="larry-icon" style="font-size:16pt;color:#009688">&#xe740;</i> 
	            进货门店：${sessionScope.ApplyStore.groupName}，联系人：${sessionScope.CurrentStockOrder.linkMan}，联系电话：${sessionScope.CurrentStockOrder.linkManPhone}, 收货地址：${sessionScope.CurrentStockOrder.applyAddress}。<br>
	            <hr>
	            <i class="larry-icon" style="font-size:16pt;color:#009688">&#xe740;</i> 供货商：${sessionScope.CurrentPurchaseOrders.providerName}，联系人：${sessionScope.CurrentPurchaseOrders.providerLinkman}，联系电话：${sessionScope.CurrentPurchaseOrders.providerLinkmanPhone}
	            <hr>当前采购订单中，计划进货数量和供应商确认的金额不一致，已用红色标出，需要作出审核和相应安排。
            </blockquote>
    	</div>

    	<div class="layui-col-lg10 layui-col-md10 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="myOrdersGoodsTables" lay-filter="myOrdersGoodsTables"></table>
    		</div>
    		<c:if test="${sessionScope.CurrentPurchaseOrders.providerAckAudit == 0}">
    		<blockquote class="layui-elem-quote" style="text-align:right">
           		<button class="layui-btn layui-btn-normal" id="providerAckAuditSaveBtn"><i class="larry-icon">&#xe88f;</i> <cite>对以上供应商采购数量已经确认</cite></button>
            </blockquote>
            </c:if>
    	</div>
    </div>
</div>
<script type="text/html" id="goodsbar">
<a class="layui-btn layui-btn-mini" lay-event="providerAck">修改数量</a>
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
<script type="text/javascript" src="js/stocks/provider_orders_audit.js"></script> 
</body>
</html>