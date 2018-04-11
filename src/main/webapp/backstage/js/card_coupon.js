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
	  elem: '#expiredDateText' //指定元素
	});
    
	var tableIns = table.render({
		elem: '#cardCouponsTables',
		cols: [
			[
			{
				field: 'levelName',
				width: 150,
				title: '针对会员等级',
				align: 'left',
			},
			{
				field: 'couponAmount',
				width: 80,
				title: '面额',
				align: 'left',
			},
			{
				field: 'totalCount',
				width: 100,
				title: '颁发数量',
				align: 'left',
			},
			{
				field: 'enabledCount',
				width: 100,
				title: '激活数量',
				align: 'left',
			},
			{
				field: 'unenabledCount',
				width: 100,
				title: '未激活数量',
				align: 'left',
			},
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
				field: 'validStatus',
				width: 100,
				title: '有效状态',
				align: 'left',
				templet: '#validStatusTpl'
			},
			{
				field: 'couponTitle',
				width: 200,
				title: '简要描述',
				align: 'left'
			},
			{
				title: '常用操作',
				width: 200,
				align: 'center',
				toolbar: '#userbar',
				fixed:"right"
			}]

		],
		url: 'card_coupons_data.html',
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
	
	form.on('submit(cardCouponInfoSubmit)',function(data){

		var datas = data.field;
		
	    $("#cardCouponInfoSubmit").addClass("layui-btn-disabled");
		
		$.post('card_coupons_save_data.html', datas, function(json){
		  var oks =  $.parseJSON(json); 
		  
		  if (oks.success == 1)
		  {
			  // 更新失败
			  layer.msg(oks.msg, {icon: 5});
			  
			  $("#cardCouponInfoSubmit").removeClass("layui-btn-disabled");
		  }
		  else if (oks.success == 0)
		  {
			  layer.msg(oks.msg, {icon: 6}, function(){
				  window.location.href = 'card_coupons.html';
			  });
		  }
	  });
		
    	return false;
	});
	
	// 这个放在form事件的后面！
	form.verify({
		memberCode: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '会员手机号不能为空';
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