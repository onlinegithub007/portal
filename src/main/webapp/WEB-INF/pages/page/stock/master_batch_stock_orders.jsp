<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>总部进货单管理</title>
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
                <legend><a name="color-design">总部批量派发采购单</a></legend>
            </fieldset>
            <form class="layui-form " action="">
            	<div class="layui-form-item layui-input-inline">
	                <button class="layui-btn" lay-submit lay-filter="stockOrderCreateSubmit"><i class="larry-icon">&#xe847;</i><cite>创建批量采购单</cite></button>
            	</div>
            </form>
    	</div>

    	<div class="layui-col-lg10 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="groupsTables" lay-filter="groupsTables"></table>
    		</div>
    	</div>
    	
    </div>
</div>
<script type="text/html" id="groupsbar">
<a class="layui-btn layui-btn-mini" lay-event="goods">配置商品</a>
<a class="layui-btn layui-btn-mini" lay-event="stores">选择门店</a>
{{# if(d.batchOrderStatus == 0){ }}
<a class="layui-btn layui-btn-mini layui-btn-normal" lay-event="batch">批量提交</a>
<a class="layui-btn layui-btn-mini layui-btn-danger" lay-event="delete">删除</a>
{{#  } }}
</script>

<script type="text/html" id="activeTpl">
{{# if(d.batchOrderStatus == 1){ }}
	已派发
{{#  } else { }}
	未派发
{{#  } }}
</script>

<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/stocks/master_batch_stock_orders.js"></script> 
</body>
</html>