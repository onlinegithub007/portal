<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>门店库存盘点</title>
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
                	<c:if test="${sessionScope.CurrentStore.groupName != null}">
                	<a class="layui-btn layui-btn-small" href="master_store_goods_stock_select.html"><i class="layui-icon">&#xe65c;</i><cite>返回</cite></a>
                	</c:if>
                	<a name="color-design">门店库存盘点  <c:if test="${sessionScope.CurrentStore.groupName != null}">-> ${sessionScope.CurrentStore.groupName}</c:if></a>
                </legend>
            </fieldset>
            <blockquote class="layui-elem-quote">
            	每个月做一次库存盘点，系统库存和实际库存无差异的，请直接点“无差异”按钮；存在差异的，请输入差额数字后点击“保存差异”按钮。当实际库存小于系统库存时，需要输入负数。
            </blockquote>
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
            </form>
            
    	</div>

    	<div class="layui-col-lg10 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="goodsStockTables" lay-filter="goodsStockTables"></table>
    		</div>
    	</div>
    </div>
</div>

<script type="text/html" id="viewTpl">
<a class="layui-btn layui-btn-mini" lay-event="ok">无差异</a>
<a class="layui-btn layui-btn-warm layui-btn-mini" lay-event="diff">保存差异</a>   
</script>

<script type="text/html" id="stockCheckTpl">
{{#  if(d.stockCheckorId == null){ }}
<input type="number" id="goodsStockId{{d.goodsStockId}}" style="width:100px" value="0"></input>
{{#  } else { }}
<input type="number" id="goodsStockId{{d.goodsStockId}}" style="width:100px" value="{{d.checkDiffValue}}"></input>
{{#  } }}
</script>

<script type="text/html" id="stockCheckStatusTpl">
{{#  if(d.stockCheckorId == null){ }}
今日未盘点
{{#  } else { }}
今日已盘点 <i class="larry-icon" style="color:#009688">&#xe88f;</i>
{{#  } }}
</script>

<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/stocks/store_goods_stock.js"></script> 
</body>
</html>