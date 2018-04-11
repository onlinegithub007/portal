layui.use(['layer', 'form', 'table', 'laydate', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		laydate = layui.laydate,
		table = layui.table,
		common = layui.common;
	
	var goodsDiscountType = 1;   //默认直接降价
	var orderDiscountType = 1;   // 默认绝对值折扣
	var groupDiscountType = 1;   // 默认赠送商品
	
	// 日期控件
    laydate.render({
	  elem: '#createDateText' //指定元素
	});
    
    // 日期控件
    laydate.render({
	  elem: '#expiredDateText' //指定元素
	});
    
 // 日期控件
    laydate.render({
	  elem: '#endDateText' //指定元素
	});
    
	var tableIns = table.render({
		elem: '#discountItems1Tables',
		cols: [
			[
			{
				field: 'goodsDiscountType',
				width: 150,
				title: '降价类型',
				align: 'left',
				templet: '#goodsDiscountTypeTpl'
			},
			{
				field: 'createDateText',
				width: 150,
				title: '创建日期',
				align: 'left',
			},
			{
				field: 'goodsName',
				width: 200,
				title: '商品名称',
				align: 'left',
			},
			{
				field: 'goodsPrice',
				width: 100,
				title: '商品原价',
				align: 'left',
			},
			{
				field: 'goodsDiscountPrice',
				width: 100,
				title: '直降价格',
				align: 'left',
			},
			{
				field: 'goodsDiscountValue',
				width: 100,
				title: '绝对值降价',
				align: 'left',
			},
			{
				field: 'goodsDiscountPercent',
				width: 100,
				title: '折扣百分比',
				align: 'left',
			},
			{
				title: '常用操作',
				width: 100,
				align: 'left',
				toolbar: '#userbar',
				fixed:"right"
			}]

		],
		url: 'discount_items1_data.html',
		page: true,
		even: true
	});
	
	var tableIns2 = table.render({
		elem: '#discountItems2Tables',
		cols: [
			[
			{
				field: 'orderDiscountType',
				width: 150,
				title: '折扣类型',
				align: 'left',
				templet: '#orderDiscountTypeTpl'
			},
			{
				field: 'createDateText',
				width: 150,
				title: '创建日期',
				align: 'left',
			},
			{
				field: 'orderReachValue',
				width: 200,
				title: '整单满额',
				align: 'left',
			},
			{
				field: 'orderDiscountValue',
				width: 100,
				title: '绝对值减元',
				align: 'left',
			},
			{
				field: 'orderDiscountPercent',
				width: 100,
				title: '折扣百分比',
				align: 'left',
			}
			,{
				title: '常用操作',
				width: 100,
				align: 'left',
				toolbar: '#userbar',
				fixed:"right"
			}]

		],
		url: 'discount_items2_data.html',
		page: true,
		even: true
	});
	
	var tableIns5 = table.render({
		elem: '#discountItems5Tables',
		cols: [
			[
			{
				field: 'groupDiscountType',
				width: 150,
				title: '捆绑类型',
				align: 'left',
				templet: '#groupDiscountTypeTpl'
			},
			{
				field: 'createDateText',
				width: 150,
				title: '创建日期',
				align: 'left',
			},
			{
				field: 'groupGoodsCount',
				width: 150,
				title: '捆绑商品个数',
				align: 'left',
			},
			{
				width: 150,
				title: '查看',
				align: 'left',
				templet: '#viewGroupGoodsTpl'
			},
			{
				field: 'goodsName',
				width: 200,
				title: '赠送商品',
				align: 'left',
			},
			{
				field: 'groupOrderDiscountPrice',
				width: 100,
				title: '直接降价',
				align: 'left',
			},
			{
				field: 'groupOrderDiscountValue',
				width: 100,
				title: '绝对值降价',
				align: 'left',
			},
			{
				field: 'groupOrderDiscountPercent',
				width: 100,
				title: '降价百分比',
				align: 'left',
			},
			{
				title: '常用操作',
				width: 100,
				align: 'left',
				toolbar: '#userbar',
				fixed:"right"
			}]

		],
		url: 'discount_items5_data.html',
		page: true,
		even: true
	});
	
	var groupGoodsTableIns = table.render({
		elem: '#viewGroupGoodsTables',
		cols: [
			[
			{
				field: 'goodsCode',
				width: 150,
				title: '商品条码',
				align: 'left',
			},
			{
				field: 'goodsName',
				width: 260,
				title: '商品名称',
				align: 'left',
			}
			,{
				field: 'goodsPrice',
				width: 100,
				title: '销售价(分)',
				align: 'left',
			}
			]
		],
		url: 'query_goods_by_ids.html',
		page: false,
		even: true
	});

	//监听工具条
	table.on('tool(discountItems1Tables)', function(obj) {
		var data = obj.data;
		if (obj.event === 'remove') 
		{
			var discountItemId = data.discountItemId;
			
			layer.confirm('真的移除 当前商品降价信息吗？', function(index) {
				layer.close(index);
				
				var params = {
					'discountItemId' : discountItemId
				};

				$.post('discount_items_delete.html', params, function(json){
					  
					  var oks =  $.parseJSON(json); 
					  
					  if (oks.success == 0)
					  {
						  layer.msg(oks.msg, {icon: 6});
						  tableIns.reload();
					  }
					  else
					  {
						  layer.msg(oks.msg, {icon: 5});
					  }
				  });
			});
		}
	});
	
	table.on('tool(discountItems2Tables)', function(obj) {
		var data = obj.data;
		if (obj.event === 'remove') 
		{
			var discountItemId = data.discountItemId;
			
			layer.confirm('真的移除 当前整额折扣信息吗？', function(index) {
				layer.close(index);
				
				var params = {
					'discountItemId' : discountItemId
				};

				$.post('discount_items_delete.html', params, function(json){
					  
					  var oks =  $.parseJSON(json); 
					  
					  if (oks.success == 0)
					  {
						  layer.msg(oks.msg, {icon: 6});
						  tableIns2.reload();
					  }
					  else
					  {
						  layer.msg(oks.msg, {icon: 5});
					  }
				  });
			});
		}
	});
	
	table.on('tool(discountItems5Tables)', function(obj) {
		var data = obj.data;
		if (obj.event === 'remove') 
		{
			var discountItemId = data.discountItemId;
			
			layer.confirm('真的移除 当前捆绑销售信息吗？', function(index) {
				layer.close(index);
				
				var params = {
					'discountItemId' : discountItemId
				};

				$.post('discount_items_delete.html', params, function(json){
					  
					  var oks =  $.parseJSON(json); 
					  
					  if (oks.success == 0)
					  {
						  layer.msg(oks.msg, {icon: 6});
						  tableIns5.reload();
					  }
					  else
					  {
						  layer.msg(oks.msg, {icon: 5});
					  }
				  });
			});
		}
		else if (obj.event == 'viewGoods')
		{
			$("#viewGroupGoodsPop").removeClass("layui-hide");
			
			groupGoodsTableIns.reload({
				where :{
					'goodsIds' : data.groupGoodsIds
				}
			});
			
			layer.open({
				type:1,
				btn: ['确定'],
				title:'查看参与捆绑销售的商品',
				area: ['600px', '400px'],
				content: $("#viewGroupGoodsPop"),
				yes: function(index, layero){
					layer.close(index);
					$("#viewGroupGoodsPop").addClass("layui-hide");
				},
				cancel:function(index, layero){
					layer.close(index);
					$("#viewGroupGoodsPop").addClass("layui-hide");
				},
			});
		}
	});
	
	/**
	 * 切换商品降价类型
	 */
	form.on('select(goodsDiscountType)', function(data){
		goodsDiscountType = data.value;
	});
	
	form.on('select(selectMemberLevel)', function(data){
	    
		var clientLevelId = data.value;
	    
	    tableIns.reload({
	    	where :{
	    		'clientLevelId':clientLevelId
	    	}
	    });
	});
	
	form.on('submit(memberQuerySubmit)',function(data){

		var datas = data.field;
		
		var clientLevelId = data.clientLevelId;
	    
	    tableIns.reload({
	    	where :{
	    		'clientLevelId':clientLevelId,
	    		'memberCode' : datas.memberCode,
	    		'createDateText': datas.createDateText
	    	}
	    });
		
    	return false;
	});
	
	/**
	 * 查询商品降价数据
	 */
	form.on('submit(goodsDiscountQuerySubmit)',function(data){

		var datas = data.field;
		
		var goodsDiscountType = datas.goodsDiscountType;
	    
	    tableIns.reload({
	    	where :{
	    		'goodsDiscountType':goodsDiscountType
	    	}
	    });
		
    	return false;
	});
	
	/**
	 * 捆绑销售查询
	 */
	form.on('submit(groupDiscountQuerySubmit)',function(data){

		var datas = data.field;
		
		var groupDiscountType = datas.groupDiscountType;
	    
	    tableIns5.reload({
	    	where :{
	    		'groupDiscountType':groupDiscountType
	    	}
	    });
		
    	return false;
	});
	
	/**
	 * 整额折扣查询
	 */
	form.on('submit(orderDiscountQuerySubmit)',function(data){

		var datas = data.field;
		
		var orderDiscountType = datas.orderDiscountType;
	    
	    tableIns2.reload({
	    	where :{
	    		'orderDiscountType':orderDiscountType
	    	}
	    });
		
    	return false;
	});
	
	/**
	 * 改变商品降价类型
	 */
	form.on('radio(goodsDiscountType)',function(data){
		goodsDiscountType = data.value;
		if (data.value == 1)
		{
			$("#goodsDiscountType1").removeClass("layui-hide");
			$("#goodsDiscountType2").addClass("layui-hide");
			$("#goodsDiscountType3").addClass("layui-hide");
		}
		else if (data.value == 2)
		{
			$("#goodsDiscountType1").addClass("layui-hide");
			$("#goodsDiscountType2").removeClass("layui-hide");
			$("#goodsDiscountType3").addClass("layui-hide");
		}
		else if (data.value == 3)
		{
			$("#goodsDiscountType1").addClass("layui-hide");
			$("#goodsDiscountType2").addClass("layui-hide");
			$("#goodsDiscountType3").removeClass("layui-hide");
		}
	});
	
	/**
	 * 改变订单整额折扣类型
	 */
	form.on('radio(orderDiscountType)',function(data){
		orderDiscountType = data.value;
		if (data.value == 1)
		{
			$("#orderDiscountType1").removeClass("layui-hide");
			$("#orderDiscountType2").addClass("layui-hide");
		}
		else if (data.value == 2)
		{
			$("#orderDiscountType1").addClass("layui-hide");
			$("#orderDiscountType2").removeClass("layui-hide");
		}
	});
	
	// 改变捆绑促销类型  
	form.on('radio(groupDiscountType)',function(data){
		groupDiscountType = data.value;
		if (data.value == 1)
		{
			$("#groupDiscountType1").removeClass("layui-hide");
			$("#groupDiscountType2").addClass("layui-hide");
			$("#groupDiscountType3").addClass("layui-hide");
			$("#groupDiscountType4").addClass("layui-hide");
		}
		else if (data.value == 2)
		{
			$("#groupDiscountType2").removeClass("layui-hide");
			$("#groupDiscountType1").addClass("layui-hide");
			$("#groupDiscountType3").addClass("layui-hide");
			$("#groupDiscountType4").addClass("layui-hide");
		}
		else if (data.value == 3)
		{
			$("#groupDiscountType3").removeClass("layui-hide");
			$("#groupDiscountType2").addClass("layui-hide");
			$("#groupDiscountType1").addClass("layui-hide");
			$("#groupDiscountType4").addClass("layui-hide");
		}
		else if (data.value == 4)
		{
			$("#groupDiscountType4").removeClass("layui-hide");
			$("#groupDiscountType2").addClass("layui-hide");
			$("#groupDiscountType3").addClass("layui-hide");
			$("#groupDiscountType1").addClass("layui-hide");
		}
	});
	
	/**
	 * 查询商品信息
	 */
	form.on('submit(queryGoodsInfoSubmit)',function(data){
		
		var datas = data.field;
		var goodsCode = datas.goodsCode;
		var params = {
			'goodsCode' : goodsCode
		};
		if (goodsCode.length > 0)
		{
			$.post('query_goods_info_indiscount.html', params, function(json){
				  var oks =  $.parseJSON(json); 
				  if (oks.code == 0)
				  {
					  $("#goodsName").val(oks.goodsName);
					  $("#goodsPrice").val(oks.goodsPrice);
					  $("#goodsId").val(oks.goodsId);
				  }
				  else
				  {
					  layer.msg("没有检索到商品", {icon: 5});
				  }
			});
		}
		else
		{
			layer.msg("请输入商品条码再检索", {icon: 5});
		}
		
		return false;
	});
	
	/**
	 * 查询商品信息
	 */
	form.on('submit(queryGoodsInfoSubmit5)',function(data){
		
		var datas = data.field;
		var goodsCode = datas.goodsCode;
		var goodsNo = datas.goodsNo;
		var params = {
			'goodsCode' : goodsCode
		};
		if (goodsCode.length > 0)
		{
			$.post('query_goods_info_indiscount.html', params, function(json){
				  var oks =  $.parseJSON(json); 
				  if (oks.code == 0)
				  {
					  $("#goodsName"+goodsNo).val(oks.goodsName);
					  $("#goodsId"+goodsNo).val(oks.goodsId);
				  }
				  else
				  {
					  layer.msg("没有检索到商品", {icon: 5});
				  }
			});
		}
		
		return false;
	});
	
	function clearQueryGoodsInfo()
	{
		$("#goodsInfoForm")[0].reset();
		$("#goodsInfoForm2")[0].reset();
		
		return false;
	}
	
	/**
	 * 新建商品降价
	 * @returns
	 */
	function newGoodsDiscount(index)
	{
		var goodsCode = $("#goodsCode").val();
		var goodsName = $("#goodsName").val();
		var goodsPrice = $("#goodsPrice").val();
		var goodsId = $("#goodsId").val();
		
		// var goodsDiscountType;
		
		// 降价后价格
		var goodsDiscountPrice = $("#goodsDiscountPrice").val();
		
		// 绝对值降价
		var goodsDiscountValue = $("#goodsDiscountValue").val();
		
		// 百分比降价
		var goodsDiscountPercent = $("#goodsDiscountPercent").val();
		
		if (goodsCode.length == 0 || goodsId.length == 0)
		{
			layer.msg("还没有查询到商品信息，请输入商品条码", {icon: 5});
			return;
		}
		
		// 1：直接降价，2：绝对值降价，3：百分比折扣
		if (goodsDiscountType == 1)
		{
			if (goodsDiscountPrice == 0)
			{
				layer.msg("请输入直接降价后的商品价格", {icon: 5});
				return;
			}
		}
		else if (goodsDiscountType == 2)
		{
			if (goodsDiscountValue == 0)
			{
				layer.msg("请输入商品降价的绝对值价格", {icon: 5});
				return;
			}
		}
		else if (goodsDiscountType == 3)
		{
			if (goodsDiscountPercent <= 0 || goodsDiscountPercent > 100)
			{
				layer.msg("请输入商品降价的百分比价格,大于0小于100", {icon: 5});
				return;
			}
		}
		
		// 提交后台
		var params = {
			'goodsCode':goodsCode,
			'goodsName':goodsName,
			'goodsPrice':goodsPrice,
			'goodsId':goodsId,
			'goodsDiscountType':goodsDiscountType,
			'goodsDiscountPrice':goodsDiscountPrice,
			'goodsDiscountValue':goodsDiscountValue,
			'goodsDiscountPercent':goodsDiscountPercent
		};
		
		$.post('save_goods_discount.html', params, function(json){
			  var oks = $.parseJSON(json); 
			  if (oks.success == 0)
			  {
				  $("#goodsDiscountConfirm").addClass("layui-hide");
				  layer.close(index);
				
				  layer.msg(oks.msg, {icon: 6}, function(){
					  
					  tableIns.reload();
				  });
			  }
			  else
			  {
				  layer.msg(oks.msg, {icon: 5});
			  }
		});
	}
	
	/**
	 * 弹出对话框，新建商品降价
	 */
	$("#newGoodsDiscount").click(function(){
		
		clearQueryGoodsInfo();
		
		$("#goodsCode").focus();
		
		$("#goodsDiscountConfirm").removeClass("layui-hide");
		
		layer.open({
			type:1,
			btn: ['新建商品', '取消'],
			title:'新建商品降价折扣 ',
			area: ['600px', '450px'],
			content: $("#goodsDiscountConfirm"),
			yes: function(index, layero){
				newGoodsDiscount(index);
			},
			btn2: function(index, layero){
				layer.close(index);
				$("#goodsDiscountConfirm").addClass("layui-hide");
			},
			cancel:function(index, layero){
				layer.close(index);
				$("#goodsDiscountConfirm").addClass("layui-hide");
			},
		});
	});
	
	// 新建订单整额折扣
	function newOrderDiscount(index)
	{
		// 订单达到的金额
		var orderReachValue = $("#orderReachValue").val();
		
		// 订单折扣绝对值
		var orderDiscountValue = $("#orderDiscountValue").val();
		
		// 订单折扣百分比
		var orderDiscountPercent = $("#orderDiscountPercent").val();
		
		// var orderDiscountType;
		
		if (orderReachValue == 0)
		{
			layer.msg("请输入订单达到的金额", {icon: 5});
			return;
		}
		
		// 1：直接降价，2：绝对值降价，3：百分比折扣
		if (orderDiscountType == 1)
		{
			orderDiscountPercent = 0;
			if (orderDiscountValue == 0)
			{
				layer.msg("请输入绝对值折扣的数值", {icon: 5});
				return;
			}
		}
		else if (orderDiscountType == 2)
		{
			orderDiscountValue = 0;
			if (orderDiscountPercent == 0)
			{
				layer.msg("请输入百分比折扣系数，大于0且小于100", {icon: 5});
				return;
			}
		}
		
		// 提交后台
		var params = {
			'orderReachValue':orderReachValue,
			'orderDiscountValue':orderDiscountValue,
			'orderDiscountPercent':orderDiscountPercent,
			'orderDiscountType':orderDiscountType
		};
		
		$.post('save_order_discount.html', params, function(json){
			  var oks = $.parseJSON(json); 
			  if (oks.success == 0)
			  {
				  $("#orderDiscountConfirm").addClass("layui-hide");
				  layer.close(index);
				
				  layer.msg(oks.msg, {icon: 6}, function(){
					  
					  tableIns2.reload();
				  });
			  }
			  else
			  {
				  layer.msg(oks.msg, {icon: 5});
			  }
		});
	}
	
	/**
	 * 弹出对话框，新建商品降价
	 */
	$("#newOrderDiscount").click(function(){
		
		$("#orderReachValue").val(0);
		$("#orderDiscountValue").val(0);
		$("#orderDiscountPercent").val(0);
		
		$("#orderDiscountConfirm").removeClass("layui-hide");
		
		layer.open({
			type:1,
			btn: ['保存', '取消'],
			title:'新建商品整额折扣 ',
			area: ['600px', '400px'],
			content: $("#orderDiscountConfirm"),
			yes: function(index, layero){
				newOrderDiscount(index);
			},
			btn2: function(index, layero){
				layer.close(index);
				$("#orderDiscountConfirm").addClass("layui-hide");
			},
			cancel:function(index, layero){
				layer.close(index);
				$("#orderDiscountConfirm").addClass("layui-hide");
			},
		});
	});
	
	function newGroupDiscount(index)
	{
		var groupGoodsIds = "";
		// 检查所有捆绑商品
		for (i = 1; i <= 8; i++)
		{
			goodsId = $("#goodsId" + i).val();
			goodsName = $("#goodsName" + i).val();
			if (goodsName.length > 0)
			{
				groupGoodsIds += goodsId + ","
			}
		}
		
		if (groupGoodsIds.length == 0)
		{
			layer.msg("您还没有设置有效的商品", {icon: 5});
			return;
		}
		
		var groupOrderDiscountPrice = $("#groupOrderDiscountPrice").val();
		var groupOrderDiscountValue = $("#groupOrderDiscountValue").val();
		var groupOrderDiscountPercent = $("#groupOrderDiscountPercent").val();
		var groupPresentGoodsId = $("#goodsId0").val();
		
		// 捆绑促销类型，1：捆绑赠商品，2：捆绑降价，3：捆绑绝对值降价，4：捆绑百分比折扣
		if (groupDiscountType == 1)
		{
			groupOrderDiscountPrice = 0;
			groupOrderDiscountValue = 0;
			groupOrderDiscountPercent = 0;
			
			if (groupPresentGoodsId.length == 0)
			{
				layer.msg("您还没有设置赠送的商品", {icon: 5});
				return;
			}
		}
		else if (groupDiscountType == 2)
		{
			groupPresentGoodsId = " ";
			groupOrderDiscountValue = 0;
			groupOrderDiscountPercent = 0;
			
			if (groupOrderDiscountPrice == 0)
			{
				layer.msg("您还没有设置直接降价金额", {icon: 5});
				return;
			}
		}
		else if (groupDiscountType == 3)
		{
			groupPresentGoodsId = " ";
			groupOrderDiscountPercent = 0;
			groupOrderDiscountPrice = 0;
			
			if (groupOrderDiscountValue == 0)
			{
				layer.msg("您还没有设置绝对值降价金额", {icon: 5});
				return;
			}
		}
		else if (groupDiscountType == 4)
		{
			groupPresentGoodsId = " ";
			groupOrderDiscountPrice = 0;
			groupOrderDiscountValue = 0;
			
			if (groupOrderDiscountPercent <= 0 || groupOrderDiscountPercent > 100)
			{
				layer.msg("您还没有设置降价百分比系数，大于0且小于100", {icon: 5});
				return;
			}
		}
		
		// 提交后台
		var params = {
			'groupDiscountType':groupDiscountType,
			'groupPresentGoodsId':groupPresentGoodsId,
			'groupOrderDiscountPrice':groupOrderDiscountPrice,
			'groupOrderDiscountValue':groupOrderDiscountValue,
			'groupOrderDiscountPercent':groupOrderDiscountPercent,
			'groupGoodsIds':groupGoodsIds
		};
		
		$.post('save_group_discount.html', params, function(json){
			  var oks = $.parseJSON(json); 
			  if (oks.success == 0)
			  {
				  $("#groupDiscountConfirm").addClass("layui-hide");
				  layer.close(index);
				
				  layer.msg(oks.msg, {icon: 6}, function(){
					  tableIns5.reload();
				  });
			  }
			  else
			  {
				  layer.msg(oks.msg, {icon: 5});
			  }
		});
	}
	
	
	// 弹出新建捆绑对话框
	$("#newGroupDiscount").click(function(){
		
		$("#goodsInfoForm0")[0].reset();
		$("#goodsInfoForm1")[0].reset();
		$("#goodsInfoForm2")[0].reset();
		$("#goodsInfoForm3")[0].reset();
		$("#goodsInfoForm4")[0].reset();
		$("#goodsInfoForm5")[0].reset();
		$("#goodsInfoForm6")[0].reset();
		$("#goodsInfoForm7")[0].reset();
		$("#goodsInfoForm8")[0].reset();
		
		$("#groupDiscountConfirm").removeClass("layui-hide");
		
		layer.open({
			type:1,
			btn: ['保存', '取消'],
			title:'新建商品捆绑销售策略 ',
			area: ['800px', '700px'],
			content: $("#groupDiscountConfirm"),
			yes: function(index, layero){
				newGroupDiscount(index);
			},
			btn2: function(index, layero){
				layer.close(index);
				$("#groupDiscountConfirm").addClass("layui-hide");
			},
			cancel:function(index, layero){
				layer.close(index);
				$("#groupDiscountConfirm").addClass("layui-hide");
			},
		});
	});
	
	// 这个放在form事件的后面！
	form.verify({
		memberCode: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '会员手机号不能为空';
	    	}
		},
		goodsCode: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '商品条码不能为空';
	    	}
		}
		,memberName:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '会员姓名不能为空';
	    	}
		},
		createDateText:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '卡券生效日期不能为空';
	    	}
		},
		expiredDateText:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '卡券失效日期不能为空';
	    	}
		}
		,totalCount:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value == 0)
	    	{
	    		return '请设置生成卡券的数量，数量必须大于0';
	    	}
		}
	});
});