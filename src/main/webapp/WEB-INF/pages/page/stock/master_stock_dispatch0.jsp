<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>总部门店库存调拨管理</title>
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
                <legend><a name="color-design">门店库存调拨初审</a></legend>
            </fieldset>
            <blockquote class="layui-elem-quote">
	       		以下是待审核的门店库存调拨计划，审核通过后，会合并给供应商生成调拨单，由供应商完成门店之间的货物库存调拨。
	        </blockquote>
    	</div>

    	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="stockDispatchTables" lay-filter="stockDispatchTables"></table>
    		</div>
    		
    		<div style="text-align:right">
	       		<a class="layui-btn" id="agreeDispachBtn" href="#"><i class="larry-icon">&#xe88f;</i> <cite>同意以上调拨计划</cite></a>
	        </div>
    	</div>
    	
    </div>
</div>
<script type="text/html" id="groupsbar">
  <a class="layui-btn layui-btn-mini layui-btn-warm" lay-event="undispatch">取消调拨</a>
</script>

<script type="text/html" id="currentStockCountTpl">
  {{d.currentStockCount}}{{d.measurUnit}}
</script>

<script type="text/html" id="dispatchStockCountTpl">
  {{d.dispatchStockCount}}{{d.measurUnit}}
</script>

<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/stocks/master_stock_dispatch0.js"></script> 
</body>
</html>