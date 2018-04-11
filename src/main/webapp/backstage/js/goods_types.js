layui.use(['layer', 'form', 'table', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		common = layui.common;

	$("#addTopTreeNode").click(function() {
		
		layer.prompt({
			  formType: 0,
			  value: '',
			  title: '请输入商品大类名称'
			}, function(value, index, elem){
			  layer.close(index);
			  
			  var datas = {
				 'goodsTypeName' : value
			  };
			  
			  $.post('save_goods_types.html', datas, function(json){
				  var oks =  $.parseJSON(json); 
				  
				  if (oks.success == 1)
				  {
					  // 更新失败
					  layer.msg(oks.msg, {icon: 5});
				  }
				  else if (oks.success == 0)
				  {
					  window.location.reload();
				  }
			  });
		});
		
	});
	
	$("#addSubTreeNode").click(function() {
		if (!selectedTreeNode.selectedNode)
		{
			layer.msg('请先选择一个商品分类后再做新增子类操作', {icon: 6});
			return;
		}
		
		// 新增子类操作
		layer.prompt({
		  formType: 0,
		  value: '',
		  title: '请输入商品子类名称'
		}, function(value, index, elem){
		  
		  layer.close(index);
		  
		  var datas = {
			 'goodsTypeName' : value,
			 'parentTypeId' : selectedTreeNode.nodeId
		  };
		  
		  $.post('save_goods_types.html', datas, function(json){
			  var oks =  $.parseJSON(json); 
			  
			  if (oks.success == 1)
			  {
				  // 更新失败
				  layer.msg(oks.msg, {icon: 5});
			  }
			  else if (oks.success == 0)
			  {
				  window.location.reload();
			  }
		  });
		});
	});
	
	$("#editTreeNode").click(function() {
		
		if (!selectedTreeNode.selectedNode || selectedTreeNode.nodeId == 'TOP')
		{
			layer.msg('请先选择一个商品分类后再做修改分类操作', {icon: 5});
			return;
		}
		
		// 修改操作
		layer.prompt({
		  formType: 0,
		  value: selectedTreeNode.nodeText,
		  title: '修改商品分类名称'
		}, function(value, index, elem){
		  
		  layer.close(index);
		  
		  var datas = {
			 'goodsTypeName' : value,
			 'goodsTypeId' : selectedTreeNode.nodeId
		  };
		  
		  $.post('save_goods_types.html', datas, function(json){
			  var oks =  $.parseJSON(json); 
			  
			  if (oks.success == 1)
			  {
				  // 更新失败
				  layer.msg(oks.msg, {icon: 5});
			  }
			  else if (oks.success == 0)
			  {
				  window.location.reload();
			  }
		  });
		});
		
	});
	
	$("#delTreeNode").click(function() {
		if (!selectedTreeNode.selectedNode || selectedTreeNode.nodeId == 'TOP')
		{
			layer.msg('请先选择一个商品分类后再做删除分类操作', {icon: 5});
			return;
		}
		
		layer.confirm('确认删除商品分类 ['+selectedTreeNode.nodeText+'] 吗？', function(index) {
			
			var datas = {
				 'goodsTypeId' : selectedTreeNode.nodeId
			  };
			
			$.post('delete_goods_types.html', datas, function(json){
				  var oks =  $.parseJSON(json); 
				  
				  if (oks.success == 1)
				  {
					  // 更新失败
					  layer.msg(oks.msg, {icon: 5});
				  }
				  else if (oks.success == 0)
				  {
					  window.location.reload();
				  }
			  });
		});
	});

});