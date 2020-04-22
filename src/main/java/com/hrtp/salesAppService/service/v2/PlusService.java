package com.hrtp.salesAppService.service.v2;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrtp.salesAppService.dao.BillAgentInfoRepository;
import com.hrtp.salesAppService.dao.PushMessageRepository;
import com.hrtp.salesAppService.dao.v2.BillTerminalConfigRepository;
import com.hrtp.salesAppService.dao.v2.CheckMicroProfittemplateLogRepository;
import com.hrtp.salesAppService.dao.v2.CheckMicroProfittemplateRepository;
import com.hrtp.salesAppService.dao.v2.CheckMicroProfittemplateWRepository;
import com.hrtp.salesAppService.dao.v2.HrtUnnoCostRepository;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.appEntity.v2.BillTerminalConfigEntity;
import com.hrtp.salesAppService.entity.appEntity.v2.CommonReqEntity;
import com.hrtp.salesAppService.entity.appEntity.v2.PlusCost;
import com.hrtp.salesAppService.entity.ormEntity.BillAgentInfoModel;
import com.hrtp.salesAppService.entity.ormEntity.PushMessage;
import com.hrtp.salesAppService.entity.ormEntity.v2.BillTerminalConfigModel;
import com.hrtp.salesAppService.entity.ormEntity.v2.CheckMicroProfitTemplateLogModel;
import com.hrtp.salesAppService.entity.ormEntity.v2.CheckMicroProfittemplateModel;
import com.hrtp.salesAppService.entity.ormEntity.v2.CheckMicroProfittemplateWModel;
import com.hrtp.salesAppService.entity.ormEntity.v2.HrtUnnoCostModel;
import com.hrtp.system.common.GenericJpaDao;
import com.hrtp.system.costant.ReturnCode;
import com.hrtp.system.costant.SysParam;
import com.hrtp.threads.PushTaskThreadPools;
import com.hrtp.threads.ReceiveRepayBDThread;

import cn.jiguang.common.utils.StringUtils;

/**
 * PlusService
 * <p>
 * This is description
 *
 * @author xuegangliu 2019/07/08
 * @since 1.8
 **/
@Service
public class PlusService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HrtUnnoCostRepository hrtUnnoCostRepository;
	@Autowired
	private CheckMicroProfittemplateRepository checkMicroProfittemplateRepository;
	@Autowired
	private CheckMicroProfittemplateWRepository checkMicroProfittemplateWRepository;
	@Autowired
	private BillTerminalConfigRepository billTerminalConfigRepository;
	@Autowired
	private BillAgentInfoRepository billAgentInfoRepository;
	@Autowired
	private CheckMicroProfittemplateLogRepository checkMicroProfittemplateLogRepository;
	@Autowired
	private GenericJpaDao genericJpaDao;
	@Autowired
	private PushMessageRepository pushMessageRepository;

	/**
	 * 按机构查询分润模板
	 * @param commonReqEntity
	 * @return
	 */
	public List<Map> getCurrentUnnoCost(CommonReqEntity commonReqEntity){
		String agentId = commonReqEntity.getAgentId();
		List<Map> listMap = new ArrayList<Map>();
		if(SysParam.AGENTID_PLUS.equals(agentId)){
			listMap = get10006Profit(commonReqEntity.getUnLvl(),commonReqEntity.getUnno(),commonReqEntity.getRebateType());
		}else if(SysParam.AGENTID_SYT.equals(agentId)){
			listMap = get10005Profit(commonReqEntity.getUnLvl(),commonReqEntity.getUnno());
		}else if(SysParam.AGENTID_MD.equals(agentId)){
			listMap = get10000Profit(commonReqEntity.getUnLvl(),commonReqEntity.getUnno());
		}else{//活动
			listMap = getHuoDongProfit(commonReqEntity.getUnLvl(),commonReqEntity.getUnno(),agentId);
		}
		return listMap;
	}

	/**
	 * 当前机构的所有产品模板
	 * @param commonReqEntity
	 * @return
	 */
	private List<JSONObject> getAllCurrentUnnoCost(CommonReqEntity commonReqEntity){
		List result = new ArrayList();
		CommonReqEntity t = new CommonReqEntity();
		t.setUnLvl(commonReqEntity.getUnLvl());
		t.setUnno(commonReqEntity.getUnno());
		t.setAgentId(SysParam.AGENTID_MD);
		List<Map> listMap = new ArrayList<Map>();
		listMap = getCurrentUnnoCost(t);
		if(listMap.size()>0){
			// 检查是否有成本值
			Map validateMap = listMap.get(0);
			if(validateMap.get("rate2")==null && validateMap.get("cash2")==null
					&& validateMap.get("rate1")==null && validateMap.get("cash1")==null
					&& validateMap.get("bankRate")==null && validateMap.get("cloudRate")==null){
			}else{
				result.add(new JSONObject().fluentPut("agentId", SysParam.AGENTID_MD)
						.fluentPut("agentName", "会员宝秒到").fluentPut("costList", listMap));
			}
		}

		t.setAgentId(SysParam.AGENTID_SYT);
		listMap = getCurrentUnnoCost(t);
		if(listMap.size()>0){
			List<Map> plusList=new ArrayList<Map>();
			// 检查是否有成本值
			for (Map validateMap : listMap) {
				if(validateMap.get("rebateType")==null){
				}else{
					plusList.add(validateMap);
				}
			}
			if(plusList.size()>0) {
				result.add(new JSONObject().fluentPut("agentId",SysParam.AGENTID_SYT)
						.fluentPut("agentName","会员宝收银台").fluentPut("costList",listMap));
			}
		}
		t.setAgentId(SysParam.AGENTID_PLUS);
		if(null!=commonReqEntity.getRebateType()){
			t.setRebateType(commonReqEntity.getRebateType());
		}else {
			t.setRebateType(null);
		}
		listMap = getCurrentUnnoCost(t);
		if(listMap.size()>0){
			List<Map> plusList=new ArrayList<Map>();
			// 检查是否有成本值
			for (Map validateMap : listMap) {
				if(validateMap.get("rebateType")==null){
				}else{
					plusList.add(validateMap);
				}
			}
			if(plusList.size()>0) {
				result.add(new JSONObject().fluentPut("agentId", SysParam.AGENTID_PLUS)
						.fluentPut("agentName", "会员宝PLUS").fluentPut("costList", listMap));
			}
		}
		//活动类型 ：20200330 -YQ放开全活动模板
		String sql = "";
		if(t.getUnLvl()>2){//二代及以下机构
			sql = "select m.unno,k.profitRule "
					+ "from check_micro_profittemplate k,"
					+ "check_unit_profitemplate m where m.aptid = k.aptid "
					+ "and k.profitRule not in('22','23') and k.profitRule not in("
					+ "select t.valueinteger from bill_purconfigure t where "
					+ "t.type=3 and t.valuestring='rate' and t.prod='syt') "
					+ "and m.status = 1 and k.mataintype != 'D' and k.profitRule > 20 "
					+ "and k.merchanttype = 6 and m.unno = :unno order by k.profitRule";
		}else{//一代机构
			sql = "select a.unno, a.txn_Detail "
					+ "from Hrt_Unno_Cost a where a.txn_Detail not in('22','23') and a.txn_Detail not in("
					+ "select t.valueinteger from bill_purconfigure t where "
					+ "t.type=3 and t.valuestring='rate' and t.prod='syt') "
					+ "and a.status = 1 and a.txn_Type = 1 and a.txn_Detail > 20 "
					+ "and a.unno = :unno order by a.txn_Detail";
		}
//		String sql = "select name,valueInteger from bill_purconfigure where type = 3 and cby = '1' "
//				+ "and valueInteger not in('21','22','23') and status = 1 ";
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("unno", t.getUnno());
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql,map);
		for(int i=0;i<list.size();i++){
			result.add(new JSONObject().fluentPut("agentId",list.get(i)[1].toString())
					.fluentPut("agentName","活动"+list.get(i)[1]).fluentPut("costList",listMap));
		}
		return result;
	}

	public Map<String,HrtUnnoCostModel> getYiDaiList(String unno){
		Map<String,HrtUnnoCostModel> map = new HashMap<String,HrtUnnoCostModel>();
		List<HrtUnnoCostModel> list = hrtUnnoCostRepository.findAllByUnnoEqualsAndStatusEquals(unno,1);
		for(HrtUnnoCostModel hrt:list){
			String key=hrt.getMachineType()+"|"+hrt.getTxnType()+"|"+hrt.getTxnDetail();
			// 除去所有为空的数据
			if(hrt.getCreditRate()!=null || hrt.getDebitRate()!=null || hrt.getDebitFeeamt()!=null || hrt.getCashCost()!=null
					|| hrt.getCashRate()!=null) {
				map.put(key, hrt);
			}
		}
		return map;
	}

	public Map<String,CheckMicroProfittemplateModel> getMicroProfitTmp(String unno){
		Map<String,CheckMicroProfittemplateModel> map = new HashMap<String,CheckMicroProfittemplateModel>();
		List<CheckMicroProfittemplateModel> list = checkMicroProfittemplateRepository.findAllSelfByUnno(unno);
		for(CheckMicroProfittemplateModel mic:list){
			String key=mic.getMerchantType()+"|"+mic.getProfitType()+"|"+mic.getProfitRule();
			map.put(key,mic);
		}
		return map;
	}

	public BigDecimal getErDaiBankRate(String unno){
		CheckMicroProfittemplateModel t = checkMicroProfittemplateRepository.findRepayRate(unno);
		return t!=null?t.getSubRate():null;
	}

	/**
	 * 秒到分润模板
	 * @param unLvl
	 * @param unno
	 * @return
	 */
	private List<Map> get10000Profit(Integer unLvl,String unno){
		// 刷卡收款-非0.72 扫码收款-微信支付宝 银联二维码 云闪付 信用卡代还 提现比例
		List<Map> listMap = new ArrayList<Map>(16);
		Map map = new HashMap();
		if(unLvl<=2){
			Map<String,HrtUnnoCostModel> hrt = getYiDaiList(unno);
			if(hrt.size()==0 || !(hrt.containsKey("1|1|1") || hrt.containsKey("1|6|8")
					|| hrt.containsKey("1|6|10") || hrt.containsKey("1|4|5") || hrt.containsKey("1|3|4"))){
				return listMap;
			}
			map.put("rate2",hrt.containsKey("1|1|1")&&null!=hrt.get("1|1|1").getDebitRate()?
					hrt.get("1|1|1").getDebitRate().divide(new BigDecimal(100)):null);
			map.put("cash2",hrt.containsKey("1|1|1")&&null!=hrt.get("1|1|1").getCashCost()?
					hrt.get("1|1|1").getCashCost():null);
			map.put("rate1",hrt.containsKey("1|6|8")&&null!=hrt.get("1|6|8").getDebitRate()?
					hrt.get("1|6|8").getDebitRate().divide(new BigDecimal(100)):null);
			map.put("cash1",hrt.containsKey("1|6|8")&&null!=hrt.get("1|6|8").getCashCost()?
					hrt.get("1|6|8").getCashCost():null);
			map.put("bankRate",hrt.containsKey("1|6|10")&&null!=hrt.get("1|6|10").getDebitRate()?
					hrt.get("1|6|10").getDebitRate().divide(new BigDecimal(100)):null);
			map.put("cloudRate",hrt.containsKey("1|4|5")&&null!=hrt.get("1|4|5").getDebitRate()?
					hrt.get("1|4|5").getDebitRate().divide(new BigDecimal(100)):null);
			map.put("repayRate",hrt.containsKey("1|3|4")&&null!=hrt.get("1|3|4").getDebitRate()?
					hrt.get("1|3|4").getDebitRate().divide(new BigDecimal(100)):null);
			map.put("startDate",hrt.containsKey("1|1|1")&&null!=hrt.get("1|1|1").getLmDate()?
					hrt.get("1|1|1").getLmDate():null);
			listMap.add(map);
		}else{
			Map<String,CheckMicroProfittemplateModel> micMap = getMicroProfitTmp(unno);
			if(micMap.size()==0){
				return listMap;
			}
			map.put("rate2",micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getCreditBankRate()?
					micMap.get("3|1|1").getCreditBankRate():null);
			map.put("cash2",micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getCashAmt()?
					micMap.get("3|1|1").getCashAmt():null);
			map.put("rate1",micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getScanRate()?
					micMap.get("3|1|1").getScanRate():null);
			map.put("cash1",micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getCashAmt1()?
					micMap.get("3|1|1").getCashAmt1():null);

			map.put("bankRate",micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getScanRate2()?
					micMap.get("3|1|1").getScanRate2():null);
			map.put("cloudRate",micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getCloudQuickRate()?
					micMap.get("3|1|1").getCloudQuickRate():null);
			map.put("repayRate",getErDaiBankRate(unno));
			map.put("startDate",micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getMatainDate()?
					micMap.get("3|1|1").getMatainDate():null);
			listMap.add(map);
		}
		return listMap;
	}

	/**
	 * SYT分润模板--活动类型查询
	 * @param unLvl
	 * @param unno
	 * @return
	 */
	private List<Map> get10005Profit(Integer unLvl,String unno){
		// 扫码收款 提现比例
		List<Map> listMap = new ArrayList<Map>(16);
		if(unLvl<=2){
			String sql = "select txn_detail, t.Credit_Rate, t.cash_cost, t.lmdate "
					+ "from Hrt_Unno_Cost t where t.unno = :unno and t.machine_Type = 1 "
					+ "and t.txn_Type = 1 and t.txn_Detail in (select t.valueinteger "
					+ "from bill_purconfigure t where t.type = 3 and t.valuestring = 'rate' "
					+ "and t.prod = 'syt') and t.status = 1 ";
			HashMap<String, Object> map = new HashMap<>();
			map.put("unno", unno);
			List<Object[]> list = genericJpaDao.queryByNativeSql(sql,map);
			for(int i = 0;i<list.size();i++){
				Map<String,Object> map1 = new HashMap<String,Object>();
				// @author:yq-20200326 收银台活动拆分，收银台查询活动
				map1.put("rate1",null==list.get(i)[1]?null:
					new BigDecimal(list.get(i)[1].toString()).divide(new BigDecimal(100)));
				map1.put("cash1",list.get(i)[2]);
				map1.put("rebateType", list.get(i)[0]);
				map1.put("startDate",list.get(i)[3]);
				listMap.add(map1);
			}
		}else{
			List<CheckMicroProfittemplateModel> subList=new ArrayList<>(16);
			subList= checkMicroProfittemplateRepository.findAllSytProfitAllByUnno(unno);
			for(CheckMicroProfittemplateModel profit:subList){
				Map map = new HashMap();
				map.put("rate1",profit.getSubRate());
				map.put("cash1",profit.getCashAmt());
				map.put("rebateType", profit.getProfitRule());
				map.put("startDate",profit.getMatainDate());
				listMap.add(map);
			}
		}
		return listMap;
	}

	/**
	 * PLUS分润模板
	 * @param unLvl
	 * @param unno
	 * @param rebateType
	 * @return
	 */
	private List<Map> get10006Profit(Integer unLvl,String unno,Integer rebateType){
		// 刷卡收款  扫码收款 云闪付(与扫码一致) 信用卡还款 提现比例
		List<Map> listMap = new ArrayList<Map>(16);
		if(null==unLvl){
			return listMap;
		}
		if(unLvl<=2){
			// 查询一代模板
			List<HrtUnnoCostModel> hrtList = new ArrayList<HrtUnnoCostModel>(16);
			if(null==rebateType) {
				hrtList = hrtUnnoCostRepository.findPlusHrtCostAllByUnno(unno);
			}else{
				hrtList = hrtUnnoCostRepository.findAllHrtCostByUnnoAndRebateType(unno,rebateType);
			}
			for(HrtUnnoCostModel o:hrtList){
				listMap.add(getCostMap(1,o,null,unno));
			}
		}else{
			// 查询二代模板
			List<CheckMicroProfittemplateModel> subList=new ArrayList<>(16);
			if(null==rebateType) {
				subList= checkMicroProfittemplateRepository.findPlusProfitAllByUnno(unno);
			}else{
				subList= checkMicroProfittemplateRepository.findAllPlusProfitByUnnoAndRebateType(unno,rebateType);
			}
			for(CheckMicroProfittemplateModel o:subList){
				listMap.add(getCostMap(2,null,o,unno));
			}
		}
		return listMap;
	}

	/**
	 * 钱包跑批活动分润模板
	 * @param unLvl
	 * @param unno
	 * @param rebateType
	 * @return
	 */
	private List<Map> getHuoDongProfit(Integer unLvl,String unno,String agentId){
		// 刷卡收款  扫码收款 云闪付(与扫码一致) 信用卡还款 提现比例
		List<Map> listMap = new ArrayList<Map>(16);
		String sql = "select name,valueInteger from bill_purconfigure where type = 3 and cby = '1' and  valueInteger = :agentId ";
		HashMap<String, Object> map = new HashMap<>();
		map.put("agentId", agentId);
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql,map);
		if(null==unLvl||list.size()<1){
			return listMap;
		}
		if(unLvl<=2){
			// 查询一代模板
			List<HrtUnnoCostModel> hrtList = new ArrayList<HrtUnnoCostModel>(16);
			hrtList = hrtUnnoCostRepository.findAllHrtCostByUnnoAndHuodongType(unno,Integer.parseInt(agentId));
			for(HrtUnnoCostModel o:hrtList){
				listMap.add(getCostMap(1,o,null,unno));
			}
		}else{
			// 查询二代模板
			List<CheckMicroProfittemplateModel> subList=new ArrayList<>(16);
			subList= checkMicroProfittemplateRepository.findAllHuoDongProfitByUnnoAndRebateType(unno,Integer.parseInt(agentId));
			for(CheckMicroProfittemplateModel o:subList){
				listMap.add(getCostMap(2,null,o,unno));
			}
		}
		return listMap;
	}

	/**
	 * 返回统一格式的分润信息
	 * @param type 1:一代与运营中心,2:二代及之下
	 * @param hrtMap 一代与运营中心成本信息
	 * @param profit 二代及之下信息
	 * @param unno 机构号
	 * @return
	 */
	private Map getCostMap(int type, HrtUnnoCostModel hrtMap, CheckMicroProfittemplateModel profit,String unno){
		Map map = new HashMap(16);
		if(type==1){
			// 一代成本未除以100
			map.put("rebateType",hrtMap.getTxnDetail());
			map.put("rate2",null==hrtMap.getDebitRate()?null:new BigDecimal(hrtMap.getDebitRate().toString()).divide(new BigDecimal(100)));
			map.put("cash2",hrtMap.getDebitFeeamt());
			map.put("rate1",null==hrtMap.getCreditRate()?null:new BigDecimal(hrtMap.getCreditRate().toString()).divide(new BigDecimal(100)));
			map.put("cash1",hrtMap.getCashCost());
			map.put("startDate",hrtMap.getLmDate());

			// 代还
			HrtUnnoCostModel t = hrtUnnoCostRepository.findYiDaiCommon(unno,1,3,4);
			if(null==t || null==t.getCreditRate()) {
				map.put("repayRate", null);
			}else{
				map.put("repayRate", new BigDecimal(t.getCreditRate().toString()).divide(new BigDecimal(100)));
			}
		}else if(type==2){
			map.put("rebateType",profit.getProfitRule());
			map.put("rate2",profit.getSubRate());
			map.put("cash2",profit.getCashAmt());
			map.put("rate1",profit.getScanRate());
			map.put("cash1",profit.getCashAmt1());
			map.put("startDate",profit.getMatainDate());
			// 代还
			map.put("repayRate",getErDaiBankRate(unno));
		}
		return map;
	}

	/**
	 * 产品列表名称
	 * @param commonReqEntity
	 * @return
	 */
	public List<Map> getProfitProdList(CommonReqEntity commonReqEntity,int a){
		List result = new ArrayList();
		List<JSONObject> list = getAllCurrentUnnoCost(commonReqEntity);
		for(JSONObject jsonObject:list){
			if(SysParam.AGENTID_MD.equals(jsonObject.get("agentId")+"")){
				result.add(new JSONObject().fluentPut("agentId",SysParam.AGENTID_MD).fluentPut("agentName","会员宝秒到"));
			}else if(SysParam.AGENTID_SYT.equals(jsonObject.get("agentId")+"")){
				if(a==1){
					result.add(new JSONObject().fluentPut("agentId",SysParam.AGENTID_SYT).fluentPut("agentName","会员宝收银台"));
				}else{
					JSONObject jsonObject1 = new JSONObject();
					JSONArray rebateArray = new JSONArray();
					jsonObject1.fluentPut("agentId",SysParam.AGENTID_SYT).fluentPut("agentName","会员宝收银台");
					JSONArray array = jsonObject.getJSONArray("costList");
					for(int i=0,len=array.size();i<len;i++){
						JSONObject t = array.getJSONObject(i);
						if(t.containsKey("rebateType")){
							Integer rebateType = t.getInteger("rebateType");
							rebateArray.add(rebateType);
						}
					}
					jsonObject1.fluentPut("rebateType",rebateArray);
					result.add(jsonObject1);
				}
			}else if(SysParam.AGENTID_PLUS.equals(jsonObject.get("agentId")+"")){
				JSONObject jsonObject1 = new JSONObject();
				JSONArray rebateArray = new JSONArray();
				jsonObject1.fluentPut("agentId",SysParam.AGENTID_PLUS).fluentPut("agentName","会员宝PLUS");
				JSONArray array = jsonObject.getJSONArray("costList");
				for(int i=0,len=array.size();i<len;i++){
					JSONObject t = array.getJSONObject(i);
					if(t.containsKey("rebateType")){
						Integer rebateType = t.getInteger("rebateType");
						rebateArray.add(rebateType);
					}
				}
				jsonObject1.fluentPut("rebateType",rebateArray);
				result.add(jsonObject1);
			}else{//活动
				//20200330 -YQ展示全活动
				String agentId = jsonObject.get("agentId").toString();
				result.add(new JSONObject().fluentPut("agentId",agentId).fluentPut("agentName","活动"+agentId));
			}
		}
		return result;
	}

	/**
	 * 下级代理查询
	 * @param commonReqEntity
	 * @return
	 */
	public Map getSubUnnoList(CommonReqEntity commonReqEntity){
		Map r = new HashMap();
		List<Map> resultMap = new ArrayList<Map>(16);
		//        List<Map> listMap = hrtUnnoCostRepository.findSelfUnnoListByUpper(commonReqEntity.getUnno());
		// 修改为分页
		// 下级代理机构获取
		List<String> subUnnoList = hrtUnnoCostRepository.findAllSubUnnoByUnno(commonReqEntity.getUnno());
		Pageable pageable = PageRequest.of(commonReqEntity.getPage()-1, commonReqEntity.getSize());
		String[] unnos=subUnnoList.size()>0?subUnnoList.toArray(new String[0]):new String[]{"ZZ"};
		// 代理商名称模糊查询
		Page<BillAgentInfoModel> pages=null;
		if(StringUtils.isNotEmpty(commonReqEntity.getAgentName())){
			pages = billAgentInfoRepository.findByRowsByUnnosAndAgentNameAndPage(unnos,"%"+commonReqEntity.getAgentName()+"%",pageable);
		}else{
			pages = billAgentInfoRepository.findByRowsByUnnosAndPage(unnos,pageable);
		}
		List<BillAgentInfoModel> billAgentInfoModels = pages.getContent();

		//        for(Map map:listMap){
		// 下级机构级别一致
		Integer unLvl = null;
		for(BillAgentInfoModel model:billAgentInfoModels){
			Map t = new HashMap(16);
			if(unLvl==null){
				unLvl=hrtUnnoCostRepository.findSelfUnnoLvlByUnno(model.getUnno());
			}
			//            t.put("unno",map.get("UNNO"));
			t.put("unno",model.getUnno());
			//            t.put("buid",map.get("BUID"));
			t.put("buid",model.getBuId());
			//            t.put("agentname",map.get("AGENTNAME"));
			t.put("agentname",model.getAgentName());
			//            t.put("unLvl",map.get("UN_LVL"));
			t.put("unLvl",unLvl);
			//            t.put("userId",map.get("USER_ID"));
			resultMap.add(t);
		}
		r.put("agentList",resultMap);
		r.put("total",pages.getTotalElements());
		return r;
	}

	public Map getParentUnno(CommonReqEntity commonReqEntity){
		Map map = hrtUnnoCostRepository.findSelfUpperUnnoByUnno(commonReqEntity.getUnno());
		Map t = null;
		if(map.size()!=0 && map!=null) {
			t=new HashMap(16);
			t.put("unno", map.get("UNNO"));
			t.put("buid", map.get("BUID"));
			t.put("agentname", map.get("AGENTNAME"));
			t.put("unLvl", map.get("UN_LVL"));
		}
		return t;
	}

	public String validateCostCommonValue(PlusCost compareCost,PlusCost currentCost,PlusCost sonSub,PlusCost defaultCost){
		// syt.setRate1(new BigDecimal("0.0038"));
		// plus.setRate1(new BigDecimal("0.0038"));
		// md.setRate1(new BigDecimal("0.0045"));
		BigDecimal curRate1=currentCost.getRate1();
		BigDecimal sonRate1=sonSub.getRate1()==null?defaultCost.getRate1():sonSub.getRate1();
		if(curRate1==null || sonRate1==null || compareCost.getRate1()==null
				|| compareCost.getRate1().compareTo(curRate1)<0 || compareCost.getRate1().compareTo(sonRate1)>0){
			return "扫码费率不符合规则";
		}

		// syt.setCash1(new BigDecimal("3"));
		// plus.setCash1(new BigDecimal("3"));
		// md.setCash1(new BigDecimal("3"));
		BigDecimal curCash1=currentCost.getCash1();
		BigDecimal sonCash1=sonSub.getCash1()==null?defaultCost.getCash1():sonSub.getCash1();
		if(curCash1==null || sonCash1==null || compareCost.getCash1()==null
				|| compareCost.getCash1().compareTo(curCash1)<0 || compareCost.getCash1().compareTo(sonCash1)>0){
			return "扫码提现不符合规则";
		}
		return null;
	}

	public String validateCostMdAndPlusValue(PlusCost compareCost,PlusCost currentCost,PlusCost sonSub,PlusCost defaultCost){
		// plus.setRepayRate(new BigDecimal("0.0075"));
		// md.setRepayRate(new BigDecimal("0.0075"));
		BigDecimal curRepayRate=currentCost.getRepayRate();
		BigDecimal sonRepayRate=sonSub.getRepayRate()==null?defaultCost.getRepayRate():sonSub.getRepayRate();
		if(curRepayRate==null || sonRepayRate==null
				|| compareCost.getRepayRate().compareTo(curRepayRate)<0 || compareCost.getRepayRate().compareTo(sonRepayRate)>0){
			return "代还费率不符合规则";
		}
		return null;
	}

	private void saveOrUpdateHrtCost(String agentId,int type,HrtUnnoCostModel hrtUnnoCostModel,
			CommonReqEntity commonReqEntity,JSONObject jsonObject){
		if(hrtUnnoCostModel!=null){
			// 更新时间
			hrtUnnoCostModel.setLmDate(new Date());
		}
		//        if(SysParam.AGENTID_MD.equals(agentId)){
		// 刷卡
		if(type==1){
			if(null!=hrtUnnoCostModel){
				hrtUnnoCostModel.setCreditRate(jsonObject.getBigDecimal("rate2").multiply(new BigDecimal(100)));
				hrtUnnoCostModel.setDebitRate(jsonObject.getBigDecimal("rate2").multiply(new BigDecimal(100)));
				hrtUnnoCostModel.setCashCost(jsonObject.getBigDecimal("cash2"));
				hrtUnnoCostRepository.saveAndFlush(hrtUnnoCostModel);
			}else{
				setSaveHrtCostInfo(1,1,1,commonReqEntity,
						jsonObject.getBigDecimal("rate2"),jsonObject.getBigDecimal("rate2"),
						jsonObject.getBigDecimal("cash2"),null);
			}
		}else if(type==2){
			// 扫码
			if(null!=hrtUnnoCostModel){
				hrtUnnoCostModel.setCreditRate(jsonObject.getBigDecimal("rate1").multiply(new BigDecimal(100)));
				hrtUnnoCostModel.setDebitRate(jsonObject.getBigDecimal("rate1").multiply(new BigDecimal(100)));
				hrtUnnoCostModel.setCashCost(jsonObject.getBigDecimal("cash1"));
				hrtUnnoCostRepository.saveAndFlush(hrtUnnoCostModel);
			}else{
				setSaveHrtCostInfo(1,6,8,commonReqEntity,
						jsonObject.getBigDecimal("rate1"),jsonObject.getBigDecimal("rate1"),
						jsonObject.getBigDecimal("cash1"),null);
			}
		}else if(type==3){
			// 银联二维码
			if(null!=hrtUnnoCostModel){
				hrtUnnoCostModel.setCreditRate(jsonObject.getBigDecimal("bankRate").multiply(new BigDecimal(100)));
				hrtUnnoCostModel.setDebitRate(jsonObject.getBigDecimal("bankRate").multiply(new BigDecimal(100)));
				hrtUnnoCostModel.setCashCost(jsonObject.getBigDecimal("cash1"));
				hrtUnnoCostRepository.saveAndFlush(hrtUnnoCostModel);
			}else{
				setSaveHrtCostInfo(1,6,10,commonReqEntity,
						jsonObject.getBigDecimal("bankRate"),jsonObject.getBigDecimal("bankRate"),
						jsonObject.getBigDecimal("cash1"),null);
			}
		}else if(type==4){
			// 云闪付
			if(null!=hrtUnnoCostModel){
				hrtUnnoCostModel.setCreditRate(jsonObject.getBigDecimal("cloudRate").multiply(new BigDecimal(100)));
				hrtUnnoCostModel.setDebitRate(jsonObject.getBigDecimal("cloudRate").multiply(new BigDecimal(100)));
				hrtUnnoCostRepository.saveAndFlush(hrtUnnoCostModel);
			}else{
				setSaveHrtCostInfo(1,4,5,commonReqEntity,
						jsonObject.getBigDecimal("cloudRate"),jsonObject.getBigDecimal("cloudRate"),
						null,null);
			}
		}else if(type==5){
			// 代还
			if(null!=hrtUnnoCostModel){
				hrtUnnoCostModel.setCreditRate(jsonObject.getBigDecimal("repayRate").multiply(new BigDecimal(100)));
				hrtUnnoCostModel.setDebitRate(jsonObject.getBigDecimal("repayRate").multiply(new BigDecimal(100)));
				hrtUnnoCostRepository.saveAndFlush(hrtUnnoCostModel);
			}else{
				setSaveHrtCostInfo(1,3,4,commonReqEntity,
						jsonObject.getBigDecimal("repayRate"),jsonObject.getBigDecimal("repayRate"),
						null,null);
			}
		}else if(type==6){
			// 活动22-23
			if(null!=hrtUnnoCostModel){
				hrtUnnoCostModel.setCreditRate(jsonObject.getBigDecimal("rate1").multiply(new BigDecimal(100)));
				hrtUnnoCostModel.setDebitRate(jsonObject.getBigDecimal("rate2").multiply(new BigDecimal(100)));
				hrtUnnoCostModel.setCashCost(jsonObject.getBigDecimal("cash1"));
				hrtUnnoCostModel.setDebitFeeamt(jsonObject.getBigDecimal("cash2"));
				hrtUnnoCostRepository.saveAndFlush(hrtUnnoCostModel);
			}else{
				setSaveHrtCostInfo(1,1,jsonObject.getInteger("rebateType"),commonReqEntity,
						jsonObject.getBigDecimal("rate1"),jsonObject.getBigDecimal("rate2"),
						jsonObject.getBigDecimal("cash1"),jsonObject.getBigDecimal("cash2"));
			}
		}else if(type==7){
			// 活动20-21 只设置活动21
			if(null!=hrtUnnoCostModel){
				hrtUnnoCostModel.setCreditRate(jsonObject.getBigDecimal("rate1").multiply(new BigDecimal(100)));
				hrtUnnoCostModel.setDebitRate(jsonObject.getBigDecimal("rate1").multiply(new BigDecimal(100)));
				hrtUnnoCostModel.setCashCost(jsonObject.getBigDecimal("cash1"));
				hrtUnnoCostRepository.saveAndFlush(hrtUnnoCostModel);
			}else{
				setSaveHrtCostInfo(1,1,21,commonReqEntity,
						jsonObject.getBigDecimal("rate1"),jsonObject.getBigDecimal("rate1"),
						jsonObject.getBigDecimal("cash1"),null);
			}
		}
		//        }
	}

	private void setSaveHrtCostInfo(Integer machineType,Integer txnType,Integer txnDetail, CommonReqEntity commonReqEntity,
			BigDecimal creditRate,BigDecimal debitRate,BigDecimal cashCost,BigDecimal debitFeeAmt){
		HrtUnnoCostModel t=new HrtUnnoCostModel();
		t.setMachineType(machineType);
		t.setTxnType(txnType);
		t.setTxnDetail(txnDetail);
		t.setUnno(commonReqEntity.getSubUnno());
		t.setStatus(1);
		t.setBuid(commonReqEntity.getSubBuid());
		t.setCby(commonReqEntity.getUserId()+"");
		t.setLmby(commonReqEntity.getUserId()+"");
		t.setFlag(0);
		t.setOperateType(1);
		Date date = new Date();
		t.setLmDate(date);
		t.setCdate(date);
		t.setCreditRate(creditRate.multiply(new BigDecimal(100)));
		t.setDebitRate(debitRate.multiply(new BigDecimal(100)));
		if(cashCost!=null) {
			t.setCashCost(cashCost);
		}
		if(debitFeeAmt!=null){
			t.setDebitFeeamt(debitFeeAmt);
		}
		hrtUnnoCostRepository.save(t);
	}

	/**
	 * 终端下发
	 * @param commonReqEntity
	 * @return
	 */
	public RespEntity sendSubUnnoTerminal(CommonReqEntity commonReqEntity){
		Integer result =0;
		BillTerminalConfigModel t = new BillTerminalConfigModel();
		t.setStartTermid(commonReqEntity.getStartTermid());
		t.setEndTermid(commonReqEntity.getEndTermid());
		t.setTermidCount(commonReqEntity.getTermidCount());
		t.setCreateUser(commonReqEntity.getUserId()+"");
		Date date=new Date();
		t.setCreateDate(date);
		t.setUpdateUser(commonReqEntity.getUserId()+"");
		t.setUpdateDate(date);
		t.setMaintainType("A");
		t.setMaintainUid(commonReqEntity.getUserId()+"");
		t.setMaintainDate(date);
		if(commonReqEntity.getType().intValue()==1){
			// 终端下发
			t.setType(1);
			t.setUnno(commonReqEntity.getUnno());
			t.setSubUnno(commonReqEntity.getSubUnno());
			t.setRemark("终端下发");
			String errMsg=validateTerminal(1,commonReqEntity.getUnno(),commonReqEntity.getStartTermid(),
					commonReqEntity.getEndTermid(),commonReqEntity.getTermidCount(),commonReqEntity.getUnno());
			if(StringUtils.isNotEmpty(errMsg)){
				return new RespEntity(ReturnCode.FALT,errMsg,-1);
			}
			String errMsg1=validateTerminal(1,commonReqEntity.getSubUnno(),commonReqEntity.getStartTermid(),
					commonReqEntity.getEndTermid(),commonReqEntity.getTermidCount(),commonReqEntity.getUnno());
			if(StringUtils.isNotEmpty(errMsg1)){
				return new RespEntity(ReturnCode.FALT,errMsg1,-1);
			}
			result = billTerminalConfigRepository.updateSelfBillTerminalInfoByStartAndEnd(commonReqEntity.getSubUnno(),
					commonReqEntity.getUserId(),commonReqEntity.getUnno(),commonReqEntity.getStartTermid(),commonReqEntity.getEndTermid());
			if(result.intValue()>0){
				t.setStatus(1);
			}else{
				//                t.setStatus(0);
				return new RespEntity(ReturnCode.FALT,"终端下发信息不匹配,请确认终端当前的归属",result);
			}
			billTerminalConfigRepository.save(t);
			return new RespEntity(ReturnCode.SUCCESS,"终端下发成功",result);
		}else if(commonReqEntity.getType().intValue()==2){
			// 终端回拨
			t.setType(2);
			//            t.setUnno(commonReqEntity.getSubUnno());
			t.setUnno(commonReqEntity.getUnno());
			//            t.setUpperUnno(commonReqEntity.getUnno());
			//            t.setUpperUnno(commonReqEntity.getUpperUnno());
			t.setSubUnno(commonReqEntity.getSubUnno());
			t.setRemark("终端回拨");
			String errMsg=validateTerminal(2,commonReqEntity.getUnno(),commonReqEntity.getStartTermid(),
					commonReqEntity.getEndTermid(),commonReqEntity.getTermidCount(),commonReqEntity.getSubUnno());
			if(StringUtils.isNotEmpty(errMsg)){
				return new RespEntity(ReturnCode.FALT,errMsg,-1);
			}
			result = billTerminalConfigRepository.updateSelfBillTerminalInfoByStartAndEnd(commonReqEntity.getUnno(),
					commonReqEntity.getUserId(),commonReqEntity.getSubUnno(),commonReqEntity.getStartTermid(),commonReqEntity.getEndTermid());
			if(result.intValue()>0){
				t.setStatus(1);
			}else{
				//                t.setStatus(0);
				return new RespEntity(ReturnCode.FALT,"终端回拨信息不匹配,请确认终端当前的归属",result);
			}
			billTerminalConfigRepository.save(t);
			return new RespEntity(ReturnCode.SUCCESS,"回拨成功",result);
		}
		return new RespEntity(ReturnCode.FALT,"终端下发/回拨参数不正确",result);
	}

	/**
	 * 校验终端是否可以下发与回拨
	 * @param type 1下发 2回拨
	 * @param unno
	 * @param startTermid
	 * @param endTermid
	 * @param termidCount
	 * @param termUnno 当前设备归属机构
	 * @return
	 */
	private String validateTerminal(int type,String unno,String startTermid,String endTermid,Integer termidCount,String termUnno){
		String errMsg="";
		// 下发时，必须设置模板，才能做该产品的下发
		if(type==1) {
			CommonReqEntity reqEntity = new CommonReqEntity();
			reqEntity.setUnno(unno);
			Integer unLvl = hrtUnnoCostRepository.findSelfUnnoLvlByUnno(unno);
			reqEntity.setUnLvl(unLvl);
			// 当前机构号的产品列表
			List<Map> prodList = getProfitProdList(reqEntity,2);
			if (prodList.size() > 0) {
				// 终端区间的活动类型列表
				List<String> rebateTypeList = billTerminalConfigRepository.findSelfBillTerminalRebateTypeList(startTermid, endTermid);
				Map errRebate = new HashMap();
				for (String s : rebateTypeList) {
					errRebate.put(s,"0");
				}
				for (Map map : prodList) {
					if (SysParam.AGENTID_MD.equals(map.get("agentId"))) {
						for (String rebateType : rebateTypeList) {
							// 非收银台及PLUS活动
							if (null!=rebateType && Integer.compare(20,Integer.parseInt(rebateType))>0) {
								errRebate.remove(rebateType);
							}
						}
					} else if (SysParam.AGENTID_SYT.equals(map.get("agentId"))) {
						JSONArray array = JSONArray.parseArray(JSONObject.toJSONString(map.get("rebateType")));
						StringBuffer plusAllType = new StringBuffer(".");
						for (int i = 0, len = array.size(); i < len; i++) {
							plusAllType.append(array.get(i).toString()).append(".");
						}
						for (String rebateType : rebateTypeList) {
							if (plusAllType.toString().contains("." + rebateType + ".")) {
								errRebate.remove(rebateType);
							}
						}
					} else if (SysParam.AGENTID_PLUS.equals(map.get("agentId"))) {
						JSONArray array = JSONArray.parseArray(JSONObject.toJSONString(map.get("rebateType")));
						StringBuffer plusAllType = new StringBuffer(".");
						for (int i = 0, len = array.size(); i < len; i++) {
							plusAllType.append(array.get(i).toString()).append(".");
						}
						for (String rebateType : rebateTypeList) {
							if (plusAllType.toString().contains("." + rebateType + ".")) {
								errRebate.remove(rebateType);
							}
						}
					}else{
						// 活动
						if(errRebate.size()!=0 && errRebate.containsKey(map.get("agentId")+"")){
							errRebate.remove(map.get("agentId")+"");
						}
					}
				}
				if(errRebate.size()!=0){
					errMsg = "下发设备与该机构拥有的产品类型不符";
					return errMsg;
				}
			} else {
				errMsg = "该机构无产品类型,不能下发终端";
				return errMsg;
			}
		}
		List<String> list = billTerminalConfigRepository.findSelfBillTerminalUnnoList(startTermid,endTermid);
		if(list.size()>1){
			errMsg="终端区间归属不为同一机构";
			return errMsg;
		}else if(list.size()==0){
			errMsg="终端区间不正确";
			return errMsg;
		}else{
			if(!list.get(0).equals(termUnno)){
				errMsg="终端区间归属不为同一机构";
			}
		}
		return errMsg;
	}

	/**
	 * 我的终端下发/回拨记录
	 * @param commonReqEntity
	 * @return
	 */
	public List<BillTerminalConfigEntity> getUnnoTerminalRecord(CommonReqEntity commonReqEntity){
		if(null==commonReqEntity || null==commonReqEntity.getType() || null==commonReqEntity.getSubType()){
			return null;
		}
		List<BillTerminalConfigModel> list=new ArrayList<BillTerminalConfigModel>();
		List<BillTerminalConfigEntity> list1=new ArrayList<BillTerminalConfigEntity>();
		// 下发记录
		if(commonReqEntity.getType()==1){
			if(commonReqEntity.getSubType()==1){
				// 我的下发
				list = billTerminalConfigRepository.findAllByUnnoEqualsAndTypeEqualsOrderByCreateDateDesc(commonReqEntity.getUnno(),1);
			}else if(commonReqEntity.getSubType()==2){
				// 上级下发
				list = billTerminalConfigRepository.findAllBySubUnnoEqualsAndTypeEqualsOrderByCreateDateDesc(commonReqEntity.getUnno(),1);
			}
		}else if(commonReqEntity.getType()==2){
			// 回拨记录
			if(commonReqEntity.getSubType()==1){
				// 我的回拨
				list = billTerminalConfigRepository.findAllByUnnoEqualsAndTypeEqualsOrderByCreateDateDesc(commonReqEntity.getUnno(),2);
			}else if(commonReqEntity.getSubType()==2){
				// 上级回拨
				list = billTerminalConfigRepository.findAllBySubUnnoEqualsAndTypeEqualsOrderByCreateDateDesc(commonReqEntity.getUnno(),2);
			}
		}
		for(BillTerminalConfigModel tt:list){
			BillTerminalConfigEntity entity = new BillTerminalConfigEntity();
			BeanUtils.copyProperties(tt,entity);
			entity.setSubName(billTerminalConfigRepository.findSelfUnnoNameByUnno(tt.getSubUnno()));
			entity.setUnName(billTerminalConfigRepository.findSelfUnnoNameByUnno(tt.getUnno()));
			entity.setUpperName(billTerminalConfigRepository.findSelfUnnoNameByUnno(tt.getUpperUnno()));
			list1.add(entity);
		}
		return list1;
	}

	/**
	 * 终端下发/回拨详情
	 * @param commonReqEntity
	 * @return
	 */
	public BillTerminalConfigEntity getTerminalConfigById(CommonReqEntity commonReqEntity){
		Optional<BillTerminalConfigModel> optional = billTerminalConfigRepository.findById(commonReqEntity.getBtcId());
		BillTerminalConfigEntity entity = new BillTerminalConfigEntity();
		if(optional.isPresent()){
			BillTerminalConfigModel tt=optional.get();
			BeanUtils.copyProperties(tt,entity);
			entity.setSubName(billTerminalConfigRepository.findSelfUnnoNameByUnno(tt.getSubUnno()));
			entity.setUnName(billTerminalConfigRepository.findSelfUnnoNameByUnno(tt.getUnno()));
			entity.setUpperName(billTerminalConfigRepository.findSelfUnnoNameByUnno(tt.getUpperUnno()));
		}
		return optional.isPresent()?entity:null;
	}


	//============扫码费率拆分======@author YQ =============
	/**
	 * 按机构查询分润模板-新
	 * @param commonReqEntity
	 * @return
	 * @author YQ
	 */
	public List<Map<String,Object>> getCurrentUnnoCostNew(CommonReqEntity commonReqEntity){
		String agentId = commonReqEntity.getAgentId();
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		//判断日期是否为一号，如果是一号，先查询修改表上月记录，如果有则展示修改记录，如果没有继续展示实时表记录
		Date date = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		String today = sd.format(date).substring(6,8);
		if(today.equals("01")){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date); // 设置为当前时间
			calendar.add(Calendar.MONTH, -1); // 设置为上一个月
			date = calendar.getTime();
			List<CheckMicroProfittemplateWModel> subList = new ArrayList<>();
			if(SysParam.AGENTID_PLUS.equals(agentId)){
				subList = checkMicroProfittemplateWRepository.findNextMonthPlusProfitByUnnoAndRebateType(commonReqEntity.getUnno(),commonReqEntity.getRebateType(),sd.format(date).substring(0,6));
			}else if(SysParam.AGENTID_SYT.equals(agentId)){
				subList = checkMicroProfittemplateWRepository.findSytProfitNextMonthByUnno(commonReqEntity.getUnno(),sd.format(date).substring(0,6),commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
			}else if(SysParam.AGENTID_MD.equals(agentId)){
				subList = checkMicroProfittemplateWRepository.findMposNextMonthByUnno(commonReqEntity.getUnno(),sd.format(date).substring(0,6));
			}else{
//				if(!checkRebateType(agentId))
//					throw new NullPointerException();
				subList = checkMicroProfittemplateWRepository.findNextMonthHuoDongProfitByUnnoAndRebateType(commonReqEntity.getUnno(),Integer.parseInt(commonReqEntity.getAgentId()),sd.format(date).substring(0,6));
			}
			if(subList.size()<1){//上月无修改记录，走实时表
				if(SysParam.AGENTID_PLUS.equals(agentId)){
					listMap = get10006ProfitNew(commonReqEntity.getUnLvl(),commonReqEntity.getUnno(),commonReqEntity.getRebateType());
				}else if(SysParam.AGENTID_SYT.equals(agentId)){
					listMap = get10005ProfitNew(commonReqEntity.getUnLvl(),commonReqEntity.getUnno(),commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
				}else if(SysParam.AGENTID_MD.equals(agentId)){
					listMap = get10000ProfitNew(commonReqEntity.getUnLvl(),commonReqEntity.getUnno());
				}else{
//					if(!checkRebateType(agentId))
//						throw new NullPointerException();
					listMap = getHuoDongProfitNew(commonReqEntity.getUnLvl(),commonReqEntity.getUnno(),Integer.parseInt(commonReqEntity.getAgentId()));
				}
			}else{//上月有修改记录，展示修改记录
				if(SysParam.AGENTID_MD.equals(agentId)){//秒到
					//二代秒到格式化
					listMap = getNextMonthMapMpos(subList);
				}else if(SysParam.AGENTID_SYT.equals(agentId)||SysParam.AGENTID_PLUS.equals(agentId)){//收银台和PLUS
					for(CheckMicroProfittemplateWModel o:subList){
						listMap = getNextMonthMapSM(o,commonReqEntity.getAgentId());
					}
				}else{//活动
					for(CheckMicroProfittemplateWModel o:subList){
						listMap = getNextMonthMapSM(o,"10007");
					}
				}
			}
		}else{
			if(SysParam.AGENTID_PLUS.equals(agentId)){
				listMap = get10006ProfitNew(commonReqEntity.getUnLvl(),commonReqEntity.getUnno(),commonReqEntity.getRebateType());
			}else if(SysParam.AGENTID_SYT.equals(agentId)){
				listMap = get10005ProfitNew(commonReqEntity.getUnLvl(),commonReqEntity.getUnno(),commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
			}else if(SysParam.AGENTID_MD.equals(agentId)){
				listMap = get10000ProfitNew(commonReqEntity.getUnLvl(),commonReqEntity.getUnno());
			}else{//活动
//				if(!checkRebateType(agentId))
//					throw new NullPointerException();
				listMap = getHuoDongProfitNew(commonReqEntity.getUnLvl(),commonReqEntity.getUnno(),Integer.parseInt(commonReqEntity.getAgentId()));
			}
		}
		return listMap;
	}

	/**
	 * PLUS分润模板 -新
	 * @param unLvl
	 * @param unno
	 * @param rebateType
	 * @return
	 * @author YQ
	 */
	private List<Map<String,Object>> get10006ProfitNew(Integer unLvl,String unno,Integer rebateType){
		List<Map<String, Object>> listMap = new ArrayList<>();
		if(null==unLvl){
			return listMap;
		}
		if(unLvl<=2){
			// 查询一代模板
			List<HrtUnnoCostModel> hrtList = new ArrayList<HrtUnnoCostModel>(16);
			if(null==rebateType) {
				hrtList = hrtUnnoCostRepository.findPlusHrtCostAllByUnno(unno);
			}else{
				hrtList = hrtUnnoCostRepository.findAllHrtCostByUnnoAndRebateType(unno,rebateType);
			}
			for(HrtUnnoCostModel o:hrtList){
				listMap = getCostMapNew(1,o,null,"10006");
			}
		}else{
			// 查询二代模板
			List<CheckMicroProfittemplateModel> subList=new ArrayList<>(16);
			if(null==rebateType) {
				subList= checkMicroProfittemplateRepository.findPlusProfitAllByUnno(unno);
			}else{
				subList= checkMicroProfittemplateRepository.findAllPlusProfitByUnnoAndRebateType(unno,rebateType);
			}
			for(CheckMicroProfittemplateModel o:subList){
				listMap = getCostMapNew(2,null,o,"10006");
			}
		}
		return listMap;
	}

	/**
	 * 跑批钱包活动分润模板 -新
	 * @param unLvl
	 * @param unno
	 * @param rebateType
	 * @return
	 * @author YQ
	 */
	private List<Map<String,Object>> getHuoDongProfitNew(Integer unLvl,String unno,Integer rebateType){
		List<Map<String, Object>> listMap = new ArrayList<>();
		if(null==unLvl){
			return listMap;
		}
		if(unLvl<=2){
			// 查询一代模板
			List<HrtUnnoCostModel> hrtList = new ArrayList<HrtUnnoCostModel>(16);
			hrtList = hrtUnnoCostRepository.findAllHrtCostByUnnoAndHuodongType(unno,rebateType);
			for(HrtUnnoCostModel o:hrtList){
				listMap = getCostMapNew(1,o,null,"10007");
			}
		}else{
			// 查询二代模板
			List<CheckMicroProfittemplateModel> subList=new ArrayList<>(16);
			subList= checkMicroProfittemplateRepository.findAllHuoDongProfitByUnnoAndRebateType(unno,rebateType);
			for(CheckMicroProfittemplateModel o:subList){
				listMap = getCostMapNew(2,null,o,"10007");
			}
		}
		return listMap;
	}

	/**
	 * SYT分润模板 -新
	 * @param unLvl
	 * @param unno
	 * @return
	 */
	private List<Map<String,Object>> get10005ProfitNew(Integer unLvl,String unno,Integer rebateType){
		// 扫码收款 提现比例
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>(16);
		List<HrtUnnoCostModel> hrtList = new ArrayList<HrtUnnoCostModel>(16);
		if(unLvl<=2){
			hrtList = hrtUnnoCostRepository.findAllHrtCostByUnnoAndRebateType(unno,rebateType);
			for(HrtUnnoCostModel hrtMap:hrtList){
				listMap = getCostMapNew(1,hrtMap,null,"10005");
			}
		}else{
			List<CheckMicroProfittemplateModel> subList=new ArrayList<>(16);
			subList= checkMicroProfittemplateRepository.findSytProfitAllByUnno(unno,rebateType);
			for(CheckMicroProfittemplateModel profit:subList){
				listMap = getCostMapNew(2,null,profit,"10005");
			}
		}
		return listMap;
	}

	/**
	 * 秒到分润模板-新
	 * @param unLvl
	 * @param unno
	 * @return
	 */
	private List<Map<String,Object>> get10000ProfitNew(Integer unLvl,String unno){
		// 刷卡收款-非0.72 扫码收款-微信支付宝 银联二维码 云闪付 信用卡代还 提现比例
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>(16);
		List<Map<String,String>> list = new ArrayList<>();
		Map map = new HashMap();
		if(unLvl<=2){
			Map<String,HrtUnnoCostModel> hrt = getYiDaiList(unno);
			if(hrt.size()==0 || !(hrt.containsKey("1|1|1") || hrt.containsKey("1|6|8")
					|| hrt.containsKey("1|6|10") || hrt.containsKey("1|4|5") || hrt.containsKey("1|3|4"))){
				return listMap;
			}
			Map<String, String> map1 = new HashMap<>();
			map1.put("text","刷卡收款");
			map1.put("rate", hrt.containsKey("1|1|1")&&null!=hrt.get("1|1|1").getDebitRate()?
					hrt.get("1|1|1").getDebitRate().divide(new BigDecimal(100)).toString():"0");
			map1.put("cash", hrt.containsKey("1|1|1")&&null!=hrt.get("1|1|1").getCashCost()?
					hrt.get("1|1|1").getCashCost().toString():"0");
			list.add(map1);
			//			Map<String, String> map2 = new HashMap<>();
			//			map2.put("text","扫码收款");
			//			map2.put("rate", hrt.containsKey("1|6|8")&&null!=hrt.get("1|6|8").getDebitRate()?
			//					hrt.get("1|6|8").getDebitRate().divide(new BigDecimal(100)).toString():"0");
			//			map2.put("cash", hrt.containsKey("1|6|8")&&null!=hrt.get("1|6|8").getCashCost()?
			//					hrt.get("1|6|8").getCashCost().toString():"0");
			//			list.add(map2);
			Map<String, String> map5 = new HashMap<>();
			map5.put("text","秒到扫码1000以下");
			map5.put("rate", hrt.containsKey("1|6|8")&&null!=hrt.get("1|6|8").getDebitRate()?
					hrt.get("1|6|8").getDebitRate().divide(new BigDecimal(100)).toString():"0");
			map5.put("cash", hrt.containsKey("1|6|8")&&null!=hrt.get("1|6|8").getCashCost()?
					hrt.get("1|6|8").getCashCost().toString():"0");
			list.add(map5);
			Map<String, String> map6 = new HashMap<>();
			map6.put("text","秒到扫码1000以上");
			map6.put("rate", hrt.containsKey("1|6|9")&&null!=hrt.get("1|6|9").getDebitRate()?
					hrt.get("1|6|9").getDebitRate().divide(new BigDecimal(100)).toString():"0");
			map6.put("cash", hrt.containsKey("1|6|9")&&null!=hrt.get("1|6|9").getCashCost()?
					hrt.get("1|6|9").getCashCost().toString():"0");
			list.add(map6);
			Map<String, String> map3 = new HashMap<>();
			map3.put("text","银联二维码");
			map3.put("rate", hrt.containsKey("1|6|10")&&null!=hrt.get("1|6|10").getDebitRate()?
					hrt.get("1|6|10").getDebitRate().divide(new BigDecimal(100)).toString():"0");
			map3.put("cash","0");
			list.add(map3);
			Map<String, String> map4 = new HashMap<>();
			map4.put("text","云闪付");
			map4.put("rate", hrt.containsKey("1|4|5")&&null!=hrt.get("1|4|5").getDebitRate()?
					hrt.get("1|4|5").getDebitRate().divide(new BigDecimal(100)).toString():"0");
			map4.put("cash","0");
			list.add(map4);
		}else{
			Map<String,CheckMicroProfittemplateModel> micMap = getMicroProfitTmp(unno);
			if(micMap.size()==0){
				return listMap;
			}
			Map<String, String> map1 = new HashMap<>();
			map1.put("text","刷卡收款");
			map1.put("rate", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getCreditBankRate()?
					micMap.get("3|1|1").getCreditBankRate().toString():"0");
			map1.put("cash", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getCashAmt()?
					micMap.get("3|1|1").getCashAmt().toString():"0");
			list.add(map1);
			//			Map<String, String> map2 = new HashMap<>();
			//			map2.put("text","扫码收款");
			//			map2.put("rate", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getScanRate()?
			//					micMap.get("3|1|1").getScanRate().toString():"0");
			//			map2.put("cash", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getCashAmt1()?
			//					micMap.get("3|1|1").getCashAmt1().toString():"0");
			//			list.add(map2);
			//秒到扫码拆分
			Map<String, String> map5 = new HashMap<>();
			map5.put("text","秒到扫码1000以下");
			map5.put("rate", micMap.containsKey("2|1|1")&&null!=micMap.get("2|1|1").getScanRate()?
					micMap.get("2|1|1").getScanRate().toString():"0");
			map5.put("cash", micMap.containsKey("2|1|1")&&null!=micMap.get("2|1|1").getCASHAMTUNDER()?
					micMap.get("2|1|1").getCASHAMTUNDER().toString():"0");
			list.add(map5);
			Map<String, String> map6 = new HashMap<>();
			map6.put("text","秒到扫码1000以上");
			map6.put("rate", micMap.containsKey("2|1|1")&&null!=micMap.get("2|1|1").getScanRate1()?
					micMap.get("2|1|1").getScanRate1().toString():"0");
			map6.put("cash", micMap.containsKey("2|1|1")&&null!=micMap.get("2|1|1").getCASHAMTABOVE()?
					micMap.get("2|1|1").getCASHAMTABOVE().toString():"0");
			list.add(map6);
			Map<String, String> map3 = new HashMap<>();
			map3.put("text","银联二维码");
			map3.put("rate", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getScanRate2()?
					micMap.get("3|1|1").getScanRate2().toString():"0");
			map3.put("cash", "0");
			list.add(map3);
			Map<String, String> map4 = new HashMap<>();
			map4.put("text","云闪付");
			map4.put("rate", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getCloudQuickRate()?
					micMap.get("3|1|1").getCloudQuickRate().toString():"0");
			map4.put("cash", "0");
			list.add(map4);
		}
		Map<String, Object> mapResult = new HashMap<>();
		mapResult.put("list", list);
		mapResult.put("rebateType", "0");
		listMap.add(mapResult);
		return listMap;
	}

	/**
	 * 按机构查询下月生效分润模板
	 * @param commonReqEntity
	 * @return
	 * @author YQ
	 */
	public List<Map<String,Object>> getCurrentUnnoCostNextMonth(CommonReqEntity commonReqEntity){
		String agentId = commonReqEntity.getAgentId();
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		if(SysParam.AGENTID_PLUS.equals(agentId)){
			listMap = get10006ProfitNextMonth(commonReqEntity.getUnLvl(),commonReqEntity.getUnno(),commonReqEntity.getRebateType());
		}else if(SysParam.AGENTID_SYT.equals(agentId)){
			listMap = get10005ProfitNextMonth(commonReqEntity.getUnLvl(),commonReqEntity.getUnno(),commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
		}else if(SysParam.AGENTID_MD.equals(agentId)){
			listMap = get10000ProfitNextMonth(commonReqEntity.getUnLvl(),commonReqEntity.getUnno());
		}else{//活动
			listMap = getHuoDongProfitNextMonth(commonReqEntity.getUnLvl(),commonReqEntity.getUnno(),Integer.parseInt(agentId));
		}
		return listMap;
	}

	/**
	 * PLUS下月生效分润模板
	 * @param unLvl
	 * @param unno
	 * @param rebateType
	 * @return
	 * @author YQ
	 */
	private List<Map<String,Object>> get10006ProfitNextMonth(Integer unLvl,String unno,Integer rebateType){
		List<Map<String, Object>> listMap = new ArrayList<>();
		// 刷卡收款  扫码收款 云闪付(与扫码一致) 信用卡还款 提现比例
		if(null==unLvl||unLvl<=2){
			return listMap;
		}else{
			if(null==rebateType) {
				return listMap;
			}else{
				listMap = getListMap(1,unno,rebateType);
			}
		}
		return listMap;
	}

	/**
	 * 跑批钱包活动下月生效分润模板
	 * @param unLvl
	 * @param unno
	 * @param rebateType
	 * @return
	 * @author YQ
	 */
	private List<Map<String,Object>> getHuoDongProfitNextMonth(Integer unLvl,String unno,Integer rebateType){
		List<Map<String, Object>> listMap = new ArrayList<>();
		// 刷卡收款  扫码收款 云闪付(与扫码一致) 信用卡还款 提现比例
		if(null==unLvl||unLvl<=2){
			return listMap;
		}else{
			if(null==rebateType) {
				return listMap;
			}else{
				listMap = getListMap(4,unno,rebateType);
			}
		}
		return listMap;
	}

	/**
	 * SYT下月生效分润模板
	 * @param unLvl
	 * @param unno
	 * @return
	 */
	private List<Map<String,Object>> get10005ProfitNextMonth(Integer unLvl,String unno,Integer rebateType){
		// 扫码收款 提现比例
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>(16);
		List<HrtUnnoCostModel> hrtList = new ArrayList<HrtUnnoCostModel>(16);
		if(unLvl<=2){
			return listMap;
		}else{
			listMap = getListMap(2,unno,rebateType);
		}
		return listMap;
	}

	/**
	 * 秒到下月生效分润模板
	 * @param unLvl
	 * @param unno
	 * @return
	 */
	private List<Map<String,Object>> get10000ProfitNextMonth(Integer unLvl,String unno){
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>(16);
		List<Map<String,String>> list = new ArrayList<>();
		Map map = new HashMap();
		if(unLvl<=2){
			return listMap;
		}else{
			listMap = getListMap(3,unno,0);
			return listMap;
		}
	}

	/**
	 * 包装下月生效分润模板逻辑
	 * @param Type
	 * @param unno
	 * @param rebateType
	 * @author YQ
	 */
	private List<Map<String,Object>> getListMap(int type, String unno,Integer rebateType){
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>(16);
		Date now = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		//判断当前月份有无变更记录
		List<CheckMicroProfittemplateWModel> subList1 = new ArrayList<>();
		if(type==1){//PLUS
			subList1 = checkMicroProfittemplateWRepository.findNextMonthPlusProfitByUnnoAndRebateType(unno,rebateType,sd.format(now).substring(0,6));
		}else if(type==2){//SYT
			subList1 = checkMicroProfittemplateWRepository.findSytProfitNextMonthByUnno(unno,sd.format(now).substring(0,6),rebateType);
		}else if(type==3){//秒到
			subList1 = checkMicroProfittemplateWRepository.findMposNextMonthByUnno(unno, sd.format(now).substring(0,6));
		}else if(type==4){//活动
			subList1 = checkMicroProfittemplateWRepository.findNextMonthHuoDongProfitByUnnoAndRebateType(unno, rebateType, sd.format(now).substring(0,6));
		}
		if(subList1.size()<1){
			//当前月没有变更记录，判断当前日期是否为1号
			log.info("当前月没有变更记录，判断当前日期是否为1号");
			if(sd.format(now).substring(6,8).equals("01")){//如果是每个月的1号,查询上个月是否有变更记录
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(now); // 设置为当前时间
				calendar.add(Calendar.MONTH, -1); // 设置为上一个月
				now = calendar.getTime();
				List<CheckMicroProfittemplateWModel> subList2 = new ArrayList<>();
				if(type==1){//PLUS
					subList2 = checkMicroProfittemplateWRepository.findNextMonthPlusProfitByUnnoAndRebateType(unno,rebateType,sd.format(now).substring(0,6));
				}else if(type==2){//SYT
					subList2 = checkMicroProfittemplateWRepository.findSytProfitNextMonthByUnno(unno,sd.format(now).substring(0,6),rebateType);
				}else if(type==3){//秒到
					subList2 = checkMicroProfittemplateWRepository.findMposNextMonthByUnno(unno, sd.format(now).substring(0,6));
				}else if(type==4){//活动
					subList2 = checkMicroProfittemplateWRepository.findNextMonthHuoDongProfitByUnnoAndRebateType(unno, rebateType, sd.format(now).substring(0,6));
				}
				if(subList2.size()<1){
					//上个月没有变更记录，查询实时表
					List<CheckMicroProfittemplateModel> subList3 = new ArrayList<>();
					if(type==1){//PLUS
						subList3 = checkMicroProfittemplateRepository.findAllPlusProfitByUnnoAndRebateType(unno,rebateType);
					}else if(type==2){//SYT
						subList3 = checkMicroProfittemplateRepository.findSytProfitAllByUnno(unno,rebateType);
					}else if(type==3){//秒到
						subList3 = checkMicroProfittemplateRepository.findAllSelfByUnno(unno);
					}else if(type==4){//活动
						subList3 = checkMicroProfittemplateRepository.findAllHuoDongProfitByUnnoAndRebateType(unno,rebateType);
					}
					if(type==3){//秒到实时表，需要新建方法，原来的格式化方法只能格式化修改表的信息
						listMap = getNextMonthMapMpos1(subList3);
					}else{//收银台PLus和活动
						for(CheckMicroProfittemplateModel o:subList3){
							listMap = getCostMapNew(2,null,o,type==1?"10006":type==2?"10005":type==4?"10007":"");
						}
					}
				}else{
					//上个月有变更记录，以上个月的变更表记录为准
					log.info("上个月有变更记录，以上个月的变更表记录为准");
					for(CheckMicroProfittemplateWModel o:subList2){
						listMap = getNextMonthMapSM(o,type==1?"10006":type==2?"10005":type==4?"10007":"");
					}
				}
			}else{
				//如果不是1号，查询实时表
				log.info("不是1号，查询实时表");
				List<CheckMicroProfittemplateModel> subList3 = new ArrayList<>();
				if(type==1){//PLUS
					subList3 = checkMicroProfittemplateRepository.findAllPlusProfitByUnnoAndRebateType(unno,rebateType);
				}else if(type==2){//SYT
					subList3 = checkMicroProfittemplateRepository.findSytProfitAllByUnno(unno,rebateType);
				}else if(type==3){//秒到
					subList3 = checkMicroProfittemplateRepository.findAllSelfByUnno(unno);
				}else if(type==4){//活动
					subList3 = checkMicroProfittemplateRepository.findAllHuoDongProfitByUnnoAndRebateType(unno, rebateType);
				}
				if(type==3){
					listMap = getNextMonthMapMpos1(subList3);
				}else{
					for(CheckMicroProfittemplateModel o:subList3){
						listMap = getCostMapNew(2,null,o,type==1?"10006":type==2?"10005":type==4?"10007":"");
					}
				}
			}
		}else{
			//当前月份有变更记录展示当前月的变更记录
			if(type==3){
				listMap = getNextMonthMapMpos(subList1);
			}else{
				for(CheckMicroProfittemplateWModel o:subList1){
					listMap = getNextMonthMapSM(o,type==1?"10006":type==2?"10005":type==4?"10007":"");
				}
			}
		}
		return listMap;
	}

	/**
	 * 返回统一格式的分润信息SYT&PLUS  -新
	 * @param type 1:一代与运营中心,2:二代及之下
	 * @param hrtMap 一代与运营中心成本信息
	 * @param profit 二代及之下信息
	 * @param unno 机构号
	 * @return
	 */
	private List<Map<String, Object>> getCostMapNew(int type, HrtUnnoCostModel hrtMap, CheckMicroProfittemplateModel profit,String agentId){
		List<Map<String, Object>> listAll = new ArrayList<>();
		List<Map<String, String>> list = new ArrayList<>();
		if(type==1){
			if(agentId.equals("10006")||agentId.equals("10007")){
				Map<String, String> map1 = new HashMap<>();
				map1.put("text","刷卡收款");
				map1.put("rate", null==hrtMap.getDebitRate()?"0":new BigDecimal(hrtMap.getDebitRate().toString()).divide(new BigDecimal(100)).toString());
				map1.put("cash", hrtMap.getDebitFeeamt()==null?"0":hrtMap.getDebitFeeamt().toString());
				list.add(map1);
				Map<String, String> map2 = new HashMap<>();
				map2.put("text","手机Pay");
				map2.put("rate", hrtMap.getYsf_rate()==null?"0":new BigDecimal(hrtMap.getYsf_rate().toString()).divide(new BigDecimal(100)).toString());
				map2.put("cash","0");
				list.add(map2);
			}
			Map<String, String> map1 = new HashMap<>();
			map1.put("text","扫码1000以下0.38%");
			map1.put("rate", null==hrtMap.getCreditRate()?"0":new BigDecimal(hrtMap.getCreditRate().toString()).divide(new BigDecimal(100)).toString());
			String result = agentId.equals("10007")?null==hrtMap.getCashCost()?"0":hrtMap.getCashCost().toString():"0";
			map1.put("cash", result);
			list.add(map1);
			Map<String, String> map2 = new HashMap<>();
			map2.put("text","扫码1000以上0.38%");
			map2.put("rate", null==hrtMap.getWx_uprate()?"0":new BigDecimal(hrtMap.getWx_uprate().toString()).divide(new BigDecimal(100)).toString());
			map2.put("cash", null==hrtMap.getWx_upcash()?"0":hrtMap.getWx_upcash().toString());
			list.add(map2);
			Map<String, String> map3 = new HashMap<>();
			map3.put("text","扫码1000以上0.45%");
			map3.put("rate", null==hrtMap.getWx_uprate1()?"0":new BigDecimal(hrtMap.getWx_uprate1().toString()).divide(new BigDecimal(100)).toString());
			map3.put("cash", null==hrtMap.getWx_upcash1()?"0":hrtMap.getWx_upcash1().toString());
			list.add(map3);
			Map<String, String> map4 = new HashMap<>();
			map4.put("text","扫码1000以下0.45%");
			map4.put("rate", null==hrtMap.getZfb_rate()?"0":new BigDecimal(hrtMap.getZfb_rate().toString()).divide(new BigDecimal(100)).toString());
			map4.put("cash", null==hrtMap.getZfb_cash()?"0":hrtMap.getZfb_cash().toString());
			list.add(map4);
			Map<String, String> map5 = new HashMap<>();
			map5.put("text","银联二维码");
			map5.put("rate", null==hrtMap.getEwm_rate()?"0":new BigDecimal(hrtMap.getEwm_rate().toString()).divide(new BigDecimal(100)).toString());
			map5.put("cash", "0");
			list.add(map5);
			if(hrtMap.getTxnDetail() != null && isAlipay(hrtMap.getTxnDetail())){
				Map<String, String> map6 = new HashMap<>();
				map6.put("text","支付宝花呗");
				map6.put("rate", null==hrtMap.getHb_rate()?"0":new BigDecimal(hrtMap.getHb_rate().toString()).divide(new BigDecimal(100)).toString());
				map6.put("cash", null==hrtMap.getHb_cash()?"0":hrtMap.getHb_cash().toString());
				list.add(map6);
			}
			Map<String, Object> mapAll = new HashMap<String,Object>();
			mapAll.put("list", list);
			//2.2.7以后版本plus和收银台都返回活动类型
			mapAll.put("rebateType", hrtMap.getTxnDetail().toString());
			listAll.add(mapAll);
		}else if(type==2){//二代及以下模板表产品所取字段不同需要区分收银台和PLUS和活动
			try{
				if(agentId.equals("10006")||agentId.equals("10007")){
					Map<String, String> map1 = new HashMap<>();
					map1.put("text","刷卡收款");
					map1.put("rate", null==profit.getSubRate()?"0":profit.getSubRate().toString());
					map1.put("cash", null==profit.getCashAmt()?"0":profit.getCashAmt().toString());
					list.add(map1);
					Map<String, String> map2 = new HashMap<>();
					map2.put("text","手机Pay");
					map2.put("rate", null==profit.getCloudQuickRate()?"0":profit.getCloudQuickRate().toString());
					map2.put("cash","0");
					list.add(map2);
					Map<String, String> map3 = new HashMap<>();
					map3.put("text","扫码1000以下0.38%");
					map3.put("rate", null==profit.getScanRate()?"0":profit.getScanRate().toString());
					String result = agentId.equals("10007")?null==profit.getCashAmt1()?"0":profit.getCashAmt1().toString():"0";
					map3.put("cash", result);
					list.add(map3);
					Map<String, String> map4 = new HashMap<>();
					map4.put("text","扫码1000以上0.38%");
					map4.put("rate", null==profit.getCreditBankRate()?"0":profit.getCreditBankRate().toString());
					map4.put("cash", null==profit.getCashRate()?"0":profit.getCashRate().toString());
					list.add(map4);
					Map<String, String> map5 = new HashMap<>();
					map5.put("text","扫码1000以上0.45%");
					map5.put("rate", null==profit.getRuleThreshold()?"0":profit.getRuleThreshold().toString());
					map5.put("cash", null==profit.getStartAmount()?"0":profit.getStartAmount().toString());
					list.add(map5);
					Map<String, String> map6 = new HashMap<>();
					map6.put("text","扫码1000以下0.45%");
					map6.put("rate", null==profit.getScanRate1()?"0":profit.getScanRate1().toString());
					map6.put("cash", null==profit.getProfitPercent1()?"0":profit.getProfitPercent1().toString());
					list.add(map6);
				}else{
					Map<String, String> map1 = new HashMap<>();
					map1.put("text","扫码1000以下0.38%");
					map1.put("rate", null==profit.getSubRate()?"0":profit.getSubRate().toString());
					map1.put("cash", "0");
					list.add(map1);
					Map<String, String> map2 = new HashMap<>();
					map2.put("text","扫码1000以上0.38%");
					map2.put("rate", null==profit.getCreditBankRate()?"0":profit.getCreditBankRate().toString());
					map2.put("cash", null==profit.getCashRate()?"0":profit.getCashRate().toString());
					list.add(map2);
					Map<String, String> map3 = new HashMap<>();
					map3.put("text","扫码1000以上0.45%");
					map3.put("rate", null==profit.getScanRate()?"0":profit.getScanRate().toString());
					map3.put("cash", null==profit.getProfitPercent1()?"0":profit.getProfitPercent1().toString());
					list.add(map3);
					Map<String, String> map4 = new HashMap<>();
					map4.put("text","扫码1000以下0.45%");
					map4.put("rate", null==profit.getScanRate1()?"0":profit.getScanRate1().toString());
					map4.put("cash", null==profit.getCashAmt1()?"0":profit.getCashAmt1().toString());
					list.add(map4);
					if(profit.getProfitRule() != null && isAlipay(profit.getProfitRule())){
						Map<String, String> map5 = new HashMap<>();
						map5.put("text","支付宝花呗");
						map5.put("rate", null==profit.getHUABEIRATE()?"0":profit.getHUABEIRATE().toString());
						map5.put("cash", null==profit.getHUABEIFEE()?"0":profit.getHUABEIFEE().toString());
						list.add(map5);
					}
				}
				Map<String, String> map = new HashMap<>();
				map.put("text","银联二维码");
				map.put("rate", null==profit.getScanRate2()?"0":profit.getScanRate2().toString());
				map.put("cash", "0");
				list.add(map);
				Map<String, Object> mapAll = new HashMap<String,Object>();
				mapAll.put("list", list);
				//2.2.7之后的版本全部返回活动类型
				mapAll.put("rebateType", profit.getProfitRule().toString());
				listAll.add(mapAll);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return listAll;
	}

	/**
	 * 返回统一格式的PLUS,SYT下月生效分润信息
	 * @author YQ
	 */
	private List<Map<String, Object>> getNextMonthMapSM(CheckMicroProfittemplateWModel model,String agentId){
		List<Map<String, Object>> listAll = new ArrayList<>();
		List<Map<String, String>> list = new ArrayList<>();
		if(agentId.equals("10006")||agentId.equals("10007")){
			Map<String, String> map1 = new HashMap<>();
			map1.put("text","刷卡收款");
			map1.put("rate", null==model.getSubRate()?"0":model.getSubRate().toString());
			map1.put("cash", null==model.getCashAmt()?"0":model.getCashAmt().toString());
			list.add(map1);
			Map<String, String> map2 = new HashMap<>();
			map2.put("text","手机Pay");
			map2.put("rate", null==model.getCloudQuickRate()?"0":model.getCloudQuickRate().toString());
			list.add(map2);
			Map<String, String> map3 = new HashMap<>();
			map3.put("text","扫码1000以下0.38%");
			map3.put("rate", null==model.getScanRate()?"0":model.getScanRate().toString());
			String result = agentId.equals("10007")?null==model.getCashAmt1()?"0":model.getCashAmt1().toString():"0";
			map3.put("cash", result);
			list.add(map3);
			Map<String, String> map4 = new HashMap<>();
			map4.put("text","扫码1000以上0.38%");
			map4.put("rate", null==model.getCreditBankRate()?"0":model.getCreditBankRate().toString());
			map4.put("cash", null==model.getCashRate()?"0":model.getCashRate().toString());
			list.add(map4);
			Map<String, String> map5 = new HashMap<>();
			map5.put("text","扫码1000以上0.45%");
			map5.put("rate", null==model.getRuleThreshold()?"0":model.getRuleThreshold().toString());
			map5.put("cash", null==model.getStartAmount()?"0":model.getStartAmount().toString());
			list.add(map5);
			Map<String, String> map6 = new HashMap<>();
			map6.put("text","扫码1000以下0.45%");
			map6.put("rate", null==model.getScanRate1()?"0":model.getScanRate1().toString());
			map6.put("cash", null==model.getProfitPercent1()?"0":model.getProfitPercent1().toString());
			list.add(map6);
		}else{
			Map<String, String> map1 = new HashMap<>();
			map1.put("text","微信1000以下0.38%");
			map1.put("rate", null==model.getSubRate()?"0":model.getSubRate().toString());
			map1.put("cash", "0");
			list.add(map1);
			Map<String, String> map2 = new HashMap<>();
			map2.put("text","微信1000以上0.38%");
			map2.put("rate", null==model.getCreditBankRate()?"0":model.getCreditBankRate().toString());
			map2.put("cash", null==model.getCashRate()?"0":model.getCashRate().toString());
			list.add(map2);
			Map<String, String> map3 = new HashMap<>();
			map3.put("text","微信1000以上0.45%");
			map3.put("rate", null==model.getScanRate()?"0":model.getScanRate().toString());
			map3.put("cash", null==model.getProfitPercent1()?"0":model.getProfitPercent1().toString());
			list.add(map3);
			Map<String, String> map4 = new HashMap<>();
			map4.put("text","微信1000以下0.45%");
			map4.put("rate", null==model.getScanRate1()?"0":model.getScanRate1().toString());
			map4.put("cash", null==model.getCashAmt1()?"0":model.getCashAmt1().toString());
			list.add(map4);
			if(model.getProfitRule() != null && isAlipay(model.getProfitRule())){
				Map<String, String> map5 = new HashMap<>();
				map5.put("text","支付宝花呗");
				map5.put("rate", null==model.getHUABEIRATE()?"0":model.getHUABEIRATE().toString());
				map5.put("cash", null==model.getHUABEIFEE()?"0":model.getHUABEIFEE().toString());
				list.add(map5);
			}
		}
		Map<String, String> map = new HashMap<>();
		map.put("text","银联二维码");
		map.put("rate", null==model.getScanRate2()?"0":model.getScanRate2().toString());
		map.put("cash", "0");
		list.add(map);
		Map<String, Object> mapAll = new HashMap<String,Object>();
		mapAll.put("list", list);
		//2.2.7以后的版本全部返回活动类型
		mapAll.put("rebateType", model.getProfitRule().toString());
		listAll.add(mapAll);
		return listAll;
	}

	/**
	 * 下级代理分润模板展示
	 * @author YQ
	 */
	public List<Map<String,Object>> queryProfittemplateRecordByUnno(CommonReqEntity commonReqEntity){
		List<Map<String, Object>> resultList = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		List<Map<String, String>> list2 = new ArrayList<>();
		//当前模板数值最小值list(父级机构实时模板)
		if(commonReqEntity.getUnLvl()>2){
			List<CheckMicroProfittemplateModel> list1 = new ArrayList<>();
			if(SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId())){
				list1 = checkMicroProfittemplateRepository.findAllPlusProfitByUnnoAndRebateType(commonReqEntity.getUpperUnno(),commonReqEntity.getRebateType());
				for(CheckMicroProfittemplateModel o:list1){
					list2 = getMinMap(1,o,null,null,commonReqEntity.getAgentId());
				}
			}else if(SysParam.AGENTID_SYT.equals(commonReqEntity.getAgentId())){
				list1 = checkMicroProfittemplateRepository.findSytProfitAllByUnno(commonReqEntity.getUpperUnno(),commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
				for(CheckMicroProfittemplateModel o:list1){
					list2 = getMinMap(1,o,null,null,commonReqEntity.getAgentId());
				}
			}else if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){//秒到
				list1 = checkMicroProfittemplateRepository.findAllSelfByUnno(commonReqEntity.getUpperUnno());
				list2 = getMinMposMap(1,list1,null,null,1);
			}
			map.put("minList", list2);
		}else{//父级机构是一代
			List<HrtUnnoCostModel> list1 = new ArrayList<>();
			if(SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId())){
				list1 = hrtUnnoCostRepository.findAllHrtCostByUnnoAndRebateType(commonReqEntity.getUpperUnno(),commonReqEntity.getRebateType());
				for(HrtUnnoCostModel o:list1){
					list2 = getMinMap(3,null,null,o,commonReqEntity.getAgentId());
				}
			}else if(SysParam.AGENTID_SYT.equals(commonReqEntity.getAgentId())){
				list1 = hrtUnnoCostRepository.findSelfSytAllByUnnoAndTxnDetailEquals(commonReqEntity.getUpperUnno(),commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
				for(HrtUnnoCostModel o:list1){
					list2 = getMinMap(3,null,null,o,commonReqEntity.getAgentId());
				}
			}else if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){//秒到
				list1 = hrtUnnoCostRepository.findAllByUnnoEqualsAndStatusEquals(commonReqEntity.getUpperUnno(),1);
				list2 = getMinMposMap(2,null,list1,null,1);
			}
			map.put("minList", list2);
		}
		//系统定义最大值
		List<Map<String, String>> list3 = getMaxProfitByUpperUnno(commonReqEntity.getAgentId(),commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
		map.put("maxList", list3);
		//判断子代是否有模板
		List<CheckMicroProfittemplateModel> list4 = new ArrayList<>();
		if(SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId())){
			list4 = checkMicroProfittemplateRepository.findAllPlusProfitByUnnoAndRebateType(commonReqEntity.getUnno(),commonReqEntity.getRebateType());
		}else if(SysParam.AGENTID_SYT.equals(commonReqEntity.getAgentId())){
			list4 = checkMicroProfittemplateRepository.findSytProfitAllByUnno(commonReqEntity.getUnno(),commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
		}else if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){
			list4 = checkMicroProfittemplateRepository.findAllSelfByUnno(commonReqEntity.getUnno());
		};
		if(list4.size()<1){//子代没有模板，展示父级机构实时模板
			log.info("展示父级机构实时模板");
			map.put("profitList", list2);
		}else{//子代有模板
			log.info("子代有模板");
			List<Map<String,String>> listMap = new ArrayList<Map<String,String>>(16);
			Date now = new Date();
			SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
			String date = sd.format(now).substring(0,6);
			//判断当前月份有无变更记录
			List<CheckMicroProfittemplateWModel> subList1 = new ArrayList<CheckMicroProfittemplateWModel>();
			if(SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId())){
				subList1 = checkMicroProfittemplateWRepository.findNextMonthPlusProfitByUnnoAndRebateType(commonReqEntity.getUnno(),commonReqEntity.getRebateType(),date);
			}else if(SysParam.AGENTID_SYT.equals(commonReqEntity.getAgentId())){
				subList1 = checkMicroProfittemplateWRepository.findSytProfitNextMonthByUnno(commonReqEntity.getUnno(),sd.format(now).substring(0,6),commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
			}else if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){
				subList1 = checkMicroProfittemplateWRepository.findMposNextMonthByUnno(commonReqEntity.getUnno(),sd.format(now).substring(0,6));
			}
			if(subList1.size()<1){
				//当前月没有变更记录，判断当前日期是否为1号
				log.info("当前月没有变更记录，判断当前日期是否为1号");
				if(sd.format(now).substring(6,8).equals("01")){//如果是每个月的1号,查询上个月是否有变更记录
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(now); // 设置为当前时间
					calendar.add(Calendar.MONTH, -1); // 设置为上一个月
					now = calendar.getTime();
					List<CheckMicroProfittemplateWModel> subList2 = new ArrayList<>();
					if(SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId())){
						subList2 = checkMicroProfittemplateWRepository.findNextMonthPlusProfitByUnnoAndRebateType(commonReqEntity.getUnno(),commonReqEntity.getRebateType(),sd.format(now).substring(0,6));
					}else if(SysParam.AGENTID_SYT.equals(commonReqEntity.getAgentId())){
						subList2 = checkMicroProfittemplateWRepository.findSytProfitNextMonthByUnno(commonReqEntity.getUnno(),sd.format(now).substring(0,6),commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
					}else if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){
						subList2 = checkMicroProfittemplateWRepository.findMposNextMonthByUnno(commonReqEntity.getUnno(),sd.format(now).substring(0,6));
					}
					if(subList2.size()<1){
						//上个月没有变更记录，查询实时表list4
						if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){
							listMap = getMinMposMap(1,list4,null,null,1);
						}else{
							for(CheckMicroProfittemplateModel o:list4){
								listMap = getMinMap(1,o,null,null,commonReqEntity.getAgentId());
							}
						}
					}else{
						//上个月有变更记录，以上个月的变更表记录为准
						log.info("上个月有变更记录，以上个月的变更表记录为准");
						if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){
							listMap = getMinMposMap(3,null,null,subList2,1);
						}else{
							for(CheckMicroProfittemplateWModel o:subList2){
								listMap = getMinMap(2,null,o,null,commonReqEntity.getAgentId());
							}
						}
					}
				}else{
					//如果不是1号，查询实时表
					log.info("不是1号，查询实时表");
					List<CheckMicroProfittemplateModel> subList3 = new ArrayList<>();
					if(SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId())){
						subList3 = checkMicroProfittemplateRepository.findAllPlusProfitByUnnoAndRebateType(commonReqEntity.getUnno(),commonReqEntity.getRebateType());
					}else if(SysParam.AGENTID_SYT.equals(commonReqEntity.getAgentId())){
						subList3 = checkMicroProfittemplateRepository.findSytProfitAllByUnno(commonReqEntity.getUnno(),commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
					}else if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){
						subList3 = checkMicroProfittemplateRepository.findAllSelfByUnno(commonReqEntity.getUnno());
					}
					if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){
						listMap = getMinMposMap(1,list4,null,null,1);
					}else{
						for(CheckMicroProfittemplateModel o:subList3){
							listMap = getMinMap(1,o,null,null,commonReqEntity.getAgentId());
						}
					}
				}
			}else{
				//当前月份有变更记录展示当前月的变更记录
				if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){
					listMap = getMinMposMap(3,null,null,subList1,1);
				}else{
					for(CheckMicroProfittemplateWModel o:subList1){
						listMap = getMinMap(2,null,o,null,commonReqEntity.getAgentId());
					}
				}
			}
			map.put("profitList", listMap);
		}
		resultList.add(map);
		return resultList;
	}

	/*
	 * 取到当前分润取现费最大值    
	 * 按规定写死，如有变更需调整
	 */
	private List<Map<String, String>> getMaxProfitByUpperUnno(String agentId,Integer rebateType){
		List<Map<String, String>> list = new ArrayList<>();
		Map<String, String> map = new HashMap<>();
		if(!agentId.equals("10005")){//plus and 秒到 and 活动
			map.put("rate", "0.01");//刷卡
			map.put("cash", "3");
			map.put("rate1", "0.01");//手机pay
			map.put("cash1", null);
		}
		if(!agentId.equals("10000")){//plus and syt and 活动
			map.put("rate2", "0.01");//微信1000以下
			String result =  agentId.equals("10005")||agentId.equals("10006")?"0":"3";
			map.put("cash2", result );
			map.put("rate3", "0.01");//微信1000以上（含）0.38%
			map.put("cash3", "3");
			map.put("rate4", "0.01");//微信1000以上（含）0.45%
			map.put("cash4", "3");
			map.put("rate5", "0.01");//支付宝
			map.put("cash5", "3");
			if(agentId.equals("10005") && rebateType==21?false:isAlipay(rebateType)){//收银台活动带支付宝花呗
				map.put("rate9", "0.01");
				map.put("cash9", "3");
			}
		}
		if(agentId.equals("10000")){//秒到扫码
			map.put("rate6", "0.01");
			map.put("cash6", "3");
			map.put("rate8", "0.01");
			map.put("cash8", "3");
		}
		//银联二维码
		map.put("rate7", "0.01");
		map.put("cash7", null);
		list.add(map);
		return list;
	}

	/**
	 * 下月生效表二代秒到信息格式化
	 * @author YQ
	 */
	private List<Map<String,Object>> getNextMonthMapMpos(List<CheckMicroProfittemplateWModel> subList){
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>(16);
		List<Map<String,String>> list = new ArrayList<>();
		Map<String,CheckMicroProfittemplateWModel> micMap = new HashMap<>();
		for(CheckMicroProfittemplateWModel mic:subList){
			String key=mic.getMerchantType()+"|"+mic.getProfitType()+"|"+mic.getProfitRule();
			micMap.put(key,mic);
		}
		if(micMap.size()==0){
			return listMap;
		}
		Map<String, String> map1 = new HashMap<>();
		map1.put("text","刷卡收款");
		map1.put("rate", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getCreditBankRate()?
				micMap.get("3|1|1").getCreditBankRate().toString():"0");
		map1.put("cash", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getCashAmt()?
				micMap.get("3|1|1").getCashAmt().toString():"0");
		list.add(map1);
		//		Map<String, String> map2 = new HashMap<>();
		//		map2.put("text","扫码收款");
		//		map2.put("rate", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getScanRate()?
		//				micMap.get("3|1|1").getScanRate().toString():"0");
		//		map2.put("cash", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getCashAmt1()?
		//				micMap.get("3|1|1").getCashAmt1().toString():"0");
		//		list.add(map2);
		Map<String, String> map5 = new HashMap<>();
		map5.put("text","秒到扫码1000以下");
		map5.put("rate", micMap.containsKey("2|1|1")&&null!=micMap.get("2|1|1").getScanRate()?
				micMap.get("2|1|1").getScanRate().toString():"0");
		map5.put("cash", micMap.containsKey("2|1|1")&&null!=micMap.get("2|1|1").getCASHAMTUNDER()?
				micMap.get("2|1|1").getCASHAMTUNDER().toString():"0");
		list.add(map5);
		Map<String, String> map6 = new HashMap<>();
		map6.put("text","秒到扫码1000以上");
		map6.put("rate", micMap.containsKey("2|1|1")&&null!=micMap.get("2|1|1").getScanRate1()?
				micMap.get("2|1|1").getScanRate1().toString():"0");
		map6.put("cash", micMap.containsKey("2|1|1")&&null!=micMap.get("2|1|1").getCASHAMTABOVE()?
				micMap.get("2|1|1").getCASHAMTABOVE().toString():"0");
		list.add(map6);
		Map<String, String> map3 = new HashMap<>();
		map3.put("text","银联二维码");
		map3.put("rate", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getScanRate2()?
				micMap.get("3|1|1").getScanRate2().toString():"0");
		map3.put("cash", "0");
		list.add(map3);
		Map<String, String> map4 = new HashMap<>();
		map4.put("text","云闪付");
		map4.put("rate", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getCloudQuickRate()?
				micMap.get("3|1|1").getCloudQuickRate().toString():"0");
		map4.put("cash", "0");
		list.add(map4);
		Map<String, Object> mapResult = new HashMap<>();
		mapResult.put("list", list);
		mapResult.put("rebateType", "0");
		listMap.add(mapResult);
		return listMap;
	}

	/**
	 * 实时生效表二代秒到信息格式化
	 * @author YQ
	 */
	private List<Map<String,Object>> getNextMonthMapMpos1(List<CheckMicroProfittemplateModel> subList){
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>(16);
		List<Map<String,String>> list = new ArrayList<>();
		Map<String,CheckMicroProfittemplateModel> micMap = new HashMap<>();
		for(CheckMicroProfittemplateModel mic:subList){
			String key=mic.getMerchantType()+"|"+mic.getProfitType()+"|"+mic.getProfitRule();
			micMap.put(key,mic);
		}
		if(micMap.size()==0){
			return listMap;
		}
		Map<String, String> map1 = new HashMap<>();
		map1.put("text","刷卡收款");
		map1.put("rate", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getCreditBankRate()?
				micMap.get("3|1|1").getCreditBankRate().toString():"0");
		map1.put("cash", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getCashAmt()?
				micMap.get("3|1|1").getCashAmt().toString():"0");
		list.add(map1);
		//		Map<String, String> map2 = new HashMap<>();
		//		map2.put("text","扫码收款");
		//		map2.put("rate", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getScanRate()?
		//				micMap.get("3|1|1").getScanRate().toString():"0");
		//		map2.put("cash", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getCashAmt1()?
		//				micMap.get("3|1|1").getCashAmt1().toString():"0");
		//		list.add(map2);
		Map<String, String> map5 = new HashMap<>();
		map5.put("text","秒到扫码1000以下");
		map5.put("rate", micMap.containsKey("2|1|1")&&null!=micMap.get("2|1|1").getScanRate()?
				micMap.get("2|1|1").getScanRate().toString():"0");
		map5.put("cash", micMap.containsKey("2|1|1")&&null!=micMap.get("2|1|1").getCASHAMTUNDER()?
				micMap.get("2|1|1").getCASHAMTUNDER().toString():"0");
		list.add(map5);
		Map<String, String> map6 = new HashMap<>();
		map6.put("text","秒到扫码1000以上");
		map6.put("rate", micMap.containsKey("2|1|1")&&null!=micMap.get("2|1|1").getScanRate1()?
				micMap.get("2|1|1").getScanRate1().toString():"0");
		map6.put("cash", micMap.containsKey("2|1|1")&&null!=micMap.get("2|1|1").getCASHAMTABOVE()?
				micMap.get("2|1|1").getCASHAMTABOVE().toString():"0");
		list.add(map6);
		Map<String, String> map3 = new HashMap<>();
		map3.put("text","银联二维码");
		map3.put("rate", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getScanRate2()?
				micMap.get("3|1|1").getScanRate2().toString():"0");
		map3.put("cash", "0");
		list.add(map3);
		Map<String, String> map4 = new HashMap<>();
		map4.put("text","云闪付");
		map4.put("rate", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getCloudQuickRate()?
				micMap.get("3|1|1").getCloudQuickRate().toString():"0");
		map4.put("cash", "0");
		list.add(map4);
		Map<String, Object> mapResult = new HashMap<>();
		mapResult.put("list", list);
		mapResult.put("rebateType", "0");
		listMap.add(mapResult);
		return listMap;
	}

	/**
	 * 下级代理展示实时模板PLUS,SYT分润信息格式化
	 * @author YQ
	 */
	private List<Map<String, String>> getMinMap(int type,CheckMicroProfittemplateModel model,CheckMicroProfittemplateWModel model1,HrtUnnoCostModel model2,String agentId){
		List<Map<String, String>> list = new ArrayList<>();
		Map<String, String> map = new HashMap<>();
		if(type == 1){
			if(agentId.equals("10006")||agentId.equals("10007")){
				//刷卡收款
				map.put("rate", null==model.getSubRate()?"0":model.getSubRate().toString());
				if(model.getProfitRule()==null||model.getProfitRule().equals("")||model.getProfitRule() != 22)
					map.put("cash", null==model.getCashAmt()?"0":model.getCashAmt().toString());
				map.put("rate1", null==model.getCloudQuickRate()?"0":model.getCloudQuickRate().toString());//手机pay
				//微信1000以下（含）
				map.put("rate2", null==model.getScanRate()?"0":model.getScanRate().toString());
				String result = agentId.equals("10007")?null==model.getCashAmt1()?"0":model.getCashAmt1().toString():"0";
				map.put("cash2", result);
				//微信1000以上0.38%
				map.put("rate3", null==model.getCreditBankRate()?"0":model.getCreditBankRate().toString());
				map.put("cash3", null==model.getCashRate()?"0":model.getCashRate().toString());
				//微信1000以上（含）0.45%
				map.put("rate4", null==model.getRuleThreshold()?"0":model.getRuleThreshold().toString());
				map.put("cash4", null==model.getStartAmount()?"0":model.getStartAmount().toString());
				//支付宝
				map.put("rate5", null==model.getScanRate1()?"0":model.getScanRate1().toString());
				map.put("cash5", null==model.getProfitPercent1()?"0":model.getProfitPercent1().toString());
			}else if(agentId.equals("10005")){
				map.put("isAlipay", "0");//默认不展示支付宝花呗
				//微信1000以下（含）
				map.put("rate2", null==model.getSubRate()?"0":model.getSubRate().toString());
				map.put("cash2", "0");
				//微信1000以上0.38%
				map.put("rate3", null==model.getCreditBankRate()?"0":model.getCreditBankRate().toString());
				map.put("cash3", null==model.getCashRate()?"0":model.getCashRate().toString());
				//微信1000以上（含）0.45%
				map.put("rate4", null==model.getScanRate()?"0":model.getScanRate().toString());
				map.put("cash4", null==model.getProfitPercent1()?"0":model.getProfitPercent1().toString());
				//支付宝
				map.put("rate5", null==model.getScanRate1()?"0":model.getScanRate1().toString());
				map.put("cash5", null==model.getCashAmt1()?"0":model.getCashAmt1().toString());
				if(isAlipay(model.getProfitRule())){//收银台活动带支付宝花呗
					map.put("rate9", null==model.getHUABEIRATE()?"0":model.getHUABEIRATE().toString());
					map.put("cash9", null==model.getHUABEIFEE()?"0":model.getHUABEIFEE().toString());
					map.put("isAlipay", "1");
				}
			}
			//银联二维码
			map.put("rate7", null==model.getScanRate2()?"0":model.getScanRate2().toString());
			map.put("cash7", "0");
		}else if(type == 2){
			if(agentId.equals("10006")||agentId.equals("10007")){
				//刷卡收款
				map.put("rate", null==model1.getSubRate()?"0":model1.getSubRate().toString());
				map.put("cash", null==model1.getCashAmt()?"0":model1.getCashAmt().toString());
				map.put("rate1", null==model1.getCloudQuickRate()?"0":model1.getCloudQuickRate().toString());//手机pay
				//微信1000以下（含）
				map.put("rate2", null==model1.getScanRate()?"0":model1.getScanRate().toString());
				String result = agentId.equals("10007")?null==model1.getCashAmt1()?"0":model1.getCashAmt1().toString():"0";
				map.put("cash2", result);
				//微信1000以上0.38%
				map.put("rate3", null==model1.getCreditBankRate()?"0":model1.getCreditBankRate().toString());
				map.put("cash3", null==model1.getCashRate()?"0":model1.getCashRate().toString());
				//微信1000以上（含）0.45%
				map.put("rate4", null==model1.getRuleThreshold()?"0":model1.getRuleThreshold().toString());
				map.put("cash4", null==model1.getStartAmount()?"0":model1.getStartAmount().toString());
				//支付宝
				map.put("rate5", null==model1.getScanRate1()?"0":model1.getScanRate1().toString());
				map.put("cash5", null==model1.getProfitPercent1()?"0":model1.getProfitPercent1().toString());
			}else if(agentId.equals("10005")){
				map.put("isAlipay", "0");//默认不展示支付宝花呗
				//微信1000以下（含）
				map.put("rate2", null==model1.getSubRate()?"0":model1.getSubRate().toString());
				map.put("cash2", "0");
				//微信1000以上0.38%
				map.put("rate3", null==model1.getCreditBankRate()?"0":model1.getCreditBankRate().toString());
				map.put("cash3", null==model1.getCashRate()?"0":model1.getCashRate().toString());
				//微信1000以上（含）0.45%
				map.put("rate4", null==model1.getScanRate()?"0":model1.getScanRate().toString());
				map.put("cash4", null==model1.getProfitPercent1()?"0":model1.getProfitPercent1().toString());
				//支付宝
				map.put("rate5", null==model1.getScanRate1()?"0":model1.getScanRate1().toString());
				map.put("cash5", null==model1.getCashAmt1()?"0":model1.getCashAmt1().toString());
				if(isAlipay(model1.getProfitRule())){//收银台活动带支付宝花呗
					map.put("rate9", null==model1.getHUABEIRATE()?"0":model1.getHUABEIRATE().toString());
					map.put("cash9", null==model1.getHUABEIFEE()?"0":model1.getHUABEIFEE().toString());
					map.put("isAlipay", "1");
				}
			}
			//银联二维码
			map.put("rate7", null==model1.getScanRate2()?"0":model1.getScanRate2().toString());
			map.put("cash7", "0");
		}else if(type == 3){//一代成本
			log.info("一代成本");
			if(agentId.equals("10006")||agentId.equals("10007")){
				map.put("rate", null==model2.getDebitRate()?"0":new BigDecimal(model2.getDebitRate().toString()).divide(new BigDecimal(100)).toString());
				if(model2.getTxnDetail() != 22)
					map.put("cash", null==model2.getDebitFeeamt()?"0":model2.getDebitFeeamt().toString());
				map.put("rate1", null==model2.getYsf_rate()?"0":new BigDecimal(model2.getYsf_rate().toString()).divide(new BigDecimal(100)).toString());//手机pay
			}
			if(agentId.equals("10005"))
				map.put("isAlipay", "0");//默认不展示支付宝花呗
			//微信1000以下（含）
			map.put("rate2", null==model2.getCreditRate()?"0":new BigDecimal(model2.getCreditRate().toString()).divide(new BigDecimal(100)).toString());
			String result = agentId.equals("10007")?null==model2.getCashCost()?"0":model2.getCashCost().toString():"0";
			map.put("cash2", result);
			//微信1000以上0.38%
			map.put("rate3", null==model2.getWx_uprate()?"0":new BigDecimal(model2.getWx_uprate().toString()).divide(new BigDecimal(100)).toString());
			map.put("cash3", null==model2.getWx_upcash()?"0":model2.getWx_upcash().toString());
			//微信1000以上（含）0.45%
			map.put("rate4", null==model2.getWx_uprate1()?"0":new BigDecimal(model2.getWx_uprate1().toString()).divide(new BigDecimal(100)).toString());
			map.put("cash4", null==model2.getWx_upcash1()?"0":model2.getWx_upcash1().toString());
			//支付宝
			map.put("rate5", null==model2.getZfb_rate()?"0":new BigDecimal(model2.getZfb_rate().toString()).divide(new BigDecimal(100)).toString());
			map.put("cash5", null==model2.getZfb_cash()?"0":model2.getZfb_cash().toString());
			//银联二维码
			map.put("rate7", null==model2.getEwm_rate()?"0":new BigDecimal(model2.getEwm_rate().toString()).divide(new BigDecimal(100)).toString());
			map.put("cash7", "0");
			if(agentId.equals("10005") && isAlipay(model2.getTxnDetail())){//收银台活动带支付宝花呗
				map.put("rate9", null==model2.getHb_rate()?"0":new BigDecimal(model2.getHb_rate().toString()).divide(new BigDecimal(100)).toString());
				map.put("cash9", null==model2.getHb_cash()?"0":model2.getHb_cash().toString());
				map.put("isAlipay", "1");
			}
		}
		list.add(map);
		return list;
	}

	/**
	 * 下级代理展示最小值Mpos分润信息格式化
	 * @author YQ
	 */
	private List<Map<String, String>> getMinMposMap(int type,List<CheckMicroProfittemplateModel> subList,List<HrtUnnoCostModel> list,List<CheckMicroProfittemplateWModel> list1,int type1){
		List<Map<String, String>> listMap = new ArrayList<>();
		if(type==1){//父级机构是下级代理
			Map<String,CheckMicroProfittemplateModel> micMap = new HashMap<>();
			for(CheckMicroProfittemplateModel mic:subList){
				String key=mic.getMerchantType()+"|"+mic.getProfitType()+"|"+mic.getProfitRule();
				micMap.put(key,mic);
			}
			if(micMap.size()==0){
				return listMap;
			}
			Map<String, String> map = new HashMap<>();
			//刷卡收款
			map.put("rate", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getCreditBankRate()?
					micMap.get("3|1|1").getCreditBankRate().toString():"0");
			map.put("cash", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getCashAmt()?
					micMap.get("3|1|1").getCashAmt().toString():"0");
			if(type1==1){
				//秒到扫码
				map.put("rate6", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getScanRate()?
						micMap.get("3|1|1").getScanRate().toString():"0");
				map.put("cash6", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getCashAmt1()?
						micMap.get("3|1|1").getCashAmt1().toString():"0");
			}else{
				//秒到扫码1000以上
				map.put("rate8", micMap.containsKey("2|1|1")&&null!=micMap.get("2|1|1").getScanRate1()?
						micMap.get("2|1|1").getScanRate1().toString():"0");
				map.put("cash8", micMap.containsKey("2|1|1")&&null!=micMap.get("2|1|1").getCASHAMTABOVE()?
						micMap.get("2|1|1").getCASHAMTABOVE().toString():"0");
				//秒到扫码1000以下
				map.put("rate6", micMap.containsKey("2|1|1")&&null!=micMap.get("2|1|1").getScanRate()?
						micMap.get("2|1|1").getScanRate().toString():"0");
				map.put("cash6", micMap.containsKey("2|1|1")&&null!=micMap.get("2|1|1").getCASHAMTUNDER()?
						micMap.get("2|1|1").getCASHAMTUNDER().toString():"0");
			}
			//银联二维码
			map.put("rate7", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getScanRate2()?
					micMap.get("3|1|1").getScanRate2().toString():"0");
			//手机pay
			map.put("rate1", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getCloudQuickRate()?
					micMap.get("3|1|1").getCloudQuickRate().toString():"0");
			listMap.add(map);
		}else if(type==2){//父级机构是一代
			Map<String,HrtUnnoCostModel> map = new HashMap<String,HrtUnnoCostModel>();
			for(HrtUnnoCostModel hrt:list){
				String key=hrt.getMachineType()+"|"+hrt.getTxnType()+"|"+hrt.getTxnDetail();
				// 除去所有为空的数据
				if(hrt.getCreditRate()!=null || hrt.getDebitRate()!=null || hrt.getDebitFeeamt()!=null || hrt.getCashCost()!=null
						|| hrt.getCashRate()!=null) {
					map.put(key, hrt);
				}
			}
			if(map.size()==0 || !(map.containsKey("1|1|1") || map.containsKey("1|6|8") || map.containsKey("1|6|9")
					|| map.containsKey("1|6|10") || map.containsKey("1|4|5") || map.containsKey("1|3|4"))){
				return listMap;
			}
			Map<String, String> map1 = new HashMap<>();
			//刷卡收款
			map1.put("rate", map.containsKey("1|1|1")&&null!=map.get("1|1|1").getDebitRate()?
					map.get("1|1|1").getDebitRate().divide(new BigDecimal(100)).toString():"0");
			map1.put("cash", map.containsKey("1|1|1")&&null!=map.get("1|1|1").getCashCost()?
					map.get("1|1|1").getCashCost().toString():"0");
			if(type1==1){
				//扫码收款
				map1.put("rate6", map.containsKey("1|6|8")&&null!=map.get("1|6|8").getDebitRate()?
						map.get("1|6|8").getDebitRate().divide(new BigDecimal(100)).toString():"0");
				map1.put("cash6", map.containsKey("1|6|8")&&null!=map.get("1|6|8").getCashCost()?
						map.get("1|6|8").getCashCost().toString():"0");
			}else{
				//秒到扫码1000以下
				map1.put("rate6", map.containsKey("1|6|8")&&null!=map.get("1|6|8").getDebitRate()?
						map.get("1|6|8").getDebitRate().divide(new BigDecimal(100)).toString():"0");
				map1.put("cash6", map.containsKey("1|6|8")&&null!=map.get("1|6|8").getCashCost()?
						map.get("1|6|8").getCashCost().toString():"0");
				//秒到扫码1000以上
				map1.put("rate8", map.containsKey("1|6|9")&&null!=map.get("1|6|9").getDebitRate()?
						map.get("1|6|9").getDebitRate().divide(new BigDecimal(100)).toString():"0");
				map1.put("cash8", map.containsKey("1|6|9")&&null!=map.get("1|6|9").getCashCost()?
						map.get("1|6|9").getCashCost().toString():"0");
			}
			//银联二维码
			map1.put("rate7", map.containsKey("1|6|10")&&null!=map.get("1|6|10").getDebitRate()?
					map.get("1|6|10").getDebitRate().divide(new BigDecimal(100)).toString():"0");
			//云闪付
			map1.put("rate1", map.containsKey("1|4|5")&&null!=map.get("1|4|5").getDebitRate()?
					map.get("1|4|5").getDebitRate().divide(new BigDecimal(100)).toString():"0");
			listMap.add(map1);
		}else if(type==3){
			Map<String,CheckMicroProfittemplateWModel> micMap = new HashMap<>();
			for(CheckMicroProfittemplateWModel mic:list1){
				String key=mic.getMerchantType()+"|"+mic.getProfitType()+"|"+mic.getProfitRule();
				micMap.put(key,mic);
			}
			if(micMap.size()==0){
				return listMap;
			}
			Map<String, String> map = new HashMap<>();
			//刷卡收款
			map.put("rate", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getCreditBankRate()?
					micMap.get("3|1|1").getCreditBankRate().toString():"0");
			map.put("cash", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getCashAmt()?
					micMap.get("3|1|1").getCashAmt().toString():"0");
			if(type1==1){
				//秒到扫码
				map.put("rate6", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getScanRate()?
						micMap.get("3|1|1").getScanRate().toString():"0");
				map.put("cash6", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getCashAmt1()?
						micMap.get("3|1|1").getCashAmt1().toString():"0");
			}else{
				//秒到扫码1000以下
				map.put("rate6", micMap.containsKey("2|1|1")&&null!=micMap.get("2|1|1").getScanRate()?
						micMap.get("2|1|1").getScanRate().toString():"0");
				map.put("cash6", micMap.containsKey("2|1|1")&&null!=micMap.get("2|1|1").getCASHAMTUNDER()?
						micMap.get("2|1|1").getCASHAMTUNDER().toString():"0");
				//秒到扫码1000以上
				map.put("rate8", micMap.containsKey("2|1|1")&&null!=micMap.get("2|1|1").getScanRate1()?
						micMap.get("2|1|1").getScanRate1().toString():"0");
				map.put("cash8", micMap.containsKey("2|1|1")&&null!=micMap.get("2|1|1").getCASHAMTABOVE()?
						micMap.get("2|1|1").getCASHAMTABOVE().toString():"0");
			}
			//银联二维码
			map.put("rate7", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getScanRate2()?
					micMap.get("3|1|1").getScanRate2().toString():"0");
			//手机pay
			map.put("rate1", micMap.containsKey("3|1|1")&&null!=micMap.get("3|1|1").getCloudQuickRate()?
					micMap.get("3|1|1").getCloudQuickRate().toString():"0");
			listMap.add(map);
		}
		return listMap;
	}

	/**
	 * 变更分润模板记录
	 * @author YQ
	 */
	public int updateProfittemplateRecord(CommonReqEntity commonReqEntity){
		PlusCost cost = commonReqEntity.getCostList();
		Map<String, String> map = new HashMap<>();
		if(!commonReqEntity.getAgentId().equals(SysParam.AGENTID_SYT)){//plus and 秒到 and 活动
			//刷卡收款
			map.put("rate", cost.getRate().toString());
			if(!(commonReqEntity.getAgentId().equals("10006")&&commonReqEntity.getRebateType() == 22 ))
				map.put("cash", cost.getCash().toString());
			map.put("rate1", cost.getRate1().toString());//手机pay
		}
		if(!commonReqEntity.getAgentId().equals(SysParam.AGENTID_MD)){//plus and syt and 活动
			map.put("rate2", cost.getRate2().toString());//扫码1000以下0.38
			map.put("cash2", cost.getCash2().toString());
			map.put("rate3", cost.getRate3().toString());//扫码1000以上0.38%
			map.put("cash3", cost.getCash3().toString());
			map.put("rate4", cost.getRate4().toString());//扫码1000以上0.45%
			map.put("cash4", cost.getCash4().toString());
			map.put("rate5", cost.getRate5().toString());//扫码1000以下0.45
			map.put("cash5", cost.getCash5().toString());
			if(commonReqEntity.getAgentId().equals(SysParam.AGENTID_SYT) && 
					isSyt(commonReqEntity.getRebateType())){//收银台活动带支付宝花呗
				map.put("rate9", cost.getRate9().toString());
				map.put("cash9", cost.getCash9().toString());
			}
		}
		if(commonReqEntity.getAgentId().equals(SysParam.AGENTID_MD)){//秒到扫码
			map.put("rate6", cost.getRate6().toString());
			map.put("cash6", cost.getCash6().toString());
			map.put("rate8", cost.getRate8().toString());
			map.put("cash8", cost.getCash8().toString());
		}
		//银联二维码
		map.put("rate7", cost.getRate7().toString());
		//校验成本值是否符合
		List<CheckMicroProfittemplateModel> list1 = new ArrayList<>();
		List<Map<String, String>> list2 = new ArrayList<>();
		List<HrtUnnoCostModel> list3 = new ArrayList<>();
		if(commonReqEntity.getUnLvl()>2){//二级以下代理
			if(SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId())){
				list1 = checkMicroProfittemplateRepository.findAllPlusProfitByUnnoAndRebateType(commonReqEntity.getUpperUnno(),commonReqEntity.getRebateType());
				for(CheckMicroProfittemplateModel o:list1){
					list2 = getMinMap(1,o,null,null,commonReqEntity.getAgentId());
				}
			}else if(SysParam.AGENTID_SYT.equals(commonReqEntity.getAgentId())){
				list1 = checkMicroProfittemplateRepository.findSytProfitAllByUnno(commonReqEntity.getUpperUnno(),commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
				for(CheckMicroProfittemplateModel o:list1){
					list2 = getMinMap(1,o,null,null,commonReqEntity.getAgentId());
				}
			}else if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){//秒到
				list1 = checkMicroProfittemplateRepository.findAllSelfByUnno(commonReqEntity.getUpperUnno());
				list2 = getMinMposMap(1,list1,null,null,2);
			}else{//活动
				list1 = checkMicroProfittemplateRepository.findAllHuoDongProfitByUnnoAndRebateType(commonReqEntity.getUpperUnno(),Integer.parseInt(commonReqEntity.getAgentId()));
				for(CheckMicroProfittemplateModel o:list1){
					list2 = getMinMap(1,o,null,null,"10007");
				}
			}
		}else{//父级机构一代
			if(SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId())){
				list3 = hrtUnnoCostRepository.findAllHrtCostByUnnoAndRebateType(commonReqEntity.getUpperUnno(),commonReqEntity.getRebateType());
				for(HrtUnnoCostModel o:list3){
					list2 = getMinMap(3,null,null,o,commonReqEntity.getAgentId());
				}
			}else if(SysParam.AGENTID_SYT.equals(commonReqEntity.getAgentId())){
				list3 = hrtUnnoCostRepository.findSelfSytAllByUnnoAndTxnDetailEquals(commonReqEntity.getUpperUnno(),commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
				for(HrtUnnoCostModel o:list3){
					list2 = getMinMap(3,null,null,o,commonReqEntity.getAgentId());
				}
			}else if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){//秒到
				list3 = hrtUnnoCostRepository.findAllByUnnoEqualsAndStatusEquals(commonReqEntity.getUpperUnno(),1);
				list2 = getMinMposMap(2,null,list3,null,2);
			}else{//活动
				list3 = hrtUnnoCostRepository.findAllHrtCostByUnnoAndHuodongType(commonReqEntity.getUpperUnno(),Integer.parseInt(commonReqEntity.getAgentId()));
				for(HrtUnnoCostModel o:list3){
					list2 = getMinMap(3,null,null,o,"10007");
				}
			}
		}
		boolean flag1 = checkBigOrSmall(list2.get(0),map,commonReqEntity);
		boolean flag2 = checkBigOrSmall(map,getMaxProfitByUpperUnno(commonReqEntity.getAgentId(),commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType()).get(0),commonReqEntity);
		if(flag1==false||flag2==false){
			return 1;
		}
		log.info(commonReqEntity.getUnno()+"模板变更校验通过");
		//查询实时表
		List<CheckMicroProfittemplateModel> subList = new ArrayList<>();
		if(SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId())){//PLUS
			subList = checkMicroProfittemplateRepository.findAllPlusProfitByUnnoAndRebateType(commonReqEntity.getUnno(),commonReqEntity.getRebateType());
		}else if(SysParam.AGENTID_SYT.equals(commonReqEntity.getAgentId())){
			subList = checkMicroProfittemplateRepository.findSytProfitAllByUnno(commonReqEntity.getUnno(),commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
		}else if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){
			subList = checkMicroProfittemplateRepository.findAllSelfByUnno(commonReqEntity.getUnno());
		}else{//活动
			subList = checkMicroProfittemplateRepository.findAllHuoDongProfitByUnnoAndRebateType(commonReqEntity.getUnno(),Integer.parseInt(commonReqEntity.getAgentId()));
		}
		Map<String,CheckMicroProfittemplateModel> mdssMap = new HashMap<>();
		if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){
			for(CheckMicroProfittemplateModel mic:subList){
				String key=mic.getMerchantType()+"|"+mic.getProfitType()+"|"+mic.getProfitRule();
				mdssMap.put(key,mic);
			}
		}
		if(subList.size()>0){//已有成本，变更成本次月生效
			log.info("已有成本，变更成本次月生效");
			Date now = new Date();
			SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
			//判断当前月份有无变更记录
			List<CheckMicroProfittemplateWModel> subList1 = new ArrayList<>();
			if(SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId())){
				subList1 = checkMicroProfittemplateWRepository.findNextMonthPlusProfitByUnnoAndRebateType(commonReqEntity.getUnno(),commonReqEntity.getRebateType(),sd.format(now).substring(0,6));
			}else if(SysParam.AGENTID_SYT.equals(commonReqEntity.getAgentId())){
				subList1 = checkMicroProfittemplateWRepository.findSytProfitNextMonthByUnno(commonReqEntity.getUnno(),sd.format(now).substring(0,6),commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
			}else if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){
				subList1 = checkMicroProfittemplateWRepository.findMposNextMonthByUnno(commonReqEntity.getUnno(),sd.format(now).substring(0,6));
			}else{
				subList1 = checkMicroProfittemplateWRepository.findNextMonthHuoDongProfitByUnnoAndRebateType(commonReqEntity.getUnno(),Integer.parseInt(commonReqEntity.getAgentId()),sd.format(now).substring(0,6));
			}
			if(subList1.size()>0){
				//更新变更记录
				log.info("更新变更记录");
				if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){
					Map<String,CheckMicroProfittemplateWModel> micMap = new HashMap<>();
					for(CheckMicroProfittemplateWModel mic:subList1){
						String key=mic.getMerchantType()+"|"+mic.getProfitType()+"|"+mic.getProfitRule();
						micMap.put(key,mic);
					}
					if(micMap.size()==0){
						return 2;
					}
					updateMpos(3,micMap.get("3|1|1"),cost);
					updateMpos(2,micMap.get("2|1|1"),cost);
					updateMpos(1,micMap.get("1|1|1"),cost);
					//极光推送
					PushTaskThreadPools.addWorkerThread(new ReceiveRepayBDThread("秒到",commonReqEntity.getUnno(),1));
					savePushMessage(commonReqEntity,"秒到",1);
				}else if(SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId())){
					CheckMicroProfittemplateWModel model = checkMicroProfittemplateWRepository.findNextMonthPlusProfitByUnnoAndRebateType(commonReqEntity.getUnno(),commonReqEntity.getRebateType(),sd.format(now).substring(0,6)).get(0);
					model.setMerchantType(6);
					model.setProfitRule(commonReqEntity.getRebateType());
					model.setSubRate(cost.getRate());//刷卡收款
					if(commonReqEntity.getRebateType() != 22)
						model.setCashAmt(cost.getCash());
					model.setCloudQuickRate(cost.getRate1());//手机pay
					//微信1000以下(含)
					model.setScanRate(cost.getRate2());
					//微信1000以上0.38%
					model.setCreditBankRate(cost.getRate3());
					model.setCashRate(cost.getCash3());
					//微信1000以上0.45%
					model.setRuleThreshold(cost.getRate4());
					model.setStartAmount(cost.getCash4());
					//支付宝
					model.setScanRate1(cost.getRate5());
					model.setProfitPercent1(cost.getCash5());
					model.setScanRate2(cost.getRate7());//银联二维码
					model.setMatainDate(new Date());
					genericJpaDao.update(model);
					//极光推送
					PushTaskThreadPools.addWorkerThread(new ReceiveRepayBDThread("会员宝PLUS",commonReqEntity.getUnno(),1));
					savePushMessage(commonReqEntity,"会员宝PLUS",1);
				}else if(SysParam.AGENTID_SYT.equals(commonReqEntity.getAgentId())){
					CheckMicroProfittemplateWModel model = checkMicroProfittemplateWRepository.findSytProfitNextMonthByUnno(commonReqEntity.getUnno(),sd.format(now).substring(0,6),commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType()).get(0);
					model.setMerchantType(5);
					model.setProfitRule(commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
					//微信1000以下(含)
					model.setSubRate(cost.getRate2());
					//微信1000以上0.38%
					model.setCreditBankRate(cost.getRate3());
					model.setCashRate(cost.getCash3());
					//微信1000以上0.45%
					model.setScanRate(cost.getRate4());
					model.setProfitPercent1(cost.getCash4());
					//支付宝
					model.setScanRate1(cost.getRate5());
					model.setCashAmt1(cost.getCash5());
					model.setScanRate2(cost.getRate7());//银联二维码
					model.setMatainDate(new Date());
					if(commonReqEntity.getRebateType()==null?false:commonReqEntity.getRebateType()==21?false:
						isAlipay(commonReqEntity.getRebateType())){
						model.setHUABEIRATE(cost.getRate9());
						model.setHUABEIFEE(cost.getCash9());
					}
					genericJpaDao.update(model);
					//极光推送
					PushTaskThreadPools.addWorkerThread(new ReceiveRepayBDThread("收银台",commonReqEntity.getUnno(),1));
					savePushMessage(commonReqEntity,"收银台",1);
				}else{//活动
					CheckMicroProfittemplateWModel model = checkMicroProfittemplateWRepository.findNextMonthHuoDongProfitByUnnoAndRebateType(commonReqEntity.getUnno(),Integer.parseInt(commonReqEntity.getAgentId()),sd.format(now).substring(0,6)).get(0);
					model.setMerchantType(6);
					model.setProfitRule(Integer.parseInt(commonReqEntity.getAgentId()));
					model.setSubRate(cost.getRate());//刷卡收款
					model.setCashAmt(cost.getCash());
					model.setCloudQuickRate(cost.getRate1());//手机pay
					//微信1000以下(含)
					model.setScanRate(cost.getRate2());
					model.setCashAmt1(cost.getCash2());
					//微信1000以上0.38%
					model.setCreditBankRate(cost.getRate3());
					model.setCashRate(cost.getCash3());
					//微信1000以上0.45%
					model.setRuleThreshold(cost.getRate4());
					model.setStartAmount(cost.getCash4());
					//支付宝
					model.setScanRate1(cost.getRate5());
					model.setProfitPercent1(cost.getCash5());
					model.setScanRate2(cost.getRate7());//银联二维码
					model.setMatainDate(new Date());
					genericJpaDao.update(model);
					//极光推送
					PushTaskThreadPools.addWorkerThread(new ReceiveRepayBDThread("活动"+commonReqEntity.getAgentId(),
							commonReqEntity.getUnno(),1));
					savePushMessage(commonReqEntity,"活动"+commonReqEntity.getAgentId(),1);
				}
				return 0;
			}else{
				//插入变更记录
				log.info("插入变更记录");
				CheckMicroProfittemplateWModel model = new CheckMicroProfittemplateWModel();
				if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){
					CheckMicroProfittemplateModel model1 = mdssMap.get("3|1|1");
					BeanUtils.copyProperties(model1, model);
					//刷卡收款
					model.setCreditBankRate(cost.getRate());
					model.setCashAmt(cost.getCash());
					//秒到扫码
					model.setScanRate(cost.getRate6());
					model.setCASHAMTUNDER(cost.getCash6());
					model.setScanRate1(cost.getRate8());
					model.setCASHAMTABOVE(cost.getCash8());
					//手机pay
					model.setCloudQuickRate(cost.getRate1());
					model.setProfitPercent(new BigDecimal("1"));
					model.setProfitPercent1(new BigDecimal("1"));
					model.setScanRate2(cost.getRate7());//银联二维码
					model.setMatainDate(new Date());
					genericJpaDao.insert(model);
					CheckMicroProfittemplateModel model2 = mdssMap.get("2|1|1");
					CheckMicroProfittemplateWModel model3 = new CheckMicroProfittemplateWModel();
					BeanUtils.copyProperties(model2, model3);
					//刷卡收款0.72不修改费率
					model3.setCashAmt(cost.getCash());
					//秒到扫码
					model3.setScanRate(cost.getRate6());
					model3.setCASHAMTUNDER(cost.getCash6());
					model3.setScanRate1(cost.getRate8());
					model3.setCASHAMTABOVE(cost.getCash8());
					//手机pay
					model3.setCloudQuickRate(cost.getRate1());
					model3.setProfitPercent(new BigDecimal("1"));
					model3.setProfitPercent1(new BigDecimal("1"));
					model3.setScanRate2(cost.getRate7());//银联二维码
					model3.setMatainDate(new Date());
					genericJpaDao.insert(model3);
					CheckMicroProfittemplateWModel model4 = new CheckMicroProfittemplateWModel();
					CheckMicroProfittemplateModel model5 = mdssMap.get("1|1|1");
					BeanUtils.copyProperties(model5, model4);
					//刷卡收款1|1|1不做修改
					//秒到扫码
					model4.setScanRate(cost.getRate6());
					model4.setCASHAMTUNDER(cost.getCash6());
					model4.setScanRate1(cost.getRate8());
					model4.setCASHAMTABOVE(cost.getCash8());
					//手机pay
					model4.setCloudQuickRate(cost.getRate1());
					model4.setProfitPercent(new BigDecimal("1"));
					model4.setProfitPercent1(new BigDecimal("1"));
					model4.setScanRate2(cost.getRate7());//银联二维码
					model4.setMatainDate(new Date());
					genericJpaDao.insert(model4);
					//极光推送
					PushTaskThreadPools.addWorkerThread(new ReceiveRepayBDThread("秒到",commonReqEntity.getUnno(),1));
					savePushMessage(commonReqEntity,"秒到",1);
				}else{
					if(SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId())){
						model.setMerchantType(6);
						model.setProfitRule(commonReqEntity.getRebateType());
						model.setSubRate(cost.getRate());//刷卡收款
						if(commonReqEntity.getRebateType() != 22)
							model.setCashAmt(cost.getCash());
						model.setCloudQuickRate(cost.getRate1());//手机pay
						//微信1000以下(含)
						model.setScanRate(cost.getRate2());
						//微信1000以上0.38%
						model.setCreditBankRate(cost.getRate3());
						model.setCashRate(cost.getCash3());
						//微信1000以上0.45%
						model.setRuleThreshold(cost.getRate4());
						model.setStartAmount(cost.getCash4());
						//支付宝
						model.setScanRate1(cost.getRate5());
						model.setProfitPercent1(cost.getCash5());
					}else if(SysParam.AGENTID_SYT.equals(commonReqEntity.getAgentId())){
						model.setMerchantType(5);
						model.setProfitRule(commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
						//微信1000以下(含)
						model.setSubRate(cost.getRate2());
						//微信1000以上0.38%
						model.setCreditBankRate(cost.getRate3());
						model.setCashRate(cost.getCash3());
						//微信1000以上0.45%
						model.setScanRate(cost.getRate4());
						model.setProfitPercent1(cost.getCash4());
						//支付宝
						model.setScanRate1(cost.getRate5());
						model.setCashAmt1(cost.getCash5());
						if(isSyt(commonReqEntity.getRebateType())){
							model.setHUABEIRATE(cost.getRate9());
							model.setHUABEIFEE(cost.getCash9());
						}
					}else{//活动
						model.setMerchantType(6);
						model.setProfitRule(Integer.parseInt(commonReqEntity.getAgentId()));
						model.setSubRate(cost.getRate());//刷卡收款
						model.setCashAmt(cost.getCash());
						model.setCloudQuickRate(cost.getRate1());//手机pay
						//微信1000以下(含)
						model.setScanRate(cost.getRate2());
						model.setCashAmt1(cost.getCash2());
						//微信1000以上0.38%
						model.setCreditBankRate(cost.getRate3());
						model.setCashRate(cost.getCash3());
						//微信1000以上0.45%
						model.setRuleThreshold(cost.getRate4());
						model.setStartAmount(cost.getCash4());
						//支付宝
						model.setScanRate1(cost.getRate5());
						model.setProfitPercent1(cost.getCash5());
					}
					model.setAptId(subList.get(0).getAptId());
					model.setScanRate2(cost.getRate7());//银联二维码
					model.setUnno(commonReqEntity.getUpperUnno());
					model.setTempName(subList.get(0).getTempName());
					model.setMatainDate(new Date());
					model.setMatainType(subList.get(0).getMatainType());
					genericJpaDao.insert(model);
					//极光推送
					PushTaskThreadPools.addWorkerThread(new ReceiveRepayBDThread(model.getMerchantType()==5?"收银台":
						model.getProfitRule()==22||model.getProfitRule()==23?"会员宝PLUS":
							"活动"+commonReqEntity.getAgentId(),commonReqEntity.getUnno(),1));
					savePushMessage(commonReqEntity,model.getMerchantType()==5?"收银台":model.getProfitRule()==22||model.getProfitRule()==23?
							"会员宝PLUS":"活动"+commonReqEntity.getAgentId(),1);
				}
				return 0;
			}
		}else{
			//新增成本实时生效
			log.info("成本实时生效");
			CheckMicroProfittemplateModel model = new CheckMicroProfittemplateModel();
			CheckMicroProfittemplateModel returnModel = new CheckMicroProfittemplateModel();
			if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){
				model.setMerchantType(3);
				model.setProfitType(1);
				model.setProfitRule(1);
				model.setSettMethod("100000");
				//刷卡收款
				model.setCreditBankRate(cost.getRate());
				model.setCashAmt(cost.getCash());
				//秒到扫码
				model.setScanRate(cost.getRate6());
				model.setCASHAMTUNDER(cost.getCash6());
				model.setScanRate1(cost.getRate8());
				model.setCASHAMTABOVE(cost.getCash8());
				//手机pay
				model.setCloudQuickRate(cost.getRate1());
				//银联二维码
				model.setScanRate2(cost.getRate7());
				model.setMatainDate(new Date());
				model.setMatainType("P");
				model.setProfitPercent(new BigDecimal("1"));
				model.setProfitPercent1(new BigDecimal("1"));
				model.setUnno(commonReqEntity.getUpperUnno());
				model.setTempName(commonReqEntity.getUpperUnno()+Calendar.getInstance().getTimeInMillis());
				returnModel = checkMicroProfittemplateRepository.saveAndFlush(model);
				saveUnitProfitemplate(returnModel,commonReqEntity);
				saveProfitemplateLog(returnModel,commonReqEntity);
				CheckMicroProfittemplateModel model1 = new CheckMicroProfittemplateModel();
				BeanUtils.copyProperties(model,model1);
				model1.setAptId(null);
				model1.setMerchantType(2);
				CheckMicroProfittemplateModel returnModel1 = new CheckMicroProfittemplateModel();
				returnModel1 = checkMicroProfittemplateRepository.saveAndFlush(model1);
				saveUnitProfitemplate(returnModel1,commonReqEntity);
				saveProfitemplateLog(returnModel1,commonReqEntity);
				CheckMicroProfittemplateModel model2 = new CheckMicroProfittemplateModel();
				BeanUtils.copyProperties(model1,model2);
				model2.setAptId(null);
				model2.setSettMethod("000000");
				model2.setMerchantType(1);
				model2.setCreditBankRate(null);
				model2.setCashAmt(null);
				CheckMicroProfittemplateModel returnModel2 = new CheckMicroProfittemplateModel();
				returnModel2 = checkMicroProfittemplateRepository.saveAndFlush(model2);
				saveUnitProfitemplate(returnModel2,commonReqEntity);
				saveProfitemplateLog(returnModel2,commonReqEntity);
				//极光推送
				PushTaskThreadPools.addWorkerThread(new ReceiveRepayBDThread("秒到",commonReqEntity.getUnno(),2));
				savePushMessage(commonReqEntity,"秒到",2);
			}else{
				if(SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId())){
					model.setMerchantType(6);
					model.setProfitRule(commonReqEntity.getRebateType());
					model.setSubRate(cost.getRate());//刷卡收款
					if(commonReqEntity.getRebateType() != 22)
						model.setCashAmt(cost.getCash());
					model.setCloudQuickRate(cost.getRate1());//手机pay
					//微信1000以下(含)
					model.setScanRate(cost.getRate2());
					//微信1000以上0.38%
					model.setCreditBankRate(cost.getRate3());
					model.setCashRate(cost.getCash3());
					//微信1000以上0.45%
					model.setRuleThreshold(cost.getRate4());
					model.setStartAmount(cost.getCash4());
					//支付宝
					model.setScanRate1(cost.getRate5());
					model.setProfitPercent1(cost.getCash5());
				}else if(SysParam.AGENTID_SYT.equals(commonReqEntity.getAgentId())){
					model.setMerchantType(5);
					model.setProfitRule(commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
					//微信1000以下(含)
					model.setSubRate(cost.getRate2());
					//微信1000以上0.38%
					model.setCreditBankRate(cost.getRate3());
					model.setCashRate(cost.getCash3());
					//微信1000以上0.45%
					model.setScanRate(cost.getRate4());
					model.setProfitPercent1(cost.getCash4());
					//支付宝
					model.setScanRate1(cost.getRate5());
					model.setCashAmt1(cost.getCash5());
					if(isSyt(commonReqEntity.getRebateType())){
						model.setHUABEIRATE(cost.getRate9());
						model.setHUABEIFEE(cost.getCash9());
					}
				}else{//活动
					model.setMerchantType(6);
					model.setProfitRule(Integer.parseInt(commonReqEntity.getAgentId()));
					model.setSubRate(cost.getRate());//刷卡收款
					model.setCashAmt(cost.getCash());
					model.setCloudQuickRate(cost.getRate1());//手机pay
					//微信1000以下(含)
					model.setScanRate(cost.getRate2());
					model.setCashAmt1(cost.getCash2());
					//微信1000以上0.38%
					model.setCreditBankRate(cost.getRate3());
					model.setCashRate(cost.getCash3());
					//微信1000以上0.45%
					model.setRuleThreshold(cost.getRate4());
					model.setStartAmount(cost.getCash4());
					//支付宝
					model.setScanRate1(cost.getRate5());
					model.setProfitPercent1(cost.getCash5());
				}
				model.setScanRate2(cost.getRate7());//银联二维码
				model.setTempName(commonReqEntity.getUpperUnno()+Calendar.getInstance().getTimeInMillis());
				model.setUnno(commonReqEntity.getUpperUnno());
				model.setMatainDate(new Date());
				model.setMatainType("P");
				returnModel = checkMicroProfittemplateRepository.saveAndFlush(model);
				//极光推送
				PushTaskThreadPools.addWorkerThread(new ReceiveRepayBDThread(model.getMerchantType()==5?"收银台":
					model.getProfitRule()==22||model.getProfitRule()==23?"会员宝PLUS":
						"活动"+commonReqEntity.getAgentId(),commonReqEntity.getUnno(),2));
				savePushMessage(commonReqEntity,model.getMerchantType()==5?"收银台":
					model.getProfitRule()==22||model.getProfitRule()==23?"会员宝PLUS":
						"活动"+commonReqEntity.getAgentId(),2);
				//添加模板表
				saveUnitProfitemplate(returnModel,commonReqEntity);
				//添加历史模板记录表
				saveProfitemplateLog(returnModel,commonReqEntity);
			}
			return 0;
		}
	}

	/**
	 * 模板-机构关系表
	 * @author YQ
	 */
	private void saveUnitProfitemplate(CheckMicroProfittemplateModel returnModel,CommonReqEntity commonReqEntity){
		//插入机构模板关系表
		log.info("插入机构模板关系表");
		String sql = "insert into check_unit_profitemplate(unno,aptid,status,mataindate,matainuserId) "
				+ "values('"+commonReqEntity.getUnno()+"',"+returnModel.getAptId()+","
				+ "'1',sysdate,"+commonReqEntity.getUserId()+")";
		genericJpaDao.executeSql(sql);
	}

	/**
	 * 添加历史模板表
	 */
	private void saveProfitemplateLog(CheckMicroProfittemplateModel returnModel,CommonReqEntity commonReqEntity){
		log.info("插入历史模板表");
		CheckMicroProfitTemplateLogModel model2 = new CheckMicroProfitTemplateLogModel();
		PlusCost cost = commonReqEntity.getCostList();
		if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){
			model2.setMerchantType(returnModel.getMerchantType());
			model2.setProfitType(1);
			model2.setProfitRule(1);
			model2.setSettMethod(returnModel.getSettMethod());
			//刷卡收款
			if(returnModel.getMerchantType()!=1){
				model2.setCreditBankRate(cost.getRate());
				model2.setCashAmt(cost.getCash());
			}
			//秒到扫码
			model2.setScanRate(cost.getRate6());
			model2.setCASHAMTUNDER(cost.getCash6());
			model2.setScanRate1(cost.getRate8());
			model2.setCASHAMTABOVE(cost.getCash8());
			//手机pay
			model2.setCloudQuickRate(cost.getRate1());
		}else{
			if(SysParam.AGENTID_SYT.equals(commonReqEntity.getAgentId())){
				model2.setMerchantType(5);
				model2.setProfitRule(commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
				//微信1000以下(含)
				model2.setSubRate(cost.getRate2());
				//微信1000以上0.38%
				model2.setCreditBankRate(cost.getRate3());
				model2.setCashRate(cost.getCash3());
				//微信1000以上0.45%
				model2.setScanRate(cost.getRate4());
				model2.setProfitPercent1(cost.getCash4());
				//支付宝
				model2.setScanRate1(cost.getRate5());
				model2.setCashAmt1(cost.getCash5());
				if(isSyt(commonReqEntity.getRebateType())){
					model2.setHUABEIRATE(cost.getRate9());
					model2.setHUABEIFEE(cost.getCash9());
				}
			}else{
				model2.setMerchantType(6);
				if(SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId())){
					model2.setProfitRule(commonReqEntity.getRebateType());
				}else{
					model2.setProfitRule(Integer.parseInt(commonReqEntity.getAgentId()));
				}
				model2.setSubRate(cost.getRate());//刷卡收款
				model2.setCashAmt(cost.getCash());
				model2.setCloudQuickRate(cost.getRate1());//手机pay
				//微信1000以下(含)
				model2.setScanRate(cost.getRate2());
				if(!SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId()))
					model2.setCashAmt1(cost.getCash2());
				//微信1000以上0.38%
				model2.setCreditBankRate(cost.getRate3());
				model2.setCashRate(cost.getCash3());
				//微信1000以上0.45%
				model2.setRuleThreshold(cost.getRate4());
				model2.setStartAmount(cost.getCash4());
				//支付宝
				model2.setScanRate1(cost.getRate5());
				model2.setProfitPercent1(cost.getCash5());
			}
		}
		model2.setScanRate2(cost.getRate7());//银联二维码
		model2.setTempName(returnModel.getTempName());
		model2.setAptId(returnModel.getAptId());
		model2.setUnno(commonReqEntity.getUnno());
		model2.setMatainDate(new Date());
		model2.setStartdate(new Date());
		model2.setStatus(1);
		model2.setMatainType("P");
		genericJpaDao.insert(model2);
	}

	/**
	 * 模板变更校验（验证A是否大于B）
	 * A大于B返回false ，有空值返回false
	 * @author YQ 
	 */
	private boolean checkBigOrSmall(Map<String,String> mapa,Map<String,String> mapb,CommonReqEntity commonReqEntity){
		int a = 0;
		if(!commonReqEntity.getAgentId().equals("10005")){//plus and 秒到 and 活动
			if(mapa.get("rate")==null)a=1;
			a += new BigDecimal(mapa.get("rate")).compareTo(new BigDecimal(mapb.get("rate")))==1?1:0;
			if(!(commonReqEntity.getAgentId().equals("10006")&&commonReqEntity.getRebateType() == 22)){
				if(mapa.get("cash")==null)a=1;
				a += new BigDecimal(mapa.get("cash")).compareTo(new BigDecimal(mapb.get("cash")))==1?1:0;
			}
			if(mapa.get("rate1")==null)a=1;
			a += new BigDecimal(mapa.get("rate1")).compareTo(new BigDecimal(mapb.get("rate1")))==1?1:0;
		}
		if(!commonReqEntity.getAgentId().equals("10000")){//plus and syt and 活动
			if(mapa.get("rate2")==null)a=1;
			if(mapa.get("cash2")==null)a=1;
			if(mapa.get("rate3")==null)a=1;
			if(mapa.get("cash3")==null)a=1;
			if(mapa.get("rate4")==null)a=1;
			if(mapa.get("cash4")==null)a=1;
			if(mapa.get("rate5")==null)a=1;
			if(mapa.get("cash5")==null)a=1;
			a += new BigDecimal(mapa.get("rate2")).compareTo(new BigDecimal(mapb.get("rate2")))==1?1:0;
			a += new BigDecimal(mapa.get("cash2")).compareTo(new BigDecimal(mapb.get("cash2")))==1?1:0;
			a += new BigDecimal(mapa.get("rate3")).compareTo(new BigDecimal(mapb.get("rate3")))==1?1:0;
			a += new BigDecimal(mapa.get("cash3")).compareTo(new BigDecimal(mapb.get("cash3")))==1?1:0;
			a += new BigDecimal(mapa.get("rate4")).compareTo(new BigDecimal(mapb.get("rate4")))==1?1:0;
			a += new BigDecimal(mapa.get("cash4")).compareTo(new BigDecimal(mapb.get("cash4")))==1?1:0;
			a += new BigDecimal(mapa.get("rate5")).compareTo(new BigDecimal(mapb.get("rate5")))==1?1:0;
			a += new BigDecimal(mapa.get("cash5")).compareTo(new BigDecimal(mapb.get("cash5")))==1?1:0;
			if(commonReqEntity.getAgentId().equals("10005") 
					&& isSyt(commonReqEntity.getRebateType())){
				if(mapa.get("rate9")==null)a=1;
				if(mapa.get("cash9")==null)a=1;
				a += new BigDecimal(mapa.get("rate9")).compareTo(new BigDecimal(mapb.get("rate9")))==1?1:0;
				a += new BigDecimal(mapa.get("cash9")).compareTo(new BigDecimal(mapb.get("cash9")))==1?1:0;
			}
		}
		if(commonReqEntity.getAgentId().equals("10000")){//秒到扫码
			if(mapa.get("rate6")==null)a=1;
			if(mapa.get("cash6")==null)a=1;
			if(mapa.get("rate8")==null)a=1;
			if(mapa.get("cash8")==null)a=1;
			a += new BigDecimal(mapa.get("rate6")).compareTo(new BigDecimal(mapb.get("rate6")))==1?1:0;
			a += new BigDecimal(mapa.get("cash6")).compareTo(new BigDecimal(mapb.get("cash6")))==1?1:0;
			a += new BigDecimal(mapa.get("rate8")).compareTo(new BigDecimal(mapb.get("rate8")))==1?1:0;
			a += new BigDecimal(mapa.get("cash8")).compareTo(new BigDecimal(mapb.get("cash8")))==1?1:0;
		}
		if(mapa.get("rate7")==null)a=1;
		a += new BigDecimal(mapa.get("rate7")).compareTo(new BigDecimal(mapb.get("rate7")))==1?1:0;
		if(a==0)return true;
		return false;
	}

	/**
	 * 历史分润模板记录 -新
	 * @param commonReqEntity
	 * @author YQ
	 */
	public List<Map<String, Object>> getCurrentUnnoRecordCostNew(CommonReqEntity commonReqEntity){
		List<Map<String, Object>> resultList = new ArrayList<>();
		List<CheckMicroProfitTemplateLogModel> subList = new ArrayList<>();
		if(SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId())){
			subList = checkMicroProfittemplateLogRepository.findPlusProfitHistoryByUnnoAndRebateType(commonReqEntity.getUnno(), commonReqEntity.getRebateType());
		}else if(SysParam.AGENTID_SYT.equals(commonReqEntity.getAgentId())){
			subList = checkMicroProfittemplateLogRepository.findSytProfitHistoryByUnno(commonReqEntity.getUnno(), commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
		}else if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){
			subList = checkMicroProfittemplateLogRepository.findMposProfitHistoryByUnno(commonReqEntity.getUnno());
		}else{//活动
			subList = checkMicroProfittemplateLogRepository.findHuoDongProfitHistoryByUnnoAndRebateType(commonReqEntity.getUnno(), Integer.parseInt(commonReqEntity.getAgentId()));
		}
		SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
		try{
			for(CheckMicroProfitTemplateLogModel o:subList){
				List<Map<String, String>> list = new ArrayList<>();
				Map<String, Object> map = new HashMap<>();
				if(SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId())){
					map.put("time",sd.format(o.getStartdate())+"~"+(o.getEnddate()==null?"至今":sd.format(o.getEnddate())));
					Map<String, String> map6 = new HashMap<>();
					map6.put("text","刷卡收款");
					map6.put("rateAndCash", o.getSubRate()==null?"0":o.getSubRate().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"% + "+(o.getCashAmt()==null?"0":o.getCashAmt().toString()));
					list.add(map6);
					Map<String, String> map7 = new HashMap<>();
					map7.put("text","手机pay");
					map7.put("rateAndCash", o.getCloudQuickRate()==null?"0":o.getCloudQuickRate().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"%");
					list.add(map7);
					Map<String, String> map1 = new HashMap<>();
					map1.put("text","扫码1000以下0.38%");
					map1.put("rateAndCash", o.getScanRate()==null?"0":o.getScanRate().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"% + "+(o.getCashAmt1()==null?"0":o.getCashAmt1().toString()));
					list.add(map1);
					Map<String, String> map2 = new HashMap<>();
					map2.put("text","扫码1000以上0.38%");
					map2.put("rateAndCash", o.getCreditBankRate()==null?"0":o.getCreditBankRate().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"% + "+(o.getCashRate()==null?"0":o.getCashRate().toString()));
					list.add(map2);
					Map<String, String> map3 = new HashMap<>();
					map3.put("text","扫码1000以上0.45%");
					map3.put("rateAndCash",o.getRuleThreshold()==null?"0":o.getRuleThreshold().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"% + "+(o.getStartAmount()==null?"0":o.getStartAmount().toString()));
					list.add(map3);
					Map<String, String> map4 = new HashMap<>();
					map4.put("text","扫码1000以下0.45%");
					map4.put("rateAndCash", o.getScanRate1()==null?"0":o.getScanRate1().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"% + "+(o.getProfitPercent1()==null?"0":o.getProfitPercent1().toString()));
					list.add(map4);
					Map<String, String> map5 = new HashMap<>();
					map5.put("text","银联二维码");
					map5.put("rateAndCash", o.getScanRate2()==null?"0":o.getScanRate2().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"% + "+(o.getCashAmt2()==null?"0":o.getCashAmt2().toString()));
					list.add(map5);
					map.put("list", list);
					resultList.add(map);
				}else if(SysParam.AGENTID_SYT.equals(commonReqEntity.getAgentId())){
					map.put("time",sd.format(o.getStartdate())+"~"+(o.getEnddate()==null?"至今":sd.format(o.getEnddate())));
					Map<String, String> map1 = new HashMap<>();
					map1.put("text","扫码1000以下0.38%");
					map1.put("rateAndCash", o.getSubRate()==null?"0":o.getSubRate().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"% + "+(o.getCashAmt()==null?"0":o.getCashAmt().toString()));
					list.add(map1);
					Map<String, String> map2 = new HashMap<>();
					map2.put("text","扫码1000以上0.38%");
					map2.put("rateAndCash", o.getCreditBankRate()==null?"0":o.getCreditBankRate().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"% + "+(o.getCashRate()==null?"0":o.getCashRate().toString()));
					list.add(map2);
					Map<String, String> map3 = new HashMap<>();
					map3.put("text","扫码1000以上0.45%");
					map3.put("rateAndCash",o.getScanRate()==null?"0":o.getScanRate().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"% + "+(o.getProfitPercent1()==null?"0":o.getProfitPercent1().toString()));
					list.add(map3);
					Map<String, String> map4 = new HashMap<>();
					map4.put("text","扫码1000以下0.45%");
					map4.put("rateAndCash", o.getScanRate1()==null?"0":o.getScanRate1().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"% + "+(o.getCashAmt1()==null?"0":o.getCashAmt1().toString()));
					list.add(map4);
					if(o.getProfitRule() != null && isAlipay(o.getProfitRule())){
						Map<String, String> map5 = new HashMap<>();
						map5.put("text","支付宝花呗");
						map5.put("rateAndCash", null==o.getHUABEIRATE()?"0":o.getHUABEIRATE().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"% + "+(o.getHUABEIFEE()==null?"0":o.getHUABEIFEE().toString()));
						list.add(map5);
					}
					Map<String, String> map5 = new HashMap<>();
					map5.put("text","银联二维码");
					map5.put("rateAndCash", o.getScanRate2()==null?"0":o.getScanRate2().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"% + "+(o.getCashAmt2()==null?"0":o.getCashAmt2().toString()));
					list.add(map5);
					map.put("list", list);
					resultList.add(map);
				}else if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){
					if(o.getMerchantType()==3){
						map.put("time",sd.format(o.getStartdate())+"~"+(o.getEnddate()==null?"至今":sd.format(o.getEnddate())));
						Map<String, String> map1 = new HashMap<>();
						map1.put("text","刷卡收款");
						map1.put("rateAndCash", o.getCreditBankRate()==null?"0":o.getCreditBankRate().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"% + "+(o.getCashAmt()==null?"0":o.getCashAmt().toString()));
						list.add(map1);
						//判断是否为历史记录，历史记录展示"扫码收款"
						if(o.getCASHAMTUNDER()==null && o.getCASHAMTABOVE()==null){
							Map<String, String> map2 = new HashMap<>();
							map2.put("text","扫码收款");
							map2.put("rateAndCash", o.getScanRate()==null?"0":o.getScanRate().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"% + "+(o.getCashAmt1()==null?"0":o.getCashAmt1().toString()));
							list.add(map2);
						}else{
							Map<String, String> map5 = new HashMap<>();
							map5.put("text","秒到扫码1000以下");
							map5.put("rateAndCash", o.getScanRate()==null?"0":o.getScanRate().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"% + "+(o.getCASHAMTUNDER()==null?"0":o.getCASHAMTUNDER().toString()));
							list.add(map5);
							Map<String, String> map6 = new HashMap<>();
							map6.put("text","秒到扫码1000以上");
							map6.put("rateAndCash",null==o.getScanRate1()?"0":o.getScanRate1().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"% + "+(o.getCASHAMTABOVE()==null?"0":o.getCASHAMTABOVE().toString()));
							list.add(map6);
						}
						Map<String, String> map3 = new HashMap<>();
						map3.put("text","云闪付");
						map3.put("rateAndCash", o.getCloudQuickRate()==null?"0":o.getCloudQuickRate().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"%");
						list.add(map3);
						Map<String, String> map4 = new HashMap<>();
						map4.put("text","银联二维码");
						map4.put("rateAndCash", o.getScanRate2()==null?"0":o.getScanRate2().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"%");
						list.add(map4);
						map.put("list", list);
						resultList.add(map);
					}
				}else{
					map.put("time",sd.format(o.getStartdate())+"~"+(o.getEnddate()==null?"至今":sd.format(o.getEnddate())));
					Map<String, String> map6 = new HashMap<>();
					map6.put("text","刷卡收款");
					map6.put("rateAndCash", o.getSubRate()==null?"0":o.getSubRate().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"% + "+(o.getCashAmt()==null?"0":o.getCashAmt().toString()));
					list.add(map6);
					Map<String, String> map7 = new HashMap<>();
					map7.put("text","手机pay");
					map7.put("rateAndCash", o.getCloudQuickRate()==null?"0":o.getCloudQuickRate().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"%");
					list.add(map7);
					Map<String, String> map1 = new HashMap<>();
					map1.put("text","扫码1000以下0.38%");
					map1.put("rateAndCash", o.getScanRate()==null?"0":o.getScanRate().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"% + "+(o.getCashAmt1()==null?"0":o.getCashAmt1().toString()));
					list.add(map1);
					Map<String, String> map2 = new HashMap<>();
					map2.put("text","扫码1000以上0.38%");
					map2.put("rateAndCash", o.getCreditBankRate()==null?"0":o.getCreditBankRate().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"% + "+(o.getCashRate()==null?"0":o.getCashRate().toString()));
					list.add(map2);
					Map<String, String> map3 = new HashMap<>();
					map3.put("text","扫码1000以上0.45%");
					map3.put("rateAndCash",o.getRuleThreshold()==null?"0":o.getRuleThreshold().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"% + "+(o.getStartAmount()==null?"0":o.getStartAmount().toString()));
					list.add(map3);
					Map<String, String> map4 = new HashMap<>();
					map4.put("text","扫码1000以下0.45%");
					map4.put("rateAndCash", o.getScanRate1()==null?"0":o.getScanRate1().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"% + "+(o.getProfitPercent1()==null?"0":o.getProfitPercent1().toString()));
					list.add(map4);
					Map<String, String> map5 = new HashMap<>();
					map5.put("text","银联二维码");
					map5.put("rateAndCash", o.getScanRate2()==null?"0":o.getScanRate2().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"% + "+(o.getCashAmt2()==null?"0":o.getCashAmt2().toString()));
					list.add(map5);
					map.put("list", list);
					resultList.add(map);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultList;
	}

	/**
	 * 秒到更新方法
	 */
	private void updateMpos(int type,CheckMicroProfittemplateWModel model,PlusCost cost){
		if(type != 1){
			//刷卡收款
			if(type==3)
				model.setCreditBankRate(cost.getRate());
			model.setCashAmt(cost.getCash());
		}
		//秒到扫码
		model.setScanRate(cost.getRate6());
		model.setCASHAMTUNDER(cost.getCash6());
		model.setScanRate1(cost.getRate8());
		model.setCASHAMTABOVE(cost.getCash8());
		//银联二维码
		model.setScanRate2(cost.getRate7());
		//手机pay
		model.setCloudQuickRate(cost.getRate1());
		model.setMatainDate(new Date());
		genericJpaDao.update(model);
	}

	/**
	 * 保存极光推送消息
	 * @author YQ
	 */
	private void savePushMessage(CommonReqEntity commonReqEntity,String modelName,int type){
		String title = "";
		String message = "";
		if(type == 1){
			title = "模板修改通知";
			message = "您的上级已经成功为您修改了"+ modelName +"分润模板，请打开APP，在”分润模板管理“—”下月生效模板“里进行查看，该模板将在下月生效。";
		}else if(type == 2){
			title = "新增模板通知";
			message = "您的上级已经成功为您设置了"+ modelName +"分润模板，请打开APP，在”分润模板管理“—”我的分润模板“里进行查看，该模板已经生效。";
		}
		if(!"".equals(message) && !"".equals(title)){
			//保存推送信息
			PushMessage pushMessage = new PushMessage();
			pushMessage.setAgentId(commonReqEntity.getUnno());
			pushMessage.setContent(message);
			pushMessage.setMsgType(null);
			pushMessage.setTime(new Date());
			pushMessage.setTitle(title);
			pushMessage.setStatus(0);
			pushMessageRepository.save(pushMessage);
		}
	}

	/**
	 * 判断上送的活动类型是否为目标活动类型
	 * 20200330修改，放开权限，所有模板都可以修改
	 * @param rebateType
	 
	private boolean checkRebateType(String rebateType){
		String sql = "select * from bill_purconfigure where type = 3 and cby = '1' and status = 1 and valueInteger = :rebateType  ";
		HashMap<String, Object> map = new HashMap<>();
		map.put("rebateType", rebateType);
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql,map);
		if(list.size()>0)
			return true;
		return false;
	}
	*/

	/**
	 * 下级代理分润模板展示  --2.5版本返回秒到扫码，2.6版本返回拆分后扫码，2.7返回收银台活动拆分
	 * @author YQ
	 */
	public List<Map<String,Object>> queryProfittemplateRecordByUnnoFor2_7(int type,CommonReqEntity commonReqEntity){
		List<Map<String, Object>> resultList = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		List<Map<String, String>> list2 = new ArrayList<>();
		//当前模板数值最小值list(父级机构实时模板)
		if(commonReqEntity.getUnLvl()>2){
			List<CheckMicroProfittemplateModel> list1 = new ArrayList<>();
			if(SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId())){
				list1 = checkMicroProfittemplateRepository.findAllPlusProfitByUnnoAndRebateType(commonReqEntity.getUpperUnno(),commonReqEntity.getRebateType());
				for(CheckMicroProfittemplateModel o:list1){
					list2 = getMinMap(1,o,null,null,commonReqEntity.getAgentId());
				}
			}else if(SysParam.AGENTID_SYT.equals(commonReqEntity.getAgentId())){
				list1 = checkMicroProfittemplateRepository.findSytProfitAllByUnno(commonReqEntity.getUpperUnno(),commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
				for(CheckMicroProfittemplateModel o:list1){
					list2 = getMinMap(1,o,null,null,commonReqEntity.getAgentId());
				}
			}else if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){//秒到
				list1 = checkMicroProfittemplateRepository.findAllSelfByUnno(commonReqEntity.getUpperUnno());
				list2 = getMinMposMap(1,list1,null,null,type);
			}else{//活动
				list1 = checkMicroProfittemplateRepository.findAllHuoDongProfitByUnnoAndRebateType(commonReqEntity.getUpperUnno(),Integer.parseInt(commonReqEntity.getAgentId()));
				for(CheckMicroProfittemplateModel o:list1){
					list2 = getMinMap(1,o,null,null,"10007");
				}
			}
			map.put("minList", list2);
		}else{//父级机构是一代
			List<HrtUnnoCostModel> list1 = new ArrayList<>();
			if(SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId())){
				list1 = hrtUnnoCostRepository.findAllHrtCostByUnnoAndRebateType(commonReqEntity.getUpperUnno(),commonReqEntity.getRebateType());
				for(HrtUnnoCostModel o:list1){
					list2 = getMinMap(3,null,null,o,commonReqEntity.getAgentId());
				}
			}else if(SysParam.AGENTID_SYT.equals(commonReqEntity.getAgentId())){
				list1 = hrtUnnoCostRepository.findSelfSytAllByUnnoAndTxnDetailEquals(commonReqEntity.getUpperUnno(),commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
				for(HrtUnnoCostModel o:list1){
					list2 = getMinMap(3,null,null,o,commonReqEntity.getAgentId());
				}
			}else if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){//秒到
				list1 = hrtUnnoCostRepository.findAllByUnnoEqualsAndStatusEquals(commonReqEntity.getUpperUnno(),1);
				list2 = getMinMposMap(2,null,list1,null,type);
			}else{
				list1 = hrtUnnoCostRepository.findAllHrtCostByUnnoAndHuodongType(commonReqEntity.getUpperUnno(),Integer.parseInt(commonReqEntity.getAgentId()));
				for(HrtUnnoCostModel o:list1){
					list2 = getMinMap(3,null,null,o,"10007");
				}
			}
			map.put("minList", list2);
		}
		//系统定义最大值
		List<Map<String, String>> list3 = getMaxProfitByUpperUnno(commonReqEntity.getAgentId(),commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
		map.put("maxList", list3);
		//判断子代是否有模板
		List<CheckMicroProfittemplateModel> list4 = new ArrayList<>();
		if(SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId())){
			list4 = checkMicroProfittemplateRepository.findAllPlusProfitByUnnoAndRebateType(commonReqEntity.getUnno(),commonReqEntity.getRebateType());
		}else if(SysParam.AGENTID_SYT.equals(commonReqEntity.getAgentId())){
			list4 = checkMicroProfittemplateRepository.findSytProfitAllByUnno(commonReqEntity.getUnno(),commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
		}else if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){
			list4 = checkMicroProfittemplateRepository.findAllSelfByUnno(commonReqEntity.getUnno());
		}else{
			list4 = checkMicroProfittemplateRepository.findAllHuoDongProfitByUnnoAndRebateType(commonReqEntity.getUnno(),Integer.parseInt(commonReqEntity.getAgentId()));
		}
		if(list4.size()<1){//子代没有模板，展示父级机构实时模板
			log.info("展示父级机构实时模板");
			map.put("profitList", list2);
		}else{//子代有模板
			log.info("子代有模板");
			List<Map<String,String>> listMap = new ArrayList<Map<String,String>>(16);
			Date now = new Date();
			SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
			String date = sd.format(now).substring(0,6);
			//判断当前月份有无变更记录
			List<CheckMicroProfittemplateWModel> subList1 = new ArrayList<CheckMicroProfittemplateWModel>();
			if(SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId())){
				subList1 = checkMicroProfittemplateWRepository.findNextMonthPlusProfitByUnnoAndRebateType(commonReqEntity.getUnno(),commonReqEntity.getRebateType(),date);
			}else if(SysParam.AGENTID_SYT.equals(commonReqEntity.getAgentId())){
				subList1 = checkMicroProfittemplateWRepository.findSytProfitNextMonthByUnno(commonReqEntity.getUnno(),date,commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
			}else if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){
				subList1 = checkMicroProfittemplateWRepository.findMposNextMonthByUnno(commonReqEntity.getUnno(),date);
			}else{
				subList1 = subList1 = checkMicroProfittemplateWRepository.findNextMonthPlusProfitByUnnoAndRebateType(commonReqEntity.getUnno(),Integer.parseInt(commonReqEntity.getAgentId()),date);
			}
			if(subList1.size()<1){
				//当前月没有变更记录，判断当前日期是否为1号
				log.info("当前月没有变更记录，判断当前日期是否为1号");
				if(sd.format(now).substring(6,8).equals("01")){//如果是每个月的1号,查询上个月是否有变更记录
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(now); // 设置为当前时间
					calendar.add(Calendar.MONTH, -1); // 设置为上一个月
					now = calendar.getTime();
					List<CheckMicroProfittemplateWModel> subList2 = new ArrayList<>();
					if(SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId())){
						subList2 = checkMicroProfittemplateWRepository.findNextMonthPlusProfitByUnnoAndRebateType(commonReqEntity.getUnno(),commonReqEntity.getRebateType(),sd.format(now).substring(0,6));
					}else if(SysParam.AGENTID_SYT.equals(commonReqEntity.getAgentId())){
						subList2 = checkMicroProfittemplateWRepository.findSytProfitNextMonthByUnno(commonReqEntity.getUnno(),sd.format(now).substring(0,6),commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
					}else if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){
						subList2 = checkMicroProfittemplateWRepository.findMposNextMonthByUnno(commonReqEntity.getUnno(),sd.format(now).substring(0,6));
					}else{
						subList2 = checkMicroProfittemplateWRepository.findNextMonthHuoDongProfitByUnnoAndRebateType(commonReqEntity.getUnno(),Integer.parseInt(commonReqEntity.getAgentId()),sd.format(now).substring(0,6));
					}
					if(subList2.size()<1){
						//上个月没有变更记录，查询实时表list4
						if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){
							listMap = getMinMposMap(1,list4,null,null,type);
						}else{
							if(SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId())||
									SysParam.AGENTID_SYT.equals(commonReqEntity.getAgentId())){
								for(CheckMicroProfittemplateModel o:list4){
									listMap = getMinMap(1,o,null,null,commonReqEntity.getAgentId());
								}
							}else{
								for(CheckMicroProfittemplateModel o:list4){
									listMap = getMinMap(1,o,null,null,"10007");
								}
							}
						}
					}else{
						//上个月有变更记录，以上个月的变更表记录为准
						log.info("上个月有变更记录，以上个月的变更表记录为准");
						if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){
							listMap = getMinMposMap(3,null,null,subList2,type);
						}else{
							if(SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId())||
									SysParam.AGENTID_SYT.equals(commonReqEntity.getAgentId())){
								for(CheckMicroProfittemplateWModel o:subList2){
									listMap = getMinMap(2,null,o,null,commonReqEntity.getAgentId());
								}
							}else{
								for(CheckMicroProfittemplateModel o:list4){
									listMap = getMinMap(1,o,null,null,"10007");
								}
							}
						}
					}
				}else{
					//如果不是1号，查询实时表
					log.info("不是1号，查询实时表");
					List<CheckMicroProfittemplateModel> subList3 = new ArrayList<>();
					if(SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId())){
						subList3 = checkMicroProfittemplateRepository.findAllPlusProfitByUnnoAndRebateType(commonReqEntity.getUnno(),commonReqEntity.getRebateType());
					}else if(SysParam.AGENTID_SYT.equals(commonReqEntity.getAgentId())){
						subList3 = checkMicroProfittemplateRepository.findSytProfitAllByUnno(commonReqEntity.getUnno(),commonReqEntity.getRebateType()==null?21:commonReqEntity.getRebateType());
					}else if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){
						subList3 = checkMicroProfittemplateRepository.findAllSelfByUnno(commonReqEntity.getUnno());
					}else{
						subList3 = checkMicroProfittemplateRepository.findAllHuoDongProfitByUnnoAndRebateType(commonReqEntity.getUnno(),Integer.parseInt(commonReqEntity.getAgentId()));
					}
					if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){
						listMap = getMinMposMap(1,list4,null,null,type);
					}else{
						if(SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId())||
								SysParam.AGENTID_SYT.equals(commonReqEntity.getAgentId())){
							for(CheckMicroProfittemplateModel o:subList3){
								listMap = getMinMap(1,o,null,null,commonReqEntity.getAgentId());
							}
						}else{
							for(CheckMicroProfittemplateModel o:list4){
								listMap = getMinMap(1,o,null,null,"10007");
							}
						}
					}
				}
			}else{
				//当前月份有变更记录展示当前月的变更记录
				if(SysParam.AGENTID_MD.equals(commonReqEntity.getAgentId())){
					listMap = getMinMposMap(3,null,null,subList1,type);
				}else if(SysParam.AGENTID_PLUS.equals(commonReqEntity.getAgentId())
						||SysParam.AGENTID_SYT.equals(commonReqEntity.getAgentId())){//plus和syt
					for(CheckMicroProfittemplateWModel o:subList1){
						listMap = getMinMap(2,null,o,null,commonReqEntity.getAgentId());
					}
				}else{//活动
					for(CheckMicroProfittemplateWModel o:subList1){
						listMap = getMinMap(2,null,o,null,"10007");
					}
				}
			}
			map.put("profitList", listMap);
		}
		resultList.add(map);
		return resultList;
	}

	/**
	 * 校验SN是否符合规则
	 */
	public String checkSNForUpdateProfit(int type,CommonReqEntity commonReqEntity){
		String sql = "select rate,secondrate from bill_terminalinfo where ";
		HashMap<String, Object> map = new HashMap<>();
		if(type==1){
			sql += " sn>= :startSn and sn <= :endSn ";
			map.put("startSn", commonReqEntity.getStartTermid());
			map.put("endSn", commonReqEntity.getEndTermid());
		}else{
			String array[] = commonReqEntity.getSnList().split(",");
			String resultSn = "";
			for(int i = 0;i<array.length;i++){
				if(i==0){
					resultSn = "'"+array[i]+"'";
				}else{
					resultSn += ",'"+array[i]+"'";
				}
			}
			sql += " sn in ("+ resultSn +") ";
		}
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql,map);
		if(list==null)
			return "没有查询到区间内的终端";
		if(list.size()>10)
			return "区间内终端数量大于10台";
		map.put("unno", commonReqEntity.getUnno());
		List<Object[]> list1 = genericJpaDao.queryByNativeSql(sql+"and unno != :unno ",map);
		if(list1.size()>0)
			return "区间内终端包含不归属当前机构下终端";
		return "1";
	}

	/**
	 * 根据Sn返回SN的信息
	 */
	public List<Map<String,String>> querySNForUpdateProfit(CommonReqEntity commonReqEntity){
		String special = returnSpecial();
		String sql = "select sn, rebateType, rate*100, secondrate as cash, "
				+ "case when status = '2' or ism35 != '1' or rebateType in("+special
				+ ") then '1' else '0' end status from bill_terminalinfo "
				+ "where sn >= :startSn and sn <= :endSn and unno = :unno ";
		HashMap<String, Object> map = new HashMap<>();
		map.put("startSn", commonReqEntity.getStartTermid());
		map.put("endSn", commonReqEntity.getEndTermid());
		map.put("unno", commonReqEntity.getUnno());
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql,map);
		List<Map<String,String>> resultList = new ArrayList<>();
		for(int i = 0;i<list.size();i++){
			Map<String,String> map1 = new HashMap<>();
			map1.put("sn", list.get(i)[0].toString());
			map1.put("rebateType", list.get(i)[1]==null?"":list.get(i)[1].toString());
			map1.put("rate", list.get(i)[2]==null?"":list.get(i)[2].toString());
			map1.put("cash", list.get(i)[3]==null?"":list.get(i)[3].toString());
			map1.put("status", list.get(i)[4].toString());
			resultList.add(map1);
		}
		return resultList;
	}

	//返回活动类型所对应的版本（极速版、特殊活动、一般活动）
	public Map<String,String> querySNForActivityUpdateProfit(String array,String unno){
		Map<String,String> map = checkUpdate(array);
		if(map.get("code").equals("00")){
			//判断版本
			String sql =  "select case when rebateType in("
					+ "select valuestring from bill_purconfigure where type=8 and status = 1) then '2' "
					+ "when rebateType in(SELECT t.rebateType FROM (SELECT unno FROM ppusr.sys_unit b"
					+" where 1 = 1 and b.un_lvl = 1 start with b.unno = '"+unno+"'"
					+" connect by b.unno = prior b.upper_unit) a,bill_specialratecondition t "
					+" where a.unno = t.unno and t.rebateType is not null and t.rateType in('1','3')) then '3' else '1' end status,rebateType from bill_terminalinfo "
					+ "where sn in("+array+") ";
			List<Object[]> list = genericJpaDao.queryByNativeSql(sql);
			Map<String,String> map1 = new HashMap<>(); 
			map1.put("code", "00");
//			if(list.get(0)[0].toString().equals("3")){//如果状态3，则判断是否可以修改押金
//				String sql3 = " select case when depositamt is not null then '4' else '3' end status,unno "
//						+ "from bill_specialratecondition where unno in(SELECT unno FROM ppusr.sys_unit b where 1 = 1 "
//						+ "and b.un_lvl = 1 start with b.unno = '"+unno+"' connect by b.unno = prior b.upper_unit) "
//						+ "and rebateType = '"+list.get(0)[1]+"'";
//				List<Object[]> list3 = genericJpaDao.queryByNativeSql(sql3);
//				map1.put("type", list3.get(0)[0].toString());
//			}else{
				map1.put("type", list.get(0)[0].toString());
//			}
			map1.put("rebateType", list.get(0)[1].toString());
			return map1;
		}else{
			return map;
		}
	}

	//更新操作
	public boolean updateSnProfit(CommonReqEntity commonReqEntity){
	
		String sql = "update bill_terminalinfo t set t.rate="+commonReqEntity.getCostList().getRate().divide(new BigDecimal(100))+","
				+ "t.secondrate="+commonReqEntity.getCostList().getCash()+",t.maintainuid="+commonReqEntity.getUserId()+
				", t.maintaindate = sysdate,t.maintaintype='M' ";
//		if(commonReqEntity.getDeposit() != null && !commonReqEntity.getDeposit().equals("")){
//			sql += ",t.depositamt = '"+commonReqEntity.getDeposit()+"' ";
//		}
		sql += "where t.status!=2 and t.ism35=1 and t.sn in("+commonReqEntity.getSnList()+")";
		int i = genericJpaDao.executeSql(sql);
		if(i>0)
			return true;
		return false;
	}

	//判断活动费率和手续费是否为活动90
	public String isActive90(int type,CommonReqEntity commonReqEntity){
		String sql = "select rate,secondRate from sys_configure WHERE groupName = 'MicRate' "
				+ "and rate = "+commonReqEntity.getCostList().getRate().divide(new BigDecimal(100))+" "
				+ "and secondRate = "+commonReqEntity.getCostList().getCash();
		if(type==1){
			sql += " and minfo2 = '1' ";
		}else{
			sql += " and minfo2 != '1' ";
		}
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql);
		return list.size()>0?"1":"0";
	}

	//不允许修改费率的特殊活动类型
	private String returnSpecial(){
		//配置的特殊活动类型
		String specialSql = "SELECT UPLOAD_PATH FROM SYS_PARAM WHERE TITLE='NotUseActivityPlus' ";
		//收银台活动类型
		String sytSql = "select t.valueinteger,t.subtype from  bill_purconfigure t where t.type=3 and t.valuestring='rate' and t.prod='syt' ";
		List<Object[]> list = genericJpaDao.queryByNativeSql(sytSql);
		String syt = "";
		for(int i = 0;i<list.size();i++){
			syt += "," + list.get(i)[0].toString();
		}
		return genericJpaDao.queryByNativeSql(specialSql).get(0).toString()+syt;
	}

	//更新校验
	private Map<String,String> checkUpdate(String array){
		Map<String,String> map = new HashMap<String,String>();
		//活动类型相同返回code为00，否则返回99
		String checkSql = "select rebateType,count(*) from bill_terminalinfo where sn in("+array+") group by rebateType ";
		List<Object[]> list = genericJpaDao.queryByNativeSql(checkSql);
		if(list.size()>1){
			map.put("code", "99");
			map.put("msg", "修改费率范围不一致");
			return map;
		}else{
			//特殊机构限制操作(可优化：直接判断一代是否为指定机构)
			for (int i = 0; i < array.split(",").length; i++) {
				String bitd = array.split(",")[i];
				String sql ="select * from bill_terminalinfo t"+
						" where 1=1 and t.sn = "+bitd+" "+
						" and t.unno in (select s.unno from sys_unit s  start with unno in ('e21003')"+
						" connect by NOCYCLE  prior unno =  upper_unit)";
				List<Object[]> list2 = genericJpaDao.queryByNativeSql(sql);
				if(list2.size()>0){
					map.put("code", "99");
					map.put("msg", "当前费率不允许变更");
					return map;
				}
			}
			//判断是否为不允许修改的特殊活动类型
			String special = returnSpecial();
			for(String s : special.split(",")){
				if(s.equals(list.get(0)[0])){
					map.put("code", "99");
					map.put("msg", "不能修改该活动下的费率");
					return map;
				}
			}
			//判断设备是否激活，是否为传统设备
			String tidsSql = "Select * from bill_terminalinfo where sn in("+array+") and (status = '2' or ism35 != '1') ";
			List<Object[]> list1 = genericJpaDao.queryByNativeSql(tidsSql);
			if(list1.size()>0){
				map.put("code", "99");
				map.put("msg", "终端中包含传统终端或已激活的终端");
				return map;
			}
			map.put("code", "00");
			map.put("msg", "校验通过");
			return map;
		}
	}

	//普通活动下拉框
	public List<Map<String,String>> querySelectUpdateProfit(){
		String sql = "SELECT rate, secondrate as cash, minfo1 || '' as MINFO1 FROM sys_configure "
				+ "WHERE groupName = 'MicRate' order by MINFO2, MINFO1 ";
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql);
		List<Map<String,String>> list1 = new ArrayList<>();
		for(int i = 0;i<list.size();i++){
			Map<String,String> map = new HashMap<>();
			map.put("rate", list.get(i)[0]==null?"":new BigDecimal(list.get(i)[0].toString()).multiply(new BigDecimal(100)).setScale(2).toString());
			map.put("cash", list.get(i)[1]==null?"":list.get(i)[1].toString());
			map.put("MINFO1", list.get(i)[2]==null?"":list.get(i)[2].toString());
			list1.add(map);
		}
		return list1;
	}

	//极速版活动下拉框-费率
	public List<Map<String,String>> querySelectUpdateRate(){
		String sql = "SELECT DISTINCT minfo1,rate FROM sys_configure WHERE groupName='SN_Rate' order by minfo1 ";
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql);
		List<Map<String,String>> list1 = new ArrayList<>();
		for(int i = 0;i<list.size();i++){
			Map<String,String> map = new HashMap<>();
			map.put("rate", list.get(i)[0]==null?"":list.get(i)[0].toString());
			list1.add(map);
		}
		return list1;
	}

	//极速版活动下拉框-手续费
	public List<Map<String,String>> querySelectUpdateCash(CommonReqEntity commonReqEntity){
		String sql = "select DISTINCT secondRate,rate from sys_configure where groupName='SN_Rate' "
				+ "and minfo1 = " +commonReqEntity.getJisuCash()+ "order by secondRate" ;
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql);
		List<Map<String,String>> list1 = new ArrayList<>();
		for(int i = 0;i<list.size();i++){
			Map<String,String> map = new HashMap<>();
			map.put("cash", list.get(i)[0]==null?"":list.get(i)[0].toString());
			list1.add(map);
		}
		return list1;
	}

	//极速版费率手续费校验
	public String checkJisu(CommonReqEntity commonReqEntity){
		String sql = "select secondRate,rate from sys_configure where groupName='SN_Rate' "
				+ "and rate = "+commonReqEntity.getCostList().getRate()+" "
				+ "and secondRate = "+commonReqEntity.getCostList().getCash();
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql);
		return list.size()>0?"1":"0";
	}


	//特殊活动取值范围
	public Map<String,String> querySelectUpdateSpecial(CommonReqEntity commonReqEntity){
		String sql = "SELECT t.SPECIALRATE1,t.SPECIALRATE2,t.SPECIALAMT1,t.SPECIALAMT2 FROM (SELECT unno FROM ppusr.sys_unit b"
				+" where 1 = 1 and b.un_lvl = 1 start with b.unno = '"+commonReqEntity.getUnno()+"'"
				+" connect by b.unno = prior b.upper_unit) a,bill_specialratecondition t "
				+" where a.unno = t.unno and rebateType = '"+commonReqEntity.getRebateType()+"' and t.rateType in('1','3')";
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql);
		Map<String,String> map = new HashMap<>();
		if(list.size()>0){
			map.put("SPECIALRATE1", list.get(0)[0]==null?"":list.get(0)[0].toString());
			map.put("SPECIALRATE2", list.get(0)[1]==null?"":list.get(0)[1].toString());
			map.put("SPECIALAMT1", list.get(0)[2]==null?"":list.get(0)[2].toString());
			map.put("SPECIALAMT2", list.get(0)[3]==null?"":list.get(0)[3].toString());
		}
		return map;
	}
	
	//特殊活动押金范围
	public Map<String,String> querySelectUpdateSpecialDeposit(CommonReqEntity commonReqEntity){
		String sql = "SELECT t.depositamt,t.SPECIALAMT2 FROM (SELECT unno FROM ppusr.sys_unit b"
				+" where 1 = 1 and b.un_lvl = 1 start with b.unno = '"+commonReqEntity.getUnno()+"'"
				+" connect by b.unno = prior b.upper_unit) a,bill_specialratecondition t "
				+" where a.unno = t.unno and rebateType = '"+commonReqEntity.getRebateType()+"' and t.rateType in('1','3')";
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql);
		Map<String,String> map = new HashMap<>();
		if(list.size()>0){
			String resultStr = list.get(0)[0].toString();
			String[] array = resultStr.split(",");
			for(String deposit : array){
				map.put(deposit, deposit+"元");
			}
		}
		return map;
	}
	
	//=============================支付宝花呗================================
	private boolean isAlipay(Integer rebateType){
		if(rebateType==null)return false;
		String sql = "select t.valueinteger,t.subtype from  bill_purconfigure t where t.type=3 and t.valuestring='rate' and t.prod='syt' and t.subtype = 1";
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql);
		if(list.size()<1)
			return false;
		for(int i=0; i<list.size(); i++){
			if(list.get(0)[0].toString().equals(rebateType.toString())){
				return true;
			}
		}
		return false;
	}
	
	private boolean isSyt(Integer rebateType){
		if(rebateType==null)return false;
		if(rebateType==21)return false;
		return isAlipay(rebateType);
	}
	


}
