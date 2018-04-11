package com.fuhuitong.applychain.model.response;

import java.util.ArrayList;

public class QueryOrderDetailResp 
{
	private HeadResp headResp;
	
	private String retCode;
	
	private String orderCode;
	
	private String orderDatetime;
	
	private int goodsCount;
	
	private int totalAmount;
	
	private int actTotalAmount;
	
	private int discountAmount;
	
	private String memberCode;
	
	private String memberName;

	private ArrayList<GoodsInfoItem> goodsList = new ArrayList<GoodsInfoItem>();
	
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

	public int getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(int goodsCount) {
		this.goodsCount = goodsCount;
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

	public int getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(int discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public ArrayList<GoodsInfoItem> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(ArrayList<GoodsInfoItem> goodsList) {
		this.goodsList = goodsList;
	}
	
	public void add(GoodsInfoItem goodsInfo)
	{
		this.goodsList.add(goodsInfo);
	}
}
