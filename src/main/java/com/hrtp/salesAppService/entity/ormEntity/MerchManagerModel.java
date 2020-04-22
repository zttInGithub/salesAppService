package com.hrtp.salesAppService.entity.ormEntity;

import java.io.Serializable;
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
 * @date 2018年8月24日
 */
@Entity
@Table(name = "BILL_MERCHANTINFO")
public class MerchManagerModel implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "S_BILL_MERCHANTINFO1", sequenceName = "S_BILL_MERCHANTINFO",allocationSize = 1)
	@GeneratedValue(generator = "S_BILL_MERCHANTINFO1",strategy = GenerationType.SEQUENCE)
    @Column(name = "BMID")
	private Integer bmid;				//商户主键
	
	@Column(name = "UNNO")
	private String unno;				//机构编号
	
	@Column(name = "MID")
	private String mid;					//商户编号
	
	@Column(name = "RNAME")
	private String rname;				//商户注册名称
	
	@Column(name = "SHORTNAME")
	private String shortName;			//商户账单名称
	
	@Column(name = "PRINTNAME")
	private String printName;			//凭条打印名称
	
	@Column(name = "BUSINESSSCOPE")
	private String businessScope;		//经营范围
	
	@Column(name = "EMAIL")
	private String email;				//电子邮箱
	
	@Column(name = "AREA_CODE")
	private String areaCode;			//商户所在地区
	
	@Column(name = "SEND_CODE")
	private String sendCode;			//上送区域码
	
	@Column(name = "BUSID")
	private Integer busid;				//业务人员
	
	@Column(name = "MAINTAINUSERID")
	private Integer maintainUserId;		//维护人员
	
	@Column(name = "JOINCONFIRMDATE")
	private Date joinConfirmDate;		//商户入网时间
	
	@Column(name = "CHECKCONFIRMDATE")
	private Date checkConfirmDate;		//对账开通时间
	
	@Column(name = "LEGALPERSON")
	private String legalPerson;			//法人
	
	@Column(name = "LEGALTYPE")
	private String legalType;			//法人证件类型
	
	@Column(name = "LEGALNUM")
	private String legalNum;			//法人证件号码
	
	@Column(name = "LEGALUPLOADFILENAME")
	private String legalUploadFileName;	//法人身份上传文件名
	
	@Column(name = "BNO")
	private String bno;					//营业执照编号
	
	@Column(name = "BADDR")
	private String baddr;				//营业地址
	
	@Column(name = "RADDR")
	private String runaddr;				//注册地址
	
	@Column(name = "PROVINCE_NAME")
	private String province_name;		//安装地址(省)
	
	@Column(name = "QX_NAME")
	private String QX_name;				//安装地址(区县)
	
	@Column(name = "NATURALRATE")
	private String naturalRate;			//D1手续费%
	
	@Column(name = "SECONDRATE")
	private String secondRate;			//秒到手续费 
	
	@Column(name = "CASHRATE")
	private String cashRate;			//提现手续费率  工作日
	
	@Column(name = "REGMONEY")
	private String regMoney;			//注册资金
	
	@Column(name = "BUPLOAD")
	private String bupLoad;				//营业执照上传文件名
	
	@Column(name = "RNO")
	private String rno;					//组织机构代码
	
	@Column(name = "RUPLOAD")
	private String rupLoad;				//组织机构证上传文件名
	
	@Column(name = "REGISTRYNO")
	private String registryNo;			//税务登记证号
	
	@Column(name = "REGISTRYUPLOAD")
	private String registryUpLoad;		//税务登记证上传文件名
	
	@Column(name = "MATERIALNO")
	private String materialNo;			//补充材料编号
	
	@Column(name = "MATERIALUPLOAD")
	private String materialUpLoad;		//补充材料上传文件名
	
	@Column(name = "PHOTOUPLOAD")
	private String photoUpLoad;
	
	@Column(name = "BIGDEALUPLOAD")
	private String bigdealUpLoad;
	
	@Column(name = "MATERIALUPLOAD1")
	private String materialUpLoad1;
	
	@Column(name = "MATERIALUPLOAD2")
	private String materialUpLoad2;
	
	@Column(name = "MATERIALUPLOAD3")
	private String materialUpLoad3;
	
	@Column(name = "MATERIALUPLOAD4")
	private String materialUpLoad4;
	
	@Column(name = "MATERIALUPLOAD5")
	private String materialUpLoad5;
	
	@Column(name = "MATERIALUPLOAD6")
	private String materialUpLoad6;
	
	@Column(name = "MATERIALUPLOAD7")
	private String materialUpLoad7;
	
	@Column(name = "CONTRACTNO")
	private String contractNo;			//合同编号
	
	@Column(name = "CONTRACTPERIOD")
	private String contractPeriod;		//合同起止时间
	
	@Column(name = "INDUSTRYTYPE")
	private String industryType;		//行业选择
	
	@Column(name = "MCCID")
	private String mccid;				//行业编码
	
	@Column(name = "REALTIMEQUERYTIMES")
	private Integer realtimeQueryTimes;	//实时交易查询次数
	
	@Column(name = "SETTLECYCLE")
	private Integer settleCycle;		//结算周期
	
	@Column(name = "SETTLERANGE")
	private String settleRange;			//结算时间点
	
	@Column(name = "SETTLEMERGER")
	private String settleMerger;		//节假日是否合并结账
	
	@Column(name = "PAYBANKID")
	private String payBankId;			//支付系统行号
	
	@Column(name = "BANKFEERATE")
	private String bankFeeRate;			//银联卡费率
	
	@Column(name = "FEEAMT")
	private String feeAmt;				//封顶手续费
	
	@Column(name = "MERCHANTTYPE")
	private Integer merchantType;		//是否大小额商户
	
	@Column(name = "DEALAMT")
	private String dealAmt;				//封顶值
	
	@Column(name = "ACCTYPE")
	private String accType;				//开户类型
	
	@Column(name = "BANKBRANCH")
	private String bankname;			//开户银行
	
	@Column(name = "BANKACCNO")
	private String bankAccNo;			//开户银行账号
	
	@Column(name = "BANKACCNAME")
	private String bankAccName;			//开户账号名称
	
	@Column(name = "BANKTYPE")
	private String bankType;			//开户银行是否为交通银行
	
	@Column(name = "AREATYPE")
	private String areaType;			//商户类型：1、标准  2、优惠类  3、减免 7、联刷
	
	@Column(name = "BANKAREA")
	private String bankArea;			//开户地
	
	@Column(name = "CONTACTADDRESS")
	private String contactAddress;		//联系地址
	
	@Column(name = "CONTACTPERSON")
	private String contactPerson;		//联系人
	
	@Column(name = "CONTACTPHONE")
	private String contactPhone;		//联系手机
	
	@Column(name = "CONTACTTEL")
	private String contactTel;			//联系固话
	
	@Column(name = "PROCESSCONTEXT")
	private String processContext;		//受理描述
	
	@Column(name = "ACCOUNTSTATUS")
	private String accountStatus;		//账户状态
	
	@Column(name = "SETTLESTATUS")
	private String settleStatus;		//结算状态
	
	@Column(name = "MAINTAINUID")
	private Integer maintainUid;		//操作人员
	
	@Column(name = "MAINTAINDATE")
	private Date maintainDate;			//操作日期
	
	@Column(name = "MAINTAINTYPE")
	private String maintainType;		//变更类型
	
	@Column(name = "APPROVEUID")
	private Integer approveUid;			//授权人员
	
	@Column(name = "APPROVEDATE")
	private Date approveDate;			//授权日期
	
	@Column(name = "APPROVESTATUS")
	private String approveStatus;		//授权状态	Y-放行   Z-待放行  K-踢回  W-已添加终端
	
	@Column(name = "ISFOREIGN")
	private Integer isForeign;			//是否开通外卡
	
	@Column(name = "FEERATE_V")
	private String feeRateV;			//外卡费率-visa
	
	@Column(name = "FEERATE_M")
	private String feeRateM;			//外卡费率-master
	
	@Column(name = "FEERATE_J")
	private String feeRateJ;			//外卡费率-jcb
	
	@Column(name = "FEERATE_A")
	private String feeRateA;			//外卡费率-美运
	
	@Column(name = "FEERATE_D")
	private String feeRateD;			//外卡费率-大莱
	
	@Column(name = "PARENTMID")
	private String parentMID;			//上级商户
	
	@Column(name = "DEPOSIT")
	private Double deposit;				//押金
	
	@Column(name = "CHARGE")
	private Double charge;				//服务费
	
	@Column(name = "IP")
	private String ip;                  //ip
	
	@Column(name = "ISM35")
	private Integer isM35;				//商户类型：1、手刷   2、个人 3、企业  4、优惠类 5、减免
	
	@Column(name = "HYBPHONE")
	private String hybPhone;			//会员宝注册手机号
	
	@Column(name = "REMARKS")
	private String remarks;				//备注
	
	@Column(name = "LEGALEXPDATE")
	private String legalExpdate;		//法人身份证有效期
	
	@Column(name = "BNOEXPDATE")
	private String bnoExpdate;			//营业执照号有效期
	
	@Column(name = "ACCNUM")
	private String accNum;				//入账人身份账号
	
	@Column(name = "ACCEXPDATE")
	private String accExpdate;			//入账人身份证有效期
	
	@Column(name = "SETTMETHOD")
	private String settMethod;			//入账方式
	
	@Column(name = "AUTHTYPE")
	private Integer authType;			//实名认证 0：未认证 1：认证失败 2：认证成功
	
	@Column(name = "QR_URL")
	private String qrUrl;				//二维码图片链接
	
	@Column(name = "QR_UPLOAD")
	private String qrUpload;			//二维码图片存放地址
	
	@Column(name = "TRANSTERM")
	private Integer transterm;			//T+0转T+1结算周期
	
	@Column(name = "DCASHRATE")
	private String dcashRate;			//提现费率 节假日
	
	@Column(name = "SECONDLIMIT")
	private String secondLimit;			//秒到封顶值
	
	@Column(name = "SECONDRATE2")
	private String secondRate2;			//秒到手续费（大）
	
	@Column(name = "SETTLEMETHOD")
	private String settleMethod;			//D- 自然日，T- 工作日 
	
	@Column(name = "IFCODE")
	private Integer ifCode;				//是否实码  1：实码  0：非实码
	
	@Column(name = "CREDITBANKRATE")
	private String creditBankRate;             //贷记卡费率

	@Column(name = "CREDITFEEAMT")
	private String creditFeeamt;             //贷记卡大额封顶手续费
	
	@Column(name = "OUTSIDEBANKRATE")
	private String outsideBankRate;           //境外银联卡费率
	
	@Column(name = "EXCHANGE_TIME")
	private String exchangTime;           //时间戳
	
	@Column(name = "SCANRATE")
	private Double scanRate;				//微信扫码费率

	@Column(name = "SCANRATE1")
	private Double scanRate1;				//银联扫码费率

	@Column(name = "SCANRATE2")
	private Double scanRate2;				//支付宝扫码费率

	@Column(name = "PRESETTIME")
	private String preSetTime;			//定时时间 9,18	PRESETTIME
	
	@Column(name = "QUOTAAMT")
	private Double quotaAmt; 			//定额结算额度	QUOTAAMT
	
	@Column(name = "NONCONNECTION")
	private String nonConnection;			//非接审核状态
	
	@Column(name = "CONMCCID")
	private String conMccid;			//业务确认的mccid
	
	@Column(name = "POSBACKIMG")
	private String posBackImg;			//安装POS背面照片
	
	@Column(name = "LABORCONTRACTIMG")
	private String laborContractImg;	//入账人劳动合同照片

	public Double getScanRate1() {
		return scanRate1;
	}
	public void setScanRate1(Double scanRate1) {
		this.scanRate1 = scanRate1;
	}
	public Double getScanRate2() {
		return scanRate2;
	}
	public void setScanRate2(Double scanRate2) {
		this.scanRate2 = scanRate2;
	}
	public Integer getBmid() {
		return bmid;
	}
	public void setBmid(Integer bmid) {
		this.bmid = bmid;
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
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getPrintName() {
		return printName;
	}
	public void setPrintName(String printName) {
		this.printName = printName;
	}
	public String getBusinessScope() {
		return businessScope;
	}
	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getSendCode() {
		return sendCode;
	}
	public void setSendCode(String sendCode) {
		this.sendCode = sendCode;
	}
	public Integer getBusid() {
		return busid;
	}
	public void setBusid(Integer busid) {
		this.busid = busid;
	}
	public Integer getMaintainUserId() {
		return maintainUserId;
	}
	public void setMaintainUserId(Integer maintainUserId) {
		this.maintainUserId = maintainUserId;
	}
	public Date getJoinConfirmDate() {
		return joinConfirmDate;
	}
	public void setJoinConfirmDate(Date joinConfirmDate) {
		this.joinConfirmDate = joinConfirmDate;
	}
	public Date getCheckConfirmDate() {
		return checkConfirmDate;
	}
	public void setCheckConfirmDate(Date checkConfirmDate) {
		this.checkConfirmDate = checkConfirmDate;
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
	public String getLegalUploadFileName() {
		return legalUploadFileName;
	}
	public void setLegalUploadFileName(String legalUploadFileName) {
		this.legalUploadFileName = legalUploadFileName;
	}
	public String getBno() {
		return bno;
	}
	public void setBno(String bno) {
		this.bno = bno;
	}
	public String getBaddr() {
		return baddr;
	}
	public void setBaddr(String baddr) {
		this.baddr = baddr;
	}
	public String getRunaddr() {
		return runaddr;
	}
	public void setRunaddr(String runaddr) {
		this.runaddr = runaddr;
	}
	public String getProvince_name() {
		return province_name;
	}
	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}
	public String getQX_name() {
		return QX_name;
	}
	public void setQX_name(String qX_name) {
		QX_name = qX_name;
	}
	public String getNaturalRate() {
		return naturalRate;
	}
	public void setNaturalRate(String naturalRate) {
		this.naturalRate = naturalRate;
	}
	public String getSecondRate() {
		return secondRate;
	}
	public void setSecondRate(String secondRate) {
		this.secondRate = secondRate;
	}
	public String getCashRate() {
		return cashRate;
	}
	public void setCashRate(String cashRate) {
		this.cashRate = cashRate;
	}
	public String getRegMoney() {
		return regMoney;
	}
	public void setRegMoney(String regMoney) {
		this.regMoney = regMoney;
	}
	public String getBupLoad() {
		return bupLoad;
	}
	public void setBupLoad(String bupLoad) {
		this.bupLoad = bupLoad;
	}
	public String getRno() {
		return rno;
	}
	public void setRno(String rno) {
		this.rno = rno;
	}
	public String getRupLoad() {
		return rupLoad;
	}
	public void setRupLoad(String rupLoad) {
		this.rupLoad = rupLoad;
	}
	public String getRegistryNo() {
		return registryNo;
	}
	public void setRegistryNo(String registryNo) {
		this.registryNo = registryNo;
	}
	public String getRegistryUpLoad() {
		return registryUpLoad;
	}
	public void setRegistryUpLoad(String registryUpLoad) {
		this.registryUpLoad = registryUpLoad;
	}
	public String getMaterialNo() {
		return materialNo;
	}
	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}
	public String getMaterialUpLoad() {
		return materialUpLoad;
	}
	public void setMaterialUpLoad(String materialUpLoad) {
		this.materialUpLoad = materialUpLoad;
	}
	public String getPhotoUpLoad() {
		return photoUpLoad;
	}
	public void setPhotoUpLoad(String photoUpLoad) {
		this.photoUpLoad = photoUpLoad;
	}
	public String getBigdealUpLoad() {
		return bigdealUpLoad;
	}
	public void setBigdealUpLoad(String bigdealUpLoad) {
		this.bigdealUpLoad = bigdealUpLoad;
	}
	public String getMaterialUpLoad1() {
		return materialUpLoad1;
	}
	public void setMaterialUpLoad1(String materialUpLoad1) {
		this.materialUpLoad1 = materialUpLoad1;
	}
	public String getMaterialUpLoad2() {
		return materialUpLoad2;
	}
	public void setMaterialUpLoad2(String materialUpLoad2) {
		this.materialUpLoad2 = materialUpLoad2;
	}
	public String getMaterialUpLoad3() {
		return materialUpLoad3;
	}
	public void setMaterialUpLoad3(String materialUpLoad3) {
		this.materialUpLoad3 = materialUpLoad3;
	}
	public String getMaterialUpLoad4() {
		return materialUpLoad4;
	}
	public void setMaterialUpLoad4(String materialUpLoad4) {
		this.materialUpLoad4 = materialUpLoad4;
	}
	public String getMaterialUpLoad5() {
		return materialUpLoad5;
	}
	public void setMaterialUpLoad5(String materialUpLoad5) {
		this.materialUpLoad5 = materialUpLoad5;
	}
	public String getMaterialUpLoad6() {
		return materialUpLoad6;
	}
	public void setMaterialUpLoad6(String materialUpLoad6) {
		this.materialUpLoad6 = materialUpLoad6;
	}
	public String getMaterialUpLoad7() {
		return materialUpLoad7;
	}
	public void setMaterialUpLoad7(String materialUpLoad7) {
		this.materialUpLoad7 = materialUpLoad7;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getContractPeriod() {
		return contractPeriod;
	}
	public void setContractPeriod(String contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
	public String getIndustryType() {
		return industryType;
	}
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	public String getMccid() {
		return mccid;
	}
	public void setMccid(String mccid) {
		this.mccid = mccid;
	}
	public Integer getRealtimeQueryTimes() {
		return realtimeQueryTimes;
	}
	public void setRealtimeQueryTimes(Integer realtimeQueryTimes) {
		this.realtimeQueryTimes = realtimeQueryTimes;
	}
	public Integer getSettleCycle() {
		return settleCycle;
	}
	public void setSettleCycle(Integer settleCycle) {
		this.settleCycle = settleCycle;
	}
	public String getSettleRange() {
		return settleRange;
	}
	public void setSettleRange(String settleRange) {
		this.settleRange = settleRange;
	}
	public String getSettleMerger() {
		return settleMerger;
	}
	public void setSettleMerger(String settleMerger) {
		this.settleMerger = settleMerger;
	}
	public String getPayBankId() {
		return payBankId;
	}
	public void setPayBankId(String payBankId) {
		this.payBankId = payBankId;
	}
	public String getBankFeeRate() {
		return bankFeeRate;
	}
	public void setBankFeeRate(String bankFeeRate) {
		this.bankFeeRate = bankFeeRate;
	}
	public String getFeeAmt() {
		return feeAmt;
	}
	public void setFeeAmt(String feeAmt) {
		this.feeAmt = feeAmt;
	}
	public Integer getMerchantType() {
		return merchantType;
	}
	public void setMerchantType(Integer merchantType) {
		this.merchantType = merchantType;
	}
	public String getDealAmt() {
		return dealAmt;
	}
	public void setDealAmt(String dealAmt) {
		this.dealAmt = dealAmt;
	}
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getBankAccNo() {
		return bankAccNo;
	}
	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}
	public String getBankAccName() {
		return bankAccName;
	}
	public void setBankAccName(String bankAccName) {
		this.bankAccName = bankAccName;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public String getAreaType() {
		return areaType;
	}
	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}
	public String getBankArea() {
		return bankArea;
	}
	public void setBankArea(String bankArea) {
		this.bankArea = bankArea;
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
	public String getProcessContext() {
		return processContext;
	}
	public void setProcessContext(String processContext) {
		this.processContext = processContext;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getSettleStatus() {
		return settleStatus;
	}
	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}
	public Integer getMaintainUid() {
		return maintainUid;
	}
	public void setMaintainUid(Integer maintainUid) {
		this.maintainUid = maintainUid;
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
	public Integer getApproveUid() {
		return approveUid;
	}
	public void setApproveUid(Integer approveUid) {
		this.approveUid = approveUid;
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
	public Integer getIsForeign() {
		return isForeign;
	}
	public void setIsForeign(Integer isForeign) {
		this.isForeign = isForeign;
	}
	public String getFeeRateV() {
		return feeRateV;
	}
	public void setFeeRateV(String feeRateV) {
		this.feeRateV = feeRateV;
	}
	public String getFeeRateM() {
		return feeRateM;
	}
	public void setFeeRateM(String feeRateM) {
		this.feeRateM = feeRateM;
	}
	public String getFeeRateJ() {
		return feeRateJ;
	}
	public void setFeeRateJ(String feeRateJ) {
		this.feeRateJ = feeRateJ;
	}
	public String getFeeRateA() {
		return feeRateA;
	}
	public void setFeeRateA(String feeRateA) {
		this.feeRateA = feeRateA;
	}
	public String getFeeRateD() {
		return feeRateD;
	}
	public void setFeeRateD(String feeRateD) {
		this.feeRateD = feeRateD;
	}
	public String getParentMID() {
		return parentMID;
	}
	public void setParentMID(String parentMID) {
		this.parentMID = parentMID;
	}
	public Double getDeposit() {
		return deposit;
	}
	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}
	public Double getCharge() {
		return charge;
	}
	public void setCharge(Double charge) {
		this.charge = charge;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getIsM35() {
		return isM35;
	}
	public void setIsM35(Integer isM35) {
		this.isM35 = isM35;
	}
	public String getHybPhone() {
		return hybPhone;
	}
	public void setHybPhone(String hybPhone) {
		this.hybPhone = hybPhone;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getLegalExpdate() {
		return legalExpdate;
	}
	public void setLegalExpdate(String legalExpdate) {
		this.legalExpdate = legalExpdate;
	}
	public String getBnoExpdate() {
		return bnoExpdate;
	}
	public void setBnoExpdate(String bnoExpdate) {
		this.bnoExpdate = bnoExpdate;
	}
	public String getAccNum() {
		return accNum;
	}
	public void setAccNum(String accNum) {
		this.accNum = accNum;
	}
	public String getAccExpdate() {
		return accExpdate;
	}
	public void setAccExpdate(String accExpdate) {
		this.accExpdate = accExpdate;
	}
	public String getSettMethod() {
		return settMethod;
	}
	public void setSettMethod(String settMethod) {
		this.settMethod = settMethod;
	}
	public Integer getAuthType() {
		return authType;
	}
	public void setAuthType(Integer authType) {
		this.authType = authType;
	}
	public String getQrUrl() {
		return qrUrl;
	}
	public void setQrUrl(String qrUrl) {
		this.qrUrl = qrUrl;
	}
	public String getQrUpload() {
		return qrUpload;
	}
	public void setQrUpload(String qrUpload) {
		this.qrUpload = qrUpload;
	}
	public Integer getTransterm() {
		return transterm;
	}
	public void setTransterm(Integer transterm) {
		this.transterm = transterm;
	}
	public String getDcashRate() {
		return dcashRate;
	}
	public void setDcashRate(String dcashRate) {
		this.dcashRate = dcashRate;
	}
	public String getSecondLimit() {
		return secondLimit;
	}
	public void setSecondLimit(String secondLimit) {
		this.secondLimit = secondLimit;
	}
	public String getSecondRate2() {
		return secondRate2;
	}
	public void setSecondRate2(String secondRate2) {
		this.secondRate2 = secondRate2;
	}
	public String getSettleMethod() {
		return settleMethod;
	}
	public void setSettleMethod(String settleMethod) {
		this.settleMethod = settleMethod;
	}
	public Integer getIfCode() {
		return ifCode;
	}
	public void setIfCode(Integer ifCode) {
		this.ifCode = ifCode;
	}
	public String getCreditBankRate() {
		return creditBankRate;
	}
	public void setCreditBankRate(String creditBankRate) {
		this.creditBankRate = creditBankRate;
	}
	public String getCreditFeeamt() {
		return creditFeeamt;
	}
	public void setCreditFeeamt(String creditFeeamt) {
		this.creditFeeamt = creditFeeamt;
	}
	public String getOutsideBankRate() {
		return outsideBankRate;
	}
	public void setOutsideBankRate(String outsideBankRate) {
		this.outsideBankRate = outsideBankRate;
	}
	public String getExchangTime() {
		return exchangTime;
	}
	public void setExchangTime(String exchangTime) {
		this.exchangTime = exchangTime;
	}
	public Double getScanRate() {
		return scanRate;
	}
	public void setScanRate(Double scanRate) {
		this.scanRate = scanRate;
	}
	public String getPreSetTime() {
		return preSetTime;
	}
	public void setPreSetTime(String preSetTime) {
		this.preSetTime = preSetTime;
	}
	public Double getQuotaAmt() {
		return quotaAmt;
	}
	public void setQuotaAmt(Double quotaAmt) {
		this.quotaAmt = quotaAmt;
	}
	public String getNonConnection() {
		return nonConnection;
	}
	public void setNonConnection(String nonConnection) {
		this.nonConnection = nonConnection;
	}
	public String getConMccid() {
		return conMccid;
	}
	public void setConMccid(String conMccid) {
		this.conMccid = conMccid;
	}
	public String getPosBackImg() {
		return posBackImg;
	}
	public void setPosBackImg(String posBackImg) {
		this.posBackImg = posBackImg;
	}
	public String getLaborContractImg() {
		return laborContractImg;
	}
	public void setLaborContractImg(String laborContractImg) {
		this.laborContractImg = laborContractImg;
	}
	
	
}