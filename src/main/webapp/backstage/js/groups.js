layui.use(['layer', 'form', 'table', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		common = layui.common;

	var tableIns = table.render({
		elem: '#groupsTables',
		cols: [
			[ {
				field: 'groupName',
				width: 200,
				title: '部门名称',
				align: 'left',
			},
			{
				field: 'groupDesc',
				width: 400,
				title: '部门描述',
				align: 'left',
			}
			, {
				title: '常用操作',
				width: 200,
				align: 'center',
				toolbar: '#groupsbar',
				fixed:"right"
			}]

		],
		url: 'merchant_groups_data.html',
		page: false,
		even: true
	});

	//监听工具条
	table.on('tool(groupsTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event === 'edit') 
		{
			var merGroupId = data.merGroupId;
			window.location.href = 'merchant_groups_info.html?merGroupId=' + merGroupId;
		}
		else if (obj.event === 'del') {
			layer.confirm('真的删除 当前部门 ['+data.groupName+'] 信息吗？', function(index) {

				$.post('delete_merchant_groups.html', data, function(json){
					  
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
	
	form.on('submit(groupSubmit)',function(data){

		var datas = data.field;
		
		$.post('save_merchant_group.html', datas, function(json){
			  var oks =  $.parseJSON(json); 
			  
			  if (oks.success == 1)
			  {
				  // 更新失败
				  layer.msg(oks.msg, {icon: 5});
			  }
			  else if (oks.success == 0)
			  {
				  window.location.href = 'merchant_groups.html';
			  }
		  });
		
    	return false;
	});
	
	// 这个放在form事件的后面！
	form.verify({
		groupName: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '部门姓名不能为空';
	    	}
		}
	}); 

});