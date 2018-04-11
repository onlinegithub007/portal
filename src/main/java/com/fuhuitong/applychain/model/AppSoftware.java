package com.fuhuitong.applychain.model;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.StringUtils;

import java.util.Date;

public class AppSoftware  extends Pagable
{
    private Integer softId;

    private String appName;

    private String os;

    private String appDesc;

    private Date createDate;
    
    private String createDateText;

    private Integer downloadCount;

    public Integer getSoftId() {
        return softId;
    }

    public void setSoftId(Integer softId) {
        this.softId = softId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os == null ? null : os.trim();
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc == null ? null : appDesc.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }
    
    public String getCreateDateText() {
		
    	if (!StringUtils.isEmpty(createDate))
    	{
    		createDateText = DateFormatUtils.format(createDate, "yyyy-MM-dd");
    	}
    	else
    	{
    		createDateText = "";
    	}
    	return createDateText;
	}
}