package com.hrtp.salesAppService.entity.ormEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * UnnoCashBackModel
 * description TODO
 * create by lxj 2018/8/23
 **/
@Entity
@Table(name = "PG_UNNOCASHBACK_SUM")
public class UnnoCashBackModel implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "UCBSID")
    private Integer ucbsId;
    @Column(name = "UNNO")
    private String unno;
    @Column(name = "CASHBACKDAY")
    private String cashBackDay;
    @Column(name = "CASHBACKAMT")
    private Double cashBackAmt;
    @Column(name = "CDATE")
    private Date cdate;
    @Column(name = "CBY")
    private String cby;
    @Column(name = "REMARKS1")
    private String remarks1;
    @Column(name = "REMARKS2")
    private String remarks2;

    public Integer getUcbsId() {
        return ucbsId;
    }

    public void setUcbsId(Integer ucbsId) {
        this.ucbsId = ucbsId;
    }

    public String getUnno() {
        return unno;
    }

    public void setUnno(String unno) {
        this.unno = unno;
    }

    public String getCashBackDay() {
        return cashBackDay;
    }

    public void setCashBackDay(String cashBackDay) {
        this.cashBackDay = cashBackDay;
    }

    public Double getCashBackAmt() {
        return cashBackAmt;
    }

    public void setCashBackAmt(Double cashBackAmt) {
        this.cashBackAmt = cashBackAmt;
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
