<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>订货审批管理</title>
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
	                <a class="layui-btn layui-btn-small" href="store_stock_orders_audit.html?stockOrderStatus=1"><i class="layui-icon">&#xe65c;</i><cite>返回</cite></a>
	           		<a name="color-design">门店进货单审批</a>
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
           		系统会自动将该进货单中的商品和对应的供应商给匹配出来，如果有存在多个供应商的商品，则需要手工设定。手动设定后，需要点击最右边的【保存】按钮。
           		<br>
                <pre>${sessionScope.CurrentStockOrder.stockOrderMemo}</pre>
            </blockquote>
    	</div>
    	
    	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
   		  <div class="user-tables">
   			<table id="stockOrderGoodsTables" lay-filter="stockOrderGoodsTables"></table>
   		  </div>
   		  <blockquote class="layui-elem-quote" style="text-align:right">
           		<a class="layui-btn layui-btn-warm" id="stockOrderBackBtn" href="#"><i class="larry-icon">&#xe88f;</i> <cite>退回重审</cite></a>
           		<button class="layui-btn layui-btn-normal" id="stockOrderSaveBtn"><i class="larry-icon">&#xe88f;</i> <cite>确认已经分配完成，提交复审</cite></button>
            </blockquote>
    	</div>
    </div>
</div>

<script type="text/html" id="selectProviderTpl">
  <a class="layui-btn layui-btn-mini" lay-event="selectProvider">选择</a>
</script>

<script type="text/html" id="selectStoreTpl">
  <a class="layui-btn layui-btn-mini" lay-event="selectStore">选择</a>
</script>

<script type="text/html" id="storeGoodsCostTextTpl">
	{{d.storeGoodsCostText}}/{{d.measurUnit}}
</script>

<script type="text/html" id="providerUnitTpl">
	{{d.providerUnit}}({{d.providerUnitMultiple}}{{d.measurUnit}})
</script>

<script type="text/html" id="goodsCountTpl">
	<input type="number" id="goodsCount{{d.stockOrderDetailId}}" style="width:100px" value="{{d.goodsCount}}"></input>  
</script>

<script type="text/html" id="goodsCountSaveTpl">
	<a class="layui-btn layui-btn-mini" lay-event="updateGoodsCount">保存修改</a>
</script>

<script type="text/html" id="processTypeTpl">
<div class="layui-input-inline" >
   <div  style="margin-top:-5px;">
{{#  if(d.dealType == 1){ }}
	   <input type="radio" lay-filter="changeDealType" id="dealTypeA{{d.stockOrderDetailId}}" name="dealType{{d.stockOrderDetailId}}" value="1-{{d.stockOrderDetailId}}" title="门店调拨" checked>
{{#  } else { }}
	   <input type="radio" lay-filter="changeDealType" id="dealTypeB{{d.stockOrderDetailId}}" name="dealType{{d.stockOrderDetailId}}" value="1-{{d.stockOrderDetailId}}" title="门店调拨">
{{#  } }}

{{#  if(d.dealType == 0){ }}
       <input type="radio" lay-filter="changeDealType" id="dealTypeA{{d.stockOrderDetailId}}" name="dealType{{d.stockOrderDetailId}}" value="0-{{d.stockOrderDetailId}}" title="供应商采购" checked>
{{#  } else { }}
	   <input type="radio" lay-filter="changeDealType" id="dealTypeB{{d.stockOrderDetailId}}" name="dealType{{d.stockOrderDetailId}}" value="0-{{d.stockOrderDetailId}}" title="供应商采购">
{{#  } }}
	   <input type="hidden" id="dealType{{d.stockOrderDetailId}}" value="{{d.dealType}}">   
</div>
</div>
</script>

<script type="text/html" id="providerTpl">
<div class="layui-show">

{{#  if(d.providerCount != 1){ }}
<a id="selectProvider{{d.stockOrderDetailId}}" class="layui-btn layui-btn-normal layui-btn-mini" lay-event="provider">供应商</a>
{{#  } }}

<input type="hidden" id="providerId{{d.stockOrderDetailId}}" value="{{d.toProviderId}}">   
<span id="providerName{{d.stockOrderDetailId}}">{{d.toProviderName}}</span>
</div>
</script>

<script type="text/html" id="storeTpl">
{{#  if(d.dealType == 1){ }}
<a id="selectStore{{d.stockOrderDetailId}}" class="layui-btn layui-btn-mini" lay-event="store">门店</a>
{{#  } else { }}
<a id="selectStore{{d.stockOrderDetailId}}" class="layui-btn layui-btn-mini layui-btn-disabled" lay-event="store">门店</a>
{{#  } }}

<span id="storeName{{d.stockOrderDetailId}}">{{d.fromStoreName}}</span>
</script>

<script type="text/html" id="goodsCostTpl">
<input type="number" id="goodsCost{{d.stockOrderDetailId}}" style="width:100px" value="{{d.providerCost}}"></input>
</script>

<script type="text/html" id="providerCostTextTpl">
{{d.providerCostText}}/{{d.providerUnit}}
</script>


<script type="text/html" id="operatorTpl">
<a class="layui-btn layui-btn-mini" lay-event="save">保存</a>
</script>

<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/stocks/store_stock_orders_audit.js"></script> 
</body>
</html>