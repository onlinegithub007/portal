package com.fuhuitong.applychain.model;

import com.fuhuitong.applychain.utils.MoneyUtils;

public class BindingGoods {
    private String bindGoodsId;

    private String merId;

    private String merGroupId;

    private String goodsCode;

    private String goodsName;

    private Integer goodsPrice;
    
    private String goodsPriceText;

    private Boolean bindActive;

    public String getBindGoodsId() {
        return bindGoodsId;
    }

    public void setBindGoodsId(String bindGoodsId) {
        this.bindGoodsId = bindGoodsId == null ? null : bindGoodsId.trim();
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public String getMerGroupId() {
        return merGroupId;
    }

    public void setMerGroupId(String merGroupId) {
        this.merGroupId = merGroupId == null ? null : merGroupId.trim();
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode == null ? null : goodsCode.trim();
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public Integer getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Integer goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Boolean getBindActive() {
        return bindActive;
    }

    public void setBindActive(Boolean bindActive) {
        this.bindActive = bindActive;
    }
    
    public String getGoodsPriceText() {
	
    	if (goodsPrice != null)
    	{
    		goodsPriceText = MoneyUtils.getMoneyText(goodsPrice);
    	}
    	
    	return goodsPriceText;
	}
}