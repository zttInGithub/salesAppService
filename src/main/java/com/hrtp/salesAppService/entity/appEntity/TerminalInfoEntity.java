package com.hrtp.salesAppService.entity.appEntity;

import java.util.Date;

/**
 * 设备信息
 */
public class TerminalInfoEntity{
	private Integer btID;			//唯一主键
	private String unNO;			//机构编号
	private String termID;			//终端编号
	private String keyType;			//密钥类型
	private String keyContext;		//密钥明文
	private Date keyConfirmDate;	//合成时间
	private Date allotConfirmDate;	//分配时间
	private Date usedConfirmDate;	//使用时间
	private Integer status;			//状态
	private Integer maintainUID;	//操作人员  
	private Date maintainDate;		//操作日期
	private String maintainType;	//操作类型   
	private String sn;				//SN号
	private Integer isM35;			//0：非M35,1：M35
	private Double rate;			//费率
	private Double secondRate;		//秒到手续费
	private Double scanRate;		//秒到手续费
	private Integer rebateType;		//返利类型 1循环送；2返款； 空/0无返利
	private Integer depositAmt;	//金额
	private Integer snType;	//三级分销设备类型：1小蓝牙，2大蓝牙
	private String batchID; //导入批次号
	private String machineModel; //机型名称
	private String terOrderID; //导出批次号
	private Integer pdlid; //发货单号	
	private String storage;//库位
	private Date outDate;//出库时间
	private String mid;
	
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public Integer getBtID() {
		return btID;
	}
	public void setBtID(Integer btID) {
		this.btID = btID;
	}
	public String getUnNO() {
		return unNO;
	}
	public void setUnNO(String unNO) {
		this.unNO = unNO;
	}
	public String getTermID() {
		return termID;
	}
	public void setTermID(String termID) {
		this.termID = termID;
	}
	public String getKeyType() {
		return keyType;
	}
	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}
	public String getKeyContext() {
		return keyContext;
	}
	public void setKeyContext(String keyContext) {
		this.keyContext = keyContext;
	}
	public Date getKeyConfirmDate() {
		return keyConfirmDate;
	}
	public void setKeyConfirmDate(Date keyConfirmDate) {
		this.keyConfirmDate = keyConfirmDate;
	}
	public Date getAllotConfirmDate() {
		return allotConfirmDate;
	}
	public void setAllotConfirmDate(Date allotConfirmDate) {
		this.allotConfirmDate = allotConfirmDate;
	}
	public Date getUsedConfirmDate() {
		return usedConfirmDate;
	}
	public void setUsedConfirmDate(Date usedConfirmDate) {
		this.usedConfirmDate = usedConfirmDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getMaintainUID() {
		return maintainUID;
	}
	public void setMaintainUID(Integer maintainUID) {
		this.maintainUID = maintainUID;
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
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Integer getIsM35() {
		return isM35;
	}
	public void setIsM35(Integer isM35) {
		this.isM35 = isM35;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Double getSecondRate() {
		return secondRate;
	}
	public void setSecondRate(Double secondRate) {
		this.secondRate = secondRate;
	}
	public Double getScanRate() {
		return scanRate;
	}
	public void setScanRate(Double scanRate) {
		this.scanRate = scanRate;
	}
	public Integer getRebateType() {
		return rebateType;
	}
	public void setRebateType(Integer rebateType) {
		this.rebateType = rebateType;
	}
	public Integer getDepositAmt() {
		return depositAmt;
	}
	public void setDepositAmt(Integer depositAmt) {
		this.depositAmt = depositAmt;
	}
	public Integer getSnType() {
		return snType;
	}
	public void setSnType(Integer snType) {
		this.snType = snType;
	}
	public String getBatchID() {
		return batchID;
	}
	public void setBatchID(String batchID) {
		this.batchID = batchID;
	}
	public String getMachineModel() {
		return machineModel;
	}
	public void setMachineModel(String machineModel) {
		this.machineModel = machineModel;
	}
	public String getTerOrderID() {
		return terOrderID;
	}
	public void setTerOrderID(String terOrderID) {
		this.terOrderID = terOrderID;
	}
	public Integer getPdlid() {
		return pdlid;
	}
	public void setPdlid(Integer pdlid) {
		this.pdlid = pdlid;
	}
	public String getStorage() {
		return storage;
	}
	public void setStorage(String storage) {
		this.storage = storage;
	}
	public Date getOutDate() {
		return outDate;
	}
	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}
}
