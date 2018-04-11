package com.fuhuitong.applychain.model;

import java.util.Date;

public class StoreSalesStat {
    private Integer saleStatId;

    private String merGroupId;

    private String merId;

    private String goodsId;

    private Date createDate;
    
    private String createDateText;

    private Integer goodsSaleCount;

    private Integer goodsStockCount;
    
    private String goodsTypeName;
    
    private String goodsName;

    public Integer getSaleStatId() {
        return saleStatId;
    }

    public void setSaleStatId(Integer saleStatId) {
        this.saleStatId = saleStatId;
    }

    public String getMerGroupId() {
        return merGroupId;
    }

    public void setMerGroupId(String merGroupId) {
        this.merGroupId = merGroupId == null ? null : merGroupId.trim();
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getGoodsSaleCount() {
        return goodsSaleCount;
    }

    public void setGoodsSaleCount(Integer goodsSaleCount) {
        this.goodsSaleCount = goodsSaleCount;
    }

    public Integer getGoodsStockCount() {
        return goodsStockCount;
    }

    public void setGoodsStockCount(Integer goodsStockCount) {
        this.goodsStockCount = goodsStockCount;
    }
    
    public void setCreateDateText(String createDateText) {
		this.createDateText = createDateText;
	}
    
    public String getCreateDateText() {
		return createDateText;
	}

	public String getGoodsTypeName() {
		return goodsTypeName;
	}

	public void setGoodsTypeName(String goodsTypeName) {
		this.goodsTypeName = goodsTypeName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
    
    
}