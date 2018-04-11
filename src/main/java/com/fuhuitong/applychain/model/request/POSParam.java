package com.fuhuitong.applychain.model.request;

public class POSParam
{
	private String ipAddress;

    private String longitude;

    private String latitude;

    private String version;

    private String os;

    private String deviceType;
    
    private int softId;
    
    private String token;

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
    
    public void setSoftId(int softId) {
		this.softId = softId;
	}
    
    public int getSoftId() {
		return softId;
	}
    
    public void setToken(String token) {
		this.token = token;
	}
    
    public String getToken() {
		return token;
	}
}
