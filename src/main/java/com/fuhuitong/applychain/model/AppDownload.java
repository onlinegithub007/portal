package com.fuhuitong.applychain.model;

import java.util.Date;

public class AppDownload {
    private Long appDownId;

    private Integer appVersionId;

    private Integer softId;

    private String ipAddress;

    private String longitude;

    private String latitude;

    private Date createDate;
    
    private String version;

    private String os;

    private String deviceType;

    public Long getAppDownId() {
        return appDownId;
    }

    public void setAppDownId(Long appDownId) {
        this.appDownId = appDownId;
    }

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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
    
    public void setVersion(String version) {
		this.version = version;
	}
    
    public String getVersion() {
		return version;
	}
}