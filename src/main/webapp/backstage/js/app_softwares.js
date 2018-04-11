layui.use(['layer', 'form', 'table', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		common = layui.common;

	var tableIns = table.render({
		elem: '#appTables',
		cols: [
			[
			{
				field: 'softId',
				width: 50,
				title: 'ID',
				align: 'left',
			},
			{
				field: 'appName',
				width: 250,
				title: 'App应用名称',
				align: 'left',
			},
			{
				field: 'os',
				width: 150,
				title: '操作系统',
				align: 'left',
			}, 
			{
				field: 'createDateText',
				width: 150,
				title: '创建时间',
				align: 'left',
			}, {
				field: 'downloadCount',
				width: 100,
				title: '下载次数',
				align: 'center',
			},
			{
				title: '常用操作',
				width: 150,
				align: 'center',
				toolbar: '#appbar',
				fixed:"right"
			}]

		],
		url: 'app_datas.html',
		page: false,
		even: true
	});

	//监听工具条
	table.on('tool(appTables)', function(obj) 
	{
		var data = obj.data;
		
		if (obj.event === 'edit') 
		{
			var softId = data.softId;
			window.location.href = 'apps_info.html?softId=' + softId;
		}
		else if (obj.event === 'del') {
			var softId = {
				'softId' : data.softId
			};
			
			layer.confirm('真的删除 当前App软件 ['+data.appName+'] 信息吗？', function(index) {

				$.post('delete_app.html', softId, function(json){
					  
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
	
	form.on('submit(appsoftwareSubmit)',function(data){

		var datas = data.field;
		
		$.post('save_apps.html', datas, function(json)
		{
			  var oks =  $.parseJSON(json); 
			  
			  if (oks.success == 1)
			  {
				  // 更新失败
				  layer.msg(oks.msg, {icon: 5});
			  }
			  else if (oks.success == 0)
			  {
				  window.location.href = 'app_softwares.html';
			  }
		  });
		
    	return false;
	});
	
	// 这个放在form事件的后面！
	form.verify({
		appName: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '软件名称不能为空';
	    	}
		}
	});
});