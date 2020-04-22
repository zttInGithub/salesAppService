package com.hrtp.salesAppService.entity.ormEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CHECK_REALTXN")
public class CheckRealtxnModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
    @Column(name = "PKID")
	private Integer pkid;
	@Column(name = "UNNO")
	private String unno;	//机构编号
	@Column(name = "UNNAME")
	private String unName;				//机构名称
	@Column(name = "ISMPOS")
	private Integer isMpos;				//商户类别:0-传统POS,1-MPOS
	@Column(name = "MID")
	private String mid;					//商户编号
	@Column(name = "RNAME")
	private String rname;				//商户名称
	@Column(name = "TID")
	private String tid;					//终端编号
	@Column(name = "INSTALLEDNAME")
	private String installedName;		//门店名称
	@Column(name = "CARDPAN")
	private String cardPan;				//卡号
	@Column(name = "TXNAMOUNT")
	private Double txnAmount;			//交易金额
	@Column(name = "TXNDAY")
	private String txnDay;				//交易日期
	@Column(name = "TXNTIME")
	private String txnTime;				//交易时间
	@Column(name = "TXNTYPE")
	private Integer txnType;			//交易方式:0-银行卡、1-微信、2-支付宝、3-银联二维码、8-快捷支付、5-手机Pay、10-信用卡还款
	@Column(name = "AUTHCODE")
	private String authCode;			//授权码
	@Column(name = "ORDERID")
	private String orderID;				//流水号
	@Column(name = "RRN")
	private String rrn;					//检索参考号
	@Column(name = "TXNSTATE")
	private Integer txnState;			//处理状态：0-成功,1-失败,2-待付款
	@Column(name = "CARDTYPE")
	private Integer cardType;			//卡类型:1-借记卡,2-贷记卡
	@Column(name = "BUSID")
	private Integer busid;				//业务员ID
	@Column(name = "SALENAME")
	private String saleName;			//业务员姓名
	@Column(name = "MTI")
	private Integer mti;				//交易类型:0-消费,1-预授权,2-预授权撤销，3-消费撤销，4-冲正，5-退款，6-退货
	
	public Integer getPkid() {
		return pkid;
	}
	public void setPkid(Integer pkid) {
		this.pkid = pkid;
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
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getInstalledName() {
		return installedName;
	}
	public void setInstalledName(String installedName) {
		this.installedName = installedName;
	}
	public String getCardPan() {
		return cardPan;
	}
	public void setCardPan(String cardPan) {
		this.cardPan = cardPan;
	}
	public Double getTxnAmount() {
		return txnAmount;
	}
	public void setTxnAmount(Double txnAmount) {
		this.txnAmount = txnAmount;
	}
	public String getTxnDay() {
		return txnDay;
	}
	public void setTxnDay(String txnDay) {
		this.txnDay = txnDay;
	}
	public String getTxnTime() {
		return txnTime;
	}
	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}
	public String getUnName() {
		return unName;
	}
	public void setUnName(String unName) {
		this.unName = unName;
	}
	public Integer getIsMpos() {
		return isMpos;
	}
	public void setIsMpos(Integer isMpos) {
		this.isMpos = isMpos;
	}
	public Integer getTxnType() {
		return txnType;
	}
	public void setTxnType(Integer txnType) {
		this.txnType = txnType;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getRrn() {
		return rrn;
	}
	public void setRrn(String rrn) {
		this.rrn = rrn;
	}
	public Integer getTxnState() {
		return txnState;
	}
	public void setTxnState(Integer txnState) {
		this.txnState = txnState;
	}
	public Integer getCardType() {
		return cardType;
	}
	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}
	public Integer getBusid() {
		return busid;
	}
	public void setBusid(Integer busid) {
		this.busid = busid;
	}
	public String getSaleName() {
		return saleName;
	}
	public Integer getMti() {
		return mti;
	}
	public void setMti(Integer mti) {
		this.mti = mti;
	}
}
