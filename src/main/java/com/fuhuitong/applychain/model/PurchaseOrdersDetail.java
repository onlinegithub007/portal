package com.fuhuitong.applychain.model;

import com.fuhuitong.applychain.utils.MoneyUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Date;

public class PurchaseOrdersDetail 
{
    private Integer purchaseDetailId;

    private String goodsTypeId;

    private String purchaseOrderId;
    
    private String stockOrderId;
    
    private Integer stockOrderDetailId;

    private String goodsId;

    private Integer goodsCost;
    
    private String goodsCostText;

    private Integer goodsAmount;
    
    private String goodsName;
    
    private String goodsCode;

    private Integer goodsAckAmount;
    
    private Integer providerAckAmount;
    
    private Integer providerAckStatus;
    
    private Date productDate;
    
    private String productDateText;

    private Date expiredDate;
    
    private String expiredDateText;

    private Integer ackStatus;
    
    private Integer hasQualityPeriod;
    
    private String ownerId;
    
    private Integer stockAmount;
    
    private String providerUnit;
    
    private Integer providerUnitMultiple = 1;

    public Integer getPurchaseDetailId() {
        return purchaseDetailId;
    }

    public void setPurchaseDetailId(Integer purchaseDetailId) {
        this.purchaseDetailId = purchaseDetailId;
    }

    public String getStockOrderId() {
		return stockOrderId;
	}

	public void setStockOrderId(String stockOrderId) {
		this.stockOrderId = stockOrderId;
	}

	public String getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(String goodsTypeId) {
        this.goodsTypeId = goodsTypeId == null ? null : goodsTypeId.trim();
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId == null ? null : purchaseOrderId.trim();
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    public Integer getGoodsCost() {
        return goodsCost;
    }

    public void setGoodsCost(Integer goodsCost) {
        this.goodsCost = goodsCost;
    }

    public Integer getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Integer goodsAmount) {
        this.goodsAmount = goodsAmount;
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

	public Integer getGoodsAckAmount() {
		return goodsAckAmount;
	}

	public void setGoodsAckAmount(Integer goodsAckAmount) {
		this.goodsAckAmount = goodsAckAmount;
	}

	public Date getProductDate() {
		
		if (!StringUtils.isEmpty(this.productDateText))
		{
			try {
				productDate = DateUtils.parseDate(productDateText, new String[]{"yyyy-MM-dd"});
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return productDate;
	}

	public void setProductDate(Date productDate) {
		this.productDate = productDate;
	}

	public Date getExpiredDate() {
		if (!StringUtils.isEmpty(this.expiredDateText))
		{
			try {
				expiredDate = DateUtils.parseDate(expiredDateText, new String[]{"yyyy-MM-dd"});
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public Integer getAckStatus() {
		return ackStatus;
	}

	public void setAckStatus(Integer ackStatus) {
		this.ackStatus = ackStatus;
	}
    
    public void setHasQualityPeriod(Integer hasQualityPeriod) {
		this.hasQualityPeriod = hasQualityPeriod;
	}
    
    public Integer getHasQualityPeriod() {
		return hasQualityPeriod;
	}

	public String getProductDateText() {
		return productDateText;
	}

	public void setProductDateText(String productDateText) {
		this.productDateText = productDateText == null ? null : productDateText.trim();
	}

	public String getExpiredDateText() {
		return expiredDateText;
	}

	public void setExpiredDateText(String expiredDateText) {
		this.expiredDateText = expiredDateText == null ? null : expiredDateText.trim();
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public Integer getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(Integer stockAmount) {
		this.stockAmount = stockAmount;
	}

	public String getProviderUnit() {
		return providerUnit;
	}

	public void setProviderUnit(String providerUnit) {
		this.providerUnit = providerUnit;
	}
    
	public void setProviderUnitMultiple(Integer providerUnitMultiple) {
		this.providerUnitMultiple = providerUnitMultiple;
	}
	
	public Integer getProviderUnitMultiple() {
		return providerUnitMultiple;
	}
	
	public String getGoodsCostText() {
		if (goodsCost != null)
		{
			goodsCostText = MoneyUtils.getMoneyText(goodsCost);
		}
		
		return goodsCostText;
	}

	public Integer getStockOrderDetailId() {
		return stockOrderDetailId;
	}

	public void setStockOrderDetailId(Integer stockOrderDetailId) {
		this.stockOrderDetailId = stockOrderDetailId;
	}

	public Integer getProviderAckAmount() {
		return providerAckAmount;
	}

	public void setProviderAckAmount(Integer providerAckAmount) {
		this.providerAckAmount = providerAckAmount;
	}
	
	public void setProviderAckStatus(Integer providerAckStatus) {
		this.providerAckStatus = providerAckStatus;
	}
	
	public Integer getProviderAckStatus() {
		return providerAckStatus;
	}
}