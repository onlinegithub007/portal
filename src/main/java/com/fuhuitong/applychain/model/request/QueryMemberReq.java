package com.fuhuitong.applychain.model.request;

public class QueryMemberReq extends POSParam {
	private String memberCode;

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode == null ? null : memberCode.trim();
	}
	
	
}
