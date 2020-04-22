
package com.hrtp.salesAppService.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>aggPayTerm complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="aggPayTerm">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bapid" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cby" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="minfo1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="minfo2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="qrtid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="shopname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "aggPayTerm", propOrder = {
    "bapid",
    "cby",
    "mid",
    "minfo1",
    "minfo2",
    "qrtid",
    "shopname",
    "typeflag",
    "unno"
})
public class AggPayTerm {

    protected Integer bapid;
    protected String cby;
    protected String mid;
    protected String minfo1;
    protected String minfo2;
    protected String qrtid;
    protected String shopname;
    protected Integer typeflag;
    protected String unno;

    /**
     * 获取bapid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBapid() {
        return bapid;
    }

    /**
     * 设置bapid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBapid(Integer value) {
        this.bapid = value;
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
     * 获取qrtid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQrtid() {
        return qrtid;
    }

    /**
     * 设置qrtid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQrtid(String value) {
        this.qrtid = value;
    }

    /**
     * 获取shopname属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShopname() {
        return shopname;
    }

    /**
     * 设置shopname属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShopname(String value) {
        this.shopname = value;
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
