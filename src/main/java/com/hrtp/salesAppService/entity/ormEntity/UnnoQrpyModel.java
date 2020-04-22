package com.hrtp.salesAppService.entity.ormEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * UnnoQrpyModel
 * description TODO
 * create by lxj 2018/8/22
 **/
@Entity
@Table(name = "PG_UNNOQPAY_FENRUN")
public class UnnoQrpyModel implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "UQPFRID")
    private Integer uqpFrid;
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
    private Double casheProfit;
    @Column(name = "CDATE")
    private Date cdate;
    @Column(name = "CBY")
    private String cby;
    @Column(name = "REMARKS1")
    private String remarks1;
    @Column(name = "REMARKS2")
    private String remarks2;

    public UnnoQrpyModel(){}
    public UnnoQrpyModel(Double txnAmt,Double txnProfit,Double casheProfit,Integer txnType){
        this.txnAmt = txnAmt;
        this.txnProfit = txnProfit;
        this.casheProfit = casheProfit;
        this.txnType = txnType;
    }

    public Integer getUqpFrid() {
        return uqpFrid;
    }

    public void setUqpFrid(Integer uqpFrid) {
        this.uqpFrid = uqpFrid;
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

    public Double getCasheProfit() {
        return casheProfit;
    }

    public void setCasheProfit(Double casheProfit) {
        this.casheProfit = casheProfit;
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
