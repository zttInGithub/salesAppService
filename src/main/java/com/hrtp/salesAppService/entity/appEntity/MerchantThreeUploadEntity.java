package com.hrtp.salesAppService.entity.appEntity;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

/**
 * 设备型号
 */
public class MerchantThreeUploadEntity{
	private Integer bmthId;
	private String unno;
	private String mid;
	private String tid;
	private String sn;
	private String license_Name;
	private String r_Name;
	private String r_Addr;
	private Integer isConnect;						//是否支持非接：0否，1是
	private Integer isImmunity;						//是否支持双免：0否，1是
	private Integer isUnionPay;						//是否支持银联二维码：0否，1是
	private MultipartFile merUpload1File;			//非接交易小票
	private MultipartFile merUpload2File;			//银联二维码交易小票
	private MultipartFile merUpload3File;			//POS机反面照片
	private MultipartFile merUpload4File;			//门店照片
	private MultipartFile merUpload5File;			//店内经营照片
	private MultipartFile merUpload6File;			//云闪付标识照片
	private MultipartFile merUpload7File;			//银联标识照片
	private MultipartFile merUpload8File;			//非接改造操作视频
	private Date maintainDate;
	private String maintainType;
	private Date approveDate;
	private String approveStatus;
	private String approveNote;						//退回原因
	public Integer getBmthId() {
		return bmthId;
	}
	public void setBmthId(Integer bmthId) {
		this.bmthId = bmthId;
	}
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getLicense_Name() {
		return license_Name;
	}
	public void setLicense_Name(String license_Name) {
		this.license_Name = license_Name;
	}
	public String getR_Name() {
		return r_Name;
	}
	public void setR_Name(String r_Name) {
		this.r_Name = r_Name;
	}
	public String getR_Addr() {
		return r_Addr;
	}
	public void setR_Addr(String r_Addr) {
		this.r_Addr = r_Addr;
	}
	public Integer getIsConnect() {
		return isConnect;
	}
	public void setIsConnect(Integer isConnect) {
		this.isConnect = isConnect;
	}
	public Integer getIsImmunity() {
		return isImmunity;
	}
	public void setIsImmunity(Integer isImmunity) {
		this.isImmunity = isImmunity;
	}
	public Integer getIsUnionPay() {
		return isUnionPay;
	}
	public void setIsUnionPay(Integer isUnionPay) {
		this.isUnionPay = isUnionPay;
	}
	public MultipartFile getMerUpload1File() {
		return merUpload1File;
	}
	public void setMerUpload1File(MultipartFile merUpload1File) {
		this.merUpload1File = merUpload1File;
	}
	public MultipartFile getMerUpload2File() {
		return merUpload2File;
	}
	public void setMerUpload2File(MultipartFile merUpload2File) {
		this.merUpload2File = merUpload2File;
	}
	public MultipartFile getMerUpload3File() {
		return merUpload3File;
	}
	public void setMerUpload3File(MultipartFile merUpload3File) {
		this.merUpload3File = merUpload3File;
	}
	public MultipartFile getMerUpload4File() {
		return merUpload4File;
	}
	public void setMerUpload4File(MultipartFile merUpload4File) {
		this.merUpload4File = merUpload4File;
	}
	public MultipartFile getMerUpload5File() {
		return merUpload5File;
	}
	public void setMerUpload5File(MultipartFile merUpload5File) {
		this.merUpload5File = merUpload5File;
	}
	public MultipartFile getMerUpload6File() {
		return merUpload6File;
	}
	public void setMerUpload6File(MultipartFile merUpload6File) {
		this.merUpload6File = merUpload6File;
	}
	public MultipartFile getMerUpload7File() {
		return merUpload7File;
	}
	public void setMerUpload7File(MultipartFile merUpload7File) {
		this.merUpload7File = merUpload7File;
	}
	public MultipartFile getMerUpload8File() {
		return merUpload8File;
	}
	public void setMerUpload8File(MultipartFile merUpload8File) {
		this.merUpload8File = merUpload8File;
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
