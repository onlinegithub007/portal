<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>商品明细验收</title>
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
    <link type="text/css" href="../css/iF.step.css" rel="stylesheet">
</head>
</head>
<body>
<div class="layui-fluid larry-wrapper">
    <div class="layui-row">
    	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<fieldset class="layui-elem-field layui-field-title site-title">
                <legend>
                	<a name="color-design">${sessionScope.CurrPurchaseOrder.providerName} -> 商品明细验收</a>
                </legend>
            </fieldset>
            <blockquote class="layui-elem-quote">
	            <i class="larry-icon" style="font-size:16pt;color:#009688">&#xe740;</i> 
	            进货门店：${sessionScope.ApplyStore.groupName}，联系人：${sessionScope.CurrentStockOrder.linkMan}，联系电话：${sessionScope.CurrentStockOrder.linkManPhone},<br>
	            收货地址：${sessionScope.CurrentStockOrder.applyAddress}。<br>
            </blockquote>
    	</div>

    	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="deliverAckGoodsTables" lay-filter="deliverAckGoodsTables"></table>
    		</div>
    		<blockquote class="layui-elem-quote" style="text-align:right">
	        	<a class="layui-btn" id="stockOrderAckAllBtn" href="#"><i class="larry-icon">&#xe88f;</i> <cite>以上货物清点无差异，全部收货确认</cite></a>
	        </blockquote>
    	</div>
    	
    </div>
</div>

<script type="text/html" id="goodsAckAmountTpl">
{{# if(d.ackStatus == 0){ }}
<input type="number" id="purchaseDetailId{{d.purchaseDetailId}}" style="width:100px" value="{{d.goodsAmount}}"></input> 
{{#  } else { }}
{{d.goodsAckAmount}}
{{#  } }}
</script>

<script type="text/html" id="goodsbar">
{{# if(d.ackStatus == 0){ }}
<a class="layui-btn layui-btn-normal layui-btn-mini" lay-event="ack_goods">商品验货</a>
{{#  } }}
</script>

<script type="text/html" id="ackStatusTpl">
{{# if(d.ackStatus == 1){ }}
	已验货
{{#  } else { }}
	未验货
{{#  } }}
</script>

<script type="text/javascript">
var purchaseOrderId = '${sessionScope.CurrPurchaseOrder.purchaseOrderId}';
</script>
<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/stocks/store_stock_orders_ack2.js"></script> 

<div id="goodsConfirm" class="layui-hide layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
     <blockquote class="layui-elem-quote">
      <i class="larry-icon" style="font-size:16pt;color:#009688">&#xe740;</i> 
        请核对商品数量是否一致，如是食品类还需要输入生产日期和有效期<hr>
        商品名称：<span id="ackGoodsName"></span>，采购数量：<span id="ackGoodsAmount"></span>
       </blockquote>
	<form id="goodsInfoForm" class="layui-form layui-col-lg11 layui-col-md11 layui-col-sm11 layui-col-xs11" method="post">
		<div class="layui-form-item">
     	<label class="layui-form-label">商品数量</label>
     	<div class="layui-input-block">
     	  	<input type="number" id="goodsAckAmount" autocomplete="off"  class="layui-input" placeholder="请输入实际商品数量">
     	</div>
     </div>
     
     <div class="layui-form-item" id="productDateInput" >
     	<label class="layui-form-label">生产日期</label>
     	<div class="layui-input-block">
     	  	<input type="text" id="productDate" autocomplete="off"  class="layui-input">
     	</div>
     </div>
     
     <!-- 
     <div class="layui-form-item" id="expiredDateInput">
     	<label class="layui-form-label">有效日期</label>
     	<div class="layui-input-block">
     	  	<input type="text" id="expiredDate" autocomplete="off"  class="layui-input">
     	</div>
     </div>
      -->
      
	</form>
</div>

</body>
</html>