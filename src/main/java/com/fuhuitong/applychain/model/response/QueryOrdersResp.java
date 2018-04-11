package com.fuhuitong.applychain.model.response;

import java.util.ArrayList;

public class QueryOrdersResp 
{
	private HeadResp headResp;
	
	private String retCode;
	
	private String orderDate;
	
	private long orderCount;
	
	private long orderTotalAmount;
	
	private long orderActToalAmount;
	
	private long discountAmount;
	
	private ArrayList<OrdersInfoItem> ordersList = new ArrayList<OrdersInfoItem>();

	public HeadResp getHeadResp() {
		return headResp;
	}

	public void setHeadResp(HeadResp headResp) {
		this.headResp = headResp;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public long getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(long orderCount) {
		this.orderCount = orderCount;
	}

	public long getOrderTotalAmount() {
		return orderTotalAmount;
	}

	public void setOrderTotalAmount(long orderTotalAmount) {
		this.orderTotalAmount = orderTotalAmount;
	}

	public long getOrderActToalAmount() {
		return orderActToalAmount;
	}

	public void setOrderActToalAmount(long orderActToalAmount) {
		this.orderActToalAmount = orderActToalAmount;
	}

	public long getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(long discountAmount) {
		this.discountAmount = discountAmount;
	}

	public ArrayList<OrdersInfoItem> getOrdersList() {
		return ordersList;
	}

	public void setOrdersList(ArrayList<OrdersInfoItem> ordersList) {
		this.ordersList = ordersList;
	}

	public void add(OrdersInfoItem item)
	{
		this.ordersList.add(item);
	}
}
