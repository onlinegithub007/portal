package com.fuhuitong.applychain.model;

public class GoodsPrice {
    
	private Long goodsPriceId;

    private String goodsId;

    private String merGroupId;

    private Integer goodsPrice;
    
    private Integer storeGoodsCost;

    private Integer goodsBulkPrice;
    
    private Integer providerUnitMultiple;

    public Long getGoodsPriceId() {
        return goodsPriceId;
    }

    public void setProviderUnitMultiple(Integer providerUnitMultiple) {
		this.providerUnitMultiple = providerUnitMultiple;
	}
    
    public Integer getProviderUnitMultiple() {
		return providerUnitMultiple;
	}
    
    public void setGoodsPriceId(Long goodsPriceId) {
        this.goodsPriceId = goodsPriceId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getMerGroupId() {
        return merGroupId;
    }

    public void setMerGroupId(String merGroupId) {
        this.merGroupId = merGroupId == null ? null : merGroupId.trim();
    }

    public Integer getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Integer goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsBulkPrice() {
        return goodsBulkPrice;
    }

    public void setGoodsBulkPrice(Integer goodsBulkPrice) {
        this.goodsBulkPrice = goodsBulkPrice;
    }
    
    public void setStoreGoodsCost(Integer storeGoodsCost) {
		this.storeGoodsCost = storeGoodsCost;
	}
    
    public Integer getStoreGoodsCost() {
		return storeGoodsCost;
	}
}