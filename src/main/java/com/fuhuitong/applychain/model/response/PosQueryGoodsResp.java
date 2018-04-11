package com.fuhuitong.applychain.model.response;

public class PosQueryGoodsResp {
	
	private HeadResp headResp;
	
	private String retCode;
	
	private String goodsName;
	
	private int goodsPrice;

	public HeadResp getHeadResp() {
		return headResp;
	}

	public void setHeadResp(HeadResp headResp) {
		this.headResp = headResp;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	
	public String getRetCode() {
		return retCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public int getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(int goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	
	
}
