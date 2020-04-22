package com.hrtp.salesAppService.entity.appEntity;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

/**
 * 设备型号
 */
public class MerchantTwoUploadEntity{
	private Integer bmtuid;
	private String unno;
	private String mid;
	private String legalName;
	private MultipartFile legalNameFile;			//身份证正面照片
	private String legalName2;
	private MultipartFile legalName2File;			//身份证反面照片
	private String bupLoad;
	private MultipartFile bupLoadFile;				//营业执照照片
	private String bigdealUpLoad;
	private MultipartFile bigdealUpLoadFile;		//大协议照片
	private String laborContractImg;
	private MultipartFile laborContractImgFile;		//含银联云闪付标贴的店内经营照片
	private Date maintaindate;
	private String maintaintype;
	private Date approveDate;
	private String approveStatus;
	private String approveNote;
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	public Integer getBmtuid() {
		return bmtuid;
	}
	public void setBmtuid(Integer bmtuid) {
		this.bmtuid = bmtuid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getLegalName() {
		return legalName;
	}
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}
	public MultipartFile getLegalNameFile() {
		return legalNameFile;
	}
	public void setLegalNameFile(MultipartFile legalNameFile) {
		this.legalNameFile = legalNameFile;
	}
	public String getLegalName2() {
		return legalName2;
	}
	public void setLegalName2(String legalName2) {
		this.legalName2 = legalName2;
	}
	public MultipartFile getLegalName2File() {
		return legalName2File;
	}
	public void setLegalName2File(MultipartFile legalName2File) {
		this.legalName2File = legalName2File;
	}
	public String getBupLoad() {
		return bupLoad;
	}
	public void setBupLoad(String bupLoad) {
		this.bupLoad = bupLoad;
	}
	public MultipartFile getBupLoadFile() {
		return bupLoadFile;
	}
	public void setBupLoadFile(MultipartFile bupLoadFile) {
		this.bupLoadFile = bupLoadFile;
	}
	public String getBigdealUpLoad() {
		return bigdealUpLoad;
	}
	public void setBigdealUpLoad(String bigdealUpLoad) {
		this.bigdealUpLoad = bigdealUpLoad;
	}
	public MultipartFile getBigdealUpLoadFile() {
		return bigdealUpLoadFile;
	}
	public void setBigdealUpLoadFile(MultipartFile bigdealUpLoadFile) {
		this.bigdealUpLoadFile = bigdealUpLoadFile;
	}
	public String getLaborContractImg() {
		return laborContractImg;
	}
	public void setLaborContractImg(String laborContractImg) {
		this.laborContractImg = laborContractImg;
	}
	public MultipartFile getLaborContractImgFile() {
		return laborContractImgFile;
	}
	public void setLaborContractImgFile(MultipartFile laborContractImgFile) {
		this.laborContractImgFile = laborContractImgFile;
	}
	public Date getMaintaindate() {
		return maintaindate;
	}
	public void setMaintaindate(Date maintaindate) {
		this.maintaindate = maintaindate;
	}
	public String getMaintaintype() {
		return maintaintype;
	}
	public void setMaintaintype(String maintaintype) {
		this.maintaintype = maintaintype;
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
