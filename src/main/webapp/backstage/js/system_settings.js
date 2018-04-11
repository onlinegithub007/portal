layui.use(['layer', 'form', 'table', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		common = layui.common;

	var tableIns = table.render({
		elem: '#settingsTables',
		cols: [
			[
			{
				field: 'dicCode',
				width: 200,
				title: '参数代码',
				align: 'left',
			},
			{
				field: 'dicValues',
				width: 500,
				title: '参数值',
				align: 'left',
			},
			{
				title: '常用操作',
				width: 100,
				align: 'center',
				toolbar: '#settingbar',
				fixed:"right"
			}]

		],
		url: 'system_settings_data.html',
		page: false,
		even: true
	});

	//监听工具条
	table.on('tool(settingsTables)', function(obj) {
		var data = obj.data;
		if (obj.event === 'edit') 
		{
			var dicCode = data.dicCode;
			window.location.href = 'system_settings_info.html?dicCode=' + dicCode;
		}
	});
	
	form.on('submit(settingsSubmit)',function(data){

		var datas = data.field;
		
		$.post('save_settings.html', datas, function(json){
			  var oks =  $.parseJSON(json); 
			  
			  if (oks.success == 1 || oks.success == -1)
			  {
				  // 更新失败
				  layer.msg(oks.msg, {icon: 5});
			  }
			  else if (oks.success == 0)
			  {
				  window.location.href = 'system_settings.html';
			  }
		  });
		
    	return false;
	});
	
	// 这个放在form事件的后面！
	form.verify({
		dicValues: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '系统参数值不能为空';
	    	}
		}
	});
});