package com.fuhuitong.applychain.model;

public class GoodsProviders 
{
    private String gpId;

    private String goodsId;

    private String merUserId;

    private Integer gpStatus;
    
    private Integer goodsCost;
    
    private String userName;
    
    private String merName;
    
    private String providerUnit;

    private Integer providerCost;

    public String getGpId() {
        return gpId;
    }

    public void setGpId(String gpId) {
        this.gpId = gpId == null ? null : gpId.trim();
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    public String getMerUserId() {
        return merUserId;
    }

    public void setMerUserId(String merUserId) {
        this.merUserId = merUserId == null ? null : merUserId.trim();
    }

    public Integer getGpStatus() {
        return gpStatus;
    }

    public void setGpStatus(Integer gpStatus) {
        this.gpStatus = gpStatus;
    }
    
    public Integer getGoodsCost() {
		return goodsCost;
	}

	public void setGoodsCost(Integer goodsCost) {
		this.goodsCost = goodsCost;
	}
	
	public void setMerName(String merName) {
		this.merName = merName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getMerName() {
		return merName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getProviderUnit() {
        return providerUnit;
    }

    public void setProviderUnit(String providerUnit) {
        this.providerUnit = providerUnit == null ? null : providerUnit.trim();
    }

    public Integer getProviderCost() {
        return providerCost;
    }

    public void setProviderCost(Integer providerCost) {
        this.providerCost = providerCost;
    }
}