package com.hrtp.salesAppService.entity.appEntity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 提现钱包信息
 * yq 2019/7
 */
public class WalletEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String unno;
	private Integer page;
	private Integer size;
	private String startDate;
	private String endDate;
	private BigDecimal cashAmt;//提现金额
	private BigDecimal feeAmt;//提现手续费
	private String unLvl;//机构级别
	private String agentName;//机构名称
	
	//2.5新增活动类型
	private String rebateType;
	private String upperUnit;
	private Integer flag;//区分收入明细和钱包开关标识

	public String getUpperUnit() {
		return upperUnit;
	}
	public void setUpperUnit(String upperUnit) {
		this.upperUnit = upperUnit;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getRebateType() {
		return rebateType;
	}
	public void setRebateType(String rebateType) {
		this.rebateType = rebateType;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getUnLvl() {
		return unLvl;
	}
	public void setUnLvl(String unLvl) {
		this.unLvl = unLvl;
	}
	public BigDecimal getFeeAmt() {
		return feeAmt;
	}
	public void setFeeAmt(BigDecimal feeAmt) {
		this.feeAmt = feeAmt;
	}
	public BigDecimal getCashAmt() {
		return cashAmt;
	}
	public void setCashAmt(BigDecimal cashAmt) {
		this.cashAmt = cashAmt;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	
	
	
}
