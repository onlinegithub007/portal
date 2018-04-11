package com.fuhuitong.applychain.model;

public class ClientLevel {
    private String clientLevelId;

    private Integer levelType;

    private String levelName;

    private Integer levelValue;

    private String merId;
    
    private String selected;

    public String getClientLevelId() {
        return clientLevelId;
    }

    public void setClientLevelId(String clientLevelId) {
        this.clientLevelId = clientLevelId == null ? null : clientLevelId.trim();
    }

    public Integer getLevelType() {
        return levelType;
    }

    public void setLevelType(Integer levelType) {
        this.levelType = levelType;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName == null ? null : levelName.trim();
    }

    public Integer getLevelValue() {
        return levelValue;
    }

    public void setLevelValue(Integer levelValue) {
        this.levelValue = levelValue;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }
    
    public void setSelected(String selected) {
		this.selected = selected;
	}
    
    public String getSelected() {
		return selected;
	}
}