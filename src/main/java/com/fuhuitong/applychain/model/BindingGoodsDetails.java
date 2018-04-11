package com.fuhuitong.applychain.model;

import com.fuhuitong.applychain.utils.MoneyUtils;

public class BindingGoodsDetails {
    private Integer bindDetailId;

    private String goodsId;

    private String bindGoodsId;

    private String goodsName;

    private Integer bindGoodsCount;
    
    private String goodsCode;
    
    private Integer goodsPrice;
    
    private String goodsPriceText;

    public Integer getBindDetailId() {
        return bindDetailId;
    }

    public void setBindDetailId(Integer bindDetailId) {
        this.bindDetailId = bindDetailId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    public String getBindGoodsId() {
        return bindGoodsId;
    }

    public void setBindGoodsId(String bindGoodsId) {
        this.bindGoodsId = bindGoodsId == null ? null : bindGoodsId.trim();
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public Integer getBindGoodsCount() {
        return bindGoodsCount;
    }

    public void setBindGoodsCount(Integer bindGoodsCount) {
        this.bindGoodsCount = bindGoodsCount;
    }
    
    public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
    
    public String getGoodsCode() {
		return goodsCode;
	}
    
    public void setGoodsPrice(Integer goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
    
    public Integer getGoodsPrice() {
		return goodsPrice;
	}
    
    public String getGoodsPriceText() {
    	if (goodsPrice != null)
    	{
    		goodsPriceText = MoneyUtils.getMoneyText(goodsPrice);
    	}
    	
    	return goodsPriceText;
	}
}