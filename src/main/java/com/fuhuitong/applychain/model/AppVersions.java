package com.fuhuitong.applychain.model;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.StringUtils;

import java.util.Date;

public class AppVersions extends Pagable
{
    private Integer appVersionId;

    private Integer softId;
    
    private String appName;
    
    private String os;

    private String spCode;

    private String merId;

    private String version;

    private Integer versionSn;

    private Integer appSize;

    private Date createDate;
    
    private String createDateText;

    private String appDesc;

    private Integer upgradeOption;
    
    private String upgradeOptionDesc;

    private String md5;

    private String filePath;

    private String fileName;

    public Integer getAppVersionId() {
        return appVersionId;
    }

    public void setAppVersionId(Integer appVersionId) {
        this.appVersionId = appVersionId;
    }

    public Integer getSoftId() {
        return softId;
    }

    public void setSoftId(Integer softId) {
        this.softId = softId;
    }

    public String getSpCode() {
        return spCode;
    }

    public void setSpCode(String spCode) {
        this.spCode = spCode == null ? null : spCode.trim();
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public Integer getVersionSn() {
        return versionSn;
    }

    public void setVersionSn(Integer versionSn) {
        this.versionSn = versionSn;
    }

    public Integer getAppSize() {
        return appSize;
    }

    public void setAppSize(Integer appSize) {
        this.appSize = appSize;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc == null ? null : appDesc.trim();
    }

    public Integer getUpgradeOption() {
        return upgradeOption;
    }

    public void setUpgradeOption(Integer upgradeOption) {
        this.upgradeOption = upgradeOption;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5 == null ? null : md5.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }
    
    public void setAppName(String appName) {
		this.appName = appName == null ? null : appName.trim();
	}
    
    public String getAppName() {
		return appName;
	}
    
    public void setOs(String os) {
		this.os = os;
	}
    
    public String getOs() {
		return os;
	}
    
    public String getUpgradeOptionDesc() {
    	
    	if (this.upgradeOption == 0)
    	{
    		upgradeOptionDesc = "否";
    	}
    	else
    	{
    		upgradeOptionDesc = "是";
    	}
    	
		return upgradeOptionDesc;
	}
    
    public String getCreateDateText() {
    	
    	if (!StringUtils.isEmpty(createDate))
    	{
    		createDateText = DateFormatUtils.format(createDate, "yyyy-MM-dd HH:mm:ss");
    	}
    	else
    	{
    		createDateText = "";
    	}
    	
		return createDateText;
	}
}