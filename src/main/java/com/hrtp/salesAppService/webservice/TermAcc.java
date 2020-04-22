
package com.hrtp.salesAppService.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>termAcc complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="termAcc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cby" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="depositAmt" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="depositFlag" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="mid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modleId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nonConnection" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rate" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="rebateType" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="secondRate" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="sn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="snType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="typeflag" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
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
@XmlType(name = "termAcc", propOrder = {
    "cby",
    "depositAmt",
    "depositFlag",
    "mid",
    "modleId",
    "nonConnection",
    "rate",
    "rebateType",
    "secondRate",
    "sn",
    "snType",
    "tid",
    "typeflag",
    "unno"
})
public class TermAcc {

    protected String cby;
    protected Integer depositAmt;
    protected Integer depositFlag;
    protected String mid;
    protected String modleId;
    protected String nonConnection;
    protected Double rate;
    protected Integer rebateType;
    protected Double secondRate;
    protected String sn;
    protected String snType;
    protected String tid;
    protected Integer typeflag;
    protected String unno;

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
     * 获取depositAmt属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDepositAmt() {
        return depositAmt;
    }

    /**
     * 设置depositAmt属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDepositAmt(Integer value) {
        this.depositAmt = value;
    }

    /**
     * 获取depositFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDepositFlag() {
        return depositFlag;
    }

    /**
     * 设置depositFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDepositFlag(Integer value) {
        this.depositFlag = value;
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
     * 获取modleId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModleId() {
        return modleId;
    }

    /**
     * 设置modleId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModleId(String value) {
        this.modleId = value;
    }

    /**
     * 获取nonConnection属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNonConnection() {
        return nonConnection;
    }

    /**
     * 设置nonConnection属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNonConnection(String value) {
        this.nonConnection = value;
    }

    /**
     * 获取rate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRate() {
        return rate;
    }

    /**
     * 设置rate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRate(Double value) {
        this.rate = value;
    }

    /**
     * 获取rebateType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRebateType() {
        return rebateType;
    }

    /**
     * 设置rebateType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRebateType(Integer value) {
        this.rebateType = value;
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
     * 获取sn属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSn() {
        return sn;
    }

    /**
     * 设置sn属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSn(String value) {
        this.sn = value;
    }

    /**
     * 获取snType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSnType() {
        return snType;
    }

    /**
     * 设置snType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSnType(String value) {
        this.snType = value;
    }

    /**
     * 获取tid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTid() {
        return tid;
    }

    /**
     * 设置tid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTid(String value) {
        this.tid = value;
    }

    /**
     * 获取typeflag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTypeflag() {
        return typeflag;
    }

    /**
     * 设置typeflag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTypeflag(Integer value) {
        this.typeflag = value;
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
