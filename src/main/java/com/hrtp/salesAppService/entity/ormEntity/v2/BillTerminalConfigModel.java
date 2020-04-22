package com.hrtp.salesAppService.entity.ormEntity.v2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

/**
 * BillTerminalConfigModel
 * <p>
 * 终端下发回拨记录表
 * </p>
 *
 * @author xuegangliu 2019/07/10
 * @since 1.8
 **/
@Entity
@Table(name="BILL_TERMINAL_CONFIG")
public class BillTerminalConfigModel {
//    BTCID	N	NUMBER(6)	N			唯一主键
//    UNNO	N	CHAR(6)	N			机构号
//    SUB_UNNO	N	CHAR(6)	Y			分配下发机构号
//    TYPE	N	INTEGER	Y			类型 1:下发,2:回拨
//    START_TERMID	N	CHAR(8)	Y			开始终端号
//    END_TERMID	N	CHAR(8)	Y			结束终端号
//    TERMID_COUNT	N	INTEGER	Y			终端总数
//    STATUS	N	INTEGER	Y			处理状态0:未处理 1:处理中 2:处理完成
//    CREATE_USER	N	VARCHAR2(50)	Y			创建人
//    CREATE_DATE	N	DATE	Y			创建时间
//    UPDATE_USER	N	VARCHAR2(50)	Y			更新人
//    UPDATE_DATE	N	DATE	Y			更新时间
//    MAINTAINTYPE	N	CHAR(1)	Y			操作类型
//    MAINTAINUID	N	INTEGER	Y			操作人
//    MAINTAINDATE	N	DATE	Y			操作时间
//    UPPER_UNNO	N	CHAR(6)	Y			回拨机构号
//    REMARK	N	VARCHAR2(256)	Y			备注

    @Id
    @SequenceGenerator(name = "s_bill_terminal_config", sequenceName = "s_bill_terminal_config",allocationSize = 1)
    @GeneratedValue(generator = "s_bill_terminal_config",strategy = GenerationType.SEQUENCE)
    @Column(name="BTCID")
    private Integer btcId;

    @Column(name="UNNO")
    private String unno;

    @Column(name="SUB_UNNO")
    private String subUnno;

    @Column(name="TYPE")
    private Integer type;

    @Column(name="START_TERMID")
    private String startTermid;

    @Column(name="END_TERMID")
    private String endTermid;

    @Column(name="TERMID_COUNT")
    private Integer termidCount;

    @Column(name="STATUS")
    private Integer status;

    @Column(name="CREATE_USER")
    private String createUser;

    @Column(name="CREATE_DATE")
    private Date createDate;

    @Column(name="UPDATE_USER")
    private String updateUser;

    @Column(name="UPDATE_DATE")
    private Date updateDate;

    @Column(name="MAINTAINTYPE")
    private String maintainType;

    @Column(name="MAINTAINUID")
    private String maintainUid;

    @Column(name="MAINTAINDATE")
    private Date maintainDate;

    @Column(name="UPPER_UNNO")
    private String upperUnno;

    @Column(name="REMARK")
    private String remark;

    public BillTerminalConfigModel() {
    }

    public Integer getBtcId() {
        return btcId;
    }

    public void setBtcId(Integer btcId) {
        this.btcId = btcId;
    }

    public String getUnno() {
        return unno;
    }

    public void setUnno(String unno) {
        this.unno = unno;
    }

    public String getSubUnno() {
        return subUnno;
    }

    public void setSubUnno(String subUnno) {
        this.subUnno = subUnno;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getStartTermid() {
        return startTermid;
    }

    public void setStartTermid(String startTermid) {
        this.startTermid = startTermid;
    }

    public String getEndTermid() {
        return endTermid;
    }

    public void setEndTermid(String endTermid) {
        this.endTermid = endTermid;
    }

    public Integer getTermidCount() {
        return termidCount;
    }

    public void setTermidCount(Integer termidCount) {
        this.termidCount = termidCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getMaintainType() {
        return maintainType;
    }

    public void setMaintainType(String maintainType) {
        this.maintainType = maintainType;
    }

    public String getMaintainUid() {
        return maintainUid;
    }

    public void setMaintainUid(String maintainUid) {
        this.maintainUid = maintainUid;
    }

    public Date getMaintainDate() {
        return maintainDate;
    }

    public void setMaintainDate(Date maintainDate) {
        this.maintainDate = maintainDate;
    }

    public String getUpperUnno() {
        return upperUnno;
    }

    public void setUpperUnno(String upperUnno) {
        this.upperUnno = upperUnno;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
