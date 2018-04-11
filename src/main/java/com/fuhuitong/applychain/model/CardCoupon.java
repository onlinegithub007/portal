package com.fuhuitong.applychain.model;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class CardCoupon extends Pagable{
    private String cardCouponId;

    private String merId;

    private String couponTitle;

    private Integer totalCount;

    private Integer couponAmount;

    private Date createDate;

    private Date expiredDate;
    
    private String createDateText;

    private String expiredDateText;

    private Integer validStatus;

    private Integer enabledCount;

    private Integer unenabledCount;
    
    private String memberLevelId;
    
    private String levelName;

    public String getCardCouponId() {
        return cardCouponId;
    }

    public void setCardCouponId(String cardCouponId) {
        this.cardCouponId = cardCouponId == null ? null : cardCouponId.trim();
    }


    public String getMemberLevelId() {
		return memberLevelId;
	}

	public void setMemberLevelId(String memberLevelId) {
		this.memberLevelId = memberLevelId;
	}

	public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public String getCouponTitle() {
        return couponTitle;
    }

    public void setCouponTitle(String couponTitle) {
        this.couponTitle = couponTitle == null ? null : couponTitle.trim();
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Integer couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    public Integer getEnabledCount() {
        return enabledCount;
    }

    public void setEnabledCount(Integer enabledCount) {
        this.enabledCount = enabledCount;
    }

    public Integer getUnenabledCount() {
        return unenabledCount;
    }

    public void setUnenabledCount(Integer unenabledCount) {
        this.unenabledCount = unenabledCount;
    }
    
    public String getExpiredDateText() {
		
		if (this.expiredDate != null)
		{
			expiredDateText = DateFormatUtils.format(this.expiredDate, "yyyy-MM-dd");
		}
		
		return expiredDateText;
	}
		
	public String getCreateDateText() {
	
		if (this.createDate != null)
		{
			createDateText = DateFormatUtils.format(this.createDate, "yyyy-MM-dd");
		}
		
		return createDateText;
	}

	public void setCreateDateText(String createDateText) {
		this.createDateText = createDateText;
	}

	public void setExpiredDateText(String expiredDateText) {
		this.expiredDateText = expiredDateText;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
	
		
}