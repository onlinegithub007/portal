package com.fuhuitong.applychain.model;

import java.util.Date;

public class GoodsStockCheck extends Pagable
{
    private Integer stockCheckId;

    private String goodsId;

    private Long goodsStockId;

    private String stockCheckorId;

    private String stockCheckorName;

    private Date createDate;
    
    private String createDateText;

    private Integer checkResult;

    private Integer checkDiffValue;
    
    private String ownerId;

    public Integer getStockCheckId() {
        return stockCheckId;
    }

    public void setStockCheckId(Integer stockCheckId) {
        this.stockCheckId = stockCheckId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    public Long getGoodsStockId() {
        return goodsStockId;
    }

    public void setGoodsStockId(Long goodsStockId) {
        this.goodsStockId = goodsStockId;
    }

    public String getStockCheckorId() {
        return stockCheckorId;
    }

    public void setStockCheckorId(String stockCheckorId) {
        this.stockCheckorId = stockCheckorId == null ? null : stockCheckorId.trim();
    }

    public String getStockCheckorName() {
        return stockCheckorName;
    }

    public void setStockCheckorName(String stockCheckorName) {
        this.stockCheckorName = stockCheckorName == null ? null : stockCheckorName.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }

    public Integer getCheckDiffValue() {
        return checkDiffValue;
    }

    public void setCheckDiffValue(Integer checkDiffValue) {
        this.checkDiffValue = checkDiffValue;
    }
    
    public void setCreateDateText(String createDateText) {
		this.createDateText = createDateText;
	}
    
    public String getCreateDateText() {
		return createDateText;
	}
    
    public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
    
    public String getOwnerId() {
		return ownerId;
	}
}