layui.use(['layer', 'form', 'table', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		common = layui.common;

	var tableIns = table.render({
		elem: '#discountGroupsTables',
		cols: [
			[ {
				field: 'groupName',
				width: 180,
				title: '门店名称',
				align: 'left',
			},
			{
				field: 'areaFullName',
				width: 280,
				title: '门店地址',
				align: 'left',
			},
			{
				field: 'detailAddress',
				width: 500,
				title: '详细地址',
				align: 'left',
			},
			{
				title: '常用操作',
				width: 100,
				align: 'center',
				toolbar: '#groupsbar',
				fixed:"right"
			}]

		],
		url: 'discount_stores_data.html',
		page: false,
		even: true
	});

	var tableIns2 = table.render({
		elem: '#groupsTables',
		id: 'idTest',
		cols: [
			[ 
			{
				field: 'merGroupId',
				checkbox: true
			},
			{
				field: 'groupName',
				width: 180,
				title: '门店名称',
				align: 'left',
			},
			{
				field: 'areaFullName',
				width: 200,
				title: '门店地址',
				align: 'left',
			},
			{
				field: 'detailAddress',
				width: 400,
				title: '详细地址',
				align: 'left',
			}]

		],
		url: 'merchant_stores_data.html',
		page: false,
		even: true
	});
	
	//监听工具条
	table.on('tool(discountGroupsTables)', function(obj) {
		
		var data = obj.data;
		
		var param = {
			'discountStoreId' : data.discountStoreId
		};
		
		if (obj.event === 'del') {
			layer.confirm('真的移除 当前门店 ['+data.groupName+'] 信息吗？', function(index) {

				$.post('discount_delete_stores.html', param, function(json){
					  
					  var oks =  $.parseJSON(json); 
					  
					  if (oks.success == 0)
					  {
						  layer.close(index);
						  tableIns.reload();
					  }
				  });
			});
		}
		
	});
	
	function selectStores(index)
	{
		var checkStatus = table.checkStatus('idTest'); //test即为基础参数id对应的值
		if(checkStatus.data.length == 0)
		{
			layer.msg('您还没有选择参与的门店', {icon: 5});
			return;
		}
		
		var storeIds = "";
		for (i = 0; i < checkStatus.data.length; i++)
		{
			storeIds += checkStatus.data[i].merGroupId + ",";
		}
		
		var params = {
			'storeIds' : storeIds
		};
		
		$.post('discount_save_stores.html', params, function(json){
			  var oks =  $.parseJSON(json); 
			  
			  if (oks.success == 1)
			  {
				  // 更新失败
				  layer.msg(oks.msg, {icon: 5});
			  }
			  else if (oks.success == 0)
			  {
				  $("#selectStorePop").addClass("layui-hide");
				  layer.close(index);
				  
				  tableIns.reload();
			  }
		  });
	}
	
	$("#selectStores").click(function(){
		
		$("#selectStorePop").removeClass("layui-hide");
		
		layer.open({
			type:1,
			title:'选择参与的门店',
			btn: ['保存设置', '取消'],
			area: ['900px', '450px'],
			content: $("#selectStorePop"),
			yes: function(index, layero){
				selectStores(index);
			},
			btn2: function(index, layero){
				layer.close(index);
				$("#selectStorePop").addClass("layui-hide");
			},
			cancel:function(index, layero){
				layer.close(index);
				$("#selectStorePop").addClass("layui-hide");
			},
		});
	});
	
	form.on('submit(storeSubmit)',function(data){

		$("#storeSubmit").addClass("layui-disabled");
		
		var datas = data.field;
		
		$.post('save_merchant_store.html', datas, function(json){
			  var oks =  $.parseJSON(json); 
			  
			  if (oks.success == 1)
			  {
				  // 更新失败
				  layer.msg(oks.msg, {icon: 5});
				  $("#storeSubmit").removeClass("layui-disabled");
			  }
			  else if (oks.success == 0)
			  {
				  window.location.href = 'merchant_stores.html';
			  }
		  });
		
    	return false;
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
	
	form.on('checkbox(getUserAccount)', function(data){
		if(data.elem.checked)
		{
			var userAccount = $("#userAccount").val();
			var userName = $("#userName").val();
			
			if (userAccount.length == 0)
			{
				if (userName.length > 0)
				{
					var datas = {
						'chineseChars' : userName
					};
					
					$.post('pinyin.html', datas, function(json){
						  var oks =  $.parseJSON(json); 
						  
						  if (oks.success == 0)
						  {
							  $("#userAccount").val(oks.msg);
						  }
					  });
				}
			}
		}
	});
	
	// 这个放在form事件的后面！
	form.verify({
		groupName: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '门店名称不能为空';
	    	}
		},
		groupCode: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '门店编号不能为空';
	    	}
		},
		detailAddress:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '请输入门店的详细地址';
	    	}
		},
		userName:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '请输入店长姓名';
	    	}
		},
		userAccount:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '请输入店长账号';
	    	}
		},
		userPhone:function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '请输入店长手机号';
	    	}
		}
	}); 

});