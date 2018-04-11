package com.fuhuitong.applychain.model;

import com.fuhuitong.applychain.utils.MoneyUtils;

public class OrderDetails {
    private Long orderDetailId;

    private String orderId;
    
    private String goodsId;
    
    private String providerId;
    
    private String merGroupId;
    private String merId;
    private Integer orderStockDetailId;

    private String goodsCode;

    private String goodsName;
    
    private String goodsTypeName;
    
    private String createDateText;
    
    private String endDateText;

    private Integer goodsPrice;
    
    private Integer goodsSalePrice;
    
    private String goodsSalePriceText;

    private Integer goodsAmount;

    private Integer goodsTotalPrice;
    
    private String goodsTotalPriceText;
    
    private Float goodsCost;
    
    private String payDateText;
    
    private Integer goodsBinding;
    
    private String bindingId;

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode == null ? null : goodsCode.trim();
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public Integer getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Integer goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Integer goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public Integer getGoodsTotalPrice() {
        return goodsTotalPrice;
    }

    public void setGoodsTotalPrice(Integer goodsTotalPrice) {
        this.goodsTotalPrice = goodsTotalPrice;
    }
    
    public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
    
    public String getGoodsId() {
		return goodsId;
	}
    
    public void setGoodsSalePrice(Integer goodsSalePrice) {
		this.goodsSalePrice = goodsSalePrice;
	}
    
    public Integer getGoodsSalePrice() {
		return goodsSalePrice;
	}

	public String getMerGroupId() {
		return merGroupId;
	}

	public void setMerGroupId(String merGroupId) {
		this.merGroupId = merGroupId;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public Integer getOrderStockDetailId() {
		return orderStockDetailId;
	}

	public void setOrderStockDetailId(Integer orderStockDetailId) {
		this.orderStockDetailId = orderStockDetailId;
	}

	public String getGoodsSalePriceText() {
		if (goodsSalePrice != null)
		{
			goodsSalePriceText = MoneyUtils.getMoneyText(goodsSalePrice);
		}
		
		return goodsSalePriceText;
	}
	
	public String getGoodsTotalPriceText() {
		if (goodsTotalPrice != null)
		{
			goodsTotalPriceText = MoneyUtils.getMoneyText(goodsTotalPrice);
		}
		
		return goodsTotalPriceText;
	}

	public Float getGoodsCost() {
		return goodsCost;
	}

	public void setGoodsCost(Float goodsCost) {
		this.goodsCost = goodsCost;
	}

	public String getCreateDateText() {
		return createDateText;
	}

	public void setCreateDateText(String createDateText) {
		this.createDateText = createDateText;
	}

	public String getEndDateText() {
		return endDateText;
	}

	public void setEndDateText(String endDateText) {
		this.endDateText = endDateText;
	}
	
	public void setGoodsTypeName(String goodsTypeName) {
		this.goodsTypeName = goodsTypeName;
	}
	
	public String getGoodsTypeName() {
		return goodsTypeName;
	}
	
	public String getProviderId() {
		return providerId;
	}
	
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	
	public void setPayDateText(String payDateText) {
		this.payDateText = payDateText;
	}
	
	public String getPayDateText() {
		return payDateText;
	}
	
	public void setGoodsBinding(Integer goodsBinding) {
		this.goodsBinding = goodsBinding;
	}
	
	public Integer getGoodsBinding() {
		return goodsBinding;
	}
	
	public void setBindingId(String bindingId) {
		this.bindingId = bindingId;
	}
	
	public String getBindingId() {
		return bindingId;
	}
}