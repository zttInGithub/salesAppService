package com.hrtp.salesAppService.entity.ormEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * BillAgentUnitTaskDetail3Model
 * description
 * create by lxj 2018/8/27
 **/
@Entity
@Table(name = "BILL_AGENTUNITTASKDETAIL3")
public class BillAgentUnitTaskDetail3Model implements Serializable{
    private static final long version = 1L;
    @Id
    @Column(name = "BAUTDID")
    @SequenceGenerator(name = "AGENTUNITTASKDETAIL3_SEQUENCE", sequenceName = "S_BILL_AGENTUNITTASKDETAIL3",allocationSize = 1)
    @GeneratedValue(generator = "AGENTUNITTASKDETAIL3_SEQUENCE",strategy = GenerationType.SEQUENCE)
    private Integer bautdId;
    @Column(name = "CONTACT")
    private String contact;
    @Column(name = "CONTACTTEL")
    private String contactTel;
    @Column(name = "RISKCONTACT")
    private String riskContact;
    @Column(name = "RISKCONTACTPHONE")
    private String riskContactPhone;
    @Column(name = "RISKCONTACTMAIL")
    private String riskContactMail;
    @Column(name = "BUSINESSCONTACTS")
    private String businessContact;
    @Column(name = "BUSINESSCONTACTSPHONE")
    private String businessContactsPhone;
    @Column(name = "BUSINESSCONTACTSMAIL")
    private String businessContactsMail;

    public Integer getBautdId() {
        return bautdId;
    }

    public void setBautdId(Integer bautdId) {
        this.bautdId = bautdId;
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
}