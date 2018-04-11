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
		elem: '#memberGoodsPriceTables',
		cols: [
			[{
				field: 'status',
				width: 50,
				title: '授权',
				align: 'left',
				templet: '#titleTpl'
			},
			{
				field: 'goodsTypeName',
				width: 150,
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
				field: 'goodsPrice',
				width: 120,
				title: '销售价(分)',
				align: 'left'
			}
			,{
				field: 'goodsMemberPrice',
				width: 120,
				title: '会员价(分)',
				align: 'left'
			},
			{
				title: '常用操作',
				width: 100,
				align: 'center',
				toolbar: '#opTpl',
				fixed:"right"
			}
			]

		],
		url: 'member_goods_price_data.html',
		where:{
			'seeAllGoods' : seeAllGoods
		},
		page: true,
		even: true
	});
	
	//执行实例
	var uploadInst = upload.render({
	    elem: '#importMemberPrice' //绑定元素
	    ,url: 'import_member_goods_price.html' //上传接口
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
	table.on('tool(memberGoodsPriceTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event === 'edit') 
		{
			var goodsId = data.goodsId;
			window.location.href = 'goods_info.html?goodsId=' + goodsId;
		}
		else if (obj.event === 'delete') {
			layer.confirm('真的删除 当前商品 ['+data.goodsName+'] 的会员价格信息吗？', function(index) {
				
				var param = {
					'goodsMembPriceId' : data.goodsMembPriceId
				};
				
				$.post('delete_member_goods_price.html', param, function(json){
					  
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
	$("#exportMemberPrice").click(function(){
		var url = "download_member_price_temp.html?parentTypeId=";
		if(parentTypeId)
		{
			url += parentTypeId;
		}
		
		window.location.href=url;
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