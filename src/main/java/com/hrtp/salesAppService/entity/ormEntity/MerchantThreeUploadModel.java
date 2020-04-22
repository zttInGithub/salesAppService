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

/**
 * MerchantThreeUploadModel
 * description TODO
 * sl
 **/
@Entity
@Table(name = "BILL_MERCHANTTHREEUPLOAD")
public class MerchantThreeUploadModel implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="mseq")
	@SequenceGenerator(name="mseq",sequenceName="S_BILL_MERCHANTTHREEUPLOAD",allocationSize=1)
	@Column(name = "BMTHID")
	private Integer bmthid;
	
	@Column(name = "MID")
	private String mid;
	
	@Column(name = "TID")
	private String tid;
	
	@Column(name = "SN")
	private String sn;
	
	@Column(name = "LICENSE_NAME")
	private String license_name;
	
	@Column(name = "R_NAME")
	private String r_name;
	
	@Column(name = "R_ADDR")
	private String r_addr;
	
	@Column(name = "ISCONNECT")
	private Integer isConnect;
	
	@Column(name = "ISIMMUNITY")
	private Integer isImmunity;
	
	@Column(name = "ISUNIONPAY")
	private Integer isUnionPay;
	
	@Column(name = "MERUPLOAD1")
	private String merUpload1;
	
	@Column(name = "MERUPLOAD2")
	private String merUpload2;
	
	@Column(name = "MERUPLOAD3")
	private String merUpload3;
	
	@Column(name = "MERUPLOAD4")
	private String merUpload4;
	
	@Column(name = "MERUPLOAD5")
	private String merUpload5;
	
	@Column(name = "MERUPLOAD6")
	private String merUpload6;
	
	@Column(name = "MERUPLOAD7")
	private String merUpload7;

	@Column(name = "MERUPLOAD8")
	private String merUpload8;
	
	@Column(name = "MAINTAINDATE")
	private Date maintainDate;
	
	@Column(name = "MAINTAINTYPE")
	private String maintainType;
	
	@Column(name = "APPROVEDATE")
	private Date approveDate;
	
	@Column(name = "APPROVESTATUS")
	private String approveStatus;
	
	@Column(name = "APPROVENOTE")
	private String approveNote;

	
	public Integer getBmthid() {
		return bmthid;
	}

	public void setBmthid(Integer bmthid) {
		this.bmthid = bmthid;
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

	public String getLicense_name() {
		return license_name;
	}

	public void setLicense_name(String license_name) {
		this.license_name = license_name;
	}

	public String getR_name() {
		return r_name;
	}

	public void setR_name(String r_name) {
		this.r_name = r_name;
	}

	public String getR_addr() {
		return r_addr;
	}

	public void setR_addr(String r_addr) {
		this.r_addr = r_addr;
	}

	public Integer getIsConnect() {
		return isConnect;
	}

	public void setIsConnect(Integer isConnect) {
		this.isConnect = isConnect;
	}

	public Integer getIsImmunity() {
		return isImmunity;
	}

	public void setIsImmunity(Integer isImmunity) {
		this.isImmunity = isImmunity;
	}

	public Integer getIsUnionPay() {
		return isUnionPay;
	}

	public void setIsUnionPay(Integer isUnionPay) {
		this.isUnionPay = isUnionPay;
	}

	public String getMerUpload1() {
		return merUpload1;
	}

	public void setMerUpload1(String merUpload1) {
		this.merUpload1 = merUpload1;
	}

	public String getMerUpload2() {
		return merUpload2;
	}

	public void setMerUpload2(String merUpload2) {
		this.merUpload2 = merUpload2;
	}

	public String getMerUpload3() {
		return merUpload3;
	}

	public void setMerUpload3(String merUpload3) {
		this.merUpload3 = merUpload3;
	}

	public String getMerUpload4() {
		return merUpload4;
	}

	public void setMerUpload4(String merUpload4) {
		this.merUpload4 = merUpload4;
	}

	public String getMerUpload5() {
		return merUpload5;
	}

	public void setMerUpload5(String merUpload5) {
		this.merUpload5 = merUpload5;
	}

	public String getMerUpload6() {
		return merUpload6;
	}

	public void setMerUpload6(String merUpload6) {
		this.merUpload6 = merUpload6;
	}

	public String getMerUpload7() {
		return merUpload7;
	}

	public void setMerUpload7(String merUpload7) {
		this.merUpload7 = merUpload7;
	}

	public String getMerUpload8() {
		return merUpload8;
	}

	public void setMerUpload8(String merUpload8) {
		this.merUpload8 = merUpload8;
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