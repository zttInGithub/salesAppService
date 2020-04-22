package com.hrtp.salesAppService.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.hrtp.redis.JedisSource;
import com.hrtp.salesAppService.dao.BillAgentInfoRepository;
import com.hrtp.salesAppService.dao.BillAgentUnitTaskDetail1Repository;
import com.hrtp.salesAppService.dao.BillAgentUnitTaskDetail2Repository;
import com.hrtp.salesAppService.dao.BillAgentUnitTaskDetail3Repository;
import com.hrtp.salesAppService.dao.BillAgentUnitTaskRepository;
import com.hrtp.salesAppService.dao.SysParamRepository;
import com.hrtp.salesAppService.dao.UserDao;
import com.hrtp.salesAppService.entity.appEntity.BillAgentInfoEntity;
import com.hrtp.salesAppService.entity.appEntity.BillAgentTxnInfoEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.ormEntity.BillAgentInfoModel;
import com.hrtp.salesAppService.entity.ormEntity.BillAgentUnitTaskDetail1Model;
import com.hrtp.salesAppService.entity.ormEntity.BillAgentUnitTaskDetail2Model;
import com.hrtp.salesAppService.entity.ormEntity.BillAgentUnitTaskDetail3Model;
import com.hrtp.salesAppService.entity.ormEntity.BillAgentUnitTaskModel;
import com.hrtp.salesAppService.entity.ormEntity.SysParamModel;
import com.hrtp.salesAppService.exception.AppException;
import com.hrtp.salesAppService.webservice.AgentUnitInfo;
import com.hrtp.salesAppService.webservice.IGmmsService;
import com.hrtp.system.common.GenericJpaDao;
import com.hrtp.system.costant.ReturnCode;
import com.hrtp.system.util.Base64;
import com.hrtp.system.util.HexConvert;
import com.hrtp.system.util.HttpXmlClient;


/**
 * BillAgentInfoService
 * description 代理商信息服务
 * create by lxj 2018/8/21
 **/
@Service
public class BillAgentInfoService {

	private static Logger log = LoggerFactory.getLogger(BillAgentInfoService.class);

	@Autowired
	private JedisSource jedis;
	@Autowired
	private IGmmsService gsClient;
	@Autowired
	private BillAgentInfoRepository billAgentInfoRepository;
	@Autowired
	private BillAgentUnitTaskRepository billAgentUnitTaskRepository;
	@Autowired
	private BillAgentUnitTaskDetail1Repository billAgentUnitTaskDetail1Repository;
	@Autowired
	private SysParamRepository sysParamRepository;
	@Autowired
	private BillAgentUnitTaskDetail2Repository billAgentUnitTaskDetail2Repository;
	@Autowired
	private BillAgentUnitTaskDetail3Repository billAgentUnitTaskDetail3Repository;
	@Autowired
	private UserDao userDao;
	@Value("${MessageUrl}")
	private String url;

	@Autowired
	private GenericJpaDao genericJpaDao;

	public RespEntity getBaseInfo(BillAgentInfoEntity reqBody) {
		if (StringUtils.isEmpty(reqBody.getBuId()) || StringUtils.isEmpty(reqBody.getUnno()))
			return new RespEntity(ReturnCode.FALT, "参数错误");
		BillAgentInfoModel model = billAgentInfoRepository.findBaseInfo(reqBody.getBuId(), reqBody
				.getUnno());
		if (StringUtils.isEmpty(model)) return new RespEntity(ReturnCode.FALT, "查询失败");
		BillAgentInfoEntity rpBody = new BillAgentInfoEntity();
		BeanUtils.copyProperties(model, rpBody);
		RespEntity rs = new RespEntity(ReturnCode.SUCCESS, "查询成功");
		rs.setReturnBody(rpBody);
		return rs;
	}

	public RespEntity getTxnInfo(BillAgentInfoEntity reqBody) {
		if (StringUtils.isEmpty(reqBody.getBuId()) || StringUtils.isEmpty(reqBody.getUnno()))
			return new RespEntity(ReturnCode.FALT, "参数错误");
		BillAgentInfoModel model = billAgentInfoRepository.findTxnInfo(reqBody.getBuId(), reqBody
				.getUnno());
		if (StringUtils.isEmpty(model)) return new RespEntity(ReturnCode.FALT, "查询失败");
		BillAgentInfoEntity rpBody = new BillAgentInfoEntity();
		BeanUtils.copyProperties(model, rpBody);
		List<String> list = queryAgentInfoDetailed(model);
		reqBody.setLegalAUpLoadFile(list.get(0));
		reqBody.setLegalBUpLoadFile(list.get(1));
		reqBody.setLegalHandUpLoadFile(list.get(2));
		reqBody.setAuthUpLoadFile(list.get(3));
		RespEntity rs = new RespEntity(ReturnCode.SUCCESS, "查询成功");
		rs.setReturnBody(rpBody);
		return rs;
	}

	public RespEntity getContactInfo(BillAgentInfoEntity reqsBody) {
		if (StringUtils.isEmpty(reqsBody.getBuId()) || StringUtils.isEmpty(reqsBody.getUnno()))
			return new RespEntity(ReturnCode.FALT, "参数错误");
		BillAgentInfoModel model = billAgentInfoRepository.findContactInfo(reqsBody.getBuId(), reqsBody
				.getUnno());
		if (StringUtils.isEmpty(model)) return new RespEntity(ReturnCode.FALT, "查询失败");
		BillAgentInfoEntity rpBody = new BillAgentInfoEntity();
		BeanUtils.copyProperties(model, rpBody);
		RespEntity rs = new RespEntity(ReturnCode.SUCCESS, "查询成功");
		rs.setReturnBody(rpBody);
		return rs;
	}

	public RespEntity updateBaseInfo(BillAgentInfoEntity reqsBody) {
		if (StringUtils.isEmpty(reqsBody.getBuId())) return new RespEntity(ReturnCode.FALT, "参数错误");
		if(queryUnitLvlByUnno(reqsBody.getUnno())) return new RespEntity(ReturnCode.FALT, "无权操作");
		List<BillAgentUnitTaskModel> exsitList = billAgentUnitTaskRepository.findByUnnoExsit(reqsBody.getUnno());
		if (exsitList.size() > 0) return new RespEntity(ReturnCode.FALT, "有未处理申请，请稍后提交");
		BillAgentUnitTaskModel taskModel = new BillAgentUnitTaskModel();
		BillAgentUnitTaskDetail1Model detail1Model = new BillAgentUnitTaskDetail1Model();
		BeanUtils.copyProperties(reqsBody, detail1Model);
		BillAgentUnitTaskDetail1Model save1 = billAgentUnitTaskDetail1Repository.save(detail1Model);
		if (!Optional.of(save1).isPresent()) return new RespEntity(ReturnCode.FALT, "提交失败");
		taskModel.setBautdId(save1.getBautdId());
		taskModel.setApproveStatus("W");
		taskModel.setTaskType("1");
		taskModel.setUnno(reqsBody.getUnno());
		taskModel.setMaintainDate(new Date());
		taskModel.setMaintainType("A");
		BillAgentUnitTaskModel save2 = billAgentUnitTaskRepository.save(taskModel);
		if (!Optional.of(save2).isPresent()) return new RespEntity(ReturnCode.FALT, "提交失败");
		return new RespEntity(ReturnCode.SUCCESS, "提交成功");
	}

	// 结算信息上传
	public RespEntity updateTxnInfo(BillAgentTxnInfoEntity reqsBody) {
		if (StringUtils.isEmpty(reqsBody) || StringUtils.isEmpty(reqsBody.getBuId()) || StringUtils.isEmpty(reqsBody
				.getUnno())) return new RespEntity(ReturnCode.FALT,"参数错误");
		if(queryUnitLvlByUnno(reqsBody.getUnno())) return new RespEntity(ReturnCode.FALT, "无权操作");
		List<BillAgentUnitTaskModel> exsitList = billAgentUnitTaskRepository.findByUnnoExsit(reqsBody.getUnno());
		if (exsitList.size() > 0) return new RespEntity(ReturnCode.FALT, "有未处理申请，请稍后提交");
		BillAgentUnitTaskModel taskModel = new BillAgentUnitTaskModel();
		BillAgentUnitTaskDetail2Model taskDetail2Model = new BillAgentUnitTaskDetail2Model();
		BeanUtils.copyProperties(reqsBody, taskDetail2Model);
		BillAgentUnitTaskDetail2Model model2 = null;
		try {
			model2 = uploadImg(reqsBody,taskDetail2Model);
		} catch (Exception e) {
			throw new AppException(ReturnCode.FALT,"上传图片失败");
		}
		BillAgentUnitTaskDetail2Model save1 = billAgentUnitTaskDetail2Repository.save(model2);
		if (!Optional.of(save1).isPresent()) return new RespEntity(ReturnCode.FALT, "提交失败");
		taskModel.setBautdId(save1.getBautdId());
		taskModel.setApproveStatus("W");
		taskModel.setTaskType("2");
		taskModel.setUnno(reqsBody.getUnno());
		taskModel.setMaintainDate(new Date());
		taskModel.setMaintainType("A");
		BillAgentUnitTaskModel save2 = billAgentUnitTaskRepository.save(taskModel);
		if (!Optional.of(save2).isPresent()) return new RespEntity(ReturnCode.FALT, "提交失败");
		return new RespEntity(ReturnCode.SUCCESS, "提交成功");
	}

	public RespEntity updateContactInfo(BillAgentInfoEntity reqsBody) {
		if (StringUtils.isEmpty(reqsBody)||StringUtils.isEmpty(reqsBody.getBuId()) || StringUtils.isEmpty(reqsBody.getUnno()
				)) return new RespEntity (ReturnCode.FALT,"参数错误");
		if(queryUnitLvlByUnno(reqsBody.getUnno())) return new RespEntity(ReturnCode.FALT, "无权操作");
		BillAgentInfoModel model = billAgentInfoRepository.findByBuId(reqsBody.getBuId());
		BillAgentUnitTaskModel taskModel = new BillAgentUnitTaskModel();
		BillAgentUnitTaskDetail3Model model3 = new BillAgentUnitTaskDetail3Model();
		BeanUtils.copyProperties(reqsBody, model3);
		BillAgentUnitTaskDetail3Model save1 = billAgentUnitTaskDetail3Repository.save(model3);
		if (!Optional.of(save1).isPresent()) return new RespEntity(ReturnCode.FALT, "提交失败");
		taskModel.setBautdId(save1.getBautdId());
		AgentUnitInfo info = new AgentUnitInfo();
		info = updateAgentUnitTaskDetail3Y(info,taskModel,reqsBody.getUnno());
		Boolean flag = gsClient.updateAgentInfo(info,"hrtREX1234.Java");
		if (!flag) throw new IllegalAccessError("调用webservice失败");
		taskModel.setApproveStatus("Y");
		taskModel.setApproveDate(new Date());
		taskModel.setTaskType("3");
		taskModel.setUnno(reqsBody.getUnno());
		taskModel.setMaintainDate(new Date());
		taskModel.setMaintainType("A");
		BillAgentUnitTaskModel save2 = billAgentUnitTaskRepository.save(taskModel);
		if (!Optional.of(save2).isPresent()) throw new AppException(ReturnCode.FALT,"保存失败");
		return new RespEntity(ReturnCode.SUCCESS, "提交成功");
	}

	private AgentUnitInfo updateAgentUnitTaskDetail3Y(AgentUnitInfo agentUnitInfo,BillAgentUnitTaskModel model,String
			unno){
		BillAgentUnitTaskDetail3Model model2 = billAgentUnitTaskDetail3Repository.getOne(model.getBautdId());
		BillAgentInfoModel agentUnitModel = billAgentInfoRepository.findByUnno(unno);
		// 修改代理商信息
		agentUnitModel.setContact(model2.getContact());
		agentUnitModel.setContactTel(model2.getContactTel());
		agentUnitModel.setBusinessContact(model2.getBusinessContact());
		agentUnitModel.setBusinessContactsPhone(model2.getBusinessContactsPhone());
		agentUnitModel.setBusinessContactsMail(model2.getBusinessContactsMail());
		agentUnitModel.setRiskContact(model2.getRiskContact());
		agentUnitModel.setRiskContactPhone(model2.getRiskContactPhone());
		agentUnitModel.setRiskContactMail(model2.getRiskContactMail());
		// 封装
		agentUnitInfo.setContact(model2.getContact());
		agentUnitInfo.setContactTel(model2.getContactTel());
		agentUnitInfo.setBusinessContacts(model2.getBusinessContact());
		agentUnitInfo.setBusinessContactsPhone(model2.getBusinessContactsPhone());
		agentUnitInfo.setBusinessContactsMail(model2.getBusinessContactsMail());
		agentUnitInfo.setRiskContact(model2.getRiskContact());
		agentUnitInfo.setRiskContactPhone(model2.getRiskContactPhone());
		agentUnitInfo.setRiskContactMail(model2.getRiskContactMail());
		agentUnitInfo.setUnno(unno);
		return agentUnitInfo;
	}

	private List<String> queryAgentInfoDetailed(BillAgentInfoModel model) {
		Optional<SysParamModel> sysModel = sysParamRepository.findById("AgentInfo");
		String upload = sysModel.get().getUploadPath();
		List<String> list = new ArrayList<>();
		if (!StringUtils.isEmpty(model.getLegalAUpLoad())) {
			list.add(upload + File.separator + model.getLegalAUpLoad());
		} else {
			list.add("");
		}
		if (!StringUtils.isEmpty(model.getLegalBUpLoad())) {
			list.add(upload + File.separator + model.getLegalBUpLoad());
		} else {
			list.add("");
		}
		if (!StringUtils.isEmpty(model.getAccountLegalHandUpLoad())) {
			list.add(upload + File.separator + model.getAccountLegalHandUpLoad());
		} else {
			list.add("");
		}
		if (!StringUtils.isEmpty(model.getAccountAuthUpLoad())) {
			list.add(upload + File.separator + model + model.getAccountAuthUpLoad());
		} else {
			list.add("");
		}
		return list;
	}

	private BillAgentUnitTaskDetail2Model uploadImg(BillAgentTxnInfoEntity bean, BillAgentUnitTaskDetail2Model
			model) throws Exception {
		String imageDay = new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
		String buId = bean.getBuId();
		if (bean.getAuthUpLoadFile() != null && !StringUtils.isEmpty(bean.getAuthUpLoadFile())) {
			StringBuffer fName = new StringBuffer();
			fName.append(bean.getUnno() + ".");
			fName.append(buId + ".");
			fName.append(imageDay);
			fName.append(".accountAuthUpLoadFile");
			fName.append(bean.getAuthUpLoadFile().getOriginalFilename().substring(bean.getAuthUpLoadFile().getOriginalFilename().lastIndexOf("."))
					.toLowerCase());
			uploadFile(bean.getAuthUpLoadFile(), fName.toString(), imageDay);
			model.setAccountAuthUpload(imageDay+File.separator+fName.toString());
		}
		//入账人身份证正面
		if (bean.getLegalAUpLoadFile() != null && !StringUtils.isEmpty(bean.getLegalAUpLoadFile())) {
			StringBuffer fName = new StringBuffer();
			fName.append(bean.getUnno() + ".");
			fName.append(buId + ".");
			fName.append(imageDay);
			fName.append(".accountLegalAUpLoadFile");
			fName.append(bean.getLegalAUpLoadFile().getOriginalFilename().substring(bean.getLegalAUpLoadFile()
					.getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(bean.getLegalAUpLoadFile(), fName.toString(), imageDay);
			model.setAccountLegalAupload(imageDay+File.separator+fName.toString());
		}
		//入账人身份证反面
		if (bean.getLegalBUpLoadFile() != null && !StringUtils.isEmpty(bean.getLegalBUpLoadFile().getOriginalFilename())) {
			StringBuffer fName = new StringBuffer();
			fName.append(bean.getUnno() + ".");
			fName.append(buId + ".");
			fName.append(imageDay);
			fName.append(".accountLegalBUpLoadFile");
			fName.append(bean.getLegalBUpLoadFile().getOriginalFilename().substring(bean.getLegalBUpLoadFile().getOriginalFilename()
					.lastIndexOf(".")).toLowerCase());
			uploadFile(bean.getLegalBUpLoadFile(), fName.toString(), imageDay);
			model.setAccountLegalBupload(imageDay+File.separator+fName.toString());
		}
		//入账人手持身份证
		if (bean.getLegalHandUpLoadFile() != null && !StringUtils.isEmpty(bean.getLegalHandUpLoadFile().getOriginalFilename())) {
			StringBuffer fName = new StringBuffer();
			fName.append(bean.getUnno() + ".");
			fName.append(buId + ".");
			fName.append(imageDay);
			fName.append(".accountLegalHandUpLoadFile");
			fName.append(bean.getLegalHandUpLoadFile().getOriginalFilename().substring(bean.getLegalHandUpLoadFile().getOriginalFilename()
					.lastIndexOf(".")).toLowerCase());
			uploadFile(bean.getLegalHandUpLoadFile(), fName.toString(), imageDay);
			model.setAccountLegalHandUpload(imageDay+File.separator+fName.toString());
		}
		return model;
	}

	private void uploadFile(MultipartFile upload, String fName, String imageDay) throws Exception {
		try {
			Optional<SysParamModel> paramModel = sysParamRepository.findById("AgentInfo");
			if(!paramModel.isPresent()){
				throw new AppException(ReturnCode.FALT,"获取代理商图片保存路径错误");
			}
			//TODO 放开
			String savePath = paramModel.get().getUploadPath();
			//            String savePath = "D://u01/hrtwork/AgentInfo";
			String realPath = savePath + File.separator + imageDay;
			File dir = new File(realPath);
			if(!dir.exists()){
				dir.mkdirs();
			}
			String fPath = realPath + File.separator + fName;
			File destFile = new File(fPath);
			upload.transferTo(destFile);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException(ReturnCode.FALT,"代理商图片保存错误");
		}
	}

	/**
	 * @return
	 * 判断是否为一代及运营中心
	 */
	public boolean queryUnitLvlByUnno(String unno) {
		Integer i = billAgentInfoRepository.queryUnitLvlByUnno(unno);
		if(i!=null) {
			return i>2?true:false;
		}else {
			return true;
		}
	}

	public RespEntity findUnitByUnno(BillAgentInfoEntity reqsBody) {
		String sql = "select unno,un_name from sys_unit start with unno='"+reqsBody.getUnno()+"' connect by prior unno=upper_unit and status=1";
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql, null);
		ArrayList<Map<String,Object>> list2 = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("unno", list.get(i)[0]);
			map.put("unName", list.get(i)[1]);
			list2.add(map);
		}
		return new RespEntity("00","查询成功",list2);
	}

	/**
	 * 添加下级机构
	 */
	public RespEntity addNewUnit(BillAgentInfoEntity reqsBody) {
		RespEntity rs = new RespEntity();
		//判断父级机构是否为运营中心
		String sqlFather = "select to_char(un_lvl) un_lvl from sys_unit where unno = '"+ reqsBody.getUnno().toString() +"' ";
		List<String> listFather = genericJpaDao.querySingleColumnByNativeSql(sqlFather);
		if(listFather.get(0).equals("1"))
			return new RespEntity(ReturnCode.FALT, "您目前为运营中心账号登录，为了保障您的权益，请登录平台进行操作！");
		String sql = "select unno from BILL_AGENTUNITINFO where agentName = :agentName ";
		//判断已激活的代理名称是否重复
		HashMap<String,Object> map = new HashMap<String ,Object>();
		map.put("agentName", reqsBody.getAgentName().toString());
		//机构信息表代理名称是否存在
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql, map);
		if(list.size()<1){
			//APP未激活临时表代理名称是否存在
			String sql1 = "select unno from BILL_AGENTUNITINFO_APP where agentName = :agentName ";
			List<Object[]> list1 = genericJpaDao.queryByNativeSql(sql1, map);
			if(list1.size()<1){
				//都不存在存储到临时表
				String sql2 = "insert into BILL_AGENTUNITINFO_APP(unno,agentname,status) values("
						+ "'"+reqsBody.getUnno()+"','"+reqsBody.getAgentName()+"','0' ) ";
				int a = genericJpaDao.executeSql(sql2);
				if(a>0){
					String sql3 = "select AppId,agentName from BILL_AGENTUNITINFO_APP where unno = :unno and agentName = :agentName and status = '0' ";
					map.put("unno", reqsBody.getUnno().toString());
					List<Object[]> list2 = genericJpaDao.queryByNativeSql(sql3, map);
					List<Map<String, Object>> list3 = new ArrayList<Map<String,Object>>();
					for(int i = 0;i < list2.size();i++){
						Map<String, Object> map2 = new HashMap<String,Object>();
						map2.put("AppId", list2.get(i)[0].toString());
						map2.put("AgentName", list2.get(i)[1].toString());
						list3.add(map2);
					}
					rs = new RespEntity(ReturnCode.SUCCESS, "下级机构开通成功！",list3);
				}else{
					rs = new RespEntity(ReturnCode.FALT, "下级机构开通失败！");
				}
			}else{
				rs = new RespEntity(ReturnCode.FALT, "机构名称已存在！");
				return rs;
			}
		}else{
			rs = new RespEntity(ReturnCode.FALT, "机构名称已存在！");
		}
		return rs;
	}

	/**
	 * 查询未激活下级机构
	 */
	public RespEntity queryNewUnit(BillAgentInfoEntity reqsBody) {
		RespEntity rs = new RespEntity();
		String sql = "select appid,agentname,to_char(cdate,'yyyy-mm-dd hh24:mm:ss') cdate from BILL_AGENTUNITINFO_APP where unno = :unno and status = '0' order by cdate desc ";
		HashMap<String, Object> map = new HashMap<String ,Object>();
		map.put("unno", reqsBody.getUnno());
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql, map);
		if(list.size()>0){
			List<Map<String, Object>> list1 = new ArrayList<Map<String,Object>>();
			for(int i = 0;i < list.size();i++){
				Map<String, Object> map1 = new HashMap<String,Object>();
				map1.put("AppId", list.get(i)[0].toString());
				map1.put("AgentName", list.get(i)[1].toString());
				map1.put("Date", list.get(i)[2].toString());
				list1.add(map1);
			}
			rs.setReturnCode(ReturnCode.SUCCESS);
			rs.setReturnMsg("查询成功");
			rs.setReturnBody(list1);
		}
		return rs;
	}

	/**
	 * 激活下级机构
	 * appId、mobile、
	 */
	public RespEntity updateNewUnit(BillAgentInfoEntity reqsBody) {
		RespEntity rs = new RespEntity();
		if(reqsBody.getMobile()==null||"".equals(reqsBody.getMobile())||reqsBody.getMobile().length()!=11){
			rs.setReturnCode(ReturnCode.FALT);
			rs.setReturnMsg("手机号码格式有误！");
			return rs;
		}
		//判断父级机构是否为运营中心
		String sqlFather = "select to_char(un_lvl) un_lvl from sys_unit where unno = '"+ reqsBody.getUnno().toString() +"' ";
		List<String> listFather = genericJpaDao.querySingleColumnByNativeSql(sqlFather);
		if(listFather.get(0).equals("1"))
			return new RespEntity(ReturnCode.FALT, "您目前为运营中心账号登录，为了保障您的权益，请登录平台进行操作！");
		//密码转MD5
		byte[] c= HexConvert.hexStringToByte(reqsBody.getPassword());
		String md5Digest = Base64.byteArrayToBase64(c);

		//生成机构号
		String sql = "select b.un_lvl,a.unno from BILL_AGENTUNITINFO_APP a,sys_unit b where a.unno = b.unno and a.agentName = '"+reqsBody.getAgentName()+"' ";
		List<Object[]> listQuery = genericJpaDao.queryByNativeSql(sql);
		if(listQuery.size()<1){
			rs.setReturnCode(ReturnCode.FALT);
			rs.setReturnMsg("您的数据有误！请联系上级机构。");
			return rs;
		}
		int unLvl = Integer.parseInt(listQuery.get(0)[0].toString());
		String unno = listQuery.get(0)[1].toString();
		String provicnceCodeSql = "SELECT province_code from sys_unit where unno = '"+unno+"' ";
		List<String> list = genericJpaDao.querySingleColumnByNativeSql(provicnceCodeSql);		//区域编号
		String provinceCode = list.get(0).toString();
		log.info("provinceCode:"+provinceCode);
		Integer childUnlvl=0;
		String childUnlvlA ="0";

		if(unLvl==3){ //3-二级作业中心/二级代理机构
			//父级为二级代理(因为unlvl 没有4)
			childUnlvl=unLvl+2;
			childUnlvlA = childUnlvl+"";
		}else if(unLvl<9){
			//父级代理
			childUnlvl=unLvl+1;
			childUnlvlA = childUnlvl+"";
		}else if (unLvl==9){
			childUnlvl=unLvl+1;
			childUnlvlA = "A";
		}else if (unLvl==10){
			childUnlvl=unLvl+1;
			childUnlvlA = "B";
		}else if (unLvl==11){
			childUnlvl=unLvl+1;
			childUnlvlA = "C";
		}else {
			int i = 1/0;
		}
		String unitType = "";
		try{
			if("a".equals(unno.substring(0, 1))||"b".equals(unno.substring(0, 1))||
					"c".equals(unno.substring(0, 1))||"d".equals(unno.substring(0, 1))||
					"e".equals(unno.substring(0, 1))||"f".equals(unno.substring(0, 1))||
					"g".equals(unno.substring(0, 1))||"h".equals(unno.substring(0, 1))||
					"i".equals(unno.substring(0, 1))||"j".equals(unno.substring(0, 1))||
					"k".equals(unno.substring(0, 1))) {
				unitType = addUnno(provinceCode,childUnlvlA,true);
			}else {
				unitType = addUnno(provinceCode,childUnlvlA,true);
			}
			//添加机构信息
			String modela = "insert into BILL_AGENTUNITINFO(UNNO, AGENTNAME, BADDR,"
					+ "LEGALPERSON, LEGALTYPE, LEGALNUM,BNO, ACCTYPE, BANKBRANCH, BANKACCNO, BANKACCNAME, "
					+ "BANKAREA, MAINTAINUID, MAINTAINUSERID,SIGNUSERID,MAINTAINDATE, "
					+ "AMOUNTCONFIRMDATE, OPENDATE, MAINTAINTYPE, APPROVESTATUS, RNO, REGISTRYNO, BANKOPENACC, BANKTYPE, "
					+ "AREATYPE, MOBILEPHONE, CASHRATIO, CASHSWITCH"
					+ ") values("
					+ ":unno,:unName,:baddr,'PLUS','1','PLUS','PLUS','1','PLUS','PLUS','PLUS',"
					+ "'PLUS',:userId,:userId,:userId,sysdate,sysdate,sysdate,"
					+ ":maintainType,:approveStatus,:rno,:no,:openAcc,:bankType,:areaType,:mobile,:switch,:switch "
					+ ")";
			HashMap<String ,Object> map2 = new HashMap<String,Object>();
			map2.put("unno", unitType);
			map2.put("unName", reqsBody.getAgentName());
			map2.put("baddr", "北京");
			map2.put("userId", Integer.parseInt(reqsBody.getUserId()));
			map2.put("maintainType", "A");
			map2.put("approveStatus", "Y");
			map2.put("rno", "******");
			map2.put("no", "******");
			map2.put("openAcc", "******");
			map2.put("bankType", "2");
			map2.put("areaType", "1");
			map2.put("mobile", reqsBody.getMobile());
			map2.put("switch", "1");
			genericJpaDao.executeSql(modela,map2);
			//添加机构级别关系
			String unitSql = "insert into sys_unit(unno,un_name,upper_unit,create_date,create_user,un_lvl,status,province_code，un_attr) values("
					+ ":unno,:uName,:upperUnit,sysdate,:cuser,:unLvl,:status,:provinceCode,'1' "
					+ ")";
			HashMap<String ,Object> map = new HashMap<String,Object>();
			map.put("unno", unitType);
			map.put("uName", reqsBody.getAgentName());
			map.put("upperUnit", reqsBody.getUnno());
			map.put("cuser", reqsBody.getUnno());
			map.put("unLvl", childUnlvl);
			map.put("status", "1");
			map.put("provinceCode", provinceCode);
			genericJpaDao.executeSql(unitSql,map);
			//添加用户
			String userSql = "insert into sys_user(LOGIN_NAME, PWD, USER_NAME, USER_LVL,CREATE_DATE,CREATE_USER,STATUS,ISLOGIN) values("
					+ ":unno,:pwd,:uName,'0',sysdate,:cuser,1,'0'"
					+ ")";
			HashMap<String ,Object> map3 = new HashMap<String,Object>();
			map3.put("unno", unitType+"99");
			map3.put("pwd", md5Digest);
			map3.put("uName", reqsBody.getAgentName()+"管理员");
			map3.put("cuser", reqsBody.getUnno());
			genericJpaDao.executeSql(userSql,map3);
			String querySql = "select user_id,login_name from sys_user where login_name = '"+unitType+"99"+"' and user_name = '"+reqsBody.getAgentName()+"管理员"+"' and create_user = '"+reqsBody.getUnno()+"' ";
			List<Object[]> listQueryUserId = genericJpaDao.queryByNativeSql(querySql, null);
			String userId = listQueryUserId.get(0)[0].toString();
			//添加登录关联表
			String user_unitSql = "insert into sys_unit_user(unno, user_id, create_date, create_user,Status) values("
					+ ":unno,:user_id,sysdate,:sys,1)";
			HashMap<String ,Object> map4 = new HashMap<String,Object>();
			map4.put("unno", unitType);
			map4.put("user_id", userId);
			map4.put("sys", reqsBody.getUnno());
			genericJpaDao.executeSql(user_unitSql,map4);
			//添加平台登录权限级别表
			String sqlRole = "select to_char(role_id) from sys_role r where r.role_Attr = 0 and r.role_Level ='"+childUnlvl+"'";
			List<String> roles = genericJpaDao.querySingleColumnByNativeSql(sqlRole);
			for(int i = 0;i<roles.size();i++){
				String userRoleSql = "insert into sys_user_role(user_id,role_id,status,create_date,create_user) values("
						+ ":userId,:roleId,1,sysdate,:sys)";
				HashMap<String ,Object> map5 = new HashMap<String,Object>();
				map5.put("userId", userId);
				map5.put("roleId", roles.get(i));
				map5.put("sys", reqsBody.getUnno());
				genericJpaDao.executeSql(userRoleSql,map5);
			}
			//更新临时表状态
			String updateSql = " update BILL_AGENTUNITINFO_APP set status = '1' where unno = :unno and agentname = :agentName ";
			HashMap<String ,Object> map1 = new HashMap<String,Object>();
			map1.put("unno", reqsBody.getUnno());
			map1.put("agentName", reqsBody.getAgentName());
			int m = genericJpaDao.executeSql(updateSql,map1);
			if(m<1){
				throw new IllegalAccessError("修改临时表状态异常！");
			}
		}catch(Error e){
			log.error(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			rs.setReturnMsg(reqsBody.getAgentName()+"机构激活失败！");
			rs.setReturnCode(ReturnCode.FALT);
			return rs;
		}
		rs.setReturnMsg(reqsBody.getAgentName()+"机构激活成功！");
		rs.setReturnCode(ReturnCode.SUCCESS);
		return rs;
	}

	/**
	 * @param provinceCode
	 * @param childUnlvlA
	 * @param flag 上级机构是否是字母开头 true-是 false-不是
	 * @return
	 */
	private String addUnno(String provinceCode,String childUnlvlA,boolean flag){
		String unitType = null;
		try{
			if(flag) {
				unitType = addUnNoIsNumber(formatUnno(provinceCode + childUnlvlA));	//如果是字母机构新增，则查询字母开头的最大值
			}else {
				unitType = addUnNoIsNumber(provinceCode + childUnlvlA);	//后三位
			}
		}catch(Error e){
			List<String> list = genericJpaDao.querySingleColumnByNativeSql("select MINFO2 from sys_configure where GROUPNAME = 'provinceCode"+childUnlvlA+"'");
			Integer i = null;
			if(list.size()>0) i = Integer.parseInt(list.get(0).toString());
			if(i!=null&&i>0){
				try{
					if(flag) {
						unitType = addUnNoIsNumber(formatUnno(i + "" + childUnlvlA));	//如果是字母机构新增，则查询字母开头的最大值
					}else {
						unitType = addUnNoIsNumber(i + "" + childUnlvlA);	//后三位
					}
				}catch(Error e1){
					Integer j = genericJpaDao.executeSql("update sys_configure set MINFO2=(MINFO2-1) where GROUPNAME = 'provinceCode"+childUnlvlA+"'");
					log.info("开通代理商查询默认区域码满值,自动调整结束：GROUPNAME = provinceCode"+childUnlvlA+";j="+j);
					if(j>0){
						unitType = addUnNoIsNumber(formatUnno((i-1) + "" + childUnlvlA));	//如果是字母机构新增，则查询字母开头的最大值
					}else{
						throw new IllegalAccessError("开通代理商查询默认区域码满值,自动调整失败：GROUPNAME = provinceCode"+childUnlvlA);
					}
				}
			}else{
				log.info("开通代理商查询默认区域码失败：GROUPNAME = provinceCode"+childUnlvlA);
				throw new IllegalAccessError("开通代理商查询默认区域码失败：GROUPNAME = provinceCode"+childUnlvlA);
			}
		}
		log.info("开通N级代理商 生成机构号完成;unno="+unitType);
		return unitType;
	}

	/**
	 * 开通代理商时判断机构编号后三位是否符合规则
	 */
	private String addUnNoIsNumber(String unNo){
		log.info("addUnNoIsNumber");
		String sql="select max(u.unNo) from sys_unit u where u.unNo like '"+unNo+"%' ";
		List<String> list = genericJpaDao.querySingleColumnByNativeSql(sql);
		String result = "";
		if(list.get(0)==null){
			result = unNo+"000";
		}else{
			result = list.get(0).toString();
			String u = result.substring(3, 6);
			if(Integer.parseInt(u)==999){
				throw new IllegalAccessError("开通代理商失败：unNo"+unNo);
			}
			result = unNo +String.format("%03d", Integer.parseInt(u)+1);
		}
		return result.toString();
	}

	private String formatUnno(String unno) {
		//		'0' -> 'a'
		//		'1' -> 'b'
		//		'2' -> 'c'
		//		'3' -> 'd'
		//		'4' -> 'e'
		//		'5' -> 'f'
		//		'6' -> 'g'
		//		'7' -> 'h'
		//		'8' -> 'i'
		//		'9' -> 'j'
		//		'A' -> 'k'
		if("0".equals(unno.substring(0, 1))) {
			return "a"+unno.substring(1, unno.length());
		}else if("1".equals(unno.substring(0, 1))) {
			return "b"+unno.substring(1, unno.length());
		}else if("2".equals(unno.substring(0, 1))) {
			return "c"+unno.substring(1, unno.length());
		}else if("3".equals(unno.substring(0, 1))) {
			return "d"+unno.substring(1, unno.length());
		}else if("4".equals(unno.substring(0, 1))) {
			return "e"+unno.substring(1, unno.length());
		}else if("5".equals(unno.substring(0, 1))) {
			return "f"+unno.substring(1, unno.length());
		}else if("6".equals(unno.substring(0, 1))) {
			return "g"+unno.substring(1, unno.length());
		}else if("7".equals(unno.substring(0, 1))) {
			return "h"+unno.substring(1, unno.length());
		}else if("8".equals(unno.substring(0, 1))) {
			return "i"+unno.substring(1, unno.length());
		}else if("9".equals(unno.substring(0, 1))) {
			return "j"+unno.substring(1, unno.length());
		}else if("A".equals(unno.substring(0, 1))) {
			return "k"+unno.substring(1, unno.length());
		}
		return "";
	}

	/**
	 * 确认当前机构是否符合发短信的标准
	 * @return boolean
	 */
	public boolean checkUnno(BillAgentInfoEntity reqsBody){
		//判断父级机构是否为运营中心
		String sqlFather = "select to_char(un_lvl) un_lvl from sys_unit where unno = '"+ reqsBody.getUnno().toString() +"' ";
		List<String> listFather = genericJpaDao.querySingleColumnByNativeSql(sqlFather);
		if(listFather.get(0).equals("1"))
			return false;
		String sql = "select status from BILL_AGENTUNITINFO_APP where status = '0' and unno = :unno and agentName = :agentName ";
		HashMap<String, Object> map = new HashMap<String ,Object>();
		map.put("unno", reqsBody.getUnno());
		map.put("agentName", reqsBody.getAgentName());
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql, map);
		if(list.size()>0)
			return true;
		return false;
	}

	/*
	 * 发送短信验证码
	 */
	public String queryMesRand(String mobile,int flag){//String phone){
		//验证码6位随机数
		String msgRand=String.valueOf(Math.random()).substring(2,8);
		Map<String,String> params = new HashMap<String, String>();
		String msg = "";
		switch(flag){
		case 1://重置密码
			msg = "密码重置";
			break;
		case 2://注册激活
			msg = "机构激活";
			break;
		case 3://绑定结算卡
			msg = "分润卡绑定";
			break;
		}
		params.put("sendType","0");
		params.put("validCode",msgRand);
		params.put("phoneNum", mobile);
		params.put("message", msgRand+"(展业宝."+msg+"手机动态验证码，请完成验证)，如非本人操作，请忽略本短信。");
		String json =JSON.toJSONString(params);
		String ss=HttpXmlClient.postForJson(url, json);
		if(!ss.equals("0")){
			msgRand = "0";
		}else{
			//写入缓存
			//jedis.selectx(4);
			boolean fa1se = jedis.setx(mobile, 300, msgRand);
			//jedis.selectx(0);
			if(!fa1se){
				return "0";
			}
		}
		return msgRand;
	}

	/*
	 * 校验短信验证码
	 */
	public String checkMsgRand(String mobile,String msg){//String phone){
		boolean fa1se = jedis.exists(mobile);
		if(!fa1se){
			return "00";
		}else{
			String getMsg = jedis.get(mobile);
			if(msg.equals(getMsg)){
				return "SUCCESS";
			}else{
				return "01";
			}
		}
	}

	/*
	 * 删除短信验证码
	 */
	public void removeMsgRand(String mobile){//String phone){
		jedis.removex(mobile);
	}



}
