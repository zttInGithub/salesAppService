package com.hrtp.salesAppService.entity.ormEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * UnnoRepayModel
 * description TODO
 * create by lxj 2018/8/22
 **/
@Entity
@Table(name = "PG_UNNOREPAY_FENRUN")
public class UnnoRepayModel implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "URFRID")
    private Integer urfrId;
    @Column(name = "UNNO")
    private String unno;
    @Column(name = "TXNDAY")
    private String txnday;
    @Column(name = "TXNAMT")
    private Double enterAmt;
    @Column(name = "TXNCOUNT")
    private Integer txnCount;
    @Column(name = "TXNPROFIT")
    private Double txnBfee;
    @Column(name = "CDATE")
    private Date cdate;
    @Column(name = "CBY")
    private String cby;
    @Column(name = "REMARKS1")
    private String remarks1;
    @Column(name = "REMARKS2")
    private String remarks2;

    public Integer getUrfrId() {
        return urfrId;
    }

    public void setUrfrId(Integer urfrId) {
        this.urfrId = urfrId;
    }

    public String getUnno() {
        return unno;
    }

    public void setUnno(String unno) {
        this.unno = unno;
    }

    public Integer getTxnCount() {
        return txnCount;
    }

    public void setTxnCount(Integer txnCount) {
        this.txnCount = txnCount;
    }

    public String getTxnday() {
        return txnday;
    }

    public void setTxnday(String txnday) {
        this.txnday = txnday;
    }

    public Double getEnterAmt() {
        return enterAmt;
    }

    public void setEnterAmt(Double enterAmt) {
        this.enterAmt = enterAmt;
    }

    public Double getTxnBfee() {
        return txnBfee;
    }

    public void setTxnBfee(Double txnBfee) {
        this.txnBfee = txnBfee;
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
