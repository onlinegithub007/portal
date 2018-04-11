<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>门店库临期存盘点</title>
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
                	<c:if test="${sessionScope.ExpiredStockCurrentStore != null}">
                	<a class="layui-btn layui-btn-small" href="master_store_goods_stock_select.html"><i class="layui-icon">&#xe65c;</i><cite>返回</cite></a>
                	</c:if>
                	<a name="color-design">门店临期库存盘点 <c:if test="${sessionScope.ExpiredStockCurrentStore != null}">-> ${sessionScope.ExpiredStockCurrentStore.groupName}</c:if></a>
                </legend>
            </fieldset>
            <form class="layui-form " action="">
            	<div class="layui-form-item layui-input-inline">
            		<select name="stockAmountParam" lay-filter="expiredType">
						<option value="-1">库存临期状态[所有]</option>
						<option value="0">三分之一保质期</option>
						<option value="1">三十天到期</option>
						<option value="2">十天到期</option>
						<option value="3">过期</option>
					</select>
            	</div>
            	<div class="layui-form-item layui-input-inline">
	                <button class="layui-btn" lay-submit lay-filter="goodsQuerySubmit"><i class="layui-icon">&#xe615;</i><cite>查询</cite></button>
            	</div>
            </form>
            
    	</div>

    	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="goodsExpiredStockTables" lay-filter="goodsExpiredStockTables"></table>
    		</div>
    	</div>
    </div>
</div>

<script type="text/html" id="viewTpl">
   <a class="layui-btn layui-btn-mini" lay-event="view">申请促销</a>
</script>

<script type="text/html" id="expiredFlagTpl">
{{#  if(d.expiredDay > 30){ }}
<button class="layui-btn layui-btn-small" lay-event="view">正常<span class="layui-badge layui-bg-gray">{{d.stockCount}}</span></button>
{{#  } else if(d.expiredDay <= 30 && d.expiredDay > 10){ }}
<button class="layui-btn layui-btn-warm layui-btn-small" lay-event="view">预警<span class="layui-badge layui-bg-gray">{{d.stockCount}}</span></button>
{{#  } else if(d.expiredDay <= 10 && d.expiredDay >0){ }}
<button class="layui-btn layui-btn-danger layui-btn-small" lay-event="view">警告<span class="layui-badge layui-bg-gray">{{d.stockCount}}</span></button>
{{#  } else{ }}
<button class="layui-btn layui-btn-small layui-bg-cyan" lay-event="view">过期<span class="layui-badge layui-bg-gray">{{d.stockCount}}</span></button>
{{#  } }}
</script>

<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/store_goods_expired_stock.js"></script> 
</body>
</html>