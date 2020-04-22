package com.hrtp.salesAppService.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrtp.salesAppService.dao.AppVersionRepository;
import com.hrtp.salesAppService.dao.MessageRepository;
import com.hrtp.salesAppService.dao.UnnoBannerRepository;
import com.hrtp.salesAppService.dao.UnnoNoticeRepository;
import com.hrtp.salesAppService.entity.appEntity.AppVersionEntity;
import com.hrtp.salesAppService.entity.appEntity.BillAgentInfoEntity;
import com.hrtp.salesAppService.entity.appEntity.MessageDetaliEntity;
import com.hrtp.salesAppService.entity.appEntity.MessageEntity;
import com.hrtp.salesAppService.entity.appEntity.ReqEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.appEntity.UnnoNoticeEntity;
import com.hrtp.salesAppService.entity.ormEntity.AppVersionModel;
import com.hrtp.salesAppService.entity.ormEntity.PushMessage;
import com.hrtp.salesAppService.entity.ormEntity.UnnoBannerModel;
import com.hrtp.system.common.GenericJpaDao;
import com.hrtp.system.costant.ReturnCode;

/**
 * HomePageService
 * description 首页服务
 * create by lxj 2018/8/24
 **/
@Service
public class HomePageService {
	@Autowired
	private UnnoNoticeRepository noticeRepository;
	@Autowired
	private UnnoBannerRepository unnoBannerRepository;
	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private AppVersionRepository appVersionRepository;
	@Autowired
	private GenericJpaDao genericJpaDao;

	public RespEntity getAnnounceList(UnnoNoticeEntity noticeEntity) {
		List<Object[]> noticeList = noticeRepository.findByStatusAndOrderByMsgsendDateDesc(1);
		return Optional.ofNullable(noticeList).map(list -> {
			if (list.size() == 0) return new RespEntity(ReturnCode.SUCCESS,"查询数据为空");
			Object[] model = list.get(0);
			UnnoNoticeEntity entity = new UnnoNoticeEntity();
			entity.setTitle(model[0] + "");
			entity.setTxt(model[1] + "");
			entity.setCreateDate(model[3]+"");
			BeanUtils.copyProperties(model, entity);
			return new RespEntity(ReturnCode.SUCCESS, "查询成功", entity);
		}).orElseGet(() -> new RespEntity(ReturnCode.SUCCESS, "查询结果不存在"));
	}

	public RespEntity getBannerList(BillAgentInfoEntity reqEntity) {
		if (StringUtils.isEmpty(reqEntity) || StringUtils.isEmpty(reqEntity.getUnno()))
			return new RespEntity(ReturnCode.FALT,
					"参数错误");
		List<UnnoBannerModel> list = unnoBannerRepository.findList();
		JSONObject returnBody = new JSONObject();
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject obj = new JSONObject();
			UnnoBannerModel model = list.get(i);
			obj.put("imageUrl", model.getImgUrl());
			if (null!=model.getDescription()&&model.getDescription().lastIndexOf("UNNO") > 0) {
				obj.put("description", model.getDescription() + reqEntity.getUnno());
			} else {
				obj.put("description", model.getDescription());
			}
			obj.put("isJump", Optional.ofNullable(list.get(i).getDescription()).isPresent() ? true : false);
			array.add(obj);
		}
		returnBody.put("imgList", array);
		return new RespEntity(ReturnCode.SUCCESS, "查询成功", returnBody);
	}

	public RespEntity getNoticeList(MessageEntity reqEntity) {
		if (StringUtils.isEmpty(reqEntity) || StringUtils.isEmpty(reqEntity.getUnno()) || StringUtils.isEmpty
				(reqEntity.getPage()) || StringUtils.isEmpty(reqEntity.getRows())) return new RespEntity(ReturnCode
						.FALT, "参数错误");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Pageable pageable = PageRequest.of(reqEntity.getPage() - 1, reqEntity.getRows());
		Page<PushMessage> pages = messageRepository.findByRowsAndPage(reqEntity.getUnno(), pageable);
		long toTal = pages.getTotalElements();
		List<PushMessage> content = pages.getContent();
		List<JSONObject> noticeList = new ArrayList<>();
		for (PushMessage p : content) {
			JSONObject obj = new JSONObject();
			obj.put("id", p.getId());
			obj.put("content", p.getContent());
			obj.put("time", format.format(p.getTime()));
			obj.put("title", p.getTitle());
			obj.put("msgType", p.getMsgType());
			obj.put("status",p.getStatus());
			noticeList.add(obj);
		}
		MessageEntity returnBody = new MessageEntity();
		returnBody.setCountTotal(Integer.valueOf(toTal + ""));
		returnBody.setNoticeList(noticeList);
		return new RespEntity(ReturnCode.SUCCESS, "查询成功", returnBody);
	}

	public RespEntity getVersion(ReqEntity reqEntity) {
		if (StringUtils.isEmpty(reqEntity) || StringUtils.isEmpty(reqEntity.getOs())) return new RespEntity
				(ReturnCode.FALT,"参数错误");
		String os = "ios".equals(reqEntity.getOs().toLowerCase())?"0":"1";
		AppVersionModel model = appVersionRepository.getByOsAndRownum(os);
		if(model == null) {
			return new RespEntity(ReturnCode.FALT,"未查询到版本信息");
		}
		AppVersionEntity appVersionEntity = new AppVersionEntity();
		appVersionEntity.setVersion(model.getVersion());
		appVersionEntity.setUrl(model.getUrl());
		appVersionEntity.setVersionDesc(model.getVersionDesc());
		appVersionEntity.setIsForceUpdate("1".equals(model.getIsForceUpdate())?true:false);
		return new RespEntity(ReturnCode.SUCCESS, "查询成功", appVersionEntity);
	}


	public RespEntity updateMsgStatus(MessageDetaliEntity reqEntity) {
		String id = reqEntity.getId();
		Integer status = reqEntity.getStatus();
		int result = messageRepository.updateStatusById(status,id);
		if(result == 1) {
			return new RespEntity(ReturnCode.SUCCESS,"更新成功");
		}

		return new RespEntity(ReturnCode.FALT, "更新失败");
	}

	/**
	 * 首页查询
	 * @param reqEntity
	 * @return
	 * @author yq
	 */
	public RespEntity queryAll(MessageDetaliEntity reqEntity) {
		RespEntity rs = new RespEntity();
		Integer flag = reqEntity.getFlag();
		List<Map<String, Object>> list1 = new ArrayList<Map<String,Object>>();
		String sql = "";
		Date date = new Date();
		Date date1 = new Date(System.currentTimeMillis()-1000*60*60*24);
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		String time = sd.format(date);
		String time1 = sd.format(date1);
		if(flag == 1) {
			//昨日今日交易金额汇总
			sql = "select a.txnDay,sum(nvl(b.txnamt,0)) forValue from "
					+ "(SELECT TO_CHAR(sysdate + ROWNUM - 2,'YYYYMMDD') as txnday FROM DUAL "
					+ "CONNECT BY ROWNUM <= 2) a "
					+ "left join (select * from pg_unnotxnfrun_sum where unno = :unno ) b " 
					+ "on a.txnday = b.txnday group by a.txnday ";
		}else if(flag == 2){
			//昨日今日分润汇总
			sql = "select a.txnDay,sum(nvl(b.txnprofit,0)+nvl(b.cashprofit,0)) forValue from "
					+ "(SELECT TO_CHAR(sysdate + ROWNUM - 3,'YYYYMMDD') as txnday FROM DUAL "
					+ "CONNECT BY ROWNUM <= 2) a "
					+ "left join (select * from pg_unnotxnfrun_sum where unno = :unno ) b " 
					+ "on a.txnday = b.txnday group by a.txnday ";
		}else if(flag ==3){
			//昨日今日激活终端数汇总
			sql = "select a.cashbackday,sum(nvl(b.actcashbacknum,0)) forValue from "
					+ "(SELECT TO_CHAR(sysdate + ROWNUM - 2,'YYYYMMDD') as cashbackday FROM DUAL CONNECT BY ROWNUM <= 2) a "
					+ "left join (select cashbackday,actcashbacknum from pg_unnocashback_sum where unno = :unno ) b "
					+ "on a.cashbackday = b.cashbackday group by a.cashbackday";
		}else if(flag == 4){
			//昨日今日开通商户数
			sql = "select a.merday,nvl(b.addmercount,0) forValue from "
					+ "(SELECT TO_CHAR(sysdate + ROWNUM - 2,'YYYYMMDD') as merday FROM DUAL CONNECT BY ROWNUM <= 2) a "
					+ "left join (select merday,addmercount from pg_merchinfo_sum where unno = :unno)b "
					+ "on a.merday = b.merday";
		}else{
			return new RespEntity(ReturnCode.FALT,"参数错误！");
		}
		HashMap<String, Object> map = new HashMap<String ,Object>();
		map.put("unno", reqEntity.getUnno());
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql, map);
		if(list.size()>0){
			for(int i = 0;i < list.size();i++){
				Map<String, Object> map1 = new HashMap<String,Object>();
				map1.put("day", list.get(i)[0].toString());
				map1.put("value", list.get(i)[1].toString());
				list1.add(map1);
			}
			rs.setReturnCode(ReturnCode.SUCCESS);
			rs.setReturnMsg("查询成功");
			rs.setReturnBody(list1);
		}else{
			return new RespEntity(ReturnCode.FALT,"数据错误！");
		}
		return rs;
	}

	/**
	 * 商户交易类型查询
	 * @param reqEntity
	 * @return
	 * @author yq
	 */
	public List<Map<String, String>> queryType(MessageDetaliEntity reqEntity) {
		List<Map<String, String>> list1 = new ArrayList<Map<String,String>>();
		String sql = "select unno,protype from sys_proparam where unno = :unno "
				+ "and protype != '0' order by protype";
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("unno", reqEntity.getUnno());
		Map<String , String> map1 = new LinkedHashMap<String,String>();
		map1.put("name","全部");
		map1.put("value","");
		list1.add(map1);
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql, map);
		for(int i=0;i<list.size();i++){
			String a = list.get(i)[1].toString();
			String text = "";
			if(a.equals("1")){
				text = "秒到";
			}else if(a.equals("2")){
				text = "收银台";
			}else if(a.equals("3")){
				text = "会员宝PLUS";
			}else{
				text = "活动"+a;
			}
			if(!text.equals("")){
				Map<String , String> map2 = new LinkedHashMap<String,String>();
				map2.put("name", text);
				map2.put("value",a);
				list1.add(map2);
			}
		}
		return list1;
	}
	/**
	 * 月交易汇总
	 * @param reqEntity
	 * @return
	 * @author yq
	 */
	public RespEntity queryTxnNew(MessageDetaliEntity reqEntity) {
		RespEntity rs = new RespEntity();
		List<Map<String, Object>> list1 = new ArrayList<Map<String,Object>>();
		String sql = "select nvl(sum(txnamt), 0),nvl(sum(txnamt_s), 0),nvl(sum(sspay_amt), 0),nvl(sum(ss_amt), 0),"
				+ "nvl(sum(zfb_amt), 0),nvl(sum(sspay_num), 0),nvl(sum(ss_num), 0),nvl(sum(zfb_num), 0),"
				+ "nvl(sum(ewm_amt), 0),nvl(sum(ewm_num), 0),nvl(sum(wx_amt), 0),nvl(sum(wx_num), 0),"
				+ "nvl(sum(wx_amt1), 0),nvl(sum(wx_num1), 0),nvl(sum(wx_amt2), 0),nvl(sum(wx_num2), 0),"
				+ "nvl(sum(mdsm_amt),0),nvl(sum(mdsm_num),0),nvl(sum(smabove_amt),0),nvl(sum(smabove_num),0),"
				+ "nvl(sum(huabei_amt),0),nvl(sum(huabei_num),0) from ("
				+ "select txnamt,txnamt_s,sspay_amt,ss_amt,case when protype = 1 then 0 else sm_amt end zfb_amt,"
				+ "case when protype = 1 then 0 else sm_num end zfb_num,sspay_num,ss_num,ewm_amt,ewm_num,wx_amt,"
				+ "wx_num,wx_amt1,wx_num1,wx_amt2,wx_num2,"
				+ "case when protype = 1 then sm_amt else 0 end mdsm_amt,"
				+ "case when protype = 1 then sm_num else 0 end mdsm_num,"
				+ "case when protype = 1 then wx_amt1 else 0 end smabove_amt,"
				+ "case when protype = 1 then wx_num1 else 0 end smabove_num,huabei_num,huabei_amt "
				+ "from pg_unnotxnfrun_sum where unno = :unno "
				+ "and to_date(txnday, 'yyyymmdd') >= to_date(:startDate, 'yyyymmdd') "
				+ "and to_date(txnday, 'yyyymmdd') < to_date(:endDate, 'yyyymmdd') ";
		if(reqEntity.getMerType() != null && !"".equals(reqEntity.getMerType())){
			if(!reqEntity.getMerType().equals("3")){
				sql += " and protype = '" +reqEntity.getMerType()+ "' ";
			}else{
				sql += " and protype in(3,22,23)  ";
			}
		}
		sql += ")";
		String unno = reqEntity.getUnno();
		//获取日期区间
		Map<String , Object> map = returnDate(reqEntity.getFlag());
		if(map==null){
			rs.setReturnCode(ReturnCode.FALT);
			rs.setReturnMsg("参数有误！");
			return rs;
		}
		HashMap<String, Object> map1 = new HashMap<String,Object>();
		map1.put("unno", unno);
		map1.put("startDate", map.get("startDate"));
		map1.put("endDate", map.get("endDate"));
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql, map1);
		list1 = resultForTxnNew(list,reqEntity,map1);
		String amt = returnTxnAmt(map.get("startDate1").toString(),map.get("endDate1").toString(),unno,reqEntity.getMerType()).get("amt");
		Map<String, Object> map2 = list1.get(0);
		map2.put("lastMonth", amt);
		list1 = new ArrayList<>();
		list1.add(map2);

		rs.setReturnCode(ReturnCode.SUCCESS);
		rs.setReturnMsg("查询成功");
		rs.setReturnBody(list1);
		return rs;
	}

	/**
	 * 月分润汇总
	 * @param reqEntity
	 * @return
	 * @author yq
	 */
	public RespEntity queryProfit(MessageDetaliEntity reqEntity) {
		RespEntity rs = new RespEntity();
		List<Map<String, Object>> list1 = new ArrayList<Map<String,Object>>();
		String sql = "select a.txnprofit,b.txnprofit_u from "
				+ "(select nvl(sum(txnprofit) + sum(cashprofit),0) txnprofit "
				+ "from pg_unnotxnfrun_sum where unno = :unno "
				+ "and txnday >= :startDate "
				+ "and txnday < :endDate ";
		if(reqEntity.getMerType() != null && !"".equals(reqEntity.getMerType())){
			if(!reqEntity.getMerType().equals("3")){
				sql += " and protype = '" +reqEntity.getMerType()+ "' ";
			}else{
				sql += " and protype in(3,22,23)  ";
			}
		}
		sql += ")a,"
				+ "(select nvl(sum(txnprofit)+sum(cashprofit),0)txnprofit_u from pg_unnotxnfrun_sum "
				+ "where unno in(select unno from sys_unit where upper_unit = :unno) "
				+ "and txnday >= :startDate and txnday < :endDate ";
		if(reqEntity.getMerType() != null && !"".equals(reqEntity.getMerType())){
			if(!reqEntity.getMerType().equals("3")){
				sql += " and protype = '" +reqEntity.getMerType()+ "' ";
			}else{
				sql += " and protype in(3,22,23)  ";
			}
		}
		sql += ")b";
		Map<String,Object> map = returnDate(reqEntity.getFlag());
		HashMap<String, Object> map1 = new HashMap<String,Object>();
		map1.put("unno", reqEntity.getUnno());
		map1.put("startDate", map.get("startDate"));
		map1.put("endDate", map.get("endDate"));
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql, map1);
		Map<String, Object> map2 = resultForProfit(list);
		list1.add(map2);

		rs.setReturnCode(ReturnCode.SUCCESS);
		rs.setReturnMsg("查询成功");
		rs.setReturnBody(list1);
		return rs;
	}	

	/**
	 * 月激活设备汇总
	 * @param reqEntity
	 * @return
	 * @author yq
	 */
	public RespEntity queryCashBack(MessageDetaliEntity reqEntity) {
		RespEntity rs = new RespEntity();
		List<Map<String, Object>> list1 = new ArrayList<Map<String,Object>>();
		String sql = "select nvl(sum(actcashbacknum),0) actcashbacknum,nvl(sum(actcashbacknum_s),0) actcashbacknum_s "
				+ "from pg_unnocashback_sum where unno = :unno and "
				+ "to_date(cashbackday,'yyyymmdd') >= to_date(:startDate,'yyyymmdd') "
				+ "and to_date(cashbackday,'yyyymmdd') < to_date(:endDate,'yyyymmdd')  ";
		if(reqEntity.getMerType() != null && !"".equals(reqEntity.getMerType())){
			if(!reqEntity.getMerType().equals("3")){
				sql += " and protype = '" +reqEntity.getMerType()+ "' ";
			}else{
				sql += " and protype in(3,22,23)  ";
			}
		}
		Map<String,Object> map = returnDate(reqEntity.getFlag());
		HashMap<String, Object> map1 = new HashMap<String,Object>();
		map1.put("unno", reqEntity.getUnno());
		map1.put("startDate", map.get("startDate"));
		map1.put("endDate", map.get("endDate"));
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql, map1);
		Map<String, Object> map2 = new HashMap<String ,Object>();
		map2.put("num", list.get(0)[0].toString());
		map2.put("nums", list.get(0)[1].toString());
		list1.add(map2);

		rs.setReturnCode(ReturnCode.SUCCESS);
		rs.setReturnMsg("查询成功");
		rs.setReturnBody(list1);
		return rs;
	}	

	/**
	 * 终端激活数时间线查询
	 * @param reqEntity
	 * @return
	 * @author yq
	 */
	public RespEntity queryCashBackDay(MessageDetaliEntity reqEntity) {
		RespEntity rs = new RespEntity();
		List<Map<String, String>> list1 = new ArrayList<Map<String,String>>();
		String sql = "select a.dt,nvl(b.actcashbacknum,0)cashnum,nvl(b.actcashbacknum_s,0)cashnum_s "
				+ "from ( "
				//指定时间范围内所有的日期序列
				+ "SELECT to_char(TO_DATE(:startDate ,'yyyymmdd') + LEVEL - 1 , 'yyyymmdd') dt "
				+ "FROM DUAL CONNECT BY LEVEL <= to_number(to_char(TO_DATE(:endDate ,'yyyymmdd')-1,'dd')) "
				+ ") a left join "
				//指定时间范围内的交易
				+ "(select cashbackday,sum(actcashbacknum) actcashbacknum,"
				+ "sum(actcashbacknum_s) actcashbacknum_s from pg_unnocashback_sum "
				+ "where unno = :unno and "
				+ "to_date(cashbackday,'yyyymmdd') >= to_date(:startDate,'yyyymmdd') "
				+ "and to_date(cashbackday,'yyyymmdd') < to_date(:endDate,'yyyymmdd') "; 
		if(reqEntity.getMerType() != null && !"".equals(reqEntity.getMerType())){
			if(!reqEntity.getMerType().equals("3")){
				sql += " and protype = '" +reqEntity.getMerType()+ "' ";
			}else{
				sql += " and protype in(3,22,23)  ";
			}
		}
		sql += "group by cashbackday ) b on a.dt = b.cashbackday order by a.dt";
		Map<String,Object> map = returnDate(reqEntity.getFlag());
		HashMap<String, Object> map1 = new HashMap<String,Object>();
		map1.put("unno", reqEntity.getUnno());
		map1.put("startDate", map.get("startDate"));
		map1.put("endDate", map.get("endDate2"));
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql, map1);
		for(int i=0;i<list.size();i++){
			Map<String,String> map2 = new HashMap<String,String>();
			map2.put("date", list.get(i)[0].toString());
			map2.put("num", list.get(i)[1].toString());
			map2.put("nums", list.get(i)[2].toString());
			list1.add(map2);
		}
		rs.setReturnCode(ReturnCode.SUCCESS);
		rs.setReturnMsg("查询成功");
		rs.setReturnBody(list1);
		return rs;
	}	

	/**
	 * 查询下级代理激活设备数量信息
	 * @param reqEntity
	 * @return unno,amtOrProfit，agentName
	 * @author yq
	 */
	public RespEntity queryUnnoCashBack(MessageDetaliEntity reqEntity) {
		RespEntity rs = new RespEntity();
		List<Map<String, String>> list1 = new ArrayList<Map<String,String>>();
		String sql = "select a.unno,a.un_name,sum(nvl(b.actcashbacknum, 0)) actcashbacknum "
				+ "from sys_unit a left join "
				+ "(select * from pg_unnocashback_sum where "
				+ "to_date(cashbackday,'yyyymmdd') >= to_date(:startDate,'yyyymmdd') "
				+ "and to_date(cashbackday,'yyyymmdd') < to_date(:endDate,'yyyymmdd') )b "
				+ "on a.unno = b.unno where "
				+ "a.upper_unit = :unno ";
		if(reqEntity.getMerType() != null && !"".equals(reqEntity.getMerType())){
			if(!reqEntity.getMerType().equals("3")){
				sql += " and b.protype = '" +reqEntity.getMerType()+ "' ";
			}else{
				sql += " and b.protype in(3,22,23)  ";
			}
		}
		sql += "group by a.unno,a.un_name order by actcashbacknum desc "; 
		Map<String,Object> map = returnDate(reqEntity.getFlag());
		HashMap<String, Object> map1 = new HashMap<String,Object>();
		map1.put("unno", reqEntity.getUnno());
		map1.put("startDate", map.get("startDate"));
		map1.put("endDate", map.get("endDate"));
		List<Object[]> list = genericJpaDao.queryByNativeSqlWithPageAndRows(sql,reqEntity.getPage(),15,map1);
		for(int i=0;i<list.size();i++){
			Map<String,String> map2 = new HashMap<String,String>();
			map2.put("unno", list.get(i)[0].toString());
			map2.put("agent_name", list.get(i)[1].toString());
			map2.put("cashbacknum", list.get(i)[2].toString());
			list1.add(map2);
		}
		rs.setReturnCode(ReturnCode.SUCCESS);
		rs.setReturnMsg("查询成功");
		rs.setReturnBody(list1);
		return rs;
	}	

	/**
	 * 交易、分润图
	 * @param reqEntity
	 * @return
	 * @author yq
	 */
	public RespEntity queryTxnProfit(MessageDetaliEntity reqEntity) {
		RespEntity rs = new RespEntity();
		Map<String ,Object> map = returnDate(reqEntity.getFlag());
		Map<String ,String> map1 = returnTxnAmt(map.get("startDate").toString(),map.get("endDate").toString(),reqEntity.getUnno(),reqEntity.getMerType());
		Map<String ,String> map2 = returnTxnAmt(map.get("startDate1").toString(),map.get("endDate1").toString(),reqEntity.getUnno(),reqEntity.getMerType());
		map1.put("lastAmt", map2.get("amt"));
		map1.put("lastAmtS", map2.get("amts"));
		map1.put("lastProfit", map2.get("profit"));
		map1.put("lastProfitS", map2.get("profits"));
		List<Map<String, String>> list1 = new ArrayList<Map<String,String>>();
		list1.add(map1);
		rs.setReturnCode(ReturnCode.SUCCESS);
		rs.setReturnMsg("查询成功！");
		rs.setReturnBody(list1);
		return rs;
	}

	/**
	 * 查询下级代理信息
	 * @param reqEntity
	 * @return unno,amtOrProfit，agentName
	 * @author yq
	 */
	public RespEntity queryUnno(MessageDetaliEntity reqEntity) {
		RespEntity rs = new RespEntity();
		List<Map<String, Object>> list1 = new ArrayList<Map<String,Object>>();
		List<Map<String, String>> list2 = new ArrayList<Map<String,String>>();
		String sql = "select a.unno,a.un_name,nvl(sum(b.txnamt), 0) txnamt,"
				+ "nvl(sum(b.txnprofit) + sum(b.cashprofit), 0) txnprofit "
				+ "from (select * from sys_unit where upper_unit = :unno ) a left join "
				+ "(select * from pg_unnotxnfrun_sum where "
				+ "txnday >= :startDate and txnday < :endDate ";
		if(reqEntity.getMerType() != null && !"".equals(reqEntity.getMerType())){
			if(!reqEntity.getMerType().equals("3")){
				sql += " and protype = '" +reqEntity.getMerType()+ "' ";
			}else{
				sql += " and protype in(3,22,23)  ";
			}
		}
		sql += ")b on a.unno = b.unno group by a.unno,a.un_name ";
		if(reqEntity.getsOrU()==1){
			sql += "order by txnamt desc "; 
		}else{
			sql += "order by txnprofit desc "; 
		}
		Map<String,Object> map = returnDate(reqEntity.getFlag());
		HashMap<String, Object> map1 = new HashMap<String,Object>();
		map1.put("unno", reqEntity.getUnno());
		map1.put("startDate", map.get("startDate"));
		map1.put("endDate", map.get("endDate"));
		List<Object[]> list = genericJpaDao.queryByNativeSqlWithPageAndRows(sql,reqEntity.getPage(),15, map1);
		Map<String, String> param = returnTxnAmt(map.get("startDate").toString(), map.get("endDate").toString(), reqEntity.getUnno(), reqEntity.getMerType());
		Map<String,Object> map3 = new HashMap<String,Object>();
		map3.put("txnamtAll", param.get("amtu"));
		map3.put("profitAll", param.get("profitu"));
		for(int i=0;i<list.size();i++){
			Map<String,String> map2 = new HashMap<String,String>();
			map2.put("unno", list.get(i)[0].toString());
			map2.put("agent_name", list.get(i)[1].toString());
			map2.put("txnamt", list.get(i)[2].toString());
			map2.put("txnprofit", list.get(i)[3].toString());
			list2.add(map2);
		}
		map3.put("list", list2);
		list1.add(map3);
		rs.setReturnCode(ReturnCode.SUCCESS);
		rs.setReturnMsg("查询成功");
		rs.setReturnBody(list1);
		return rs;
	}

	/**
	 * 交易分润时间线查询
	 * @param reqEntity
	 * @return
	 * @author yq
	 */
	public RespEntity queryDay(MessageDetaliEntity reqEntity) {
		RespEntity rs = new RespEntity();
		List<Map<String, String>> list1 = new ArrayList<Map<String,String>>();
		String sql = "select a.dt,nvl(b.txnamt,0),nvl(b.txnamt_s,0),nvl(b.txnprofit,0),"
				+ "(nvl(b.txnprofit,0)-nvl(b.txnprofit_u,0)) txnprofit_s "  
				+ "from ( "
				//指定时间范围内所有的日期序列
				+ "SELECT to_char(TO_DATE(:startDate ,'yyyymmdd') + LEVEL - 1 , 'yyyymmdd') dt "
				+ "FROM DUAL CONNECT BY LEVEL <= to_number(to_char(TO_DATE(:endDate ,'yyyymmdd')-1,'dd')) "
				+ ") a left join "
				//指定时间范围内的交易
				+ "(select a.txnday,nvl(sum(a.txnamt),0)txnamt ,nvl(sum(a.txnamt_s),0)txnamt_s, " 
				+ "nvl(sum(a.txnprofit)+sum(a.cashprofit),0) txnprofit,"
				+ "b.txnprofit_u " 
				+ "from pg_unnotxnfrun_sum a left join (select txnday,nvl(sum(txnprofit) + sum(cashprofit), 0) txnprofit_u "
				+ "from pg_unnotxnfrun_sum where unno in(select unno from sys_unit where upper_unit = :unno) "
				+ "and txnday >= :startDate and txnday < :endDate group by txnday ) b "
				+ "on a.txnday = b.txnday where a.unno = :unno and "
				+ "a.txnday >= :startDate and a.txnday < :endDate "; 
		if(reqEntity.getMerType() != null && !"".equals(reqEntity.getMerType())){
			if(!reqEntity.getMerType().equals("3")){
				sql += " and protype = '" +reqEntity.getMerType()+ "' ";
			}else{
				sql += " and protype in(3,22,23)  ";
			}
		}
		sql += "group by a.txnday,b.txnprofit_u ) b on a.dt = b.txnday order by a.dt";
		Map<String,Object> map = returnDate(reqEntity.getFlag());
		HashMap<String, Object> map1 = new HashMap<String,Object>();
		map1.put("unno", reqEntity.getUnno());
		map1.put("startDate", map.get("startDate"));
		map1.put("endDate", map.get("endDate2"));
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql, map1);
		int j = reqEntity.getsOrU();
		for(int i=0;i<list.size();i++){
			Map<String,String> map2 = new HashMap<String,String>();
			map2.put("date", list.get(i)[0].toString());
			map2.put("value", new BigDecimal(list.get(i)[j].toString()).compareTo(BigDecimal.ZERO)==-1?"0":list.get(i)[j].toString());
			list1.add(map2);
		}
		rs.setReturnCode(ReturnCode.SUCCESS);
		rs.setReturnMsg("查询成功");
		rs.setReturnBody(list1);
		return rs;
	}	

	//时间处理方法
	private Map<String,Object> returnDate(int flag){
		Map<String,Object> map = new HashMap<String,Object>();
		Date date = new Date(System.currentTimeMillis());
		Date date1 = new Date(System.currentTimeMillis()+1000*60*60*24);
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		//当前日期后一天
		map.put("tomorrow", sd.format(date1));
		//当前所在月份第一天
		String monthStart = sd.format(date).substring(0,6)+"01";
		map.put("monthStart", monthStart);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date); // 设置为当前时间
		calendar.add(Calendar.MONTH, 1); // 设置为下一个月
		date = calendar.getTime();
		//下个月同期
		String nextMonth = sd.format(date);
		map.put("nextMonth", nextMonth);
		//下个月第一天
		String nextMonthStart = nextMonth.substring(0,6)+"01";
		map.put("nextMonthStart", nextMonthStart);

		calendar.add(Calendar.MONTH, - 2); // 设置为上一个月
		date = calendar.getTime();
		//上个月第一天
		map.put("lastMonthStart", sd.format(date).substring(0,6)+"01");
		//上个月同期后一天
		calendar.add(Calendar.DATE, 1);
		date1 = calendar.getTime();
		map.put("lastMonth", sd.format(date1));
		//上两个月第一天
		calendar.add(Calendar.DATE, -1);
		calendar.add(Calendar.MONTH, - 1); // 设置为上两个月
		String lastTwoMonthStart = sd.format(calendar.getTime()).substring(0,6)+"01";
		map.put("lastTwoMonthStart", lastTwoMonthStart);
		//上三个月第一天
		calendar.add(Calendar.MONTH, - 1); // 设置为上两个月
		String lastThreeMonthStart = sd.format(calendar.getTime()).substring(0,6)+"01";
		map.put("lastThreeMonthStart", lastThreeMonthStart);
		if(flag==1){
			//前两个月
			map.put("startDate", map.get("lastTwoMonthStart"));
			map.put("endDate", map.get("lastMonthStart"));
			map.put("startDate1", map.get("lastThreeMonthStart"));
			map.put("endDate1", map.get("lastTwoMonthStart"));
			map.put("endDate2", map.get("lastMonthStart"));
		}else if(flag==2){
			//前一个月
			map.put("startDate", map.get("lastMonthStart"));
			map.put("endDate", map.get("monthStart"));
			map.put("startDate1", map.get("lastTwoMonthStart"));
			map.put("endDate1", map.get("lastMonthStart"));
			map.put("endDate2", map.get("monthStart"));
		}else if(flag==3){
			//当前月
			map.put("startDate", map.get("monthStart"));
			map.put("endDate", map.get("tomorrow"));
			map.put("startDate1", map.get("lastMonthStart"));
			map.put("endDate1", map.get("lastMonth"));
			map.put("endDate2", map.get("nextMonthStart"));
		}else{
			return null;
		}
		return map;
	}

	//交易结果处理
	private List<Map<String, Object>> resultForTxnNew(List<Object[]> list,MessageDetaliEntity reqEntity,HashMap<String, Object> param){
		List<Map<String, Object>> list1 = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listProp = new ArrayList<Map<String,Object>>();
		Map<String, String> map = new HashMap<>();
		boolean checkSyt = false;
		String sql = "select unno,case when protype = 2 then '2' "
				+ "when protype = 1 then '1' else '3' end protype "
				+ "from sys_proparam where unno = '"+reqEntity.getUnno()+"' and protype is not null ";
		if(reqEntity.getMerType() != null && !"".equals(reqEntity.getMerType())){
			if(!reqEntity.getMerType().equals("3")){
				sql += " and protype = '" +reqEntity.getMerType()+ "' ";
			}else{
				sql += " and protype in(3,22,23)  ";
			}
		}
		if(reqEntity.getMerType() == null || "".equals(reqEntity.getMerType()) || reqEntity.getMerType().equals("2")){//收银台需判断是否有花呗交易
			checkSyt = chectRebateType(reqEntity.getUnno());
		}
		List<Object[]> list2 = genericJpaDao.queryByNativeSql(sql, null);
		for(int i=0;i<list2.size();i++){
			String protype = list2.get(i)[1].toString();
			map.put(protype, "");
		}
		String payamtProp = "0.0";
		String paynumProp = "0.0";
		String ssamtProp = "0.0";
		String ssnumProp = "0.0";
		String zfbnumProp = "0.0";
		String zfbamtProp = "0.0";
		String ewmamtProp = "0.0";
		String ewmnumProp = "0.0";
		String wxamtProp = "0.0";
		String wxNumProp = "0.0";
		String wxamt1Prop = "0.0";
		String wxNum1Prop = "0.0";
		String wxamt2Prop = "0.0";
		String wxNum2Prop = "0.0";
		String mdsmamtProp = "0.0";
		String mdsmnumProp = "0.0";
		String mdsmaboveamtProp = "0.0";
		String mdsmabovenumProp = "0.0";
		String huabeiamtProp = "0.0";
		String huabeinumProp = "0.0";
		BigDecimal paynum = new BigDecimal(list.get(0)[5].toString());
		BigDecimal ssnum = new BigDecimal(list.get(0)[6].toString());
		BigDecimal zfbnum = new BigDecimal(list.get(0)[7].toString());
		BigDecimal ewmnum = new BigDecimal(list.get(0)[9].toString());
		BigDecimal payamt = new BigDecimal(list.get(0)[2].toString());
		BigDecimal ssamt = new BigDecimal(list.get(0)[3].toString());
		BigDecimal zfbamt = new BigDecimal(list.get(0)[4].toString());
		BigDecimal ewamt = new BigDecimal(list.get(0)[8].toString());
		BigDecimal wxamt = new BigDecimal(list.get(0)[10].toString());
		BigDecimal wxnum = new BigDecimal(list.get(0)[11].toString());
		BigDecimal wxamt1 = new BigDecimal(list.get(0)[12].toString());
		BigDecimal wxnum1 = new BigDecimal(list.get(0)[13].toString());
		BigDecimal wxamt2 = new BigDecimal(list.get(0)[14].toString());
		BigDecimal wxnum2 = new BigDecimal(list.get(0)[15].toString());
		BigDecimal mdsmamt = new BigDecimal(list.get(0)[16].toString());
		BigDecimal mdsmnum = new BigDecimal(list.get(0)[17].toString());
		BigDecimal mdsmaboveamt = new BigDecimal(list.get(0)[18].toString());
		BigDecimal mdsmabovenum = new BigDecimal(list.get(0)[19].toString());
		BigDecimal huabeiamt = new BigDecimal(list.get(0)[20].toString());
		BigDecimal huabeinum = new BigDecimal(list.get(0)[21].toString());
		BigDecimal amt = zfbamt.add(ewamt).add(payamt).add(ssamt).add(wxamt).add(wxamt1).add(wxamt2).add(mdsmamt).add(mdsmaboveamt);
		BigDecimal num = zfbnum.add(ewmnum).add(paynum).add(ssnum).add(wxnum).add(wxnum1).add(wxnum2).add(mdsmnum).add(mdsmabovenum);
		if(checkSyt){
			amt.add(huabeiamt);
			num.add(huabeinum);
		}
		if(map.containsKey("1")||map.containsKey("3")){//刷卡和手机pay
			if(amt.compareTo(BigDecimal.ZERO)!=0){
				Map<String, Object> map1 = new HashMap<String,Object>();
				ssamtProp = String.valueOf(ssamt.multiply(new BigDecimal(100)).divide(amt, 2, RoundingMode.HALF_UP));
				ssnumProp = String.valueOf(ssnum.multiply(new BigDecimal(100)).divide(num, 2, RoundingMode.HALF_UP));
				map1.put("name", "刷卡收款");
				map1.put("amt", ssamt.toString());
				map1.put("num", ssnum.toString());
				map1.put("numProp", ssnumProp);
				map1.put("amtProp", ssamtProp);
				map1.put("color", "#D26565");
				listProp.add(map1);
				Map<String, Object> map2 = new HashMap<String,Object>();
				payamtProp = String.valueOf(payamt.multiply(new BigDecimal(100)).divide(amt, 2, RoundingMode.HALF_UP));
				paynumProp = String.valueOf(paynum.multiply(new BigDecimal(100)).divide(num, 2, RoundingMode.HALF_UP));
				map2.put("name", "手机pay");
				map2.put("amt", payamt.toString());
				map2.put("num", paynum.toString());
				map2.put("numProp", paynumProp);
				map2.put("amtProp", payamtProp);
				map2.put("color", "#F5A623");
				listProp.add(map2);
			}
		}
		if(map.containsKey("1")){//秒到扫码
			Map<String, Object> map1 = new HashMap<String,Object>();
			if(amt.compareTo(BigDecimal.ZERO)!=0){
				mdsmamtProp = String.valueOf(mdsmamt.multiply(new BigDecimal(100)).divide(amt, 2, RoundingMode.HALF_UP));
				mdsmnumProp = String.valueOf(mdsmnum.multiply(new BigDecimal(100)).divide(num, 2, RoundingMode.HALF_UP));
				map1.put("name", "秒到扫码1000以下");
				map1.put("amt", mdsmamt.toString());
				map1.put("num", mdsmnum.toString());
				map1.put("numProp", mdsmnumProp);
				map1.put("amtProp", mdsmamtProp);
				map1.put("color", "#4A90E2");
				listProp.add(map1);
			Map<String, Object> map2 = new HashMap<String,Object>();
				mdsmaboveamtProp = String.valueOf(mdsmaboveamt.multiply(new BigDecimal(100)).divide(amt, 2, RoundingMode.HALF_UP));
				mdsmabovenumProp = String.valueOf(mdsmabovenum.multiply(new BigDecimal(100)).divide(num, 2, RoundingMode.HALF_UP));
				map2.put("name", "秒到扫码1000以上");
				map2.put("amt", mdsmaboveamt.toString());
				map2.put("num", mdsmabovenum.toString());
				map2.put("numProp", mdsmabovenumProp);
				map2.put("amtProp", mdsmaboveamtProp);
				map2.put("color", "#00C7FF");
				listProp.add(map2);
			}
		}
		if(map.containsKey("2")||map.containsKey("3")){//非秒到：扫码拆分
			if(amt.compareTo(BigDecimal.ZERO)!=0){
				Map<String, Object> map1 = new HashMap<String,Object>();
				wxamtProp = String.valueOf(wxamt.multiply(new BigDecimal(100)).divide(amt, 2, RoundingMode.HALF_UP));
				wxNumProp = String.valueOf(wxnum.multiply(new BigDecimal(100)).divide(num, 2, RoundingMode.HALF_UP));
				boolean flag = false;
				if(Integer.parseInt(param.get("startDate").toString())>=20191201){
					flag = true;
				}
				if(flag){
					map1.put("name", "扫码1000以下0.38%");
				}else{
					map1.put("name", "微信1000以下（含）");
				}
				map1.put("amt", wxamt.toString());
				map1.put("num", wxnum.toString());
				map1.put("numProp", wxNumProp);
				map1.put("amtProp", wxamtProp);
				map1.put("color", "#1FC19D");
				listProp.add(map1);
				Map<String, Object> map4 = new HashMap<String,Object>();
				zfbamtProp = String.valueOf(zfbamt.multiply(new BigDecimal(100)).divide(amt, 2, RoundingMode.HALF_UP));
				zfbnumProp = String.valueOf(zfbnum.multiply(new BigDecimal(100)).divide(num, 2, RoundingMode.HALF_UP));
				if(flag){
					map4.put("name", "扫码1000以下0.45%");
				}else{
					map4.put("name", "支付宝");
				}
				map4.put("amt", zfbamt.toString());
				map4.put("num", zfbnum.toString());
				map4.put("numProp", zfbnumProp);
				map4.put("amtProp", zfbamtProp);
				map4.put("color", "#604AE2");
				listProp.add(map4);
				Map<String, Object> map2 = new HashMap<String,Object>();
				wxamt1Prop = String.valueOf(wxamt1.multiply(new BigDecimal(100)).divide(amt, 2, RoundingMode.HALF_UP));
				wxNum1Prop = String.valueOf(wxnum1.multiply(new BigDecimal(100)).divide(num, 2, RoundingMode.HALF_UP));
				if(flag){
					map2.put("name", "扫码1000以上0.38%");
				}else{
					map2.put("name", "微信1000以上0.38%");
				}
				map2.put("amt", wxamt1.toString());
				map2.put("num", wxnum1.toString());
				map2.put("numProp", wxNum1Prop);
				map2.put("amtProp", wxamt1Prop);
				map2.put("color", "#BD10E0");
				listProp.add(map2);
				Map<String, Object> map3 = new HashMap<String,Object>();
				wxamt2Prop = String.valueOf(wxamt2.multiply(new BigDecimal(100)).divide(amt, 2, RoundingMode.HALF_UP));
				wxNum2Prop = String.valueOf(wxnum2.multiply(new BigDecimal(100)).divide(num, 2, RoundingMode.HALF_UP));
				if(flag){
					map3.put("name", "扫码1000以上0.45%");
				}else{
					map3.put("name", "微信1000以上0.45%");
				}
				map3.put("amt", wxamt2.toString());
				map3.put("num", wxnum2.toString());
				map3.put("numProp", wxNum2Prop);
				map3.put("amtProp", wxamt2Prop);
				map3.put("color", "#7ED321");
				listProp.add(map3);
				if(checkSyt){
					Map<String, Object> map5 = new HashMap<String,Object>();
					huabeiamtProp = String.valueOf(huabeiamt.multiply(new BigDecimal(100)).divide(amt, 2, RoundingMode.HALF_UP));
					huabeinumProp = String.valueOf(huabeinum.multiply(new BigDecimal(100)).divide(num, 2, RoundingMode.HALF_UP));
					map5.put("name", "支付宝花呗");
					map5.put("amt", huabeiamt.toString());
					map5.put("num", huabeinum.toString());
					map5.put("numProp", huabeinumProp);
					map5.put("amtProp", huabeiamtProp);
					map5.put("color", "#1677FF");
					listProp.add(map5);
				}
			}
		}
		if(amt.compareTo(BigDecimal.ZERO)!=0){
			Map<String, Object> map1 = new HashMap<String,Object>();
			ewmamtProp = String.valueOf(ewamt.multiply(new BigDecimal(100)).divide(amt, 2, RoundingMode.HALF_UP));
			ewmnumProp = String.valueOf(ewmnum.multiply(new BigDecimal(100)).divide(num, 2, RoundingMode.HALF_UP));
			map1.put("name", "银联二维码");
			map1.put("amt", ewamt.toString());
			map1.put("num", ewmnum.toString());
			map1.put("numProp", ewmnumProp);
			map1.put("amtProp", ewmamtProp);
			map1.put("color", "#D32173");
			listProp.add(map1);
		}
		BigDecimal txnamt = new BigDecimal(list.get(0)[0].toString());
		String txnAmt_u = "0";
		if(txnamt.compareTo(BigDecimal.ZERO)!=0){
			BigDecimal txnamt_s = new BigDecimal(list.get(0)[1].toString());
			txnAmt_u = txnamt.subtract(txnamt_s).toString();
		}
		Map<String, Object> map2 = new HashMap<String,Object>();
		map2.put("txnAmt", list.get(0)[0].toString());
		map2.put("txnAmt_s", list.get(0)[1].toString());
		map2.put("txnAmt_u", txnAmt_u);
		map2.put("prop", listProp);
		list1.add(map2);
		return list1; 
	}

	//分润结果处理
	private Map<String, Object> resultForProfit(List<Object[]> list){
		Map<String, Object> map1 = new HashMap<String,Object>();
		BigDecimal txnProfit = new BigDecimal(list.get(0)[0].toString());
		String txnProfit_s = "0";
		String txnProfit_u_prop = "100";
		String txnProfit_s_prop = "0";
		if(txnProfit.compareTo(BigDecimal.ZERO)!=0){
			BigDecimal txnProfit_u = new BigDecimal(list.get(0)[1].toString());
			BigDecimal big_txnProfit_s = txnProfit.subtract(txnProfit_u);
			int r = big_txnProfit_s.compareTo(BigDecimal.ZERO); //和0，ZERO比较
			if(r!=-1){
				//自营分润大于零
				txnProfit_s = big_txnProfit_s.toString();
				txnProfit_s_prop = String.valueOf(big_txnProfit_s.multiply(new BigDecimal(100)).divide(txnProfit, 2, RoundingMode.HALF_UP));
				txnProfit_u_prop = String.valueOf(txnProfit_u.multiply(new BigDecimal(100)).divide(txnProfit, 2, RoundingMode.HALF_UP));
			}
		}
		map1.put("txnProfit", list.get(0)[0].toString());
		map1.put("txnProfit_s", txnProfit_s);
		map1.put("txnProfit_u", list.get(0)[1].toString());
		map1.put("txnProfit_s_prop", txnProfit_s_prop);
		map1.put("txnProfit_u_prop", txnProfit_u_prop);
		return map1;
	}

	//返回指定日期内交易汇总及分润
	private Map<String, String> returnTxnAmt(String start,String end,String unno,String merType){
		HashMap<String, Object> map = new HashMap<String, Object>();
		String sql = "select a.*,b.txnprofit_u,(a.txnprofit-b.txnprofit_u) txnprofit_s from "
				+ "(select nvl(sum(txnamt),0) txnamt,nvl(sum(txnamt_s),0) txnamt_s,"
				+ "nvl(sum(txnprofit)+sum(cashprofit),0) txnprofit,"
				+ "nvl(sum(txnamt)-sum(txnamt_s),0) txnamt_u "
				+ "from pg_unnotxnfrun_sum where unno = :unno "
				+ "and txnday >= :startDate and txnday < :endDate ";
		if(merType != null && !"".equals(merType)){
			if(!merType.equals("3")){
				sql += " and protype = '" +merType+ "' ";
			}else{
				sql += " and protype in(3,22,23)  ";
			}
		}
		sql += ")a, (select nvl(sum(txnprofit)+sum(cashprofit),0) txnprofit_u "
				+ "from pg_unnotxnfrun_sum where unno in(select unno from sys_unit where upper_unit = :unno) "
				+ "and txnday >= :startDate and txnday < :endDate ";
		if(merType != null && !"".equals(merType)){
			if(!merType.equals("3")){
				sql += " and protype = '" +merType+ "' ";
			}else{
				sql += " and protype in(3,22,23)  ";
			}
		}
		sql += ")b ";
		map.put("unno", unno);
		map.put("startDate", start);
		map.put("endDate", end);
		List<Object[]> listLast = genericJpaDao.queryByNativeSql(sql, map);
		Map<String, String> param = new HashMap<String,String>();
		param.put("amt", listLast.get(0)[0].toString());
		param.put("amts", listLast.get(0)[1].toString());
		param.put("profit", listLast.get(0)[2].toString());
		param.put("profitu", listLast.get(0)[4].toString());
		param.put("amtu", listLast.get(0)[3].toString());
		param.put("profits", listLast.get(0)[5].toString());
		return param;
	}

	/**
	 * 老接口月交易汇总
	 * @param reqEntity
	 * @return
	 * @author yq
	 */
	public RespEntity queryTxn(MessageDetaliEntity reqEntity) {
		RespEntity rs = new RespEntity();
		List<Map<String, Object>> list1 = new ArrayList<Map<String,Object>>();
		String sql = "select nvl(sum(txnamt),0),nvl(sum(txnamt_s),0),nvl(sum(sspay_amt),0),"
				+ "nvl(sum(ss_amt),0),nvl(sum(sm_amt),0),nvl(sum(sspay_num),0),"
				+ "nvl(sum(ss_num),0),nvl(sum(sm_num),0),nvl(sum(dh_amt),0),nvl(sum(dh_num),0), "
				+ "nvl(sum(ewm_amt),0),nvl(sum(ewm_num),0) "
				+ "from pg_unnotxnfrun_sum where unno = :unno "
				+ "and to_date(txnday,'yyyymmdd') >= to_date(:startDate,'yyyymmdd') "
				+ "and to_date(txnday,'yyyymmdd') < to_date(:endDate,'yyyymmdd') ";
		if(reqEntity.getMerType() != null && !"".equals(reqEntity.getMerType())){
			if(!reqEntity.getMerType().equals("3")){
				sql += " and protype = '" +reqEntity.getMerType()+ "' ";
			}else{
				sql += " and protype in(3,22,23)  ";
			}
		}else{
			sql += " and protype in(select protype from sys_proparam where unno = '"+reqEntity.getUnno()+"' "
					+ "and protype is not null) ";
		}
		String unno = reqEntity.getUnno();
		//获取日期区间
		Map<String , Object> map = returnDate(reqEntity.getFlag());
		if(map==null){
			rs.setReturnCode(ReturnCode.FALT);
			rs.setReturnMsg("参数有误！");
			return rs;
		}
		HashMap<String, Object> map1 = new HashMap<String,Object>();
		map1.put("unno", unno);
		map1.put("startDate", map.get("startDate"));
		map1.put("endDate", map.get("endDate"));
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql, map1);
		list1 = resultForTxn(list,reqEntity);
		String amt = returnTxnAmt(map.get("startDate1").toString(),map.get("endDate1").toString(),unno,reqEntity.getMerType()).get("amt");
		Map<String, Object> map2 = list1.get(0);
		map2.put("lastMonth", amt);
		list1 = new ArrayList<>();
		list1.add(map2);

		rs.setReturnCode(ReturnCode.SUCCESS);
		rs.setReturnMsg("查询成功");
		rs.setReturnBody(list1);
		return rs;
	}

	//交易结果处理 ---老接口
	private List<Map<String, Object>> resultForTxn(List<Object[]> list,MessageDetaliEntity reqEntity){
		List<Map<String, Object>> list1 = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listProp = new ArrayList<Map<String,Object>>();
		String sql = "select unno,case when protype = 1 then '1' "
				+ "when protype = 21 then '2' else '3' end protype "
				+ "from sys_proparam where unno = '"+reqEntity.getUnno()+"' and protype is not null ";
		if(reqEntity.getMerType() != null && !"".equals(reqEntity.getMerType())){
			if(!reqEntity.getMerType().equals("3")){
				sql += " and protype = '" +reqEntity.getMerType()+ "' ";
			}else{
				sql += " and protype in(3,22,23)  ";
			}
		}
		List<Object[]> list2 = genericJpaDao.queryByNativeSql(sql, null);
		int j = 0;
		for(int i=0;i<list2.size();i++){
			String proty = list2.get(i)[1].toString();
			if(proty.equals("1")){	
				j = 5;
			}else if(proty.equals("2")&& j < 1){
				j = 1;
			}else if(proty.equals("3")&& j < 4){
				j = 4;
			}
		}
		String payamtProp = "0.0";
		String paynumProp = "0.0";
		String ssamtProp = "0.0";
		String ssnumProp = "0.0";
		String smnumProp = "0.0";
		String smamtProp = "0.0";
		String dhamtProp = "0.0";
		String dhnumProp = "0.0";
		String ewmamtProp = "0.0";
		String ewmnumProp = "0.0";
		BigDecimal paynum = new BigDecimal(list.get(0)[5].toString());
		BigDecimal ssnum = new BigDecimal(list.get(0)[6].toString());
		BigDecimal smnum = new BigDecimal(list.get(0)[7].toString());
		BigDecimal dhnum = new BigDecimal(list.get(0)[9].toString());
		BigDecimal ewmnum = new BigDecimal(list.get(0)[11].toString());
		BigDecimal payamt = new BigDecimal(list.get(0)[2].toString());
		BigDecimal ssamt = new BigDecimal(list.get(0)[3].toString());
		BigDecimal smamt = new BigDecimal(list.get(0)[4].toString());
		BigDecimal ewamt = new BigDecimal(list.get(0)[10].toString());
		BigDecimal dhamt = new BigDecimal(list.get(0)[8].toString());
		BigDecimal amt = smamt.add(ewamt).add(payamt).add(dhamt).add(ssamt);
		BigDecimal num = smnum.add(ewmnum).add(paynum).add(dhnum).add(ssnum);
		if(j==1){
			Map<String, Object> map1 = new HashMap<String,Object>();
			String a = "0";
			if(smnum.add(ewmnum).compareTo(BigDecimal.ZERO)!=0)a = "100";
			map1.put("name", "扫码收款");
			map1.put("amt", smnum.add(ewmnum).toString());
			map1.put("num", smnum.add(ewmnum).toString());
			map1.put("numProp", a);
			map1.put("amtProp", a);
			listProp.add(map1);
		}else if(j==5||j==4){
			if(amt.compareTo(BigDecimal.ZERO)!=0){
				payamtProp = String.valueOf(payamt.multiply(new BigDecimal(100)).divide(amt, 2, RoundingMode.HALF_UP));
				ssamtProp = String.valueOf(ssamt.multiply(new BigDecimal(100)).divide(amt, 2, RoundingMode.HALF_UP));
				dhamtProp = String.valueOf(dhamt.multiply(new BigDecimal(100)).divide(amt, 2, RoundingMode.HALF_UP));
				if(j==4){
					smamtProp = String.valueOf((smamt.add(ewamt)).multiply(new BigDecimal(100)).divide(amt, 2, RoundingMode.HALF_UP));
				}else{
					smamtProp = String.valueOf(smamt.multiply(new BigDecimal(100)).divide(amt, 2, RoundingMode.HALF_UP));
					ewmamtProp = String.valueOf(ewamt.multiply(new BigDecimal(100)).divide(amt, 2, RoundingMode.HALF_UP));
				}
			}
			if(num.compareTo(BigDecimal.ZERO)!=0){
				paynumProp = String.valueOf(paynum.multiply(new BigDecimal(100)).divide(num, 2, RoundingMode.HALF_UP));
				ssnumProp = String.valueOf(ssnum.multiply(new BigDecimal(100)).divide(num, 2, RoundingMode.HALF_UP));
				dhnumProp = String.valueOf(dhnum.multiply(new BigDecimal(100)).divide(num, 2, RoundingMode.HALF_UP));
				if(j==4){
					smnumProp = String.valueOf((smnum.add(ewmnum)).multiply(new BigDecimal(100)).divide(num, 2, RoundingMode.HALF_UP));
				}else{
					smnumProp = String.valueOf(smnum.multiply(new BigDecimal(100)).divide(num, 2, RoundingMode.HALF_UP));
					ewmnumProp = String.valueOf(ewmnum.multiply(new BigDecimal(100)).divide(num, 2, RoundingMode.HALF_UP));
				}
			}
			Map<String, Object> map1 = new HashMap<String,Object>();
			map1.put("name", "手机Pay");
			map1.put("amt", payamt.toString());
			map1.put("num", paynum.toString());
			map1.put("numProp", paynumProp);
			map1.put("amtProp", payamtProp);
			listProp.add(map1);
			Map<String, Object> map2 = new HashMap<String,Object>();
			map2.put("name", "信用卡还款");
			map2.put("amt", dhamt.toString());
			map2.put("num", dhnum.toString());
			map2.put("numProp", dhnumProp);
			map2.put("amtProp", dhamtProp);
			listProp.add(map2);
			Map<String, Object> map3 = new HashMap<String,Object>();
			map3.put("name", "刷卡收款");
			map3.put("amt", ssamt.toString());
			map3.put("num", ssnum.toString());
			map3.put("numProp", ssnumProp);
			map3.put("amtProp", ssamtProp);
			listProp.add(map3);
			if(j==4){
				Map<String, Object> map4 = new HashMap<String,Object>();
				map4.put("name", "扫码收款");
				map4.put("amt", smamt.add(ewmnum).toString());
				map4.put("num", smnum.add(ewmnum).toString());
				map4.put("numProp", smnumProp);
				map4.put("amtProp", smamtProp);
				listProp.add(map4);
			}else{
				Map<String, Object> map4 = new HashMap<String,Object>();
				map4.put("name", "扫码收款");
				map4.put("amt", smamt.toString());
				map4.put("num", smnum.toString());
				map4.put("numProp", smnumProp);
				map4.put("amtProp", smamtProp);
				listProp.add(map4);
				Map<String, Object> map5 = new HashMap<String,Object>();
				map5.put("name", "银联二维码");
				map5.put("amt", ewamt.toString());
				map5.put("num", ewmnum.toString());
				map5.put("numProp", ewmnumProp);
				map5.put("amtProp", ewmamtProp);
				listProp.add(map5);
			}
		}
		BigDecimal txnamt = new BigDecimal(list.get(0)[0].toString());
		String txnAmt_u = "0";
		if(txnamt.compareTo(BigDecimal.ZERO)!=0){
			BigDecimal txnamt_s = new BigDecimal(list.get(0)[1].toString());
			txnAmt_u = txnamt.subtract(txnamt_s).toString();
		}
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("txnAmt", list.get(0)[0].toString());
		map.put("txnAmt_s", list.get(0)[1].toString());
		map.put("txnAmt_u", txnAmt_u);
		map.put("prop", listProp);
		list1.add(map);
		return list1; 
	}
	
	//判断当前机构下收银台是否有花呗交易
	private boolean chectRebateType(String unno){
		//判断当前机构级别
		String sql = "select unno,un_lvl from sys_unit where unno = '"+unno+"' ";
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql, null);
		Integer unLvl = Integer.parseInt(list.get(0)[1].toString());
		//判断成本是否包含花呗活动
		if(unLvl<=2){//一代及以上
			String rebateTypeSql = "select * from Hrt_Unno_Cost t where t.unno = :unno and t.machine_Type = 1 "
					+ "and t.txn_Type = 1 and t.txn_Detail in (select t.valueinteger "
					+ "from bill_purconfigure t where t.type = 3 and t.valuestring = 'rate' "
					+ "and t.prod = 'syt' and t.subtype = 1) and t.status = 1 ";
			HashMap<String, Object> map = new HashMap<>();
			map.put("unno", unno);
			List<Object[]> rebateTypeList = genericJpaDao.queryByNativeSql(rebateTypeSql,map);
			if(rebateTypeList.size()>0)return true;
		}else{
			String rebateTypeSql = "select k.* from check_micro_profittemplate k,check_unit_profitemplate m "
					+ "where m.aptid = k.aptid and k.merchanttype=5 and k.mataintype!='D' and m.status=1 "
					+ "and m.unno=:unno and k.profitRule in(select t.valueinteger "
					+ "from bill_purconfigure t where t.type = 3 and t.valuestring = 'rate' "
					+ "and t.prod = 'syt' and t.subtype = 1)";
			HashMap<String, Object> map = new HashMap<>();
			map.put("unno", unno);
			List<Object[]> rebateTypeList = genericJpaDao.queryByNativeSql(rebateTypeSql,map);
			if(rebateTypeList.size()>0)return true;
		}
		return false;
	}
	
}