package com.hrtp.salesAppService.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONObject;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.appEntity.WalletEntity;
import com.hrtp.system.common.GenericJpaDao;
import com.hrtp.system.costant.ReturnCode;
import com.hrtp.system.util.HttpXmlClient;

/**
 * 提现钱包服务
 * yq 2019/7
 */
@Service
public class WalletService {

	private static Logger log = LoggerFactory.getLogger(WalletService.class);

	@Autowired
	private GenericJpaDao genericJpaDao;
	@Value("${CashAmtUrl}")
	private String url;

	/**
	 * 查询钱包首页数据
	 * @param wallet
	 */
	public Map<String, String> getHomePage(WalletEntity wallet){
		String sqlQueryList = "select b.pwd_expiry_days,b.pwd_err_times,b.upload_path,a.cashamtprofit,a.curamt,a.bindCardStatus "
				+ "from (select a.cashamtprofit,nvl(p.curamt,0)curamt,a.bindCardStatus from ("
				+ "select nvl(ag.bindCardStatus,0) bindCardStatus,nvl(ag.cashamtprofit,6)cashamtprofit,ag.unno from bill_agentunitinfo ag where ag.unno= :unno)a "
				+ "left join (select unno,curamt from pg_unno_purse where status = '1' and unno = :unno ) p on a.unno=p.unno )a,"
				+ "(select p.pwd_expiry_days,p.pwd_err_times,p.upload_path from sys_param p "
				+ "where title='SalesAppService')b";
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("unno", wallet.getUnno());
		List<Object[]> list = genericJpaDao.queryByNativeSql(sqlQueryList, map);
		Map<String , String> map1 = new HashMap<String,String>();
		map1.put("minCashAmt", list.get(0)[0].toString());
		map1.put("addAmt", list.get(0)[1].toString());
		map1.put("maxAddAmt", list.get(0)[2].toString());
		map1.put("cashAmtProfit", list.get(0)[3].toString());
		map1.put("curamt", list.get(0)[4].toString());
		map1.put("bindCardStatus", list.get(0)[5].toString());
		return map1;
	}

	/**
	 * 查询钱包支出明细
	 * @param wallet
	 */
	public RespEntity queryCashHis(WalletEntity wallet){
		RespEntity rs = new RespEntity();
		String sql = "select cashdate as cashdate1,to_char(cashdate,'yyyy.mm.dd') cashdate,cashamt,(cashamt-cashfee)paymentAmt "
				+ "from PG_UNNO_CASH where unno = :unno and cashStatus = 1 and cashdate >= to_date(:startDate,'yyyymmdd') and "
				+ "cashdate < to_date(:endDate,'yyyymmdd')+1 order by cashdate1 desc ";
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("unno", wallet.getUnno());
		map.put("startDate", wallet.getStartDate().replace("-", ""));
		map.put("endDate", wallet.getEndDate().replace("-", ""));
		List<Object[]> list = genericJpaDao.queryByNativeSqlWithPageAndRows(sql,wallet.getPage(),20,map);
		List<Map<String, String>> list1 = new ArrayList<Map<String,String>>();
		for(int i = 0 ;i<list.size();i++){
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("date", list.get(i)[1].toString());
			map1.put("cashAmt", list.get(i)[2].toString());
			map1.put("paymentAmt", list.get(i)[3].toString());
			list1.add(map1);
		}
		rs.setReturnBody(list1);
		rs.setReturnCode(ReturnCode.SUCCESS);
		rs.setReturnMsg("查询成功！");
		return rs;
	}

	/**
	 * 查询钱包收入明细  老版本
	 * @param wallet
	 */
	public RespEntity queryAddProfit(WalletEntity wallet){
		RespEntity rs = new RespEntity();
		String sql = "select cdate,to_char(cdate,'yyyy.mm.dd'),endbalance from PG_UNNO_PURSELOG "
				+ "where  unno = :unno and cdate >= to_date(:startDate,'yyyymmdd') and "
				+ "cdate < to_date(:endDate,'yyyymmdd')+1 order by cdate desc ";
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("unno", wallet.getUnno());
		map.put("startDate", wallet.getStartDate().replace("-", ""));
		map.put("endDate", wallet.getEndDate().replace("-", ""));
		List<Object[]> list = genericJpaDao.queryByNativeSqlWithPageAndRows(sql,wallet.getPage(),20,map);
		List<Map<String, String>> list1 = new ArrayList<Map<String,String>>();
		for(int i = 0 ;i<list.size();i++){
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("date", list.get(i)[1].toString());
			map1.put("cashAmt", list.get(i)[2].toString());
			list1.add(map1);
		}
		rs.setReturnBody(list1);
		rs.setReturnCode(ReturnCode.SUCCESS);
		rs.setReturnMsg("查询成功！");
		return rs;
	}

	/**
	 * 余额提现
	 * @param wallet
	 */
	public synchronized RespEntity updateCashAmt(WalletEntity wallet){
		RespEntity rs = new RespEntity();
		Map<String, String> map = getHomePage(wallet);
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		String time = sd.format(date);
		if(wallet.getCashAmt().compareTo(new BigDecimal(map.get("curamt"))) == 1){
			rs = new RespEntity(ReturnCode.FALT,"提现金额大于当前钱包余额！不可提现！");
		}else if(wallet.getCashAmt().compareTo(new BigDecimal(map.get("minCashAmt"))) == -1){
			rs = new RespEntity(ReturnCode.FALT,"提现金额小于最小提现金额！不可提现！");
		}else if(!map.get("bindCardStatus").equals("1")){
			rs = new RespEntity(ReturnCode.FALT,"未绑定结算卡！不可提现！");
		}else if(Integer.parseInt(wallet.getUnLvl()) < 2){
			rs = new RespEntity(ReturnCode.FALT,"中心分润月结不可提现！");
		}else{
			//计算手续费扣除后的真实提现金额
			BigDecimal feeAmt = wallet.getCashAmt().multiply(new BigDecimal("100").subtract(new BigDecimal(map.get("cashAmtProfit")))).divide(new BigDecimal("100"), 2,BigDecimal.ROUND_HALF_UP);
			//判断是否大于服务费3元扣除最大金额
			if(wallet.getCashAmt().compareTo(new BigDecimal(map.get("maxAddAmt"))) != 1){
				feeAmt = feeAmt.subtract(new BigDecimal(map.get("addAmt")));
			}
			try{ 
				//钱包余额表减去提现金额
				String sql = "update PG_UNNO_PURSE set curamt = curamt-"+wallet.getCashAmt()+",balance = balance-"+wallet.getCashAmt()+" where unno = '"+wallet.getUnno()+"' and curamt-"+wallet.getCashAmt()+" >= 0 and status = '1' ";
				int a = genericJpaDao.executeSql(sql);
				if(a>0){
					String firunno = "";String bankaccname = "";
					String bankaccno = "";String bankname = "";String acctype = "";
					//下级机构
					String uperUnnoSql = "select b.yidai,a.BANKACCNAME,a.BANKACCNO,a.bankbranch,acctype from "
							+ "(select unno,BANKACCNAME,BANKACCNO,bankbranch,acctype from BILL_AGENTUNITINFO "
							+ "where unno = :unno and mobilePhone is not null and bindCardStatus =1) a "
							+ "left join (select unno as yidai,:unno as unno from ("
							+ "select * from sys_unit u start with u.unno = :unno "
							+ "connect by prior u.upper_unit = u.unno )"
							+ "a where a.un_lvl = 2) b "
							+ "on a.unno = b.unno";
					HashMap<String, Object> uperUnnoMap = new HashMap<String,Object>();
					uperUnnoMap.put("unno", wallet.getUnno());
					List<Object[]> list = genericJpaDao.queryByNativeSql(uperUnnoSql, uperUnnoMap);
					if(list.size()>0){
						for(int j = 0;j<list.size();j++){
							firunno = list.get(j)[0]==null?"":list.get(j)[0].toString();
							bankaccname = list.get(j)[1]==null?"":list.get(j)[1].toString();
							bankaccno = list.get(j)[2]==null?"":list.get(j)[2].toString();
							bankname = list.get(j)[3]==null?"":list.get(j)[3].toString();
							acctype = list.get(j)[4]==null?"2":list.get(j)[4].toString();
						}
					}else{
						throw new IndexOutOfBoundsException();
					}
					//机构级别为运营中心和一代
					if(Integer.parseInt(wallet.getUnLvl()) < 2){
						firunno = wallet.getUnno();
					}
					if(firunno.isEmpty()||bankaccname.isEmpty()
							||bankaccno.isEmpty()||bankname.isEmpty()){
						throw new IndexOutOfBoundsException();
					}
					//判断一代的可提现余额是否有足够的金额
					String querySql = "select curamt,balance from PG_UNNO_MUTIPURSE where unno in("
							+ "select unno from (select * from sys_unit start with unno = :unno "
							+ "and status = '1' connect by NOCYCLE prior upper_unit = unno) where un_lvl = 2) "
							+ "and status = '1' ";
					HashMap<String, Object> param = new HashMap<>();
					param.put("unno", wallet.getUnno());
					List<Object[]> listQuery = genericJpaDao.queryByNativeSql(querySql,param);
					if(listQuery.size()<1){//没有查询到一代的可提现余额
						throw new NullPointerException();
					}
					BigDecimal curamt = new BigDecimal(listQuery.get(0)[0].toString());
					log.info(wallet.getUnno()+"一代查询金额:"+curamt);
					if(curamt.compareTo(wallet.getCashAmt())==-1){//提现金额大于可提现余额
						throw new NullPointerException();
					}
					String updateSql = "update PG_UNNO_MUTIPURSE set curamt = curamt - :amt where unno in("
							+ "select unno from (select * from sys_unit start with unno = :unno "
							+ "and status = '1' connect by NOCYCLE prior upper_unit = unno) where un_lvl = 2) and status = '1'";
					HashMap<String, Object> updateMap = new HashMap<>();
					updateMap.put("amt", wallet.getCashAmt());
					updateMap.put("unno", wallet.getUnno());
					int i = genericJpaDao.executeSql(updateSql,updateMap);
					if(i<1)
						throw new NullPointerException();
					//请求综合代付
					Map<String,String> params = new HashMap<String, String>();
					params.put("unno",wallet.getUnno());//机构号
					params.put("firunno",firunno);//一代机构号
					params.put("cashamt", feeAmt.toString());//提现到账金额
					params.put("acctype", acctype);//对公1对私2
					params.put("cashfee", wallet.getCashAmt().subtract(feeAmt).toString());//提现手续费
					params.put("cashday", time);//提现日期
					params.put("paymentnumber", "0");//系统支付行号
					params.put("bankaccname", bankaccname);//入账人名称
					params.put("bankaccno", bankaccno);//卡号
					params.put("bankname", bankname);//银行名称
					log.info(params.toString());
					String resultStr = HttpXmlClient.post(url, params);
					int result = 0;
					try{
						JSONObject json1 = JSONObject.parseObject(resultStr);
						result = Integer.parseInt(json1.getString("status"));
					}catch(Exception e){
						log.info("提现请求综合异常！");
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						rs.setReturnCode(ReturnCode.FALT);
						rs.setReturnMsg("提现失败！");
						return rs;
					}
					if(result==1){
						rs.setReturnCode(ReturnCode.SUCCESS);
						rs.setReturnMsg("提现成功！");
						//添加提现明细
						String sql1 = "insert into PG_UNNO_CASH(unno,cashmode,cashamt,cashfee,cashday,cashdate,cashstatus) "
								+ "values('"+wallet.getUnno()+"',1,"+wallet.getCashAmt()+","+wallet.getCashAmt().subtract(feeAmt)+",'"+time+"',sysdate,"+result+")";
						genericJpaDao.executeSql(sql1);
						return rs;
					}else{
						//综合提现返回失败
						throw new Exception();
					}
				}else{
					throw new NullPointerException();//可提现余额不足
				}
			}catch(IndexOutOfBoundsException e){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				rs.setReturnCode(ReturnCode.FALT);
				rs.setReturnMsg("提现信息不足，提现失败！请核实提现信息。");
			}catch(NullPointerException e){//一代金额不足
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				rs.setReturnCode(ReturnCode.FALT);
				rs.setReturnMsg("提现失败！代理额度不足。");
			}catch(Exception e){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				rs.setReturnCode(ReturnCode.FALT);
				rs.setReturnMsg("提现失败！");
			}
		}
		return rs;
	}

	/**
	 * 按照日期查询下级代理及自营交易金额 --老版本
	 */
	public RespEntity queryAddProfitDownUnit(WalletEntity wallet){
		RespEntity rs = new RespEntity();
		List<Map<String, Object>> listResult = new ArrayList<>();
		List<Map<String, String>> listMap = new ArrayList<>();
		//查询Pg_Unno_Purselog_w,分别按照unno和upperUnit分组汇总查询,日期和机构号为筛选条件
		String querySql = "select a.unno, b.agentname, sum(a.profit) profit "
				+ "from Pg_Unno_Purselog_w a, bill_agentunitinfo b "
				+ "where a.unno = b.unno and upper_unit = :unit and txnday = :txnday ";
		if(wallet.getAgentName()!=null&&!"".equals(wallet.getAgentName())){
			querySql += "and b.agentname like '%"+wallet.getAgentName()+"%' ";
		}
		querySql += "group by a.unno,b.agentName ";
		HashMap<String, Object> map = new HashMap<>();
		map.put("unit", wallet.getUnno());
		map.put("txnday", wallet.getStartDate().replace("-", "").replace("/", ""));
		List<Object[]> list = genericJpaDao.queryByNativeSqlWithPageAndRows(querySql,wallet.getPage(),wallet.getSize(),map);
		int rows = genericJpaDao.getRowsCount(querySql, map);
		if(list.size()>0){
			for(int i = 0;i<list.size();i++){
				Map<String, String> map1 = new HashMap<>();
				map1.put("unno", list.get(i)[0].toString());
				map1.put("agentName", list.get(i)[1].toString());
				map1.put("profit", list.get(i)[2].toString());
				listMap.add(map1);
			}
		}
		if("自营".equals(wallet.getAgentName())||"自".equals(wallet.getAgentName())||"营".equals(wallet.getAgentName())||
				wallet.getAgentName()==null||"".equals(wallet.getAgentName())){
			String querySql1 = "select unno,sum(profit) profit "
					+ "from Pg_Unno_Purselog_w where unno = :unit and txnday = :txnday "
					+ "group by unno ";
			List<Object[]> list1 = new ArrayList<>();
			if(wallet.getPage()==1)
				list1 = genericJpaDao.queryByNativeSql(querySql1,map);
			if(list1.size()>0){
				Map<String, String> map1 = new HashMap<>();
				map1.put("agentName", "自营");
				map1.put("unno", list1.get(0)[0].toString());
				map1.put("profit", list1.get(0)[1].toString());
				listMap.add(0, map1);
				rows = rows+1;
			}
		}
		Map<String, Object> mapObject = new HashMap<>();
		mapObject.put("rows", rows);
		mapObject.put("list", listMap);
		listResult.add(mapObject);
		rs.setReturnBody(listResult);
		rs.setReturnCode(ReturnCode.SUCCESS);
		rs.setReturnMsg("查询成功");
		return rs;
	}

	/**
	 * 按照日期查询下级代理各个交易类型产生分润金额 --老版本
	 */
	public RespEntity queryAddProfitBy(WalletEntity wallet){
		RespEntity rs = new RespEntity();
		List<Map<String, String>> listResult = new ArrayList<>();
		//查询Pg_Unno_Purselog_w,按照日期和机构号查询
		String querySql = "select unno, sum(profit) profit, "
				+ "case when txntype = 1 then '刷卡收款' "
				+ "when txnType = 2 then '秒到扫码' "
				+ "when txntype = 3 then '云闪付' "
				+ "when txntype = 4 then '微信1000以下（含）' "
				+ "when txntype = 5 then '微信1000以上0.38' "
				+ "when txntype = 6 then '微信1000以上0.45' "
				+ "when txntype = 7 then '支付宝' "
				+ "when txntype = 8 then '银联二维码' else '' end txntype "
				+ "from Pg_Unno_Purselog_w where "
				+ "unno = :unit and txnday = :txnday "
				+ "group by unno,txnType ";
		HashMap<String, String> map = new HashMap<>();
		map.put("unit", wallet.getUnno());
		map.put("txnday", wallet.getStartDate().replace("-", "").replace("/", ""));
		List<Object[]> list = genericJpaDao.queryByNativeSql(querySql,map);
		if(list.size()>0){
			for(int i = 0;i<list.size();i++){
				Map<String, String> map1 = new HashMap<>();
				map1.put("type", list.get(i)[2].toString());
				map1.put("profit", list.get(i)[1].toString());
				listResult.add(map1);
			}
		}
		rs.setReturnCode(ReturnCode.SUCCESS);
		rs.setReturnBody(listResult);
		rs.setReturnMsg("查询成功");
		return rs;
	}

	/**
	 * 2.5版本查询钱包收入明细
	 * @param wallet
	 */
	public RespEntity queryAddProfitFor2_5(WalletEntity wallet){
		RespEntity rs = new RespEntity();
		HashMap<String, Object> map = new HashMap<String,Object>();
		String sql = "select cdate,minfo1,to_char(to_date(settleday,'yyyy.mm.dd'),'yyyy.mm.dd') settleday,fenrunMda from PG_UNNO_PURSELOG "
				+ "where unno = :unno and settleday between :startDate and :endDate ";
		if(!"".equals(wallet.getRebateType())&&wallet.getRebateType()!=null){
			sql += "and minfo1 = :rebateType ";
			map.put("rebateType", wallet.getRebateType());
		}else{
			sql += "and minfo1 in (select valueInteger from bill_purconfigure where type=3 and cby='1' ) and minfo1 is not null ";
		}
		sql += " order by settleday desc ";
		map.put("unno", wallet.getUnno());
		map.put("startDate", wallet.getStartDate().replace("-", ""));
		map.put("endDate", wallet.getEndDate().replace("-", ""));
		List<Object[]> list = genericJpaDao.queryByNativeSqlWithPageAndRows(sql,wallet.getPage(),20,map);
		List<Map<String, String>> list1 = new ArrayList<Map<String,String>>();
		for(int i = 0 ;i<list.size();i++){
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("date", list.get(i)[2].toString());
			map1.put("rebateTypeName", list.get(i)[1].toString().equals("1")?"秒到":"活动"+list.get(i)[1].toString());
			map1.put("rebateType", list.get(i)[1].toString());
			map1.put("cashAmt", list.get(i)[3].toString());
			list1.add(map1);
		}
		rs.setReturnBody(list1);
		rs.setReturnCode(ReturnCode.SUCCESS);
		rs.setReturnMsg("查询成功！");
		return rs;
	}

	/**
	 * 2.5版本按照日期查询下级代理及自营交易金额
	 */
	public RespEntity queryAddProfitDownUnitFor2_5(WalletEntity wallet){
		RespEntity rs = new RespEntity();
		List<Map<String, Object>> listResult = new ArrayList<>();
		List<Map<String, String>> listMap = new ArrayList<>();
		//查询Pg_Unno_Purselog_w,分别按照unno和upperUnit分组汇总查询,日期和机构号为筛选条件--2.5版本新增活动类型
		String querySql = "select a.unno, b.agentname, sum(nvl(a.upper_profit,0)) profit,a.cby "
				+ "from Pg_Unno_Purselog_w a, bill_agentunitinfo b "
				+ "where a.unno = b.unno and a.upper_unit = :unit and a.recordday = :txnday and cby = :rebateType ";
		if(wallet.getAgentName()!=null&&!"".equals(wallet.getAgentName())){
			querySql += "and b.agentname like '%"+wallet.getAgentName()+"%' ";
		}
		querySql += "group by a.unno,b.agentName,a.cby ";
		HashMap<String, Object> map = new HashMap<>();
		map.put("unit", wallet.getUnno());
		map.put("rebateType", wallet.getRebateType());
		map.put("txnday", wallet.getStartDate().replace("-", "").replace("/", ""));
		List<Object[]> list = genericJpaDao.queryByNativeSqlWithPageAndRows(querySql,wallet.getPage(),wallet.getSize(),map);
		int rows = genericJpaDao.getRowsCount(querySql, map);
		if(list.size()>0){
			for(int i = 0;i<list.size();i++){
				Map<String, String> map1 = new HashMap<>();
				map1.put("unno", list.get(i)[0].toString());
				map1.put("rebateType", list.get(i)[3].toString());
				map1.put("agentName", list.get(i)[1].toString());
				map1.put("profit", list.get(i)[2].toString());
				listMap.add(map1);
			}
		}
		if("自营".equals(wallet.getAgentName())||"自".equals(wallet.getAgentName())||"营".equals(wallet.getAgentName())||
				wallet.getAgentName()==null||"".equals(wallet.getAgentName())){
			String querySql1 = "select unno,sum(nvl(zy_profit,0)) profit,cby "
					+ "from Pg_Unno_Purselog_w where unno = :unit and recordday = :txnday and cby = :rebateType "
					+ "group by unno,cby ";
			List<Object[]> list1 = new ArrayList<>();
			if(wallet.getPage()==1)
				list1 = genericJpaDao.queryByNativeSql(querySql1,map);
			if(list1.size()>0){
				for(int i=0;i<list1.size();i++){
					Map<String, String> map1 = new HashMap<>();
					map1.put("agentName", "自营");
					map1.put("rebateType", list1.get(i)[2].toString());
					map1.put("unno", list1.get(i)[0].toString());
					map1.put("profit", list1.get(i)[1].toString());
					listMap.add(0, map1);
					rows = rows+1;
				}
			}
		}
		Map<String, Object> mapObject = new HashMap<>();
		mapObject.put("rows", rows);
		mapObject.put("list", listMap);
		listResult.add(mapObject);
		rs.setReturnBody(listResult);
		rs.setReturnCode(ReturnCode.SUCCESS);
		rs.setReturnMsg("查询成功");
		return rs;
	}

	/**
	 * 2.5版本按照日期查询下级代理各个交易类型产生分润金额
	 */
	public RespEntity queryAddProfitByFor2_5(WalletEntity wallet){
		RespEntity rs = new RespEntity();
		List<Map<String, String>> listResult = new ArrayList<>();
		//查询Pg_Unno_Purselog_w,按照日期和机构号查询
		String querySql = "select * from (select unno, ";
		if(wallet.getFlag()==1){//自营
			querySql += "sum(nvl(zy_profit,0)) profit, ";
		}else{
			querySql += "sum(nvl(upper_profit,0)) profit, ";
		}
		querySql += "case when txntype = 1 then '刷卡收款' "
				+ "when txnType = 2 then '秒到扫码' "
				+ "when txntype = 3 then '云闪付' "
				+ "when txntype = 4 then '微信1000以下（含）' "
				+ "when txntype = 5 then '微信1000以上0.38%' "
				+ "when txntype = 6 then '微信1000以上0.45%' "
				+ "when txntype = 7 then '支付宝' "
				+ "when txntype = 9 then '扫码1000以下0.38%' "
				+ "when txntype = 10 then '扫码1000以下0.45%' "
				+ "when txntype = 11 then '扫码1000以上0.38%' "
				+ "when txntype = 12 then '扫码1000以上0.45%' "
				+ "when txntype = 8 then '银联二维码' else '' end txntype "
				+ "from Pg_Unno_Purselog_w where "
				+ "unno = :unit and recordday = :txnday and cby = :rebateType "
				+ "group by unno,txnType) where profit > 0 ";
		HashMap<String, String> map = new HashMap<>();
		map.put("unit", wallet.getUnno());
		map.put("txnday", wallet.getStartDate().replace("-", "").replace("/", ""));
		map.put("rebateType", wallet.getRebateType());
		List<Object[]> list = genericJpaDao.queryByNativeSql(querySql,map);
		if(list.size()>0){
			for(int i = 0;i<list.size();i++){
				Map<String, String> map1 = new HashMap<>();
				map1.put("type", list.get(i)[2].toString());
				map1.put("profit", list.get(i)[1].toString());
				listResult.add(map1);
			}
		}
		rs.setReturnCode(ReturnCode.SUCCESS);
		rs.setReturnBody(listResult);
		rs.setReturnMsg("查询成功");
		return rs;
	}

	/**
	 * 2.5版本查询下级代理钱包开关状态
	 */
	public RespEntity queryWalletSwitchFor2_5(WalletEntity wallet){
		int o = returnUnnoWalletCashSwitch(wallet.getUnno(),wallet.getRebateType());
		if(o!=1)
			return new RespEntity(ReturnCode.FALT, "请联系上级代理确认当前钱包状态",new ArrayList<>());
		RespEntity rs = new RespEntity();
		List<Map<String, String>> list = new ArrayList<>();
		List<Map<String, Object>> listResult = new ArrayList<>();
		Date now = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		HashMap<String, String> map = new HashMap<>();
		boolean flag = sd.format(now).substring(6,8).equals("01");
		String querySql = "";
		if(flag)//是1号增加查询上月修改记录
			querySql += "select a.unno,a.un_name,"
					+ "case when b.status is null then a.status else b.status end status,"
					+ "a.status1 from (";
		//查询模板
		querySql += "select unno,un_name,"
				+ "case when status is null then '0' else status end status,"
				+ "case when status1 is null and status is null then '0' "
				+ "when status1 is null then status else status1 end status1 "
				+ "from (select a.unno, a.un_name, a.status, b.status as status1 "
				+ "from (select a.unno,a.un_name,b.status from ( ";
		if(Integer.parseInt(wallet.getUnLvl())>1){//一代以上的下级代理
			querySql += "select a.unno, a.un_name "
					+ "from sys_unit a,check_micro_profittemplate k,check_unit_profitemplate m,bill_purconfigure b "
					+ "where a.unno = m.unno and m.aptid = k.aptid "
					+ "and k.profitRule = b.valueInteger and b.type = 3 and b.cby = '1' "
					+ "and m.status = 1 and k.mataintype != 'D' and a.upper_unit = :unno and k.profitRule = :rebateType ";
			if(wallet.getAgentName()!=null&&!"".equals(wallet.getAgentName()))
				querySql += "and a.un_name like '%"+wallet.getAgentName().trim()+"%' ";
		}else{//运营中心的下级代理
			querySql += "select a.unno, a.un_name from sys_unit a,Hrt_Unno_Cost b,bill_purconfigure c "
					+ "where a.unno = b.unno and b.txn_Detail = c.valueinteger "
					+ "and b.status = 1 and c.type = 3 and b.cby = '1' "
					+ "and a.upper_unit = :unno and b.txn_detail = :rebateType "; 
			if(wallet.getAgentName()!=null&&!"".equals(wallet.getAgentName()))
				querySql += "and a.un_name like '%"+wallet.getAgentName().trim()+"%' ";
		}	
		querySql += ") a left join (select unno, walletStatus status from check_walletCashSwitch "
				+ "where rebateType = :rebateType) b on a.unno = b.unno) a left join ("
				+ "select unno, walletStatus as status from check_walletCashSwitch_w "
				+ "where rebateType = :rebateType and to_char(createDate,'yyyymm') = :createDate ) b on a.unno = b.unno )";
		if(flag){//是1号增加查询上月修改记录
			querySql += ") a left join (select unno, walletStatus as status from check_walletCashSwitch_w "
					+ "where rebateType = :rebateType and to_char(createDate, 'yyyymm') = :createDate1 ) b on a.unno = b.unno";
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now); // 设置为当前时间
			calendar.add(Calendar.MONTH, -1); // 设置为上一个月
			Date last = calendar.getTime();
			map.put("createDate1", sd.format(last).substring(0,6));
		}
		map.put("createDate", sd.format(now).substring(0,6));
		map.put("unno", wallet.getUnno());
		map.put("rebateType", wallet.getRebateType());
		List<Object[]> list1 = genericJpaDao.queryByNativeSqlWithPageAndRows(querySql,wallet.getPage(),wallet.getSize(),map);
		if(list1.size()>0){
			for(int i = 0;i<list1.size();i++){
				Map<String, String> map1 = new HashMap<>();
				map1.put("unno", list1.get(i)[0].toString());
				map1.put("agentName", list1.get(i)[1].toString());
				map1.put("status", list1.get(i)[2].toString());
				map1.put("statusNextMonth", list1.get(i)[3].toString());
				list.add(map1);
			}
		}
		int rows = genericJpaDao.getRowsCount(querySql,map);
		Map<String, Object> param = new HashMap<>();
		param.put("rows", rows);
		param.put("list", list);
		listResult.add(param);
		rs.setReturnCode(ReturnCode.SUCCESS);
		rs.setReturnBody(listResult);
		rs.setReturnMsg("查询成功");
		return rs;
	}

	/**
	 * 修改钱包状态
	 * @param unno
	 * @param rebateType
	 * @return
	 */
	public RespEntity updateWalletSwitchFor2_5(WalletEntity wallet){
		String unno = wallet.getUnno();
		String rebateType = wallet.getRebateType();
		//父级机构当前状态是否为开通状态
		int m = returnUnnoWalletCashSwitch(wallet.getUpperUnit(),rebateType);
		if(m!=1)
			return new RespEntity(ReturnCode.FALT, "请联系上级代理查看钱包状态");
		//实时表有无数据
		Date now = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		int i = returnUnnoWalletCashSwitch(unno,rebateType);
		if(i!=0){
			//实时表有数据再查修改表有无记录
			int j = returnUnnoWalletCashSwitchW(unno,rebateType,sd.format(now).substring(0, 6));
			if(j!=0){//修改变更表状态
				String updateWSql = "update check_walletCashSwitch_w set walletStatus = :walletStatus "
						+ "where unno = :unno and rebateType = :rebateType and to_char(createDate,'yyyymm') = :createDate ";
				HashMap<String, Object> map = new HashMap<>();
				map.put("walletStatus", wallet.getFlag());
				map.put("unno", unno);
				map.put("rebateType", rebateType);
				map.put("createDate", sd.format(now).substring(0, 6));
				int o = genericJpaDao.executeSql(updateWSql,map);
				Map<String,String> result = returnUpdateResult(unno,rebateType,sd.format(now).substring(0, 6));
				if(o>0)
					return new RespEntity(ReturnCode.SUCCESS, "状态变更成功",result);
				return new RespEntity(ReturnCode.FALT, "状态变更失败");
			}else{//新增变更状态
				String insertWSql = "insert into check_walletCashSwitch_w(unno,rebateType,createDate,walletStatus) "
						+ "values('"+unno+"','"+rebateType+"',sysdate,'"+wallet.getFlag()+"') ";
				int o = genericJpaDao.executeSql(insertWSql,null);
				Map<String,String> result = returnUpdateResult(unno,rebateType,sd.format(now).substring(0, 6));
				if(o>0)
					return new RespEntity(ReturnCode.SUCCESS, "状态变更成功",result);
				return new RespEntity(ReturnCode.FALT, "状态变更失败");
			}
		}else{//实时无记录插入实时表，历史记录表
			String insertNowSql = "insert into check_walletCashSwitch(unno,rebateType,createDate,walletStatus) "
					+ "values('"+unno+"','"+rebateType+"',sysdate,'"+wallet.getFlag()+"') ";
			int o = genericJpaDao.executeSql(insertNowSql,null);
			if(o<1)
				return new RespEntity(ReturnCode.FALT, "状态变更失败");
			String insertLogSql = "insert into check_walletCashSwitch_Log(unno,rebateType,createDate,walletStatus) "
					+ "values('"+unno+"','"+rebateType+"',sysdate,'"+wallet.getFlag()+"') ";
			int p = genericJpaDao.executeSql(insertLogSql,null);
			Map<String,String> result = returnUpdateResult(unno,rebateType,sd.format(now).substring(0, 6));
			if(p>0)
				return new RespEntity(ReturnCode.SUCCESS, "状态变更成功",result);
			throw new NullPointerException();
		}
	}

	/**
	 * 判断上送的活动类型是否为目标活动类型
	 * @param rebateType
	 */
	public boolean checkRebateType(String rebateType){
		String sql = "select * from bill_purconfigure where type = 3 and cby = '1' and status = 1 and valueInteger = :rebateType  ";
		HashMap<String, Object> map = new HashMap<>();
		map.put("rebateType", rebateType);
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql,map);
		if(list.size()>0)
			return true;
		return false;
	}

	/* 
	 * 根据机构号查询实时钱包状态  -1 开
	 */
	private Integer returnUnnoWalletCashSwitch(String unno,String rebateType){
		String sql = "select unno,walletStatus from check_walletCashSwitch where unno = :unno and rebateType = :rebateType";
		HashMap<String, String> map = new HashMap<>();
		map.put("unno", unno);
		map.put("rebateType", rebateType);
		List<Object[]> list2 = genericJpaDao.queryByNativeSql(sql,map);
		if(list2.size()<1){
			return 0;
		}else{
			if(list2.get(0)[1].toString().equals("1"))
				return 1;
			return 2;
		}
	}

	/* 
	 * 根据机构号查询下月生效钱包状态  
	 */
	private Integer returnUnnoWalletCashSwitchW(String unno,String rebateType,String date){
		String sql = "select unno,walletStatus from check_walletCashSwitch_w where unno = :unno "
				+ "and rebateType = :rebateType and to_char(createDate,'yyyymm') = :date ";
		HashMap<String, String> map = new HashMap<>();
		map.put("unno", unno);
		map.put("rebateType", rebateType);
		map.put("date", date);
		List<Object[]> list2 = genericJpaDao.queryByNativeSql(sql,map);
		if(list2.size()<1){
			return 0;//不存在
		}else{
			if(list2.get(0)[1].toString().equals("1"))
				return 1;//开通
			return 2;//关闭
		}
	}

	/*
	 * 返回变更结果
	 */
	private Map<String, String> returnUpdateResult(String unno,String rebateType,String date){
		Map<String, String> map = new HashMap<>();
		//本月状态查询
		int a = returnUnnoWalletCashSwitch(unno,rebateType);
		String result = a==1?"1":"0";
		if(a==0){
			throw new NullPointerException();
		}else{
			map.put("status", result);
		}
		//下月生效状态查询
		int b = returnUnnoWalletCashSwitchW(unno,rebateType,date);
		if(b==0){
			map.put("statusNextMonth", result);
		}else{
			String nextMonth = b==1?"1":"0";
			map.put("statusNextMonth", nextMonth);
		}
		map.put("unno", unno);
		return map;
	}




}

