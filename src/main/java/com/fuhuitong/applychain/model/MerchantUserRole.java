package com.fuhuitong.applychain.model;

public class MerchantUserRole {
    private Integer merUserRoleId;

    private String merId;

    private String userRoleName;

    private Integer sysDef;
    
    private String selected = "";

    public Integer getMerUserRoleId() {
        return merUserRoleId;
    }

    public void setMerUserRoleId(Integer merUserRoleId) {
        this.merUserRoleId = merUserRoleId;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public String getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName == null ? null : userRoleName.trim();
    }

    public Integer getSysDef() {
        return sysDef;
    }

    public void setSysDef(Integer sysDef) {
        this.sysDef = sysDef;
    }
    
    public void setSelected(String selected) {
		this.selected = selected;
	}
    
    public String getSelected() {
		return selected;
	}
}