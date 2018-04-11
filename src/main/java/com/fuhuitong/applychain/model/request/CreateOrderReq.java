package com.fuhuitong.applychain.model.request;

public class CreateOrderReq extends POSParam
{
	private String memberPayToken;
	
	private String goodsCodes;
	
	private Integer orderAmount;
	
	public void setGoodsCodes(String goodsCodes) {
		this.goodsCodes = goodsCodes == null ? null : goodsCodes.trim();
	}
	
	public String getGoodsCodes() {
		return goodsCodes;
	}
	
	public void setMemberPayToken(String memberPayToken) {
		this.memberPayToken = memberPayToken;
	}
	
	public String getMemberPayToken() {
		return memberPayToken;
	}
	
	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}
	
	public Integer getOrderAmount() {
		return orderAmount;
	}
}
