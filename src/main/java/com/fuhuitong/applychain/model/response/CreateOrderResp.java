package com.fuhuitong.applychain.model.response;

import java.util.ArrayList;

public class CreateOrderResp 
{
	private HeadResp headResp;
	
	private String retCode;
	
	private String orderId;
	
	private String addtional;
	
	private ArrayList<GoodsInOrderResp> goods = new ArrayList<GoodsInOrderResp>();
	
	private int finalPrice;
	
	private int totalPrice;
	
	private int discountPrice;
	
	private Integer orderAmount;
	
	public void setHeadResp(HeadResp headResp) {
		this.headResp = headResp;
	}
	
	public HeadResp getHeadResp() {
		return headResp;
	}
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getOrderId() {
		return orderId;
	}
	
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	
	public String getRetCode() {
		return retCode;
	}
	
	public ArrayList<GoodsInOrderResp> getGoods() {
		return goods;
	}

	public void setGoods(ArrayList<GoodsInOrderResp> goods) {
		this.goods = goods;
	}

	public int getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(int finalPrice) {
		this.finalPrice = finalPrice;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setAddtional(String addtional) {
		this.addtional = addtional;
	}
	
	public String getAddtional() {
		return addtional;
	}

	public int getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(int discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Integer getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}
	
}
