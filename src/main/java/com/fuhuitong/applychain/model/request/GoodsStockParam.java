package com.fuhuitong.applychain.model.request;

import com.fuhuitong.applychain.model.response.HeadResp;

public class GoodsStockParam extends POSParam
{
	private String retCode;
	
	private String goodsCode;
	
	private String goodsName;
	
	private Integer goodsStock;
	
	private HeadResp headResp;
	
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Integer getGoodsStock() {
		return goodsStock;
	}
	public void setGoodsStock(Integer goodsStock) {
		this.goodsStock = goodsStock;
	}
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
	
	
	
}
