<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>门店管理</title>
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
    				<span class="tit">修改门店</span>
    			</div>
    			<div class="larry-body-content clearfix changepwd">
                    <form class="layui-form layui-col-lg8 layui-col-md12 layui-col-sm12 layui-col-xs12 " method="post" action="">
			 	        <div class="layui-form-item">
				        	<label class="layui-form-label">门店名称</label>
				        	<div class="layui-input-block">  
				        	  	<input type="hidden" name="merGroupId" value="${sessionScope.CurrentMerStore.merGroupId}">
				        	  	<input type="text" name="groupName" required lay-verify="groupName" readonly="readonly" autocomplete="off"  class="layui-input" value="${sessionScope.CurrentMerStore.groupName}" placeholder="请输入部门名称">
				        	</div>
				        </div>

						<div class="layui-form-item">
				        	<label class="layui-form-label">门店编号</label>
				        	<div class="layui-input-block">  
				        	  	<input type="text" name="groupCode" required lay-verify="groupCode"  autocomplete="off"  class="layui-input" value="${sessionScope.CurrentMerStore.groupCode}" placeholder="请输入门店编号">
				        	</div>
				        </div>

				        <div class="layui-form-item">
				        	<label class="layui-form-label">所在地区</label>
				        	<div class="layui-input-block">
					        	<div class="layui-input-inline">
					        		<select name="province" id="selectProvince" required lay-filter="province">
					        			<c:forEach var="province" items="${Provinces}">
								        	<option value="${province.areaId}" ${province.selected}>${province.areaName}</option>
					        			</c:forEach>
								    </select>
					        	</div>
					        	<div class="layui-input-inline">
								    <select name="city" id="selectCity" required lay-filter="city">
								    	<c:forEach var="city" items="${CityAreas}">
								    		<option value="${city.areaId}" ${city.selected}>${city.areaName}</option>
								    	</c:forEach>
								    </select>
					        	</div>
					        	<div class="layui-input-inline">
								    <select id="selectAreas" name="areaId" required lay-filter="areas">
								    	<c:forEach var="area" items="${AreasList}">
								        	<option value="${area.areaId}" ${area.selected}>${area.areaName}</option>
					        			</c:forEach>
								    </select>
					        	</div>
				        	</div>
				        </div>
				        
				        <div class="layui-form-item">
				        	<label class="layui-form-label">详细地址</label>
				        	<div class="layui-input-block">  
				        	  	<input type="text" class="layui-input" name="detailAddress" required lay-verify="detailAddress" autocomplete="off"  value="${sessionScope.CurrentMerStore.detailAddress}" placeholder="请输入门店的详细地址">
				        	</div>
				        </div>
				        
				        <div class="layui-form-item">
				        	<label class="layui-form-label">门店备注</label>
				        	<div class="layui-input-block">  
				        	  	<input type="text" name="groupDesc" autocomplete="off"  class="layui-input" value="${sessionScope.CurrentMerStore.groupDesc}">
				        	</div>
				        </div>
				        
				        <div class="layui-form-item change-submit">
				        	<div class="layui-input-block">
				        		<button class="layui-btn larry-tj" lay-submit lay-filter="storeSubmit">立即提交</button>
				        		<a class="layui-btn layui-btn-primary" href="merchant_stores.html">取消</a>
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
<script type="text/javascript" src="js/stores_info.js"></script> 
</body>
</html>