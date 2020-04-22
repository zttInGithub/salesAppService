package com.hrtp.salesAppService.entity.ormEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "BILL_MERCHANTTASKDETAIL2")
public class MerchantTaskDetail2Model  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "S_BILL_MERCHANTTASKDETAIL21", sequenceName = "S_BILL_MERCHANTTASKDETAIL2",allocationSize = 1)
	@GeneratedValue(generator = "S_BILL_MERCHANTTASKDETAIL21",strategy = GenerationType.SEQUENCE)
    @Column(name = "BT2ID")
	private Integer bt2id;							// 修改商户Banck信息唯一主键
	
	@Column(name = "BANKBRANCH")
	private String bankBranch;						//开户银行
	
	@Column(name = "BANKACCNO")
	private String bankAccNo;						//开户银行账号
	
	@Column(name = "BANKACCNAME")
	private String bankAccName;						//开户账号名称
	
	@Column(name = "BANKTYPE")
	private String bankType;						//开户银行是否为交通银行     1-交通银行  2-非交通银行
	
	@Column(name = "AREATYPE")
	private String areaType;						//开户银行所在地      1-北京  2-非北京
	
	@Column(name = "SETTLECYCLE")
	private Integer settleCycle;					//结算周期
	
	@Column(name = "BMTKID")
	private Integer bmtkid;							//所关联“待审核”主键
	
	@Column(name = "ACCUPLOAD")
	private String accUpLoad;						//开户证明上传文件名
	
	@Column(name = "PAYBANKID")
	private String payBankId;
	
	@Column(name = "AUTHUPLOAD")
	private String authUpLoad;
	
	@Column(name = "DUPLOAD")
	private String DUpLoad;
	
	@Column(name = "OPENUPLOAD")
	private String openUpLoad;
	
	@Column(name = "ACCTYPE")
	private String accType;
	
	
	public Integer getBt2id() {
		return bt2id;
	} 
	public void setBt2id(Integer bt2id) {
		this.bt2id = bt2id; 
	}
	public String getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	public String getBankAccNo() {
		return bankAccNo;
	}
	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}
	public String getBankAccName() {
		return bankAccName;
	}
	public void setBankAccName(String bankAccName) {
		this.bankAccName = bankAccName;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public String getAreaType() {
		return areaType;
	}
	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}
	public Integer getSettleCycle() {
		return settleCycle;
	}
	public void setSettleCycle(Integer settleCycle) {
		this.settleCycle = settleCycle;
	}
	public Integer getBmtkid() {
		return bmtkid;
	}
	public void setBmtkid(Integer bmtkid) {
		this.bmtkid = bmtkid;
	}
	public String getAccUpLoad() {
		return accUpLoad;
	}
	public void setAccUpLoad(String accUpLoad) {
		this.accUpLoad = accUpLoad;
	}
	public MerchantTaskDetail2Model() {
		super();
	}
	public String getPayBankId() {
		return payBankId;
	}
	public void setPayBankId(String payBankId) {
		this.payBankId = payBankId;
	}
	public String getAuthUpLoad() {
		return authUpLoad;
	}
	public void setAuthUpLoad(String authUpLoad) {
		this.authUpLoad = authUpLoad;
	}
	public String getDUpLoad() {
		return DUpLoad;
	}
	public void setDUpLoad(String dUpLoad) {
		DUpLoad = dUpLoad;
	}
	public String getOpenUpLoad() {
		return openUpLoad;
	}
	public void setOpenUpLoad(String openUpLoad) {
		this.openUpLoad = openUpLoad;
	}
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
	}
	
}
