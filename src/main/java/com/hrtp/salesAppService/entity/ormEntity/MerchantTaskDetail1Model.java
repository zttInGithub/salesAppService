package com.hrtp.salesAppService.entity.ormEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "BILL_MERCHANTTASKDETAIL1")
public class MerchantTaskDetail1Model  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "S_BILL_MERCHANTTASKDETAIL11", sequenceName = "S_BILL_MERCHANTTASKDETAIL1",allocationSize = 1)
	@GeneratedValue(generator = "S_BILL_MERCHANTTASKDETAIL11",strategy = GenerationType.SEQUENCE)
    @Column(name = "BT1ID")
	private Integer bt1id;							// 修改商户信息唯一主键
	
	@Column(name = "BMTKID")
	private Integer bmtkid;							//所关联“待审核”主键
	
	@Column(name = "BUSID")
	private Integer busid;							//所属销售
	
	@Column(name = "RName")
	private String rname;			 				//商户全称
	
	@Column(name = "LEGALPERSON")
	private String legalPerson;						//法人
	
	@Column(name = "LEGALTYPE")
	private String legalType;						//法人证件类型
	
	@Column(name = "LEGALNUM")
	private String legalNum;						//法人证件号码
	
	@Column(name = "LEGALEXPDATE")
	private String legalExpDate;					//法人证件有效期
	
	@Column(name = "CONTACTADDRESS")
	private String contactAddress;					//联系地址
	
	@Column(name = "CONTACTPERSON")
	private String contactPerson;					//联系人
	
	@Column(name = "CONTACTPHONE")
	private String contactPhone;					//联系手机
	
	@Column(name = "CONTACTTEL")
	private String contactTel; 	 					//联系固话
	
	@Column(name = "LEGALUPLOADFILENAME")
	private String legalUploadFileName;				//法人身份上传文件名
	
	@Column(name = "BUPLOAD")
	private String bupload;							//营业执照上传文件名
	
	@Column(name = "RUPLOAD")
	private String rupload;							//组织结构证上传文件名
	
	@Column(name = "REGISTRYUPLOAD")
	private String registryUpLoad;					//税务登记证上传文件
	
	@Column(name = "MATERIALUPLOAD")
	private String materialUpLoad;					//补充材料上传文件名
	
	
	public Integer getBt1id() {
		return bt1id; 
	}
	public void setBt1id(Integer bt1id) {
		this.bt1id = bt1id;
	}
	public Integer getBusid() {
		return busid;
	}
	public void setBusid(Integer busid) {
		this.busid = busid;
	}
	
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
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
	public String getLegalExpDate() {
		return legalExpDate;
	}
	public void setLegalExpDate(String legalExpDate) {
		this.legalExpDate = legalExpDate;
	}
	public String getContactAddress() {
		return contactAddress;
	}
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContactTel() {
		return contactTel;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	public Integer getBmtkid() {
		return bmtkid;
	}
	public void setBmtkid(Integer bmtkid) {
		this.bmtkid = bmtkid;
	}
	public String getLegalUploadFileName() {
		return legalUploadFileName;
	}
	public void setLegalUploadFileName(String legalUploadFileName) {
		this.legalUploadFileName = legalUploadFileName;
	}

	public String getBupload() {
		return bupload;
	}
	public void setBupload(String bupload) {
		this.bupload = bupload;
	}
	public String getRupload() {
		return rupload;
	}
	public void setRupload(String rupload) {
		this.rupload = rupload;
	}
	public String getRegistryUpLoad() {
		return registryUpLoad;
	}
	public void setRegistryUpLoad(String registryUpLoad) {
		this.registryUpLoad = registryUpLoad;
	}
	public String getMaterialUpLoad() {
		return materialUpLoad;
	}
	public void setMaterialUpLoad(String materialUpLoad) {
		this.materialUpLoad = materialUpLoad;
	}
	public MerchantTaskDetail1Model() {
		super();
	}
}
