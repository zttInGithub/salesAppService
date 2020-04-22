package com.hrtp.salesAppService.entity.ormEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * UnnoPosModel
 * description TODO
 * create by lxj 2018/8/22
 **/
@Entity
@Table(name = "PG_UNNOPOS_FENRUN")
public class UnnoPosModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "UPFRID")
    private Integer upfrId;
    @Column(name = "TXNTYPE")
    private Integer txnType;
    @Column(name = "PTYPE")
    private Integer pType;
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
    @Column(name = "CDATE")
    private Date cdate;
    @Column(name = "CBY")
    private String cby;
    @Column(name = "REMARKS1")
    private String remarks1;
    @Column(name = "REMARKS2")
    private String remarks2;
    @Column(name = "CASHPROFIT")
    private Double cashProfit;

    public UnnoPosModel() {
    }

    public UnnoPosModel(Double txnAmt,Double txnProfit,long txnCount,Integer pType){
        this.txnAmt = txnAmt;
        this.txnProfit = txnProfit;
        this.txnCount = Integer.parseInt(txnCount+"");
        this.pType = pType;
    }

    public UnnoPosModel(Double txnAmt,Long txnCount,Double cashProfit,Integer pType){
        this.txnAmt = txnAmt;
        this.txnCount = Integer.valueOf(txnCount+"");
        this.cashProfit = cashProfit;
        this.pType = pType;
    }

    public Integer getUpfrId() {
        return upfrId;
    }

    public void setUpfrId(Integer upfrId) {
        this.upfrId = upfrId;
    }

    public Integer getTxnType() {
        return txnType;
    }

    public void setTxnType(Integer txnType) {
        this.txnType = txnType;
    }

    public Integer getpType() {
        return pType;
    }

    public void setpType(Integer pType) {
        this.pType = pType;
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

    public Double getCashProfit() {
        return cashProfit;
    }

    public void setCashProfit(Double cashProfit) {
        this.cashProfit = cashProfit;
    }
}
