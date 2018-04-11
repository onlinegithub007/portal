layui.use(['layer', 'form', 'table', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		common = layui.common;

	var parentTypeId = null;
    var goodsTypeId = null;
    var status = -1;
    var selectProviderPrmpIndex;
    var batchStockGoodsId;
	
	var tableIns = table.render({
		elem: '#batchGroupsTables',
		cols: [
			[ 
			{
				field: 'groupName',
				width: 200,
				title: '门店',
				align: 'left',
			},
			{
				field: 'areaFullName',
				width: 300,
				title: '所在地区',
				align: 'left',
			},
			{
				field: 'detailAddress',
				width: 400,
				title: '详细地址',
				align: 'left',
			},
			{
				field: 'groupDesc',
				width: 200,
				title: '备注',
				align: 'left',
			},
			{
				title: '常用操作',
				width: 100,
				align: 'center',
				toolbar: '#groupsbar',
				fixed:"right"
			}
			]

		],
		url: 'master_batch_stock_orders_stores_data.html',
		page: false,
		even: true
	});

	//监听工具条
	table.on('tool(batchGroupsTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event == 'delete') 
		{
			layer.confirm('确认要移除当前门店吗？', function(index) {
				
				$.post('master_batch_stock_orders_stores_remove.html', data, function(json){
					  
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
		page: true,
		even: true
	});
	
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
		
		$.post('master_batch_stock_orders_stores_save.html', params, function(json){
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
	
	form.on('select(selectTopGoodsType)', function(data){
		
		parentTypeId = data.value;
		
		if (parentTypeId.length > 0)
		{
			var params = {
				'parentTypeId' : parentTypeId
			};
			
			$.post('goods_sub_types.html', params, function(data){
				var result = $.parseJSON(data); 
				
				if(result.count > 0)
				{
					var subTypeHtml = "<option value=\"\">请选择商品小类[全部]</option>";
					var jsonData = eval(result.data);
					for (var i = 0; i < jsonData.length; i++)
		    		{
						subTypeHtml += "<option value='" + jsonData[i].goodsTypeId + "'>" + jsonData[i].goodsTypeName + "</option>";
		    		}
					
					$("#subGoodsType").html(subTypeHtml);
					form.render('select');
				}
				else
				{
					$("#subGoodsType").html("<option value=\"\">请选择商品小类[全部]</option>");
					form.render('select');
				}
			});
		}
		else
		{
			$("#subGoodsType").html("<option value=\"\">请选择商品小类[全部]</option>");
			form.render('select');
		}
	});
	
	form.on('select(selectSubGoodsType)', function(data){
		goodsTypeId = data.value;
	});
	
	form.on('submit(goodsQuerySubmit)',function(data){

		var datas = data.field;
		
		parentTypeId = datas.parentTypeId;
		goodsTypeId = datas.goodsTypeId;
		status = datas.status;
		
		tableIns2.reload({
	    	where :{
	    		'parentTypeId':parentTypeId,
	    		'goodsTypeId':goodsTypeId,
	    		'seeAllGoods':1,
	    		'show':'true'
	    	}
	    });
		
    	return false;
	});
	
	// 暂存数量
	$("#stockOrderCountBtn").click(function(){
		
		// 先采集批量的参数
		var batchStockGoodsIdStr = "";
		var goodsCountStr = "";
		
		for (var i = 0; i < goodsData.length; i++)
		{
			batchStockGoodsIdStr += goodsData[i].batchStockGoodsId + ",";
			goodsCountStr += $("#batchStockGoodsId"+goodsData[i].batchStockGoodsId).val() + ",";
		}
		
		var params = {
			'batchStockGoodsIdStr':batchStockGoodsIdStr,
			'goodsCountStr':goodsCountStr
		};
		
		$.post('master_batch_stock_order_goods_count.html', params, function(json){
			  
			  var oks =  $.parseJSON(json); 
			  
			  if (oks.success == 0)
			  {
				  layer.alert(oks.msg, {icon: 6});
			  }
			  else
			  {
				  layer.alert(oks.msg, {icon: 5});
			  }
		  });
	});
	
	form.on('submit(queryMerStores)',function(data){
		
		var datas = data.field;
		
		tableIns2.reload({
			where:datas
		});
		
		return false;
	});
	
form.on('select(province)', function(data){
	    
		var parentAreaId = data.value;
	    var params = {'parentAreaId' : parentAreaId};
	    
	    if (parentAreaId.length == 0)
	    {
	    	$("#selectCity").html("");
			$("#selectAreas").html("");
			form.render('select');
	    	return;
	    }
	    
		$.post('areas.html', params, function(data){
			var result = $.parseJSON(data); 
			
			if(result.count > 0)
			{
				var cityHtml = "<option value=''>[不限]</option>";
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
	    
	    if (parentAreaId.length == 0)
	    {
			$("#selectAreas").html("");
			form.render('select');
	    	return;
	    }
	    
		$.post('areas.html', params, function(data){
			var result = $.parseJSON(data); 
			
			if(result.count > 0)
			{
				var areaHtml = "<option value=''>[不限]</option>";;
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