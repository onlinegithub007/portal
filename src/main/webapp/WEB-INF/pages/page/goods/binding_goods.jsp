<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>捆绑商品管理</title>
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
                <legend>
                <a class="layui-btn layui-btn-small" href="binding_goods_select_stores.html"><i class="layui-icon">&#xe65c;</i><cite>返回</cite></a>
                <a name="color-design">捆绑商品管理[${sessionScope.CurrStore.groupName}]</a>
                </legend>
            </fieldset>
            <form class="layui-form " action="">
            	<div class="layui-form-item layui-input-inline">
	                <a id="createBindGoods" class="layui-btn" href="#"><i class="layui-icon">&#xe61f;</i><cite>新建捆绑商品</cite></a>
            	</div>
            </form>
    	</div>
		
    	<div class="layui-col-lg10 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="bindingGoodsTables" lay-filter="bindingGoodsTables"></table>
    		</div>
    	</div>
    </div>
</div>
<script type="text/html" id="groupsbar">
<a class="layui-btn layui-btn-mini" lay-event="modify">修改</a>
<a class="layui-btn layui-btn-mini layui-btn-danger" lay-event="delete">删除</a>
{{# if(d.bindActive == false){ }}
<a class="layui-btn layui-btn-mini" lay-event="active">激活</a>
{{#  } else { }}
<a class="layui-btn layui-btn-mini layui-btn-warm" lay-event="inactive">下架</a>
{{#  } }}
<a class="layui-btn layui-btn-mini layui-btn-normal" lay-event="bind_goods">商品明细</a>
</script>

<script type="text/html" id="bindDetailBar">
<a class="layui-btn layui-btn-mini layui-btn-danger" lay-event="delete">解除捆绑</a>
<a class="layui-btn layui-btn-mini layui-btn-normal" lay-event="change">修改数量</a>
</script>

<script type="text/html" id="bindGoodsCountTpl">
<input type="number" id="bindDetailId{{d.bindDetailId}}" style="width:100px" value="{{d.bindGoodsCount}}"></input> 
</script>

<script type="text/html" id="activeTpl">
{{# if(d.bindActive == true){ }}
	已生效
{{#  } else { }}
	未生效
{{#  } }}
</script>

<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/goods/binding_goods.js"></script> 

<div id="bindGoodsForm" class="layui-hide layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
	<blockquote class="layui-elem-quote">
      <i class="larry-icon" style="font-size:16pt;color:#009688">&#xe740;</i> 
        请输入捆绑后的商品名称，设定其商品条码，设定销售价格。随后在主界面中添加捆绑的实际商品。
       </blockquote>
	<form class="layui-form layui-col-lg10 layui-col-md10 layui-col-sm10 layui-col-xs10" action="">
    	<div class="layui-form-item">
	     	<label class="layui-form-label">商品名称</label>
	     	<div class="layui-input-block">
	     	  	<input type="text" id="goodsName" autocomplete="off"  class="layui-input" placeholder="请输入捆绑商品名称">
	     	</div>
     	</div>
     	<div class="layui-form-item">
	     	<label class="layui-form-label">商品条码</label>
	     	<div class="layui-input-block">
	     	  	<input type="text" id="goodsCode" autocomplete="off"  class="layui-input" placeholder="请输入捆绑商品条码">
	     	</div>
     	</div>
     	<div class="layui-form-item">
	     	<label class="layui-form-label">销售价格(分)</label>
	     	<div class="layui-input-block">
	     	  	<input type="number" id="goodsPrice" autocomplete="off" value="0" class="layui-input">
	     	</div>
     	</div>
    </form>
</div>

<div id="selectGoodsPop"  class="layui-row layui-hide">
	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
		<blockquote class="layui-elem-quote">输入要绑定的商品条码，查询并增加到当前绑定列表中</blockquote>
		<form class="layui-form layui-col-lg10 layui-col-md10 layui-col-sm10 layui-col-xs10" action="">
		    <div class="layui-form-item layui-input-inline">
		     	<label class="layui-form-label">商品条码</label>
		     	<div class="layui-input-block">
		     	  	<input type="text" id="bindingGoodsCode" name="goodsCode" autocomplete="off"  class="layui-input" placeholder="请输入捆绑商品条码">
		     	</div>
	     	</div>
		    <div class="layui-form-item layui-input-inline">
		    	<button class="layui-btn" lay-submit lay-filter="goodsQuerySubmit"  href="#"><i class="larry-icon">&#xe85b;</i> 绑定商品</button>
		    </div>
		</form>
	</div>
	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
	  	<div class="user-tables">
	  		<table id="selectGoodsTables" lay-filter="selectGoodsTables"></table>
	  	</div>
	</div>
</div>

</body>
</html>