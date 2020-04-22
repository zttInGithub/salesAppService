package com.hrtp.salesAppService.entity.ormEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 * <p>Description: </p>
 * @author zhq
 * @date 2018年8月27日
 */
@Entity
@Table(name = "BILL_MIDSEQINFO")
public class MIDSeqInfoModel implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	//TODO 在MerchManagerService涉及到save表的时候要加@SequenceGenerator和@GeneratedValue注解 通过GeneratedValue的值找到SequenceGenerator的name值 再找到序列的值
	@Id
	@SequenceGenerator(name = "S_BILL_MIDSEQINFO1", sequenceName = "S_BILL_MIDSEQINFO",allocationSize = 1)
	@GeneratedValue(generator = "S_BILL_MIDSEQINFO1",strategy = GenerationType.SEQUENCE)
    @Column(name = "BMSID")
	private Integer bmsid;			//主键
	@Column(name = "AREA_CODE")
	private String areaCode;		//区域编码
	@Column(name = "MCCID")
	private String mccid;			//MCC编码
	@Column(name = "SEQNO")			//TODO 不能写成SeqNo  这样在查询SELECT m FROM MIDSeqInfoModel的时候 默认查询字段 sql_no会报错
	private Integer seqNo;			//流水号
	@Column(name = "Minfo1")
	private String minfo1;			//扩展字段1
	@Column(name = "Minfo2")
	private String minfo2;			//扩展字段2
	@Column(name = "CREATE_DATE")
	private Date createDate;	//创建日期
	@Column(name = "CREATE_USER")
	private String createUser;		//创建人
	public MIDSeqInfoModel() {
	}
	
	public MIDSeqInfoModel(Integer seqNo) {
		super();
		this.seqNo = seqNo;
	}
	public Integer getBmsid() {
		return bmsid;
	}
	public void setBmsid(Integer bmsid) {
		this.bmsid = bmsid;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getMccid() {
		return mccid;
	}
	public void setMccid(String mccid) {
		this.mccid = mccid;
	}
	public Integer getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}
	public String getMinfo1() {
		return minfo1;
	}
	public void setMinfo1(String minfo1) {
		this.minfo1 = minfo1;
	}
	public String getMinfo2() {
		return minfo2;
	}
	public void setMinfo2(String minfo2) {
		this.minfo2 = minfo2;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
}
