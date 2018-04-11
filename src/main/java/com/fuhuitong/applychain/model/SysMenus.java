package com.fuhuitong.applychain.model;

public class SysMenus {
    private String menuId;

    private String parentMenuId;

    private String menuText;

    private String menuUrl;

    private Boolean menuEnabled;

    private Boolean menuVisible;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }

    public String getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(String parentMenuId) {
        this.parentMenuId = parentMenuId == null ? null : parentMenuId.trim();
    }

    public String getMenuText() {
        return menuText;
    }

    public void setMenuText(String menuText) {
        this.menuText = menuText == null ? null : menuText.trim();
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public Boolean getMenuEnabled() {
        return menuEnabled;
    }

    public void setMenuEnabled(Boolean menuEnabled) {
        this.menuEnabled = menuEnabled;
    }

    public Boolean getMenuVisible() {
        return menuVisible;
    }

    public void setMenuVisible(Boolean menuVisible) {
        this.menuVisible = menuVisible;
    }
}