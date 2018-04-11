<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>门店交易数据查询</title>
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
                <legend><a name="color-design">各门店交易数据查询</a></legend>
            </fieldset>
            <form class="layui-form">
            	<div class="layui-form-item layui-input-inline">
             		<input type="text" id="createDateText" name="createDateText" autocomplete="off"  class="layui-input" placeholder="请选择交易日期">
             	</div>
             	<div class="layui-form-item layui-input-inline">
             		<input type="text" id="endDateText" name="endDateText" autocomplete="off"  class="layui-input" placeholder="交易截止日期">
             	</div>
             	<div class="layui-form-item layui-input-inline">
             		<select name="merGroupId">
						<option value="">请选择门店[全部]</option>
						<c:forEach var="store" items="${AllStoresForRecord}">
							<option value="${store.merGroupId}">${store.groupName}</option>
						</c:forEach>
					</select>
             	</div>
             	<div class="layui-form-item layui-input-inline">
             		<input type="text" name="orderCode" autocomplete="off"  class="layui-input" placeholder="请输入订单编码" size="30">
             	</div>
             	<div class="layui-form-item layui-input-inline">
             		<input type="text" name="termCode" autocomplete="off"  class="layui-input" placeholder="终端号" size="20">
             	</div>
             	<div class="layui-form-item layui-input-inline">
             		<input type="text" name="termSn" autocomplete="off"  class="layui-input" placeholder="SN号" size="20">
             	</div>
             	<div class="layui-form-item layui-input-inline">
             		<button class="layui-btn" lay-submit lay-filter="storeOrdersQuerySubmit"><i class="larry-icon">&#xe896;</i><cite>查询</cite></button>
             	</div>
             	<div class="layui-form-item layui-input-inline">
             		<button class="layui-btn" lay-submit lay-filter="storeOrdersExportSubmit"><i class="layui-icon">&#xe61e;</i><cite>导出</cite></button>
             	</div>
             </form>
    	</div>

    	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="storeOrdersTables" lay-filter="storeOrdersTables"></table>
    		</div>
    	</div>
    </div>
</div>
<script type="text/html" id="groupsbar">
  <a class="layui-btn layui-btn-mini" lay-event="view">明细</a>
{{#  if(d.orderConfirm != 1){ }}
<a class="layui-btn layui-btn-mini layui-btn-normal" lay-event="confirm">核销</a>
{{#  } }}
  
</script>

<script type="text/html" id="payMethodTpl">
{{#  if(d.payMethod == 0){ }}
现金支付
{{#  } else if(d.payMethod == 1){ }}
微信支付
{{#  } else if(d.payMethod == 2){ }}
支付宝
{{#  } else if(d.payMethod == 3){ }}
电信翼支付
{{#  } else if(d.payMethod == 4){ }}
银行卡
{{#  } else if(d.payMethod == 5){ }}
微信公众号
{{#  } else if(d.payMethod == 6){ }}
支付宝服务号
{{#  } }}
</script>
<script type="text/html" id="shouxufei">
{{#  if(d.payMethod == 0){ }}
0
{{#  } else if(d.payMethod == 1){ }}
{{d.finalPriceText*0.0038}}
{{#  } else if(d.payMethod == 2){ }}
{{d.finalPriceText*0.0038}}
{{#  } else if(d.payMethod == 3){ }}
{{d.finalPriceText*0.0038}}
{{#  } else if(d.payMethod == 4){ }}
{{d.finalPriceText*0.0038}}
{{#  } else if(d.payMethod == 5){ }}
{{d.finalPriceText*0.0038}}
{{#  } else if(d.payMethod == 6){ }}
{{d.finalPriceText*0.0038}}
{{#  } }}
</script>
<script type="text/html" id="confirmTpl">
{{#  if(d.orderConfirm == 1){ }}
已核销
{{#  } }}
</script>
<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/orders/master_stores_orders_records.js"></script> 

<div id="orderDetailsPop" class="layui-hide layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
	<div class="user-tables">
		<table id="ordersDetailsTables" lay-filter="ordersDetailsTables"></table>
	</div>
</div>

</body>
</html>