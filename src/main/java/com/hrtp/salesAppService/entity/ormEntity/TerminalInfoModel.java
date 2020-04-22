package com.hrtp.salesAppService.entity.ormEntity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "BILL_TERMINALINFO")
public class TerminalInfoModel implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "BTID")
	private Integer btID;			//唯一主键
	
	@Column(name = "UNNO")
	private String unno;			//机构编号
	
	@Column(name = "TERMID")
	private String termID;			//终端编号
	
	@Column(name = "KEYTYPE")
	private String keyType;			//密钥类型
	
	@Column(name = "KEYCONTEXT")
	private String keyContext;		//密钥明文
	
	@Column(name = "KEYCONFIRMDATE")
	private Date keyConfirmDate;	//合成时间
	
	@Column(name = "ALLOTCONFIRMDATE")
	private Date allotConfirmDate;	//分配时间
	
	@Column(name = "USEDCONFIRMDATE")
	private Date usedConfirmDate;	//使用时间
	
	@Column(name = "STATUS")
	private Integer status;			//状态
	
	@Column(name = "MAINTAINUID")
	private Integer maintainUID;	//操作人员 
	
	@Column(name = "MAINTAINDATE")
	private Date maintainDate;		//操作日期
	
	@Column(name = "MAINTAINTYPE")
	private String maintainType;	//操作类型 
	
	@Column(name = "SN")
	private String sn;				//SN号
	
	@Column(name = "ISM35")
	private Integer isM35;			//0：非M35,1：M35
	
	@Column(name = "RATE")
	private Double rate;			//费率
	
	@Column(name = "SECONDRATE")
	private Double secondRate;		//秒到手续费
	
	@Column(name = "SCANRATE")
	private Double scanRate;		//秒到手续费
	
	@Column(name = "REBATETYPE")
	private Integer rebateType;		//返利类型 1循环送；2返款； 空/0无返利
	
	@Column(name = "DEPOSITAMT")
	private Integer depositAmt;	//金额
	
	@Column(name = "SNTYPE")
	private Integer snType;	//三级分销设备类型：1小蓝牙，2大蓝牙
	
	@Column(name = "BATCHID")
	private String batchID; //导入批次号
	
	@Column(name = "MACHINEMODEL")
	private String machineModel; //机型名称
	
	@Column(name = "TERORDERID")
	private String terOrderID; //导出批次号
	
	@Column(name = "PDLID")
	private Integer pdlid; //发货单号	
	
	@Column(name = "STORAGE")
	private String storage;//库位
	
	@Column(name = "OUTDATE")
	private Date outDate;//出库时间

	public TerminalInfoModel() {
	}
	
	public TerminalInfoModel(Integer btID, String termID, String keyType) {
		super();
		this.btID = btID;
		this.termID = termID;
		this.keyType = keyType;
	}

	public Integer getBtID() {
		return btID;
	}

	public void setBtID(Integer btID) {
		this.btID = btID;
	}

	public String getUnno() {
		return unno;
	}

	public void setUnno(String unno) {
		this.unno = unno;
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