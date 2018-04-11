layui.use(['layer', 'form', 'table', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		common = layui.common;

	var tableIns = table.render({
		elem: '#membersTables',
		cols: [
			[
			{
				field: 'memberCode',
				width: 120,
				title: '手机号',
				align: 'left',
			},
			{
				field: 'memberName',
				width: 100,
				title: '会员姓名',
				align: 'left',
			},
			{
				field: 'nickName',
				width: 100,
				title: '会员昵称',
				align: 'left',
			}, 
			{
				field: 'gender',
				width: 60,
				title: '性别',
				align: 'left',
				templet: '#sexTpl'
			}, {
				field: 'mobileSp',
				width: 120,
				title: '运营商',
				align: 'left',
			}, {
				field: 'createDateText',
				width: 120,
				title: '注册日期',
				align: 'left',
			}, {
				field: 'groupName',
				width: 240,
				title: '关注门店',
				align: 'left',
			}, 
			{
				field: 'memberScore',
				width: 80,
				title: '积分',
				align: 'left',
			},
			{
				field: 'memberBalanceText',
				width: 100,
				title: '余额',
				align: 'left',
			},
			{
				field: 'attentionDateText',
				width: 120,
				title: '关注日期',
				align: 'left',
			}, {
				title: '常用操作',
				width: 160,
				align: 'center',
				toolbar: '#userbar',
				fixed:"right"
			}]

		],
		url: 'members_data.html',
		page: true,
		even: true
	});

	//监听工具条
	table.on('tool(membersTables)', function(obj) {
		var data = obj.data;
		if (obj.event === 'edit') 
		{
			var memberId = data.memberId;
			window.location.href = 'member_info.html?memberId=' + memberId;
		}
		else if (obj.event === 'del') {
			layer.confirm('真的删除 当前会员 ['+data.memberName+'] 信息吗？', function(index) {

				var params = {
					'memberId' : data.memberId
				};
				
				$.post('members_delete_data.html', params, function(json){
					  
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
	    		'memberCode' : datas.memberCode
	    	}
	    });
		
    	return false;
	});
	
	form.on('submit(memberInfoSubmit)',function(data){

		var datas = data.field;
		
	    $("#memberInfoSubmit").addClass("layui-disabled");
		
		$.post('members_save_data.html', datas, function(json){
		  var oks =  $.parseJSON(json); 
		  
		  if (oks.success == 1)
		  {
			  // 更新失败
			  layer.msg(oks.msg, {icon: 5});
			  
			  $("#memberInfoSubmit").removeClass("layui-disabled");
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
		}
	});
});