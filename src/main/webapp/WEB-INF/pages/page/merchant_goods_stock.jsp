<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>总部商品库存盘点</title>
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
                <legend><a name="color-design">总部商品库存盘点</a></legend>
            </fieldset>
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
            		<select name="stockAmountParam" id="stockAmountParam">
						<option value="-1">[库存状态不限]</option>
						<option value="0">0或负库存</option>
						<option value="0-100">100及以下</option>
						<option value="101-500">100-500区间</option>
						<option value="501-1000">500-1000区间</option>
						<option value="1001-2000">1000-2000区间</option>
						<option value="2001-5000">2000-5000区间</option>
						<option value="5000-0">5000以上</option>
					</select>
            	</div>
            	<div class="layui-form-item layui-input-inline">
	                <button class="layui-btn" lay-submit lay-filter="goodsQuerySubmit"><i class="layui-icon">&#xe615;</i><cite>查询</cite></button>
            	</div>
            	<div class="layui-form-item layui-input-inline">
	                <a id="downloadStocklist" class="layui-btn"  href="#"><i class="layui-icon">&#xe630;</i><cite>下载商品库存清单</cite></a>
            	</div>
            	<div class="layui-form-item layui-input-inline">
	                <a id="uploadGoodsStockTemp" class="layui-btn layui-btn-normal"  href="#"><i class="layui-icon">&#xe62f;</i><cite>导入商品入库单</cite></a>
            	</div>
            </form>
            
    	</div>

    	<div class="layui-col-lg10 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="goodsStockTables" lay-filter="goodsStockTables"></table>
    		</div>
    	</div>
    </div>
</div>

<script type="text/html" id="titleTpl">
	{{#  if(d.status == 0){ }}
    <i class="icon larry-icon" style="font-size:12pt;color:#5FB878">&#xe88f;</i>
	{{#  } else { }}
	<i class="icon larry-icon" style="font-size:12pt;color:#FF5722">&#xe893;</i>
	{{#  } }}
</script>

<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/merchant_goods_stock.js"></script> 
</body>
</html>