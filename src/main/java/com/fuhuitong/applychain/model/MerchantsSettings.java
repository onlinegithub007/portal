package com.fuhuitong.applychain.model;

public class MerchantsSettings {
    private Integer merSettingId;

    private String merId;

    private Boolean stockLessZero;

    public Integer getMerSettingId() {
        return merSettingId;
    }

    public void setMerSettingId(Integer merSettingId) {
        this.merSettingId = merSettingId;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public Boolean getStockLessZero() {
        return stockLessZero;
    }

    public void setStockLessZero(Boolean stockLessZero) {
        this.stockLessZero = stockLessZero;
    }
}