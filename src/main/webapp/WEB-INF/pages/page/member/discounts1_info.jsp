<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>商品降价促销</title>
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
                <div class="larry-body-header">
    				<span class="tit">${sessionScope.DiscountType.discountTypeName}</span>
    			</div>
    			<div class="larry-body-content clearfix changepwd">
                    <form id="goodsInfoForm" class="layui-form layui-col-lg8 layui-col-md12 layui-col-sm12 layui-col-xs12 " method="post" action="">
			 	        <div class="layui-form-item">
				        	<label class="layui-form-label">会员级别</label>
				        	<div class="layui-input-block">
					        	<div class="layui-input-inline">
					        		<select name="memberLevelId">
										<option value="">请选择会员级别[不设]</option>
										<c:forEach var="clientLevel" items="${MemberClientLevel}">
											<option value="${clientLevel.clientLevelId}" ${clientLevel.selected}>${clientLevel.levelName}</option>
										</c:forEach>
									</select>
					        	</div>
				        	</div>
				        </div>
			 	        
			 	        <div class="layui-form-item">
				        	<label class="layui-form-label">优惠促销活动名称</label>
				        	<div class="layui-input-block">
				        		<input type="hidden" name="discountId" value="${sessionScope.CurrentDiscount.discountId}">
				        		<input type="hidden" name="discountType" value="${sessionScope.CurrDiscountTypeInForm}">
				        		<input type="text" id="discountTypeName" name="discountTypeName" required lay-verify="discountTypeName" value="${sessionScope.CurrentDiscount.discountTypeName}"  autocomplete="off"  class="layui-input" placeholder="请输入优惠促销活动">
				        	</div>
				        </div>
			 	        
				        <div class="layui-form-item">
				        	<label class="layui-form-label">生效时间</label>
				        	<div class="layui-input-block">
				        		<input type="text" id="createDateText2" name="createDateText" required lay-verify="createDateText" value="${sessionScope.CurrentDiscount.createDateText}"  autocomplete="off"  class="layui-input" placeholder="请选择生效时间">
				        	</div>
				        </div>
				        
				        <div class="layui-form-item">
				        	<label class="layui-form-label">失效日期时间</label>
				        	<div class="layui-input-block">
				        		<input type="text" id="endDateText" name="endDateText" required lay-verify="endDateText"  value="${sessionScope.CurrentDiscount.endDateText}"  autocomplete="off"  class="layui-input" placeholder="请选择失效时间">
				        	</div>
				        </div>
				        
				        <div class="layui-form-item change-submit">
				        	<div class="layui-input-block">
				        		<button class="layui-btn larry-tj" id="saveDiscountSubmit" lay-submit lay-filter="saveDiscountSubmit">立即提交</button>
				        		<a class="layui-btn layui-btn-primary" href="discounts.html?discountType=${sessionScope.DiscountType.discountType}">取消</a>
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
<script type="text/javascript" src="js/discount1.js"></script> 
</body>
</html>