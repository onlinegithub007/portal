package com.fuhuitong.applychain.model;

public class BatchStockGoodsDetails {
    private Integer batchStockGoodsId;

    private String batchStockOrderId;

    private String goodsId;

    private String goodsName;

    private String goodsCode;
    
    private Integer GoodsCount;
    
    private Integer providerCount;
    private String providerId;
    private String providerName;
    private Integer providerCost;
    private String providerUnit;

    public Integer getBatchStockGoodsId() {
        return batchStockGoodsId;
    }

    public void setBatchStockGoodsId(Integer batchStockGoodsId) {
        this.batchStockGoodsId = batchStockGoodsId;
    }

    public String getBatchStockOrderId() {
        return batchStockOrderId;
    }

    public void setBatchStockOrderId(String batchStockOrderId) {
        this.batchStockOrderId = batchStockOrderId == null ? null : batchStockOrderId.trim();
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode == null ? null : goodsCode.trim();
    }
    
    public void setGoodsCount(Integer goodsCount) {
		GoodsCount = goodsCount;
	}
    
    public Integer getGoodsCount() {
		return GoodsCount;
	}

	public Integer getProviderCount() {
		return providerCount;
	}

	public void setProviderCount(Integer providerCount) {
		this.providerCount = providerCount;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public Integer getProviderCost() {
		return providerCost;
	}

	public void setProviderCost(Integer providerCost) {
		this.providerCost = providerCost;
	}

	public String getProviderUnit() {
		return providerUnit;
	}

	public void setProviderUnit(String providerUnit) {
		this.providerUnit = providerUnit;
	}
    
}