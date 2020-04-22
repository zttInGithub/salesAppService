
package com.hrtp.salesAppService.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>agentUnitInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="agentUnitInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agentName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="amountConfirmDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="areaType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="baddr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankAccName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankAccNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankArea" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankBranch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankOpenAcc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="buid" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="businessContacts" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="businessContactsMail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="businessContactsPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contact" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contactPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contactTel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contractNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cronym" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isM35" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="legalNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="legalPerson" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="legalType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maintainDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="maintainType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maintainUid" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="maintainUserId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="mid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="openDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="parCompany" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="payBankId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="registryNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="riskAmount" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="riskContact" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="riskContactMail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="riskContactPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secondContact" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secondContactPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secondContactTel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="signUserId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="unno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="upperUnit1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="upperUnit2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="upperUnit3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "agentUnitInfo", propOrder = {
    "accType",
    "agentName",
    "amountConfirmDate",
    "areaType",
    "baddr",
    "bankAccName",
    "bankAccNo",
    "bankArea",
    "bankBranch",
    "bankOpenAcc",
    "bankType",
    "bno",
    "buid",
    "businessContacts",
    "businessContactsMail",
    "businessContactsPhone",
    "contact",
    "contactPhone",
    "contactTel",
    "contractNo",
    "cronym",
    "isM35",
    "legalNum",
    "legalPerson",
    "legalType",
    "maintainDate",
    "maintainType",
    "maintainUid",
    "maintainUserId",
    "mid",
    "openDate",
    "parCompany",
    "payBankId",
    "registryNo",
    "riskAmount",
    "riskContact",
    "riskContactMail",
    "riskContactPhone",
    "rno",
    "secondContact",
    "secondContactPhone",
    "secondContactTel",
    "signUserId",
    "unno",
    "upperUnit1",
    "upperUnit2",
    "upperUnit3"
})
public class AgentUnitInfo {

    protected String accType;
    protected String agentName;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar amountConfirmDate;
    protected String areaType;
    protected String baddr;
    protected String bankAccName;
    protected String bankAccNo;
    protected String bankArea;
    protected String bankBranch;
    protected String bankOpenAcc;
    protected String bankType;
    protected String bno;
    protected Integer buid;
    protected String businessContacts;
    protected String businessContactsMail;
    protected String businessContactsPhone;
    protected String contact;
    protected String contactPhone;
    protected String contactTel;
    protected String contractNo;
    protected String cronym;
    protected String isM35;
    protected String legalNum;
    protected String legalPerson;
    protected String legalType;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar maintainDate;
    protected String maintainType;
    protected Integer maintainUid;
    protected Integer maintainUserId;
    protected String mid;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar openDate;
    protected String parCompany;
    protected String payBankId;
    protected String registryNo;
    protected Double riskAmount;
    protected String riskContact;
    protected String riskContactMail;
    protected String riskContactPhone;
    protected String rno;
    protected String secondContact;
    protected String secondContactPhone;
    protected String secondContactTel;
    protected Integer signUserId;
    protected String unno;
    protected String upperUnit1;
    protected String upperUnit2;
    protected String upperUnit3;

    /**
     * 获取accType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccType() {
        return accType;
    }

    /**
     * 设置accType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccType(String value) {
        this.accType = value;
    }

    /**
     * 获取agentName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     * 设置agentName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentName(String value) {
        this.agentName = value;
    }

    /**
     * 获取amountConfirmDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAmountConfirmDate() {
        return amountConfirmDate;
    }

    /**
     * 设置amountConfirmDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAmountConfirmDate(XMLGregorianCalendar value) {
        this.amountConfirmDate = value;
    }

    /**
     * 获取areaType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAreaType() {
        return areaType;
    }

    /**
     * 设置areaType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAreaType(String value) {
        this.areaType = value;
    }

    /**
     * 获取baddr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBaddr() {
        return baddr;
    }

    /**
     * 设置baddr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBaddr(String value) {
        this.baddr = value;
    }

    /**
     * 获取bankAccName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankAccName() {
        return bankAccName;
    }

    /**
     * 设置bankAccName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankAccName(String value) {
        this.bankAccName = value;
    }

    /**
     * 获取bankAccNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankAccNo() {
        return bankAccNo;
    }

    /**
     * 设置bankAccNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankAccNo(String value) {
        this.bankAccNo = value;
    }

    /**
     * 获取bankArea属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankArea() {
        return bankArea;
    }

    /**
     * 设置bankArea属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankArea(String value) {
        this.bankArea = value;
    }

    /**
     * 获取bankBranch属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankBranch() {
        return bankBranch;
    }

    /**
     * 设置bankBranch属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankBranch(String value) {
        this.bankBranch = value;
    }

    /**
     * 获取bankOpenAcc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankOpenAcc() {
        return bankOpenAcc;
    }

    /**
     * 设置bankOpenAcc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankOpenAcc(String value) {
        this.bankOpenAcc = value;
    }

    /**
     * 获取bankType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankType() {
        return bankType;
    }

    /**
     * 设置bankType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankType(String value) {
        this.bankType = value;
    }

    /**
     * 获取bno属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBno() {
        return bno;
    }

    /**
     * 设置bno属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBno(String value) {
        this.bno = value;
    }

    /**
     * 获取buid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBuid() {
        return buid;
    }

    /**
     * 设置buid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBuid(Integer value) {
        this.buid = value;
    }

    /**
     * 获取businessContacts属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessContacts() {
        return businessContacts;
    }

    /**
     * 设置businessContacts属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessContacts(String value) {
        this.businessContacts = value;
    }

    /**
     * 获取businessContactsMail属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessContactsMail() {
        return businessContactsMail;
    }

    /**
     * 设置businessContactsMail属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessContactsMail(String value) {
        this.businessContactsMail = value;
    }

    /**
     * 获取businessContactsPhone属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessContactsPhone() {
        return businessContactsPhone;
    }

    /**
     * 设置businessContactsPhone属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessContactsPhone(String value) {
        this.businessContactsPhone = value;
    }

    /**
     * 获取contact属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContact() {
        return contact;
    }

    /**
     * 设置contact属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContact(String value) {
        this.contact = value;
    }

    /**
     * 获取contactPhone属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * 设置contactPhone属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactPhone(String value) {
        this.contactPhone = value;
    }

    /**
     * 获取contactTel属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactTel() {
        return contactTel;
    }

    /**
     * 设置contactTel属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactTel(String value) {
        this.contactTel = value;
    }

    /**
     * 获取contractNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractNo() {
        return contractNo;
    }

    /**
     * 设置contractNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractNo(String value) {
        this.contractNo = value;
    }

    /**
     * 获取cronym属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCronym() {
        return cronym;
    }

    /**
     * 设置cronym属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCronym(String value) {
        this.cronym = value;
    }

    /**
     * 获取isM35属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsM35() {
        return isM35;
    }

    /**
     * 设置isM35属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsM35(String value) {
        this.isM35 = value;
    }

    /**
     * 获取legalNum属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegalNum() {
        return legalNum;
    }

    /**
     * 设置legalNum属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegalNum(String value) {
        this.legalNum = value;
    }

    /**
     * 获取legalPerson属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegalPerson() {
        return legalPerson;
    }

    /**
     * 设置legalPerson属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegalPerson(String value) {
        this.legalPerson = value;
    }

    /**
     * 获取legalType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegalType() {
        return legalType;
    }

    /**
     * 设置legalType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegalType(String value) {
        this.legalType = value;
    }

    /**
     * 获取maintainDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMaintainDate() {
        return maintainDate;
    }

    /**
     * 设置maintainDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMaintainDate(XMLGregorianCalendar value) {
        this.maintainDate = value;
    }

    /**
     * 获取maintainType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaintainType() {
        return maintainType;
    }

    /**
     * 设置maintainType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaintainType(String value) {
        this.maintainType = value;
    }

    /**
     * 获取maintainUid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaintainUid() {
        return maintainUid;
    }

    /**
     * 设置maintainUid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaintainUid(Integer value) {
        this.maintainUid = value;
    }

    /**
     * 获取maintainUserId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaintainUserId() {
        return maintainUserId;
    }

    /**
     * 设置maintainUserId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaintainUserId(Integer value) {
        this.maintainUserId = value;
    }

    /**
     * 获取mid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMid() {
        return mid;
    }

    /**
     * 设置mid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMid(String value) {
        this.mid = value;
    }

    /**
     * 获取openDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOpenDate() {
        return openDate;
    }

    /**
     * 设置openDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOpenDate(XMLGregorianCalendar value) {
        this.openDate = value;
    }

    /**
     * 获取parCompany属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParCompany() {
        return parCompany;
    }

    /**
     * 设置parCompany属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParCompany(String value) {
        this.parCompany = value;
    }

    /**
     * 获取payBankId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayBankId() {
        return payBankId;
    }

    /**
     * 设置payBankId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayBankId(String value) {
        this.payBankId = value;
    }

    /**
     * 获取registryNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistryNo() {
        return registryNo;
    }

    /**
     * 设置registryNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistryNo(String value) {
        this.registryNo = value;
    }

    /**
     * 获取riskAmount属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRiskAmount() {
        return riskAmount;
    }

    /**
     * 设置riskAmount属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRiskAmount(Double value) {
        this.riskAmount = value;
    }

    /**
     * 获取riskContact属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiskContact() {
        return riskContact;
    }

    /**
     * 设置riskContact属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiskContact(String value) {
        this.riskContact = value;
    }

    /**
     * 获取riskContactMail属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiskContactMail() {
        return riskContactMail;
    }

    /**
     * 设置riskContactMail属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiskContactMail(String value) {
        this.riskContactMail = value;
    }

    /**
     * 获取riskContactPhone属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiskContactPhone() {
        return riskContactPhone;
    }

    /**
     * 设置riskContactPhone属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiskContactPhone(String value) {
        this.riskContactPhone = value;
    }

    /**
     * 获取rno属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRno() {
        return rno;
    }

    /**
     * 设置rno属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRno(String value) {
        this.rno = value;
    }

    /**
     * 获取secondContact属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondContact() {
        return secondContact;
    }

    /**
     * 设置secondContact属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondContact(String value) {
        this.secondContact = value;
    }

    /**
     * 获取secondContactPhone属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondContactPhone() {
        return secondContactPhone;
    }

    /**
     * 设置secondContactPhone属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondContactPhone(String value) {
        this.secondContactPhone = value;
    }

    /**
     * 获取secondContactTel属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondContactTel() {
        return secondContactTel;
    }

    /**
     * 设置secondContactTel属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondContactTel(String value) {
        this.secondContactTel = value;
    }

    /**
     * 获取signUserId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSignUserId() {
        return signUserId;
    }

    /**
     * 设置signUserId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSignUserId(Integer value) {
        this.signUserId = value;
    }

    /**
     * 获取unno属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnno() {
        return unno;
    }

    /**
     * 设置unno属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnno(String value) {
        this.unno = value;
    }

    /**
     * 获取upperUnit1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpperUnit1() {
        return upperUnit1;
    }

    /**
     * 设置upperUnit1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpperUnit1(String value) {
        this.upperUnit1 = value;
    }

    /**
     * 获取upperUnit2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpperUnit2() {
        return upperUnit2;
    }

    /**
     * 设置upperUnit2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpperUnit2(String value) {
        this.upperUnit2 = value;
    }

    /**
     * 获取upperUnit3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpperUnit3() {
        return upperUnit3;
    }

    /**
     * 设置upperUnit3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpperUnit3(String value) {
        this.upperUnit3 = value;
    }

}
