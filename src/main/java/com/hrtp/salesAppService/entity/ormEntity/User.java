package com.hrtp.salesAppService.entity.ormEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "SYS_USER")
public class User implements Serializable{
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @Column(name="USER_ID")
    private String id;
    @Column(name = "LOGIN_NAME")
    private String loginName;
    @Column(name = "PWD")
    private String pwd;
    @Column(name = "USER_NAME")
    private String userName;
    @Column(name = "status")
    private String status;
    @Column(name = "USER_LVL")
    private Integer userLvl;
    @Column(name = "PWD_MOD_DATE")
    private String pwdModDate;
    @Column(name = "RESET_FLAG")
    private Integer restFlag;
    @Column(name = "START_DATE")
    private Date startDate;
    @Column(name = "END_DATE")
    private Date endDate;
    @Column(name = "CREATE_DATE")
    private Date createDate;
    @Column(name = "CREATE_USER")
    private String createUser;
    @Column(name = "UPDATE_DATE")
    private Date updateDate;
    @Column(name = "UPDATE_USER")
    private String updateUser;
    @Column(name = "ISLOGIN")
    private Integer isLogin;
    @Column(name = "OPENID")
    private String openId;
    @Column(name = "SYSFLAG")
    private String sysFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUserLvl() {
        return userLvl;
    }

    public void setUserLvl(Integer userLvl) {
        this.userLvl = userLvl;
    }

    public String getPwdModDate() {
        return pwdModDate;
    }

    public void setPwdModDate(String pwdModDate) {
        this.pwdModDate = pwdModDate;
    }

    public Integer getRestFlag() {
        return restFlag;
    }

    public void setRestFlag(Integer restFlag) {
        this.restFlag = restFlag;
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

    public Integer getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Integer isLogin) {
        this.isLogin = isLogin;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSysFlag() {
        return sysFlag;
    }

    public void setSysFlag(String sysFlag) {
        this.sysFlag = sysFlag;
    }
}
