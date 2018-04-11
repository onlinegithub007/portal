package com.fuhuitong.applychain.model;

import java.util.Date;

public class GoodsMemberPrice {
    private Long goodsMembPriceId;

    private String merId;

    private String clientLevelId;

    private String goodsTypeId;

    private String goodsId;

    private Integer goodsPrice;
    
    private Integer pricePercent;

    private Date createDate;

    private Date expiredDate;

    private Boolean valid;

    public Long getGoodsMembPriceId() {
        return goodsMembPriceId;
    }

    public void setGoodsMembPriceId(Long goodsMembPriceId) {
        this.goodsMembPriceId = goodsMembPriceId;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public void setClientLevelId(String clientLevelId) {
		this.clientLevelId = clientLevelId;
	}
    
    public String getClientLevelId() {
		return clientLevelId;
	}

    public String getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(String goodsTypeId) {
        this.goodsTypeId = goodsTypeId == null ? null : goodsTypeId.trim();
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Integer goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
    
    public void setPricePercent(Integer pricePercent) {
		this.pricePercent = pricePercent;
	}
    
    public Integer getPricePercent() {
		return pricePercent;
	}
}