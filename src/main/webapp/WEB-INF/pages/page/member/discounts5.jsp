<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>商品捆绑销售管理</title>
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
                <legend><a name="color-design">商品捆绑销售管理</a></legend>
            </fieldset>
             <form class="layui-form " action="">
             	<div class="layui-form-item layui-input-inline">
             		<a class="layui-btn"  href="discounts1_info.html?discountType=${sessionScope.DiscountType.discountType}"><i class="layui-icon">&#xe61f;</i><cite>新建捆绑策略</cite></a>
             	</div>
             	<div class="layui-form-item layui-input-inline">
					<input type="hidden" name="discountType" value="0">
					<select name="memberLevelId">
						<option value="">请选择会员级别[全部]</option>
						<c:forEach var="clientLevel" items="${MemberClientLevel}">
							<option value="${clientLevel.clientLevelId}">${clientLevel.levelName}</option>
						</c:forEach>
					</select>
				</div>
				<div class="layui-form-item layui-input-inline">
					<select name="discountItemStatus">
						<option value="0">请选择状态[未生效]</option>
						<option value="1">已生效</option>
						<option value="2">已过期</option>
						<option value="3">已禁用</option>
					</select>
				</div>
				<div class="layui-form-item layui-input-inline">
					<input type="text" id="createDateText" name="createDateText" autocomplete="off"  class="layui-input" placeholder="请选择创建日期">
				</div>
             	<div class="layui-form-item layui-input-inline">
             		<button class="layui-btn larry-tj" lay-submit lay-filter="discountQuerySubmit">查询</button>
             	</div>
             </form>
    	</div>

    	<div class="layui-col-lg10 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="discount1Tables" lay-filter="discount1Tables"></table>
    		</div>
    	</div>
    </div>
</div>

<script type="text/html" id="userbar">
<a class="layui-btn layui-btn-mini" lay-event="view">新建规则</a>
<a class="layui-btn layui-btn-normal layui-btn-mini" lay-event="stores">指定门店</a>
{{#  if(d.discountItemStatus == 0){ }}  
<a class="layui-btn layui-btn-mini" lay-event="enable">激活</a>
{{#  } }}
{{#  if(d.discountItemStatus == 1){ }}  
<a class="layui-btn layui-btn-mini" lay-event="disable">禁用</a>
{{#  } }}

{{#  if(d.discountItemStatus == 0){ }}  
  <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
{{#  } }}
</script>

<script type="text/html" id="validStatusTpl">
	{{#  if(d.validStatus == 0){ }}
    未生效
	{{#  } else if(d.validStatus == 1){ }}
	已生效
	{{#  } else if(d.validStatus == 2){ }}
	已过期
	{{#  } }}
</script>

<script type="text/html" id="discountUserTypeTpl">
	{{#  if(d.discountUserType == 0){ }}
    全员
	{{#  } else if(d.discountUserType == 1){ }}
	会员
	{{#  } }}
</script>

<script type="text/html" id="discountItemStatusTpl">
	{{#  if(d.discountItemStatus == 0){ }}
    未生效
	{{#  } else if(d.discountItemStatus == 1){ }}
	已生效
	{{#  } else if(d.discountItemStatus == 2){ }}
	已过期
	{{#  } else if(d.discountItemStatus == 3){ }}
	已禁用
	{{#  } }}
</script>

<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/discount5.js"></script> 
</body>
</html>