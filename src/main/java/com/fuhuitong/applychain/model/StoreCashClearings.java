package com.fuhuitong.applychain.model;

import java.util.Date;

public class StoreCashClearings {
    private Integer cashClearingId;

    private Integer dailyClearId;

    private String merGroupId;

    private Integer cashAmonut;

    private Date clearingDate;

    private Integer clearingMethod;

    private String payNo;

    public Integer getCashClearingId() {
        return cashClearingId;
    }

    public void setCashClearingId(Integer cashClearingId) {
        this.cashClearingId = cashClearingId;
    }

    public Integer getDailyClearId() {
        return dailyClearId;
    }

    public void setDailyClearId(Integer dailyClearId) {
        this.dailyClearId = dailyClearId;
    }

    public String getMerGroupId() {
        return merGroupId;
    }

    public void setMerGroupId(String merGroupId) {
        this.merGroupId = merGroupId == null ? null : merGroupId.trim();
    }

    public Integer getCashAmonut() {
        return cashAmonut;
    }

    public void setCashAmonut(Integer cashAmonut) {
        this.cashAmonut = cashAmonut;
    }

    public Date getClearingDate() {
        return clearingDate;
    }

    public void setClearingDate(Date clearingDate) {
        this.clearingDate = clearingDate;
    }

    public Integer getClearingMethod() {
        return clearingMethod;
    }

    public void setClearingMethod(Integer clearingMethod) {
        this.clearingMethod = clearingMethod;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo == null ? null : payNo.trim();
    }
}