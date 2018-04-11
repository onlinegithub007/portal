package com.fuhuitong.applychain.model;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class MemberCardCoupons extends Pagable{
    private Long memCardCouponId;

    private String merId;

    private String cardCouponId;

    private Long memberId;

    private Date createDate;

    private Date expiredDate;
    
    private String createDateText;

    private String expiredDateText;

    private Integer couponAmount;

    private Integer couponBalance;

    private Integer finalStatus;
    
    private Date enableDate;
    
    private String enableDateText;

    public Long getMemCardCouponId() {
        return memCardCouponId;
    }

    public void setMemCardCouponId(Long memCardCouponId) {
        this.memCardCouponId = memCardCouponId;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public String getCardCouponId() {
        return cardCouponId;
    }

    public void setCardCouponId(String cardCouponId) {
        this.cardCouponId = cardCouponId == null ? null : cardCouponId.trim();
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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

    public Integer getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Integer couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Integer getCouponBalance() {
        return couponBalance;
    }

    public void setCouponBalance(Integer couponBalance) {
        this.couponBalance = couponBalance;
    }

    public Integer getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(Integer finalStatus) {
        this.finalStatus = finalStatus;
    }

	public Date getEnableDate() {
		return enableDate;
	}

	public void setEnableDate(Date enableDate) {
		this.enableDate = enableDate;
	}

	public String getEnableDateText() {
		if (this.enableDate != null)
		{
			enableDateText = DateFormatUtils.format(enableDate, "yyyy-MM-dd HH:mm:ss");
		}
		
		return enableDateText;
	}

	public void setEnableDateText(String enableDateText) {
		this.enableDateText = enableDateText;
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
}