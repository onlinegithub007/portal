package com.fuhuitong.applychain.model;

import java.util.Date;

public class DiscountCoupon 
{
    private Long couponId;

    private String merId;

    private Integer memberLevelCode;

    private Integer couponType;

    private Integer couponAmount;

    private Integer topAmount;

    private String couponDesc;

    private Date createDate;

    private Date expiredDate;

    private Integer backgroudId;
    
    private boolean valid;
    
    // 会员优惠券信息，冗余部分，用于从视图中查询数据
    private Long memCouponId;

    private Long memberId;

    private Integer finalStatus;

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public Integer getMemberLevelCode() {
        return memberLevelCode;
    }

    public void setMemberLevelCode(Integer memberLevelCode) {
        this.memberLevelCode = memberLevelCode;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public Integer getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Integer couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Integer getTopAmount() {
        return topAmount;
    }

    public void setTopAmount(Integer topAmount) {
        this.topAmount = topAmount;
    }

    public String getCouponDesc() {
        return couponDesc;
    }

    public void setCouponDesc(String couponDesc) {
        this.couponDesc = couponDesc == null ? null : couponDesc.trim();
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Integer getBackgroudId() {
        return backgroudId;
    }

    public void setBackgroudId(Integer backgroudId) {
        this.backgroudId = backgroudId;
    }

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getMemCouponId() {
		return memCouponId;
	}

	public void setMemCouponId(Long memCouponId) {
		this.memCouponId = memCouponId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getFinalStatus() {
		return finalStatus;
	}

	public void setFinalStatus(Integer finalStatus) {
		this.finalStatus = finalStatus;
	}
	
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	public boolean isValid() {
		return valid;
	}
    
}