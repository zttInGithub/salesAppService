package com.hrtp.salesAppService.entity.appEntity.v2;

import java.math.BigDecimal;

/**
 * PlusCost
 * <p>
 * 分润模板设置bean
 * </p>
 *
 * @author xuegangliu 2019/07/12
 * @since 1.8
 **/
public class PlusCost {
	//=======2.3版本新增============
	private BigDecimal cash;//刷卡
	private BigDecimal rate;
	private BigDecimal cash3;//微信1000以上（含）0.38%
	private BigDecimal rate3;
	private BigDecimal cash4;//微信1000以上（含）0.45%
	private BigDecimal rate4;
	private BigDecimal cash5;//支付宝
	private BigDecimal rate5;
	private BigDecimal cash6;//秒到扫码  2.6版本后变为扫码1000以下
	private BigDecimal rate6;
	private BigDecimal rate7;//银联二维码
	private BigDecimal cash8;//秒到扫码1000以上
	private BigDecimal rate8;
	private BigDecimal cash9;//支付宝花呗
	private BigDecimal rate9;

	// 扫码成本       2.3修改为手机pay费率
	private BigDecimal cash1;
	// 刷卡成本       2.3修改为微信1000以下提现 
	private BigDecimal cash2;
	private BigDecimal profitPercent;
	// 扫码提现       2.3修改为手机pay费率
	private BigDecimal rate1;
	// 刷卡提现	2.3修改为微信1000以下费率
	private BigDecimal rate2;
	// 银联二维码-秒到独有
	private BigDecimal bankRate;
	// 云闪付-秒到独有
	private BigDecimal cloudRate;
	// 代还费率-秒到与plus
	private BigDecimal repayRate;
	// 活动类型-plus独有
	private Integer rebateType;
	// 提现状态
	private Integer cashStatus;
	// 提现比例
	private BigDecimal cashRatio;
	// 提现开关
	private Integer cashSwitch;

	public BigDecimal getCashRatio() {
		return cashRatio;
	}

	public void setCashRatio(BigDecimal cashRatio) {
		this.cashRatio = cashRatio;
	}

	public Integer getCashSwitch() {
		return cashSwitch;
	}

	public void setCashSwitch(Integer cashSwitch) {
		this.cashSwitch = cashSwitch;
	}

	public BigDecimal getBankRate() {
		return bankRate;
	}

	public void setBankRate(BigDecimal bankRate) {
		this.bankRate = bankRate;
	}

	public BigDecimal getCloudRate() {
		return cloudRate;
	}

	public void setCloudRate(BigDecimal cloudRate) {
		this.cloudRate = cloudRate;
	}

	public BigDecimal getRepayRate() {
		return repayRate;
	}

	public void setRepayRate(BigDecimal repayRate) {
		this.repayRate = repayRate;
	}

	public BigDecimal getCash1() {
		return cash1;
	}

	public void setCash1(BigDecimal cash1) {
		this.cash1 = cash1;
	}

	public BigDecimal getCash2() {
		return cash2;
	}

	public void setCash2(BigDecimal cash2) {
		this.cash2 = cash2;
	}

	public BigDecimal getProfitPercent() {
		return profitPercent;
	}

	public void setProfitPercent(BigDecimal profitPercent) {
		this.profitPercent = profitPercent;
	}

	public BigDecimal getRate1() {
		return rate1;
	}

	public void setRate1(BigDecimal rate1) {
		this.rate1 = rate1;
	}

	public BigDecimal getRate2() {
		return rate2;
	}

	public void setRate2(BigDecimal rate2) {
		this.rate2 = rate2;
	}

	public Integer getRebateType() {
		return rebateType;
	}

	public void setRebateType(Integer rebateType) {
		this.rebateType = rebateType;
	}

	public Integer getCashStatus() {
		return cashStatus;
	}

	public void setCashStatus(Integer cashStatus) {
		this.cashStatus = cashStatus;
	}

	public BigDecimal getCash() {
		return cash;
	}

	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getCash3() {
		return cash3;
	}

	public void setCash3(BigDecimal cash3) {
		this.cash3 = cash3;
	}

	public BigDecimal getRate3() {
		return rate3;
	}

	public void setRate3(BigDecimal rate3) {
		this.rate3 = rate3;
	}

	public BigDecimal getCash4() {
		return cash4;
	}

	public void setCash4(BigDecimal cash4) {
		this.cash4 = cash4;
	}

	public BigDecimal getRate4() {
		return rate4;
	}

	public void setRate4(BigDecimal rate4) {
		this.rate4 = rate4;
	}

	public BigDecimal getCash5() {
		return cash5;
	}

	public void setCash5(BigDecimal cash5) {
		this.cash5 = cash5;
	}

	public BigDecimal getRate5() {
		return rate5;
	}

	public void setRate5(BigDecimal rate5) {
		this.rate5 = rate5;
	}

	public BigDecimal getCash6() {
		return cash6;
	}

	public void setCash6(BigDecimal cash6) {
		this.cash6 = cash6;
	}

	public BigDecimal getRate6() {
		return rate6;
	}

	public void setRate6(BigDecimal rate6) {
		this.rate6 = rate6;
	}

	public BigDecimal getRate7() {
		return rate7;
	}

	public void setRate7(BigDecimal rate7) {
		this.rate7 = rate7;
	}
	
	public BigDecimal getCash8() {
		return cash8;
	}
	
	public void setCash8(BigDecimal cash8) {
		this.cash8 = cash8;
	}
	
	public BigDecimal getRate8() {
		return rate8;
	}
	
	public void setRate8(BigDecimal rate8) {
		this.rate8 = rate8;
	}
	
	public BigDecimal getCash9() {
		return cash9;
	}
	
	public void setCash9(BigDecimal cash9) {
		this.cash9 = cash9;
	}
	
	public BigDecimal getRate9() {
		return rate9;
	}
	
	public void setRate9(BigDecimal rate9) {
		this.rate9 = rate9;
	}
	
}
