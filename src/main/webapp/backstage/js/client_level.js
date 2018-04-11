layui.use(['layer', 'form', 'table', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		common = layui.common;

	var tableIns = table.render({
		elem: '#clientLevelTables',
		cols: [
			[ {
				field: 'levelName',
				width: 400,
				title: '会员级别描述',
				align: 'left',
			},
			{
				field: 'levelValue',
				width: 300,
				title: '会员级别数字描述',
				align: 'left',
			},
			{
				title: '常用操作',
				width: 150,
				align: 'center',
				toolbar: '#clientLevelbar',
				fixed:"right"
			}]

		],
		url: 'client_level_data.html',
		page: false,
		even: true
	});

	//监听工具条
	table.on('tool(clientLevelTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event === 'edit') 
		{
			var clientLevelId = data.clientLevelId;
			window.location.href = 'client_level_info.html?clientLevelId=' + clientLevelId;
		}
		else if (obj.event === 'price') 
		{
			var clientLevelId = data.clientLevelId;
			window.location.href = 'members_goods_price.html?clientLevelId=' + clientLevelId;
		}
		else if (obj.event === 'del') {
			layer.confirm('真的删除 客户等级 ['+data.levelName+'] 信息吗？', function(index) {

				$.post('delete_client_level.html', data, function(json){
					  
					  var oks =  $.parseJSON(json); 
					  
					  if (oks.success == 0)
					  {
						  obj.del();
						  layer.close(index);
					  }
				  });
			});
		}
	});
	
	form.on('submit(clientLevelSubmit)',function(data){

		var datas = data.field;
		
		$.post('save_client_level.html', datas, function(json){
			  var oks =  $.parseJSON(json); 
			  
			  if (oks.success == 1)
			  {
				  // 更新失败
				  layer.msg(oks.msg, {icon: 5});
			  }
			  else if (oks.success == 0)
			  {
				  window.location.href = 'member_client_level.html';
			  }
		  });
		
    	return false;
	});
	
	// 这个放在form事件的后面！
	form.verify({
		levelName: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '客户级别描述不能为空';
	    	}
		},
		levelValue:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '会员级别数字描述不能为空';
	    	}
		}
	}); 

});