package com.fuhuitong.applychain.model;

public class ProductUserRole 
{
    private Integer productUserRoleId;

    private Integer productRoleId;

    private String userRoleName;
    
    private String merId;
    
    private String selected = "";

    public Integer getProductUserRoleId() {
        return productUserRoleId;
    }

    public void setProductUserRoleId(Integer productUserRoleId) {
        this.productUserRoleId = productUserRoleId;
    }

    public Integer getProductRoleId() {
        return productRoleId;
    }

    public void setProductRoleId(Integer productRoleId) {
        this.productRoleId = productRoleId;
    }

    public String getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName == null ? null : userRoleName.trim();
    }
    
    public void setMerId(String merId) {
		this.merId = merId;
	}
    
    public String getMerId() {
		return merId;
	}
    
    public void setSelected(String selected) {
		this.selected = selected;
	}
    
    public String getSelected() {
		return selected;
	}
}