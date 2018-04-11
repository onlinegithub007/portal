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
    				<span class="tit">商品降价促销</span>
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
				        	<label class="layui-form-label">生效日期</label>
				        	<div class="layui-input-block">
				        		<input type="text" id="createDateText" name="createDateText" required lay-verify="createDateText"  autocomplete="off"  class="layui-input" placeholder="请选择生效日期">
				        	</div>
				        </div>
				        
				        <div class="layui-form-item">
				        	<label class="layui-form-label">失效日期</label>
				        	<div class="layui-input-block">
				        		<input type="text" id="endDateText" name="endDateText" required lay-verify="endDateText"  autocomplete="off"  class="layui-input" placeholder="请选择失效日期">
				        	</div>
				        </div>
				        
				        <div class="layui-form-item">
				        	<label class="layui-form-label">降价商品条码</label>
				        	<div class="layui-input-block">
				        		<div class="layui-input-inline">
				        			<input type="text" id="goodsCode" name="goodsCode" required lay-verify="goodsCode"  autocomplete="off"  class="layui-input" placeholder="请扫描或者输入条码">
				        		</div>
				        		<div class="layui-input-inline">
				        			<input type="number" id="goodsPrice" name="goodsPrice" required lay-verify="goodsPrice"  autocomplete="off"  class="layui-input" placeholder="商品现价">
				        		</div>
				        		<div class="layui-input-inline">
				        			<a class="layui-btn larry-tj" id="queryGoodsInfo">查询商品</a>
				        		</div>
				        	</div>
				        </div>
				        
				        <div class="layui-form-item">
				        	<label class="layui-form-label">商品降价类型</label>
				        	<div class="layui-input-block">
							      <input type="radio" lay-filter="goodsDiscountType" name="goodsDiscountType" value="1" title="直接降价" checked>
							      <input type="radio" lay-filter="goodsDiscountType" name="goodsDiscountType" value="2" title="绝对值降价">
							      <input type="radio" lay-filter="goodsDiscountType" name="goodsDiscountType" value="3" title="百分比折扣">
				        	</div>
				        </div>
				        
				        <div class="layui-form-item"  id="goodsDiscountType1">
				        	<label class="layui-form-label">降价后价格</label>
				        	<div class="layui-input-block">
				        		<input type="number" id="goodsDiscountPrice" name="goodsDiscountPrice" autocomplete="off"  class="layui-input">
				        	</div>
				        </div>
				        
				        <div class="layui-form-item layui-hide" id="goodsDiscountType2">
				        	<label class="layui-form-label">绝对值降价</label>
				        	<div class="layui-input-block">
				        		<input type="number" id="goodsDiscountValue" name="goodsDiscountValue" autocomplete="off"  class="layui-input">
				        	</div>
				        </div>
				        
				         <div class="layui-form-item layui-hide" id="goodsDiscountType3">
				        	<label class="layui-form-label">百分比降价</label>
				        	<div class="layui-input-block">
				        		<input type="number" id="goodsDiscountPercent" name="goodsDiscountPercent" autocomplete="off"  class="layui-input">
				        	</div>
				        </div>
				        
				        <div class="layui-form-item change-submit">
				        	<div class="layui-input-block">
				        		<button class="layui-btn larry-tj" id="saveDiscountItem1Submit" lay-submit lay-filter="saveDiscountItem1Submit">立即提交</button>
				        		<a class="layui-btn layui-btn-primary" href="discount_items.html">取消</a>
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
<script type="text/javascript" src="js/discount_items.js"></script> 
</body>
</html>