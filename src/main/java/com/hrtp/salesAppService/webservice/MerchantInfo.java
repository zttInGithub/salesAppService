
package com.hrtp.salesAppService.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>merchantInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="merchantInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accExpdate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accountStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="areaType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="baddr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankAccName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankAccNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankBranch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankFeeRate" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="bankType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cby" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="conMccid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contactPerson" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contactPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="creditBankRate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="creditFeeamt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dailyLimit" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="dcashRate" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="dealAmt" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="feeAmt" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="foreignFeeRate" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="identificationValid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ifCode" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="industryType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isM35" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="legalNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="legalPerson" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="legalType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="licenseValid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mainTenance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maintainDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="mccid" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="merchantType" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="mid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="minfo1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="minfo2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="naturalRate" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="outsideBankRate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="payBankId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="presettime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="province_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="QX_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="quotaamt" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="raddr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sales" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="scanRate" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="secondLimit" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="secondRate" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="secondRate2" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="sendCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="settMethod" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="settleCycle" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="settleMerger" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="settleMethod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="settleRange" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="singleLimit" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="tcashRate" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="tranSterm" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="unno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "merchantInfo", propOrder = {
    "accExpdate",
    "accNum",
    "accType",
    "accountStatus",
    "areaType",
    "baddr",
    "bankAccName",
    "bankAccNo",
    "bankBranch",
    "bankFeeRate",
    "bankType",
    "bno",
    "cby",
    "conMccid",
    "contactPerson",
    "contactPhone",
    "creditBankRate",
    "creditFeeamt",
    "dailyLimit",
    "dcashRate",
    "dealAmt",
    "email",
    "feeAmt",
    "foreignFeeRate",
    "identificationValid",
    "ifCode",
    "industryType",
    "isM35",
    "legalNum",
    "legalPerson",
    "legalType",
    "licenseValid",
    "mainTenance",
    "maintainDate",
    "mccid",
    "merchantType",
    "mid",
    "minfo1",
    "minfo2",
    "naturalRate",
    "outsideBankRate",
    "payBankId",
    "presettime",
    "provinceName",
    "qxName",
    "quotaamt",
    "raddr",
    "rname",
    "sales",
    "scanRate",
    "secondLimit",
    "secondRate",
    "secondRate2",
    "sendCode",
    "settMethod",
    "settleCycle",
    "settleMerger",
    "settleMethod",
    "settleRange",
    "singleLimit",
    "tcashRate",
    "tranSterm",
    "unno"
})
public class MerchantInfo {

    protected String accExpdate;
    protected String accNum;
    protected String accType;
    protected String accountStatus;
    protected String areaType;
    protected String baddr;
    protected String bankAccName;
    protected String bankAccNo;
    protected String bankBranch;
    protected Double bankFeeRate;
    protected String bankType;
    protected String bno;
    protected String cby;
    protected String conMccid;
    protected String contactPerson;
    protected String contactPhone;
    protected String creditBankRate;
    protected String creditFeeamt;
    protected Double dailyLimit;
    protected Double dcashRate;
    protected Double dealAmt;
    protected String email;
    protected Double feeAmt;
    protected Double foreignFeeRate;
    protected String identificationValid;
    protected Integer ifCode;
    protected String industryType;
    protected Integer isM35;
    protected String legalNum;
    protected String legalPerson;
    protected String legalType;
    protected String licenseValid;
    protected String mainTenance;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar maintainDate;
    protected Integer mccid;
    protected Integer merchantType;
    protected String mid;
    protected String minfo1;
    protected String minfo2;
    protected Double naturalRate;
    protected String outsideBankRate;
    protected String payBankId;
    protected String presettime;
    @XmlElement(name = "province_name")
    protected String provinceName;
    @XmlElement(name = "QX_name")
    protected String qxName;
    protected Double quotaamt;
    protected String raddr;
    protected String rname;
    protected String sales;
    protected Double scanRate;
    protected Double secondLimit;
    protected Double secondRate;
    protected Double secondRate2;
    protected String sendCode;
    protected Integer settMethod;
    protected Integer settleCycle;
    protected String settleMerger;
    protected String settleMethod;
    protected String settleRange;
    protected Double singleLimit;
    protected Double tcashRate;
    protected Integer tranSterm;
    protected String unno;

    /**
     * 获取accExpdate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccExpdate() {
        return accExpdate;
    }

    /**
     * 设置accExpdate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccExpdate(String value) {
        this.accExpdate = value;
    }

    /**
     * 获取accNum属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccNum() {
        return accNum;
    }

    /**
     * 设置accNum属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccNum(String value) {
        this.accNum = value;
    }

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
     * 获取accountStatus属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountStatus() {
        return accountStatus;
    }

    /**
     * 设置accountStatus属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountStatus(String value) {
        this.accountStatus = value;
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
     * 获取bankFeeRate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getBankFeeRate() {
        return bankFeeRate;
    }

    /**
     * 设置bankFeeRate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setBankFeeRate(Double value) {
        this.bankFeeRate = value;
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
     * 获取cby属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCby() {
        return cby;
    }

    /**
     * 设置cby属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCby(String value) {
        this.cby = value;
    }

    /**
     * 获取conMccid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConMccid() {
        return conMccid;
    }

    /**
     * 设置conMccid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConMccid(String value) {
        this.conMccid = value;
    }

    /**
     * 获取contactPerson属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactPerson() {
        return contactPerson;
    }

    /**
     * 设置contactPerson属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactPerson(String value) {
        this.contactPerson = value;
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
     * 获取creditBankRate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditBankRate() {
        return creditBankRate;
    }

    /**
     * 设置creditBankRate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditBankRate(String value) {
        this.creditBankRate = value;
    }

    /**
     * 获取creditFeeamt属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditFeeamt() {
        return creditFeeamt;
    }

    /**
     * 设置creditFeeamt属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditFeeamt(String value) {
        this.creditFeeamt = value;
    }

    /**
     * 获取dailyLimit属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDailyLimit() {
        return dailyLimit;
    }

    /**
     * 设置dailyLimit属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDailyLimit(Double value) {
        this.dailyLimit = value;
    }

    /**
     * 获取dcashRate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDcashRate() {
        return dcashRate;
    }

    /**
     * 设置dcashRate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDcashRate(Double value) {
        this.dcashRate = value;
    }

    /**
     * 获取dealAmt属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDealAmt() {
        return dealAmt;
    }

    /**
     * 设置dealAmt属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDealAmt(Double value) {
        this.dealAmt = value;
    }

    /**
     * 获取email属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置email属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * 获取feeAmt属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getFeeAmt() {
        return feeAmt;
    }

    /**
     * 设置feeAmt属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setFeeAmt(Double value) {
        this.feeAmt = value;
    }

    /**
     * 获取foreignFeeRate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getForeignFeeRate() {
        return foreignFeeRate;
    }

    /**
     * 设置foreignFeeRate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setForeignFeeRate(Double value) {
        this.foreignFeeRate = value;
    }

    /**
     * 获取identificationValid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificationValid() {
        return identificationValid;
    }

    /**
     * 设置identificationValid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificationValid(String value) {
        this.identificationValid = value;
    }

    /**
     * 获取ifCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIfCode() {
        return ifCode;
    }

    /**
     * 设置ifCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIfCode(Integer value) {
        this.ifCode = value;
    }

    /**
     * 获取industryType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndustryType() {
        return industryType;
    }

    /**
     * 设置industryType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndustryType(String value) {
        this.industryType = value;
    }

    /**
     * 获取isM35属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIsM35() {
        return isM35;
    }

    /**
     * 设置isM35属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIsM35(Integer value) {
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
     * 获取licenseValid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLicenseValid() {
        return licenseValid;
    }

    /**
     * 设置licenseValid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLicenseValid(String value) {
        this.licenseValid = value;
    }

    /**
     * 获取mainTenance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainTenance() {
        return mainTenance;
    }

    /**
     * 设置mainTenance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainTenance(String value) {
        this.mainTenance = value;
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
     * 获取mccid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMccid() {
        return mccid;
    }

    /**
     * 设置mccid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMccid(Integer value) {
        this.mccid = value;
    }

    /**
     * 获取merchantType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMerchantType() {
        return merchantType;
    }

    /**
     * 设置merchantType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMerchantType(Integer value) {
        this.merchantType = value;
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
     * 获取minfo1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMinfo1() {
        return minfo1;
    }

    /**
     * 设置minfo1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinfo1(String value) {
        this.minfo1 = value;
    }

    /**
     * 获取minfo2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMinfo2() {
        return minfo2;
    }

    /**
     * 设置minfo2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinfo2(String value) {
        this.minfo2 = value;
    }

    /**
     * 获取naturalRate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getNaturalRate() {
        return naturalRate;
    }

    /**
     * 设置naturalRate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setNaturalRate(Double value) {
        this.naturalRate = value;
    }

    /**
     * 获取outsideBankRate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutsideBankRate() {
        return outsideBankRate;
    }

    /**
     * 设置outsideBankRate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutsideBankRate(String value) {
        this.outsideBankRate = value;
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
     * 获取presettime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPresettime() {
        return presettime;
    }

    /**
     * 设置presettime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPresettime(String value) {
        this.presettime = value;
    }

    /**
     * 获取provinceName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvinceName() {
        return provinceName;
    }

    /**
     * 设置provinceName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvinceName(String value) {
        this.provinceName = value;
    }

    /**
     * 获取qxName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQXName() {
        return qxName;
    }

    /**
     * 设置qxName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQXName(String value) {
        this.qxName = value;
    }

    /**
     * 获取quotaamt属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getQuotaamt() {
        return quotaamt;
    }

    /**
     * 设置quotaamt属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setQuotaamt(Double value) {
        this.quotaamt = value;
    }

    /**
     * 获取raddr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRaddr() {
        return raddr;
    }

    /**
     * 设置raddr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRaddr(String value) {
        this.raddr = value;
    }

    /**
     * 获取rname属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRname() {
        return rname;
    }

    /**
     * 设置rname属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRname(String value) {
        this.rname = value;
    }

    /**
     * 获取sales属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSales() {
        return sales;
    }

    /**
     * 设置sales属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSales(String value) {
        this.sales = value;
    }

    /**
     * 获取scanRate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getScanRate() {
        return scanRate;
    }

    /**
     * 设置scanRate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setScanRate(Double value) {
        this.scanRate = value;
    }

    /**
     * 获取secondLimit属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSecondLimit() {
        return secondLimit;
    }

    /**
     * 设置secondLimit属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSecondLimit(Double value) {
        this.secondLimit = value;
    }

    /**
     * 获取secondRate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSecondRate() {
        return secondRate;
    }

    /**
     * 设置secondRate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSecondRate(Double value) {
        this.secondRate = value;
    }

    /**
     * 获取secondRate2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSecondRate2() {
        return secondRate2;
    }

    /**
     * 设置secondRate2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSecondRate2(Double value) {
        this.secondRate2 = value;
    }

    /**
     * 获取sendCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendCode() {
        return sendCode;
    }

    /**
     * 设置sendCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendCode(String value) {
        this.sendCode = value;
    }

    /**
     * 获取settMethod属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSettMethod() {
        return settMethod;
    }

    /**
     * 设置settMethod属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSettMethod(Integer value) {
        this.settMethod = value;
    }

    /**
     * 获取settleCycle属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSettleCycle() {
        return settleCycle;
    }

    /**
     * 设置settleCycle属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSettleCycle(Integer value) {
        this.settleCycle = value;
    }

    /**
     * 获取settleMerger属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSettleMerger() {
        return settleMerger;
    }

    /**
     * 设置settleMerger属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSettleMerger(String value) {
        this.settleMerger = value;
    }

    /**
     * 获取settleMethod属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSettleMethod() {
        return settleMethod;
    }

    /**
     * 设置settleMethod属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSettleMethod(String value) {
        this.settleMethod = value;
    }

    /**
     * 获取settleRange属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSettleRange() {
        return settleRange;
    }

    /**
     * 设置settleRange属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSettleRange(String value) {
        this.settleRange = value;
    }

    /**
     * 获取singleLimit属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSingleLimit() {
        return singleLimit;
    }

    /**
     * 设置singleLimit属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSingleLimit(Double value) {
        this.singleLimit = value;
    }

    /**
     * 获取tcashRate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTcashRate() {
        return tcashRate;
    }

    /**
     * 设置tcashRate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTcashRate(Double value) {
        this.tcashRate = value;
    }

    /**
     * 获取tranSterm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTranSterm() {
        return tranSterm;
    }

    /**
     * 设置tranSterm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTranSterm(Integer value) {
        this.tranSterm = value;
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

}
