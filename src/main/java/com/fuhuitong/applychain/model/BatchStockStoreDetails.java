package com.fuhuitong.applychain.model;

public class BatchStockStoreDetails {
    private Integer batchStockStoreId;

    private String merGroupId;

    private String batchStockOrderId;

    private String groupName;
    
    private String areaFullName;
    private String groupDesc;
    private String detailAddress;

    public Integer getBatchStockStoreId() {
        return batchStockStoreId;
    }

    public void setBatchStockStoreId(Integer batchStockStoreId) {
        this.batchStockStoreId = batchStockStoreId;
    }

    public String getMerGroupId() {
        return merGroupId;
    }

    public void setMerGroupId(String merGroupId) {
        this.merGroupId = merGroupId == null ? null : merGroupId.trim();
    }

    public String getBatchStockOrderId() {
        return batchStockOrderId;
    }

    public void setBatchStockOrderId(String batchStockOrderId) {
        this.batchStockOrderId = batchStockOrderId == null ? null : batchStockOrderId.trim();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

	public String getAreaFullName() {
		return areaFullName;
	}

	public void setAreaFullName(String areaFullName) {
		this.areaFullName = areaFullName;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
    
    
}