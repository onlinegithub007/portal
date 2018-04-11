package com.fuhuitong.applychain.model;

import java.util.Date;

public class TempPosOrders {
    private String tmpOrderId;

    private String merGroupId;

    private String merId;

    private String merUserId;
    
    private String operatorId;
    
    private String operatorName;

    private Date createDate;

    private Integer totalPrice;

    private String memberCode;

    private String memberName;

    private Integer memberLevel;

    private Integer memberScore;

    private String discountCoupon;

    private String cardCoupon;

    private Integer balancePay;

    private Integer balance;

    private String vipGoods;

    private int finalPrice;
    
    private int discountPrice;
    
    private Integer cardPayPrice;
    
    private int discountType;
    
    private boolean orderDiscount;
    private int orderDiscountType;
    private int orderDiscountValue;
    private int orderDiscountPercent;

    private int groupDiscountType;
    private int groupOrderDiscountPrice;
    private int groupOrderDiscountValue;
    private int groupOrderDiscountPercent;
    
    private int orderType;
    
    public String getTmpOrderId() {
        return tmpOrderId;
    }

    public void setTmpOrderId(String tmpOrderId) {
        this.tmpOrderId = tmpOrderId == null ? null : tmpOrderId.trim();
    }

    public String getMerGroupId() {
        return merGroupId;
    }

    public void setMerGroupId(String merGroupId) {
        this.merGroupId = merGroupId == null ? null : merGroupId.trim();
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public String getMerUserId() {
        return merUserId;
    }

    public void setMerUserId(String merUserId) {
        this.merUserId = merUserId == null ? null : merUserId.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode == null ? null : memberCode.trim();
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public Integer getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(Integer memberLevel) {
        this.memberLevel = memberLevel;
    }

    public Integer getMemberScore() {
        return memberScore;
    }

    public void setMemberScore(Integer memberScore) {
        this.memberScore = memberScore;
    }

    public String getDiscountCoupon() {
        return discountCoupon;
    }

    public void setDiscountCoupon(String discountCoupon) {
        this.discountCoupon = discountCoupon == null ? null : discountCoupon.trim();
    }

    public String getCardCoupon() {
        return cardCoupon;
    }

    public void setCardCoupon(String cardCoupon) {
        this.cardCoupon = cardCoupon == null ? null : cardCoupon.trim();
    }

    public Integer getBalancePay() {
        return balancePay;
    }

    public void setBalancePay(Integer balancePay) {
        this.balancePay = balancePay;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getVipGoods() {
        return vipGoods;
    }

    public void setVipGoods(String vipGoods) {
        this.vipGoods = vipGoods == null ? null : vipGoods.trim();
    }

    public Integer getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

	public Integer getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(int discountPrice) {
		this.discountPrice = discountPrice;
	}
    
    public void setCardPayPrice(Integer cardPayPrice) {
		this.cardPayPrice = cardPayPrice;
	}
    
    public Integer getCardPayPrice() {
		return cardPayPrice;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public boolean isOrderDiscount() {
		return orderDiscount;
	}

	public void setOrderDiscount(boolean orderDiscount) {
		this.orderDiscount = orderDiscount;
	}

	public int getOrderDiscountType() {
		return orderDiscountType;
	}

	public void setOrderDiscountType(int orderDiscountType) {
		this.orderDiscountType = orderDiscountType;
	}

	public int getOrderDiscountValue() {
		return orderDiscountValue;
	}

	public void setOrderDiscountValue(int orderDiscountValue) {
		this.orderDiscountValue = orderDiscountValue;
	}

	public int getOrderDiscountPercent() {
		return orderDiscountPercent;
	}

	public void setOrderDiscountPercent(int orderDiscountPercent) {
		this.orderDiscountPercent = orderDiscountPercent;
	}

	public int getDiscountType() {
		return discountType;
	}

	public void setDiscountType(int discountType) {
		this.discountType = discountType;
	}

	public int getGroupDiscountType() {
		return groupDiscountType;
	}

	public void setGroupDiscountType(int groupDiscountType) {
		this.groupDiscountType = groupDiscountType;
	}

	public int getGroupOrderDiscountPrice() {
		return groupOrderDiscountPrice;
	}

	public void setGroupOrderDiscountPrice(int groupOrderDiscountPrice) {
		this.groupOrderDiscountPrice = groupOrderDiscountPrice;
	}

	public int getGroupOrderDiscountValue() {
		return groupOrderDiscountValue;
	}

	public void setGroupOrderDiscountValue(int groupOrderDiscountValue) {
		this.groupOrderDiscountValue = groupOrderDiscountValue;
	}

	public int getGroupOrderDiscountPercent() {
		return groupOrderDiscountPercent;
	}

	public void setGroupOrderDiscountPercent(int groupOrderDiscountPercent) {
		this.groupOrderDiscountPercent = groupOrderDiscountPercent;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	
    
}