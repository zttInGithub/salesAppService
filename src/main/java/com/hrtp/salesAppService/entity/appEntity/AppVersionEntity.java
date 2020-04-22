package com.hrtp.salesAppService.entity.appEntity;

public class AppVersionEntity{
	
	private String   Version;
	private String   url;
	private String   versionDesc;
	private boolean   isForceUpdate;
	public String getVersion() {
		return Version;
	}
	public void setVersion(String version) {
		Version = version;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getVersionDesc() {
		return versionDesc;
	}
	public void setVersionDesc(String versionDesc) {
		this.versionDesc = versionDesc;
	}
	public boolean getIsForceUpdate() {
		return isForceUpdate;
	}
	public void setIsForceUpdate(boolean isForceUpdate) {
		this.isForceUpdate = isForceUpdate;
	}
	
	
}
