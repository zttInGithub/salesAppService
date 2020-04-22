package com.hrtp.salesAppService.entity.appEntity;

import com.alibaba.fastjson.JSONObject;

public class ReqEntity {
	private String appVersion;
	private String os;
	private String osVersion;
	private String agentID;
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public String getAgentID() {
		return agentID;
	}
	public void setAgentID(String agentID) {
		this.agentID = agentID;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
