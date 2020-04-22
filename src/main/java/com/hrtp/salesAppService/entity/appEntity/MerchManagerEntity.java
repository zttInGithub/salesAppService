package com.hrtp.salesAppService.entity.appEntity;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

/**
 * <p>商户入网</p>
 * @author zhq
 * @date 2018年8月24日
 */
public class MerchManagerEntity {
	private Integer bmid;				//商户主键
	private String unno;				//机构编号
	private String mid;					//商户编号
	private String rname;				//商户注册名称
	private String shortName;			//商户账单名称
	private String printName;			//凭条打印名称
	private String businessScope;		//经营范围
	private String email;				//电子邮箱
	private String areaCode;			//商户所在地区
	private String sendCode;			//上送区域码
	private Integer busid;				//业务人员
	private Integer maintainUserId;		//维护人员
	private Date joinConfirmDate;		//商户入网时间
	private Date checkConfirmDate;		//对账开通时间
	private String legalPerson;			//法人
	private String legalType;			//法人证件类型
	private String legalNum;			//法人证件号码
	private String legalUploadFileName;	//法人身份上传文件名
	private String bno;					//营业执照编号
	private String baddr;				//营业地址
	private String runaddr;				//注册地址
	private String province_name;		//安装地址(省)
	private String QX_name;				//安装地址(区县)
	private String naturalRate;			//D1手续费%
	private String secondRate;			//秒到手续费 
	private String cashRate;			//提现手续费率  工作日
	private String regMoney;			//注册资金
	private String rno;					//组织机构代码
	private String registryNo;			//税务登记证号
	private String materialNo;			//补充材料编号
	
	private String contractNo;			//合同编号
	private String contractPeriod;		//合同起止时间
	private String industryType;		//行业选择
	private String mccid;				//行业编码
	private Integer realtimeQueryTimes;	//实时交易查询次数
	private Integer settleCycle;		//结算周期
	private String settleRange;			//结算时间点
	private String settleMerger;		//节假日是否合并结账
	private String payBankId;			//支付系统行号
	private String bankFeeRate;			//银联卡费率
	private String feeAmt;				//封顶手续费
	private Integer merchantType;		//是否大小额商户
	private String dealAmt;				//封顶值
	private String accType;				//开户类型
	private String bankName;			//开户银行
	private String bankAccNo;			//开户银行账号
	private String bankAccName;			//开户账号名称
	private String bankType;			//开户银行是否为交通银行
	private String areaType;			//商户类型：1、标准  2、优惠类  3、减免 7、联刷
	private String bankArea;			//开户地
	private String contactAddress;		//联系地址
	private String contactPerson;		//联系人
	private String contactPhone;		//联系手机
	private String contactTel;			//联系固话
	private String processContext;		//受理描述
	private String accountStatus;		//账户状态
	private String settleStatus;		//结算状态
	private Integer maintainUid;		//操作人员
	private Date maintainDate;			//操作日期
	private String maintainType;		//变更类型
	private Integer approveUid;			//授权人员
	private Date approveDate;			//授权日期
	private String approveStatus;		//授权状态	Y-放行   Z-待放行  K-踢回  W-已添加终端
	private Integer isForeign;			//是否开通外卡
	private String feeRateV;			//外卡费率-visa
	private String feeRateM;			//外卡费率-master
	private String feeRateJ;			//外卡费率-jcb
	private String feeRateA;			//外卡费率-美运
	private String feeRateD;			//外卡费率-大莱
	private String parentMID;			//上级商户
	private Double deposit;				//押金
	private Double charge;				//服务费
	private String ip;                  //ip
	private Integer isM35;				//商户类型：1、手刷   2、个人 3、企业  4、优惠类 5、减免
	private String hybPhone;			//会员宝注册手机号
	private String remarks;				//备注
	private String legalExpdate;		//法人身份证有效期
	private String bnoExpdate;			//营业执照号有效期
	private String accNum;				//入账人身份账号
	private String accExpdate;			//入账人身份证有效期
	private String settMethod;			//入账方式
	private Integer authType;			//实名认证 0：未认证 1：认证失败 2：认证成功
	private String unitName;			//归属
	private String qrUrl;				//二维码图片链接
	private String qrUpload;			//二维码图片存放地址
	private Integer transterm;			//T+0转T+1结算周期
	private String dcashRate;			//提现费率 节假日
	private String secondLimit;			//秒到封顶值
	private String secondRate2;			//秒到手续费（大）
	private String settleMethod;			//D- 自然日，T- 工作日 
	private Integer ifCode;				//是否实码  1：实码  0：非实码
	
	private String creditBankRate;             //贷记卡费率
	private String creditFeeamt;             //贷记卡大额封顶手续费
	private String outsideBankRate;           //境外银联卡费率
	private String exchangTime;           //时间戳
	private String scanRate;				//微信扫码费率
	private String scanRate1;				//银联扫码费率
	private String scanRate2;				//支付宝扫码费率
	private String machineModel;
	private String preSetTime;			//定时时间 9,18	PRESETTIME
	private Double quotaAmt; 			//定额结算额度	QUOTAAMT
	private String nonConnection;			//非接审核状态
	private String conMccid;			//业务确认的mccid
	private String posBackImg;			//安装POS背面照片
	private String laborContractImg;	//入账人劳动合同照片
	private String sn;
	
	
	private MultipartFile legalUploadFile;
	private MultipartFile bupLoadFile;
	private MultipartFile rupLoadFile;
	private MultipartFile registryUpLoadFile;//店面门头照片(小微)
	private MultipartFile materialUpLoadFile;
	private MultipartFile photoUpLoadFile;//店内经营照片(小微)
	private MultipartFile bigdealUpLoadFile;//大协议照片(小微)
	private MultipartFile materialUpLoad1File;
	private MultipartFile materialUpLoad2File;//结算卡正面照片(小微)
	private MultipartFile materialUpLoad3File;//申请人身份证正面照片国徽(小微)
	private MultipartFile materialUpLoad4File;//申请人身份证反面照片头像(小微)
	private MultipartFile materialUpLoad5File;//申请人手持身份证照片(小微)
	private MultipartFile materialUpLoad6File;
	private MultipartFile materialUpLoad7File;
	private MultipartFile posBackImgFile;//SN照片(小微)
	private MultipartFile laborContractImgFile;
	private String province;
	private String city;
	private String userId;

	public String getScanRate1() {
		return scanRate1;
	}
	public void setScanRate1(String scanRate1) {
		this.scanRate1 = scanRate1;
	}
	public String getScanRate2() {
		return scanRate2;
	}
	public void setScanRate2(String scanRate2) {
		this.scanRate2 = scanRate2;
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
	public String getNonConnection() {
		return nonConnection;
	}
	public void setNonConnection(String nonConnection) {
		this.nonConnection = nonConnection;
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
	public void setMachineModel(String machineModel) {
		this.machineModel = machineModel;
	}
	public String getMachineModel() {
		return machineModel;
	}
	public String getScanRate() {
		return scanRate;
	}
	public void setScanRate(String scanRate) {
		this.scanRate = scanRate;
	}
	public String getExchangTime() {
		return exchangTime;
	}
	public void setExchangTime(String exchangTime) {
		this.exchangTime = exchangTime;
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
	
	public String getCreditBankRate() {
		return creditBankRate;
	}
	public void setCreditBankRate(String creditBankRate) {
		this.creditBankRate = creditBankRate;
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
	public String getNaturalRate() {
		return naturalRate;
	}
	public void setNaturalRate(String naturalRate) {
		this.naturalRate = naturalRate;
	}
	public String getProvince_name() {
		return province_name;
	}
	public void setProvince_name(String provinceName) {
		province_name = provinceName;
	}
	public String getQX_name() {
		return QX_name;
	}
	public void setQX_name(String qXName) {
		QX_name = qXName;
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
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
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
	public String getRegMoney() {
		return regMoney;
	}
	public void setRegMoney(String regMoney) {
		this.regMoney = regMoney;
	}
	
	public String getRno() {
		return rno;
	}
	public void setRno(String rno) {
		this.rno = rno;
	}
	
	public String getRegistryNo() {
		return registryNo;
	}
	public void setRegistryNo(String registryNo) {
		this.registryNo = registryNo;
	}
	public String getMaterialNo() {
		return materialNo;
	}
	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
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
	public Integer getMerchantType() {
		return merchantType;
	}
	public void setMerchantType(Integer merchantType) {
		this.merchantType = merchantType;
	}
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
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
	public String getIndustryType() {
		return industryType;
	}
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	public String getSendCode() {
		return sendCode;
	}
	public void setSendCode(String sendCode) {
		this.sendCode = sendCode;
	}
	public Integer getIsForeign() {
		return isForeign;
	}
	public void setIsForeign(Integer isForeign) {
		this.isForeign = isForeign;
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
	public String getDealAmt() {
		return dealAmt;
	}
	public void setDealAmt(String dealAmt) {
		this.dealAmt = dealAmt;
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
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public MultipartFile getLegalUploadFile() {
		return legalUploadFile;
	}
	public void setLegalUploadFile(MultipartFile legalUploadFile) {
		this.legalUploadFile = legalUploadFile;
	}
	public MultipartFile getBupLoadFile() {
		return bupLoadFile;
	}
	public void setBupLoadFile(MultipartFile bupLoadFile) {
		this.bupLoadFile = bupLoadFile;
	}
	public MultipartFile getRupLoadFile() {
		return rupLoadFile;
	}
	public void setRupLoadFile(MultipartFile rupLoadFile) {
		this.rupLoadFile = rupLoadFile;
	}
	public MultipartFile getRegistryUpLoadFile() {
		return registryUpLoadFile;
	}
	public void setRegistryUpLoadFile(MultipartFile registryUpLoadFile) {
		this.registryUpLoadFile = registryUpLoadFile;
	}
	public MultipartFile getMaterialUpLoadFile() {
		return materialUpLoadFile;
	}
	public void setMaterialUpLoadFile(MultipartFile materialUpLoadFile) {
		this.materialUpLoadFile = materialUpLoadFile;
	}
	public MultipartFile getPhotoUpLoadFile() {
		return photoUpLoadFile;
	}
	public void setPhotoUpLoadFile(MultipartFile photoUpLoadFile) {
		this.photoUpLoadFile = photoUpLoadFile;
	}
	public MultipartFile getBigdealUpLoadFile() {
		return bigdealUpLoadFile;
	}
	public void setBigdealUpLoadFile(MultipartFile bigdealUpLoadFile) {
		this.bigdealUpLoadFile = bigdealUpLoadFile;
	}
	public MultipartFile getMaterialUpLoad1File() {
		return materialUpLoad1File;
	}
	public void setMaterialUpLoad1File(MultipartFile materialUpLoad1File) {
		this.materialUpLoad1File = materialUpLoad1File;
	}
	public MultipartFile getMaterialUpLoad2File() {
		return materialUpLoad2File;
	}
	public void setMaterialUpLoad2File(MultipartFile materialUpLoad2File) {
		this.materialUpLoad2File = materialUpLoad2File;
	}
	public MultipartFile getMaterialUpLoad3File() {
		return materialUpLoad3File;
	}
	public void setMaterialUpLoad3File(MultipartFile materialUpLoad3File) {
		this.materialUpLoad3File = materialUpLoad3File;
	}
	public MultipartFile getMaterialUpLoad4File() {
		return materialUpLoad4File;
	}
	public void setMaterialUpLoad4File(MultipartFile materialUpLoad4File) {
		this.materialUpLoad4File = materialUpLoad4File;
	}
	public MultipartFile getMaterialUpLoad5File() {
		return materialUpLoad5File;
	}
	public void setMaterialUpLoad5File(MultipartFile materialUpLoad5File) {
		this.materialUpLoad5File = materialUpLoad5File;
	}
	public MultipartFile getMaterialUpLoad6File() {
		return materialUpLoad6File;
	}
	public void setMaterialUpLoad6File(MultipartFile materialUpLoad6File) {
		this.materialUpLoad6File = materialUpLoad6File;
	}
	public MultipartFile getMaterialUpLoad7File() {
		return materialUpLoad7File;
	}
	public void setMaterialUpLoad7File(MultipartFile materialUpLoad7File) {
		this.materialUpLoad7File = materialUpLoad7File;
	}
	public MultipartFile getPosBackImgFile() {
		return posBackImgFile;
	}
	public void setPosBackImgFile(MultipartFile posBackImgFile) {
		this.posBackImgFile = posBackImgFile;
	}
	public MultipartFile getLaborContractImgFile() {
		return laborContractImgFile;
	}
	public void setLaborContractImgFile(MultipartFile laborContractImgFile) {
		this.laborContractImgFile = laborContractImgFile;
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
