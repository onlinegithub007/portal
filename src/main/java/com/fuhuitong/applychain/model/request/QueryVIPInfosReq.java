package com.fuhuitong.applychain.model.request;

/**
 * POS终端查询订单信息的请求参数类
 * @author haoqingfeng
 *
 */
public class QueryVIPInfosReq extends POSParam 
{
	private String orderId;
	
	private String memberCode;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	
}
