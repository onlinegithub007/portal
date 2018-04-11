package com.fuhuitong.applychain.model;

public class UserRoleMenus {
	
    private Integer userRoleMenusId;

    private Integer productMenuId;

    private Integer userRoleId;

    private Integer sysRoleId;
    
    public Integer getUserRoleMenusId() {
        return userRoleMenusId;
    }

    public void setUserRoleMenusId(Integer userRoleMenusId) {
        this.userRoleMenusId = userRoleMenusId;
    }

    public Integer getProductMenuId() {
        return productMenuId;
    }

    public void setProductMenuId(Integer productMenuId) {
        this.productMenuId = productMenuId;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }
    
    public void setSysRoleId(Integer sysRoleId) {
		this.sysRoleId = sysRoleId;
	}
    
    public Integer getSysRoleId() {
		return sysRoleId;
	}
}