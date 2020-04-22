package com.hrtp.salesAppService.entity.ormEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * UnnoNoticeModel
 * description
 * create by lxj 2018/8/24
 **/
@Entity
@Table(name = "PG_UNNO_NOTICEID")
public class UnnoNoticeModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @JsonIgnore
    @Column(name = "NOTICEID")
    private Integer id;
    @JsonIgnore
    @Column(name = "MSGSENDDATE")
    private String msgsendDate;
    @Column(name = "MSGTOPIC")
    private String msgTopid;
    @Column(name = "MSGDESC")
    private String msgDesc;
    @JsonIgnore
    @Column(name = "CBY")
    private String cby;
    @JsonIgnore
    @Column(name = "CDATE")
    private Date cdate;
    @JsonIgnore
    @Column(name = "REMARKS")
    private String remarks;
    @Column(name = "STATUS")
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsgsendDate() {
        return msgsendDate;
    }

    public void setMsgsendDate(String msgsendDate) {
        this.msgsendDate = msgsendDate;
    }

    public String getMsgTopid() {
        return msgTopid;
    }

    public void setMsgTopid(String msgTopid) {
        this.msgTopid = msgTopid;
    }

    public String getMsgDesc() {
        return msgDesc;
    }

    public void setMsgDesc(String msgDesc) {
        this.msgDesc = msgDesc;
    }

    public String getCby() {
        return cby;
    }

    public void setCby(String cby) {
        this.cby = cby;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
