package com.fuhuitong.applychain.model;

public class Areas {
    private String areaId;

    private String areaName;

    private String parentAreaId;

    private Integer level;

    private String fullName;
    
    private String selected = "";

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    public String getParentAreaId() {
        return parentAreaId;
    }

    public void setParentAreaId(String parentAreaId) {
        this.parentAreaId = parentAreaId == null ? null : parentAreaId.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }
    
    public void setSelected(String selected) {
		this.selected = selected == null ? null : selected.trim();
	}
    
    public String getSelected() {
		return selected;
	}
}