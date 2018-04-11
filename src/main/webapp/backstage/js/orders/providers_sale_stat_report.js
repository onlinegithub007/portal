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
		elem: '#userTables',
		cols: [
			[
			{
				field: 'goodsCode',
				width: 150,
				title: '商品条码',
				align: 'left',
			},
			{
				field: 'goodsName',
				width: 260,
				title: '商品名称',
				align: 'left',
			},
			{
				field: 'goodsAmount',
				width: 100,
				title: '商品销量',
				align: 'right',
			}]

		],
		url: 'providers_sale_stat_report_data.html',
		true: false,
		even: true
	});

	//监听工具条
	table.on('tool(userTables)', function(obj) {
		var data = obj.data;
		if (obj.event == 'sell') 
		{
			var merUserId = data.merUserId;
			window.location.href = 'providers_sale_stat_report.html?merUserId=' + merUserId;
		}
	});
	
	form.on('submit(storeOrdersQuerySubmit)', function(data){
	    
		var datas = data.field;
		
		tableIns.reload({
			where :{
				'createDateText' : datas.createDateText,
				'endDateText': datas.endDateText
			}
		});
		
		return false;
	});
	
//	form.on('submit(merGpsSubmit)',function(data){
//
//		var datas = data.field;
//		
//		$("#merGpsSubmitBtn").addClass("layui-disabled");
//		$("#merGpsSubmitBtn").attr("disabled" , "disabled");
//		
//		$.post('save_merchant_gps.html', datas, function(json){
//			  var oks =  $.parseJSON(json); 
//			  
//			  if (oks.success == 1)
//			  {
//				  // 更新失败
//				  layer.msg(oks.msg, {icon: 5});
//				  
//				  $("#merGpsSubmitBtn").removeClass("layui-disabled");
//				  $("#merGpsSubmitBtn").removeAttr("disabled" , "disabled");
//			  }
//			  else if (oks.success == 0)
//			  {
//				  window.location.href = 'merchant_providers.html';
//			  }
//		  });
//		
//    	return false;
//	});
//	
//	// 这个放在form事件的后面！
//	form.verify({
//		userName:function(value, item){ //value：表单的值、item：表单的DOM对象
//		    if(value.length == 0)
//	    	{
//	    		return '供应商联系人不能为空';
//	    	}
//		}
//		,userAccount:function(value, item){ //value：表单的值、item：表单的DOM对象
//		    if(value.length == 0)
//	    	{
//	    		return '供应商账号不能为空，可以采用供应商手机号或邮件作为账号';
//	    	}
//		}
//		,merName:function(value, item){ //value：表单的值、item：表单的DOM对象
//		    if(value.length == 0)
//	    	{
//	    		return '供应商联单位不能为空';
//	    	}
//		}
//		,mobilePhone:function(value, item){ //value：表单的值、item：表单的DOM对象
//		    if(value.length == 0)
//	    	{
//	    		return '用户手机号不能为空';
//	    	}
//		}
//		,userEmail:function(value, item){ //value：表单的值、item：表单的DOM对象
//		    if(value.length == 0)
//	    	{
//	    		return '用户邮箱账号不能为空';
//	    	}
//		}
//	});
});