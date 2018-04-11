layui.use(['layer', 'form', 'table', 'laydate', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		laydate = layui.laydate,
		table = layui.table,
		common = layui.common;

	// 日期控件
    laydate.render({
	  elem: '#createDateText' //指定元素
	});
    
 // 日期控件
    laydate.render({
	  elem: '#createDateText2' //指定元素
		  ,type:'datetime'
	});
    
    // 日期控件
    laydate.render({
	  elem: '#expiredDateText' //指定元素
	});
    
    // 日期控件
    laydate.render({
	  elem: '#endDateText' //指定元素
		  ,type:'datetime'
	});
    
	var tableIns = table.render({
		elem: '#discount1Tables',
		cols: [
			[
			{
				field: 'discountTypeName',
				width: 200,
				title: '促销主题',
				align: 'left',
			},
			{
				field: 'discountUserType',
				width: 100,
				title: '作用范围',
				align: 'left',
				templet: '#discountUserTypeTpl'
			},
			{
				field: 'levelName',
				width: 150,
				title: '针对会员等级',
				align: 'left',
			},
			{
				field: 'discountItemSatus',
				width: 100,
				title: '活动状态',
				align: 'left',
				templet: '#discountItemStatusTpl'
			},
			{
				field: 'createDateText',
				width: 200,
				title: '开始时间',
				align: 'left',
			},
			{
				field: 'endDateText',
				width: 200,
				title: '到期时间',
				align: 'left',
			},
			{
				title: '常用操作',
				width: 300,
				align: 'left',
				toolbar: '#userbar',
				fixed:"right"
			}]

		],
		url: 'discounts_data.html',
		page: true,
		even: true
	});
	
	var tableInsMembCard = table.render({
		elem: '#memCardCouponsTables',
		cols: [
			[
			{
				field: 'createDateText',
				width: 130,
				title: '颁发日期',
				align: 'left',
			},
			{
				field: 'expiredDateText',
				width: 130,
				title: '到期日期',
				align: 'left',
			},
			{
				field: 'couponAmount',
				width: 80,
				title: '面额',
				align: 'left',
			},
			{
				field: 'couponBalance',
				width: 80,
				title: '余额',
				align: 'left',
			},
			{
				field: 'enableDateText',
				width: 200,
				title: '激活时间',
				align: 'left',
			},
			{
				field: 'finalStatus',
				width: 100,
				title: '状态',
				align: 'left',
				templet: '#finalStatusTpl'
			}
			]
		],
		url: 'member_card_coupons_data.html',
		page: true,
		even: true
	});

	//监听工具条
	table.on('tool(discount1Tables)', function(obj) {
		var data = obj.data;
		if (obj.event === 'enable') 
		{
			var discountId = data.discountId;
			
			layer.confirm('真的激活 当前促销信息吗？', function(index) {
				layer.close(index);
				
				var params = {
					'discountId' : discountId,
					'discountItemStatus' : 1
				};

				$.post('change_discount_status.html', params, function(json){
					  
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
		else if (obj.event === 'disable') 
		{
			var discountId = data.discountId;
			
			layer.confirm('真的禁用 当前促销信息吗？', function(index) {
				layer.close(index);
				
				var params = {
					'discountId' : discountId,
					'discountItemStatus' : 0
				};

				$.post('change_discount_status.html', params, function(json){
					  
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
		else if (obj.event === 'del') {
			layer.confirm('真的删除 当前促销信息吗？', function(index) {

				var params = {
					'discountId' : data.discountId
				};
				
				$.post('delete_discounts.html', params, function(json){
					  
					  var oks =  $.parseJSON(json); 
					  
					  if (oks.success == 0)
					  {
						  obj.del();
						  layer.close(index);
					  }
					  else
					  {
						  layer.close(index);
						  layer.msg(oks.msg, {icon: 5});
					  }
				  });
			});
		}
		else if (obj.event == 'view') 
		{
			var discountId = data.discountId;
			var discountType = data.discountType;
			window.location.href="discount_items.html?discountId=" + discountId + "&discountType=" + discountType;
		}
		else if (obj.event == 'stores') 
		{
			var discountId = data.discountId;
			window.location.href="discount_stores.html?discountId=" + discountId;
		};
	});
	
	
	//监听工具条
	table.on('tool(cardCouponsTables)', function(obj) {
		var data = obj.data;
		if (obj.event === 'enable') 
		{
			var cardCouponId = data.cardCouponId;
			
			layer.confirm('真的激活 当前卡券信息吗？', function(index) {
				layer.close(index);
				
				var params = {
					'cardCouponId' : cardCouponId,
					'validStatus' : 1
				};

				$.post('card_coupons_change_valid_status.html', params, function(json){
					  
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
		else if (obj.event === 'disable') 
		{
			var cardCouponId = data.cardCouponId;
			
			layer.confirm('真的禁用 当前卡券信息吗？', function(index) {
				layer.close(index);
				
				var params = {
					'cardCouponId' : cardCouponId,
					'validStatus' : 0
				};

				$.post('card_coupons_change_valid_status.html', params, function(json){
					  
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
		else if (obj.event === 'del') {
			layer.confirm('真的删除 当前卡券信息吗？', function(index) {

				var params = {
					'cardCouponId' : data.cardCouponId
				};
				
				$.post('card_coupons_delete_data.html', params, function(json){
					  
					  var oks =  $.parseJSON(json); 
					  
					  if (oks.success == 0)
					  {
						  obj.del();
						  layer.close(index);
					  }
					  else
					  {
						  layer.close(index);
						  layer.msg(oks.msg, {icon: 5});
					  }
				  });
			});
		}
		else if (obj.event == 'view') 
		{
			var cardCouponId = data.cardCouponId;
			window.location.href="member_card_coupons.html?cardCouponId=" + cardCouponId;
		};
	});
	
	form.on('select(selectMemberLevel)', function(data){
	    
		var clientLevelId = data.value;
	    
	    tableIns.reload({
	    	where :{
	    		'clientLevelId':clientLevelId
	    	}
	    });
	});
	
	form.on('submit(discountQuerySubmit)', function(data){

		var datas = data.field;
	    
	    tableIns.reload({
	    	where :{
	    		'memberLevelId': datas.memberLevelId,
	    		'discountItemStatus' : datas.discountItemStatus,
	    		'createDateText': datas.createDateText,
	    	}
	    });
		
    	return false;
	});
	
	form.on('submit(memberInfoSubmit)',function(data){

		var datas = data.field;
		
	    $("#memberInfoSubmit").addClass("layui-btn-disabled");
		
		$.post('members_save_data.html', datas, function(json){
		  var oks =  $.parseJSON(json); 
		  
		  if (oks.success == 1)
		  {
			  // 更新失败
			  layer.msg(oks.msg, {icon: 5});
			  
			  $("#memberInfoSubmit").removeClass("layui-btn-disabled");
		  }
		  else if (oks.success == 0)
		  {
			  layer.msg(oks.msg, {icon: 6}, function(){
				  window.location.href = 'members.html';
			  });
		  }
	  });
		
    	return false;
	});
	
	form.on('submit(saveDiscountSubmit)',function(data){

		var datas = data.field;
		var discountType = datas.discountType;
		
	    $("#saveDiscountSubmit").addClass("layui-btn-disabled");
		
		$.post('discounts1_save_info.html', datas, function(json){
		  var oks =  $.parseJSON(json); 
		  
		  if (oks.success == 1)
		  {
			  // 更新失败
			  layer.msg(oks.msg, {icon: 5});
			  
			  $("#saveDiscountSubmit").removeClass("layui-btn-disabled");
		  }
		  else if (oks.success == 0)
		  {
			  layer.msg(oks.msg, {icon: 6}, function(){
				  window.location.href = 'discounts.html?discountType='+discountType;
			  });
		  }
	  });
		
    	return false;
	});
	
	form.on('radio(goodsDiscountType)',function(data){
		
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
	
	$("#queryGoodsInfo").click(function(){
		
		var goodsCode = $("#goodsCode").val();
		var params = {
			'goodsCode' : goodsCode
		};
		if (goodsCode.length > 0)
		{
			$.post('query_goods_info_indiscount.html', params, function(json){
				  var oks =  $.parseJSON(json); 
				  
				  $("#goodsName").val(oks.goodsName);
				  $("#goodsPrice").val(oks.goodsPrice);
			});
		}
	});
	
	// 这个放在form事件的后面！
	form.verify({
		discountTypeName: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '优惠促销活动名称不能为空';
	    	}
		},
		createDateText:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '生效日期不能为空';
	    	}
		},
		endDateText:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '失效日期不能为空';
	    	}
		}
	});
});