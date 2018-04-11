layui.use(['layer', 'form', 'laydate', 'table', 'common'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		laydate = layui.laydate,
		common = layui.common;

	// 日期控件
    laydate.render({
	  elem: '#clearingDateText' //指定元素
	  ,type: 'month'
	});
    
    laydate.render({
  	  elem: '#createDateText' //指定元素
  	});
	
	var tableIns = table.render({
		elem: '#storeDailyClearingTables',
		cols: [
			[ {
				field: 'clearingDateText',
				width: 120,
				title: '交易日',
				align: 'left',
			},
			{
				field: 'orderCount',
				width: 100,
				title: '订单总数',
				align: 'right',
			},
			{
				field: 'orderAmountText',
				width: 100,
				title: '营业总额',
				align: 'right',
			},
			{
				field: 'orderProfitText',
				width: 120,
				title: '毛利润(元)',
				align: 'right',
			},
			{
				field: 'cashOrderCount',
				width: 100,
				title: '现金交易数',
				align: 'right',
			},
			{
				field: 'cashOrderAmountText',
				width: 130,
				title: '现金交易额(元)',
				align: 'right',
			},
			/*
			{
				field: 'cashClearAmountText',
				width: 130,
				title: '现金缴存额(元)',
				align: 'right',
			},
			{
				width: 100,
				title: '现金缴存',
				align: 'center',
				templet: '#cashClearTpl'
			},*/
			{
				field: 'yipayOrderCount',
				width: 100,
				title: '翼支付数',
				align: 'right',
			},
			{
				field: 'yipayOrderAmountText',
				width: 130,
				title: '翼支付额(元)',
				align: 'right',
			},
			{
				field: 'weixinOrderCount',
				width: 100,
				title: '微信支付数',
				align: 'right',
			},
			{
				field: 'weixinOrderAmountText',
				width: 130,
				title: '微信支付额(元)',
				align: 'right',
			},
			{
				field: 'alipayOrderCount',
				width: 100,
				title: '支付宝数',
				align: 'right',
			},
			{
				field: 'alipayOrderAmountText',
				width: 130,
				title: '支付宝额(元)',
				align: 'right',
			}
			,{
				title: '常用操作',
				width: 160,
				align: 'center',
				toolbar: '#groupsbar',
				fixed:"right"
			}
			]

		],
		url: 'store_daliy_clearing_data.html',
		page: true,
		even: true
	});
	
	var tableIns2 = table.render({
		elem: '#storeDailyClearingGoodsTables',
		cols: [
			[ 
			{
				field: 'goodsTypeName',
				width: 150,
				title: '分类',
				align: 'left',
			},
			{
				field: 'goodsName',
				width: 350,
				title: '商品名称',
				align: 'left',
			}
			,{
				field: 'goodsSaleCount',
				width: 100,
				title: '销售数量',
				align: 'right',
			}
			,{
				field: 'goodsStockCount',
				width: 100,
				title: '剩余库存',
				align: 'right',
			}
			]

		],
		url: 'store_daily_clearing_details_data.html',
		page: true,
		even: true
	});
	
	//监听工具条
	table.on('tool(storeDailyClearingTables)', function(obj) {
		
		var data = obj.data;
		
		if (obj.event === 'refresh') 
		{
			var params = {
				'createDateText':data.clearingDateText
			};
			
			$.post('store_daliy_clearing_do.html', params, function(json){
				  
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
		}
		else if (obj.event === 'details') 
		{
			// 打开新窗口
			layer.open({
				type:2,
				maxmin: true,
				title:'日结交易详细信息',
				area: ['750px', '500px'],
				content: 'store_daily_clearing_details.html?createDateText=' + data.clearingDateText
			});
		}
		
	});
	
	$("#doDailyClear").click(function(){
		
		var params = {
			'createDateText':$("#createDateText").val()
		};
		
		$.post('store_daliy_clearing_do.html', params, function(json){
			  
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
	
	form.on('submit(storeClearingQuerySubmit)',function(data){

		var datas = data.field;
		var clearingDateText = datas.clearingDateText;
		
		tableIns.reload({
			where :{
				'clearingDateText' : clearingDateText
			}
		});
		
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