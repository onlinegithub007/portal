<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>门店商品价格管理</title>
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
                <legend><a name="color-design">门店商品价格管理  -> <b> ${sessionScope.CurrentStoreForPrice.groupName}</b></a></legend>
            </fieldset>
            <form class="layui-form " action="">
            	<div class="layui-form-item layui-input-inline">
	                <a class="layui-btn" href="stores_goods_price_select.html"><i class="layui-icon">&#xe65c;</i><cite>返回</cite></a>
            	</div>
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
            		<select name="all" lay-filter="allGoods">
						<option value="0">只看可售</option>
						<option value="1">全部商品</option>
					</select>
            	</div>
            	<div class="layui-form-item layui-input-inline">
	                <button class="layui-btn" lay-submit lay-filter="goodsQuerySubmit"><i class="layui-icon">&#xe615;</i><cite>查询</cite></button>
            	</div>
            	
            	<div class="layui-form-item layui-input-inline">
	                <a id="exportStorePrice" class="layui-btn layui-btn-normal"><i class="layui-icon">&#xe630;</i><cite>导出商品价格</cite></a>
            	</div>
            	
            	<div class="layui-form-item layui-input-inline">
	                <a id="importStorePrice" class="layui-btn layui-btn-warm"><i class="layui-icon">&#xe62f;</i><cite>批量导入</cite></a>
            	</div>
            	
            	<div class="layui-form-item layui-input-inline">
	                <a id="copyStorePrice" class="layui-btn"><i class="layui-icon">&#xe639;</i><cite>复制到其他门店</cite></a>
            	</div>
            	
            	<!-- 
            	<div class="layui-form-item layui-input-inline">
	                <a class="layui-btn"  href="#"><i class="layui-icon">&#xe601;</i><cite>下载EXCEL</cite></a>
            	</div>
            	<div class="layui-form-item layui-input-inline">
	                <a class="layui-btn layui-btn-normal"  href="#"><i class="layui-icon">&#xe62f;</i><cite>上传EXCEL</cite></a>
            	</div>
            	 -->
            </form>
            
    	</div>

    	<div class="layui-col-lg10 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="storeGoodsPriceTables" lay-filter="storeGoodsPriceTables"></table>
    		</div>
    	</div>
    </div>
</div>
<script type="text/html" id="goodsbar">
{{#  if(d.goodsPriceId == null){ }}
  <a class="layui-btn layui-btn-mini layui-btn-warm" lay-event="grantPrice">授权价格</a>
{{#  } else { }}
  <a class="layui-btn layui-btn-mini layui-btn-normal" lay-event="savePrice">修改价格</a>
{{#  } }}
</script>

<script type="text/html" id="titleTpl">
	{{#  if(d.goodsPriceId != null){ }}
    <i class="icon larry-icon" style="font-size:12pt;color:#5FB878">&#xe88f;</i>
	{{#  } else { }}
	<i class="icon larry-icon" style="font-size:12pt;color:#FF5722">&#xe893;</i>
	{{#  } }}
</script>

<script type="text/html" id="priceTpl">
{{#  if(d.goodsPriceId == null){ }}
<input type="number" id="goodsId{{d.goodsId}}" style="width:100px" value="{{d.goodsPrice}}"></input>
{{#  } else { }}
<input type="number" id="goodsId{{d.goodsId}}" style="width:100px" value="{{d.storeGoodsPrice}}"></input>
{{#  } }}
</script>

<script type="text/html" id="costTpl">
{{#  if(d.goodsPriceId == null){ }}
0.00
{{#  } else { }}
{{d.storeGoodsCostText}}/{{d.measurUnit}}
{{#  } }}
</script>

<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript">
var seeAllGoods = 0;
</script>
<script type="text/javascript" src="js/stores_goods_price.js"></script> 

<div id="selectStorePop" class="layui-hide layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
	<div class="user-tables">
	<form class="layui-form " action="">
          	<div class="layui-form-item layui-input-inline">
          		<select name="province" id="selectProvince" required lay-filter="province">
				<option value="">[不限]</option>
       			<c:forEach var="province" items="${Provinces}">
		        	<option value="${province.areaId}">${province.areaName}</option>
       			</c:forEach>
		    </select>
          	</div>
          	<div class="layui-form-item layui-input-inline">
          		<select name="city" id="selectCity" required lay-filter="city">
		    </select>
          	</div>
          	<div class="layui-form-item layui-input-inline">
          		<select id="selectAreas" name="areaId" required lay-filter="areas">
			</select>
          	</div>
          	<div class="layui-form-item layui-input-inline">
          		<input type="text" name="groupName" autocomplete="off"  class="layui-input" value="">
          	</div>
          	<div class="layui-form-item layui-input-inline">
          		<button class="layui-btn" lay-submit lay-filter="queryMerStores"><i class="layui-icon">&#xe615;</i><cite>查询</cite></button>
          	</div>
          </form>
	
		<table id="groupsTables" lay-filter="groupsTables"></table>
	</div>
</div>

</body>
</html>