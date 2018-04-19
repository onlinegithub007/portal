package com.fuhuitong.applychain.model.request;

public class QueryOrderDetailReq extends POSParam {

	private String orderCode;

	private String payBillNumber;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getPayBillNumber() {
		return payBillNumber;
	}

	public void setPayBillNumber(String payBillNumber) {
		this.payBillNumber = payBillNumber;
	}
}
