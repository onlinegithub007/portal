package com.fuhuitong.applychain.model;

import java.util.Date;

public class MerchantUsersSession 
{
    private String merUserToken;

    private String merGroupId;
    
    private String groupName;
    
    private String userName;

    private String merId;

    private String merUserId;

    private Date expiredDate;

    private Date createDate;

    private String version;

    private String os;

    private String deviceType;

    private String ipAddress;

    private String longitude;

    private String latitude;

    private Boolean valid;

    public String getMerUserToken() {
        return merUserToken;
    }

    public void setMerUserToken(String merUserToken) {
        this.merUserToken = merUserToken == null ? null : merUserToken.trim();
    }

    public String getMerGroupId() {
        return merGroupId;
    }

    public void setMerGroupId(String merGroupId) {
        this.merGroupId = merGroupId == null ? null : merGroupId.trim();
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public String getMerUserId() {
        return merUserId;
    }

    public void setMerUserId(String merUserId) {
        this.merUserId = merUserId == null ? null : merUserId.trim();
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
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

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
    
    public void setUserName(String userName) {
		this.userName = userName;
	}
    
    public String getUserName() {
		return userName;
	}
    
    public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
    
    public String getGroupName() {
		return groupName;
	}
}