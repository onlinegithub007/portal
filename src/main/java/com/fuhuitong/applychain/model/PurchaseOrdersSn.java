package com.fuhuitong.applychain.model;

public class PurchaseOrdersSn {
    private Integer purchaseOrderSn;

    private String purchaseOrderId;

    public Integer getPurchaseOrderSn() {
        return purchaseOrderSn;
    }

    public void setPurchaseOrderSn(Integer purchaseOrderSn) {
        this.purchaseOrderSn = purchaseOrderSn;
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId == null ? null : purchaseOrderId.trim();
    }
}