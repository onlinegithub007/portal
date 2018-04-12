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
		
	var tableIns = table.render({
		elem: '#goodsTables',
		cols: [
			[{
				field: 'status',
				width: 50,
				title: '状态',
				align: 'left',
				templet: '#titleTpl'
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
				width: 260,
				title: '商品名称',
				align: 'left',
			}
			,{
				field: 'goodsPrice',
				width: 100,
				title: '销售价(分)',
				align: 'left',
			}
			,{
				field: 'measurUnit',
				width: 100,
				title: '零售单位',
				align: 'left',
			},
			{
				field: 'providerUnit',
				width: 100,
				title: '进货单位',
				align: 'left',
			}
			,{
				field: 'providerUnitMultiple',
				width: 100,
				title: '换算倍数',
				align: 'left',
			}
			, {
				title: '常用操作',
				width: 240,
				align: 'center',
				toolbar: '#goodsbar',
				fixed:"right"
			}]

		],
		url: 'merchant_goods_data.html',
		page: true,
		even: true
	});
	
	// 下载空白EXCEL模板
	$("#downloadBlankTemp").click(function(){
		layer.confirm('下载EXCEL模板前，请先选定好对应的商品大类和小类(小类可选)，确认继续？', function(index) {
			layer.close(index);
			
			if (!parentTypeId)
			{
				layer.msg('请先选定好对应的商品大类和小类(小类可选)', {icon: 5});
			}
			else
			{
				var url = "download_goodsinfo_temp.html?blank=true&parentTypeId="+parentTypeId+"&goodsTypeId=";
				if(goodsTypeId)
				{
					url += goodsTypeId;
				}
				window.location.href=url;
			}
		});
		
	});
	
	// 下载商品EXCEL模板
	$("#downloadGoodsTemp").click(function(){
		layer.confirm('导出EXCEL模板前，请先选定好对应的商品大类和小类(小类可选)，确认继续？', function(index) {
			layer.close(index);
			
			if (!parentTypeId)
			{
				layer.msg('请先选定好对应的商品大类和小类(小类可选)', {icon: 5});
			}
			else
			{
				var url = "download_goodsinfo_temp.html?blank=false&parentTypeId="+parentTypeId+"&goodsTypeId=";
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
	    elem: '#uploadGoodsTemp' //绑定元素
	    ,url: 'import_goods_info.html' //上传接口
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
	    	    		'status':status
	    	    	}
	    	    });
	    	});
	    }
	    ,error: function(){
	    	layer.msg('上传文件失败', {icon: 5});
	    }
	});

	//监听工具条
	table.on('tool(goodsTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event === 'edit') 
		{
			var goodsId = data.goodsId;
			window.location.href = 'goods_info.html?goodsId=' + goodsId;
		}
		else if (obj.event === 'addSame') 
		{
			var goodsId = data.goodsId;
			window.location.href = 'create_same_goods_info.html?goodsId=' + goodsId;
		}
		else if (obj.event === 'disable') 
		{
			var datas = {
				'goodsId' : data.goodsId,
				'status' : 1
			};
			layer.confirm('确认要下架商品 ['+data.goodsName+'] 信息吗？', function(index) {
				
				layer.close(index);
				
				$.post('change_mer_goods_status.html', datas, function(json){
					  
					  var oks =  $.parseJSON(json); 
					  
					  if (oks.success == 0)
					  {
						  tableIns.reload({
						    	where :{
						    		'parentTypeId':parentTypeId,
						    		'goodsTypeId':goodsTypeId,
						    		'status':status
						    	}
						    });
					  }
					  else{
						  layer.msg(oks.msg, {icon: 5});
					  }
				  });
			});
		}
		else if (obj.event === 'enable') 
		{
			var datas = {
				'goodsId' : data.goodsId,
				'status' : 0
			};
			layer.confirm('确认要上架商品 ['+data.goodsName+'] 信息吗？', function(index) {
				
				layer.close(index);
				
				$.post('change_mer_goods_status.html', datas, function(json){
					  
					  var oks =  $.parseJSON(json); 
					  
					  if (oks.success == 0)
					  {
						  tableIns.reload({
						    	where :{
						    		'parentTypeId':parentTypeId,
						    		'goodsTypeId':goodsTypeId,
						    		'status':status
						    	}
						    });
					  }
					  else{
						  layer.msg(oks.msg, {icon: 5});
					  }
				  });
			});
		}
		else if (obj.event === 'del') {
			layer.confirm('真的删除 当前 ['+data.goodsName+'] 信息吗？', function(index) {
				
				var param = {
					'goodsId' : data.goodsId
				};
				
				$.post('delete_goods_info.html', param, function(json){
					  
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
	
	form.on('checkbox(hasQualityPeriod)',function(data){
		
		if(data.elem.checked)
		{
			$("#hasQualityPeriod").val(1);
		}
		else
		{
			$("#hasQualityPeriod").val(0);
		}
	});
	
	form.on('submit(goodsQuerySubmit)',function(data){

		var datas = data.field;
		
		parentTypeId = datas.parentTypeId;
		goodsTypeId = datas.goodsTypeId;
		status = datas.status;
        goodsCode=datas.goodsCode;
		tableIns.reload({
	    	where :{
	    		'parentTypeId':parentTypeId,
	    		'goodsTypeId':goodsTypeId,
                'goodsCode':goodsCode,
	    		'status':status
	    	}
	    });
		
    	return false;
	});
	
	form.on('submit(goodsInfoSubmit)',function(data){

		var datas = data.field;
		
		$("#goodsInfoSubmitBtn").addClass("layui-disabled");
		$("#goodsInfoSubmitBtn").attr("disabled" , "disabled");
		
		$.post('save_goods_info.html', datas, function(json){
			  var oks =  $.parseJSON(json); 
			  
			  if (oks.success == 1)
			  {
				  // 更新失败
				  $("#goodsInfoSubmitBtn").removeClass("layui-disabled");
				  $("#goodsInfoSubmitBtn").removeAttr("disabled" , "disabled");
				  layer.msg(oks.msg, {icon: 5});
			  }
			  else if (oks.success == 0)
			  {
				  layer.msg(oks.msg, {icon: 6}, function(){
					  window.location.href = 'goods.html';
				  });
			  }
			  else if (oks.success == 2)
			  {
				  layer.msg(oks.msg, {icon: 6}, function(){
					  window.location.href = 'goods.html';
				  });
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