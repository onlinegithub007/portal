<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>门店库存盘点->商品库存明细</title>
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
	                <a name="color-design">门店库存盘点->商品库存明细</a>
                </legend>
            </fieldset>
            <blockquote class="layui-elem-quote">
	            <i class="larry-icon" style="font-size:16pt;color:#009688">&#xe740;</i>
	            商品名称：${sessionScope.CurrGoodsInfo.goodsName}，商品条码：${sessionScope.CurrGoodsInfo.goodsCode}。这里的库存数量显示是的进货入库之后的换算成零售单品的数量。
	        </blockquote>
    	</div>

    	<div class="layui-col-lg10 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="goodsStockDetailTables" lay-filter="goodsStockDetailTables"></table>
    		</div>
    	</div>
    </div>
</div>

<script type="text/html" id="viewTpl">
   <a class="layui-btn layui-btn-mini" lay-event="dispatch">调拨</a>
</script>

<script type="text/html" id="stockTypeTpl">
{{# if(d.stockBillType == 0){ }}
	入库
{{#  } else { }}
	出库
{{#  } }}   
</script>

<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/stocks/store_goods_stock.js"></script> 

<div id="dispatchPop"  class="layui-row layui-hide">
	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
		<blockquote class="layui-elem-quote">请选择要调拨到的门店，输入调拨数量，建立调拨任务草稿，后续还需经过审核后生效。</blockquote>
	</div>
	<div class="layui-col-lg11 layui-col-md11 layui-col-sm11 layui-col-xs11">
	  	<form class="layui-form " action="">
         	<div class="layui-form-item">
	         	<label class="layui-form-label">选择门店</label>
	         	<div class="layui-input-block">
		         	<select id="toStore">
						<option value="">请选择门店</option>
						<c:forEach var="store" items="${AllStoreToDispatch}">
							<option value="${store.merGroupId}">${store.groupName}</option>
						</c:forEach>
					</select>
	         	</div>
         	</div>
         	<div class="layui-form-item">
         		<label class="layui-form-label">调拨数量</label>
	         	<div class="layui-input-block">
	         		<input type="number" id="dispatchNum" autocomplete="off" class="layui-input" value="0">
	         	</div>
         	</div>
         </form>
	</div>
</div>

</body>
</html>