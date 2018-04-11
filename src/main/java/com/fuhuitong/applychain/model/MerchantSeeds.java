package com.fuhuitong.applychain.model;

public class MerchantSeeds {
    private Integer merSeedId;

    private String merId;

    public Integer getMerSeedId() {
        return merSeedId;
    }

    public void setMerSeedId(Integer merSeedId) {
        this.merSeedId = merSeedId;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }
}