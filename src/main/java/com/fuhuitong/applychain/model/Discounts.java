package com.fuhuitong.applychain.model;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class Discounts extends Pagable{
    private String discountId;

    private String merId;

    private Boolean allStore;

    private Integer discountItemStatus;

    private Integer discountUserType;

    private String memberLevelId;
    
    private String levelName;

    private Integer discountType;

    private String discountTypeName;

    private Date createDate;

    private Date endDate;
    
    private String createDateText;

    private String endDateText;
    
    private String storeIds;

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId == null ? null : discountId.trim();
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public Boolean getAllStore() {
        return allStore;
    }

    public void setAllStore(Boolean allStore) {
        this.allStore = allStore;
    }

    public Integer getDiscountItemStatus() {
        return discountItemStatus;
    }

    public void setDiscountItemStatus(Integer discountItemStatus) {
        this.discountItemStatus = discountItemStatus;
    }

    public Integer getDiscountUserType() {
        return discountUserType;
    }

    public void setDiscountUserType(Integer discountUserType) {
        this.discountUserType = discountUserType;
    }

    public String getMemberLevelId() {
        return memberLevelId;
    }

    public void setMemberLevelId(String memberLevelId) {
        this.memberLevelId = memberLevelId == null ? null : memberLevelId.trim();
    }

    public Integer getDiscountType() {
        return discountType;
    }

    public void setDiscountType(Integer discountType) {
        this.discountType = discountType;
    }

    public String getDiscountTypeName() {
        return discountTypeName;
    }

    public void setDiscountTypeName(String discountTypeName) {
        this.discountTypeName = discountTypeName == null ? null : discountTypeName.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public String getCreateDateText() {
		if (this.createDate != null)
		{
			createDateText = DateFormatUtils.format(this.createDate, "yyyy-MM-dd HH:mm:ss");
		}
		
		return createDateText;
	}

	public void setCreateDateText(String createDateText) {
		this.createDateText = createDateText;
	}

	public String getEndDateText() {
		if (this.endDate != null)
		{
			endDateText = DateFormatUtils.format(this.endDate, "yyyy-MM-dd HH:mm:ss");
		}
		
		return endDateText;
	}

	public void setEndDateText(String endDateText) {
		this.endDateText = endDateText;
	}
	
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
	public String getLevelName() {
		return levelName;
	}
	
	public String getStoreIds() {
		return storeIds;
	}
	
	public void setStoreIds(String storeIds) {
		this.storeIds = storeIds;
	}
}