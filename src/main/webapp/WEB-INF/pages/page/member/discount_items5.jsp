<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>商品捆绑促销管理</title>
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
                	<a name="color-design">商品捆绑促销管理 -> ${sessionScope.CurrentDiscounts.discountTypeName}</a>
                </legend>
            </fieldset>
             <form class="layui-form " action="">
             	<div class="layui-form-item layui-input-inline">
             		<a class="layui-btn" id="newGroupDiscount" href="#"><i class="layui-icon">&#xe61f;</i><cite>新建子项目</cite></a>
             	</div>
				<div class="layui-form-item layui-input-inline">
					<select name="groupDiscountType">
						<option value="">捆绑类型[不限]</option>
						<option value="1">捆绑赠商品</option>
						<option value="2">捆绑降价</option>
						<option value="3">捆绑绝对值降价</option>
						<option value="4">捆绑百分比折扣</option>
					</select>
				</div>
             	<div class="layui-form-item layui-input-inline">
             		<button class="layui-btn larry-tj" lay-submit lay-filter="groupDiscountQuerySubmit">查询</button>
             	</div>
             </form>
    	</div>

    	<div class="layui-col-lg10 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="discountItems5Tables" lay-filter="discountItems5Tables"></table>
    		</div>
    	</div>
    </div>
</div>

<script type="text/html" id="userbar">
  <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="remove">移除</a>
</script>

<script type="text/html" id="viewGroupGoodsTpl">
  <a class="layui-btn layui-btn-mini" lay-event="viewGoods">查看捆绑商品</a>
</script>

<script type="text/html" id="groupDiscountTypeTpl">
{{#  if(d.groupDiscountType == 1){ }}
捆绑赠商品
{{#  } else if(d.groupDiscountType == 2){ }}
捆绑降价
{{#  } else if(d.groupDiscountType == 3){ }}
捆绑绝对值降价
{{#  } else if(d.groupDiscountType == 4){ }}
捆绑百分比折扣
{{#  } }}
</script>
<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/discount_items.js"></script> 

<div id="groupDiscountConfirm" class="layui-hide layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
     <blockquote class="layui-elem-quote">
      <i class="larry-icon" style="font-size:16pt;color:#009688">&#xe740;</i> 
        请输入参与捆绑销售的商品，最多支持8个商品组合，重复录入同种商品视为一种
       </blockquote>
       
     <form id="goodsInfoForm1" class="layui-form layui-col-lg11 layui-col-md11 layui-col-sm11 layui-col-xs11" method="post">
     <div class="layui-form-item">
       	<label class="layui-form-label">商品1</label>
       	<div class="layui-input-block">
       		<div class="layui-input-inline">
       			<input type="text" id="goodsCode1" name="goodsCode" required lay-verify="goodsCode"  autocomplete="off"  class="layui-input" placeholder="请扫描或者输入条码">
       		</div>
       		<div class="layui-input-inline">
       			<input type="text" id="goodsName1" name="goodsName" autocomplete="off"  class="layui-input" readonly="readonly">
       			<input type="hidden" name="goodsNo" value="1" autocomplete="off"  class="layui-input">
       			<input type="hidden" id="goodsId1" name="goodsId" autocomplete="off"  class="layui-input">
       		</div>
       		<div class="layui-input-inline">
       			<button class="layui-btn layui-btn-small larry-tj" id="queryGoodsInfo" lay-submit lay-filter="queryGoodsInfoSubmit5">查询商品</button>
       			<button type="reset" class="layui-btn layui-btn-small layui-btn-normal">清空</button>
       		</div>
       		
       	</div>
     </div>
     </form>
     
     <form id="goodsInfoForm2" class="layui-form layui-col-lg11 layui-col-md11 layui-col-sm11 layui-col-xs11" method="post">
     <div class="layui-form-item">
       	<label class="layui-form-label">商品2</label>
       	<div class="layui-input-block">
       		<div class="layui-input-inline">
       			<input type="text" id="goodsCode2" name="goodsCode" required lay-verify="goodsCode"  autocomplete="off"  class="layui-input" placeholder="请扫描或者输入条码">
       		</div>
       		<div class="layui-input-inline">
       			<input type="text" id="goodsName2" name="goodsName" autocomplete="off"  class="layui-input" readonly="readonly">
       			<input type="hidden" name="goodsNo" value="2" autocomplete="off"  class="layui-input">
       			<input type="hidden" id="goodsId2"  name="goodsId" autocomplete="off"  class="layui-input">
       		</div>
       		<div class="layui-input-inline">
       			<button class="layui-btn layui-btn-small larry-tj" id="queryGoodsInfo" lay-submit lay-filter="queryGoodsInfoSubmit5">查询商品</button>
       			<button type="reset" class="layui-btn layui-btn-small layui-btn-normal">清空</button>
       		</div>
       	</div>
     </div>
     </form>
     
     <form id="goodsInfoForm3" class="layui-form layui-col-lg11 layui-col-md11 layui-col-sm11 layui-col-xs11" method="post">
     <div class="layui-form-item">
       	<label class="layui-form-label">商品3</label>
       	<div class="layui-input-block">
       		<div class="layui-input-inline">
       			<input type="text" id="goodsCode3" name="goodsCode" required lay-verify="goodsCode"  autocomplete="off"  class="layui-input" placeholder="请扫描或者输入条码">
       		</div>
       		<div class="layui-input-inline">
       			<input type="text" id="goodsName3" name="goodsName" autocomplete="off"  class="layui-input" readonly="readonly">
       			<input type="hidden" name="goodsNo" value="3" autocomplete="off"  class="layui-input">
       			<input type="hidden" id="goodsId3"  name="goodsId" autocomplete="off"  class="layui-input">
       		</div>
       		<div class="layui-input-inline">
       			<button class="layui-btn layui-btn-small larry-tj" id="queryGoodsInfo" lay-submit lay-filter="queryGoodsInfoSubmit5">查询商品</button>
       			<button type="reset" class="layui-btn layui-btn-small layui-btn-normal">清空</button>
       		</div>
       	</div>
     </div>
     </form>
     
     <form id="goodsInfoForm4" class="layui-form layui-col-lg11 layui-col-md11 layui-col-sm11 layui-col-xs11" method="post">
     <div class="layui-form-item">
       	<label class="layui-form-label">商品4</label>
       	<div class="layui-input-block">
       		<div class="layui-input-inline">
       			<input type="text" id="goodsCode4" name="goodsCode" required lay-verify="goodsCode"  autocomplete="off"  class="layui-input" placeholder="请扫描或者输入条码">
       		</div>
       		<div class="layui-input-inline">
       			<input type="text" id="goodsName4" name="goodsName" autocomplete="off"  class="layui-input"  readonly="readonly">
       			<input type="hidden" name="goodsNo" value="4" autocomplete="off"  class="layui-input">
       			<input type="hidden"  id="goodsId4"  name="goodsId" autocomplete="off"  class="layui-input">
       		</div>
       		<div class="layui-input-inline">
       			<button class="layui-btn layui-btn-small larry-tj" id="queryGoodsInfo" lay-submit lay-filter="queryGoodsInfoSubmit5">查询商品</button>
       			<button type="reset" class="layui-btn layui-btn-small layui-btn-normal">清空</button>
       		</div>
       	</div>
     </div>
     </form>
     
     <form id="goodsInfoForm5" class="layui-form layui-col-lg11 layui-col-md11 layui-col-sm11 layui-col-xs11" method="post">
     <div class="layui-form-item">
       	<label class="layui-form-label">商品5</label>
       	<div class="layui-input-block">
       		<div class="layui-input-inline">
       			<input type="text" id="goodsCode5" name="goodsCode" required lay-verify="goodsCode"  autocomplete="off"  class="layui-input" placeholder="请扫描或者输入条码">
       		</div>
       		<div class="layui-input-inline">
       			<input type="text" id="goodsName5" name="goodsName" autocomplete="off"  class="layui-input" readonly="readonly">
       			<input type="hidden" name="goodsNo" value="5" autocomplete="off"  class="layui-input">
       			<input type="hidden" id="goodsId5"  name="goodsId" autocomplete="off"  class="layui-input">
       		</div>
       		<div class="layui-input-inline">
       			<button class="layui-btn layui-btn-small larry-tj" id="queryGoodsInfo" lay-submit lay-filter="queryGoodsInfoSubmit5">查询商品</button>
       			<button type="reset" class="layui-btn layui-btn-small layui-btn-normal">清空</button>
       		</div>
       	</div>
     </div>
     </form>
     
     <form id="goodsInfoForm6" class="layui-form layui-col-lg11 layui-col-md11 layui-col-sm11 layui-col-xs11" method="post">
     <div class="layui-form-item">
       	<label class="layui-form-label">商品6</label>
       	<div class="layui-input-block">
       		<div class="layui-input-inline">
       			<input type="text" id="goodsCode6" name="goodsCode" required lay-verify="goodsCode"  autocomplete="off"  class="layui-input" placeholder="请扫描或者输入条码">
       		</div>
       		<div class="layui-input-inline">
       			<input type="text" id="goodsName6" name="goodsName" autocomplete="off"  class="layui-input" readonly="readonly">
       			<input type="hidden" name="goodsNo" value="6" autocomplete="off"  class="layui-input">
       			<input type="hidden"  id="goodsId6"  name="goodsId" autocomplete="off"  class="layui-input">
       		</div>
       		<div class="layui-input-inline">
       			<button class="layui-btn layui-btn-small larry-tj" id="queryGoodsInfo" lay-submit lay-filter="queryGoodsInfoSubmit5">查询商品</button>
       			<button type="reset" class="layui-btn layui-btn-small layui-btn-normal">清空</button>
       		</div>
       	</div>
     </div>
     </form>
     
     <form id="goodsInfoForm7" class="layui-form layui-col-lg11 layui-col-md11 layui-col-sm11 layui-col-xs11" method="post">
     <div class="layui-form-item">
       	<label class="layui-form-label">商品7</label>
       	<div class="layui-input-block">
       		<div class="layui-input-inline">
       			<input type="text" id="goodsCode7" name="goodsCode" required lay-verify="goodsCode"  autocomplete="off"  class="layui-input" placeholder="请扫描或者输入条码">
       		</div>
       		<div class="layui-input-inline">
       			<input type="text" id="goodsName7" name="goodsName" autocomplete="off"  class="layui-input" readonly="readonly">
       			<input type="hidden" name="goodsNo" value="7" autocomplete="off"  class="layui-input">
       			<input type="hidden"  id="goodsId7"  name="goodsId" autocomplete="off"  class="layui-input">
       		</div>
       		<div class="layui-input-inline">
       			<button class="layui-btn layui-btn-small larry-tj" id="queryGoodsInfo" lay-submit lay-filter="queryGoodsInfoSubmit5">查询商品</button>
       			<button type="reset" class="layui-btn layui-btn-small layui-btn-normal">清空</button>
       		</div>
       	</div>
     </div>
     </form>
     
     <form id="goodsInfoForm8" class="layui-form layui-col-lg11 layui-col-md11 layui-col-sm11 layui-col-xs11" method="post">
     <div class="layui-form-item">
       	<label class="layui-form-label">商品8</label>
       	<div class="layui-input-block">
       		<div class="layui-input-inline">
       			<input type="text" id="goodsCode8" name="goodsCode" required lay-verify="goodsCode"  autocomplete="off"  class="layui-input" placeholder="请扫描或者输入条码">
       		</div>
       		<div class="layui-input-inline">
       			<input type="text" id="goodsName8" name="goodsName" autocomplete="off"  class="layui-input" readonly="readonly">
       			<input type="hidden" name="goodsNo" value="8" autocomplete="off"  class="layui-input">
       			<input type="hidden"  id="goodsId8"  name="goodsId" autocomplete="off"  class="layui-input">
       		</div>
       		<div class="layui-input-inline">
       			<button class="layui-btn layui-btn-small larry-tj" id="queryGoodsInfo" lay-submit lay-filter="queryGoodsInfoSubmit5">查询商品</button>
       			<button type="reset" class="layui-btn layui-btn-small layui-btn-normal">清空</button>
       		</div>
       	</div>
     </div>
     </form>
      
     <form id="goodsInfoForm" class="layui-form layui-col-lg11 layui-col-md11 layui-col-sm11 layui-col-xs11" method="post">
     <div class="layui-form-item">
       	<label class="layui-form-label">捆绑类型</label>
       	<div class="layui-input-block">
		      <input type="radio" lay-filter="groupDiscountType" name="groupDiscountType" value="1" title="赠商品" checked>
		      <input type="radio" lay-filter="groupDiscountType" name="groupDiscountType" value="2" title="直接降价">
		      <input type="radio" lay-filter="groupDiscountType" name="groupDiscountType" value="3" title="绝对值降价">
		      <input type="radio" lay-filter="groupDiscountType" name="groupDiscountType" value="4" title="百分比折扣">
       	</div>
     </div>
     </form>
     <form id="goodsInfoForm0" class="layui-form layui-col-lg11 layui-col-md11 layui-col-sm11 layui-col-xs11" method="post">
     <div class="layui-form-item" id="groupDiscountType1">
       	<label class="layui-form-label">赠送商品</label>
       	<div class="layui-input-block">
       		<div class="layui-input-inline">
       			<input type="text" id="goodsCode0" name="goodsCode" required lay-verify="goodsCode"  autocomplete="off"  class="layui-input" placeholder="请扫描或者输入条码">
       		</div>
       		<div class="layui-input-inline">
       			<input type="text" id="goodsName0" name="goodsName" autocomplete="off"  class="layui-input">
       			<input type="hidden" name="goodsNo" value="0" autocomplete="off"  class="layui-input">
       			<input type="hidden"  id="goodsId0"  name="goodsId" autocomplete="off"  class="layui-input">
       		</div>
       		<div class="layui-input-inline">
       			<button class="layui-btn layui-btn-small larry-tj" id="queryGoodsInfo" lay-submit lay-filter="queryGoodsInfoSubmit5">查询商品</button>
       			<button type="reset" class="layui-btn layui-btn-small layui-btn-normal">清空</button>
       		</div>
       	</div>
     </div>
     </form>
     <form id="goodsInfoForm" class="layui-form layui-col-lg11 layui-col-md11 layui-col-sm11 layui-col-xs11" method="post">
     <div class="layui-form-item layui-hide" id="groupDiscountType2">
     	<label class="layui-form-label">直接降价(分)</label>
     	<div class="layui-input-block">
     		<input type="number" id="groupOrderDiscountPrice" name="groupOrderDiscountPrice" value="0" autocomplete="off"  class="layui-input">
     	</div>
     </div>  
     
     <div class="layui-form-item layui-hide" id="groupDiscountType3">
     	<label class="layui-form-label">绝对值(分)</label>
     	<div class="layui-input-block">
     		<input type="number" id="groupOrderDiscountValue" name="groupOrderDiscountValue" value="0" autocomplete="off"  class="layui-input">
     	</div>
     </div>
       
     <div class="layui-form-item layui-hide" id="groupDiscountType4">
    	<label class="layui-form-label">百分比折扣</label>
    	<div class="layui-input-block">
    		<input type="number" id="groupOrderDiscountPercent" name="groupOrderDiscountPercent" value="0" autocomplete="off"  class="layui-input">
    	</div>
    </div>
     
	</form>
</div>

<div id="viewGroupGoodsPop" class="layui-hide layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
	<div class="user-tables">
		<table id="viewGroupGoodsTables" lay-filter="viewGroupGoodsTables"></table>
	</div>
</div>

</body>
</html>