package com.hrtp.salesAppService.entity.ormEntity.v2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * HrtUnnoCostModel
 * <p>
 * 运营中心-一代成本表
 *
 * @author xuegangliu 2019/07/09
 * @since 1.8
 **/
@Entity
@Table(name = "HRT_UNNO_COST")
public class HrtUnnoCostModel {
	private Integer hucid;
	private String unno;
	private Integer machineType;
	private Integer txnType;
	private Integer txnDetail;
	private BigDecimal debitRate;
	private BigDecimal debitFeeamt;
	private BigDecimal creditRate;
	private BigDecimal cashCost;
	private BigDecimal cashRate;
	private Integer status;
	private Integer flag;
	private Integer operateType;
	private Date cdate;
	private String cby;
	private Date lmDate;
	private String lmby;
	private Integer buid;
	private BigDecimal wx_uprate;
	private BigDecimal wx_upcash;
	private BigDecimal wx_uprate1;
	private BigDecimal wx_upcash1;
	private BigDecimal zfb_rate;
	private BigDecimal zfb_cash;
	private BigDecimal ewm_rate;
	private BigDecimal ewm_cash;
	private BigDecimal ysf_rate;
	private BigDecimal hb_cash;
	private BigDecimal hb_rate;

	@Id
	@Column(name = "HUCID")
	@SequenceGenerator(name = "S_HRT_UNNO_COST", sequenceName = "S_HRT_UNNO_COST",allocationSize = 1)
	@GeneratedValue(generator = "S_HRT_UNNO_COST",strategy = GenerationType.SEQUENCE)
	public Integer getHucid() {
		return hucid;
	}

	public void setHucid(Integer hucid) {
		this.hucid = hucid;
	}

	@Column(name = "UNNO")
	public String getUnno() {
		return unno;
	}

	public void setUnno(String unno) {
		this.unno = unno;
	}

	@Column(name = "MACHINE_TYPE")
	public Integer getMachineType() {
		return machineType;
	}

	public void setMachineType(Integer machineType) {
		this.machineType = machineType;
	}

	@Column(name = "TXN_TYPE")
	public Integer getTxnType() {
		return txnType;
	}

	public void setTxnType(Integer txnType) {
		this.txnType = txnType;
	}

	@Column(name = "DEBIT_RATE")
	public BigDecimal getDebitRate() {
		return debitRate;
	}

	public void setDebitRate(BigDecimal debitRate) {
		this.debitRate = debitRate;
	}

	@Column(name = "DEBIT_FEEAMT")
	public BigDecimal getDebitFeeamt() {
		return debitFeeamt;
	}

	public void setDebitFeeamt(BigDecimal debitFeeamt) {
		this.debitFeeamt = debitFeeamt;
	}

	@Column(name = "CREDIT_RATE")
	public BigDecimal getCreditRate() {
		return creditRate;
	}

	public void setCreditRate(BigDecimal creditRate) {
		this.creditRate = creditRate;
	}

	@Column(name = "CASH_COST")
	public BigDecimal getCashCost() {
		return cashCost;
	}

	public void setCashCost(BigDecimal cashCost) {
		this.cashCost = cashCost;
	}

	@Column(name = "CASH_RATE")
	public BigDecimal getCashRate() {
		return cashRate;
	}

	public void setCashRate(BigDecimal cashRate) {
		this.cashRate = cashRate;
	}

	@Column(name = "STATUS")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "FLAG")
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	@Column(name = "OPERATE_TYPE")
	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	@Column(name = "CDATE")
	public Date getCdate() {
		return cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	@Column(name = "CBY")
	public String getCby() {
		return cby;
	}

	public void setCby(String cby) {
		this.cby = cby;
	}

	@Column(name = "LMDATE")
	public Date getLmDate() {
		return lmDate;
	}

	public void setLmDate(Date lmDate) {
		this.lmDate = lmDate;
	}

	@Column(name = "LMBY")
	public String getLmby() {
		return lmby;
	}

	public void setLmby(String lmby) {
		this.lmby = lmby;
	}

	@Column(name = "BUID")
	public Integer getBuid() {
		return buid;
	}

	public void setBuid(Integer buid) {
		this.buid = buid;
	}

	@Column(name = "TXN_DETAIL")
	public Integer getTxnDetail() {
		return txnDetail;
	}

	public void setTxnDetail(Integer txnDetail) {
		this.txnDetail = txnDetail;
	}
	@Column(name = "WX_UPRATE")
	public BigDecimal getWx_uprate() {
		return wx_uprate;
	}

	public void setWx_uprate(BigDecimal wx_uprate) {
		this.wx_uprate = wx_uprate;
	}
	@Column(name = "WX_UPCASH")
	public BigDecimal getWx_upcash() {
		return wx_upcash;
	}

	public void setWx_upcash(BigDecimal wx_upcash) {
		this.wx_upcash = wx_upcash;
	}
	@Column(name = "WX_UPRATE1")
	public BigDecimal getWx_uprate1() {
		return wx_uprate1;
	}

	public void setWx_uprate1(BigDecimal wx_uprate1) {
		this.wx_uprate1 = wx_uprate1;
	}
	@Column(name = "WX_UPCASH1")
	public BigDecimal getWx_upcash1() {
		return wx_upcash1;
	}

	public void setWx_upcash1(BigDecimal wx_upcash1) {
		this.wx_upcash1 = wx_upcash1;
	}
	@Column(name = "ZFB_RATE")
	public BigDecimal getZfb_rate() {
		return zfb_rate;
	}

	public void setZfb_rate(BigDecimal zfb_rate) {
		this.zfb_rate = zfb_rate;
	}
	@Column(name = "ZFB_CASH")
	public BigDecimal getZfb_cash() {
		return zfb_cash;
	}

	public void setZfb_cash(BigDecimal zfb_cash) {
		this.zfb_cash = zfb_cash;
	}
	@Column(name = "EWM_RATE")
	public BigDecimal getEwm_rate() {
		return ewm_rate;
	}

	public void setEwm_rate(BigDecimal ewm_rate) {
		this.ewm_rate = ewm_rate;
	}
	@Column(name = "EWM_CASH")
	public BigDecimal getEwm_cash() {
		return ewm_cash;
	}

	public void setEwm_cash(BigDecimal ewm_cash) {
		this.ewm_cash = ewm_cash;
	}
	@Column(name = "YSF_RATE")
	public BigDecimal getYsf_rate() {
		return ysf_rate;
	}

	public void setYsf_rate(BigDecimal ysf_rate) {
		this.ysf_rate = ysf_rate;
	}

	@Column(name = "HB_RATE")
	public BigDecimal getHb_rate() {
		return hb_rate;
	}

	public void setHb_rate(BigDecimal hb_rate) {
		this.hb_rate = hb_rate;
	}
	@Column(name = "HB_CASH")
	public BigDecimal getHb_cash() {
		return hb_cash;
	}

	public void setHb_cash(BigDecimal hb_cash) {
		this.hb_cash = hb_cash;
	}

}
