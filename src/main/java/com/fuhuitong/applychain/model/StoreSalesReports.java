package com.fuhuitong.applychain.model;

import com.fuhuitong.applychain.utils.MoneyUtils;

import java.util.Date;

public class StoreSalesReports {
    private Integer saleReportId;

    private String goodsId;

    private String merId;

    private String merGroupId;

    private String goodsCode;

    private String goodsName;

    private Float goodsCost;
    
    private String goodsCostText;

    private Integer goodsSalePrice;
    
    private String goodsSalePriceText;

    private Integer stockBuyCount;

    private Integer stockBuyAmount;
    
    private String stockBuyAmountText;

    private Integer goodsSaleCount;

    private Integer goodsSaleAmount;
    
    private String goodsSaleAmountText;

    private Integer goodsProfit;
    
    private String goodsProfitText;

    private Integer dispatchOutCount;

    private Integer dispatchOutAmount;
    
    private String dispatchOutAmountText;

    private Integer dispatchInCount;

    private Integer dispatchInAmount;
    
    private String dispatchInAmountText;

    private Integer returnCount;

    private Integer returnAmount;
    
    private String returnAmountText;

    private Integer goodsStockCount;

    private Date createDate;

    private String createDateText;

    public Integer getSaleReportId() {
        return saleReportId;
    }

    public void setSaleReportId(Integer saleReportId) {
        this.saleReportId = saleReportId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public String getMerGroupId() {
        return merGroupId;
    }

    public void setMerGroupId(String merGroupId) {
        this.merGroupId = merGroupId == null ? null : merGroupId.trim();
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

    public Float getGoodsCost() {
        return goodsCost;
    }

    public void setGoodsCost(Float goodsCost) {
        this.goodsCost = goodsCost;
    }

    public Integer getGoodsSalePrice() {
        return goodsSalePrice;
    }

    public void setGoodsSalePrice(Integer goodsSalePrice) {
        this.goodsSalePrice = goodsSalePrice;
    }

    public Integer getStockBuyCount() {
        return stockBuyCount;
    }

    public void setStockBuyCount(Integer stockBuyCount) {
        this.stockBuyCount = stockBuyCount;
    }

    public Integer getStockBuyAmount() {
        return stockBuyAmount;
    }

    public void setStockBuyAmount(Integer stockBuyAmount) {
        this.stockBuyAmount = stockBuyAmount;
    }

    public Integer getGoodsSaleCount() {
        return goodsSaleCount;
    }

    public void setGoodsSaleCount(Integer goodsSaleCount) {
        this.goodsSaleCount = goodsSaleCount;
    }

    public Integer getGoodsSaleAmount() {
        return goodsSaleAmount;
    }

    public void setGoodsSaleAmount(Integer goodsSaleAmount) {
        this.goodsSaleAmount = goodsSaleAmount;
    }

    public Integer getGoodsProfit() {
        return goodsProfit;
    }

    public void setGoodsProfit(Integer goodsProfit) {
        this.goodsProfit = goodsProfit;
    }

    public Integer getDispatchOutCount() {
        return dispatchOutCount;
    }

    public void setDispatchOutCount(Integer dispatchOutCount) {
        this.dispatchOutCount = dispatchOutCount;
    }

    public Integer getDispatchOutAmount() {
        return dispatchOutAmount;
    }

    public void setDispatchOutAmount(Integer dispatchOutAmount) {
        this.dispatchOutAmount = dispatchOutAmount;
    }

    public Integer getDispatchInCount() {
        return dispatchInCount;
    }

    public void setDispatchInCount(Integer dispatchInCount) {
        this.dispatchInCount = dispatchInCount;
    }

    public Integer getDispatchInAmount() {
        return dispatchInAmount;
    }

    public void setDispatchInAmount(Integer dispatchInAmount) {
        this.dispatchInAmount = dispatchInAmount;
    }

    public Integer getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Integer returnCount) {
        this.returnCount = returnCount;
    }

    public Integer getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(Integer returnAmount) {
        this.returnAmount = returnAmount;
    }

    public Integer getGoodsStockCount() {
        return goodsStockCount;
    }

    public void setGoodsStockCount(Integer goodsStockCount) {
        this.goodsStockCount = goodsStockCount;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateDateText() {
        return createDateText;
    }

    public void setCreateDateText(String createDateText) {
        this.createDateText = createDateText == null ? null : createDateText.trim();
    }

	public String getGoodsCostText() {
		
		if (goodsCost != null)
		{
			goodsCostText = MoneyUtils.getMoneyText(goodsCost.intValue());
		}
		
		return goodsCostText;
	}

	public String getGoodsSalePriceText() {
		
		if (goodsSalePrice != null)
		{
			goodsSalePriceText = MoneyUtils.getMoneyText(goodsSalePrice);
		}
		
		return goodsSalePriceText;
	}

	public String getStockBuyAmountText() {
		if (stockBuyAmount != null)
		{
			stockBuyAmountText = MoneyUtils.getMoneyText(stockBuyAmount);
		}
		
		return stockBuyAmountText;
	}

	public String getGoodsSaleAmountText() {
		if (goodsSaleAmount != null)
		{
			goodsSaleAmountText = MoneyUtils.getMoneyText(goodsSaleAmount);
		}
		
		return goodsSaleAmountText;
	}

	public String getGoodsProfitText() {
		if (goodsProfit != null)
		{
			goodsProfitText = MoneyUtils.getMoneyText(goodsProfit);
		}
		
		return goodsProfitText;
	}

	public String getDispatchOutAmountText() {
		if (dispatchOutAmount != null)
		{
			dispatchOutAmountText = MoneyUtils.getMoneyText(dispatchOutAmount);
		}
		
		return dispatchOutAmountText;
	}

	public String getDispatchInAmountText() {
		if (dispatchInAmount != null)
		{
			dispatchInAmountText = MoneyUtils.getMoneyText(dispatchInAmount);
		}
		
		return dispatchInAmountText;
	}

	public String getReturnAmountText() {
		if (returnAmount != null)
		{
			returnAmountText = MoneyUtils.getMoneyText(returnAmount);
		}
		
		return returnAmountText;
	}
    
}