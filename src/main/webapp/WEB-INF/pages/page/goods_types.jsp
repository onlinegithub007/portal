<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>商品分类管理</title>
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
    <link rel="stylesheet" href="jstree/dist/themes/default/style.min.css" />
</head>
</head>
<body>
<div class="layui-fluid larry-wrapper">
    <div class="layui-row">
    	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<fieldset class="layui-elem-field layui-field-title site-title">
                <legend><a name="color-design">商品分类管理</a></legend>
            </fieldset>
            <div class="layui-btn-group larry-group" id="larry_group">
                <button id="addTopTreeNode" class="layui-btn"><i class="layui-icon">&#xe61f;</i><cite>增加大类</cite></button>
                <button id="addSubTreeNode" class="layui-btn" ><i class="layui-icon">&#xe608;</i><cite>增加子类</cite></button>
                <button id="editTreeNode" class="layui-btn layui-btn-normal" ><i class="layui-icon">&#xe642;</i><cite>修改分类</cite></button>
                <button id="delTreeNode" class="layui-btn layui-btn-danger"><i class="layui-icon">&#xe640;</i><cite>删除分类</cite></button>
            </div>
    	</div>

    	<div class="layui-col-lg10 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<div id="goodsTypeTree"></div>
    		</div>
    	</div>
    </div>
</div>
<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="../common/plus/jquery-3.2.1.min.js"></script>
<script src="jstree/dist/jstree.min.js"></script>

<script type="text/javascript">
	
	var selectedTreeNode = {};
	
	$('#goodsTypeTree')
	.on('changed.jstree', function (e, data) 
	{
		selectedTreeNode.selectedNode = data.instance.get_node(data.selected[0]);
		selectedTreeNode.nodeText = selectedTreeNode.selectedNode.text;
		selectedTreeNode.nodeId = selectedTreeNode.selectedNode.id;
	})
	.jstree({
   	  	"core" : {
   		    "themes" : {
   		      "variant" : "large"
   		    },
   		    
   		    "data":[
   		    	${sessionScope.GoodsTypeTree}
   		    ]
   		  }
   		});
</script>

<script type="text/javascript" src="js/goods_types.js"></script>
</body>
</html>