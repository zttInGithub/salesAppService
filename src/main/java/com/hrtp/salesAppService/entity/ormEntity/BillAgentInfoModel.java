package com.hrtp.salesAppService.entity.ormEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * BillAgentInfoModel
 * description TODO
 * create by lxj 2018/8/21
 **/
@Entity
@Table(name = "BILL_AGENTUNITINFO")
public class BillAgentInfoModel implements Serializable {
    private static final long serialVersionUID = 1L;

    public BillAgentInfoModel(){}

    public BillAgentInfoModel(String agentName,String bAddr,String bno,String legalNum,String legalPerson,String
            legalType){
        this.agentName = agentName;
        this.bAddr = bAddr;
        this.bno = bno;
        this.legalNum = legalNum;
        this.legalPerson = legalPerson;
        this.legalType = legalType;
    }

    public BillAgentInfoModel(String accType,String areaType,String bankAccName,String bankAccNo,String bankArea,String
            bankBranch,String bankType){
        this.accType = accType;
        this.areaType = areaType;
        this.bankAccName = bankAccName;
        this.bankAccNo = bankAccNo;
        this.bankArea = bankArea;
        this.bankBranch = bankBranch;
        this.bankType = bankType;
    }

    public BillAgentInfoModel(String businessContact,String businessContactsMail,String businessContactsPhone,String contact,String contactTel,String
            riskContact,String riskContactPhone,String riskContactMail){
        this.businessContact = businessContact;
        this.businessContactsMail = businessContactsMail;
        this.businessContactsPhone = businessContactsPhone;
        this.contact = contact;
        this.contactTel = contactTel;
        this.riskContact = riskContact;
        this.riskContactPhone = riskContactPhone;
        this.riskContactMail = riskContactMail;
    }
    @Id
    @Column(name = "BUID")
    private String buId;                    //代理商主键
    @Column(name = "PARENTUNNO")
    private String parentUnno;                //归属机构编号-用于暂存
    @Column(name = "UNNO")
    private String unno;                    //机构编号
    @Column(name = "AGENTNAME")
    private String agentName;                //代理商名称
    @Column(name = "BADDR")
    private String bAddr;            //经营地址
    @Column(name = "LEGALPERSON")
    private String legalPerson;                //法人
    @Column(name = "LEGALTYPE")
    private String legalType;                //法人证件类型
    @Column(name = "LEGALNUM")
    private String legalNum;                //法人证件号码
    @Column(name = "ACCTYPE")
    private String accType;                    //开户类型
    @Column(name = "BANKBRANCH")
    private String bankBranch;                //开户银行
    @Column(name = "BANKACCNO")
    private String bankAccNo;                //开户银行账号
    @Column(name = "BANKACCNAME")
    private String bankAccName;                //开户账号名称
    @Column(name = "BANKTYPE")
    private String bankType;                //开户银行是否为交通银行
    @Column(name = "AREATYPE")
    private String areaType;                //开户银行所在地类别
    @Column(name = "BANKAREA")
    private String bankArea;                //开户地
    @Column(name = "RISKAMOUNT")
    private Double riskAmount;                //风险保障金
    @Column(name = "AMOUNTCONFIRMDATE")
    private Date amountConfirmDate;            //缴款时间
    @Column(name = "OPENDATE")
    private Date openDate;                    //代理商开通
    @Column(name = "CONTRACTNO")
    private String contractNo;                //签约合同编号
    @Column(name = "SIGNUSERID")
    private Integer signUserId;                //签约人员
    @Column(name = "BNO")
    private String bno;                        //营业执照编号
    @Column(name = "RNO")
    private String rno;                        //组织机构代码
    @Column(name = "REGISTRYNO")
    private String registryNo;                //税务登记证号
    @Column(name = "BANKOPENACC")
    private String bankOpenAcc;                //企业银行开户许可证号
    @Column(name = "MAINTAINUSERID")
    private Integer maintainUserId;            //维护人员
    @Column(name = "CONTACT")
    private String contact;                    //负责人
    @Column(name = "CONTACTTEL")
    private String contactTel;                //负责人固定电话
    @Column(name = "CONTACTPHONE")
    private String contactPhone;            //负责人手机
    @Column(name = "SECONDCONTACT")
    private String secondContact;            //第二联系人
    @Column(name = "SECONDCONTACTTEL")
    private String secondContactTel;        //第二联系人固定电话
    @Column(name = "SECONDCONTACTPHONE")
    private String secondContactPhone;        //第二联系人手机
    @Column(name = "RISKCONTACT")
    private String riskContact;                //风险调单联系人
    @Column(name = "RISKCONTACTPHONE")
    private String riskContactPhone;        //风险调单联系手机
    @Column(name = "RISKCONTACTMAIL")
    private String riskContactMail;            //风险调单联系邮箱
    @Column(name = "BUSINESSCONTACTS")
    private String businessContact;        //业务联系人
    @Column(name = "BUSINESSCONTACTSPHONE")
    private String businessContactsPhone;    //业务联系手机
    @Column(name = "BUSINESSCONTACTSMAIL")
    private String businessContactsMail;    //业务联系邮箱
    @Column(name = "MAINTAINUID")
    private Integer maintainUid;            //操作人员
    @Column(name = "MAINTAINDATE")
    private Date maintainDate;                //操作日期
    @Column(name = "MAINTAINTYPE")
    private String maintainType;            //操作类型
    @Column(name = "REMARKS")
    private String remarks;                    //备注
    // new add
    @Column(name = "REGADDRESS")
    private String regAddress;     //注册地址
    @Column(name = "PAYBANKID")
    private String payBankID;     //支付系统行号
    @Column(name = "PROFITCONTACTPERSON")
    private String profitContactPerson;     //分润联系人
    @Column(name = "PROFITCONTACTPHONE")
    private String profitContactPhone;     //联系电话
    @Column(name = "PROFITCONTACTEMAIL")
    private String profitContactEmail;     //邮箱
    @Column(name = "DEALUPLOAD")
    private String dealUpLoad;     //协议签章页照片
    @Column(name = "BUSLICUPLOAD")
    private String busLicUpLoad;     //营业执照（企业必传）
    @Column(name = "PROOFOFOPENACCOUNTUPLOAD")
    private String proofOfOpenAccountUpLoad;     //对公开户证明（企业必传）
    @Column(name = "LEGALAUPLOAD")
    private String legalAUpLoad;     //法人身份证正面
    @Column(name = "LEGALBUPLOAD")
    private String legalBUpLoad;     //法人身份证反面
    @Column(name = "ACCOUNTAUTHUPLOAD")
    private String accountAuthUpLoad;     //入账授权书
    @Column(name = "ACCOUNTLEGALAUPLOAD")
    private String accountLegalAUpLoad;     //入账人身份证正面
    @Column(name = "ACCOUNTLEGALBUPLOAD")
    private String accountLegalBUpLoad;     //入账人身份证反面
    @Column(name = "ACCOUNTLEGALHANDUPLOAD")
    private String accountLegalHandUpLoad;     //入账人手持身份证
    @Column(name = "OFFICEADDRESSUPLOAD")
    private String officeAddressUpLoad;     //办公地点照片
    @Column(name = "PROFITUPLOAD")
    private String profitUpLoad;     //分润照片
    @Column(name = "AGENTLVL")
    private Integer agentLvl;     //是否为运营中心   只存在签约运营中心为1
    @Column(name = "APPROVESTATUS")
    private String approveStatus;  // K退回W待审Y通过
    @Column(name = "AGENTSHORTNM")
    private String agentShortNm;
    @Column(name = "RETURNREASON")
    private String returnReason; //退回原因
    @Column(name = "MOBILEPHONE")
    private String mobilePhone;//登录手机号

    public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getBuId() {
        return buId;
    }

    public void setBuId(String buId) {
        this.buId = buId;
    }

    public String getParentUnno() {
        return parentUnno;
    }

    public void setParentUnno(String parentUnno) {
        this.parentUnno = parentUnno;
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

    public String getbAddr() {
        return bAddr;
    }

    public void setbAddr(String bAddr) {
        this.bAddr = bAddr;
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

    public String getLegalNum() {
        return legalNum;
    }

    public void setLegalNum(String legalNum) {
        this.legalNum = legalNum;
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

    public Double getRiskAmount() {
        return riskAmount;
    }

    public void setRiskAmount(Double riskAmount) {
        this.riskAmount = riskAmount;
    }

    public Date getAmountConfirmDate() {
        return amountConfirmDate;
    }

    public void setAmountConfirmDate(Date amountConfirmDate) {
        this.amountConfirmDate = amountConfirmDate;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Integer getSignUserId() {
        return signUserId;
    }

    public void setSignUserId(Integer signUserId) {
        this.signUserId = signUserId;
    }

    public String getBno() {
        return bno;
    }

    public void setBno(String bno) {
        this.bno = bno;
    }

    public String getRno() {
        return rno;
    }

    public void setRno(String rno) {
        this.rno = rno;
    }

    public String getRegistryNo() {
        return registryNo;
    }

    public void setRegistryNo(String registryNo) {
        this.registryNo = registryNo;
    }

    public String getBankOpenAcc() {
        return bankOpenAcc;
    }

    public void setBankOpenAcc(String bankOpenAcc) {
        this.bankOpenAcc = bankOpenAcc;
    }

    public Integer getMaintainUserId() {
        return maintainUserId;
    }

    public void setMaintainUserId(Integer maintainUserId) {
        this.maintainUserId = maintainUserId;
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

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getSecondContact() {
        return secondContact;
    }

    public void setSecondContact(String secondContact) {
        this.secondContact = secondContact;
    }

    public String getSecondContactTel() {
        return secondContactTel;
    }

    public void setSecondContactTel(String secondContactTel) {
        this.secondContactTel = secondContactTel;
    }

    public String getSecondContactPhone() {
        return secondContactPhone;
    }

    public void setSecondContactPhone(String secondContactPhone) {
        this.secondContactPhone = secondContactPhone;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getBusinessContact() {
        return businessContact;
    }

    public void setBusinessContact(String businessContact) {
        this.businessContact = businessContact;
    }

    public String getBusinessContactsPhone() {
        return businessContactsPhone;
    }

    public void setBusinessContactsPhone(String businessContactsPhone) {
        this.businessContactsPhone = businessContactsPhone;
    }

    public String getBusinessContactsMail() {
        return businessContactsMail;
    }

    public void setBusinessContactsMail(String businessContactsMail) {
        this.businessContactsMail = businessContactsMail;
    }

    public Integer getMaintainUid() {
        return maintainUid;
    }

    public void setMaintainUid(Integer maintainUid) {
        this.maintainUid = maintainUid;
    }

    public Date getMaintainDate() {
        return maintainDate;
    }

    public void setMaintainDate(Date maintainDate) {
        this.maintainDate = maintainDate;
    }

    public String getMaintainType() {
        return maintainType;
    }

    public void setMaintainType(String maintainType) {
        this.maintainType = maintainType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRegAddress() {
        return regAddress;
    }

    public void setRegAddress(String regAddress) {
        this.regAddress = regAddress;
    }

    public String getPayBankID() {
        return payBankID;
    }

    public void setPayBankID(String payBankID) {
        this.payBankID = payBankID;
    }

    public String getProfitContactPerson() {
        return profitContactPerson;
    }

    public void setProfitContactPerson(String profitContactPerson) {
        this.profitContactPerson = profitContactPerson;
    }

    public String getProfitContactPhone() {
        return profitContactPhone;
    }

    public void setProfitContactPhone(String profitContactPhone) {
        this.profitContactPhone = profitContactPhone;
    }

    public String getProfitContactEmail() {
        return profitContactEmail;
    }

    public void setProfitContactEmail(String profitContactEmail) {
        this.profitContactEmail = profitContactEmail;
    }

    public String getDealUpLoad() {
        return dealUpLoad;
    }

    public void setDealUpLoad(String dealUpLoad) {
        this.dealUpLoad = dealUpLoad;
    }

    public String getBusLicUpLoad() {
        return busLicUpLoad;
    }

    public void setBusLicUpLoad(String busLicUpLoad) {
        this.busLicUpLoad = busLicUpLoad;
    }

    public String getProofOfOpenAccountUpLoad() {
        return proofOfOpenAccountUpLoad;
    }

    public void setProofOfOpenAccountUpLoad(String proofOfOpenAccountUpLoad) {
        this.proofOfOpenAccountUpLoad = proofOfOpenAccountUpLoad;
    }

    public String getLegalAUpLoad() {
        return legalAUpLoad;
    }

    public void setLegalAUpLoad(String legalAUpLoad) {
        this.legalAUpLoad = legalAUpLoad;
    }

    public String getLegalBUpLoad() {
        return legalBUpLoad;
    }

    public void setLegalBUpLoad(String legalBUpLoad) {
        this.legalBUpLoad = legalBUpLoad;
    }

    public String getAccountAuthUpLoad() {
        return accountAuthUpLoad;
    }

    public void setAccountAuthUpLoad(String accountAuthUpLoad) {
        this.accountAuthUpLoad = accountAuthUpLoad;
    }

    public String getAccountLegalAUpLoad() {
        return accountLegalAUpLoad;
    }

    public void setAccountLegalAUpLoad(String accountLegalAUpLoad) {
        this.accountLegalAUpLoad = accountLegalAUpLoad;
    }

    public String getAccountLegalBUpLoad() {
        return accountLegalBUpLoad;
    }

    public void setAccountLegalBUpLoad(String accountLegalBUpLoad) {
        this.accountLegalBUpLoad = accountLegalBUpLoad;
    }

    public String getAccountLegalHandUpLoad() {
        return accountLegalHandUpLoad;
    }

    public void setAccountLegalHandUpLoad(String accountLegalHandUpLoad) {
        this.accountLegalHandUpLoad = accountLegalHandUpLoad;
    }

    public String getOfficeAddressUpLoad() {
        return officeAddressUpLoad;
    }

    public void setOfficeAddressUpLoad(String officeAddressUpLoad) {
        this.officeAddressUpLoad = officeAddressUpLoad;
    }

    public String getProfitUpLoad() {
        return profitUpLoad;
    }

    public void setProfitUpLoad(String profitUpLoad) {
        this.profitUpLoad = profitUpLoad;
    }

    public Integer getAgentLvl() {
        return agentLvl;
    }

    public void setAgentLvl(Integer agentLvl) {
        this.agentLvl = agentLvl;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getAgentShortNm() {
        return agentShortNm;
    }

    public void setAgentShortNm(String agentShortNm) {
        this.agentShortNm = agentShortNm;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }
}
