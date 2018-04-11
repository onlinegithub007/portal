package com.fuhuitong.applychain.model;

public class StockOrdersSN {
    private Integer stockOrderSn;

    private String stockOrderId;

    public Integer getStockOrderSn() {
        return stockOrderSn;
    }

    public void setStockOrderSn(Integer stockOrderSn) {
        this.stockOrderSn = stockOrderSn;
    }

    public String getStockOrderId() {
        return stockOrderId;
    }

    public void setStockOrderId(String stockOrderId) {
        this.stockOrderId = stockOrderId == null ? null : stockOrderId.trim();
    }
}