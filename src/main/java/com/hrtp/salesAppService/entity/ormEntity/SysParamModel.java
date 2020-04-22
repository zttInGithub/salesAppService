package com.hrtp.salesAppService.entity.ormEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * SysParamModel
 * description
 * create by lxj 2018/8/27
 **/
@Entity
@Table(name = "SYS_PARAM")
public class SysParamModel implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "TITLE")
    private String title;
    @Column(name = "PWD_EXPIRY_DAYS")
    private Integer pwdExpiryDays;
    @Column(name = "PWD_ERR_TIMES")
    private Integer pwdErrTimes;
    @Column(name = "UPLOAD_PATH")
    private String uploadPath;
    @Column(name = "DOWNLOAD_PATH")
    private String downLoadPath;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "CREATE_DATE")
    private Date createDate;
    @Column(name = "CREATE_USER")
    private String createUser;
    @Column(name = "UPDATE_DATE")
    private Date updateDate;
    @Column(name = "UPDATE_USER")
    private String updateUser;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPwdExpiryDays() {
        return pwdExpiryDays;
    }

    public void setPwdExpiryDays(Integer pwdExpiryDays) {
        this.pwdExpiryDays = pwdExpiryDays;
    }

    public Integer getPwdErrTimes() {
        return pwdErrTimes;
    }

    public void setPwdErrTimes(Integer pwdErrTimes) {
        this.pwdErrTimes = pwdErrTimes;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getDownLoadPath() {
        return downLoadPath;
    }

    public void setDownLoadPath(String downLoadPath) {
        this.downLoadPath = downLoadPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}
