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
				field: 'groupName',
				width: 200,
				title: '门店',
				align: 'left',
			},
			{
				field: 'totalPriceText',
				width: 200,
				title: '销售额(元)',
				align: 'right',
			},
			{
				field: 'orderProfitText',
				width: 200,
				title: '销售利润(元)',
				align: 'right',
			}]
		],
		url: 'stores_sale_sort_data.html',
		page: false,
		even: true
	});
	
	function showChart()
	{
		$.post('stores_sale_sort_chart_data.html', null, function(json){
			  
			  var data =  $.parseJSON(json); 
			  
			  myChart.setOption({
				    title: {
						text: data.title
					},
				  	xAxis: {
			            data: data.storeName
			        },
			        series: [{
			        	name:'销售额',
			            yAxisIndex: 0,
			            data: data.stockTotalPrices
			        },
			        {
			        	name:'销售利润',
			            type:'bar',
			            yAxisIndex: 0,
			            data:data.storeOrderProfits
			        }]
			    });
		  });
	}
	
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
				'endDateText': datas.endDateText,
				'indexType': datas.indexType
			},
			done:function()
			{
				// 紧接着查询CHART数据
				showChart();
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