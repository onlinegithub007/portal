package com.fuhuitong.applychain.model.response;

public class PosUserLoginResp 
{
	private HeadResp headResp;
	
	private String loginRet;
	
	private String token;
	
	private String userName;
	
	private String userId;
	
	private String storeId;
	
	private String storeName;
	
	private String merName;
	
	private String merchantId;
	
	private String weixinMerCode;
	
	private String alipayMerCode;
	
	private String bestPayCode;

    private String bankPayCode;

	public HeadResp getHeadResp() {
		return headResp;
	}

	public void setHeadResp(HeadResp headResp) {
		this.headResp = headResp;
	}

	public String getLoginRet() {
		return loginRet;
	}

	public void setLoginRet(String loginRet) {
		this.loginRet = loginRet;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getWeixinMerCode() {
		return weixinMerCode;
	}

	public void setWeixinMerCode(String weixinMerCode) {
		this.weixinMerCode = weixinMerCode;
	}

	public String getAlipayMerCode() {
		return alipayMerCode;
	}

	public void setAlipayMerCode(String alipayMerCode) {
		this.alipayMerCode = alipayMerCode;
	}
	
	public void setMerName(String merName) {
		this.merName = merName;
	}
	
	public String getMerName() {
		return merName;
	}

	public String getBestPayCode() {
		return bestPayCode;
	}

	public void setBestPayCode(String bestPayCode) {
		this.bestPayCode = bestPayCode;
	}

	public String getBankPayCode() {
		return bankPayCode;
	}

	public void setBankPayCode(String bankPayCode) {
		this.bankPayCode = bankPayCode;
	}
	
	
}
