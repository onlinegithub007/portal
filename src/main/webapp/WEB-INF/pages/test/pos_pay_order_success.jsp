<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pos Pay Order success</title>
<link rel="stylesheet" type="text/css" href="${ctx}/common/frame/layui/css/layui.css" media="all">
</head>
<body  class="layui-layout">
<div class="layui-row">
<div class="layui-col-lg8 layui-col-md18 layui-col-sm12 layui-col-xs12">
<fieldset class="layui-elem-field layui-field-title">
  <legend>支付成功</legend>
</fieldset>
<form class="layui-form" method="post" action="${ctx}/posPayOrderSuccess">
	<div class="layui-form-item">
	    <label class="layui-form-label">版本号</label>
	    <div class="layui-input-block">
	      <input type="text" name="version" required  autocomplete="off" class="layui-input" value="1.1.0">
	    </div>
	</div>
	<div class="layui-form-item">
	    <label class="layui-form-label">APP ID</label>
	    <div class="layui-input-block">
	      <input type="text" name="softId" required  autocomplete="off" value="1" class="layui-input">
	    </div>
	</div>
	<div class="layui-form-item">
	    <label class="layui-form-label">spCode</label>
	    <div class="layui-input-block">
	      <input type="text" name="spCode" required  autocomplete="off" value="0" class="layui-input">
	    </div>
	</div>
	<div class="layui-form-item">
	    <label class="layui-form-label">os</label>
	    <div class="layui-input-block">
	      <input type="text" name="os" required  autocomplete="off" value="AndroidPOS" class="layui-input">
	    </div>
	</div>
	<div class="layui-form-item">
	    <label class="layui-form-label">deviceType</label>
	    <div class="layui-input-block">
	      <input type="text" name="deviceType" required  autocomplete="off" value="AndroidPOS" class="layui-input">
	    </div>
	</div>
	<div class="layui-form-item">
	    <label class="layui-form-label">longitude</label>
	    <div class="layui-input-block">
	      <input type="text" name="longitude" required  autocomplete="off" value="30.259244" class="layui-input">
	    </div>
	</div>
	<div class="layui-form-item">
	    <label class="layui-form-label">latitude</label>
	    <div class="layui-input-block">
	      <input type="text" name="latitude" required  autocomplete="off" value="120.219375" class="layui-input">
	    </div>
	</div>
	
	<div class="layui-form-item">
	    <label class="layui-form-label">Token</label>
	    <div class="layui-input-block">
	      <input type="text" name="token" required  autocomplete="off" value="" class="layui-input">
	    </div>
	</div>
	
	<div class="layui-form-item">
	    <label class="layui-form-label">订单号</label>
	    <div class="layui-input-block">
	      <input type="text" name="orderId"  autocomplete="off" value="" class="layui-input">
	    </div>
	</div>
	
	<div class="layui-form-item">
	    <label class="layui-form-label">会员支付Token</label>
	    <div class="layui-input-block">
	      <input type="text" name="memberPayToken" autocomplete="off" value="" class="layui-input">
	    </div>
	</div>
	
	<div class="layui-form-item">
	    <label class="layui-form-label">订单支付状态</label>
	    <div class="layui-input-block">
	      <input type="radio" name="payStatus" value="0" title="成功" checked>
		  <input type="radio" name="payStatus" value="1" title="失败">
	    </div>
	</div>
	
	<div class="layui-form-item">
	    <label class="layui-form-label">支付方式</label>
	    <div class="layui-input-block">
	      <select name="payMethod" lay-verify="">
			  <option value="0">现金支付</option>
			  <option value="1">微信POS机扫码支付</option>
			  <option value="2">微信用户扫码支付</option>
			  <option value="3">支付宝POS机扫码支付</option>
			  <option value="4">支付宝用户扫码支付</option>
		  </select> 
	    </div>
	</div>
	
	<div class="layui-form-item">
	    <label class="layui-form-label">支付编码</label>
	    <div class="layui-input-block">
	      <input type="text" name="payBillNumber"  autocomplete="off" value="" class="layui-input">
	    </div>
	</div>
	
	<div class="layui-form-item">
	    <label class="layui-form-label">三方支付金额</label>
	    <div class="layui-input-block">
	      <input type="number" name="payAmount"  autocomplete="off" value="" class="layui-input">
	    </div>
	</div>
	
	<div class="layui-form-item">
	    <label class="layui-form-label">三方支付时间</label>
	    <div class="layui-input-block">
	      <input type="text" class="layui-input" name="payDate" id="payDate">
	    </div>
	</div>
	
	<div class="layui-form-item">
	    <label class="layui-form-label">收现金金额</label>
	    <div class="layui-input-block">
	      <input type="number" name="cashAmount"  autocomplete="off" value="" class="layui-input">
	    </div>
	</div>
	
	<div class="layui-form-item">
	    <label class="layui-form-label">现金找零</label>
	    <div class="layui-input-block">
	      <input type="number" name="cashChange"  autocomplete="off" value="" class="layui-input">
	    </div>
	</div>
	
	<div class="layui-form-item">
	    <div class="layui-input-block">
	      <button class="layui-btn" lay-submit>查询</button>
	      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
	    </div>
	</div>

</form>
</div></div>
<!-- Layui necessary plugins -->
<script type="text/javascript" src="${ctx}/common/frame/layui/layui.js"></script>

<script type="text/javascript">

layui.use(['jquery', 'form', 'layer', 'element', 'table', 'laydate'], function(){
	
	var $ = layui.jquery
	var form = layui.form;
	var layer = layui.layer;
	var element = layui.element;
	var table = layui.table;
	
	var laydate = layui.laydate;
	  
	  //执行一个laydate实例
	  laydate.render({
	    elem: '#payDate', //指定元素,
	    type: 'datetime'
	  });
});

</script>
</body>
</html>