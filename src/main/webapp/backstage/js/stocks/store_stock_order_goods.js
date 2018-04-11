layui.use(['layer', 'form', 'upload', 'table', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		common = layui.common;
		upload = layui.upload;

    var parentTypeId = null;
    var goodsTypeId = null;
    var status = -1;
    var goodsData;
		
	var tableIns = table.render({
		elem: '#goodsTables',
		cols: [
			[
			{
				field: 'goodsTypeName',
				width: 120,
				title: '分类',
				align: 'left',
			},
			{
				field: 'goodsCode',
				width: 150,
				title: '商品条码',
				align: 'left',
			},
			{
				field: 'goodsName',
				width: 300,
				title: '商品名称',
				align: 'left',
			}
			,{
				field: 'goodsCount',
				width: 130,
				title: '进货数量',
				align: 'right',
				templet: '#goodsCountTpl'
			}
			,{
				field: 'providerUnit',
				width: 100,
				title: '进货单位',
				align: 'right',
				templet:'#providerUnitTpl'
			},
			{
				title: '常用操作',
				width: 100,
				align: 'center',
				toolbar: '#goodsbar',
				fixed:"right"
			}
			]

		],
		url: 'store_stock_order_goods_data.html',
		page: false,
		even: true,
		done: function(res, curr, count){
			// 引用返回表格中所有数据，为后面批量设定价格做准备
			goodsData = res.data;
		}
	});
	
	var tableIns2 = table.render({
		elem: '#selectGoodsTables',
		id: 'selectGoodsTable',
		cols: [
			[
			{
				field: 'goodsId',
				checkbox: true
			},
			{
				field: 'goodsTypeName',
				width: 120,
				title: '分类',
				align: 'left',
			},
			{
				field: 'goodsCode',
				width: 150,
				title: '商品条码',
				align: 'left',
			},
			{
				field: 'goodsName',
				width: 350,
				title: '商品名称',
				align: 'left',
			}
			]
		],
		url: 'stores_goods_price_data.html?dispatch=dispatch',
		page: false,
		even: true
	});
	
	$("#addGoodsBtn").click(function(){
		
		$("#selectGoodsPop").removeClass("layui-hide");
		
		tableIns2.reload({
	    	where :{
	    		'seeAllGoods':0,
	    		'show':'false'
	    	}
	    });
		
		layer.open({
			type:1,
			btn: ['确定','取消'],
			maxmin: true,
			title:'选择要采购的商品',
			area: ['750px', '450px'],
			content: $("#selectGoodsPop"),
			yes: function(index, layero){ 
				
				var checkStatus = table.checkStatus('selectGoodsTable');
				if(checkStatus.data.length == 0)
				{
					layer.msg('还没有选择要采购的商品', {icon: 5});
					return;
				}
				
				var goodIdList = "";
				for (i = 0; i < checkStatus.data.length; i++)
				{
					goodIdList += checkStatus.data[i].goodsId + ",";
				}
				
				var params = {
					'goodsList' : goodIdList
				};
				
				$.post('store_stock_init_order_detail.html', params, function(json){
				  var oks =  $.parseJSON(json); 
				  
				  if (oks.success == 1)
				  {
					  // 更新失败
					  layer.msg(oks.msg, {icon: 5});
				  }
				  else if (oks.success == 0)
				  {
					  tableIns.reload();
				  }
				  
			  });
				
				layer.close(index);
				$("#selectGoodsPop").addClass("layui-hide");
			},
			btn2: function(index, layero){ 
				layer.close(index);
				$("#selectGoodsPop").addClass("layui-hide");
			},
			cancel: function(index, layero){ 
				layer.close(index);
				$("#selectGoodsPop").addClass("layui-hide");
			}
		});
		
	});
	
	/*
	//执行实例
	var uploadInst = upload.render({
	    elem: '#uploadStockOrderGoodsTemp' //绑定元素
	    ,url: 'import_store_stock_order_goods.html' //上传接口
	    ,data:{
	    }
		,accept:'file'
		,exts:'xls'
		,field:'fileName'
		,size:51200
	    ,done: function(res){
	        //上传完毕回调
	    	layer.msg(res.msg, {icon: 6}, function(){
	    		tableIns.reload();
	    	});
	    }
	    ,error: function(){
	    	layer.msg('上传文件失败', {icon: 5});
	    }
	});
	*/

	//监听工具条
	table.on('tool(goodsTables)', function(obj) {
		
		var data = obj.data;
		
		var params = {
			'stockOrderDetailId': data.stockOrderDetailId
		};
		
		if (obj.event === 'del') {
			layer.confirm('确认移除当前采购商品 ['+data.goodsName+'] 吗？', function(index) {

				$.post('store_stock_delete_order_detail.html', params, function(json){
					  
					  var oks =  $.parseJSON(json); 
					  
					  if (oks.success == 0)
					  {
						  layer.close(index);
						  tableIns.reload();
					  }
				  });
			});
		}
		else if (obj.event === 'save')
		{
			var goodsCount = $("#goodsId"+data.stockOrderDetailId).val();
			if (goodsCount == 0)
			{
				layer.msg('商品采购数量不能为0，如果不采购该商品，请将其移除', {icon: 5});
				return;
			}
			
			var params = {
				'stockOrderDetailId':data.stockOrderDetailId,
				'goodsCount':goodsCount,
				'goodsId':data.goodsId
			};
			
			$.post('store_stock_update_order_detail.html', params, function(json){
				  
				  var oks =  $.parseJSON(json); 
				  
				  if (oks.success == 0)
				  {
					  layer.msg(oks.msg, {icon: 6}, function(){
						  tableIns.reload();
					  });
				  }
			  });
		}
		
	});
	
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
	    		'seeAllGoods':0,
	    		'show':'true'
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
			  else if (oks.success == 2)
			  {
				  layer.msg(oks.msg, {icon: 6});
			  }
		  });
		
    	return false;
	});
	
	// 暂存数量
	$("#stockOrderCountBtn").click(function(){
		
		// 先采集批量的参数
		var stockOrderDetailIdStr = "";
		var goodsCountStr = "";
		
		for (var i = 0; i < goodsData.length; i++)
		{
			stockOrderDetailIdStr += goodsData[i].stockOrderDetailId + ",";
			goodsCountStr += $("#goodsId"+goodsData[i].stockOrderDetailId).val() + ",";
		}
		
		var params = {
			'stockOrderDetailIdStr':stockOrderDetailIdStr,
			'goodsCountStr':goodsCountStr
		};
		
		$.post('store_stock_batch_update_count.html', params, function(json){
			  
			  var oks =  $.parseJSON(json); 
			  
			  if (oks.success == 0)
			  {
				  layer.msg(oks.msg, {icon: 6});
			  }
			  else
			  {
				  layer.msg(oks.msg, {icon: 5});
			  }
		  });
	});
	
	$("#stockOrderSubmitBtn").click(function(){
		layer.confirm('确认要提交当前进货单信息吗？', function(index) {
			
			$("#stockOrderSubmitBtn").attr("disabled","disabled");
			$("#stockOrderSubmitBtn").addClass("layui-disabled");
			
			// 先采集批量的参数
			var stockOrderDetailIdStr = "";
			var goodsCountStr = "";
			
			for (var i = 0; i < goodsData.length; i++)
			{
				stockOrderDetailIdStr += goodsData[i].stockOrderDetailId + ",";
				goodsCountStr += $("#goodsId"+goodsData[i].stockOrderDetailId).val() + ",";
			}
			
			var params = {
				'stockOrderDetailIdStr':stockOrderDetailIdStr,
				'goodsCountStr':goodsCountStr
			};
			
			$.post('store_stock_orders_submit.html', params, function(json){
				  
				  var oks =  $.parseJSON(json); 
				  
				  if (oks.success == 0)
				  {
					  layer.msg(oks.msg, {icon: 6}, function(){
						  window.location.href = "store_stock_orders.html";
					  });
				  }
				  else
				  {
					  layer.msg(oks.msg, {icon: 5});
					  $("#stockOrderSubmitBtn").removeAttr("disabled","disabled");
					  $("#stockOrderSubmitBtn").removeClass("layui-disabled");
				  }
			  });
		});
	});
	
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