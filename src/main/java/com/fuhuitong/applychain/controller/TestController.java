package com.fuhuitong.applychain.controller;

import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
public class TestController {
	
	@RequestMapping("test/test_check_new_version.html")
	public String checkNewView()
	{
		return "test/check_new_version";
	}
	
	@RequestMapping("test/pos_user_login.html")
	public String userLogin()
	{
		return "test/pos_user_login";
	}
	
	@RequestMapping("test/pos_query_goods_info.html")
	public String queryGoodsInfoLogin()
	{
		return "test/pos_query_goods_info";
	}
	
	@RequestMapping("test/pos_create_order.html")
	public String posCreateOrder()
	{
		return "test/pos_create_order";
	}
	
	@RequestMapping("test/pos_query_vip_infos.html")
	public String posQueryVipInfos()
	{
		return "test/pos_query_vip_infos";
	}
	
	@RequestMapping("test/pos_pay_order_success.html")
	public String posPayOrderSuccess()
	{
		return "test/pos_pay_order_success";
	}
	
	@RequestMapping("test/pos_query_orders.html")
	public String posQueryOrders()
	{
		return "test/pos_query_orders";
	}
	
	@RequestMapping("test/pos_query_order_detail.html")
	public String posGetOderDetail()
	{
		return "test/pos_query_order_detail";
	}
	
	@RequestMapping("test/pos_query_order_print.html")
	public String posGetOrderPrintInfo()
	{
		return "test/pos_query_order_print";
	}
	
	@RequestMapping("test/pos_query_member_info.html")
	public String posQueryMemberInfo()
	{
		return "test/pos_query_member_info";
	}
}
