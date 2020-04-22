package com.hrtp.salesAppService.entity.ormEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * BillAgentUnitTaskDetail2Model
 * description TODO
 * create by lxj 2018/8/27
 **/
@Entity
@Table(name = "BILL_AGENTUNITTASKDetail2")
public class BillAgentUnitTaskDetail2Model implements Serializable{
    private static final long version = 1L;
    @Id
    @Column(name = "BAUTDID")
    @SequenceGenerator(name = "AGENTUNITTASKDETAIL2_SEQUENCE", sequenceName = "S_BILL_AGENTUNITTASKDETAIL2",allocationSize = 1)
    @GeneratedValue(generator = "AGENTUNITTASKDETAIL2_SEQUENCE",strategy = GenerationType.SEQUENCE)
    private Integer bautdId;
    @Column(name = "ACCTYPE")
    private String accType;
    @Column(name = "BANKBRANCH")
    private String bankBranch;
    @Column(name = "BANKACCNO")
    private String bankAccNo;
    @Column(name = "BANKACCNAME")
    private String bankAccName;
    @Column(name = "BANKTYPE")
    private String bankType;
    @Column(name = "AREATYPE")
    private String areaType;
    @Column(name = "BANKAREA")
    private String bankArea;
    @Column(name = "ACCOUNTLEGALAUPLOAD")
    private String accountLegalAupload;
    @Column(name = "ACCOUNTLEGALBUPLOAD")
    private String accountLegalBupload;
    @Column(name = "ACCOUNTLEGALHANDUPLOAD")
    private String accountLegalHandUpload;
    @Column(name = "ACCOUNTAUTHUPLOAD")
    private String accountAuthUpload;

    public Integer getBautdId() {
        return bautdId;
    }

    public void setBautdId(Integer bautdId) {
        this.bautdId = bautdId;
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

    public String getBankArea() {
        return bankArea;
    }

    public void setBankArea(String bankArea) {
        this.bankArea = bankArea;
    }

    public String getAccountLegalAupload() {
        return accountLegalAupload;
    }

    public void setAccountLegalAupload(String accountLegalAupload) {
        this.accountLegalAupload = accountLegalAupload;
    }

    public String getAccountLegalBupload() {
        return accountLegalBupload;
    }

    public void setAccountLegalBupload(String accountLegalBupload) {
        this.accountLegalBupload = accountLegalBupload;
    }

    public String getAccountLegalHandUpload() {
        return accountLegalHandUpload;
    }

    public void setAccountLegalHandUpload(String accountLegalHandUpload) {
        this.accountLegalHandUpload = accountLegalHandUpload;
    }

    public String getAccountAuthUpload() {
        return accountAuthUpload;
    }

    public void setAccountAuthUpload(String accountAuthUpload) {
        this.accountAuthUpload = accountAuthUpload;
    }
}
