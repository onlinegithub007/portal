layui.use(['layer', 'form', 'laydate', 'upload', 'table', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		common = layui.common,
		laydate = layui.laydate,
		upload = layui.upload;
		
	var tableStockGoods = table.render({
		elem: '#purchaseOrderGoodsTables',
		cols: [
			[
			{
				field: 'goodsCode',
				width: 140,
				title: '商品编码',
				align: 'left',
			},
			{
				field: 'goodsName',
				width: 250,
				title: '商品名称',
				align: 'left',
			},
			{
				field: 'goodsAmount',
				width: 90,
				title: '采购数量',
				align: 'right'
			},
			{
				field: 'providerUnit',
				width: 60,
				title: '单位',
				align: 'left'
			},
			{
				field: 'goodsAckAmount',
				width: 90,
				title: '实收数量',
				align: 'right'
			}
			,{
				field: 'goodsCostText',
				width: 120,
				title: '采购价格(元)',
				align: 'right',
				templet: '#goodsCostTextTpl'
			}
			]

		],
		url: 'store_purchase_order_view_goods.html?purchaseOrderId=' + purchaseOrderId,
		page: false,
		even: true
	});
	
	
	$("#printBtn").click(function(){
		window.print();
	});
	
	
});