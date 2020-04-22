package com.hrtp.salesAppService.service;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.hrtp.system.costant.SysParam;
import com.hrtp.system.util.PictureWaterMarkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.hrtp.salesAppService.dao.MerchManagerRepository;
import com.hrtp.salesAppService.dao.MerchantBankRepository;
import com.hrtp.salesAppService.dao.MidSeqInfoRepository;
import com.hrtp.salesAppService.dao.SysParamRepository;
import com.hrtp.salesAppService.entity.appEntity.MerchManagerEntity;
import com.hrtp.salesAppService.entity.appEntity.MerchantInfoEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.ormEntity.MIDSeqInfoModel;
import com.hrtp.salesAppService.entity.ormEntity.MerchManagerModel;
import com.hrtp.salesAppService.entity.ormEntity.MerchantBankCardModel;
import com.hrtp.salesAppService.entity.ormEntity.SysParamModel;
import com.hrtp.salesAppService.exception.AppException;
import com.hrtp.system.common.GenericJpaDao;
import com.hrtp.system.costant.ReturnCode;

/**
 * <p>商户入网管理</p>
 * @date 2018年8月24日
 */
@Service
public class MerchManagerService {
	
	 private static Logger logger = LoggerFactory.getLogger(MerchManagerService.class);
	 @Autowired
	 private MerchManagerRepository merchManagerRepository;
	 @Autowired
	 private MidSeqInfoRepository midSeqInfoRepository;
	 @Autowired
	 private MerchantBankRepository merchantBankRepository;
	 @Autowired
	 private SysParamRepository sysParamRepository;
	 @Autowired
	 private GenericJpaDao genericJpaDao;
	 
	 /**
	 * <p>查询销售</p>
	 * @author zhq
	 */
	public RespEntity searchAgentSales(MerchManagerEntity merchManagerEntity) {
		List<Map<String,String>>list= merchManagerRepository.searchAgentSales(merchManagerEntity.getUnno());
		return new RespEntity(ReturnCode.SUCCESS, "查询成功", list);
	}
	 
 	/**
	 * <p>查询行业编码 </p>
	 * @author zhq
	 */
	public RespEntity searchMCC() {
		List<Map<String,String>>list= merchManagerRepository.searchMCC();
		return new RespEntity(ReturnCode.SUCCESS, "查询成功", list);
	}
	
	/**
	 * <p>查询市级code </p>
	 * @author zhq
	 */
	public RespEntity searchAreaCode(MerchManagerEntity merchManagerEntity) {
		List<Map<String,String>>list= merchManagerRepository.searchAreaCode(merchManagerEntity.getProvince());
		return new RespEntity(ReturnCode.SUCCESS, "查询成功", list);
	}
	 
	 /**
	  * <p>小微商户入驻 </p>
	  * @author zhq
	  * 
	  */
	public RespEntity update(MerchManagerEntity merchManagerEntity) {
		try {
//			Controller上加了限制上传文件的大小
//			String res= ifbeyondMax(merchManagerEntity);
//			if(res.length()>1){
//				return new RespEntity(ReturnCode.FALT,res);
//			}
			if(StringUtils.isEmpty(merchManagerEntity.getMccid())){
				return new RespEntity(ReturnCode.FALT,"行业编码不能为空");
			}
			if(StringUtils.isEmpty(merchManagerEntity.getUnno())){
				return new RespEntity(ReturnCode.FALT,"机构号不能为空");
			}
			if(StringUtils.isEmpty(merchManagerEntity.getBusid())){
				return new RespEntity(ReturnCode.FALT,"销售不能为空");
			}
			if(StringUtils.isEmpty(merchManagerEntity.getLegalNum())){
				return new RespEntity(ReturnCode.FALT,"身份证不能为空");
			}
//			if(StringUtils.isEmpty(merchManagerEntity.getProvince())||StringUtils.isEmpty(merchManagerEntity.getCity())||StringUtils.isEmpty(merchManagerEntity.getRunaddr())){
//				return new RespEntity(ReturnCode.FALT,"经营地址不能为空");
//			}
			if(StringUtils.isEmpty(merchManagerEntity.getBankFeeRate())){
				return new RespEntity(ReturnCode.FALT,"借记卡费率不能为空");
			}
			if(StringUtils.isEmpty(merchManagerEntity.getFeeAmt())){
				return new RespEntity(ReturnCode.FALT,"借记卡封顶手续费不能为空");
			}
			if(StringUtils.isEmpty(merchManagerEntity.getBankName())){
				return new RespEntity(ReturnCode.FALT,"开户行名称不能为空");
			}
			if(StringUtils.isEmpty(merchManagerEntity.getBankAccNo())){
				return new RespEntity(ReturnCode.FALT,"结算账号不能为空");
			}
			if(StringUtils.isEmpty(merchManagerEntity.getBankAccName())){
				return new RespEntity(ReturnCode.FALT,"申请人姓名不能为空");
			}
			if(StringUtils.isEmpty(merchManagerEntity.getContactAddress())){
				return new RespEntity(ReturnCode.FALT,"职务不能为空");
			}
			if(StringUtils.isEmpty(merchManagerEntity.getContactPerson())){
				return new RespEntity(ReturnCode.FALT,"联系人不能为空");
			}
			if(StringUtils.isEmpty(merchManagerEntity.getRname())){
				return new RespEntity(ReturnCode.FALT,"商户名字不能为空");
			}
			if(StringUtils.isEmpty(merchManagerEntity.getIsM35())){
				return new RespEntity(ReturnCode.FALT,"商户属性不能为空！");
			}
			if(StringUtils.isEmpty(merchManagerEntity.getRunaddr())){
				return new RespEntity(ReturnCode.FALT,"经营地址不能为空！");
			}

			String tname=merchManagerEntity.getRname();
			String bandAccNo=merchManagerEntity.getBankAccNo();
			String accNum=merchManagerEntity.getAccNum();
			List<Object[]>list0=merchManagerRepository.queryIsHotMerch(tname,bandAccNo,accNum);
			if(list0.size()>0){
				return new RespEntity(ReturnCode.FALT,"该户在黑名单商户中，请核查！");
			}
			
//			String tt=merchManagerEntity.getBankName();
//			if(tt.indexOf("青海")>=0 || tt.indexOf("西藏")>=0 || tt.indexOf("新疆")>=0 ){
//				return new RespEntity(ReturnCode.FALT,"不支持此开户行！");
//			}
			
			List<Object[]>list01=merchManagerRepository.searchMCCById(merchManagerEntity.getMccid());
			if(list01.size()<0){
				return new RespEntity(ReturnCode.FALT,"行业编码不存在！");
			}
			
			MerchManagerModel mManagerModel=new MerchManagerModel();
			BeanUtils.copyProperties(merchManagerEntity, mManagerModel);
			if(merchManagerEntity.getIsM35()==null){
				mManagerModel.setIsM35(0);
			}else if(merchManagerEntity.getIsM35()==2||merchManagerEntity.getIsM35()==3){
				mManagerModel.setAreaType("1");
			}else if(merchManagerEntity.getIsM35()==4){
				mManagerModel.setAreaType("2");
			}else if(merchManagerEntity.getIsM35()==5){
				mManagerModel.setAreaType("3");
			}
			mManagerModel.setBusinessScope(String.valueOf(list01.get(0)[1]));//行业名称
			String areaCode="011";//北京商户的地区码  4-8位
			mManagerModel.setAreaCode(areaCode);
			//结算方式（是否秒到，传统商户默认：000000）
			mManagerModel.setSettMethod("000000");
			mManagerModel.setSettleMethod("T");
			mManagerModel.setRealtimeQueryTimes(20);
			//合同起始时间
			mManagerModel.setContractPeriod(new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString());
			//结算周期
			if(merchManagerEntity.getSettleCycle()==null||"".equals(merchManagerEntity.getSettleCycle())){
				mManagerModel.setSettleCycle(1);
			}
			if (!StringUtils.isEmpty(merchManagerEntity.getSettleRange())){
				mManagerModel.setSettleRange(merchManagerEntity.getSettleRange());
			}
			//节假日是否合并结账 0：否 1：是
			mManagerModel.setSettleMerger("1");
			//营业地址拼接
			mManagerModel.setRunaddr(merchManagerEntity.getRunaddr());
			//商户账单名称为空则和商户注册名称一样
			if(StringUtils.isEmpty(merchManagerEntity.getShortName())){
				mManagerModel.setShortName(merchManagerEntity.getRname());
			}
			
			//凭条打印名称为空泽合商户注册名称一样
			if(StringUtils.isEmpty(merchManagerEntity.getPrintName())){
				mManagerModel.setPrintName(merchManagerEntity.getRname());
			}
			
			StringBuffer bank = new StringBuffer();
			if(merchManagerEntity.getBankName()!=null&&!"".equals(merchManagerEntity.getBankName())&&!"其他".equals(merchManagerEntity.getBankName().trim())){
				bank.append(merchManagerEntity.getBankName());
			}
			//判断开户银行中是否有交通银行几个字
			if(bank.toString().indexOf("交通银行") == -1){
				mManagerModel.setBankType("2");		//非交通银行
			}else{
				mManagerModel.setBankType("1");		//交通银行
			}
			mManagerModel.setApproveStatus("Z");		//默认为待放行 Y-放行   Z-待放行  K-踢回
			mManagerModel.setMaintainUserId(merchManagerEntity.getBusid());//添加的时候业务人员和维护人员一样
			
			//小微商户默认值
			if(merchManagerEntity.getIsM35()==2) {
				mManagerModel.setBno("未知");
				mManagerModel.setLegalPerson(merchManagerEntity.getBankAccName());//法人名称
				mManagerModel.setIsM35(2);
				mManagerModel.setAccType("2");
				mManagerModel.setLegalType("1");//证件类型
				mManagerModel.setAccNum(merchManagerEntity.getLegalNum());//申请人身份证号码(小微商户签约入账人传到后台的是legalNum)
			}else {
				mManagerModel.setLegalType(merchManagerEntity.getLegalType());//证件类型
				mManagerModel.setAccNum(merchManagerEntity.getAccNum());//申请人身份证号码
			}
			mManagerModel.setMerchantType(2);//是否大小额商户，默认2
			mManagerModel.setIsForeign(2);//默认
			
			mManagerModel.setRname(merchManagerEntity.getRname());//商户名称
			mManagerModel.setMccid(merchManagerEntity.getMccid());//行业编码
			mManagerModel.setBankAccName(merchManagerEntity.getBankAccName());//申请人姓名
			mManagerModel.setLegalNum(merchManagerEntity.getLegalNum());//申请人身份证号码
			
			if (!StringUtils.isEmpty(merchManagerEntity.getAccExpdate())){
				mManagerModel.setAccExpdate(merchManagerEntity.getAccExpdate());//申请人身份证有效期
			}
			mManagerModel.setBaddr(merchManagerEntity.getRunaddr());
			mManagerModel.setBankAccNo(merchManagerEntity.getBankAccNo());//结算账号
			mManagerModel.setBankname(merchManagerEntity.getBankName());//开户行名称
			if (!StringUtils.isEmpty(merchManagerEntity.getPayBankId())){
				mManagerModel.setPayBankId(merchManagerEntity.getPayBankId());//系统支付行号
			}
			if (!StringUtils.isEmpty(merchManagerEntity.getBankFeeRate())){
				Double bankFeeRate = Double.parseDouble(merchManagerEntity.getBankFeeRate())/100;
				mManagerModel.setBankFeeRate(bankFeeRate.toString());//借记卡费率
			}else{
				mManagerModel.setBankFeeRate("0");
			}
			if(!"0".equals(merchManagerEntity.getBankFeeRate()) && !"".equals(merchManagerEntity.getFeeAmt())&&merchManagerEntity.getFeeAmt()!=null
					&& !"".equals(merchManagerEntity.getBankFeeRate())&&merchManagerEntity.getBankFeeRate()!=null){//借记卡手续费
				Double dealAmt = Double.parseDouble(merchManagerEntity.getFeeAmt())/(Double.parseDouble(merchManagerEntity.getBankFeeRate())/100);
				Double deal = Math.floor(dealAmt);//封顶值
				mManagerModel.setFeeAmt(merchManagerEntity.getFeeAmt());
				mManagerModel.setDealAmt(deal.toString());
			}else{
				mManagerModel.setDealAmt("0");
				mManagerModel.setFeeAmt("0");
			}
			if (!StringUtils.isEmpty(merchManagerEntity.getCreditBankRate())){
				Double creditBankRate = Double.parseDouble(merchManagerEntity.getCreditBankRate())/100;
				mManagerModel.setCreditBankRate(creditBankRate.toString());//贷记卡费率
			}else{
				mManagerModel.setCreditBankRate("0");
			}
			if (!StringUtils.isEmpty(merchManagerEntity.getScanRate())){
				Double ScanRate = Double.parseDouble(merchManagerEntity.getScanRate())/100;//微信扫码支付费率
				mManagerModel.setScanRate(ScanRate);
			}else{
				mManagerModel.setScanRate(0.0);
			}
			if (!StringUtils.isEmpty(merchManagerEntity.getScanRate1())){
				Double ScanRate1 = Double.parseDouble(merchManagerEntity.getScanRate1())/100;//银联扫码支付费率
				mManagerModel.setScanRate1(ScanRate1);
			}else{
				mManagerModel.setScanRate1(0.0);
			}
			if (!StringUtils.isEmpty(merchManagerEntity.getScanRate2())){
				Double ScanRate2 = Double.parseDouble(merchManagerEntity.getScanRate2())/100;//支付宝扫码支付费率
				mManagerModel.setScanRate2(ScanRate2);
			}else{
				mManagerModel.setScanRate2(0.0);
			}

			
			mManagerModel.setContactPerson(merchManagerEntity.getContactPerson());//联系人姓名
			mManagerModel.setContactAddress(merchManagerEntity.getContactAddress());//职务
			if (!StringUtils.isEmpty(merchManagerEntity.getContactPhone())){
				mManagerModel.setContactPhone(merchManagerEntity.getContactPhone());//手机号
			}
			mManagerModel.setBusid(merchManagerEntity.getBusid());//销售
			if (!StringUtils.isEmpty(merchManagerEntity.getUnno())){
				mManagerModel.setUnno(merchManagerEntity.getUnno());
			}
			mManagerModel.setMaintainDate(new Date());			//操作日期
			mManagerModel.setMaintainType("A");		//A-add   M-Modify  D-Delete
			mManagerModel.setSettleStatus("1");		//结算状态 1-正常 2-冻结
			mManagerModel.setAccountStatus("1");	//账户状态 1-正常 2-冻结 3-注销
			
			StringBuffer mid = new StringBuffer("8640");
			mid.append(areaCode);
			mid.append(merchManagerEntity.getMccid());
			
			//商户编号生成
			Integer seqNo = 600;
			List<MIDSeqInfoModel> list= midSeqInfoRepository.findByAreaCodeAndMccid(areaCode+" ",merchManagerEntity.getMccid());
			if(list!=null && list.size() > 0){
				MIDSeqInfoModel midSeqInfoModel = list.get(0);
				seqNo = midSeqInfoModel.getSeqNo() + 1;
				midSeqInfoModel.setSeqNo(seqNo);
				//midSeqInfoDao.update(midSeqInfoModel);//不用写更新方法 TransactionAdviceConfig配置了切面 方法名是update就行
			}else{
				MIDSeqInfoModel midSeqInfo = new MIDSeqInfoModel();
				midSeqInfo.setAreaCode(areaCode);
				midSeqInfo.setMccid(merchManagerEntity.getMccid());
				midSeqInfo.setSeqNo(seqNo);
				midSeqInfo.setCreateDate(new Date());
				midSeqInfo.setCreateUser("appuser");
				midSeqInfoRepository.save(midSeqInfo);
			}
			DecimalFormat deFormat = new DecimalFormat("0000");
			mid.append(deFormat.format(seqNo));
			mManagerModel.setMid(mid.toString());
			logger.info("areaCode:"+areaCode+",mccid:"+merchManagerEntity.getMccid()+",list:"+list+",mid="+mid);
		     //小微上传照片
		    uploadMicroMerchImg(merchManagerEntity,mManagerModel);
			
			MerchantBankCardModel mbc = new MerchantBankCardModel();
			if (!StringUtils.isEmpty(merchManagerEntity.getBankAccName())){
				mbc.setBankAccName(merchManagerEntity.getBankAccName());
			}
			if (!StringUtils.isEmpty(merchManagerEntity.getBankAccNo())){
				mbc.setBankAccNo(merchManagerEntity.getBankAccNo());
			}
			mbc.setCreateDate(new Date());
			mbc.setMerCardType(0);
			mbc.setStatus(0);
			mbc.setMid(mManagerModel.getMid());
			mbc.setBankBranch(merchManagerEntity.getBankName());
			if (!StringUtils.isEmpty(merchManagerEntity.getPayBankId())){
				mbc.setPayBankId(merchManagerEntity.getPayBankId());
			}
			merchantBankRepository.save(mbc);
			
			MerchManagerModel mManagerModel1 =merchManagerRepository.save(mManagerModel);
			if(mManagerModel1!=null){
				JSONObject json=new JSONObject();
				json.put("mid", mManagerModel1.getMid());
				return new RespEntity(ReturnCode.SUCCESS, "添加成功", json);
			}else{
				return new RespEntity(ReturnCode.FALT, "添加失败");
			}
		} catch (Exception e) {
			logger.error("商户入网签约异常：" + e);
			return new RespEntity(ReturnCode.FALT, "添加失败");
		}

	}

	/**
	 * <p>判断上传文件大小</p>
	 * @author zhq
	 */
	private String ifbeyondMax(MerchManagerEntity merchManagerEntity) {
		if(merchManagerEntity.getLegalUploadFile() != null && merchManagerEntity.getLegalUploadFile().getSize()>1024*512){
			return "您上传的法人身份图片大于512KB，请重新添加！";
		}
		if(merchManagerEntity.getRegistryUpLoadFile() != null && merchManagerEntity.getRegistryUpLoadFile().getSize()>1024*512){
			return "您上传的店内图片大于512KB，请重新添加！";
		}
		if(merchManagerEntity.getBupLoadFile() != null && merchManagerEntity.getBupLoadFile().getSize()>1024*512){
			return "您上传的营业执照图片大于512KB，请重新添加！";
		}
		if(merchManagerEntity.getMaterialUpLoadFile() != null && merchManagerEntity.getMaterialUpLoadFile().getSize()>1024*512){ 
			return "您上传的法人身份证反面图片大于512KB，请重新添加！";
		}
		if(merchManagerEntity.getRupLoadFile() != null && merchManagerEntity.getRupLoadFile().getSize()>1024*512){
			return "您上传的账户证明图片大于512KB，请重新添加！";
		}
		if(merchManagerEntity.getPhotoUpLoadFile() != null && merchManagerEntity.getPhotoUpLoadFile().getSize()>1024*512){
			return "您上传的门面照片图片大于512KB，请重新添加！";
		}
		if(merchManagerEntity.getBigdealUpLoadFile() != null && merchManagerEntity.getBigdealUpLoadFile().getSize()>1024*512){
			return "您上传的大协议照片图片大于512KB，请重新添加！";
		}
		if(merchManagerEntity.getMaterialUpLoad1File() != null && merchManagerEntity.getMaterialUpLoad1File().getSize()>1024*512){
			return "您上传的POS结算授权书图片大于512KB，请重新添加！";
		}
		if(merchManagerEntity.getMaterialUpLoad2File() != null && merchManagerEntity.getMaterialUpLoad2File().getSize()>1024*512){
			return "您上传的结算卡正面图片大于512KB，请重新添加！";
		}
		if(merchManagerEntity.getMaterialUpLoad3File() != null && merchManagerEntity.getMaterialUpLoad3File().getSize()>1024*512){
			return "您上传的入账人身份证正面图片大于512KB，请重新添加！";
		}
		if(merchManagerEntity.getMaterialUpLoad4File() != null && merchManagerEntity.getMaterialUpLoad4File().getSize()>1024*512){
			return "您上传的入账人身份证反面图片大于512KB，请重新添加！";
		}
		if(merchManagerEntity.getMaterialUpLoad5File() != null && merchManagerEntity.getMaterialUpLoad5File().getSize()>1024*512){
			return "您上传的入账人手持身份证图片大于512KB，请重新添加！";
		}
		if(merchManagerEntity.getMaterialUpLoad6File() != null && merchManagerEntity.getMaterialUpLoad6File().getSize()>1024*512){
			return "您上传的优惠类专用资质/减免类专用资质证明1图片大于512KB，请重新添加！";
		}
		if(merchManagerEntity.getMaterialUpLoad7File() != null && merchManagerEntity.getMaterialUpLoad7File().getSize()>1024*512){
			return "您上传的优惠类专用资质/减免类专用资质证明2图片大于512KB，请重新添加！";
		}
		if(merchManagerEntity.getPosBackImgFile() != null && merchManagerEntity.getPosBackImgFile().getSize()>1024*512){
			return "您上传的安装POS机背面图片大于512KB，请重新添加！";
		}
		return "";
	}

	/**
	 * <p>Description: </p>
	 * @author zhq
	 * @throws Exception 
	 */
	private void uploadMicroMerchImg(MerchManagerEntity merchManagerEntity, MerchManagerModel mManagerModel) throws Exception {
		String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
		//法人证件正面
		if(merchManagerEntity.getLegalUploadFile() != null && !StringUtils.isEmpty(merchManagerEntity.getLegalUploadFile().getOriginalFilename())){
			StringBuffer fName1 = new StringBuffer();
			fName1.append(mManagerModel.getMid());
			fName1.append(".");
			fName1.append(imageDay);
			fName1.append(".1");
			fName1.append(merchManagerEntity.getLegalUploadFile().getOriginalFilename().substring(merchManagerEntity.getLegalUploadFile().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(merchManagerEntity.getLegalUploadFile(), fName1.toString(),imageDay);
			mManagerModel.setLegalUploadFileName(fName1.toString());
		}
		//营业执照
		if(merchManagerEntity.getBupLoadFile() != null && !StringUtils.isEmpty(merchManagerEntity.getBupLoadFile().getOriginalFilename())){
			StringBuffer fName2 = new StringBuffer();
			fName2.append(mManagerModel.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".2");
			fName2.append(merchManagerEntity.getBupLoadFile().getOriginalFilename().substring(merchManagerEntity.getBupLoadFile().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(merchManagerEntity.getBupLoadFile(), fName2.toString(),imageDay);
			mManagerModel.setBupLoad(fName2.toString());
		}
		//结算账户证明（银行卡）正面照片名
		if(merchManagerEntity.getRupLoadFile() != null && !StringUtils.isEmpty(merchManagerEntity.getRupLoadFile().getOriginalFilename())){
			StringBuffer fName3 = new StringBuffer();
			fName3.append(mManagerModel.getMid());
			fName3.append(".");
			fName3.append(imageDay);
			fName3.append(".3");
			fName3.append(merchManagerEntity.getRupLoadFile().getOriginalFilename().substring(merchManagerEntity.getRupLoadFile().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(merchManagerEntity.getRupLoadFile(), fName3.toString(),imageDay);
			mManagerModel.setRupLoad(fName3.toString());
		}
		//上传文件-店面门头照片
		if(merchManagerEntity.getRegistryUpLoadFile() != null && !StringUtils.isEmpty(merchManagerEntity.getRegistryUpLoadFile().getOriginalFilename())){
			StringBuffer fName4 = new StringBuffer();
			fName4.append(mManagerModel.getMid());
			fName4.append(".");
			fName4.append(imageDay);
			fName4.append(".4");
			fName4.append(merchManagerEntity.getRegistryUpLoadFile().getOriginalFilename().substring(merchManagerEntity.getRegistryUpLoadFile().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(merchManagerEntity.getRegistryUpLoadFile(), fName4.toString(),imageDay);
			mManagerModel.setRegistryUpLoad(fName4.toString());
		}
		//法人证件反面
		if(merchManagerEntity.getMaterialUpLoadFile() != null && !StringUtils.isEmpty(merchManagerEntity.getMaterialUpLoadFile().getOriginalFilename())){
			StringBuffer fName5 = new StringBuffer();
			fName5.append(mManagerModel.getMid());
			fName5.append(".");
			fName5.append(imageDay);
			fName5.append(".5");
			fName5.append(merchManagerEntity.getMaterialUpLoadFile().getOriginalFilename().substring(merchManagerEntity.getMaterialUpLoadFile().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(merchManagerEntity.getMaterialUpLoadFile(), fName5.toString(),imageDay);
			mManagerModel.setMaterialUpLoad(fName5.toString());
		}
		//店内经营照片
		if(merchManagerEntity.getPhotoUpLoadFile() != null && !StringUtils.isEmpty(merchManagerEntity.getPhotoUpLoadFile().getOriginalFilename())){
			StringBuffer fName6 = new StringBuffer();
			fName6.append(mManagerModel.getMid());
			fName6.append(".");
			fName6.append(imageDay);
			fName6.append(".6");
			fName6.append(merchManagerEntity.getPhotoUpLoadFile().getOriginalFilename().substring(merchManagerEntity.getPhotoUpLoadFile().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(merchManagerEntity.getPhotoUpLoadFile(), fName6.toString(),imageDay);
			mManagerModel.setPhotoUpLoad(fName6.toString());
		}
		//大协议照片
		if(merchManagerEntity.getBigdealUpLoadFile() != null && !StringUtils.isEmpty(merchManagerEntity.getBigdealUpLoadFile().getOriginalFilename())){
			StringBuffer fName7 = new StringBuffer();
			fName7.append(mManagerModel.getMid());
			fName7.append(".");
			fName7.append(imageDay);
			fName7.append(".7");
			fName7.append(merchManagerEntity.getBigdealUpLoadFile().getOriginalFilename().substring(merchManagerEntity.getBigdealUpLoadFile().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(merchManagerEntity.getBigdealUpLoadFile(), fName7.toString(),imageDay);
			mManagerModel.setBigdealUpLoad(fName7.toString());
		}
		//POS结算授权书照片名
		if(merchManagerEntity.getMaterialUpLoad1File() != null && !StringUtils.isEmpty(merchManagerEntity.getMaterialUpLoad1File().getOriginalFilename())){
			StringBuffer fName8 = new StringBuffer();
			fName8.append(mManagerModel.getMid());
			fName8.append(".");
			fName8.append(imageDay);
			fName8.append(".8");
			fName8.append(merchManagerEntity.getMaterialUpLoad1File().getOriginalFilename().substring(merchManagerEntity.getMaterialUpLoad1File().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(merchManagerEntity.getMaterialUpLoad1File(), fName8.toString(),imageDay);
			mManagerModel.setMaterialUpLoad1(fName8.toString());
		}
		//结算卡正面照片
		if(merchManagerEntity.getMaterialUpLoad2File() != null && !StringUtils.isEmpty(merchManagerEntity.getMaterialUpLoad2File().getOriginalFilename())){
			StringBuffer fName9 = new StringBuffer();
			fName9.append(mManagerModel.getMid());
			fName9.append(".");
			fName9.append(imageDay);
			fName9.append(".9");
			fName9.append(merchManagerEntity.getMaterialUpLoad2File().getOriginalFilename().substring(merchManagerEntity.getMaterialUpLoad2File().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(merchManagerEntity.getMaterialUpLoad2File(), fName9.toString(),imageDay);
			mManagerModel.setMaterialUpLoad2(fName9.toString());
		}
		//申请人身份证正面照片国徽
		if(merchManagerEntity.getMaterialUpLoad3File() != null && !StringUtils.isEmpty(merchManagerEntity.getMaterialUpLoad3File().getOriginalFilename())){
			StringBuffer fNameA = new StringBuffer();
			fNameA.append(mManagerModel.getMid());
			fNameA.append(".");
			fNameA.append(imageDay);
			fNameA.append(".A");
			fNameA.append(merchManagerEntity.getMaterialUpLoad3File().getOriginalFilename().substring(merchManagerEntity.getMaterialUpLoad3File().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(merchManagerEntity.getMaterialUpLoad3File(), fNameA.toString(),imageDay);
			mManagerModel.setMaterialUpLoad3(fNameA.toString());
		}
		//申请人身份证反面照片头像
		if(merchManagerEntity.getMaterialUpLoad4File() != null && !StringUtils.isEmpty(merchManagerEntity.getMaterialUpLoad4File().getOriginalFilename())){
			StringBuffer fNameB = new StringBuffer();
			fNameB.append(mManagerModel.getMid());
			fNameB.append(".");
			fNameB.append(imageDay);
			fNameB.append(".B");
			fNameB.append(merchManagerEntity.getMaterialUpLoad4File().getOriginalFilename().substring(merchManagerEntity.getMaterialUpLoad4File().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(merchManagerEntity.getMaterialUpLoad4File(), fNameB.toString(),imageDay);
			mManagerModel.setMaterialUpLoad4(fNameB.toString());
		}
		//申请人手持身份证照片
		if(merchManagerEntity.getMaterialUpLoad5File() != null && !StringUtils.isEmpty(merchManagerEntity.getMaterialUpLoad5File().getOriginalFilename())){
			StringBuffer fNameC = new StringBuffer();
			fNameC.append(mManagerModel.getMid());
			fNameC.append(".");
			fNameC.append(imageDay);
			fNameC.append(".C");
			fNameC.append(merchManagerEntity.getMaterialUpLoad5File().getOriginalFilename().substring(merchManagerEntity.getMaterialUpLoad5File().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(merchManagerEntity.getMaterialUpLoad5File(), fNameC.toString(),imageDay);
			mManagerModel.setMaterialUpLoad5(fNameC.toString());
		}
		//优惠商户特殊材料照片名
		if(merchManagerEntity.getMaterialUpLoad6File() != null && !StringUtils.isEmpty(merchManagerEntity.getMaterialUpLoad6File().getOriginalFilename())){
			StringBuffer fNameD = new StringBuffer();
			fNameD.append(mManagerModel.getMid());
			fNameD.append(".");
			fNameD.append(imageDay);
			fNameD.append(".D");
			fNameD.append(merchManagerEntity.getMaterialUpLoad6File().getOriginalFilename().substring(merchManagerEntity.getMaterialUpLoad6File().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(merchManagerEntity.getMaterialUpLoad6File(), fNameD.toString(),imageDay);
			mManagerModel.setMaterialUpLoad6(fNameD.toString());
		}
		//减免商户2（暂时没有）
		if(merchManagerEntity.getMaterialUpLoad7File() != null && !StringUtils.isEmpty(merchManagerEntity.getMaterialUpLoad7File().getOriginalFilename())){
			StringBuffer fNameE = new StringBuffer();
			fNameE.append(mManagerModel.getMid());
			fNameE.append(".");
			fNameE.append(imageDay);
			fNameE.append(".E");
			fNameE.append(merchManagerEntity.getMaterialUpLoad7File().getOriginalFilename().substring(merchManagerEntity.getMaterialUpLoad7File().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(merchManagerEntity.getMaterialUpLoad7File(), fNameE.toString(),imageDay);
			mManagerModel.setMaterialUpLoad7(fNameE.toString());
		}
		//SN照片
		if(merchManagerEntity.getPosBackImgFile() != null && !StringUtils.isEmpty(merchManagerEntity.getPosBackImgFile().getOriginalFilename())){
			StringBuffer fNameF = new StringBuffer();
			fNameF.append(mManagerModel.getMid());
			fNameF.append(".");
			fNameF.append(imageDay);
			fNameF.append(".F");
			fNameF.append(merchManagerEntity.getPosBackImgFile().getOriginalFilename().substring(merchManagerEntity.getPosBackImgFile().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(merchManagerEntity.getPosBackImgFile(), fNameF.toString(),imageDay);
			mManagerModel.setPosBackImg(fNameF.toString());
		}
		
	}

	public RespEntity findMerchantInfo(MerchantInfoEntity repayEntity){
		if (StringUtils.isEmpty(repayEntity) || StringUtils.isEmpty(repayEntity.getUnno())) {
        	return new RespEntity(ReturnCode.FALT, "参数错误");
        }
		MerchantInfoEntity rs = new MerchantInfoEntity();
		HashMap<String, Object> param = new HashMap<>();
		String sql = "select b.agentName,a.rname,a.mid,to_char(a.approvedate,'yyyy-MM-dd') from bill_merchantinfo a,bill_agentunitinfo b where a.unno = b.unno and a.approveStatus ='Y' ";
		if(repayEntity.getUnno()!=null&&!"".equals(repayEntity.getUnno())) {
			sql += " and a.unno in (select unno from sys_unit start with unno=:unno connect by prior unno = upper_unit)";
			param.put("unno", repayEntity.getUnno());
		}
		if(repayEntity.getRname()!=null&&!"".equals(repayEntity.getRname())) {
			sql += " and a.rname=:rname";
			param.put("rname", repayEntity.getRname());
		}
		if(repayEntity.getMid()!=null&&!"".equals(repayEntity.getMid())) {
			sql += " and a.mid=:mid";
			param.put("mid", repayEntity.getMid());
		}
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String day = df.format(date);
		if(repayEntity.getJoinConfirmDate()!=null&&!"".equals(repayEntity.getJoinConfirmDate())) {
			sql += " and a.approvedate>=to_date('"+repayEntity.getJoinConfirmDate()+"000000','yyyymmddhh24miss')";
		}else{
			sql += " and a.approvedate>=to_date('"+day+"000000','yyyymmddhh24miss')";
		}
		if(repayEntity.getJoinConfirmDateEnd()!=null&&!"".equals(repayEntity.getJoinConfirmDateEnd())) {
			sql += " and a.approvedate<=to_date('"+repayEntity.getJoinConfirmDateEnd()+"235959','yyyymmddhh24miss')";
		}else{
			sql += " and a.approvedate<=to_date('"+day+"235959','yyyymmddhh24miss')";
		}
		Integer count = genericJpaDao.getRowsCount(sql, param);
		sql += " order by approvedate desc";
		List<Object[]> list = genericJpaDao.queryByNativeSqlWithPageAndRows(sql,repayEntity.getPage(),repayEntity.getRows(), param);
		List<JSONObject> list2 = new ArrayList<JSONObject>();
		for (int i = 0; i < list.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("agentName", list.get(i)[0]);
			obj.put("rname", list.get(i)[1]);
			obj.put("mid", list.get(i)[2]);
			obj.put("joinConfirmDate", list.get(i)[3]);
			list2.add(obj);
        }
		rs.setList(list2);
		rs.setTotal(count);
		return new RespEntity("00","查询成功",rs);
	}

	public RespEntity findMerByMid(MerchManagerEntity repayEntity){
		List<MerchManagerModel> list = merchManagerRepository.findMerByUnnoAdnMid(repayEntity.getUnno(),repayEntity.getMid());
		if(list.size()>0) {
			return new RespEntity("00","查询成功",list.get(0));
		}else {
			return new RespEntity("99","查询失败");
		}
	}

	 private void uploadFile(MultipartFile upload, String fName, String imageDay) throws Exception {
        try {
        	 Optional<SysParamModel> paramModel = sysParamRepository.findById("HrtFrameWork");
             if(!paramModel.isPresent()){
                 throw new AppException(ReturnCode.FALT,"小微商户签约图片保存路径错误");
             }
            // 放开
	        String savePath = paramModel.get().getUploadPath();
//            String savePath = "D://u01/hrtwork/HrtFrameWork";
            String realPath = savePath + File.separator + imageDay;
            File dir = new File(realPath);
            if(!dir.exists()){
                dir.mkdirs();
            }
            String fPath = realPath + File.separator + fName;

			// @date:20190123 商户签约上传的图片添加水印
			PictureWaterMarkUtil.addWatermark(upload.getInputStream(),fPath, SysParam.PICTURE_WATER_MARK,
					fName.substring(fName.lastIndexOf(".")+1));
//            File destFile = new File(fPath);
//            upload.transferTo(destFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(ReturnCode.FALT,"微商户签约图片保存路径错误");
        }
    }
}
