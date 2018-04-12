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
		elem: '#storeOrdersTables',
		cols: [
			[ {
				field: 'groupName',
				width: 200,
				title: '门店名称',
				align: 'left',
			},
			{
				field: 'operatorName',
				width: 100,
				title: '收银员',
				align: 'left',
			},
			{
				field: 'orderCode',
				width: 250,
				title: '订单编号',
				align: 'left',
			},
			{
				field: 'termCode',
				width: 100,
				title: '设备型号',
				align: 'left',
			},
			{
				field: 'termSn',
				width: 100,
				title: 'SN号',
				align: 'left',
			},
			{
				field: 'createDateText',
				width: 180,
				title: '订单时间',
				align: 'left',
			},
			{
				field: 'payDateText',
				width: 180,
				title: '支付时间',
				align: 'left',
			},
			{
				field: 'totalPriceText',
				width: 130,
				title: '订单总金额(元)',
				align: 'right',
			},
			{
				field: 'finalPriceText',
				width: 130,
				title: '支付金额(元)',
				align: 'right',
			},
			{
				field: 'discountAmountText',
				width: 150,
				title: '促销折让金额(元)',
				align: 'right',
			},
			{
				field: 'chargeRate',
				width: 150,
				title: '手续率',
				align: 'right',

			},
                {
                    field: 'chargeAmount',
                    width: 150,
                    title: '手续费',
                    align: 'right',

                },
			{
				field: 'payMethod',
				width: 100,
				title: '支付方式',
				align: 'left',
				templet: '#payMethodTpl'
			},
			{
				width: 80,
				title: '核销',
				align: 'left',
				templet: '#confirmTpl'
			},
			{
				field: 'orderConfirmDateText',
				width: 170,
				title: '核销时间',
				align: 'left',
			},
			{
				field: 'orderConfirmPerson',
				width: 100,
				title: '核销人',
				align: 'left',
			}
			,{
				title: '常用操作',
				width: 160,
				align: 'center',
				toolbar: '#groupsbar',
				fixed:"right"
			}]

		],
		url: 'master_stores_orders_records_data.html',
		page: true,
		even: true
	});
	
	var tableIns2 = table.render({
		elem: '#ordersDetailsTables',
		cols: [
			[ 
			{
				field: 'goodsCode',
				width: 150,
				title: '商品编码',
				align: 'left',
			},
			{
				field: 'goodsName',
				width: 200,
				title: '商品名称',
				align: 'left',
			},
			{
				field: 'goodsAmount',
				width: 100,
				title: '商品数量',
				align: 'left',
			},
			{
				field: 'goodsSalePriceText',
				width: 100,
				title: '商品单价',
				align: 'right',
			},
			{
				field: 'goodsTotalPriceText',
				width: 100,
				title: '商品总价',
				align: 'right',
			}]
		],
		url: 'store_orders_goods_data.html',
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
		else if (obj.event == 'confirm') 
		{
			layer.confirm('确认要对当前订单进行核销吗？', function(index) {
				
				var params = {
					'orderId':data.orderId
				};
				
				$.post('master_stores_orders_records_confirm.html', params, function(json){
					
					var oks =  $.parseJSON(json); 
					
					if (oks.success == 0)
					{
						layer.msg(oks.msg, {icon: 6});
						
						tableIns.reload();
					}
					else
					{
						layer.msg(oks.msg, {icon: 5});
					}
				});
			});
			
		}
		
	});
	
	form.on('submit(storeOrdersQuerySubmit)',function(data){

		var datas = data.field;
		var createDateText = datas.createDateText;
		var endDateText = datas.endDateText;
		var termCode =datas.termCode;
		var termSn   = datas.termSn
		tableIns.reload({
			where :{
				'createDateText' : createDateText,
				'endDateText':endDateText,
				'merGroupId': datas.merGroupId,
				'orderCode':datas.orderCode,
				'termCode':termCode,
				'termSn'  :termSn
			}
		});
		
    	return false;
	});
	
	form.on('submit(storeOrdersExportSubmit)',function(data){
		var datas = data.field;
		var createDateText = datas.createDateText;
		var endDateText = datas.endDateText;
		var termCode = datas.termCode;
		var termSn   = datas.termSn
		
		window.location.href = 'exportAllOrders.html?createDateText='+createDateText+'&endDateText='+endDateText+'&termCode='+termCode+'&termSn='+termSn;
		
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