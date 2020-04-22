package com.hrtp.salesAppService.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.hrtp.system.costant.SysParam;
import com.hrtp.system.util.HttpXmlClient;
import com.hrtp.system.util.PictureWaterMarkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.hrtp.salesAppService.dao.MerchTask1Repository;
import com.hrtp.salesAppService.dao.MerchTask2Repository;
import com.hrtp.salesAppService.dao.MerchTask3Repository;
import com.hrtp.salesAppService.dao.MerchTaskRepository;
import com.hrtp.salesAppService.dao.SysParamRepository;
import com.hrtp.salesAppService.dao.TodoRepository;
import com.hrtp.salesAppService.entity.appEntity.MerchManagerEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.ormEntity.MerchantTaskDataModel;
import com.hrtp.salesAppService.entity.ormEntity.MerchantTaskDetail1Model;
import com.hrtp.salesAppService.entity.ormEntity.MerchantTaskDetail2Model;
import com.hrtp.salesAppService.entity.ormEntity.MerchantTaskDetail3Model;
import com.hrtp.salesAppService.entity.ormEntity.SysParamModel;
import com.hrtp.salesAppService.entity.ormEntity.TodoModel;
import com.hrtp.salesAppService.exception.AppException;
import com.hrtp.system.costant.ReturnCode;

/**
 * <p>Description: </p>
 * @author zhq
 * @date 2018年8月28日
 */
@Service
public class MerchTaskService {
	
	@Autowired
	private MerchTaskRepository merchTaskRepository;
	@Autowired
	private MerchTask1Repository merchTask1Repository;
	@Autowired
	private MerchTask2Repository merchTask2Repository;
	@Autowired
	private MerchTask3Repository merchTask3Repository;
	@Autowired
	private TodoRepository todoRepository;
	@Autowired
	private SysParamRepository sysParamRepository;
	@Value("${admAppIp}")
	private  String admAppIp;
	
	/**
	 * <p>工单申请基本信息变更根据mid查询 </p>
	 * @author zhq
	 */
	public RespEntity getmerInfo(MerchManagerEntity merchManagerEntity) {
		List<Object[]>list= merchTaskRepository.queryAgentSale(merchManagerEntity.getUserId());
		Map<String,String> map=null;
		if(list.size()==0){
			map=merchTaskRepository.getmerInfo(merchManagerEntity.getUnno(),merchManagerEntity.getMid());
		}else{
			map=merchTaskRepository.getmerInfo1(String.valueOf(list.get(0)[0]),merchManagerEntity.getMid());
		}
		//a.rname,a.legalperson,a.legaltype,a.accnum,a.raddr,a.contactperson,a.contactphone
		return new RespEntity(ReturnCode.SUCCESS, "查询成功", map);
	}
	/**
	 * <p>工单申请账户信息变更根据mid查询 </p>
	 * @author zhq
	 */
	public RespEntity getmerAccInfo(MerchManagerEntity merchManagerEntity) {
		List<Object[]>list= merchTaskRepository.queryAgentSale(merchManagerEntity.getUserId());
		Map<String,String> map=null;
		if(list.size()==0){
			map=merchTaskRepository.getmerAccInfo(merchManagerEntity.getUnno(),merchManagerEntity.getMid());
		}else{
			map=merchTaskRepository.getmerAccInfo1(String.valueOf(list.get(0)[0]),merchManagerEntity.getMid());
		}
		//a.rname,a.legalperson,a.legaltype,a.accnum,a.raddr,a.contactperson,a.contactphone
		return new RespEntity(ReturnCode.SUCCESS, "查询成功", map);
	}
	/**
	 * <p>工单申请费率信息变更根据mid查询 </p>
	 * @author zhq
	 */
	public RespEntity getmerRateInfo(MerchManagerEntity merchManagerEntity) {
		List<Object[]>list= merchTaskRepository.queryAgentSale(merchManagerEntity.getUserId());
		Map<String,String> map=null;
		if(list.size()==0){
			map=merchTaskRepository.getmerRateInfo(merchManagerEntity.getUnno(),merchManagerEntity.getMid());
		}else{
			map=merchTaskRepository.getmerRateInfo1(String.valueOf(list.get(0)[0]),merchManagerEntity.getMid());
		}
		//a.rname,a.legalperson,a.legaltype,a.accnum,a.raddr,a.contactperson,a.contactphone
		return new RespEntity(ReturnCode.SUCCESS, "查询成功", map);
	}
	/**
	 * <p>费率信息变更申请 </p>
	 * @author zhq
	 */
	public RespEntity upmerRateInfo(MerchManagerEntity merchManagerEntity) {
		if(merchManagerEntity.getUserId()==null||"".equals(merchManagerEntity.getUserId())){
			return new RespEntity(ReturnCode.FALT,"userId不能为空");
		}
		String mid=merchManagerEntity.getMid();
		List<Object[]> list= merchTaskRepository.queryMid(mid);
		if(list.size()>0){
			return new RespEntity(ReturnCode.FALT,"已存在待审批的工单！请等待其审批后再次提交");
		}
		if (merchManagerEntity.getBankFeeRate() != null && !"".equals(merchManagerEntity.getBankFeeRate())
				&& merchManagerEntity.getCreditBankRate() != null
				&& !"".equals(merchManagerEntity.getCreditBankRate())) {
			double bankRate = Double.parseDouble(merchManagerEntity.getBankFeeRate());
			double creditRate = Double.parseDouble(merchManagerEntity.getCreditBankRate());
			if(bankRate<0.58||creditRate<0.58) {
				return new RespEntity(ReturnCode.FALT,"费率不能低于0.58!请重新填写后再次提交");
			}
		}
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
		MerchantTaskDataModel merchantTaskDataModel =new MerchantTaskDataModel();
		StringBuffer sb=new StringBuffer();
		String unno=merchManagerEntity.getUnno();
		sb.append(sf.format(new Date())).append("-").append(unno).append("-").append(System.currentTimeMillis());
		merchantTaskDataModel.setTaskId(sb.toString());
		merchantTaskDataModel.setUnno(unno);
		merchantTaskDataModel.setMid(mid);
		merchantTaskDataModel.setType("3");
		merchantTaskDataModel.setApproveStatus("Z");
		merchantTaskDataModel.setMainTainDate(new Date());
		merchantTaskDataModel.setMainTainUId(Integer.parseInt(merchManagerEntity.getUserId()));
		 //--->首先保存商户银行信息工单申请关联的“待审”对象
		MerchantTaskDataModel mTaskDataModel= merchTaskRepository.save(merchantTaskDataModel);  

		if(mTaskDataModel==null){
			return new RespEntity(ReturnCode.FALT, "工单申请失败");
		}
		Integer bmtkid=mTaskDataModel.getBmtkid();
		MerchantTaskDetail3Model merchantTaskDetail3=new MerchantTaskDetail3Model();
		merchantTaskDetail3.setBmtkid(bmtkid);
		merchantTaskDetail3.setIsForeign(2);//是否外卡1-是 2-否
		if(!"0".equals(merchManagerEntity.getBankFeeRate()) && !"".equals(merchManagerEntity.getFeeAmt())&&merchManagerEntity.getFeeAmt()!=null
				&& !"".equals(merchManagerEntity.getBankFeeRate())&&merchManagerEntity.getBankFeeRate()!=null){//借记卡手续费
			Double dealAmt = Double.parseDouble(merchManagerEntity.getFeeAmt())/(Double.parseDouble(merchManagerEntity.getBankFeeRate())/100);
			Double deal = Math.floor(dealAmt);//封顶值
			merchantTaskDetail3.setFeeAmt(Double.parseDouble(merchManagerEntity.getFeeAmt()));
			merchantTaskDetail3.setDealAmt(deal);
			merchantTaskDetail3.setBankFeeRate(Double.parseDouble(merchManagerEntity.getBankFeeRate())/100);
		}else{
			merchantTaskDetail3.setDealAmt(0.0);
			merchantTaskDetail3.setFeeAmt(0.0);
		}
		
		if(merchManagerEntity.getCreditBankRate()!=null&&!"".equals(merchManagerEntity.getCreditBankRate())){
			Double creditBankRate = Double.parseDouble(merchManagerEntity.getCreditBankRate())/100;
			merchantTaskDetail3.setCreditBankRate(creditBankRate);//贷记卡费率
		}else{
			merchantTaskDetail3.setCreditBankRate(0.0);
		}
		
		if(merchManagerEntity.getScanRate()!=null&&!"".equals(merchManagerEntity.getScanRate())){
			Double ScanRate = Double.parseDouble(merchManagerEntity.getScanRate())/100;//微信扫码支付费率
			merchantTaskDetail3.setScanRate(ScanRate);
		}else{
			merchantTaskDetail3.setScanRate(0.0);
		}
		if(merchManagerEntity.getScanRate1()!=null&&!"".equals(merchManagerEntity.getScanRate1())){
			Double ScanRate1 = Double.parseDouble(merchManagerEntity.getScanRate1())/100;//银联扫码支付费率
			merchantTaskDetail3.setScanRate1(ScanRate1);
		}else{
			merchantTaskDetail3.setScanRate1(0.0);
		}
		if(merchManagerEntity.getScanRate2()!=null&&!"".equals(merchManagerEntity.getScanRate2())){
			Double ScanRate2 = Double.parseDouble(merchManagerEntity.getScanRate2())/100;//支付宝扫码支付费率
			merchantTaskDetail3.setScanRate2(ScanRate2);
		}else{
			merchantTaskDetail3.setScanRate2(0.0);
		}
		merchTask3Repository.save(merchantTaskDetail3); 
		
		//保存工单申请到todo
		TodoModel todo=new TodoModel();
		List<Object[]> list1= merchTaskRepository.getFatherUnno(merchManagerEntity.getUnno());
		
		if(list1.size()>0){
			String fUnno=String.valueOf(list1.get(0)[0]);
			if(fUnno.equals("110000")){
				todo.setUnNo("110003");
			}else{
				todo.setUnNo(fUnno);
			}
		}
		
		todo.setMsgSender(Integer.parseInt(merchManagerEntity.getUserId()));
		todo.setBizType("bill_task");
		todo.setMsgSendTime(new Date());
		todo.setMsgSendUnit(merchManagerEntity.getUnno());
		todo.setMsgTopic("待审核的商户工单申请");
		todo.setBatchJobNo(bmtkid.toString());
		todo.setStatus(0);
		todo.setTranCode("10470");
		todoRepository.save(todo);
		
		return new RespEntity(ReturnCode.SUCCESS, "修改成功");
		
	}
	/**
	 * <p>基本信息变更申请 </p>
	 * @throws Exception 
	 */
	public RespEntity upmerBasicInfo(MerchManagerEntity merchManagerEntity) throws Exception {
		if(merchManagerEntity.getUserId()==null||"".equals(merchManagerEntity.getUserId())){
			return new RespEntity(ReturnCode.FALT,"userId不能为空");
		}
		String mid=merchManagerEntity.getMid();
		List<Object[]> list= merchTaskRepository.queryMid(mid);
		if(list.size()>0){
			return new RespEntity(ReturnCode.FALT,"已存在待审批的工单！请等待其审批后再次提交");
		}
		
		String unno=merchManagerEntity.getUnno();
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
		MerchantTaskDataModel merchantTaskDataModel =new MerchantTaskDataModel();
		StringBuffer sb=new StringBuffer();
		sb.append(sf.format(new Date())).append("-").append(unno).append("-").append(System.currentTimeMillis());
		merchantTaskDataModel.setTaskId(sb.toString());
		merchantTaskDataModel.setUnno(unno);
		merchantTaskDataModel.setMid(mid);
		merchantTaskDataModel.setType("1");
		merchantTaskDataModel.setApproveStatus("Z");
		merchantTaskDataModel.setMainTainDate(new Date());
		merchantTaskDataModel.setMainTainUId(Integer.parseInt(merchManagerEntity.getUserId()));
		MerchantTaskDataModel mtdm = merchTaskRepository.save(merchantTaskDataModel);                 //--->首先保存商户基本信息工单申请关联的“待审”对象
		//保存工单明细
		Integer bmtkid=mtdm.getBmtkid();
		MerchantTaskDetail1Model merchantTaskDetail1 = new MerchantTaskDetail1Model();
		merchantTaskDetail1.setBmtkid(bmtkid);
		merchantTaskDetail1.setContactAddress(merchManagerEntity.getContactAddress());
		merchantTaskDetail1.setContactPerson(merchManagerEntity.getContactPerson());
		merchantTaskDetail1.setContactPhone(merchManagerEntity.getContactPhone());
		merchantTaskDetail1.setLegalNum(merchManagerEntity.getLegalNum());
		merchantTaskDetail1.setLegalPerson(merchManagerEntity.getLegalPerson());
		merchantTaskDetail1.setLegalType(merchManagerEntity.getLegalType());
		merchantTaskDetail1.setRname(merchManagerEntity.getRname());
		//保存照片
		String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
		//法人证件正面
		if(merchManagerEntity.getLegalUploadFile() != null && !StringUtils.isEmpty(merchManagerEntity.getLegalUploadFile().getOriginalFilename())){
			StringBuffer fName1 = new StringBuffer();
			fName1.append(mtdm.getMid());
			fName1.append(".");
			fName1.append(imageDay);
			fName1.append(".1");
			fName1.append(merchManagerEntity.getLegalUploadFile().getOriginalFilename().substring(merchManagerEntity.getLegalUploadFile().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(merchManagerEntity.getLegalUploadFile(), fName1.toString(),imageDay);
			merchantTaskDetail1.setLegalUploadFileName(fName1.toString());
		}
		//营业执照
		if(merchManagerEntity.getBupLoadFile() != null && !StringUtils.isEmpty(merchManagerEntity.getBupLoadFile().getOriginalFilename())){
			StringBuffer fName2 = new StringBuffer();
			fName2.append(mtdm.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".2");
			fName2.append(merchManagerEntity.getBupLoadFile().getOriginalFilename().substring(merchManagerEntity.getBupLoadFile().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(merchManagerEntity.getBupLoadFile(), fName2.toString(),imageDay);
			merchantTaskDetail1.setBupload(fName2.toString());
		}
		//法人证件反面
		if(merchManagerEntity.getMaterialUpLoadFile() != null && !StringUtils.isEmpty(merchManagerEntity.getMaterialUpLoadFile().getOriginalFilename())){
			StringBuffer fName5 = new StringBuffer();
			fName5.append(mtdm.getMid());
			fName5.append(".");
			fName5.append(imageDay);
			fName5.append(".5");
			fName5.append(merchManagerEntity.getMaterialUpLoadFile().getOriginalFilename().substring(merchManagerEntity.getMaterialUpLoadFile().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(merchManagerEntity.getMaterialUpLoadFile(), fName5.toString(),imageDay);
			merchantTaskDetail1.setMaterialUpLoad(fName5.toString());
		}
		merchTask1Repository.save(merchantTaskDetail1);
		
		//保存工单申请到todo
		TodoModel todo=new TodoModel();
		List<Object[]> list1= merchTaskRepository.getFatherUnno(merchManagerEntity.getUnno());
		
		if(list1.size()>0){
			String fUnno=String.valueOf(list1.get(0)[0]);
			if(fUnno.equals("110000")){
				todo.setUnNo("110003");
			}else{
				todo.setUnNo(fUnno);
			}
		}
		
		todo.setMsgSender(Integer.parseInt(merchManagerEntity.getUserId()));
		todo.setBizType("bill_task");
		todo.setMsgSendTime(new Date());
		todo.setMsgSendUnit(merchManagerEntity.getUnno());
		todo.setMsgTopic("待审核的商户工单申请");
		todo.setBatchJobNo(bmtkid.toString());
		todo.setStatus(0);
		todo.setTranCode("10470");
		todoRepository.save(todo);
		
		return new RespEntity(ReturnCode.SUCCESS, "提交成功");
		
	}
	/**
	 * <p>账户信息变更申请 </p>
	 * @throws Exception 
	 */
	public RespEntity upmerAcountInfo(MerchManagerEntity merchManagerEntity) throws Exception {
		if(merchManagerEntity.getUserId()==null||"".equals(merchManagerEntity.getUserId())){
			return new RespEntity(ReturnCode.FALT,"userId不能为空");
		}
		String mid=merchManagerEntity.getMid();
		List<Object[]> list= merchTaskRepository.queryMid(mid);
		if(list.size()>0){
			return new RespEntity(ReturnCode.FALT,"已存在待审批的工单！请等待其审批后再次提交");
		}
		
		String unno=merchManagerEntity.getUnno();
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
		MerchantTaskDataModel merchantTaskDataModel =new MerchantTaskDataModel();
		StringBuffer sb=new StringBuffer();
		sb.append(sf.format(new Date())).append("-").append(unno).append("-").append(System.currentTimeMillis());
		merchantTaskDataModel.setTaskId(sb.toString());
		merchantTaskDataModel.setUnno(unno);
		merchantTaskDataModel.setMid(mid);
		merchantTaskDataModel.setType("2");
		merchantTaskDataModel.setApproveStatus("Z");
		merchantTaskDataModel.setMainTainDate(new Date());
		merchantTaskDataModel.setMainTainUId(Integer.parseInt(merchManagerEntity.getUserId()));
		MerchantTaskDataModel mtdm = merchTaskRepository.save(merchantTaskDataModel);                 //--->首先保存商户基本信息工单申请关联的“待审”对象
		//保存工单明细
		Integer bmtkid=mtdm.getBmtkid();
		MerchantTaskDetail2Model merchantTaskDetail2 = new MerchantTaskDetail2Model();
		merchantTaskDetail2.setBmtkid(bmtkid);
		merchantTaskDetail2.setAccType(merchManagerEntity.getAccType());
		merchantTaskDetail2.setAreaType(merchManagerEntity.getAreaType());
		merchantTaskDetail2.setBankAccName(merchManagerEntity.getBankAccName());
		merchantTaskDetail2.setBankAccNo(merchManagerEntity.getBankAccNo());
		merchantTaskDetail2.setBankBranch(merchManagerEntity.getBankName());
		merchantTaskDetail2.setPayBankId(merchManagerEntity.getPayBankId());
		merchantTaskDetail2.setSettleCycle(merchManagerEntity.getSettleCycle());
		
		//保存照片
		String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
		//POS结算授权书照片名
		if(merchManagerEntity.getMaterialUpLoad1File() != null && !StringUtils.isEmpty(merchManagerEntity.getMaterialUpLoad1File().getOriginalFilename())){
			StringBuffer fName8 = new StringBuffer();
			fName8.append(mtdm.getMid());
			fName8.append(".");
			fName8.append(imageDay);
			fName8.append(".8");
			fName8.append(merchManagerEntity.getMaterialUpLoad1File().getOriginalFilename().substring(merchManagerEntity.getMaterialUpLoad1File().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(merchManagerEntity.getMaterialUpLoad1File(), fName8.toString(),imageDay);
			merchantTaskDetail2.setAccUpLoad(fName8.toString());
		}
		//结算卡正面照片
		if(merchManagerEntity.getMaterialUpLoad2File() != null && !StringUtils.isEmpty(merchManagerEntity.getMaterialUpLoad2File().getOriginalFilename())){
			StringBuffer fName9 = new StringBuffer();
			fName9.append(mtdm.getMid());
			fName9.append(".");
			fName9.append(imageDay);
			fName9.append(".9");
			fName9.append(merchManagerEntity.getMaterialUpLoad2File().getOriginalFilename().substring(merchManagerEntity.getMaterialUpLoad2File().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(merchManagerEntity.getMaterialUpLoad2File(), fName9.toString(),imageDay);
			merchantTaskDetail2.setAuthUpLoad(fName9.toString());
		}
		//申请人身份证正面照片国徽
		if(merchManagerEntity.getMaterialUpLoad3File() != null && !StringUtils.isEmpty(merchManagerEntity.getMaterialUpLoad3File().getOriginalFilename())){
			StringBuffer fNameA = new StringBuffer();
			fNameA.append(mtdm.getMid());
			fNameA.append(".");
			fNameA.append(imageDay);
			fNameA.append(".A");
			fNameA.append(merchManagerEntity.getMaterialUpLoad3File().getOriginalFilename().substring(merchManagerEntity.getMaterialUpLoad3File().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(merchManagerEntity.getMaterialUpLoad3File(), fNameA.toString(),imageDay);
			merchantTaskDetail2.setDUpLoad(fNameA.toString());
		}
		//申请人身份证反面照片头像
		if(merchManagerEntity.getMaterialUpLoad4File() != null && !StringUtils.isEmpty(merchManagerEntity.getMaterialUpLoad4File().getOriginalFilename())){
			StringBuffer fNameB = new StringBuffer();
			fNameB.append(mtdm.getMid());
			fNameB.append(".");
			fNameB.append(imageDay);
			fNameB.append(".B");
			fNameB.append(merchManagerEntity.getMaterialUpLoad4File().getOriginalFilename().substring(merchManagerEntity.getMaterialUpLoad4File().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(merchManagerEntity.getMaterialUpLoad4File(), fNameB.toString(),imageDay);
			merchantTaskDetail2.setOpenUpLoad(fNameB.toString());
		}
		merchTask2Repository.save(merchantTaskDetail2);
		
		//保存工单申请到todo
		TodoModel todo=new TodoModel();
		List<Object[]> list1= merchTaskRepository.getFatherUnno(merchManagerEntity.getUnno());
		
		if(list1.size()>0){
			String fUnno=String.valueOf(list1.get(0)[0]);
			if(fUnno.equals("110000")){
				todo.setUnNo("110003");
			}else{
				todo.setUnNo(fUnno);
			}
		}
		
		todo.setMsgSender(Integer.parseInt(merchManagerEntity.getUserId()));
		todo.setBizType("bill_task");
		todo.setMsgSendTime(new Date());
		todo.setMsgSendUnit(merchManagerEntity.getUnno());
		todo.setMsgTopic("待审核的商户工单申请");
		todo.setBatchJobNo(bmtkid.toString());
		todo.setStatus(0);
		todo.setTranCode("10470");
		todoRepository.save(todo);
		
		return new RespEntity(ReturnCode.SUCCESS, "提交成功");
		
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
//            File destFile = new File(fPath);
			// @date:20190125 基本信息\账户信息资料变更上传的图片添加水印
			PictureWaterMarkUtil.addWatermark(upload.getInputStream(),fPath, SysParam.PICTURE_WATER_MARK,
					fName.substring(fName.lastIndexOf(".")+1));
//            upload.transferTo(destFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(ReturnCode.FALT,"微商户签约图片保存路径错误");
        }
    }
	/**
	 * <p>Description: </p>
	 * @author zhq
	 */
	public RespEntity querySnSumsamt(MerchManagerEntity merchManagerEntity) {
		if(merchManagerEntity.getSn()==null||"".equals(merchManagerEntity.getSn())||
				merchManagerEntity.getMid()==null||"".equals(merchManagerEntity.getMid())){
			return new RespEntity(ReturnCode.FALT,"sn或者mid不能为空");
		}
		//查询综合sn交易量
		Map<String,String> map = new HashMap<String, String>();
		map.put("sn", merchManagerEntity.getSn());
		map.put("mid", merchManagerEntity.getMid());
		String post = HttpXmlClient.post(admAppIp+"/AdmApp/bank/bankTermAcc_querySnSumsamt.action", map);
		Boolean flag = (Boolean) ((Map<String, Object>) JSONObject.parse(post)).get("success");
		RespEntity rs = new RespEntity();
		if(flag==false){
			rs.setReturnCode(ReturnCode.FALT);
			rs.setReturnMsg(String.valueOf(((Map<String, Object>) JSONObject.parse(post)).get("msg")));
		}else{
			rs.setReturnCode(ReturnCode.SUCCESS);
			rs.setReturnMsg(String.valueOf(((Map<String, Object>) JSONObject.parse(post)).get("msg")));
			rs.setReturnBody((Map)((Map<String, Object>) JSONObject.parse(post)).get("obj"));
		}
		return rs;
	}
}
