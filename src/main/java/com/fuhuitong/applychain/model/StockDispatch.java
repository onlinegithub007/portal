package com.fuhuitong.applychain.model;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class StockDispatch {
    private Integer stockDispatchId;
    
    private String purchaseOrderId;

    private String goodsId;
    private String goodsName;
    
    private String merId;

    private Integer goodsStockDetailId;

    private String operator;

    private String operatorName;

    private String providerId;

    private String providerName;

    private String providerLinkman;

    private String fromStoreId;

    private String fromStoreName;

    private String toStoreId;

    private String toStoreName;

    private Float goodsCost;

    private Date productDate;
    
    private String productDateText;

    private Date expiredDate;
    
    private String expiredDateText;

    private Integer currentStockCount;

    private Integer dispatchStockCount;

    private Date createDate;
    
    private String createDateText;
    
    private String dateText;

    private Integer dispatchStatus;
    
    private String measurUnit;
    
    private String providerUnit;

    private Integer providerUnitMultiple = 1;

    private String memo;
    
    private Integer[] stockDispatchIds = null;

    public Integer getStockDispatchId() {
        return stockDispatchId;
    }

    public void setStockDispatchId(Integer stockDispatchId) {
        this.stockDispatchId = stockDispatchId;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public Integer getGoodsStockDetailId() {
        return goodsStockDetailId;
    }

    public void setGoodsStockDetailId(Integer goodsStockDetailId) {
        this.goodsStockDetailId = goodsStockDetailId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName == null ? null : operatorName.trim();
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId == null ? null : providerId.trim();
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName == null ? null : providerName.trim();
    }

    public String getProviderLinkman() {
        return providerLinkman;
    }

    public void setProviderLinkman(String providerLinkman) {
        this.providerLinkman = providerLinkman == null ? null : providerLinkman.trim();
    }

    public String getFromStoreId() {
        return fromStoreId;
    }

    public void setFromStoreId(String fromStoreId) {
        this.fromStoreId = fromStoreId == null ? null : fromStoreId.trim();
    }

    public String getFromStoreName() {
        return fromStoreName;
    }

    public void setFromStoreName(String fromStoreName) {
        this.fromStoreName = fromStoreName == null ? null : fromStoreName.trim();
    }

    public String getToStoreId() {
        return toStoreId;
    }

    public void setToStoreId(String toStoreId) {
        this.toStoreId = toStoreId == null ? null : toStoreId.trim();
    }

    public String getToStoreName() {
        return toStoreName;
    }

    public void setToStoreName(String toStoreName) {
        this.toStoreName = toStoreName == null ? null : toStoreName.trim();
    }

    public Float getGoodsCost() {
        return goodsCost;
    }

    public void setGoodsCost(Float goodsCost) {
        this.goodsCost = goodsCost;
    }

    public Date getProductDate() {
        return productDate;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Integer getCurrentStockCount() {
        return currentStockCount;
    }

    public void setCurrentStockCount(Integer currentStockCount) {
        this.currentStockCount = currentStockCount;
    }

    public Integer getDispatchStockCount() {
        return dispatchStockCount;
    }

    public void setDispatchStockCount(Integer dispatchStockCount) {
        this.dispatchStockCount = dispatchStockCount;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getDispatchStatus() {
        return dispatchStatus;
    }

    public void setDispatchStatus(Integer dispatchStatus) {
        this.dispatchStatus = dispatchStatus;
    }

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
    public String getMeasurUnit() {
		return measurUnit;
	}

	public void setMeasurUnit(String measurUnit) {
		this.measurUnit = measurUnit;
	}

	public String getProviderUnit() {
		return providerUnit;
	}

	public void setProviderUnit(String providerUnit) {
		this.providerUnit = providerUnit;
	}

	public Integer getProviderUnitMultiple() {
		return providerUnitMultiple;
	}

	public void setProviderUnitMultiple(Integer providerUnitMultiple) {
		this.providerUnitMultiple = providerUnitMultiple;
	}

	public String getExpiredDateText() {
		
    	if (expiredDate != null)
    	{
    		expiredDateText = DateFormatUtils.format(expiredDate, "yyyy-MM-dd");
    	}
    	
    	return expiredDateText;
	}
    
    public String getProductDateText() {
    	
    	if (productDate != null)
    	{
    		productDateText = DateFormatUtils.format(productDate, "yyyy-MM-dd");
    	}
    	
    	return productDateText;
	}
    
    public String getCreateDateText() {
    	if (createDate != null)
    	{
    		createDateText = DateFormatUtils.format(createDate, "yyyy-MM-dd HH:mm:ss");
    	}
    	
    	return createDateText;
	}
    
    public void setStockDispatchIds(Integer[] stockDispatchIds) {
		this.stockDispatchIds = stockDispatchIds;
	}
    
    public Integer[] getStockDispatchIds() {
		return stockDispatchIds;
	}
    
    public void setPurchaseOrderId(String purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}
    
    public String getPurchaseOrderId() {
		return purchaseOrderId;
	}
    
    public String getMemo() {
		
    	if (this.measurUnit != null && this.providerUnit != null && this.providerUnitMultiple != null)
		{
    		if (this.measurUnit.equalsIgnoreCase(this.providerUnit))
    		{
    			memo = this.dispatchStockCount + this.measurUnit;
    		}
    		else
    		{
    			int providerUnitCount = this.dispatchStockCount / this.providerUnitMultiple;
    			int measurUnitCount = this.dispatchStockCount % this.providerUnitMultiple;
    			
    			memo = providerUnitCount + this.providerUnit;
    			if (measurUnitCount > 0)
    			{
    				memo += measurUnitCount + this.measurUnit;
    			}
    			else
    			{
    				memo += "整";
    			}
    		}
		}
    	
    	return memo;
	}
    
    public void setDateText(String dateText) {
		this.dateText = dateText;
	}
    
    public String getDateText() {
		return dateText;
	}
}