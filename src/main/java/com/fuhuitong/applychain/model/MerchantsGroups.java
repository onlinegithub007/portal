package com.fuhuitong.applychain.model;

import java.util.Date;

public class MerchantsGroups extends Pagable
{
    private String merGroupId;

    private String areaId;
    
    private String areaFullName;

    private String merId;
    
    private String merCode;

    private String clientLevelId;

    private String groupName;

    private String parentGroupId;

    private String groupCode;

    private Integer groupType;

    private Date createDate;

    private String groupDesc;

    private Integer groupIndex;
    
    private String selected = "";
    
    private String detailAddress;
    
    private String province;
    
    private String city;
    
    // 用户属性
    private String userAccount;
    private String userName;
    private String userPhone;

    public String getMerGroupId() {
        return merGroupId;
    }

    public void setMerGroupId(String merGroupId) {
        this.merGroupId = merGroupId == null ? null : merGroupId.trim();
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public String getClientLevelId() {
        return clientLevelId;
    }

    public void setClientLevelId(String clientLevelId) {
        this.clientLevelId = clientLevelId == null ? null : clientLevelId.trim();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(String parentGroupId) {
        this.parentGroupId = parentGroupId == null ? null : parentGroupId.trim();
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode == null ? null : groupCode.trim();
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc == null ? null : groupDesc.trim();
    }

    public Integer getGroupIndex() {
        return groupIndex;
    }

    public void setGroupIndex(Integer groupIndex) {
        this.groupIndex = groupIndex;
    }
    
    public void setMerCode(String merCode) {
		this.merCode = merCode;
	}
    
    public String getMerCode() {
		return merCode;
	}
    
    public String getSelected() {
		return selected;
	}
    
    public void setSelected(String selected) {
		this.selected = selected;
	}
    
    public void setAreaFullName(String areaFullName) {
		this.areaFullName = areaFullName;
	}
    
    public String getAreaFullName() {
		return areaFullName;
	}
    
    public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress == null ? null : detailAddress.trim();
	}
    
    public String getDetailAddress() {
		return detailAddress;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount == null ? null : userAccount.trim();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone == null ? null : userPhone.trim();
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}
    
}