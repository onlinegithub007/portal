layui.use(['layer', 'form', 'upload', 'table', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		common = layui.common;
		upload = layui.upload;

    var parentTypeId = null;
    var goodsTypeId = null;
    var stockAmountParam = -1;
		
	var tableIns = table.render({
		elem: '#goodsStockTables',
		cols: [
			[
			{
				field: 'goodsTypeName',
				width: 150,
				title: '分类',
				align: 'left',
			},
			{
				field: 'goodsCode',
				width: 200,
				title: '商品条码',
				align: 'left',
			},
			{
				field: 'goodsName',
				width: 450,
				title: '商品名称',
				align: 'left',
			}
			,{
				field: 'stockAmount',
				width: 200,
				title: '商品库存',
				align: 'left',
			}]

		],
		url: 'merchant_goods_stock_data.html',
		where:{
			'stockAmountParam':stockAmountParam
		},
		page: true,
		even: true
	});
	
	// 下载商品库存EXCEL清单
	$("#downloadStocklist").click(function(){
		layer.confirm('导出EXCEL模板前，请先选定好对应的商品大类和小类(小类可选)，确认继续？', function(index) {
			layer.close(index);
			
			if (!parentTypeId)
			{
				layer.msg('请先选定好对应的商品大类和小类(小类可选)', {icon: 5});
			}
			else
			{
				var url = "download_merchant_stock.html?stockAmountParam="+stockAmountParam+"&parentTypeId="+parentTypeId+"&goodsTypeId=";
				if(goodsTypeId)
				{
					url += goodsTypeId;
				}
				window.location.href=url;
			}
		});
		
	});
	
	//执行实例
	var uploadInst = upload.render({
	    elem: '#uploadGoodsStockTemp' //绑定元素
	    ,url: 'import_merchant_goods_stock.html' //上传接口
	    ,data:{
	    }
		,accept:'file'
		,exts:'xls'
		,field:'fileName'
		,size:51200
	    ,done: function(res){
	      //上传完毕回调
	      layer.msg(res.msg, {icon: 6}, function(){
	    	  tableIns.reload({
	  	    	where :{
	  	    		'parentTypeId':parentTypeId,
	  	    		'goodsTypeId':goodsTypeId,
	  	    		'stockAmountParam':stockAmountParam
	  	    	}
	  	    });
	      });
	    }
	    ,error: function(){
	    	layer.msg('上传文件失败', {icon: 5});
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
	
	form.on('checkbox(againInputChecked)',function(data){
		againInputChecked = data.elem.checked; //是否被选中，true或者false
	});
	
	form.on('submit(goodsQuerySubmit)',function(data){

		var datas = data.field;
		
		parentTypeId = datas.parentTypeId;
		goodsTypeId = datas.goodsTypeId;
		stockAmountParam = datas.stockAmountParam;
		
		tableIns.reload({
	    	where :{
	    		'parentTypeId':parentTypeId,
	    		'goodsTypeId':goodsTypeId,
	    		'stockAmountParam':stockAmountParam
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