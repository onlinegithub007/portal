<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>门店交易日结管理</title>
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
                <legend><a name="color-design">门店交易日结管理</a></legend>
            </fieldset>
            <blockquote class="layui-elem-quote">
            	选择交易日期后，点“生成日结数据”，可生成当日交易数据的汇总。
            </blockquote>
            <form class="layui-form">
            	<div class="layui-form-item layui-input-inline">
             		<input type="text" id="clearingDateText" name="clearingDateText" autocomplete="off"  class="layui-input" placeholder="请选择交易月">
             	</div>
             	<div class="layui-form-item layui-input-inline">
             		<a class="layui-btn" lay-submit lay-filter="storeClearingQuerySubmit"><i class="larry-icon">&#xe896;</i><cite>查询</cite></a>
             	</div>
             	<div class="layui-form-item layui-input-inline">
             		<input type="text" id="createDateText" name="createDateText" autocomplete="off"  class="layui-input" placeholder="请选择交易日期">
             	</div>
             	<div class="layui-form-item layui-input-inline">
             		<a class="layui-btn layui-btn-normal" id="doDailyClear"><i class="larry-icon">&#xe896;</i><cite>生成日结数据</cite></a>
             	</div>
             </form>
    	</div>

    	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="storeDailyClearingTables" lay-filter="storeDailyClearingTables"></table>
    		</div>
    	</div>
    </div>
</div>
<script type="text/html" id="groupsbar">
  <a class="layui-btn layui-btn-mini" lay-event="refresh">刷新</a>
  <a class="layui-btn layui-btn-mini" lay-event="details">销售明细</a>
</script>

<script type="text/html" id="cashClearTpl">
{{#  if(d.cashOrderAmount != d.cashClearAmount){ }}
  <a class="layui-btn layui-btn-mini" lay-event="save">缴存</a>
{{#  } }}
</script>

<script type="text/html" id="payMethodTpl">
{{#  if(d.payMethod == 0){ }}
现金支付
{{#  } else{ }}
电子化支付
{{#  } }}
</script>
<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/orders/store_daily_clearing.js"></script> 

</body>
</html>