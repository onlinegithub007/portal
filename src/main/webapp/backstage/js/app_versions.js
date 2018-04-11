layui.use(['layer', 'form', 'upload', 'table', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		common = layui.common,
		upload = layui.upload;

	var tableIns = table.render({
		elem: '#appVersionTables',
		cols: [
			[
			{
				field: 'appName',
				width: 200,
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
				field: 'version',
				width: 80,
				title: '版本号',
				align: 'left',
			}, 
			{
				field: 'appSize',
				width: 150,
				title: '文件大小',
				align: 'left',
			},
			{
				field: 'upgradeOptionDesc',
				width: 100,
				title: '强制升级',
				align: 'center',
			},
			{
				field: 'createDateText',
				width: 180,
				title: '发布日期',
				align: 'center',
			},
			{
				field: 'fileName',
				width: 200,
				title: '文件名',
				align: 'left',
			},
			{
				title: '常用操作',
				width: 100,
				align: 'center',
				toolbar: '#appversionbar',
				fixed:"right"
			}]

		],
		url: 'app_version_datas.html',
		page: false,
		even: true
	});

	//执行实例
	var uploadInst = upload.render({
	    elem: '#appFileUpload' //绑定元素
	    ,url: 'apps_version_upload.html' //上传接口
	    ,data:{
	    	
	    }
		,accept:'file'
		,exts:'apk'
		,field:'fileName'
		,size:51200
	    ,done: function(res){
	      //上传完毕回调
	      var fileName = res.msg;
	      $("#uploadLabel").text(fileName);
	    }
	    ,error: function(){
	    	layer.msg('上传文件失败', {icon: 5});
	    }
	});
	
	//监听工具条
	table.on('tool(appVersionTables)', function(obj) 
	{
		var data = obj.data;
		
		if (obj.event === 'edit') 
		{
			var softId = data.softId;
			window.location.href = 'apps_info.html?softId=' + softId;
		}
		else if (obj.event === 'del') {
			var appVersionId = {
				'appVersionId' : data.appVersionId
			};
			
			layer.confirm('真的删除 当前App软件 ['+data.appName+' '+data.version+'] 版本吗？', function(index) {

				$.post('delete_app_version.html', appVersionId, function(json){
					  
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
	
	form.on('submit(appsoftVersionSubmit)',function(data){

		var datas = data.field;
		
		$.post('save_app_version.html', datas, function(json){
			  var oks =  $.parseJSON(json); 
			  
			  if (oks.success == 1)
			  {
				  // 更新失败
				  layer.msg(oks.msg, {icon: 5});
			  }
			  else if (oks.success == 0)
			  {
				  window.location.href = 'app_versions.html';
			  }
		  });
		
    	return false;
	});
	
	form.on('select(software)', function(data){
	    
		var softId = data.value;
	    
	    tableIns.reload({
	    	where :{
	    		'softId':softId
	    	}
	    });
	});
	
	// 这个放在form事件的后面！
	form.verify({
		appName: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '软件名称不能为空';
	    	}
		},
		appVersion: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '软件版本号不能为空';
	    	}
		},
		appDesc: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '软件升级描述不能为空';
	    	}
		}
	});
});