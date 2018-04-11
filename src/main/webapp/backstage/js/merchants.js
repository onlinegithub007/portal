layui.use(['layer', 'form', 'jquery', 'table', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		common = layui.common;

	var tableIns = table.render({
		elem: '#merchantTables',
		cols: [
			[
			{
				field: 'merName',
				width: 150,
				title: '商户名称(简称)',
				align: 'left',
			},
			{
				field: 'merCode',
				width: 100,
				title: '商户编码',
				align: 'center',
			}, 
			{
				field: 'merStatusText',
				width: 100,
				title: '状态',
				align: 'center',
			},
			{
				field: 'linkMan',
				width: 100,
				title: '联系人',
				align: 'center',
			}, {
				field: 'linkManPhone',
				width: 120,
				title: '联系电话',
				align: 'left',
			},
			{
				field: 'linkManEmail',
				width: 200,
				title: '联系人邮件',
				align: 'left',
			},{
				field: 'createDateText',
				width: 160,
				title: '录入时间',
				align: 'center',
			}, {
				field: 'creator',
				width: 100,
				title: '录入人',
				align: 'center',
			},
			{
				field: 'areaFullName',
				width: 200,
				title: '所在地区',
				align: 'left',
			}, 
			{
				field: 'trade',
				width: 150,
				title: '所属行业',
				align: 'left',
			}, 
			{
				title: '常用操作',
				width: 220,
				align: 'center',
				toolbar: '#merchantbar',
				fixed:"right"
			}]

		],
		url: 'merchant_data.html',
		page: true,
		even: true
	});

	//监听工具条
	table.on('tool(merchantTables)', function(obj) 
	{
		var data = obj.data;
		var merId = data.merId;
		
		if (obj.event === 'edit') 
		{
			window.location.href = 'merchant_info.html?merId=' + merId;
		}
		else if (obj.event === 'online') 
		{
			var param = {
				'merId' : merId,
				'merStatus' : 0
			};
			
			layer.confirm('确认将当前商户 ['+data.merName+'] 上线吗？', function(index) {

				$.post('change_merchant.html', param, function(json){
					  
					  var oks =  $.parseJSON(json); 
					  
					  if (oks.success == 0)
					  {
						  layer.close(index);
						  window.location.reload();
					  }
				  });
			});
		}
		else if (obj.event === 'offline')
		{
			var param = {
				'merId' : merId,
				'merStatus' : 1
			};
			
			layer.confirm('确认将当前商户 ['+data.merName+'] 下线吗？', function(index) {

				$.post('change_merchant.html', param, function(json){
					  
					  var oks =  $.parseJSON(json); 
					  
					  if (oks.success == 0)
					  {
						  layer.close(index);
						  window.location.reload();
					  }
				  });
			});
		}
		else if (obj.event === 'del')
		{
			layer.confirm('真的删除 当前商户 ['+data.merName+'] 信息吗？', function(index) {

				$.post('del_merchant.html', data, function(json){
					  
					  var oks =  $.parseJSON(json); 
					  
					  if (oks.success == 0)
					  {
						  layer.close(index);
						  window.location.reload();
					  }
				  });
			});
		}
	});
	
	form.on('select(selectProductRole)', function(data){
	    
		var productRoleId = data.value;
	    
	    tableIns.reload({
	    	where :{
	    		'productRoleId':productRoleId
	    	}
	    });
	});
	
	form.on('select(province)', function(data){
	    
		var parentAreaId = data.value;
	    var params = {'parentAreaId' : parentAreaId};
	    
		$.post('areas.html', params, function(data){
			var result = $.parseJSON(data); 
			
			if(result.count > 0)
			{
				var cityHtml = "";
				var jsonData = eval(result.data);
				for (var i = 0; i < jsonData.length; i++)
	    		{
					cityHtml += "<option value='" + jsonData[i].areaId + "'>" + jsonData[i].areaName + "</option>";
	    		}
				
				$("#selectCity").html(cityHtml);
				$("#selectAreas").html("");
				form.render('select');
			}
			else
			{
				$("#selectCity").html("");
				$("#selectAreas").html("");
				form.render('select');
			}
		});
	});
	
	form.on('select(city)', function(data){
	    
		var parentAreaId = data.value;
	    var params = {'parentAreaId' : parentAreaId};
	    
		$.post('areas.html', params, function(data){
			var result = $.parseJSON(data); 
			
			if(result.count > 0)
			{
				var areaHtml = "";
				var jsonData = eval(result.data);
				for (var i = 0; i < jsonData.length; i++)
	    		{
					areaHtml += "<option value='" + jsonData[i].areaId + "'>" + jsonData[i].areaName + "</option>";
	    		}
				
				$("#selectAreas").html(areaHtml);
				form.render('select');
			}
			else
			{
				$("#selectAreas").html("");
				form.render('select');
			}
		});
	});
	
	form.on('checkbox(productRole)', function(data){
		
		var productRoles = document.getElementsByName("productRoleId");
		var productRoleIds = "";
		
		for(i = 0; i < productRoles.length; i++)
		{
			if (productRoles[i].checked)
			{
				productRoleIds += productRoles[i].value + ",";
			}
		}
		
		$("#productRoleIds").val(productRoleIds);
	});
	
	form.on('submit(merchantsSubmit)',function(data){

		$("#merchantsSubmit").addClass("layui-btn-disabled");
		
		var datas = data.field;
		
		$.post('save_merchant.html', datas, function(json){
			  var oks =  $.parseJSON(json); 
			  
			  if (oks.success == 1)
			  {
				  // 更新失败
				  layer.msg(oks.msg, {icon: 5});
			  }
			  else if (oks.success == 0)
			  {
				  window.location.href = 'merchants.html';
			  }
		  });
		
    	return false;
	});
	
	form.on('checkbox(getAdminAccount)', function(data){
		if(data.elem.checked)
		{
			var adminAccount = $("#adminAccount").val();
			var merName = $("#merName").val();
			
			if (adminAccount.length == 0)
			{
				if (merName.length > 0)
				{
					var datas = {
						'merName' : merName
					};
					
					$.post('merchant_admin_account.html', datas, function(json){
						  var oks =  $.parseJSON(json); 
						  
						  if (oks.success == 0)
						  {
							  $("#adminAccount").val(oks.msg);
						  }
					  });
				}
			}
		}
	});
	
	// 这个放在form事件的后面！
	form.verify({
		merFullName:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '商户名称全称不能为空';
	    	}
		}
		,merName: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '商户名称简称不能为空';
	    	}
		    
		    if(value.length > 5)
	    	{
	    		return '商户名称简称长度不能大于5个字符';
	    	}
		}
		,linkMan:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '请输入联系人姓名';
	    	}
		}
		,linkManPhone:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '请输入联系人姓名手机号';
	    	}
		}
		,adminAccount:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '管理员缺省账号不能为空，或可采用系统自动生成账号';
	    	}
		}
	});
	
});

