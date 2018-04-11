layui.use(['layer', 'form', 'laydate', 'table', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		laydate = layui.laydate,
		common = layui.common;

	// 日期控件
    laydate.render({
	  elem: '#createDateText' //指定元素
	});
    
	var tableIns = table.render({
		elem: '#storeOrdersGoodsTables',
		cols: [
			[ 
			{
				field: 'goodsName',
				width: 300,
				title: '商品名称',
				align: 'left',
			},
			{
				field: 'goodsCode',
				width: 150,
				title: '商品条码',
				align: 'left',
			},
			{
				field: 'goodsCostText',
				width: 100,
				title: '进价(元)',
				align: 'right',
			},
			{
				field: 'goodsSalePriceText',
				width: 100,
				title: '零售价(元)',
				align: 'right',
			},
			{
				field: 'stockBuyCount',
				width: 90,
				title: '进货数量',
				align: 'right',
			},
			{
				field: 'stockBuyAmountText',
				width: 100,
				title: '金额(元)',
				align: 'right',
			},
			{
				field: 'goodsSaleCount',
				width: 80,
				title: '销量',
				align: 'right',
			},
			{
				field: 'goodsSaleAmountText',
				width: 100,
				title: '销售额(元)',
				align: 'right',
			},
			{
				field: 'goodsProfitText',
				width: 100,
				title: '毛利润(元)',
				align: 'right',
			},
			{
				field: 'dispatchInCount',
				width: 90,
				title: '调入数量',
				align: 'right',
			},
			{
				field: 'dispatchInAmountText',
				width: 100,
				title: '金额(元)',
				align: 'right',
			},
			{
				field: 'dispatchOutCount',
				width: 90,
				title: '调出数量',
				align: 'right',
			},
			{
				field: 'dispatchOutAmountText',
				width: 100,
				title: '金额(元)',
				align: 'right',
			},
			{
				field: 'returnCount',
				width: 90,
				title: '退货数量',
				align: 'right',
			},
			{
				field: 'returnAmountText',
				width: 100,
				title: '金额(元)',
				align: 'right',
			},
			{
				field: 'goodsStockCount',
				width: 90,
				title: '结余库存',
				align: 'right',
				fixed:"right"
			}
			]
		],
		url: 'stores_sale_reports_data.html',
		page: false,
		even: true
	});
	
	//监听工具条
	table.on('tool(storeOrdersTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event === 'view') 
		{
			$("#orderDetailsPop").removeClass("layui-hide");
			
			layer.open({
				type:1,
				btn: ['确定'],
				title:'查看订单明细',
				area: ['700px', '400px'],
				content: $("#orderDetailsPop"),
				yes: function(index, layero){
					layer.close(index);
					$("#orderDetailsPop").addClass("layui-hide");
				},
				cancel:function(index, layero)
				{
					layer.close(index);
					$("#orderDetailsPop").addClass("layui-hide");
				}
			});
			
			tableIns2.reload({
				where :{
					'orderId' : data.orderId
				}
			});
			
		}
		
	});
	
	form.on('submit(storeOrdersQuerySubmit)',function(data){

		var datas = data.field;
		var createDateText = datas.createDateText;
		
		tableIns.reload({
			where :{
				'createDateText' : createDateText,
				'merGroupId': datas.merGroupId
			}
		});
		
    	return false;
	});
	
	form.on('submit(storeOrdersExportSubmit)',function(data){

		var datas = data.field;
		var createDateText = datas.createDateText;
		
		window.location.href = 'stores_sale_reports_data_export.html?createDateText=' + createDateText + "&merGroupId=" + datas.merGroupId;
		
    	return false;
	});
	
	/*
	// 这个放在form事件的后面！
	form.verify({
		groupName: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value.length == 0)
	    	{
	    		return '部门姓名不能为空';
	    	}
		}
	}); 

	*/

});