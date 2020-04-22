package com.hrtp.salesAppService.entity.ormEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * BillAgentUnitTaskDetail1Model
 * description 基本信息
 * create by lxj 2018/8/27
 **/
@Entity
@Table(name = "BILL_AGENTUNITTASKDETAIL1")
public class BillAgentUnitTaskDetail1Model implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "BAUTDID")
    @SequenceGenerator(name = "AGENTUNITTASKDETAIL1_SEQUENCE", sequenceName = "S_BILL_AGENTUNITTASKDETAIL1",allocationSize = 1)
    @GeneratedValue(generator = "AGENTUNITTASKDETAIL1_SEQUENCE",strategy = GenerationType.SEQUENCE)
    private Integer bautdId;
    @Column(name = "AGENTNAME")
    private String agentName;
    @Column(name = "LEGALPERSON")
    private String legalPerson;
    @Column(name = "LEGALTYPE")
    private String legalType;
    @Column(name = "LEGALNUM")
    private String legalNum;
    @Column(name = "BNO")
    private String bno;
    @Column(name = "BADDR")
    private String bAddr;
    @Column(name = "LEGALAUPLOAD")
    private String legalAupload;
    @Column(name = "LEGALBUPLOAD")
    private String legalBupload;
    @Column(name = "DEALUPLOAD")
    private String dealUpload;
    @Column(name = "BUSLICUPLOAD")
    private String busliCupload;

    public Integer getBautdId() {
        return bautdId;
    }

    public void setBautdId(Integer bautdId) {
        this.bautdId = bautdId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
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

    public String getBno() {
        return bno;
    }

    public void setBno(String bno) {
        this.bno = bno;
    }

    public String getbAddr() {
        return bAddr;
    }

    public void setbAddr(String bAddr) {
        this.bAddr = bAddr;
    }

    public String getLegalAupload() {
        return legalAupload;
    }

    public void setLegalAupload(String legalAupload) {
        this.legalAupload = legalAupload;
    }

    public String getLegalBupload() {
        return legalBupload;
    }

    public void setLegalBupload(String legalBupload) {
        this.legalBupload = legalBupload;
    }

    public String getDealUpload() {
        return dealUpload;
    }

    public void setDealUpload(String dealUpload) {
        this.dealUpload = dealUpload;
    }

    public String getBusliCupload() {
        return busliCupload;
    }

    public void setBusliCupload(String busliCupload) {
        this.busliCupload = busliCupload;
    }
}
