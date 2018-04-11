package com.fuhuitong.applychain.model.response;

public class CheckNewVersionResp 
{
	private String retCode;
	private boolean hasNewVersion;
	private String newVersionHint;
	private boolean forceUpgrade;
	private String upgradeUrl;
	private String md5;
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public boolean isHasNewVersion() {
		return hasNewVersion;
	}
	public void setHasNewVersion(boolean hasNewVersion) {
		this.hasNewVersion = hasNewVersion;
	}
	public String getNewVersionHint() {
		return newVersionHint;
	}
	public void setNewVersionHint(String newVersionHint) {
		this.newVersionHint = newVersionHint;
	}
	public boolean isForceUpgrade() {
		return forceUpgrade;
	}
	public void setForceUpgrade(boolean forceUpgrade) {
		this.forceUpgrade = forceUpgrade;
	}
	public String getUpgradeUrl() {
		return upgradeUrl;
	}
	public void setUpgradeUrl(String upgradeUrl) {
		this.upgradeUrl = upgradeUrl;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	
	
}
