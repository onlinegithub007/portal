package com.fuhuitong.applychain.model;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class GoodsStockDetail 
{
    private Integer goodsStockDetailId;
    
    private String parentTypeId;
    private String goodsTypeId;
    private String merId;

    private String stockBillId;

    private String goodsId;
    
    private String ownerId;
    
    private String storeName;

    private Integer stockCount;
    
    private Integer stockCount0;
    
    private Integer stockBillType;

    private Float goodsCost;

    private Date productDate;
    
    private String productDateText;

    private Date expiredDate;
    
    private Integer hasQualityPeriod;
    
    /**
     * 超期类型，查询使用
     */
    private Integer expiredType;
    
    private String expiredDateText;
    
    private Date createDate;
    
    private String createDateText;
    
    private String dateText;
    
    private Integer stockDetailStatus;//STOCK_DETAIL_STATUS
    
    private Integer referStockDetailId; //   REFER_STOCK_DETAIL_ID int comment '在出库还未确认时，引用的原库存明细ID',
    
    private String goodsTypeName;
    private String goodsName;
    private String goodsCode;
    
    private Integer expiredDay;
    
    private Double expiredPercent;
    
    private Boolean locked;
    
    // 下面几个属性是关联属性，用于显示供应商
    private String providerId;
    private String providerLinkman;
    private String providerLinkmanPhone;
    private String providerLinkmanEmail;
    private String providerName;
    private String purchaseOrderId;
    private String stockOrderId;

    public Integer getGoodsStockDetailId() {
        return goodsStockDetailId;
    }

    public void setGoodsStockDetailId(Integer goodsStockDetailId) {
        this.goodsStockDetailId = goodsStockDetailId;
    }

    public String getStockBillId() {
        return stockBillId;
    }

    public void setStockBillId(String stockBillId) {
        this.stockBillId = stockBillId == null ? null : stockBillId.trim();
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
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
    
    public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
    
    public String getOwnerId() {
		return ownerId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Integer getStockCount0() {
		return stockCount0;
	}

	public void setStockCount0(Integer stockCount0) {
		this.stockCount0 = stockCount0;
	}

	public Integer getStockBillType() {
		return stockBillType;
	}

	public void setStockBillType(Integer stockBillType) {
		this.stockBillType = stockBillType;
	}
	
	
	public String getProductDateText() {
		
		if (productDate != null)
		{
			productDateText = DateFormatUtils.format(productDate, "yyyy-MM-dd");
		}
		
		return productDateText;
	}

	public String getExpiredDateText() {
		
		if (expiredDate != null)
		{
			expiredDateText = DateFormatUtils.format(expiredDate, "yyyy-MM-dd");
		}
		
		return expiredDateText;
	}

	public String getCreateDateText() {
		
		if (createDate != null)
		{
			createDateText = DateFormatUtils.format(createDate, "yyyy-MM-dd HH:mm:ss");
		}
		
		return createDateText;
	}

	public Integer getStockDetailStatus() {
		return stockDetailStatus;
	}

	public void setStockDetailStatus(Integer stockDetailStatus) {
		this.stockDetailStatus = stockDetailStatus;
	}

	public Integer getReferStockDetailId() {
		return referStockDetailId;
	}

	public void setReferStockDetailId(Integer referStockDetailId) {
		this.referStockDetailId = referStockDetailId;
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

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	
	public void setExpiredType(Integer expiredType) {
		this.expiredType = expiredType;
	}
	
	public Integer getExpiredType() {
		return expiredType;
	}
	
	public void setExpiredDay(Integer expiredDay) {
		this.expiredDay = expiredDay;
	}
	
	public Integer getExpiredDay() {
		return expiredDay;
	}
	
	public void setExpiredPercent(Double expiredPercent) {
		this.expiredPercent = expiredPercent;
	}
	
	public Double getExpiredPercent() {
		return expiredPercent;
	}
	
	public void setHasQualityPeriod(Integer hasQualityPeriod) {
		this.hasQualityPeriod = hasQualityPeriod;
	}
	
	public Integer getHasQualityPeriod() {
		return hasQualityPeriod;
	}
	
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	public String getStoreName() {
		return storeName;
	}

	public String getParentTypeId() {
		return parentTypeId;
	}

	public void setParentTypeId(String parentTypeId) {
		this.parentTypeId = parentTypeId;
	}

	public String getGoodsTypeId() {
		return goodsTypeId;
	}

	public void setGoodsTypeId(String goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getProviderLinkman() {
		return providerLinkman;
	}

	public void setProviderLinkman(String providerLinkman) {
		this.providerLinkman = providerLinkman;
	}

	public String getProviderLinkmanPhone() {
		return providerLinkmanPhone;
	}

	public void setProviderLinkmanPhone(String providerLinkmanPhone) {
		this.providerLinkmanPhone = providerLinkmanPhone;
	}

	public String getProviderLinkmanEmail() {
		return providerLinkmanEmail;
	}

	public void setProviderLinkmanEmail(String providerLinkmanEmail) {
		this.providerLinkmanEmail = providerLinkmanEmail;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(String purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public String getStockOrderId() {
		return stockOrderId;
	}

	public void setStockOrderId(String stockOrderId) {
		this.stockOrderId = stockOrderId;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}
	
	public void setDateText(String dateText) {
		this.dateText = dateText;
	}
	
	public String getDateText() {
		return dateText;
	}
}