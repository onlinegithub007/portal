<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>订货单信息</title>
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
    <link rel="stylesheet" type="text/css" href="css/mypanel.css" media="all">
</head>
</head>
<body>
<div class="layui-fluid larry-wrapper">
    <div class="layui-row lay-col-space20">
    	<div class="layui-cos-xs12 layui-col-sm12 layui-col-md12 layui-col-lg12">
    		<section class="larry-body">
    			<blockquote class="layui-elem-quote">发起订货单，请先输入联系人等信息，然后在订货单主界面录入待采购的商品。</blockquote>
                <div class="larry-body-header">
    				<span class="tit">发起订货申请</span>
    			</div>
    			<div class="larry-body-content clearfix changepwd">
                    <form id="goodsInfoForm" class="layui-form layui-col-lg8 layui-col-md12 layui-col-sm12 layui-col-xs12 " method="post" action="">
			 	        
			 	        <!-- 
			 	        <div class="layui-form-item">
				        	<label class="layui-form-label">进货单名称/描述</label>
				        	<div class="layui-input-block">
				        	  	<input type="text" id="orderTitle" name="orderTitle" required lay-verify="orderTitle"  autocomplete="off"  class="layui-input" value="${sessionScope.CurrentStockOrderInfo.orderTitle}" placeholder="请输入进货单名称或描述">
				        	</div>
				        </div>
				         -->
				         
				        <div class="layui-form-item">
				        	<label class="layui-form-label">联系人姓名</label>
				        	<div class="layui-input-block">
				        	  	<input type="hidden" name="stockOrderId" value="${sessionScope.CurrentStockOrderInfo.stockOrderId}">
				        	  	<input type="text" id="linkMan" name="linkMan" required lay-verify="linkMan"  autocomplete="off"  class="layui-input" value="${sessionScope.CurrentStockOrderInfo.linkMan}" placeholder="请输入联系人姓名">
				        	</div>
				        </div>
				        
				        <div class="layui-form-item">
				        	<label class="layui-form-label">联系人电话</label>
				        	<div class="layui-input-block">  
				        	  	<input type="text" id="linkManPhone" name="linkManPhone" required lay-verify="linkManPhone"  autocomplete="off"  class="layui-input" value="${sessionScope.CurrentStockOrderInfo.linkManPhone}" placeholder="请输入联系人电话">
				        	</div>
				        </div>
				        
				        <div class="layui-form-item">
				        	<label class="layui-form-label">收货地址</label>
				        	<div class="layui-input-block">
				        	  	<input type="text" id="applyAddress" name="applyAddress"  required lay-verify="applyAddress"  autocomplete="off"  class="layui-input" value="${sessionScope.CurrentStockOrderInfo.applyAddress}" placeholder="请输入收货地址">
				        	</div>
				        </div>
				        
				        <div class="layui-form-item change-submit">
				        	<div class="layui-input-block">
				        		<button class="layui-btn larry-tj" lay-submit lay-filter="stockOrderInfoSubmit">立即提交</button>
				        		<a class="layui-btn layui-btn-primary" href="store_stock_orders.html?orderStatus=0">取消</a>
				        	</div>
				        </div>
				        
			        </form>
    			</div>
    		</section>
    	</div>
    </div>
</div>

<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/stocks/store_stock_orders.js"></script> 
</body>
</html>