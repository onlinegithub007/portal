package com.fuhuitong.applychain.model;

import java.util.Date;

public class MerchantRole {
    private Integer merRoleId;

    private String merId;

    private Integer productRoleId;

    private Integer merRoleStatus;

    private Date createDate;

    public Integer getMerRoleId() {
        return merRoleId;
    }

    public void setMerRoleId(Integer merRoleId) {
        this.merRoleId = merRoleId;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public Integer getProductRoleId() {
        return productRoleId;
    }

    public void setProductRoleId(Integer productRoleId) {
        this.productRoleId = productRoleId;
    }

    public Integer getMerRoleStatus() {
        return merRoleStatus;
    }

    public void setMerRoleStatus(Integer merRoleStatus) {
        this.merRoleStatus = merRoleStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}