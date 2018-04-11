package com.fuhuitong.applychain.model;

import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;

public class BatchStockOrders extends Pagable{
    private String batchStockOrderId;

    private String merId;

    private Date createDate;
    
    private String createDateText;

    private String creator;

    private Integer goodsCount;
    
    private Integer storesCount;

    private Integer goodsTotalPrice;

    private Integer batchOrderStatus;
    
    public String getBatchStockOrderId() {
        return batchStockOrderId;
    }

    public void setBatchStockOrderId(String batchStockOrderId) {
        this.batchStockOrderId = batchStockOrderId == null ? null : batchStockOrderId.trim();
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public Integer getGoodsTotalPrice() {
        return goodsTotalPrice;
    }

    public void setGoodsTotalPrice(Integer goodsTotalPrice) {
        this.goodsTotalPrice = goodsTotalPrice;
    }

    public Integer getBatchOrderStatus() {
        return batchOrderStatus;
    }

    public void setBatchOrderStatus(Integer batchOrderStatus) {
        this.batchOrderStatus = batchOrderStatus;
    }
    
    public String getCreateDateText() {
		if (createDate != null)
		{
			createDateText = DateFormatUtils.format(createDate, "yyyy-MM-dd HH:mm:ss");
		}
    	
    	return createDateText;
	}
    
    public void setStoresCount(Integer storesCount) {
		this.storesCount = storesCount;
	}
    
    public Integer getStoresCount() {
		return storesCount;
	}
    
}