package com.fuhuitong.applychain.model;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class DiscountItems extends Pagable
{
    private Integer discountItemId;
    
    private String discountId;

    private String goodsId;

    private String merId;

    private String goodsName;

    private Date createDate;

    private Date endDate;
    
    private String createDateText;

    private String endDateText;

    private Integer goodsPrice;

    private Integer goodsDiscountType;

    private Integer goodsDiscountPrice;

    private Integer goodsDiscountValue;

    private Integer goodsDiscountPercent;

    private Integer orderDiscountType;

    private Integer orderReachValue;

    private Integer orderDiscountValue;

    private Integer orderDiscountPercent;

    private Integer amountDiscountType;

    private Integer amountReachValue;

    private Integer amountDiscountPrice;

    private Integer amountDiscountValue;

    private Integer amountDiscountPercent;

    private Integer amountTotalDiscountPrice;

    private Integer amountTotalDiscountValue;

    private Integer presentDiscountType;

    private String saleGoodsId;

    private String presentGoodsId;

    private Integer presentGoodsPrice;

    private Integer groupDiscountType;

    private String groupGoodsIds;

    private String groupPresentGoodsId;

    // 捆绑的商品个数
    private Integer groupGoodsCount;
    
    private Integer groupOrderDiscountPrice;

    private Integer groupOrderDiscountValue;

    private Integer groupOrderDiscountPercent;

    private Integer orderTopSaleType;

    private Integer orderTopValue;

    private String orderTopPresentGoodsId;

    private String orderAddtionalGoodsId;

    private Integer orderAddtionalGoodsPrice;

    private Integer orderAddtionalGoodsCount;

    private Integer orderAddtionalGoodsMinCount;

    private Integer orderAddtionalGoodsMaxCount;
    
    // 以下是关联的Discount的信息
    private Integer discountItemStatus;

    private Integer discountUserType;

    private String memberLevelId;
    
    private String levelName;

    private Integer discountType;

    private String discountTypeName;
    
    private String storeId;

    public Integer getDiscountItemId() {
        return discountItemId;
    }

    public void setDiscountItemId(Integer discountItemId) {
        this.discountItemId = discountItemId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }


    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
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

    public Integer getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Integer goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsDiscountType() {
        return goodsDiscountType;
    }

    public void setGoodsDiscountType(Integer goodsDiscountType) {
        this.goodsDiscountType = goodsDiscountType;
    }

    public Integer getGoodsDiscountPrice() {
        return goodsDiscountPrice;
    }

    public void setGoodsDiscountPrice(Integer goodsDiscountPrice) {
        this.goodsDiscountPrice = goodsDiscountPrice;
    }

    public Integer getGoodsDiscountValue() {
        return goodsDiscountValue;
    }

    public void setGoodsDiscountValue(Integer goodsDiscountValue) {
        this.goodsDiscountValue = goodsDiscountValue;
    }

    public Integer getGoodsDiscountPercent() {
        return goodsDiscountPercent;
    }

    public void setGoodsDiscountPercent(Integer goodsDiscountPercent) {
        this.goodsDiscountPercent = goodsDiscountPercent;
    }

    public Integer getOrderDiscountType() {
        return orderDiscountType;
    }

    public void setOrderDiscountType(Integer orderDiscountType) {
        this.orderDiscountType = orderDiscountType;
    }

    public Integer getOrderReachValue() {
        return orderReachValue;
    }

    public void setOrderReachValue(Integer orderReachValue) {
        this.orderReachValue = orderReachValue;
    }

    public Integer getOrderDiscountValue() {
        return orderDiscountValue;
    }

    public void setOrderDiscountValue(Integer orderDiscountValue) {
        this.orderDiscountValue = orderDiscountValue;
    }

    public Integer getOrderDiscountPercent() {
        return orderDiscountPercent;
    }

    public void setOrderDiscountPercent(Integer orderDiscountPercent) {
        this.orderDiscountPercent = orderDiscountPercent;
    }

    public Integer getAmountDiscountType() {
        return amountDiscountType;
    }

    public void setAmountDiscountType(Integer amountDiscountType) {
        this.amountDiscountType = amountDiscountType;
    }

    public Integer getAmountReachValue() {
        return amountReachValue;
    }

    public void setAmountReachValue(Integer amountReachValue) {
        this.amountReachValue = amountReachValue;
    }

    public Integer getAmountDiscountPrice() {
        return amountDiscountPrice;
    }

    public void setAmountDiscountPrice(Integer amountDiscountPrice) {
        this.amountDiscountPrice = amountDiscountPrice;
    }

    public Integer getAmountDiscountValue() {
        return amountDiscountValue;
    }

    public void setAmountDiscountValue(Integer amountDiscountValue) {
        this.amountDiscountValue = amountDiscountValue;
    }

    public Integer getAmountDiscountPercent() {
        return amountDiscountPercent;
    }

    public void setAmountDiscountPercent(Integer amountDiscountPercent) {
        this.amountDiscountPercent = amountDiscountPercent;
    }

    public Integer getAmountTotalDiscountPrice() {
        return amountTotalDiscountPrice;
    }

    public void setAmountTotalDiscountPrice(Integer amountTotalDiscountPrice) {
        this.amountTotalDiscountPrice = amountTotalDiscountPrice;
    }

    public Integer getAmountTotalDiscountValue() {
        return amountTotalDiscountValue;
    }

    public void setAmountTotalDiscountValue(Integer amountTotalDiscountValue) {
        this.amountTotalDiscountValue = amountTotalDiscountValue;
    }

    public Integer getPresentDiscountType() {
        return presentDiscountType;
    }

    public void setPresentDiscountType(Integer presentDiscountType) {
        this.presentDiscountType = presentDiscountType;
    }

    public String getSaleGoodsId() {
        return saleGoodsId;
    }

    public void setSaleGoodsId(String saleGoodsId) {
        this.saleGoodsId = saleGoodsId == null ? null : saleGoodsId.trim();
    }

    public String getPresentGoodsId() {
        return presentGoodsId;
    }

    public void setPresentGoodsId(String presentGoodsId) {
        this.presentGoodsId = presentGoodsId == null ? null : presentGoodsId.trim();
    }

    public Integer getPresentGoodsPrice() {
        return presentGoodsPrice;
    }

    public void setPresentGoodsPrice(Integer presentGoodsPrice) {
        this.presentGoodsPrice = presentGoodsPrice;
    }

    public Integer getGroupDiscountType() {
        return groupDiscountType;
    }

    public void setGroupDiscountType(Integer groupDiscountType) {
        this.groupDiscountType = groupDiscountType;
    }

    public String getGroupGoodsIds() {
        return groupGoodsIds;
    }

    public void setGroupGoodsIds(String groupGoodsIds) {
        this.groupGoodsIds = groupGoodsIds == null ? null : groupGoodsIds.trim();
    }

    public String getGroupPresentGoodsId() {
        return groupPresentGoodsId;
    }

    public void setGroupPresentGoodsId(String groupPresentGoodsId) {
        this.groupPresentGoodsId = groupPresentGoodsId == null ? null : groupPresentGoodsId.trim();
    }

    public Integer getGroupOrderDiscountPrice() {
        return groupOrderDiscountPrice;
    }

    public void setGroupOrderDiscountPrice(Integer groupOrderDiscountPrice) {
        this.groupOrderDiscountPrice = groupOrderDiscountPrice;
    }

    public Integer getGroupOrderDiscountValue() {
        return groupOrderDiscountValue;
    }

    public void setGroupOrderDiscountValue(Integer groupOrderDiscountValue) {
        this.groupOrderDiscountValue = groupOrderDiscountValue;
    }

    public Integer getGroupOrderDiscountPercent() {
        return groupOrderDiscountPercent;
    }

    public void setGroupOrderDiscountPercent(Integer groupOrderDiscountPercent) {
        this.groupOrderDiscountPercent = groupOrderDiscountPercent;
    }

    public Integer getOrderTopSaleType() {
        return orderTopSaleType;
    }

    public void setOrderTopSaleType(Integer orderTopSaleType) {
        this.orderTopSaleType = orderTopSaleType;
    }

    public Integer getOrderTopValue() {
        return orderTopValue;
    }

    public void setOrderTopValue(Integer orderTopValue) {
        this.orderTopValue = orderTopValue;
    }

    public String getOrderTopPresentGoodsId() {
        return orderTopPresentGoodsId;
    }

    public void setOrderTopPresentGoodsId(String orderTopPresentGoodsId) {
        this.orderTopPresentGoodsId = orderTopPresentGoodsId == null ? null : orderTopPresentGoodsId.trim();
    }

    public String getOrderAddtionalGoodsId() {
        return orderAddtionalGoodsId;
    }

    public void setOrderAddtionalGoodsId(String orderAddtionalGoodsId) {
        this.orderAddtionalGoodsId = orderAddtionalGoodsId == null ? null : orderAddtionalGoodsId.trim();
    }

    public Integer getOrderAddtionalGoodsPrice() {
        return orderAddtionalGoodsPrice;
    }

    public void setOrderAddtionalGoodsPrice(Integer orderAddtionalGoodsPrice) {
        this.orderAddtionalGoodsPrice = orderAddtionalGoodsPrice;
    }

    public Integer getOrderAddtionalGoodsCount() {
        return orderAddtionalGoodsCount;
    }

    public void setOrderAddtionalGoodsCount(Integer orderAddtionalGoodsCount) {
        this.orderAddtionalGoodsCount = orderAddtionalGoodsCount;
    }

    public Integer getOrderAddtionalGoodsMinCount() {
        return orderAddtionalGoodsMinCount;
    }

    public void setOrderAddtionalGoodsMinCount(Integer orderAddtionalGoodsMinCount) {
        this.orderAddtionalGoodsMinCount = orderAddtionalGoodsMinCount;
    }

    public Integer getOrderAddtionalGoodsMaxCount() {
        return orderAddtionalGoodsMaxCount;
    }

    public void setOrderAddtionalGoodsMaxCount(Integer orderAddtionalGoodsMaxCount) {
        this.orderAddtionalGoodsMaxCount = orderAddtionalGoodsMaxCount;
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

	public String getEndDateText() {
		if (this.endDate != null)
		{
			endDateText = DateFormatUtils.format(this.endDate, "yyyy-MM-dd");
		}
		
		return endDateText;
	}

	public void setEndDateText(String endDateText) {
		this.endDateText = endDateText;
	}

	public String getDiscountId() {
		return discountId;
	}

	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}

	public Integer getGroupGoodsCount() {
		
		if (groupGoodsIds != null)
		{
			groupGoodsCount = groupGoodsIds.split(",").length;
		}
		else{
			groupGoodsCount = 0;
		}
		
		return groupGoodsCount;
	}

	public void setGroupGoodsCount(Integer groupGoodsCount) {
		this.groupGoodsCount = groupGoodsCount;
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
		this.memberLevelId = memberLevelId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
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
		this.discountTypeName = discountTypeName;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
    
    
}