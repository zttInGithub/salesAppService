package com.hrtp.salesAppService.entity.ormEntity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "BILL_MERCHANTBANKCARDINFO")
public class MerchantBankCardModel implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "S_BILL_MERCHANTBANKCARDINFO1", sequenceName = "S_BILL_MERCHANTBANKCARDINFO",allocationSize = 1)
	@GeneratedValue(generator = "S_BILL_MERCHANTBANKCARDINFO1",strategy = GenerationType.SEQUENCE )
	@Column(name = "MBCID")
	private Integer mbcid;		//主键
	
	@Column(name = "MID")
	private String mid;			//商户编号
	
	@Column(name = "BANKACCNO")
	private String bankAccNo;	//银行账号
	
	@Column(name = "BANKACCNAME")
	private String bankAccName;	//银行账户名称
	
	@Column(name = "CREATEDATE")
	private Date createDate;	//创建日期
	
	@Column(name = "PAYBANKID")
	private String payBankId;	//支付系统行号
	
	@Column(name = "BANKBRANCH")
	private String bankBranch;	//银行名称
	
	@Column(name = "MATAINDATE")
	private Date matainDate;
	
	@Column(name = "STATUS")
	private Integer status;		//0：可用  1：停用
	
	@Column(name = "MERCARDTYPE")
	private Integer merCardType; //0:默认，不可删除 1：可删除
	
	public Integer getMbcid() {
		return mbcid;
	}
	public void setMbcid(Integer mbcid) {
		this.mbcid = mbcid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getMatainDate() {
		return matainDate;
	}
	public void setMatainDate(Date matainDate) {
		this.matainDate = matainDate;
	}
	public void setMerCardType(Integer merCardType) {
		this.merCardType = merCardType;
	}
	public Integer getMerCardType() {
		return merCardType;
	}
	public String getPayBankId() {
		return payBankId;
	}
	public void setPayBankId(String payBankId) {
		this.payBankId = payBankId;
	}
	public String getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	
}
