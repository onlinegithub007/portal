layui.use(['layer', 'form', 'table', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		common = layui.common;

	var tableIns = table.render({
		elem: '#groupsTables',
		cols: [
			[ 
			{
				field: 'stockDispatchId',
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
				width: 300,
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
				field: 'groupDesc',
				width: 300,
				title: '备注',
				align: 'left',
			}
			]

		],
		url: 'merchant_stores_data.html',
		page: false,
		even: true
	});

	//监听工具条
	table.on('tool(groupsTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event === 'select') 
		{
			var merGroupId = data.merGroupId;
			window.location.href = 'store_stock_orders.html?storeId=' + merGroupId;
		}
		else if (obj.event == 'stock') 
		{
			var merGroupId = data.merGroupId;
			window.location.href = 'store_goods_stock.html?merGroupId=' + merGroupId;
		}
		else if (obj.event == 'expired') 
		{
			var merGroupId = data.merGroupId;
			window.location.href = 'store_goods_expired_stock.html?merGroupId=' + merGroupId;
		}
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