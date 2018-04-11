layui.use(['layer', 'form', 'table', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		common = layui.common;

	var bindGoodsId = '';
	var currBindGoodsId = '';
	
	var tableIns = table.render({
		elem: '#bindingGoodsTables',
		cols: [
			[ 
			{
				field: 'goodsCode',
				width: 200,
				title: '捆绑商品条码',
				align: 'left',
			},
			{
				field: 'goodsName',
				width: 300,
				title: '捆绑商品名称',
				align: 'left',
			},
			{
				field: 'goodsPriceText',
				width: 150,
				title: '捆绑商品价格(元)',
				align: 'right',
			},
			{
				field: 'bindActive',
				width: 100,
				title: '状态',
				align: 'left',
				templet: '#activeTpl'
			}
			, {
				title: '常用操作',
				width: 250,
				align: 'center',
				toolbar: '#groupsbar',
				fixed:"right"
			}]

		],
		url: 'binding_goods_data.html',
		page: false,
		even: true
	});
	
	var tableIns2 = table.render({
		elem: '#selectGoodsTables',
		cols: [
			[ 
			{
				field: 'goodsCode',
				width: 140,
				title: '商品条码',
				align: 'left',
			},
			{
				field: 'goodsName',
				width: 300,
				title: '商品名称',
				align: 'left',
			},
			{
				field: 'goodsPriceText',
				width: 120,
				title: '销售价格(元)',
				align: 'right',
			},
			{
				width: 120,
				title: '数量',
				align: 'left',
				templet: '#bindGoodsCountTpl'
			}
			, {
				title: '常用操作',
				width: 180,
				align: 'center',
				toolbar: '#bindDetailBar',
				fixed:"right"
			}]

		],
		url: 'binding_goods_detail_data.html',
		page: false,
		even: true
	});

	//监听工具条
	table.on('tool(bindingGoodsTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event == 'modify') 
		{
			bindGoodsId = data.bindGoodsId;
			saveBindingGoods(false, data);
		}
		else if (obj.event == 'bind_goods') 
		{
			showBindingGoodsDetails(data.bindGoodsId);
		}
		else if (obj.event == 'active') 
		{
			layer.confirm('要激活 当前捆绑商品 ['+data.goodsName+'] 吗？', function(index) {
				layer.close(index);
				changeBindingGoodsActive(data, true);
			});
		}
		else if (obj.event === 'inactive') 
		{
			layer.confirm('要下架 当前捆绑商品 ['+data.goodsName+'] 吗？', function(index) {
				layer.close(index);
				changeBindingGoodsActive(data, false);
			});
		}
		else if (obj.event == 'delete') {
			layer.confirm('真的删除 当前捆绑商品 ['+data.goodsName+'] 信息吗？', function(index) {

				$.post('binding_goods_delete.html', data, function(json){
					  
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
	
	//监听工具条
	table.on('tool(selectGoodsTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event == 'delete') {
			layer.confirm('确认解除 当前捆绑商品 ['+data.goodsName+'] 信息吗？', function(index) {

				$.post('binding_goods_detail_delete.html', data, function(json){
					  
					  var oks =  $.parseJSON(json); 
					  
					  if (oks.success == 0)
					  {
						  layer.close(index);
						  tableIns2.reload({
								 where :{
									 'bindGoodsId':currBindGoodsId
								 }
							});
					  }
				  });
			});
		}
		else if (obj.event == 'change') {
			
			var bindDetailId = data.bindDetailId;
			var bindGoodsCount = $("#bindDetailId"+bindDetailId).val();
			
			var params = {
				'bindDetailId' : bindDetailId,
				'bindGoodsCount' : bindGoodsCount
			};
			
			$.post('binding_goods_detail_change.html', params, function(json){
				  
				  var oks =  $.parseJSON(json); 
				  
				  if (oks.success == 0)
				  {
					  tableIns2.reload({
							 where :{
								 'bindGoodsId':currBindGoodsId
							 }
						});
				  }
				  else
				  {
					  layer.msg(oks.msg, {icon: 5});
				  }
			  });
		}
	});
	
	function showBindingGoodsDetails(bindGoodsId)
	{
		currBindGoodsId = bindGoodsId;
		
		$("#bindingGoodsCode").val("");
		
		layer.open({
			type:1,
			btn: ['关闭'],
			title:'捆绑商品明细',
			area: ['900px', '550px'],
			content: $("#selectGoodsPop"),
			yes: function(index, layero){
				layer.close(index);
				$("#bindGoodsForm").addClass("layui-hide");
			}
		});
		
		$("#selectGoodsPop").removeClass("layui-hide");
		
		tableIns2.reload({
			 where :{
				 'bindGoodsId':currBindGoodsId
			 }
		});
	}
	
	function changeBindingGoodsActive(data, active)
	{
		$.post('binding_goods_active.html?active='+active, data, function(json){
			var oks =  $.parseJSON(json); 
			  
			if (oks.success == 0)
			{
				tableIns.reload();
			}
			else
			{
				layer.msg(oks.msg, {icon: 5});
			}
		});
	}
	
	function saveBindingGoods(isCreateFlag, data)
	{
		var title = '';
		
		if (isCreateFlag)
		{
			bindGoodsId = '';
			title = '新建捆绑商品';
			
			$("#goodsName").val("");
			$("#goodsCode").val("");
			$("#goodsPrice").val("");
		}
		else{
			title = '修改捆绑商品';
			
			$("#goodsName").val(data.goodsName);
			$("#goodsCode").val(data.goodsCode);
			$("#goodsPrice").val(data.goodsPrice);
		}
		
		layer.open({
			type:1,
			btn: ['保存', '取消'],
			title:title,
			area: ['600px', '350px'],
			content: $("#bindGoodsForm"),
			yes: function(index, layero){
				
				var goodsName = $("#goodsName").val();
				var goodsCode = $("#goodsCode").val();
				var goodsPrice = $("#goodsPrice").val();
				
				if (!goodsPrice || goodsPrice <= 0)
				{
					layer.msg('请输入捆绑商品销售价格', {icon: 5});
					return false;
				}
				
				// 发起验收流程
				var params = {
					'goodsName' : goodsName,
					'goodsCode' : goodsCode,
					'goodsPrice' : goodsPrice,
					'bindGoodsId':bindGoodsId
				};
				
				$.post('binding_goods_save.html', params, function(json){
					var oks =  $.parseJSON(json); 
					  
					if (oks.success == 0)
					{
						layer.close(index);
						$("#bindGoodsForm").addClass("layui-hide");
						
						layer.alert(oks.msg, {icon: 6}, function(index){
							layer.close(index);
							tableIns.reload();
						});
					}
					else
					{
						layer.msg(oks.msg, {icon: 5});
					}
				});
			},
			btn2: function(index, layero){
				$("#bindGoodsForm").addClass("layui-hide");
				layer.close(index);
			}
		});
		
		$("#bindGoodsForm").removeClass("layui-hide");
	}
	
	$("#createBindGoods").click(function(){
		saveBindingGoods(true, null);
	});
	
	form.on('submit(goodsQuerySubmit)',function(data){
		var datas = data.field;
		
		var params = {
			'goodsCode' : datas.goodsCode,
			'bindGoodsId':currBindGoodsId
		};
		
		$.post('binding_goods_detail_queryAndBind.html', params, function(json){
			  var oks =  $.parseJSON(json); 
			  
			  if (oks.success == 1)
			  {
				  // 更新失败
				  layer.msg(oks.msg, {icon: 5});
			  }
			  else if (oks.success == 0)
			  {
				  tableIns2.reload({
					  'bindGoodsId':currBindGoodsId
				  });
			  }
		  });
		
		return false;
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