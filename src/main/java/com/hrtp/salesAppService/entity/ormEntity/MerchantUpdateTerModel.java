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
@Table(name = "BILL_MERCHANTUPDATETER")
@SequenceGenerator(name = "S_BILL_MERCHANTUPDATETER1", sequenceName = "S_BILL_MERCHANTUPDATETER" ,allocationSize = 1)
public class MerchantUpdateTerModel implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "S_BILL_MERCHANTUPDATETER1",strategy = GenerationType.SEQUENCE)
    @Column(name = "BMUTID")
	private Integer bmutID;				//主键
	
	@Column(name = "UNNO")
	private String unno;				//机构号
	
	@Column(name = "MID")
	private String mid;					//商户号
	
	@Column(name = "TYPE")
	private String type;				//类型1-换机申请(审核) 2-撤机申请 3-关闭商户
	
	@Column(name = "TID")
	private String tid;					//终端号
	
	@Column(name = "SN")
	private String sn;					//sn号
	
	@Column(name = "SNIMG")
	private String snImg;		//sn照片
	
	@Column(name = "MAINTAINTYPE")
	private String maintainType;		//操作类型

	@Column(name = "MAINTAINDATE")
	private Date maintainDate;			//操作日期
	
	@Column(name = "APPROVETYPE")
	private String approveType;			//审核类型
	
	@Column(name = "APPROVEDATE")
	private Date approveDate;			//审核日期

	public Integer getBmutID() {
		return bmutID;
	}

	public void setBmutID(Integer bmutID) {
		this.bmutID = bmutID;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getSnImg() {
		return snImg;
	}

	public void setSnImg(String snImg) {
		this.snImg = snImg;
	}

	public String getMaintainType() {
		return maintainType;
	}

	public void setMaintainType(String maintainType) {
		this.maintainType = maintainType;
	}

	public Date getMaintainDate() {
		return maintainDate;
	}

	public void setMaintainDate(Date maintainDate) {
		this.maintainDate = maintainDate;
	}

	public String getApproveType() {
		return approveType;
	}

	public void setApproveType(String approveType) {
		this.approveType = approveType;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
}