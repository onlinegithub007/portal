package com.fuhuitong.applychain.model;

public class GoodsStock {
    private Long goodsStockId;

    private String goodsId;

    private Integer ownerType;

    private String ownerId;

    private String goodsCode;

    private Integer stockAmount;
    
    private Integer stockAmount0;
    
    private String groupName;
    
    private String merGroupId;

    public Long getGoodsStockId() {
        return goodsStockId;
    }

    public void setGoodsStockId(Long goodsStockId) {
        this.goodsStockId = goodsStockId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(Integer ownerType) {
        this.ownerType = ownerType;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId == null ? null : ownerId.trim();
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode == null ? null : goodsCode.trim();
    }

    public Integer getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(Integer stockAmount) {
        this.stockAmount = stockAmount;
    }
    
    public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
    
    public String getGroupName() {
		return groupName;
	}
    
    public void setMerGroupId(String merGroupId) {
		this.merGroupId = merGroupId;
	}
    
    public String getMerGroupId() {
		return merGroupId;
	}
    
    public void setStockAmount0(Integer stockAmount0) {
		this.stockAmount0 = stockAmount0;
	}
    
    public Integer getStockAmount0() {
		return stockAmount0;
	}
}