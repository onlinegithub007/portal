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
    
 // 日期控件
    laydate.render({
	  elem: '#endDateText' //指定元素
	});
	
	
	var tableIns = table.render({
		elem: '#storeOrdersGoodsTables',
		cols: [
			[ 
			{
				field: 'hour',
				width: 100,
				title: '时段',
				align: 'left',
			},
			{
				field: 'totalCount',
				width: 150,
				title: '销售数量',
				align: 'left',
			},
			{
				field: 'totalPriceText',
				width: 150,
				title: '销售金额(元)',
				align: 'right',
			},
			{
				field: 'percentText',
				width: 150,
				title: '金额占比',
				align: 'right',
			}]
		],
		url: 'stores_time_sale_data.html',
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
		if (createDateText.length > 0)
		{
			tableIns.reload({
				where :{
					'createDateText' : createDateText,
					'merGroupId': datas.merGroupId
				}
			});
		}
		else
		{
			layer.msg('请选择交易日期', {icon: 5});
		}
		
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