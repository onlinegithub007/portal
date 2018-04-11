package com.fuhuitong.applychain.model.response;

public class OrdersInfoItem 
{
	private String orderCode;
	
	private String orderDatetime;
	
	private int totalAmount;
	
	private int actTotalAmount;
	
	private int disCountAmount;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderDatetime() {
		return orderDatetime;
	}

	public void setOrderDatetime(String orderDatetime) {
		this.orderDatetime = orderDatetime;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getActTotalAmount() {
		return actTotalAmount;
	}

	public void setActTotalAmount(int actTotalAmount) {
		this.actTotalAmount = actTotalAmount;
	}

	public int getDisCountAmount() {
		return disCountAmount;
	}

	public void setDisCountAmount(int disCountAmount) {
		this.disCountAmount = disCountAmount;
	}
	
}
