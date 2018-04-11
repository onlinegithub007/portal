layui.use(['layer', 'form', 'upload', 'table', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		common = layui.common;
		upload = layui.upload;

    var parentTypeId = null;
    var goodsTypeId = null;
		
	var tableIns = table.render({
		elem: '#storeGoodsPriceTables',
		cols: [
			[{
				field: 'status',
				width: 50,
				title: '可售',
				align: 'left',
				templet: '#titleTpl'
			},
			{
				field: 'goodsTypeName',
				width: 140,
				title: '分类',
				align: 'left',
			},
			{
				field: 'goodsCode',
				width: 170,
				title: '商品条码',
				align: 'left',
			},
			{
				field: 'goodsName',
				width: 320,
				title: '商品名称',
				align: 'left',
			}
			,{
				field: 'goodsPrice',
				width: 130,
				title: '基础销售价(分)',
				align: 'left'
			}
			,{
				field: 'goodsStorePrice',
				width: 130,
				title: '门店销售价(分)',
				align: 'left',
				templet: '#priceTpl'
			}
			, {
				title: '保存价格',
				width: 100,
				align: 'center',
				toolbar: '#goodsbar',
				fixed:"right"
			}]

		],
		url: 'stores_goods_price_data.html',
		where:{
			'seeAllGoods' : seeAllGoods
		},
		page: true,
		even: true
	});
	
	//执行实例
	var uploadInst = upload.render({
	    elem: '#importStorePrice' //绑定元素
	    ,url: 'import_store_goods_price.html' //上传接口
	    ,data:{
	    }
		,accept:'file'
		,exts:'xls'
		,field:'fileName'
		,size:51200
	    ,done: function(res){
	    	//上传完毕回调
	    	layer.msg(res.msg, {icon: 6}, function(){
	    		// 刷新表格
	    		tableIns.reload({
			    	where :{
			    		'parentTypeId':parentTypeId,
			    		'goodsTypeId':goodsTypeId,
			    		'seeAllGoods' : seeAllGoods
			    	}
			    });
	    	});
	    }
	    ,error: function(){
	    	layer.msg('上传文件失败', {icon: 5});
	    }
	});
	
	//监听工具条
	table.on('tool(storeGoodsPriceTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event === 'edit') 
		{
			var goodsId = data.goodsId;
			window.location.href = 'goods_info.html?goodsId=' + goodsId;
		}
		else if (obj.event === 'grantPrice' || obj.event === 'savePrice') 
		{
			var goodsId = data.goodsId;
			var storePriceId = "#goodsId"+goodsId;
			var storePrice = $(storePriceId).val();
			
			var goodsPriceId = "";
			if (obj.event === 'savePrice')
			{
				goodsPriceId = data.goodsPriceId;
			}
			
			var datas = {
				'goodsId' : goodsId,
				'storeGoodsPrice':storePrice,
				'goodsPriceId':goodsPriceId,
				'priceSetting':obj.event
			};
			
			if(storePrice)
			{
				$.post('save_stores_goods_price.html', datas, function(json){
					var oks =  $.parseJSON(json); 
					  
					if (oks.success == 0)
					{
						layer.msg(oks.msg, {icon: 6});
						
						tableIns.reload({
					    	where :{
					    		'parentTypeId':parentTypeId,
					    		'goodsTypeId':goodsTypeId,
					    		'seeAllGoods' : seeAllGoods
					    	}
					    });
					}
					else
					{
						layer.msg(oks.msg, {icon: 5});
					}
				});
			}
				
		}
		else if (obj.event === 'addSame') 
		{
			var goodsId = data.goodsId;
			window.location.href = 'create_same_goods_info.html?goodsId=' + goodsId;
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
	
	form.on('select(allGoods)', function(data){
		seeAllGoods = data.value;
	});
	
	form.on('select(selectTopGoodsType)', function(data){
		var parentTypeId = data.value;
		
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
	
	form.on('checkbox(againInputChecked)',function(data){
		againInputChecked = data.elem.checked; //是否被选中，true或者false
	});
	
	form.on('submit(goodsQuerySubmit)',function(data){

		var datas = data.field;
		
		parentTypeId = datas.parentTypeId;
		goodsTypeId = datas.goodsTypeId;
		
		tableIns.reload({
	    	where :{
	    		'parentTypeId':parentTypeId,
	    		'goodsTypeId':goodsTypeId,
	    		'seeAllGoods' : seeAllGoods
	    	}
	    });
		
    	return false;
	});
	
	form.on('submit(goodsInfoSubmit)',function(data){

		var datas = data.field;
		
		$.post('save_goods_info.html', datas, function(json){
			  var oks =  $.parseJSON(json); 
			  
			  if (oks.success == 1)
			  {
				  // 更新失败
				  layer.msg(oks.msg, {icon: 5});
			  }
			  else if (oks.success == 0)
			  {
				  layer.msg(oks.msg, {icon: 6});
				  window.location.href = 'goods.html';
			  }
		  });
		
    	return false;
	});
	
	// 将门店价格导出到EXCEL中
	$("#exportStorePrice").click(function(){
		var url = "download_store_price_temp.html?parentTypeId=";
		if(parentTypeId)
		{
			url += parentTypeId;
		}
		
		window.location.href=url;
	});
	
	// 将当前价格策略复制到其他门店
	$("#copyStorePrice").click(function(){
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
	
	form.on('submit(queryMerStores)',function(data){
		
		var datas = data.field;
		
		tableIns2.reload({
			where:datas
		});
		
		return false;
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
		
		$.post('stores_goods_price_copy.html', params, function(json){
			  var oks =  $.parseJSON(json); 
			  
			  layer.close(index);
			  $("#selectStorePop").addClass("layui-hide");
			  
			  if (oks.success == 1)
			  {
				  // 更新失败
				  layer.msg(oks.msg, {icon: 5});
			  }
			  else if (oks.success == 0)
			  {
				  layer.alert(oks.msg, {icon: 6}, function(index){
					  layer.close(index);
				  });
			  }
		  });
	}
	
	// 这个放在form事件的后面！
	form.verify({
		goodsCode: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '请录入商品条码';
	    	}
		},
		goodsName: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '请录入商品名称';
	    	}
		},
		goodsPrice: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '请录入商品销售价格,且只能输入数字';
	    	}
		}
	}); 

});