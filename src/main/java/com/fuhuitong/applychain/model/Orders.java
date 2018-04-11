package com.fuhuitong.applychain.model;

import com.fuhuitong.applychain.utils.MoneyUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.DecimalFormat;
import java.util.Date;

public class Orders extends Pagable
{
    private String orderId;
    
    private int orderType;

    private String merUserId;

    private Long memberId;

    private String merId;
    
    private String operatorId;
    
    private String operatorName;

    private String spCode;

    private String merGroupId;
    
    private String groupName;

    private String orderCode;

    private Date createDate;
    
    private String createDateText;
    
    private String endDateText;

    private Integer totalPrice;
    
    private String totalPriceText;

    private Integer finalPrice;
    
    private String finalPriceText;
    
    private Integer totalCount;

    private Integer memberDiscount;

    private String memberCode;

    private String paymentInfo;

    private Integer orderStatus;

    private Integer payAmount;
    
    private Integer payMethod;

    private Date payDate;
    
    private String payDateText;

    private String payBillNumber;

    private Integer cashAmount;

    private Integer cashChange;

    private Integer memberBalancePay;

    private Integer discountAmount;
    
    private String discountAmountText;

    private Integer cardCouponPay;

    private String version;

    private String os;

    private String deviceType;

    private String longitude;

    private String latitude;

    private String ipAddress;

    private Boolean orderBillPrint;

    private String orderBill;
    
    private Integer orderProfit = 0;
    
    private String orderProfitText;
    
    private Integer indexType = 0;
    
    private String hour;
    
    private Float percent;
    
    private String percentText;
    
    private String secondPayNumber;

    private String merCode;

    private String merName;

    private String termCode;

    private String bankName;

    private String bankCard;
    
    private String bankCardType;

    private String bankCardExpired;

    private String authorCode;

    private String tradeBatchCode;

    private String referCode;
    
    private Integer orderConfirm;

    private Date orderConfirmDate;
    
    private String orderConfirmDateText;

    private String orderConfirmPerson;
    
    private String termSn;

    public String getTermSn() {
		return termSn;
	}

	public void setTermSn(String termSn) {
		this.termSn = termSn;
	}

	public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getMerUserId() {
        return merUserId;
    }

    public void setMerUserId(String merUserId) {
        this.merUserId = merUserId == null ? null : merUserId.trim();
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public String getSpCode() {
        return spCode;
    }

    public void setSpCode(String spCode) {
        this.spCode = spCode == null ? null : spCode.trim();
    }

    public String getMerGroupId() {
        return merGroupId;
    }

    public void setMerGroupId(String merGroupId) {
        this.merGroupId = merGroupId == null ? null : merGroupId.trim();
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
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

    public Integer getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Integer finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Integer getMemberDiscount() {
        return memberDiscount;
    }

    public void setMemberDiscount(Integer memberDiscount) {
        this.memberDiscount = memberDiscount;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode == null ? null : memberCode.trim();
    }

    public String getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(String paymentInfo) {
        this.paymentInfo = paymentInfo == null ? null : paymentInfo.trim();
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Integer payAmount) {
        this.payAmount = payAmount;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getPayBillNumber() {
        return payBillNumber;
    }

    public void setPayBillNumber(String payBillNumber) {
        this.payBillNumber = payBillNumber == null ? null : payBillNumber.trim();
    }

    public Integer getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(Integer cashAmount) {
        this.cashAmount = cashAmount;
    }

    public Integer getCashChange() {
        return cashChange;
    }

    public void setCashChange(Integer cashChange) {
        this.cashChange = cashChange;
    }

    public Integer getMemberBalancePay() {
        return memberBalancePay;
    }

    public void setMemberBalancePay(Integer memberBalancePay) {
        this.memberBalancePay = memberBalancePay;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getCardCouponPay() {
        return cardCouponPay;
    }

    public void setCardCouponPay(Integer cardCouponPay) {
        this.cardCouponPay = cardCouponPay;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os == null ? null : os.trim();
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
    }

    public Boolean getOrderBillPrint() {
        return orderBillPrint;
    }

    public void setOrderBillPrint(Boolean orderBillPrint) {
        this.orderBillPrint = orderBillPrint;
    }

    public String getOrderBill() {
        return orderBill;
    }

    public void setOrderBill(String orderBill) {
        this.orderBill = orderBill;
    }
    
    public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}
    
    public Integer getPayMethod() {
		return payMethod;
	}
    
    public void setCreateDateText(String createDateText) {
		this.createDateText = createDateText;
	}
    
    public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
    
    public String getGroupName() {
		return groupName;
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

	public String getTotalPriceText() {
		
    	if (totalPrice != null)
    	{
    		totalPriceText = MoneyUtils.getMoneyText(this.totalPrice);
    	}
    	
    	return totalPriceText;
	}
    
    public String getFinalPriceText() {
		
    	if (finalPrice != null)
    	{
    		finalPriceText = MoneyUtils.getMoneyText(this.finalPrice);
    	}
    	
    	return finalPriceText;
	}
    
    public String getDiscountAmountText() {
    	
		if (discountAmount != null)
		{
			discountAmountText = MoneyUtils.getMoneyText(this.discountAmount);
		}
    	
    	return discountAmountText;
	}
    
    public String getCreateDateText() {
		if (this.createDate != null)
		{
			createDateText = DateFormatUtils.format(createDate, "yyyy-MM-dd HH:mm:ss");
		}
    	
    	return createDateText;
	}
    
    public void setPayDateText(String payDateText) {
		this.payDateText = payDateText;
	}
    
    public String getPayDateText() {
		
    	if (this.payDate != null)
		{
    		payDateText = DateFormatUtils.format(payDate, "yyyy-MM-dd HH:mm:ss");
		}
    	
    	return payDateText;
	}
    
    public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
    
    public Integer getTotalCount() {
		return totalCount;
	}

	public Integer getOrderProfit() {
		return orderProfit;
	}

	public void setOrderProfit(Integer orderProfit) {
		this.orderProfit = orderProfit;
	}

	public String getEndDateText() {
		return endDateText;
	}

	public void setEndDateText(String endDateText) {
		this.endDateText = endDateText;
	}

	public Integer getIndexType() {
		return indexType;
	}

	public void setIndexType(Integer indexType) {
		this.indexType = indexType;
	}
    
    public String getOrderProfitText() {
    	if (orderProfit != null)
    	{
    		orderProfitText = MoneyUtils.getMoneyText(orderProfit);
    	}
    	
    	return orderProfitText;
	}
    
    public void setHour(String hour) {
		this.hour = hour;
	}
    
    public String getHour() {
		return hour;
	}
    
    public void setPercent(Float percent) {
    	this.percent = percent;
	}
    
    public String getPercentText() {
    	
    	if (this.percent != null)
    	{
    		DecimalFormat decimalFormat=new DecimalFormat("0.0");
    		percentText = decimalFormat.format(this.percent) + "%";
    	}
    	
    	return percentText;
	}
    
    public String getSecondPayNumber() {
        return secondPayNumber;
    }

    public void setSecondPayNumber(String secondPayNumber) {
        this.secondPayNumber = secondPayNumber == null ? null : secondPayNumber.trim();
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode == null ? null : merCode.trim();
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName == null ? null : merName.trim();
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode == null ? null : termCode.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard == null ? null : bankCard.trim();
    }

    public String getBankCardExpired() {
        return bankCardExpired;
    }

    public void setBankCardExpired(String bankCardExpired) {
        this.bankCardExpired = bankCardExpired == null ? null : bankCardExpired.trim();
    }

    public String getAuthorCode() {
        return authorCode;
    }

    public void setAuthorCode(String authorCode) {
        this.authorCode = authorCode == null ? null : authorCode.trim();
    }

    public String getTradeBatchCode() {
        return tradeBatchCode;
    }

    public void setTradeBatchCode(String tradeBatchCode) {
        this.tradeBatchCode = tradeBatchCode == null ? null : tradeBatchCode.trim();
    }

    public String getReferCode() {
        return referCode;
    }

    public void setReferCode(String referCode) {
        this.referCode = referCode == null ? null : referCode.trim();
    }

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	
	public Integer getOrderConfirm() {
        return orderConfirm;
    }

    public void setOrderConfirm(Integer orderConfirm) {
        this.orderConfirm = orderConfirm;
    }

    public Date getOrderConfirmDate() {
        return orderConfirmDate;
    }

    public void setOrderConfirmDate(Date orderConfirmDate) {
        this.orderConfirmDate = orderConfirmDate;
    }
    
    public String getOrderConfirmDateText() {
		if (this.orderConfirmDate != null)
		{
			orderConfirmDateText = DateFormatUtils.format(orderConfirmDate, "yyyy-MM-dd HH:mm:ss");
		}
    	
    	return orderConfirmDateText;
	}

    public String getOrderConfirmPerson() {
        return orderConfirmPerson;
    }

    public void setOrderConfirmPerson(String orderConfirmPerson) {
        this.orderConfirmPerson = orderConfirmPerson == null ? null : orderConfirmPerson.trim();
    }
    
    public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}
    
    public String getBankCardType() {
		return bankCardType;
	}

	@Override
	public String toString() {
		return "Orders [orderId=" + orderId + ", orderType=" + orderType + ", merUserId=" + merUserId + ", memberId="
				+ memberId + ", merId=" + merId + ", operatorId=" + operatorId + ", operatorName=" + operatorName
				+ ", spCode=" + spCode + ", merGroupId=" + merGroupId + ", groupName=" + groupName + ", orderCode="
				+ orderCode + ", createDate=" + createDate + ", createDateText=" + createDateText + ", endDateText="
				+ endDateText + ", totalPrice=" + totalPrice + ", totalPriceText=" + totalPriceText + ", finalPrice="
				+ finalPrice + ", finalPriceText=" + finalPriceText + ", totalCount=" + totalCount + ", memberDiscount="
				+ memberDiscount + ", memberCode=" + memberCode + ", paymentInfo=" + paymentInfo + ", orderStatus="
				+ orderStatus + ", payAmount=" + payAmount + ", payMethod=" + payMethod + ", payDate=" + payDate
				+ ", payDateText=" + payDateText + ", payBillNumber=" + payBillNumber + ", cashAmount=" + cashAmount
				+ ", cashChange=" + cashChange + ", memberBalancePay=" + memberBalancePay + ", discountAmount="
				+ discountAmount + ", discountAmountText=" + discountAmountText + ", cardCouponPay=" + cardCouponPay
				+ ", version=" + version + ", os=" + os + ", deviceType=" + deviceType + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", ipAddress=" + ipAddress + ", orderBillPrint=" + orderBillPrint
				+ ", orderBill=" + orderBill + ", orderProfit=" + orderProfit + ", orderProfitText=" + orderProfitText
				+ ", indexType=" + indexType + ", hour=" + hour + ", percent=" + percent + ", percentText="
				+ percentText + ", secondPayNumber=" + secondPayNumber + ", merCode=" + merCode + ", merName=" + merName
				+ ", termCode=" + termCode + ", bankName=" + bankName + ", bankCard=" + bankCard + ", bankCardType="
				+ bankCardType + ", bankCardExpired=" + bankCardExpired + ", authorCode=" + authorCode
				+ ", tradeBatchCode=" + tradeBatchCode + ", referCode=" + referCode + ", orderConfirm=" + orderConfirm
				+ ", orderConfirmDate=" + orderConfirmDate + ", orderConfirmDateText=" + orderConfirmDateText
				+ ", orderConfirmPerson=" + orderConfirmPerson + ", termSn=" + termSn + "]";
	}
    
    
}