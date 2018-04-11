package com.fuhuitong.applychain.model;

public class MerchantClients
{
    private Long merClientId;

    private String merId;

    private String merUserId;

    private String clientLevelId;

    private Integer relationship;

    public Long getMerClientId() {
        return merClientId;
    }

    public void setMerClientId(Long merClientId) {
        this.merClientId = merClientId;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public String getMerUserId() {
        return merUserId;
    }

    public void setMerUserId(String merUserId) {
        this.merUserId = merUserId == null ? null : merUserId.trim();
    }

    public String getClientLevelId() {
        return clientLevelId;
    }

    public void setClientLevelId(String clientLevelId) {
        this.clientLevelId = clientLevelId == null ? null : clientLevelId.trim();
    }

    public Integer getRelationship() {
        return relationship;
    }

    public void setRelationship(Integer relationship) {
        this.relationship = relationship;
    }
    
}