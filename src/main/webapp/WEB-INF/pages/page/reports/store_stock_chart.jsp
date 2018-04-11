<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>库存排行</title>
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
                	<a name="color-design">库存排行</a>
                </legend>
            </fieldset>
            <form class="layui-form " action="">
	    		<div class="layui-form-item layui-input-inline">
	    			<button class="layui-btn layui-btn-normal" lay-submit lay-filter="viewTotalStock"><i class="larry-icon">&#xe7dd;</i><cite> 查询总库存</cite></button>
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
	                <button class="layui-btn" lay-submit lay-filter="viewGoodsTypeStock"><i class="layui-icon">&#xe615;</i><cite>查询品类库存</cite></button>
            	</div>
            	<div class="layui-form-item layui-input-inline">
	                <button class="layui-btn layui-bg-cyan" lay-submit lay-filter="viewGoodsStock"><i class="larry-icon">&#xe918;</i><cite> 查询商品库存</cite></button>
            	</div>
           	</form>
    	</div>

    	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
   			<div id="storeStockChart" style="height:250pt"></div>
    	</div>
    	
    	<div class="layui-col-lg10 layui-col-md10 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
	   			<table id="storeChartTables" lay-filter="storeChartTables"></table>
	   		  </div>
    	</div>
    </div>
</div>

<script type="text/html" id="goodsbar">
<a class="layui-btn layui-btn-mini" lay-event="select">选择</a>  
</script>

<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/reports/store_stock_chart.js"></script>
<script type="text/javascript" src="echarts/echarts.min.js"></script>


<script type="text/javascript">
var myChart = echarts.init(document.getElementById('storeStockChart'));

var option = {
	title: {
		text: '门店库存排行'
	},
	color: ['#009688', '#5FB878'],
    tooltip : {
    	trigger: 'axis',
        axisPointer: {
            type: 'cross',
            crossStyle: {
                color: '#999'
            }
        }
    },
    legend: {
        data:['库存数量', '库存金额']
    },
    toolbox: {
        show: true,
        feature: {
            saveAsImage: {}
        }
    },
    xAxis : [
        {
            type : 'category',
            splitLine: {show: false},
            data : [],
            axisPointer: {
                type: 'shadow'
            }
        }
    ],
    yAxis : [
    	{
            type: 'value',
            name: '数量'
        },
        {
            type: 'value',
            name: '金额',
            axisLabel: {
                formatter: '{value} 元'
            }
        }
    ],
    series : [
    	{
            name:'库存数量',
            type:'bar',
            yAxisIndex: 0,
            data:[]
        },
        {
            name:'库存金额',
            type:'bar',
            yAxisIndex: 1,
            data:[]
        }
    ]
};

myChart.setOption(option);
</script>

<div id="selectGoodsPop"  class="layui-row layui-hide">
	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
		<blockquote class="layui-elem-quote">请选择要查看库存的商品</blockquote>
	</div>
	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
	  	<div class="user-tables">
	  		<table id="selectGoodsTables" lay-filter="selectGoodsTables"></table>
	  	</div>
	</div>
</div>

</body>
</html>