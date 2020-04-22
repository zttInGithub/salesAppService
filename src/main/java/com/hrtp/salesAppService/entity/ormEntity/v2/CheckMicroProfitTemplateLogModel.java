package com.hrtp.salesAppService.entity.ormEntity.v2;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 二代及以下代理分润模板记录
 * @author YQ
 */
@Entity
@Table(name = "CHECK_MICRO_PROFITTEMPLATE_LOG")
public class CheckMicroProfitTemplateLogModel {

	@Id
	@Column(name = "WID")
	@SequenceGenerator(name = "S_CHECK_MICRO_PROFITTEMPLATE_L", sequenceName = "S_CHECK_MICRO_PROFITTEMPLATE_L",allocationSize = 1)
	@GeneratedValue(generator = "S_CHECK_MICRO_PROFITTEMPLATE_L",strategy = GenerationType.SEQUENCE)
	private Integer wId;
	@Column(name = "APTID")
	private Integer aptId;
	@Column(name = "UNNO")
	private String unno;
	@Column(name = "TEMPNAME")
	private String tempName;
	@Column(name = "MERCHANTTYPE")
	private Integer merchantType  ;		 //1理财；2秒到0.72；3非0.72秒到
	@Column(name = "PROFITTYPE")
	private Integer profitType ;     	 // 分润方式  1：按金额分润  2：按笔分润
	@Column(name = "PROFITRULE")
	private Integer  profitRule ;    	 // 分润规则   1：单笔交易金额 2：其他
	@Column(name = "STARTAMOUNT")
	private BigDecimal startAmount;   		 // 借记卡手续费
	@Column(name = "ENDAMOUNT")
	private BigDecimal endAmount  ; 		 // 借记卡封顶值
	@Column(name = "RULETHRESHOLD")
	private BigDecimal ruleThreshold ;   	//  借记卡费率
	@Column(name = "CREDITBANKRATE")
	private BigDecimal creditBankRate;   	 // 贷记卡分润成本
	@Column(name = "CASHRATE")
	private BigDecimal cashRate;   	         // T0提现费率
	@Column(name = "CLOUDQUICKRATE")
	private BigDecimal cloudQuickRate;   	 //云闪付费率
	@Column(name = "CASHAMT")
	private BigDecimal cashAmt;   	         // 转账费
	@Column(name = "SCANRATE")
	private BigDecimal scanRate;   	         // 微信费率
	@Column(name = "SCANRATE1")
	private BigDecimal scanRate1;   	     // 支付宝费率
	@Column(name = "SCANRATE2")
	private BigDecimal scanRate2;   	     // 银联二维码费率
	@Column(name = "CASHAMT1")
	private BigDecimal cashAmt1;   	         // 扫码转账费
	@Column(name = "CASHAMT2")
	private BigDecimal cashAmt2;   	         // 快捷转账费
	@Column(name = "SUBRATE")
	private BigDecimal subRate;				//代还费率
	@Column(name = "PROFITPERCENT")
	private BigDecimal profitPercent ;  	//利润百分比
	@Column(name = "PROFITPERCENT1")
	private BigDecimal profitPercent1 ;  	//快捷分润比例
	@Column(name = "MATAINTYPE")
	private String matainType  ;    	//操作类型    A ：添加  M ：修改 D :删除
	@Column(name = "MATAINDATE")
	private Date matainDate  ;    	//生效日期
	@Column(name = "MATAINUSERID")
	private Integer matainUserId;
	@Column(name = "SETTMETHOD")
	private String settMethod;		//模板类型   000000：普通手刷模板  100000：秒到模板
	@Column(name = "STARTDATE")
	private Date startdate  ;
	@Column(name = "ENDDATE")
	private Date enddate  ;
	@Column(name = "REMARK")
	private String remark;
	@Column(name = "STATUS")
	private Integer  status ;
	//秒到扫码拆分
	@Column(name = "CASHAMTABOVE")
	private BigDecimal CASHAMTABOVE;    //扫码1000以上转账费;		
	@Column(name = "CASHAMTUNDER")
	private BigDecimal CASHAMTUNDER;    //扫码1000以下转账费;
	@Column(name = "HUABEIRATE")
	private BigDecimal HUABEIRATE;    //支付宝花呗费率;		
	@Column(name = "HUABEIFEE")
	private BigDecimal HUABEIFEE;    //支付宝花呗转账费;

	public BigDecimal getHUABEIRATE() {
		return HUABEIRATE;
	}
	public void setHUABEIRATE(BigDecimal hUABEIRATE) {
		HUABEIRATE = hUABEIRATE;
	}
	public BigDecimal getHUABEIFEE() {
		return HUABEIFEE;
	}
	public void setHUABEIFEE(BigDecimal hUABEIFEE) {
		HUABEIFEE = hUABEIFEE;
	}
	public BigDecimal getCASHAMTABOVE() {
		return CASHAMTABOVE;
	}
	public void setCASHAMTABOVE(BigDecimal cASHAMTABOVE) {
		CASHAMTABOVE = cASHAMTABOVE;
	}
	public BigDecimal getCASHAMTUNDER() {
		return CASHAMTUNDER;
	}
	public void setCASHAMTUNDER(BigDecimal cASHAMTUNDER) {
		CASHAMTUNDER = cASHAMTUNDER;
	}
	public Integer getwId() {
		return wId;
	}
	public void setwId(Integer wId) {
		this.wId = wId;
	}
	public Integer getAptId() {
		return aptId;
	}
	public void setAptId(Integer aptId) {
		this.aptId = aptId;
	}
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	public String getTempName() {
		return tempName;
	}
	public void setTempName(String tempName) {
		this.tempName = tempName;
	}
	public Integer getMerchantType() {
		return merchantType;
	}
	public void setMerchantType(Integer merchantType) {
		this.merchantType = merchantType;
	}
	public Integer getProfitType() {
		return profitType;
	}
	public void setProfitType(Integer profitType) {
		this.profitType = profitType;
	}
	public Integer getProfitRule() {
		return profitRule;
	}
	public void setProfitRule(Integer profitRule) {
		this.profitRule = profitRule;
	}
	public BigDecimal getStartAmount() {
		return startAmount;
	}
	public void setStartAmount(BigDecimal startAmount) {
		this.startAmount = startAmount;
	}
	public BigDecimal getEndAmount() {
		return endAmount;
	}
	public void setEndAmount(BigDecimal endAmount) {
		this.endAmount = endAmount;
	}
	public BigDecimal getRuleThreshold() {
		return ruleThreshold;
	}
	public void setRuleThreshold(BigDecimal ruleThreshold) {
		this.ruleThreshold = ruleThreshold;
	}
	public BigDecimal getCreditBankRate() {
		return creditBankRate;
	}
	public void setCreditBankRate(BigDecimal creditBankRate) {
		this.creditBankRate = creditBankRate;
	}
	public BigDecimal getCashRate() {
		return cashRate;
	}
	public void setCashRate(BigDecimal cashRate) {
		this.cashRate = cashRate;
	}
	public BigDecimal getCloudQuickRate() {
		return cloudQuickRate;
	}
	public void setCloudQuickRate(BigDecimal cloudQuickRate) {
		this.cloudQuickRate = cloudQuickRate;
	}
	public BigDecimal getCashAmt() {
		return cashAmt;
	}
	public void setCashAmt(BigDecimal cashAmt) {
		this.cashAmt = cashAmt;
	}
	public BigDecimal getScanRate() {
		return scanRate;
	}
	public void setScanRate(BigDecimal scanRate) {
		this.scanRate = scanRate;
	}
	public BigDecimal getScanRate1() {
		return scanRate1;
	}
	public void setScanRate1(BigDecimal scanRate1) {
		this.scanRate1 = scanRate1;
	}
	public BigDecimal getScanRate2() {
		return scanRate2;
	}
	public void setScanRate2(BigDecimal scanRate2) {
		this.scanRate2 = scanRate2;
	}
	public BigDecimal getCashAmt1() {
		return cashAmt1;
	}
	public void setCashAmt1(BigDecimal cashAmt1) {
		this.cashAmt1 = cashAmt1;
	}
	public BigDecimal getCashAmt2() {
		return cashAmt2;
	}
	public void setCashAmt2(BigDecimal cashAmt2) {
		this.cashAmt2 = cashAmt2;
	}
	public BigDecimal getSubRate() {
		return subRate;
	}
	public void setSubRate(BigDecimal subRate) {
		this.subRate = subRate;
	}
	public BigDecimal getProfitPercent() {
		return profitPercent;
	}
	public void setProfitPercent(BigDecimal profitPercent) {
		this.profitPercent = profitPercent;
	}
	public BigDecimal getProfitPercent1() {
		return profitPercent1;
	}
	public void setProfitPercent1(BigDecimal profitPercent1) {
		this.profitPercent1 = profitPercent1;
	}
	public String getMatainType() {
		return matainType;
	}
	public void setMatainType(String matainType) {
		this.matainType = matainType;
	}
	public Date getMatainDate() {
		return matainDate;
	}
	public void setMatainDate(Date matainDate) {
		this.matainDate = matainDate;
	}
	public Integer getMatainUserId() {
		return matainUserId;
	}
	public void setMatainUserId(Integer matainUserId) {
		this.matainUserId = matainUserId;
	}
	public String getSettMethod() {
		return settMethod;
	}
	public void setSettMethod(String settMethod) {
		this.settMethod = settMethod;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}




}
