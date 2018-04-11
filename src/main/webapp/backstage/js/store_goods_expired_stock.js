layui.use(['layer', 'form', 'upload', 'table', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		common = layui.common;
		upload = layui.upload;

    var expiredType = null;
		
	var tableIns = table.render({
		elem: '#goodsExpiredStockTables',
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
				width: 160,
				title: '商品条码',
				align: 'left',
			},
			{
				field: 'goodsName',
				width: 350,
				title: '商品名称',
				align: 'left',
			},
			{
				field: 'createDateText',
				width: 160,
				title: '入库时间',
				align: 'left',
			},
			{
				field: 'stockCount0',
				width: 100,
				title: '入库库存',
				align: 'left',
			},
			{
				field: 'stockCount',
				width: 120,
				title: '剩余库存',
				align: 'left',
				templet: '#expiredFlagTpl'
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
				field: 'expiredDay',
				width: 120,
				title: '剩余质保天数',
				align: 'left'
			}]

		],
		url: 'store_goods_expired_stock_data.html',
		where:{
			'expiredType':-1
		},
		page: false,
		even: true
	});
	
	//监听工具条
	table.on('tool(goodsExpiredStockTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event == 'view') 
		{
			
		}
	});
	
	form.on('select(expiredType)', function(data){
		expiredType = data.value;
	});
	
	form.on('submit(goodsQuerySubmit)',function(data){

		tableIns.reload({
			where:{
				'expiredType':expiredType
			},
	    });
		
    	return false;
	});

});