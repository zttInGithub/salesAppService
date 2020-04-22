package com.hrtp.salesAppService.entity.ormEntity.v2;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * CheckProfittemplateRecordModel
 * <p>
 * 分润模板记录表
 * </p>
 *
 * @author xuegangliu 2019/07/10
 * @since 1.8
 **/
@Entity
@Table(name="CHECK_PROFITTEMPLATE_RECORD")
public class CheckProfittemplateRecordModel implements Serializable {
//    CPRID	N	NUMBER(6)	N			主键
//    UNNO	N	CHAR(6)	Y			机构号
//    REBATETYPE	N	INTEGER	Y			活动类型
//    PROFITPERCENT	N	NUMBER(18,6)	Y			分润比例
//    STATUS	N	INTEGER	Y			状态
//    TEMPNAME	N	VARCHAR2(100)	Y			模板名称
//    RATE1	N	NUMBER(18,6)	Y			扫码费率
//    CASH1	N	NUMBER(18,6)	Y			扫码提现
//    CREATE_USER	N	VARCHAR2(100)	Y			创建人
//    CREATE_DATE	N	DATE	Y			创建时间
//    START_DATE	N	DATE	Y			模板生效开始时间
//    END_DATE	N	DATE	Y			模板生效结束时间
//    RATE2	N	NUMBER(18,6)	Y			刷卡费率
//    CASH2	N	NUMBER(18,6)	Y			刷卡提现
//    ACT_CASH	N	NUMBER(18,6)	Y			激活返现
//    TXN_CASH	N	NUMBER(18,6)	Y			交易返现
//    CASH_STATUS	N	INTEGER	Y			提现开关
//    REMARK	N	VARCHR2(4000)	Y			备注

    @Id
    @SequenceGenerator(name = "s_check_profittemplate_record", sequenceName = "s_check_profittemplate_record",allocationSize = 1)
    @GeneratedValue(generator = "s_check_profittemplate_record",strategy = GenerationType.SEQUENCE)
    @Column(name="CPRID")
    private Integer cprId;

    @Column(name="UNNO")
    private String unno;

    @Column(name="REBATETYPE")
    private Integer rebateType;

    @Column(name="PROFITPERCENT")
    private BigDecimal profitPercent;

    @Column(name="STATUS")
    private Integer status;

    @Column(name="TEMPNAME")
    private String tempname;

    @Column(name="RATE1")
    private BigDecimal rate1;

    @Column(name="CASH1")
    private BigDecimal cash1;

    @Column(name="CREATE_USER")
    private String createUser;

    @Column(name="CREATE_DATE")
    private Date createDate;

    @Column(name="START_DATE")
    private Date startDate;

    @Column(name="END_DATE")
    private Date endDate;

    @Column(name="RATE2")
    private BigDecimal rate2;

    @Column(name="CASH2")
    private BigDecimal cash2;

    @Column(name="ACT_CASH")
    private BigDecimal actCash;

    @Column(name="TXN_CASH")
    private BigDecimal txnCash;

    @Column(name="CASH_STATUS")
    private Integer cashStatus;

    @Column(name="REMARK")
    private String remark;

    @Column(name="BANKRATE")
    private BigDecimal bankRate;

    @Column(name="CLOUDRATE")
    private BigDecimal cloudRate;

    @Column(name="REPAYRATE")
    private BigDecimal repayRate;

    @Column(name="CASHRATIO")
    private BigDecimal cashRatio;

    @Column(name="CASHSWITCH")
    private Integer cashSwitch;

    @Column(name = "AGENTID")
    private String agentId;

    @Column(name = "TYPE")
    private Integer type;

    public CheckProfittemplateRecordModel() {
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCprId() {
        return cprId;
    }

    public void setCprId(Integer cprId) {
        this.cprId = cprId;
    }

    public String getUnno() {
        return unno;
    }

    public void setUnno(String unno) {
        this.unno = unno;
    }

    public Integer getRebateType() {
        return rebateType;
    }

    public void setRebateType(Integer rebateType) {
        this.rebateType = rebateType;
    }

    public BigDecimal getProfitPercent() {
        return profitPercent;
    }

    public void setProfitPercent(BigDecimal profitPercent) {
        this.profitPercent = profitPercent;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTempname() {
        return tempname;
    }

    public void setTempname(String tempname) {
        this.tempname = tempname;
    }

    public BigDecimal getRate1() {
        return rate1;
    }

    public void setRate1(BigDecimal rate1) {
        this.rate1 = rate1;
    }

    public BigDecimal getCash1() {
        return cash1;
    }

    public void setCash1(BigDecimal cash1) {
        this.cash1 = cash1;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getRate2() {
        return rate2;
    }

    public void setRate2(BigDecimal rate2) {
        this.rate2 = rate2;
    }

    public BigDecimal getCash2() {
        return cash2;
    }

    public void setCash2(BigDecimal cash2) {
        this.cash2 = cash2;
    }

    public BigDecimal getActCash() {
        return actCash;
    }

    public void setActCash(BigDecimal actCash) {
        this.actCash = actCash;
    }

    public BigDecimal getTxnCash() {
        return txnCash;
    }

    public void setTxnCash(BigDecimal txnCash) {
        this.txnCash = txnCash;
    }

    public Integer getCashStatus() {
        return cashStatus;
    }

    public void setCashStatus(Integer cashStatus) {
        this.cashStatus = cashStatus;
    }
}
