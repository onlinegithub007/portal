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
                <legend><a name="color-design">商品信息管理</a></legend>
            </fieldset>
            <form class="layui-form " action="">
            	<div class="layui-form-item layui-input-inline">
	                <a class="layui-btn" href="goods_info.html"><i class="layui-icon">&#xe61f;</i><cite>增加商品</cite></a>
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
					<input type="text" name="goodsCode" autocomplete="off"  class="layui-input" placeholder="请输入商品编号" size="20">
				</div>
            	<div class="layui-form-item layui-input-inline">
            		<select name="status" id="subGoodsType">
						<option value="-1">商品状态[不限]</option>
						<option value="0">已上架</option>
						<option value="1">已下架</option>
					</select>
            	</div>
				<div class="layui-form-item layui-input-inline">
					<button class="layui-btn" lay-submit lay-filter="goodsQuery"><i class="layui-icon">&#xe615;</i><cite>创建时间</cite></button>
				</div>
            	<div class="layui-form-item layui-input-inline">
	                <button class="layui-btn" lay-submit lay-filter="goodsQuerySubmit"><i class="layui-icon">&#xe615;</i><cite>查询</cite></button>
            	</div>
            	<div class="layui-form-item layui-input-inline">
	                <a id="downloadBlankTemp" class="layui-btn"  href="#"><i class="layui-icon">&#xe630;</i><cite>下载空白模板</cite></a>
            	</div>
            	<div class="layui-form-item layui-input-inline">
	                <a id="downloadGoodsTemp" class="layui-btn"  href="#"><i class="layui-icon">&#xe630;</i><cite>导出商品信息</cite></a>
            	</div>
            	<div class="layui-form-item layui-input-inline">
	                <a id="uploadGoodsTemp" class="layui-btn layui-btn-normal"  href="#"><i class="layui-icon">&#xe62f;</i><cite>导入商品</cite></a>
            	</div>
            </form>
            
    	</div>

    	<div class="layui-col-lg10 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="goodsTables" lay-filter="goodsTables"></table>
    		</div>
    	</div>
    </div>
</div>
<script type="text/html" id="goodsbar">
  <a class="layui-btn layui-btn-mini" lay-event="edit">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>

{{#  if(d.status == 0){ }}
  <a class="layui-btn layui-btn-warm layui-btn-mini" lay-event="disable">下架</a>
{{#  } else { }}
  <a class="layui-btn layui-btn-mini" lay-event="enable">上架</a>
{{#  } }}
  <a class="layui-btn layui-btn-mini" lay-event="addSame">新增同类</a>
</script>

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
<script type="text/javascript" src="js/goods.js"></script> 
</body>
</html>