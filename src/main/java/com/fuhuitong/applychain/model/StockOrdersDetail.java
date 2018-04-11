package com.fuhuitong.applychain.model;

import com.fuhuitong.applychain.utils.MoneyUtils;

public class StockOrdersDetail {
    private Integer stockOrderDetailId;

    private String goodsTypeId;

    private String goodsId;
    
    private String goodsTypeName;
    
    private String goodsName;
    
    private String goodsCode;
    
    private Integer goodsPrice;
    
    private String measurUnit;

    private String stockOrderId;

    private Integer goodsCount;

    private Integer dealType;

    private String fromStoreId;

    private String fromStoreName;

    private String toProviderId;

    private String toProviderName;

    private Integer providerCost;
    
    private String providerCostText;
    
    private Integer providerCount;
    
    private String providerUnit;
    
    private Integer providerUnitMultiple;

    private String merId;
    
    private String applyId;
    
    private Integer storeGoodsCost;
    
    private String storeGoodsCostText;
    
    private Integer goodsTotalPrice;
    private String goodsTotalPriceText;
    private Integer ackGoodsTotalPrice;
    private String ackGoodsTotalPriceText;
    
    private Integer ackGoodsCount;
    
    public Integer getStockOrderDetailId() {
        return stockOrderDetailId;
    }

    public void setStockOrderDetailId(Integer stockOrderDetailId) {
        this.stockOrderDetailId = stockOrderDetailId;
    }

    public String getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(String goodsTypeId) {
        this.goodsTypeId = goodsTypeId == null ? null : goodsTypeId.trim();
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    public String getStockOrderId() {
        return stockOrderId;
    }

    public void setStockOrderId(String stockOrderId) {
        this.stockOrderId = stockOrderId == null ? null : stockOrderId.trim();
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

	public Integer getDealType() {
		return dealType;
	}

	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}

	public String getFromStoreId() {
		return fromStoreId;
	}

	public void setFromStoreId(String fromStoreId) {
		this.fromStoreId = fromStoreId;
	}

	public String getFromStoreName() {
		return fromStoreName;
	}

	public void setFromStoreName(String fromStoreName) {
		this.fromStoreName = fromStoreName;
	}

	public String getToProviderId() {
		return toProviderId;
	}

	public void setToProviderId(String toProviderId) {
		this.toProviderId = toProviderId;
	}

	public String getToProviderName() {
		return toProviderName;
	}

	public void setToProviderName(String toProviderName) {
		this.toProviderName = toProviderName;
	}

	public Integer getProviderCost() {
		return providerCost;
	}

	public void setProviderCost(Integer providerCost) {
		this.providerCost = providerCost;
	}
    
    public void setMerId(String merId) {
		this.merId = merId;
	}
    
    public String getMerId() {
		return merId;
	}
    
    public void setProviderCount(Integer providerCount) {
		this.providerCount = providerCount;
	}
    
    public Integer getProviderCount() {
		return providerCount;
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

	public String getMeasurUnit() {
		return measurUnit;
	}

	public void setMeasurUnit(String measurUnit) {
		this.measurUnit = measurUnit;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public Integer getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Integer goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getProviderUnit() {
		return providerUnit;
	}

	public void setProviderUnit(String providerUnit) {
		this.providerUnit = providerUnit;
	}

	public Integer getStoreGoodsCost() {
		return storeGoodsCost;
	}

	public void setStoreGoodsCost(Integer storeGoodsCost) {
		this.storeGoodsCost = storeGoodsCost;
	}
    
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	
	public String getApplyId() {
		return applyId;
	}
	
	public void setStoreGoodsCostText(String storeGoodsCostText) {
		this.storeGoodsCostText = storeGoodsCostText;
	}
	
	public void setProviderUnitMultiple(Integer providerUnitMultiple) {
		this.providerUnitMultiple = providerUnitMultiple;
	}
	
	public Integer getProviderUnitMultiple() {
		return providerUnitMultiple;
	}
	
	public void setAckGoodsCount(Integer ackGoodsCount) {
		this.ackGoodsCount = ackGoodsCount;
	}
	
	public void setAckGoodsTotalPrice(Integer ackGoodsTotalPrice) {
		this.ackGoodsTotalPrice = ackGoodsTotalPrice;
	}
	
	public void setGoodsTotalPrice(Integer goodsTotalPrice) {
		this.goodsTotalPrice = goodsTotalPrice;
	}
	
	public Integer getAckGoodsCount() {
		return ackGoodsCount;
	}
	
	public Integer getAckGoodsTotalPrice() {
		return ackGoodsTotalPrice;
	}
	
	public Integer getGoodsTotalPrice() {
		return goodsTotalPrice;
	}
	
	public String getGoodsTotalPriceText() {
		if (goodsTotalPrice != null)
		{
			goodsTotalPriceText = MoneyUtils.getMoneyText(goodsTotalPrice);
		}
		
		return goodsTotalPriceText;
	}

	public void setGoodsTotalPriceText(String goodsTotalPriceText) {
		this.goodsTotalPriceText = goodsTotalPriceText;
	}
	
	public String getProviderCostText() {
		if (providerCost != null)
		{
			providerCostText = MoneyUtils.getMoneyText(providerCost);
		}
		
		return providerCostText;
	}

	public String getAckGoodsTotalPriceText() {
		
		if (ackGoodsTotalPrice != null)
		{
			ackGoodsTotalPriceText = MoneyUtils.getMoneyText(ackGoodsTotalPrice);
		}
		
		return ackGoodsTotalPriceText;
	}

	public void setAckGoodsTotalPriceText(String ackGoodsTotalPriceText) {
		this.ackGoodsTotalPriceText = ackGoodsTotalPriceText;
	}

	public String getStoreGoodsCostText() {
		
		if (storeGoodsCost != null)
		{
			storeGoodsCostText = MoneyUtils.getMoneyText(storeGoodsCost);
		}
		
		return storeGoodsCostText;
	}
}