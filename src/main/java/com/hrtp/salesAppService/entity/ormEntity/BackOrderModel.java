package com.hrtp.salesAppService.entity.ormEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * BackOrderModel
 * description TODO
 * sl
 **/
@Entity
@Table(name = "CHECK_REORDER")
public class BackOrderModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ROID")
    private String roId;
    @Column(name = "ORIPKID")
    private String oripkid;
    @Column(name = "MID")
    private String mid;
    @Column(name = "TID")
    private String tid;
    @Column(name = "TXNDAY")
    private String txnday;
    @Column(name = "CARDPAN")
    private String cardPan;
    @Column(name = "SAMT")
    private Double samt;
    @Column(name = "RRN")
    private String rrn;
    @Column(name = "RAMT")
    private Double ramt;
    @Column(name = "REFUNDDATE")
    private String refundDate;
    @Column(name = "REFUNDTYPE")
    private Integer refundType;
    @Column(name = "REFUNDCODE")
    private String refundCode;
    @Column(name = "MINFO")
    private String minfo;
    @Column(name = "MINFO2")
    private String minfo2;
    @Column(name = "REORDERUPLOAD")
    private String reorDerupload;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "MAINTAINUID")
    private Integer maintainuId;
    @Column(name = "MAINTAINDATE")
    private Date maintainDate;
    @Column(name = "REMARKS")
    private String remarks;
    @Column(name = "REORDERUPLOAD1")
    private String reorDerupload1;
    @Column(name = "REORDERUPLOAD2")
    private String reorDerupload2;
    @Column(name = "REORDERUPLOAD3")
    private String reorDerupload3;
    @Column(name = "REORDERUPLOAD4")
    private String reorDerupload4;
    @Column(name = "REORDERUPLOAD5")
    private String reorDerupload5;
    @Column(name = "PROCESSCONTEXT")
    private String processContext;
    @Column(name = "COMMENTCONTEXT")
    private String commentContext;
    @Column(name = "PUSHSTATUS")
    private Integer pushStatus;
    

	public Integer getPushStatus() {
		return pushStatus;
	}


	public void setPushStatus(Integer pushStatus) {
		this.pushStatus = pushStatus;
	}


	public String getRoId() {
		return roId;
	}


	public void setRoId(String roId) {
		this.roId = roId;
	}


	public String getOripkid() {
		return oripkid;
	}


	public void setOripkid(String oripkid) {
		this.oripkid = oripkid;
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


	public String getTxnday() {
		return txnday;
	}


	public void setTxnday(String txnday) {
		this.txnday = txnday;
	}


	public String getCardPan() {
		return cardPan;
	}


	public void setCardPan(String cardPan) {
		this.cardPan = cardPan;
	}


	public Double getSamt() {
		return samt;
	}


	public void setSamt(Double samt) {
		this.samt = samt;
	}


	public String getRrn() {
		return rrn;
	}


	public void setRrn(String rrn) {
		this.rrn = rrn;
	}


	public Double getRamt() {
		return ramt;
	}


	public void setRamt(Double ramt) {
		this.ramt = ramt;
	}


	public String getRefundDate() {
		return refundDate;
	}


	public void setRefundDate(String refundDate) {
		this.refundDate = refundDate;
	}


	public Integer getRefundType() {
		return refundType;
	}


	public void setRefundType(Integer refundType) {
		this.refundType = refundType;
	}


	public String getRefundCode() {
		return refundCode;
	}


	public void setRefundCode(String refundCode) {
		this.refundCode = refundCode;
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


	public String getReorDerupload() {
		return reorDerupload;
	}


	public void setReorDerupload(String reorDerupload) {
		this.reorDerupload = reorDerupload;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
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


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public String getReorDerupload1() {
		return reorDerupload1;
	}


	public void setReorDerupload1(String reorDerupload1) {
		this.reorDerupload1 = reorDerupload1;
	}


	public String getReorDerupload2() {
		return reorDerupload2;
	}


	public void setReorDerupload2(String reorDerupload2) {
		this.reorDerupload2 = reorDerupload2;
	}


	public String getReorDerupload3() {
		return reorDerupload3;
	}


	public void setReorDerupload3(String reorDerupload3) {
		this.reorDerupload3 = reorDerupload3;
	}


	public String getReorDerupload4() {
		return reorDerupload4;
	}


	public void setReorDerupload4(String reorDerupload4) {
		this.reorDerupload4 = reorDerupload4;
	}


	public String getReorDerupload5() {
		return reorDerupload5;
	}


	public void setReorDerupload5(String reorDerupload5) {
		this.reorDerupload5 = reorDerupload5;
	}


	public String getProcessContext() {
		return processContext;
	}


	public void setProcessContext(String processContext) {
		this.processContext = processContext;
	}


	public String getCommentContext() {
		return commentContext;
	}


	public void setCommentContext(String commentContext) {
		this.commentContext = commentContext;
	}


	public BackOrderModel() {}

	
    public BackOrderModel(String roId,String reorDerupload,String reorDerupload1,String reorDerupload2,String commentContext,String processContext){
        this.roId = roId;
        this.reorDerupload = reorDerupload;
        this.reorDerupload1 = reorDerupload1;
        this.reorDerupload2 = reorDerupload2;
        this.commentContext = commentContext;
        this.processContext = processContext;
    }

}
