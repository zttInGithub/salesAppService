package com.hrtp.salesAppService.entity.appEntity;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * BillAgentInfoEntity
 * description 代理商信息
 * create by lxj 2018/8/21
 **/
public class BillAgentInfoEntity implements Serializable{
    
	private static final long serialVersionUID = 1L;
	private String buId;
    private String unno;
    private String agentName;
    private String accType;
    private String bankBranch;
    private String bankAccNo;
    private String bankAccName;
    private String bankArea;
    private String bankType;
    private String areaType;
    private String bAddr;
    private String bno;
    private String legalNum;
    private String legalPerson;
    private String legalType;
    private String businessContact;
    private String businessContactsMail;
    private String businessContactsPhone;
    private String contact;
    private String contactTel;
    private String riskContact;
    private String riskContactPhone;
    private String riskContactMail;
    private String legalAUpLoadFile;
    private String legalBUpLoadFile;
    private String legalHandUpLoadFile;
    private String authUpLoadFile;
    
    private String mobilePhone;
	private Integer unLvl;
	private String mobile;
	private String userId;
	private String password;
	private String msg;
	@JsonProperty("AppId")  
	private String AppId;
	@JsonProperty("AppId")  
	public String getAppId() {
		return AppId;
	}
	@JsonProperty("AppId")  
	public void setAppId(String AppId) {
		this.AppId = AppId;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Integer getUnLvl() {
		return unLvl;
	}

	public void setUnLvl(Integer unLvl) {
		this.unLvl = unLvl;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getLegalAUpLoadFile() {
        return legalAUpLoadFile;
    }

    public void setLegalAUpLoadFile(String legalAUpLoadFile) {
        this.legalAUpLoadFile = legalAUpLoadFile;
    }

    public String getLegalBUpLoadFile() {
        return legalBUpLoadFile;
    }

    public void setLegalBUpLoadFile(String legalBUpLoadFile) {
        this.legalBUpLoadFile = legalBUpLoadFile;
    }

    public String getLegalHandUpLoadFile() {
        return legalHandUpLoadFile;
    }

    public void setLegalHandUpLoadFile(String legalHandUpLoadFile) {
        this.legalHandUpLoadFile = legalHandUpLoadFile;
    }

    public String getAuthUpLoadFile() {
        return authUpLoadFile;
    }

    public void setAuthUpLoadFile(String authUpLoadFile) {
        this.authUpLoadFile = authUpLoadFile;
    }

    public String getBuId() {
        return buId;
    }

    public void setBuId(String buId) {
        this.buId = buId;
    }

    public String getUnno() {
        return unno;
    }

    public void setUnno(String unno) {
        this.unno = unno;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getBankAccNo() {
        return bankAccNo;
    }

    public void setBankAccNo(String bankAccNo) {
        this.bankAccNo = bankAccNo;
    }

    public String getBankAccName() {
        return bankAccName;
    }

    public void setBankAccName(String bankAccName) {
        this.bankAccName = bankAccName;
    }

    public String getBankArea() {
        return bankArea;
    }

    public void setBankArea(String bankArea) {
        this.bankArea = bankArea;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public String getbAddr() {
        return bAddr;
    }

    public void setbAddr(String bAddr) {
        this.bAddr = bAddr;
    }

    public String getBno() {
        return bno;
    }

    public void setBno(String bno) {
        this.bno = bno;
    }

    public String getLegalNum() {
        return legalNum;
    }

    public void setLegalNum(String legalNum) {
        this.legalNum = legalNum;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getLegalType() {
        return legalType;
    }

    public void setLegalType(String legalType) {
        this.legalType = legalType;
    }

    public String getBusinessContact() {
        return businessContact;
    }

    public void setBusinessContact(String businessContact) {
        this.businessContact = businessContact;
    }

    public String getBusinessContactsMail() {
        return businessContactsMail;
    }

    public void setBusinessContactsMail(String businessContactsMail) {
        this.businessContactsMail = businessContactsMail;
    }

    public String getBusinessContactsPhone() {
        return businessContactsPhone;
    }

    public void setBusinessContactsPhone(String businessContactsPhone) {
        this.businessContactsPhone = businessContactsPhone;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getRiskContact() {
        return riskContact;
    }

    public void setRiskContact(String riskContact) {
        this.riskContact = riskContact;
    }

    public String getRiskContactPhone() {
        return riskContactPhone;
    }

    public void setRiskContactPhone(String riskContactPhone) {
        this.riskContactPhone = riskContactPhone;
    }

    public String getRiskContactMail() {
        return riskContactMail;
    }

    public void setRiskContactMail(String riskContactMail) {
        this.riskContactMail = riskContactMail;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
