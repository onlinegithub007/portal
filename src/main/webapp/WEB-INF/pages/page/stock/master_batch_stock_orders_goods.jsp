<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>总部进货单管理</title>
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
                <a class="layui-btn layui-btn-small" href="master_batch_stock_ordes.html"><i class="layui-icon">&#xe65c;</i><cite>返回</cite></a>
                <a name="color-design">总部批量派发采购单->设定待采购的商品</a>
                </legend>
            </fieldset>
            <c:if test="${sessionScope.CurrBatchStockOrders.batchOrderStatus == 0}">
            <form class="layui-form " action="">
            	<div class="layui-form-item layui-input-inline">
	                <button class="layui-btn" lay-submit lay-filter="stockOrderGoodsSubmit"><i class="larry-icon">&#xe847;</i><cite>添加待采购商品</cite></button>
            	</div>
            </form>
            </c:if>
    	</div>

    	<div class="layui-col-lg8 layui-col-md10 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="groupsTables" lay-filter="groupsTables"></table>
    		</div>
    		<c:if test="${sessionScope.CurrBatchStockOrders.batchOrderStatus == 0}">
    		<blockquote class="layui-elem-quote" style="text-align:right">
	       		<button class="layui-btn " id="stockOrderCountBtn" href="#"><i class="larry-icon">&#xe88f;</i> <cite>保存数量</cite></button>
	        </blockquote>
	        </c:if>
    	</div>
    	
    </div>
</div>

<script type="text/html" id="groupsbar">
<c:if test="${sessionScope.CurrBatchStockOrders.batchOrderStatus == 0}">
<a class="layui-btn layui-btn-mini layui-btn-danger" lay-event="delete">删除</a>
</c:if>
</script>

<script type="text/html" id="goodsCountTpl">
<c:if test="${sessionScope.CurrBatchStockOrders.batchOrderStatus == 0}">
<input type="number" id="batchStockGoodsId{{d.batchStockGoodsId}}" style="width:100px" value="{{d.goodsCount}}"></input> 
 </c:if>
<c:if test="${sessionScope.CurrBatchStockOrders.batchOrderStatus == 1}">
{{d.goodsCount}}
</c:if>
</script>

<script type="text/html" id="providerTpl">
<div class="layui-show">
<c:if test="${sessionScope.CurrBatchStockOrders.batchOrderStatus == 0}">
{{#  if(d.providerCount != 1){ }}
<a id="selectProvider{{d.batchStockGoodsId}}" class="layui-btn layui-btn-normal layui-btn-mini" lay-event="provider">供应商</a>
{{#  } }}
</c:if>
<input type="hidden" id="providerId{{d.batchStockGoodsId}}" value="{{d.providerId}}">   
<span id="providerName{{d.batchStockGoodsId}}">{{d.providerName}}</span>
</div>
</script>

<script type="text/html" id="selectProviderTpl">
  <a class="layui-btn layui-btn-mini" lay-event="selectProvider">选择</a>
</script>

<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/stocks/master_batch_stock_orders_goods.js"></script> 

<div id="selectGoodsPop"  class="layui-row layui-hide">
	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
		<blockquote class="layui-elem-quote">先选择要采购的商品，添加到当前采购的列表里。</blockquote>
		<form class="layui-form " action="">
		    <div class="layui-form-item layui-input-inline">
		    	<select name="parentTypeId" id="topGoodsType" lay-filter="selectTopGoodsType">
					<option value="">请选择商品大类[全部]</option>
					<c:forEach var="type" items="${TopGoodsTypes}">
						<option value="${type.goodsTypeId}">${type.goodsTypeName}</option>
					</c:forEach>
				</select>
		    </div>
		    <div class="layui-form-item layui-input-inline">
		    	<select name="goodsTypeId" id="subGoodsType" lay-filter="selectSubGoodsType">
					<option value="">请选择商品小类[全部]</option>
				</select>
		    </div>
		    <div class="layui-form-item layui-input-inline">
		    	<button class="layui-btn" lay-submit lay-filter="goodsQuerySubmit"  href="#"><i class="larry-icon">&#xe85b;</i> 查询商品</button>
		    </div>
		</form>
	</div>
	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
	  	<div class="user-tables">
	  		<table id="selectGoodsTables" lay-filter="selectGoodsTables"></table>
	  	</div>
	</div>
</div>

</body>
</html>