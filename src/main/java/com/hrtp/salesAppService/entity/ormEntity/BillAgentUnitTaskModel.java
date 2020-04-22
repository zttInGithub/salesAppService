package com.hrtp.salesAppService.entity.ormEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * BillAgentUnitTaskModel
 * description 代理商工单映射
 * create by lxj 2018/8/27
 **/
@Entity
@Table(name = "BILL_AGENTUNITTASK")
public class BillAgentUnitTaskModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "BAUTID")
    @SequenceGenerator(name = "AGENTUNITTASK_SEQUENCE", sequenceName = "S_BILL_AGENTUNITTASK",allocationSize = 1)
    @GeneratedValue(generator = "AGENTUNITTASK_SEQUENCE",strategy = GenerationType.SEQUENCE)
    private Integer buId;
    @JsonIgnore
    @Column(name = "BAUTDID")
    private Integer bautdId;
    @JsonIgnore
    @Column(name = "TASKTYPE")
    private String taskType;
    @Column(name = "UNNO")
    private String unno;
    @JsonIgnore
    @Column(name = "MAINTAINDATE")
    private Date maintainDate;
    @Column(name = "MAINTAINTYPE")
    private String maintainType;
    @JsonIgnore
    @Column(name = "APPROVEDATE")
    private Date approveDate;
    @Column(name = "APPROVESTATUS")
    private String approveStatus;
    @Column(name = "APPROVENOTE")
    private String approveNote;

    public Integer getBuId() {
        return buId;
    }

    public void setBuId(Integer buId) {
        this.buId = buId;
    }

    public Integer getBautdId() {
        return bautdId;
    }

    public void setBautdId(Integer bautdId) {
        this.bautdId = bautdId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getUnno() {
        return unno;
    }

    public void setUnno(String unno) {
        this.unno = unno;
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

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getApproveNote() {
        return approveNote;
    }

    public void setApproveNote(String approveNote) {
        this.approveNote = approveNote;
    }
}
