<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>门店销售排行</title>
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
                <legend><a name="color-design">门店销售排行</a></legend>
            </fieldset>
            <form class="layui-form">
            	<div class="layui-form-item layui-input-inline">
             		<input type="text" id="createDateText" name="createDateText" autocomplete="off"  class="layui-input" placeholder="请选择开始交易日期">
             	</div>
             	<div class="layui-form-item layui-input-inline">
             		<input type="text" id="endDateText" name="endDateText" autocomplete="off"  class="layui-input" placeholder="请选择结束交易日期">
             	</div>
             	<div class="layui-form-item layui-input-inline">
             		<select name="indexType">
						<option value="0">销售额排行</option>
						<option value="1">销售利润排行</option>
					</select>
             	</div>
             	<div class="layui-form-item layui-input-inline">
             		<a class="layui-btn" lay-submit lay-filter="storeOrdersQuerySubmit"><i class="larry-icon">&#xe896;</i><cite>查询</cite></a>
             	</div>
             	
             </form>
    	</div>

		<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
   			<div id="storeSalesChart" style="height:250pt"></div>
    	</div>

    	<div class="layui-col-lg10 layui-col-md10 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			<table id="storeOrdersGoodsTables" lay-filter="storeOrdersGoodsTables"></table>
    		</div>
    	</div>
    </div>
</div>
<script type="text/html" id="groupsbar">
  <a class="layui-btn layui-btn-mini" lay-event="view">明细</a>
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
{{#  } }}
</script>
<!-- 加载js文件 -->
<script type="text/javascript" src="../common/frame/layui/layui.js"></script> 
<script type="text/javascript" src="js/common.js"></script> 
<script type="text/javascript" src="js/orders/stores_sale_sort.js"></script> 
<script type="text/javascript" src="echarts/echarts.min.js"></script>

<script type="text/javascript">
var myChart = echarts.init(document.getElementById('storeSalesChart'));

var option = {
	title: {
		text: '门店销售排行'
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
        data:['销售额', '销售利润']
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
            name: '金额',
            axisLabel: {
                formatter: '{value} 元'
            }
        }
    ],
    series : [
    	{
            name:'销售额',
            type:'bar',
            yAxisIndex: 0,
            data:[]
        },
        {
            name:'销售利润',
            type:'bar',
            min:0,
            yAxisIndex: 0,
            data:[]
        }
    ]
};

myChart.setOption(option);
</script>

</body>
</html>