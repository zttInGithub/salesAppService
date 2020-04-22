package com.hrtp.salesAppService.entity.ormEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * UnnoScanModel
 * description TODO
 * create by lxj 2018/8/22
 **/
@Entity
@Table(name = "PG_UNNOSCAN_FENRUN")
public class UnnoScanModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "USFRID")
    private Integer usfrId;
    @Column(name = "TXNTYPE")
    private Integer txnType;
    @Column(name = "UNNO")
    private String unno;
    @Column(name = "TXNDAY")
    private String txnDay;
    @Column(name = "TXNAMT")
    private Double txnAmt;
    @Column(name = "TXNCOUNT")
    private Integer txnCount;
    @Column(name = "TXNPROFIT")
    private Double txnProfit;
    @Column(name = "CASHPROFIT")
    private Double cashProfit;
    @Column(name = "CDATE")
    private Date cdate;
    @Column(name = "CBY")
    private String cby;
    @Column(name = "REMARKS1")
    private String remarks1;
    @Column(name = "REMARKS2")
    private String remarks2;

    public UnnoScanModel() {
    }

    public UnnoScanModel(Double txnAmt, Double txnProfit, Double cashProfit, Integer txnType) {
        this.txnAmt = txnAmt;
        this.txnProfit = txnProfit;
        this.cashProfit = cashProfit;
        this.txnType = txnType;
    }

    public Integer getUsfrId() {
        return usfrId;
    }

    public void setUsfrId(Integer usfrId) {
        this.usfrId = usfrId;
    }

    public Integer getTxnType() {
        return txnType;
    }

    public void setTxnType(Integer txnType) {
        this.txnType = txnType;
    }

    public String getUnno() {
        return unno;
    }

    public void setUnno(String unno) {
        this.unno = unno;
    }

    public String getTxnDay() {
        return txnDay;
    }

    public void setTxnDay(String txnDay) {
        this.txnDay = txnDay;
    }

    public Double getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(Double txnAmt) {
        this.txnAmt = txnAmt;
    }

    public Integer getTxnCount() {
        return txnCount;
    }

    public void setTxnCount(Integer txnCount) {
        this.txnCount = txnCount;
    }

    public Double getTxnProfit() {
        return txnProfit;
    }

    public void setTxnProfit(Double txnProfit) {
        this.txnProfit = txnProfit;
    }

    public Double getCashProfit() {
        return cashProfit;
    }

    public void setCashProfit(Double cashProfit) {
        this.cashProfit = cashProfit;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public String getCby() {
        return cby;
    }

    public void setCby(String cby) {
        this.cby = cby;
    }

    public String getRemarks1() {
        return remarks1;
    }

    public void setRemarks1(String remarks1) {
        this.remarks1 = remarks1;
    }

    public String getRemarks2() {
        return remarks2;
    }

    public void setRemarks2(String remarks2) {
        this.remarks2 = remarks2;
    }
}
