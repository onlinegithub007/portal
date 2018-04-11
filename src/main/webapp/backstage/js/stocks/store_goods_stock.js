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
    var layIndex;
		
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
				width: 300,
				title: '商品名称',
				align: 'left',
			}
			,{
				field: 'stockAmount',
				width: 100,
				title: '商品库存',
				align: 'left'
			},
			{
				width: 140,
				title: '盘点状态',
				align: 'left',
				templet: '#stockCheckStatusTpl'
			},
			{
				width: 160,
				title: '库存差异',
				align: 'left',
				templet:'#stockCheckTpl'
			},
			{
				title: '常用操作',
				width: 200,
				align: 'center',
				toolbar: '#viewTpl',
				fixed:"right"
			}]

		],
		url: 'store_goods_stock_check_data.html',
		where:{
			'stockAmountParam':stockAmountParam
		},
		page: true,
		even: true
	});
	
	//监听工具条
	table.on('tool(goodsStockTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event == 'view') 
		{
			var goodsId = data.goodsId;
			layer.open({
				type:2,
				maxmin: true,
				title:'查看商品库存明细',
				area: ['900px', '600px'],
				content: 'store_goods_stock_detail.html?goodsId=' + goodsId
			});
		}
		else if (obj.event == 'ok' || obj.event == 'diff') 
		{
			var goodsStockId = data.goodsStockId;
			var checkResult = 0;
			var checkDiffValue = 0;
			
			if (obj.event == 'diff')
			{
				checkResult = 1;
				checkDiffValue = $("#goodsStockId"+goodsStockId).val();
				
				if (checkDiffValue == 0)
				{
					layer.msg('请输入差异数量，少于库存输入负数，大于当前库存输入正数', {icon: 5});
					return;
				}
			}
			
			var params = {
				'goodsStockId' : goodsStockId,
				'checkResult' : checkResult,
				'checkDiffValue' : checkDiffValue,
				'goodsId' : data.goodsId
			};
			
			$.post('store_goods_stock_check.html', params, function(data){
				var oks =  $.parseJSON(data); 
				if (oks.success == 0)
				{
					layer.msg(oks.msg, {icon: 6}, function(){
						tableIns.reload();
					});
				}
				else
				{
					layer.msg(oks.msg, {icon: 5});
				}
			});
		}
	});
	
	var tableIns2 = table.render({
		elem: '#goodsStockDetailTables',
		cols: [
			[
			{
				field: 'goodsStockDetailId',
				width: 80,
				title: '编码',
				align: 'left',
			},
			{
				field: 'stockCount0',
				width: 100,
				title: '入库库存',
				align: 'left',
			},
			{
				field: 'createDateText',
				width: 160,
				title: '入库时间',
				align: 'left',
			},
			{
				field: 'stockCount',
				width: 100,
				title: '剩余库存',
				align: 'left',
			},
			{
				field: 'productDateText',
				width: 120,
				title: '生产日期',
				align: 'left'
			},
			{
				field: 'expiredDateText',
				width: 120,
				title: '有效日期',
				align: 'left'
			},
			{
				width: 250,
				title: '供应商',
				align: 'left',
				field: 'providerName'
			},
			{
				title: '常用操作',
				width: 100,
				align: 'center',
				toolbar: '#viewTpl',
				fixed:"right"
			}]
		],
		url: 'store_goods_stock_data_detail.html',
		page: false,
		even: true
	});
	
	//监听工具条
	table.on('tool(goodsStockDetailTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event == 'dispatch') 
		{
			var goodsStockDetailId = data.goodsStockDetailId;
			$("#dispatchNum").val(data.stockCount);
			
			$("#dispatchPop").removeClass("layui-hide");
			
			layIndex = layer.open({
				type:1,
				btn: ['确定', '取消'],
				maxmin: true,
				title:'选择调往的门店',
				area: ['600px', '400px'],
				content: $("#dispatchPop"),
				yes: function(index, layero){ 
					
					var toStore = $("#toStore").val();
					if (toStore.length == 0)
					{
						layer.msg('请选择调拨的门店', {icon: 5});
						return false;
					}
					
					var dispatchNum = $("#dispatchNum").val();
					if (dispatchNum <= 0 || dispatchNum > data.stockCount)
					{
						layer.msg('调拨数量不正确，数量应该大于0小于原库存', {icon: 5});
						return false;
					}
					
					var params = {
						'goodsStockDetailId':goodsStockDetailId,
						'toStoreId':toStore,
						'dispatchStockCount':dispatchNum
					};
					
					$.post('store_stock_dispatch0.html', params, function(data){
						
						layer.close(index);
						$("#dispatchPop").addClass("layui-hide");
						
						var oks =  $.parseJSON(data); 
						if (oks.success == 0)
						{
							tableIns2.reload();
							layer.msg(oks.msg, {icon: 6});
						}
						else
						{
							layer.alert(oks.msg, {icon: 5});
						}
						
					});
				},
				btn2:function(index, layero){ 
					
					layer.close(index);
					$("#dispatchPop").addClass("layui-hide");
				},
				cancel: function(index, layero){ 
					layer.close(index);
					$("#dispatchPop").addClass("layui-hide");
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

});