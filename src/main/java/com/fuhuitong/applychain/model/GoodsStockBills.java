package com.fuhuitong.applychain.model;

import java.util.Date;

public class GoodsStockBills {
    private String stockBillId;

    private String purchaseOrderId;

    private String stockOrderId;

    private String ownerId;

    private Integer ownerType;

    private Date createDate;

    private Integer stockBillSource;

    private Integer stockBillType;
    
    private Integer goodsAmount;
    
    private Integer stockBillStatus;

    public String getStockBillId() {
        return stockBillId;
    }

    public void setStockBillId(String stockBillId) {
        this.stockBillId = stockBillId == null ? null : stockBillId.trim();
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId == null ? null : purchaseOrderId.trim();
    }

    public String getStockOrderId() {
        return stockOrderId;
    }

    public void setStockOrderId(String stockOrderId) {
        this.stockOrderId = stockOrderId == null ? null : stockOrderId.trim();
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId == null ? null : ownerId.trim();
    }

    public Integer getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(Integer ownerType) {
        this.ownerType = ownerType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getStockBillSource() {
        return stockBillSource;
    }

    public void setStockBillSource(Integer stockBillSource) {
        this.stockBillSource = stockBillSource;
    }

    public Integer getStockBillType() {
        return stockBillType;
    }

    public void setStockBillType(Integer stockBillType) {
        this.stockBillType = stockBillType;
    }

	public Integer getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(Integer goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	public Integer getStockBillStatus() {
		return stockBillStatus;
	}

	public void setStockBillStatus(Integer stockBillStatus) {
		this.stockBillStatus = stockBillStatus;
	}
    
    
}