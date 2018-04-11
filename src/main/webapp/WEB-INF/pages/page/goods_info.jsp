<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>商品信息</title>
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
    				<span class="tit">${sessionScope.GoodsInfoTitle} ${sessionScope.CurrentGoodsInfo.goodsName}</span>
    			</div>
    			<div class="larry-body-content clearfix changepwd">
                    <form id="goodsInfoForm" class="layui-form layui-col-lg8 layui-col-md12 layui-col-sm12 layui-col-xs12 " method="post" action="">
			 	        
			 	        <div class="layui-form-item">
				        	<label class="layui-form-label">商品类别</label>
				        	<div class="layui-input-block">
					        	<div class="layui-input-inline">
					        		<select name="parentTypeId" id="topGoodsType" lay-filter="selectTopGoodsType">
					        			<option value="">请选择商品大类[全部]</option>
										<c:forEach var="type" items="${TopGoodsTypes}">
											<option value="${type.goodsTypeId}" ${type.selected}>${type.goodsTypeName}</option>
										</c:forEach>
								    </select>
					        	</div>
					        	<div class="layui-input-inline">
						        	<select name="goodsTypeId" id="subGoodsType">
										<option value="">请选择商品小类[全部]</option>
										<c:forEach var="type" items="${SubGoodsTypes}">
											<option value="${type.goodsTypeId}" ${type.selected}>${type.goodsTypeName}</option>
										</c:forEach>
									</select>
								</div>
				        	</div>
				        </div>
			 	        
			 	        <div class="layui-form-item">
				        	<label class="layui-form-label">商品条码</label>
				        	<div class="layui-input-block">
				        	  	<input type="hidden" name="goodsId" value="${sessionScope.CurrentGoodsInfo.goodsId}">
				        	  	<input type="text" id="goodsCode" name="goodsCode" required lay-verify="goodsCode"  autocomplete="off"  class="layui-input" value="${sessionScope.CurrentGoodsInfo.goodsCode}" placeholder="请输入商品条码">
				        	</div>
				        </div>
				        <div class="layui-form-item">
				        	<label class="layui-form-label">商品名称</label>
				        	<div class="layui-input-block">  
				        	  	<input type="text" id="goodsName" name="goodsName" required lay-verify="goodsName"  autocomplete="off"  class="layui-input" value="${sessionScope.CurrentGoodsInfo.goodsName}" placeholder="请输入商品名称">
				        	</div>
				        </div>
				        
				        <div class="layui-form-item">
				        	<label class="layui-form-label">销售价(分)</label>
				        	<div class="layui-input-block">
				        	  	<input type="number" id="goodsPrice" name="goodsPrice"  required lay-verify="goodsPrice|number"  autocomplete="off"  class="layui-input" value="${sessionScope.CurrentGoodsInfo.goodsPrice}" placeholder="请输入商品销售价">
				        	</div>
				        </div>
				        
				        <div class="layui-form-item">
				        	<label class="layui-form-label">零售计量单位</label>
				        	<div class="layui-input-block">
				        	  	<c:forEach var="unit" items="${MeasureUnits}">
					        	  	<c:if test="${unit == sessionScope.CurrentGoodsInfo.measurUnit}">
					        	  	  <input type="radio" name="measurUnit" autocomplete="off"  class="layui-input" title="${unit}" value="${unit}" checked="checked">
				        	  		</c:if>
				        	  		<c:if test="${unit != sessionScope.CurrentGoodsInfo.measurUnit}">
					        	  	  <input type="radio" name="measurUnit" autocomplete="off"  class="layui-input" title="${unit}" value="${unit}">
				        	  		</c:if>
				        	  	</c:forEach>
				        	</div>
				        </div>
				        
				        <div class="layui-form-item">
				        	<label class="layui-form-label">进货计量单位</label>
				        	<div class="layui-input-block">
				        	  	<c:forEach var="unit" items="${MeasureUnits}">
					        	  	<c:if test="${unit == sessionScope.CurrentGoodsInfo.providerUnit}">
					        	  	  <input type="radio" name="providerUnit" autocomplete="off"  class="layui-input" title="${unit}" value="${unit}" checked="checked">
				        	  		</c:if>
				        	  		<c:if test="${unit != sessionScope.CurrentGoodsInfo.providerUnit}">
					        	  	  <input type="radio" name="providerUnit" autocomplete="off"  class="layui-input" title="${unit}" value="${unit}">
				        	  		</c:if>
				        	  	</c:forEach>
				        	</div>
				        </div>
				        
				        <div class="layui-form-item">
				        	<label class="layui-form-label">进货与零售计量单位换算倍数</label>
				        	<div class="layui-input-block">
				        	  	<input type="number" name="providerUnitMultiple" class="layui-input" value="${sessionScope.CurrentGoodsInfo.providerUnitMultiple}">
				        	</div>
				        </div>
				        
				        <div class="layui-form-item">
				        	<label class="layui-form-label">保质期属性</label>
				        	<div class="layui-input-block">
				        	    <c:if test="${sessionScope.CurrentGoodsInfo.hasQualityPeriod == 0}">
				        	    	<input type="checkbox" lay-filter="hasQualityPeriod"  title="含有保质期属性">
				        	    </c:if>
				        	    <c:if test="${sessionScope.CurrentGoodsInfo.hasQualityPeriod == 1}">
				        	    	<input type="checkbox" lay-filter="hasQualityPeriod" title="含有保质期属性" checked>
				        	    </c:if>
				        		<input type="hidden" id="hasQualityPeriod"  name="hasQualityPeriod" value="${sessionScope.CurrentGoodsInfo.hasQualityPeriod}">
				        	</div>
				        </div>
				        
				        <div class="layui-form-item">
				        	<label class="layui-form-label">保质期/天</label>
				        	<div class="layui-input-block">
				        	  	<input type="number" id="expiredDay" name="expiredDay" autocomplete="off"  class="layui-input" value="${sessionScope.CurrentGoodsInfo.expiredDay}"/>
				        	</div>
				        </div>
				        
				        <div class="layui-form-item">
				        	<label class="layui-form-label">备注</label>
				        	<div class="layui-input-block">
				        	  	<textarea id="goodsMemo" name="goodsMemo" autocomplete="off"  class="layui-input">${sessionScope.CurrentGoodsInfo.goodsMemo}</textarea>
				        	</div>
				        </div>
				        
				        <div class="layui-form-item change-submit">
				        	<div class="layui-input-block">
				        		<button id="goodsInfoSubmitBtn" class="layui-btn larry-tj" lay-submit lay-filter="goodsInfoSubmit">立即提交</button>
				        		<a class="layui-btn layui-btn-primary" href="goods.html">取消</a>
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
<script type="text/javascript" src="js/goods.js"></script> 
</body>
</html>