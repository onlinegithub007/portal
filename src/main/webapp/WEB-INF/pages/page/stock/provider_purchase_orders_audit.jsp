<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>供应商订单审核</title>
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
                <legend><a name="color-design">供应商订单审核</a></legend>
            </fieldset>
            <form class="layui-form " action="">
            	<div class="layui-form-item layui-input-inline">
            		<select name="providerAckAudit">
            			<option value="">审核状态[全部]</option>
						<option value="0" selected>未审核</option>
						<option value="1">已审核</option>
					</select>
            	</div>
            	<div class="layui-form-item layui-input-inline">
            		<select name="dealType">
            			<option value="">请选择订单类型[全部]</option>
						<option value="0">采购单</option>
						<option value="1">调拨单</option>
					</select>
            	</div>
            	<div class="layui-form-item layui-input-inline">
	                <button class="layui-btn" lay-submit lay-filter="stockOrderQuerySubmit"><i class="layui-icon">&#xe615;</i><cite>查询</cite></button>
            	</div>
            </form>
            
    	</div>

    	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="myOrdersTables" lay-filter="myOrdersTables"></table>
    		</div>
    	</div>
    </div>
</div>
<script type="text/html" id="goodsbar">
{{# if(d.providerAckAudit == 0){ }}
<a class="layui-btn layui-btn-mini" lay-event="ack">审核</a>
{{#  }else { }}
<a class="layui-btn layui-btn-normal layui-btn-mini" lay-event="view">查看</a>
{{#  } }}
</script>

<script type="text/html" id="providerAckStatusTpl">
{{# if(d.providerAckStatus == 0){ }}
	未确认
{{#  } else if(d.providerAckStatus == 1){ }}
	已确认且无修改
{{#  } else { }}
	已确认且已修改，待审批
{{#  } }}
</script>

<script type="text/html" id="dealTypeTpl">
{{# if(d.dealType == 0){ }}
	采购单
{{#  } else { }}
	门店调拨单
{{#  } }}
</script>

<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/stocks/provider_orders_audit.js"></script> 
</body>
</html>