package com.fuhuitong.applychain.model.response;

public class QueryMemberResp {
	
	private HeadResp headResp;
	
	private String retCode;
	
	private String memberCode;
	private String memberName;
	private String levelName;
	private Integer memberScore;
    private Integer memberBalance;
    private String memberPayToken;

	public HeadResp getHeadResp() {
		return headResp;
	}

	public void setHeadResp(HeadResp headResp) {
		this.headResp = headResp;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Integer getMemberScore() {
		return memberScore;
	}

	public void setMemberScore(Integer memberScore) {
		this.memberScore = memberScore;
	}

	public Integer getMemberBalance() {
		return memberBalance;
	}

	public void setMemberBalance(Integer memberBalance) {
		this.memberBalance = memberBalance;
	}

	public String getMemberPayToken() {
		return memberPayToken;
	}

	public void setMemberPayToken(String memberPayToken) {
		this.memberPayToken = memberPayToken;
	}
	
}
