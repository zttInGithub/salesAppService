package com.hrtp.salesAppService.entity.ormEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * UnnoMerInfoModel
 * description TODO
 * create by lxj 2018/8/23
 **/
@Entity
@Table(name = "PG_MERCHINFO_SUM")
public class UnnoMerInfoModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "MISID")
    private Integer misId;
    @Column(name = "UNNO")
    private String unno;
    @Column(name = "MERDAY")
    private String merDay;
    @Column(name = "ADDMERCOUNT")
    private Integer addMerCount;
    @Column(name = "JHUOMERCOUNT")
    private Integer jhuoMerCount;
    @Column(name = "ACTMERCOUNT")
    private Integer actMerCount;
    @Column(name = "CDATE")
    private Date cdate;
    @Column(name = "CBY")
    private String cby;
    @Column(name = "REMARKS1")
    private String remarks1;
    @Column(name = "REMARKS2")
    private String remarks2;

    public Integer getMisId() {
        return misId;
    }

    public void setMisId(Integer misId) {
        this.misId = misId;
    }

    public String getUnno() {
        return unno;
    }

    public void setUnno(String unno) {
        this.unno = unno;
    }

    public String getMerDay() {
        return merDay;
    }

    public void setMerDay(String merDay) {
        this.merDay = merDay;
    }

    public Integer getAddMerCount() {
        return addMerCount;
    }

    public void setAddMerCount(Integer addMerCount) {
        this.addMerCount = addMerCount;
    }

    public Integer getJhuoMerCount() {
        return jhuoMerCount;
    }

    public void setJhuoMerCount(Integer jhuoMerCount) {
        this.jhuoMerCount = jhuoMerCount;
    }

    public Integer getActMerCount() {
        return actMerCount;
    }

    public void setActMerCount(Integer actMerCount) {
        this.actMerCount = actMerCount;
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
