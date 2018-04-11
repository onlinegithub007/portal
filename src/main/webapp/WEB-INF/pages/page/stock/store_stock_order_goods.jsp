<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>商品信息管理</title>
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
                <a class="layui-btn layui-btn-mini" href="store_stock_orders.html"><i class="layui-icon">&#xe65c;</i><cite>返回</cite></a>
                <a name="color-design">配置进货单商品</a>
                </legend>
            </fieldset>
            <blockquote class="layui-elem-quote">先添加商品后，然后设定商品的采购数量，调整采购数量后需要点击右边的【保存数量】按钮进行保存，不采购的商品可以通过【移除】去掉。确认后即可提交到总部审核。</blockquote>
            <form class="layui-form " action="">
            	<div class="layui-form-item layui-input-inline">
	                
            	</div>
            	<div class="layui-form-item layui-input-inline">
	                <a id="addGoodsBtn" class="layui-btn"><i class="layui-icon">&#xe630;</i><cite>添加采购商品</cite></a>
            	</div>
            </form>
            
    	</div>

    	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="goodsTables" lay-filter="goodsTables"></table>
    		</div>
    		<blockquote class="layui-elem-quote" style="text-align:right">
	       		<button class="layui-btn layui-btn-normal" id="stockOrderCountBtn" href="#"><i class="larry-icon">&#xe88f;</i> <cite>暂存数量</cite></button>
	       		<button class="layui-btn" id="stockOrderSubmitBtn" href="#"><i class="larry-icon">&#xe88f;</i> <cite>确认以上采购数量准确无误，提交总部审核</cite></button>
	        </blockquote>
    	</div>
    	
    </div>
</div>
<script type="text/html" id="goodsbar">
<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">移除</a>
</script>

<script type="text/html" id="goodsCountTpl">
<input type="number" id="goodsId{{d.stockOrderDetailId}}" style="width:100px" value="{{d.goodsCount}}"></input>  
</script>

<script type="text/html" id="titleTpl">
	{{#  if(d.status == 0){ }}
    <i class="icon larry-icon" style="font-size:12pt;color:#5FB878">&#xe88f;</i>
	{{#  } else { }}
	<i class="icon larry-icon" style="font-size:12pt;color:#FF5722">&#xe893;</i>
	{{#  } }}
</script>

<script type="text/html" id="providerUnitTpl">
	{{d.providerUnit}}({{d.providerUnitMultiple}}{{d.measurUnit}})
</script>

<script type="text/html" id="storeGoodsCostTextTpl">
	{{d.storeGoodsCostText}}/{{d.measurUnit}}
</script>


<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/stocks/store_stock_order_goods.js"></script> 

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