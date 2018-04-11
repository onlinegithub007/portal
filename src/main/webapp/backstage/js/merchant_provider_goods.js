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
			[
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
				field: 'measurUnit',
				width: 100,
				title: '零售单位',
				align: 'left',
			}
			,{
				field: 'goodsPriceText',
				width: 100,
				title: '零售价(元)',
				align: 'right',
			}
			,{
				field: 'providerUnit',
				width: 100,
				title: '采购单位',
				align: 'left',
			}
			,{
				field: 'providerUnitMultiple',
				width: 100,
				title: '单位换算',
				align: 'left',
			}
			,{
				field: 'goodsCostText',
				width: 100,
				title: '采购价(元)',
				align: 'right',
			}
			, {
				title: '常用操作',
				width: 160,
				align: 'center',
				toolbar: '#goodsbar',
				fixed:"right"
			}]

		],
		url: 'merchant_provider_goods_data.html',
		page: false,
		even: true
	});
	
	//执行实例
	var uploadInst = upload.render({
	    elem: '#uploadGoodsTemp' //绑定元素
	    ,url: 'import_provider_goods.html' //上传接口
	    ,data:{
	    }
		,accept:'file'
		,exts:'xls'
		,field:'fileName'
		,size:51200
	    ,done: function(res){
	    	layer.msg(res.msg, {icon: 6}, function(){
	    		//上传完毕回调
		    	tableIns.reload();
	    	});
	      
	    }
	    ,error: function(){
	    	layer.msg('上传文件失败', {icon: 5});
	    }
	});

	//监听工具条
	table.on('tool(goodsTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event === 'disable') 
		{
			var datas = {
				'gpId' : data.gpId,
				'gpStatus' : 1
			};
			layer.confirm('确认要对该商品 ['+data.goodsName+'] 禁止供货吗？', function(index) {
				
				layer.close(index);
				
				$.post('change_mer_provider_goods_status.html', datas, function(json){
					  
					  var oks =  $.parseJSON(json); 
					  
					  if (oks.success == 0)
					  {
						  tableIns.reload();
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
				'gpId' : data.gpId,
				'gpStatus' : 0
			};
			layer.confirm('确认要对该商品 ['+data.goodsName+'] 恢复供货吗吗？', function(index) {
				
				layer.close(index);
				
				$.post('change_mer_provider_goods_status.html', datas, function(json){
					  
					  var oks =  $.parseJSON(json); 
					  
					  if (oks.success == 0)
					  {
						  tableIns.reload();
					  }
					  else{
						  layer.msg(oks.msg, {icon: 5});
					  }
				  });
			});
		}
		else if (obj.event === 'del') {
			layer.confirm('真的从供货商名下删除 当前 ['+data.goodsName+'] 商品吗？', function(index) {

				$.post('delete_provider_goods.html', data, function(json){
					  
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

});