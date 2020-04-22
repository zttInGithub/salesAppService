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
@Table(name = "BILL_MERCHANTTWOUPLOAD")
public class MerchantTwoUploadModel implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="mseq")
	@SequenceGenerator(name="mseq",sequenceName="S_BILL_MERCHANTTWOUPLOAD",allocationSize=1)
	@Column(name = "BMTUID")
	private Integer bmtuid;
	
	@Column(name = "MID")
	private String mid;
	
	@Column(name = "LEGALNAME")
	private String legalName;
	
	@Column(name = "LEGALNAME2")
	private String legalName2;
	
	@Column(name = "BUPLOAD")
	private String bupLoad;
	
	@Column(name = "BIGDEALUPLOAD")
	private String bigdealUpLoad;
	
	@Column(name = "LABORCONTRACTIMG")
	private String laborContractImg;
	
	@Column(name = "MAINTAINDATE")
	private Date maintaindate;
	
	@Column(name = "MAINTAINTYPE")
	private String maintaintype;
	
	@Column(name = "APPROVEDATE")
	private Date approveDate;
	
	@Column(name = "APPROVESTATUS")
	private String approveStatus;
	
	@Column(name = "APPROVENOTE")
	private String approveNote;

	public Integer getBmtuid() {
		return bmtuid;
	}

	public void setBmtuid(Integer bmtuid) {
		this.bmtuid = bmtuid;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getLegalName2() {
		return legalName2;
	}

	public void setLegalName2(String legalName2) {
		this.legalName2 = legalName2;
	}

	public String getBupLoad() {
		return bupLoad;
	}

	public void setBupLoad(String bupLoad) {
		this.bupLoad = bupLoad;
	}

	public String getBigdealUpLoad() {
		return bigdealUpLoad;
	}

	public void setBigdealUpLoad(String bigdealUpLoad) {
		this.bigdealUpLoad = bigdealUpLoad;
	}

	public String getLaborContractImg() {
		return laborContractImg;
	}

	public void setLaborContractImg(String laborContractImg) {
		this.laborContractImg = laborContractImg;
	}

	public Date getMaintaindate() {
		return maintaindate;
	}

	public void setMaintaindate(Date maintaindate) {
		this.maintaindate = maintaindate;
	}

	public String getMaintaintype() {
		return maintaintype;
	}

	public void setMaintaintype(String maintaintype) {
		this.maintaintype = maintaintype;
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

	public String getApproveNote() {
		return approveNote;
	}

	public void setApproveNote(String approveNote) {
		this.approveNote = approveNote;
	}
}