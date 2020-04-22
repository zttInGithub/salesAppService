package com.hrtp.salesAppService.entity.ormEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * MistakeModel
 * description TODO
 * sl
 **/
@Entity
@Table(name = "CHECK_DISPATCHORDER")
public class MistakeModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "DPID")
    private String dpId;
    @Column(name = "INPORTDATE")
    private String inportDate;
    @Column(name = "MID")
    private String mid;
    @Column(name = "UNNO")
    private String unno;
    @Column(name = "TID")
    private String tid;
    @Column(name = "REASON")
    private String reason;
    @Column(name = "CARDNO")
    private String cardNo;
    @Column(name = "TRANSDATE")
    private String transDate;
    @Column(name = "FINALDATE")
    private String finalDate;
    @Column(name = "BANKREMARKS")
    private String bankRemarks;
    @Column(name = "RRN")
    private String rrn;
    @Column(name = "RNAME")
    private String rname;
    @Column(name = "RADDR")
    private String raddr;
    @Column(name = "CONTACTPHONE")
    private String contactPhone;
    @Column(name = "CONTACTPERSON")
    private String contactPerson;
    @Column(name = "MINFO")
    private String minfo;
    @Column(name = "MINFO2")
    private String minfo2;
    @Column(name = "ORDERUPLOAD")
    private String orderUpload;
    @Column(name = "ORDERTYPE")
    private String orderType;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "AGREMARKS")
    private String agRemarks;
    @Column(name = "MAINTAINUID")
    private Integer maintainuId;
    @Column(name = "MAINTAINDATE")
    private Date maintainDate;
    @Column(name = "SAMT")
    private Double samt;
    @Column(name = "PUSHSTATUS")
    private Integer pushStatus;
    

	public Integer getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(Integer pushStatus) {
		this.pushStatus = pushStatus;
	}

	public String getDpId() {
		return dpId;
	}

	public void setDpId(String dpId) {
		this.dpId = dpId;
	}

	public String getInportDate() {
		return inportDate;
	}

	public void setInportDate(String inportDate) {
		this.inportDate = inportDate;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(String finalDate) {
		this.finalDate = finalDate;
	}

	public String getBankRemarks() {
		return bankRemarks;
	}

	public void setBankRemarks(String bankRemarks) {
		this.bankRemarks = bankRemarks;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public String getRaddr() {
		return raddr;
	}

	public void setRaddr(String raddr) {
		this.raddr = raddr;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getMinfo() {
		return minfo;
	}

	public void setMinfo(String minfo) {
		this.minfo = minfo;
	}

	public String getMinfo2() {
		return minfo2;
	}

	public void setMinfo2(String minfo2) {
		this.minfo2 = minfo2;
	}

	public String getOrderUpload() {
		return orderUpload;
	}

	public void setOrderUpload(String orderUpload) {
		this.orderUpload = orderUpload;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUnno() {
		return unno;
	}

	public void setUnno(String unno) {
		this.unno = unno;
	}

	public String getAgRemarks() {
		return agRemarks;
	}

	public void setAgRemarks(String agRemarks) {
		this.agRemarks = agRemarks;
	}

	public Integer getMaintainuId() {
		return maintainuId;
	}

	public void setMaintainuId(Integer maintainuId) {
		this.maintainuId = maintainuId;
	}

	public Date getMaintainDate() {
		return maintainDate;
	}

	public void setMaintainDate(Date maintainDate) {
		this.maintainDate = maintainDate;
	}

	public Double getSamt() {
		return samt;
	}

	public void setSamt(Double samt) {
		this.samt = samt;
	}

	public MistakeModel() {}

	
    public MistakeModel(String dpId,String rname,String raddr,String contactPhone,String contactPerson,String agRemarks,String orderUpload){
        this.dpId = dpId;
        this.rname = rname;
        this.raddr = raddr;
        this.contactPhone = contactPhone;
        this.contactPerson = contactPerson;
        this.agRemarks = agRemarks;
        this.orderUpload=orderUpload;
    }

}
