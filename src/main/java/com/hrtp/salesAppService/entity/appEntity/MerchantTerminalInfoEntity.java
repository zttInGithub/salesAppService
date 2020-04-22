package com.hrtp.salesAppService.entity.appEntity;

import java.util.Date;

/**
 * 商户设备中间表
 */
public class MerchantTerminalInfoEntity{
	private Integer bmtid;				//唯一主键
	private String unno;				//机构编号
	private String mid;					//商户编号
	private String tid;					//终端编号
	private String sn;					//sn
	private Integer bmaid;				//设备编号
	private String installedAddress;	//装机地址
	private String installedName;		//装机名称
	private String contactPerson;		//联系人
	private String contactPhone;		//联系人电话
	private String contactTel;			//联系人固话
	private String processContext;		//受理描述
	private Integer maintainUid;		//操作人员
	private Date maintainDate;			//操作日期
	private String maintainType;		//变更类型
	private Integer approveUid;			//授权人员
	private Date approveDate;			//授权日期
	private String approveStatus;		//授权状态
	private Integer status;             //状态
	private String installedSIM;		//sim卡
	private Integer depositFlag;	//1--收取押金   0 -不收取押金
	
	public Integer getBmtid() {
		return bmtid;
	}
	public void setBmtid(Integer bmtid) {
		this.bmtid = bmtid;
	}
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Integer getBmaid() {
		return bmaid;
	}
	public void setBmaid(Integer bmaid) {
		this.bmaid = bmaid;
	}
	public String getInstalledAddress() {
		return installedAddress;
	}
	public void setInstalledAddress(String installedAddress) {
		this.installedAddress = installedAddress;
	}
	public String getInstalledName() {
		return installedName;
	}
	public void setInstalledName(String installedName) {
		this.installedName = installedName;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContactTel() {
		return contactTel;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	public String getProcessContext() {
		return processContext;
	}
	public void setProcessContext(String processContext) {
		this.processContext = processContext;
	}
	public Integer getMaintainUid() {
		return maintainUid;
	}
	public void setMaintainUid(Integer maintainUid) {
		this.maintainUid = maintainUid;
	}
	public Date getMaintainDate() {
		return maintainDate;
	}
	public void setMaintainDate(Date maintainDate) {
		this.maintainDate = maintainDate;
	}
	public String getMaintainType() {
		return maintainType;
	}
	public void setMaintainType(String maintainType) {
		this.maintainType = maintainType;
	}
	public Integer getApproveUid() {
		return approveUid;
	}
	public void setApproveUid(Integer approveUid) {
		this.approveUid = approveUid;
	}
	public Date getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	public String getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getInstalledSIM() {
		return installedSIM;
	}
	public void setInstalledSIM(String installedSIM) {
		this.installedSIM = installedSIM;
	}
	public Integer getDepositFlag() {
		return depositFlag;
	}
	public void setDepositFlag(Integer depositFlag) {
		this.depositFlag = depositFlag;
	}
}
