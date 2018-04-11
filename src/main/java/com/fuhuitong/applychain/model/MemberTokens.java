package com.fuhuitong.applychain.model;

import java.util.Date;

public class MemberTokens 
{
    private Long memTokenId;

    private Long memberId;

    private String menmberToken;

    private Date expiredDate;
    
    private Date createDate;

    public Long getMemTokenId() {
        return memTokenId;
    }

    public void setMemTokenId(Long memTokenId) {
        this.memTokenId = memTokenId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMenmberToken() {
        return menmberToken;
    }

    public void setMenmberToken(String menmberToken) {
        this.menmberToken = menmberToken == null ? null : menmberToken.trim();
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }
    
    public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
    
    public Date getCreateDate() {
		return createDate;
	}
}