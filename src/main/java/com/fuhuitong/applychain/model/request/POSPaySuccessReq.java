package com.fuhuitong.applychain.model.request;

public class POSPaySuccessReq extends POSParam
{
	private String orderId;
	
	private int payStatus;
	
	private int payMethod;
	
	private int payAmount;
	
	private String payDate;
	
	private String payBillNumber;
	
	private int cashAmount;
	
	private int cashChange;
	
	private String memberPayToken;
	
	private String secondPayNumber;

    private String merCode;

    private String merName;

    private String termCode;

    private String bankName;

    private String bankCard;

    private String bankCardExpired;

    private String authorCode;

    private String tradeBatchCode;

    private String referCode;
    
    private String termSn;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}
	
	public int getPayStatus() {
		return payStatus;
	}

	public int getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(int payMethod) {
		this.payMethod = payMethod;
	}

	public String getPayBillNumber() {
		return payBillNumber;
	}

	public void setPayBillNumber(String payBillNumber) {
		this.payBillNumber = payBillNumber;
	}

	public int getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(int cashAmount) {
		this.cashAmount = cashAmount;
	}

	public int getCashChange() {
		return cashChange;
	}

	public void setCashChange(int cashChange) {
		this.cashChange = cashChange;
	}
	
	public void setMemberPayToken(String memberPayToken) {
		this.memberPayToken = memberPayToken;
	}
	
	public String getMemberPayToken() {
		return memberPayToken;
	}
	
	public String getPayDate() {
		return payDate;
	}
	
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	
	public void setPayAmount(int payAmount) {
		this.payAmount = payAmount;
	}
	
	public int getPayAmount() {
		return payAmount;
	}

	public String getSecondPayNumber() {
		return secondPayNumber;
	}

	public void setSecondPayNumber(String secondPayNumber) {
		this.secondPayNumber = secondPayNumber;
	}

	public String getMerCode() {
		return merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public String getMerName() {
		return merName;
	}

	public void setMerName(String merName) {
		this.merName = merName;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getBankCardExpired() {
		return bankCardExpired;
	}

	public void setBankCardExpired(String bankCardExpired) {
		this.bankCardExpired = bankCardExpired;
	}

	public String getAuthorCode() {
		return authorCode;
	}

	public void setAuthorCode(String authorCode) {
		this.authorCode = authorCode;
	}

	public String getTradeBatchCode() {
		return tradeBatchCode;
	}

	public void setTradeBatchCode(String tradeBatchCode) {
		this.tradeBatchCode = tradeBatchCode;
	}

	public String getReferCode() {
		return referCode;
	}

	public void setReferCode(String referCode) {
		this.referCode = referCode;
	}

	public String getTermSn() {
		return termSn;
	}

	public void setTermSn(String termSn) {
		this.termSn = termSn;
	}
	
	
}
