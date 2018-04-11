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
                <legend><a name="color-design">门店库存调拨 -> 门店出库确认</a></legend>
            </fieldset>
            
            <form class="layui-form " action="">
            	<div class="layui-form-item layui-input-inline">
            		<select name="orderStatus">
						<option value="1">选择调拨状态[已终审]</option>
						<option value="2">调出门店已确认</option>
					</select>
            	</div>
            	<div class="layui-form-item layui-input-inline">
	                <button class="layui-btn layui-bg-cyan" lay-submit lay-filter="queryDispatchOrders"><i class="larry-icon">&#xe918;</i><cite> 查询</cite></button>
            	</div>
           	</form>
    	</div>

    	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="stockDispatchTables" lay-filter="stockDispatchTables"></table>
    		</div>
    		
    	</div>
    	
    </div>
</div>
<script type="text/html" id="groupsbar">
  <a class="layui-btn layui-btn-mini" lay-event="view">查看明细</a>
</script>

<script type="text/html" id="currentStockCountTpl">
  {{d.currentStockCount}}{{d.measurUnit}}
</script>

<script type="text/html" id="orderStatusTpl">
{{#  if(d.orderStatus == 0){ }}
已初审
{{#  } else if(d.orderStatus == 1){ }}
已终审
{{#  } else if(d.orderStatus == 2){ }}
调出门店已确认
{{#  } else if(d.orderStatus == 3){ }}
调入门店已签收
{{#  } }}
</script>

<script type="text/html" id="dispatchStockCountTpl">
  {{d.dispatchStockCount}}{{d.measurUnit}}
</script>

<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/stocks/master_stock_dispatch3.js"></script> 
</body>
</html>