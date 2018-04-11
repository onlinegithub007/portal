<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>商品降价促销管理</title>
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
                	<a name="color-design">商品降价促销 -> ${sessionScope.CurrentDiscounts.discountTypeName}</a>
                </legend>
            </fieldset>
             <form class="layui-form " action="">
             	<div class="layui-form-item layui-input-inline">
             		<a class="layui-btn" id="newGoodsDiscount" href="#"><i class="layui-icon">&#xe61f;</i><cite>新建降价子项目</cite></a>
             	</div>
				<div class="layui-form-item layui-input-inline">
					<select name="goodsDiscountType">
						<option value="">降价类型[不限]</option>
						<option value="1">直接降价</option>
						<option value="2">绝对值降价</option>
						<option value="3">百分比折扣</option>
					</select>
				</div>
             	<div class="layui-form-item layui-input-inline">
             		<button class="layui-btn larry-tj" lay-submit lay-filter="goodsDiscountQuerySubmit">查询</button>
             	</div>
             </form>
    	</div>

    	<div class="layui-col-lg10 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="discountItems1Tables" lay-filter="discountItems1Tables"></table>
    		</div>
    	</div>
    </div>
</div>

<script type="text/html" id="userbar">
  <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="remove">移除</a>
</script>

<script type="text/html" id="goodsDiscountTypeTpl">
	{{#  if(d.goodsDiscountType == 1){ }}
    直接降价
	{{#  } else if(d.goodsDiscountType == 2){ }}
	绝对值降价
	{{#  } else if(d.goodsDiscountType == 3){ }}
	百分比折扣
	{{#  } }}
</script>
<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/discount_items.js"></script> 

<div id="goodsDiscountConfirm" class="layui-hide layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
     <blockquote class="layui-elem-quote">
      <i class="larry-icon" style="font-size:16pt;color:#009688">&#xe740;</i> 
        请输入商品条码，确定商品后，设定降价类型，设置降价数据
       </blockquote>
    
	<form id="goodsInfoForm" class="layui-form layui-col-lg11 layui-col-md11 layui-col-sm11 layui-col-xs11" method="post">
		<div class="layui-form-item">
	       	<label class="layui-form-label">商品条码</label>
	       	<div class="layui-input-block">
	       		<div class="layui-input-inline">
	       			<input type="text" id="goodsCode" name="goodsCode" autocomplete="off"  class="layui-input" placeholder="请扫描或者输入条码">
	       			<input type="hidden" id="goodsId" name="goodsId" value="">
	       		</div>
	       		<div class="layui-input-inline">
	       			<button class="layui-btn larry-tj" id="queryGoodsInfo" lay-submit lay-filter="queryGoodsInfoSubmit">查询商品</button>
	       			<button type="reset" class="layui-btn layui-btn-normal" >清空</button>
	       		</div>
	       	</div>
	       </div>
     
     <div class="layui-form-item">
       	<label class="layui-form-label">商品名称</label>
       	<div class="layui-input-block">
       		<input type="text" id="goodsName" name="goodsName" autocomplete="off" class="layui-input">
       	</div>
     </div>
     
     <div class="layui-form-item">
       	<label class="layui-form-label">商品现价</label>
       	<div class="layui-input-block">
       		<input type="number" id="goodsPrice" name="goodsPrice" autocomplete="off" value="0" class="layui-input">
       	</div>
     </div>
     
     </form>
     
     <form id="goodsInfoForm2" class="layui-form layui-col-lg11 layui-col-md11 layui-col-sm11 layui-col-xs11" method="post">
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
     
	</form>
</div>

</body>
</html>