package com.fuhuitong.applychain.model;

import com.fuhuitong.applychain.utils.MoneyUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class Members extends Pagable
{
    private Long memberId;

    private String merId;

    private String clientLevelId;
    
    private String levelName;

    private Integer levelValue;

    private String memberCode;

    private String memberName;

    private String nickName;

    private Integer gender;

    private Date birthday;

    private String personalId;

    private Integer mobileSp;

    private String cardNo;

    private String memberMk;

    private Integer memberScore;

    private Integer memberBalance;
    
    private String memberBalanceText;

    private Date createDate;
    
    private String createDateText;

    private String storeId;
    
    private String groupName;

    private Date attentionDate;

    private String attentionDateText;
    
    private boolean locked;

    private String lockedToken;

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

    public void setClientLevelId(String clientLevelId) {
		this.clientLevelId = clientLevelId;
	}
    
    public String getClientLevelId() {
		return clientLevelId;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId == null ? null : personalId.trim();
    }

    public Integer getMobileSp() {
        return mobileSp;
    }

    public void setMobileSp(Integer mobileSp) {
        this.mobileSp = mobileSp;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public String getMemberMk() {
        return memberMk;
    }

    public void setMemberMk(String memberMk) {
        this.memberMk = memberMk == null ? null : memberMk.trim();
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId == null ? null : storeId.trim();
    }

    public Date getAttentionDate() {
        return attentionDate;
    }

    public void setAttentionDate(Date attentionDate) {
        this.attentionDate = attentionDate;
    }

    public boolean isLocked() {
		return locked;
	}

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getLockedToken() {
        return lockedToken;
    }

    public void setLockedToken(String lockedToken) {
        this.lockedToken = lockedToken == null ? null : lockedToken.trim();
    }

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Integer getLevelValue() {
		return levelValue;
	}

	public void setLevelValue(Integer levelValue) {
		this.levelValue = levelValue;
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

	public String getAttentionDateText() {
		
		if (this.attentionDate != null)
		{
			attentionDateText = DateFormatUtils.format(this.attentionDate, "yyyy-MM-dd");
		}
		
		return attentionDateText;
	}

	public void setAttentionDateText(String attentionDateText) {
		this.attentionDateText = attentionDateText;
	}
    
    public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
    
    public String getGroupName() {
		return groupName;
	}
    
    public String getMemberBalanceText() {
	
    	if (memberBalance != null && memberBalance > 0)
    	{
    		memberBalanceText = MoneyUtils.getMoneyText(memberBalance.intValue());
    	}
    	
    	return memberBalanceText;
	}

    
}