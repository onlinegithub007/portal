package com.fuhuitong.applychain.model;

public class SysUsersMenus {
    private Long sysUserMenuId;

    private String menuId;

    private String account;

    public Long getSysUserMenuId() {
        return sysUserMenuId;
    }

    public void setSysUserMenuId(Long sysUserMenuId) {
        this.sysUserMenuId = sysUserMenuId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }
}