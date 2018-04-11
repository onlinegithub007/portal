<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>商品整额折扣管理</title>
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
    	<div class="layui-col-lg10 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<fieldset class="layui-elem-field layui-field-title site-title">
                <legend>
                	<a class="layui-btn layui-btn-small" href="discounts.html?discountType=${sessionScope.CurrentDiscounts.discountType}"><i class="layui-icon">&#xe65c;</i><cite>返回</cite></a>
                	<a name="color-design">商品整额折扣 -> ${sessionScope.CurrentDiscounts.discountTypeName}</a>
                </legend>
            </fieldset>
             <form class="layui-form " action="">
             	<div class="layui-form-item layui-input-inline">
             		<a class="layui-btn" id="newOrderDiscount" href="#"><i class="layui-icon">&#xe61f;</i><cite>新建子项目</cite></a>
             	</div>
				<div class="layui-form-item layui-input-inline">
					<select name="orderDiscountType">
						<option value="">折扣类型[不限]</option>
						<option value="1">绝对值折扣</option>
						<option value="2">百分比折扣</option>
					</select>
				</div>
             	<div class="layui-form-item layui-input-inline">
             		<button class="layui-btn larry-tj" lay-submit lay-filter="orderDiscountQuerySubmit">查询</button>
             	</div>
             </form>
    	</div>

    	<div class="layui-col-lg10 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="discountItems2Tables" lay-filter="discountItems2Tables"></table>
    		</div>
    	</div>
    </div>
</div>

<script type="text/html" id="userbar">
  <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="remove">移除</a>
</script>

<script type="text/html" id="orderDiscountTypeTpl">
	{{#  if(d.orderDiscountType == 1){ }}
    绝对值折扣
	{{#  } else if(d.orderDiscountType == 2){ }}
	百分比折扣
	{{#  } }}
</script>
<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/discount_items.js"></script> 

<div id="orderDiscountConfirm" class="layui-hide layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
     <blockquote class="layui-elem-quote">
      <i class="larry-icon" style="font-size:16pt;color:#009688">&#xe740;</i> 
        请输入整单达到金额后，设定折扣的类型和数值，金额单位为分
       </blockquote>
       
     <form id="goodsInfoForm2" class="layui-form layui-col-lg11 layui-col-md11 layui-col-sm11 layui-col-xs11" method="post">
     <div class="layui-form-item">
       	<label class="layui-form-label">折扣类型</label>
       	<div class="layui-input-block">
		      <input type="radio" lay-filter="orderDiscountType" name="orderDiscountType" value="1" title="绝对值折扣" checked>
		      <input type="radio" lay-filter="orderDiscountType" name="orderDiscountType" value="2" title="百分比折扣">
       	</div>
     </div>
     
     <div class="layui-form-item">
     	<label class="layui-form-label">订单金额达到额(分)</label>
     	<div class="layui-input-block">
     		<input type="number" id="orderReachValue" name="orderReachValue" value="0" autocomplete="off"  class="layui-input">
     	</div>
     </div>  
     
     <div class="layui-form-item" id="orderDiscountType1">
     	<label class="layui-form-label">绝对值折扣(分)</label>
     	<div class="layui-input-block">
     		<input type="number" id="orderDiscountValue" name="orderDiscountValue" value="0" autocomplete="off"  class="layui-input">
     	</div>
     </div>
       
     <div class="layui-form-item layui-hide" id="orderDiscountType2">
    	<label class="layui-form-label">百分比折扣</label>
    	<div class="layui-input-block">
    		<input type="number" id="orderDiscountPercent" name="orderDiscountPercent" value="0" autocomplete="off"  class="layui-input">
    	</div>
    </div>
     
	</form>
</div>

</body>
</html>