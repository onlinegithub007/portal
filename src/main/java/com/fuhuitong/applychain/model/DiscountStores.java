package com.fuhuitong.applychain.model;

public class DiscountStores 
{
    private Integer discountStoreId;

    private String discountId;

    private String storeId;
    
    private String groupName;
    
    private String detailAddress;
    
    private String areaFullName;

    public Integer getDiscountStoreId() {
        return discountStoreId;
    }

    public void setDiscountStoreId(Integer discountStoreId) {
        this.discountStoreId = discountStoreId;
    }

    public String getDiscountId() {
		return discountId;
	}

	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}

	public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId == null ? null : storeId.trim();
    }

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public String getAreaFullName() {
		return areaFullName;
	}

	public void setAreaFullName(String areaFullName) {
		this.areaFullName = areaFullName;
	}
    
    
}