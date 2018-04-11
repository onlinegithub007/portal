package com.fuhuitong.applychain.model;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.StringUtils;

import java.util.Date;

public class Merchants extends Pagable{
    private String merId;

    private String spCode;

    private String areaId;
    
    private String areaFullName;

    private String merCode;

    private String merName;

    private String merFullName;

    private String merLogo;
    
    private String adminAccount;

    private String trade;

    private String merScale;

    private String merAddress;

    private String linkMan;

    private String linkManPhone;

    private String linkManEmail;

    private Date createDate;
    
    private String createDateText;
    
    private String productRoleIds;

    private String creator;

    private Date checkDate;

    private String checkor;

    private String weixinPayCode;

    private String noticeUrl;

    private String alipayPayCode;
    
    private Integer merStatus;
    
    private Integer delStatus;
    
    private String merStatusText;

    private Integer productRoleId;

    private Integer merRoleStatus;
    
    private String bestPayCode;

    private String bankPayCode;

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

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
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

    public String getMerFullName() {
        return merFullName;
    }

    public void setMerFullName(String merFullName) {
        this.merFullName = merFullName == null ? null : merFullName.trim();
    }

    public String getMerLogo() {
        return merLogo;
    }

    public void setMerLogo(String merLogo) {
        this.merLogo = merLogo == null ? null : merLogo.trim();
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade == null ? null : trade.trim();
    }

    public String getMerScale() {
        return merScale;
    }

    public void setMerScale(String merScale) {
        this.merScale = merScale == null ? null : merScale.trim();
    }

    public String getMerAddress() {
        return merAddress;
    }

    public void setMerAddress(String merAddress) {
        this.merAddress = merAddress == null ? null : merAddress.trim();
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan == null ? null : linkMan.trim();
    }

    public String getLinkManPhone() {
        return linkManPhone;
    }

    public void setLinkManPhone(String linkManPhone) {
        this.linkManPhone = linkManPhone == null ? null : linkManPhone.trim();
    }

    public String getLinkManEmail() {
        return linkManEmail;
    }

    public void setLinkManEmail(String linkManEmail) {
        this.linkManEmail = linkManEmail == null ? null : linkManEmail.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckor() {
        return checkor;
    }

    public void setCheckor(String checkor) {
        this.checkor = checkor == null ? null : checkor.trim();
    }

    public void setAlipayPayCode(String alipayPayCode) {
		this.alipayPayCode = alipayPayCode == null ? null : alipayPayCode.trim();
	}
    
    public String getAlipayPayCode() {
		return alipayPayCode;
	}
    
    public void setWeixinPayCode(String weixinPayCode) {
		this.weixinPayCode = weixinPayCode == null ? null : weixinPayCode.trim();
	}
    
    public String getWeixinPayCode() {
		return weixinPayCode;
	}

    public String getNoticeUrl() {
        return noticeUrl;
    }

    public void setNoticeUrl(String noticeUrl) {
        this.noticeUrl = noticeUrl == null ? null : noticeUrl.trim();
    }
    
    public String getProductRoleIds() {
		return productRoleIds;
	}

	public void setProductRoleIds(String productRoleIds) {
		this.productRoleIds = productRoleIds;
	}
	
	public void setAdminAccount(String adminAccount) {
		this.adminAccount = adminAccount == null ? null : adminAccount.trim();
	}
	
	public String getAdminAccount() {
		return adminAccount;
	}
	
	public Integer getProductRoleId() {
		return productRoleId;
	}

	public void setProductRoleId(Integer productRoleId) {
		this.productRoleId = productRoleId;
	}

	public Integer getMerRoleStatus() {
		return merRoleStatus;
	}

	public void setMerRoleStatus(Integer merRoleStatus) {
		this.merRoleStatus = merRoleStatus;
	}

	public Integer getMerStatus() {
		return merStatus;
	}

	public void setMerStatus(Integer merStatus) {
		this.merStatus = merStatus;
	}
	
	public void setAreaFullName(String areaFullName) {
		this.areaFullName = areaFullName;
	}
	
	public void setDelStatus(Integer delStatus) {
		this.delStatus = delStatus;
	}
	
	public Integer getDelStatus() {
		return delStatus;
	}
	
	public String getAreaFullName() {
		return areaFullName;
	}

	public String getMerStatusText() {
		if (this.merStatus == 0)
		{
			merStatusText = "已上线";
		}
		else
		{
			merStatusText = "已下线";
		}
		
		return merStatusText;
	}
	
	public String getCreateDateText() {
    	
    	if (!StringUtils.isEmpty(this.createDate))
    	{
    		createDateText = DateFormatUtils.format(createDate, "yyyy-MM-dd HH:mm:ss");
    	}
    	else
    	{
    		createDateText = "";
    	}
    	
		return createDateText;
	}
	
	public String getBestPayCode() {
        return bestPayCode;
    }

    public void setBestPayCode(String bestPayCode) {
        this.bestPayCode = bestPayCode == null ? null : bestPayCode.trim();
    }

    public String getBankPayCode() {
        return bankPayCode;
    }

    public void setBankPayCode(String bankPayCode) {
        this.bankPayCode = bankPayCode == null ? null : bankPayCode.trim();
    }
}