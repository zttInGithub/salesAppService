package com.hrtp.salesAppService.entity.appEntity;

public class LoginRespEntity  extends RespEntity{
	private String unno;
	private Integer userLvl;
	private String userName;
	private Integer userId;
	private String BuId;
	private Integer agentType;//0 传统 1手刷
	private Integer unLvl;
	private Integer cashSwitch;//提现开关
	private String cashRatio;//提现比例
	private Integer isSale;//是否是业务员 1-是 0-不是
	private Integer isManager;//是否是经理级别   1-是 0-不是
	private Integer isLeader;//是否是组长   1-是 0-不是
	private String group;//组
	private String phone;//销售电话
	
	private String appVersion;//app版本号码

	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Integer getIsSale() {
		return isSale;
	}

	public void setIsSale(Integer isSale) {
		this.isSale = isSale;
	}

	public Integer getIsManager() {
		return isManager;
	}

	public void setIsManager(Integer isManager) {
		this.isManager = isManager;
	}

	public Integer getIsLeader() {
		return isLeader;
	}

	public void setIsLeader(Integer isLeader) {
		this.isLeader = isLeader;
	}

	public Integer getCashSwitch() {
		return cashSwitch;
	}

	public void setCashSwitch(Integer cashSwitch) {
		this.cashSwitch = cashSwitch;
	}

	public String getCashRatio() {
		return cashRatio;
	}

	public void setCashRatio(String cashRatio) {
		this.cashRatio = cashRatio;
	}

	public Integer getUnLvl() {
		return unLvl;
	}

	public void setUnLvl(Integer unLvl) {
		this.unLvl = unLvl;
	}

	public String getBuId() {
		return BuId;
	}

	public void setBuId(String buId) {
		BuId = buId;
	}

	public String getUnno() {
		return unno;
	}

	public void setUnno(String unno) {
		this.unno = unno;
	}

	public Integer getUserLvl() {
		return userLvl;
	}

	public void setUserLvl(Integer userLvl) {
		this.userLvl = userLvl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getAgentType() {
		return agentType;
	}

	public void setAgentType(Integer agentType) {
		this.agentType = agentType;
	}
	
}
