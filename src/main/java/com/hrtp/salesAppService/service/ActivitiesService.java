package com.hrtp.salesAppService.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hrtp.salesAppService.entity.appEntity.WalletEntity;
import com.hrtp.system.common.GenericJpaDao;

/**
 * 关于活动类型拆分服务
 * @author YQ
 */
@Service
public class ActivitiesService {

	private static Logger log = LoggerFactory.getLogger(ActivitiesService.class);
	@Autowired
	private GenericJpaDao genericJpaDao;

	public List<Map<String,String>> getActivities(WalletEntity wallet){
		List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();;
		String sql = "";
		if(Integer.parseInt(wallet.getUnLvl())>2){//二代及以下机构
			sql = "select m.unno,k.profitRule "
					+ "from check_micro_profittemplate k,"
					+ "check_unit_profitemplate m,bill_purconfigure b "
					+ "where m.aptid = k.aptid "
					+ "and k.profitRule = b.valueInteger "
					+ "and b.type = 3 and b.cby = '1' and m.status = 1 "
					+ "and k.mataintype != 'D' and m.unno = :unno order by k.profitRule desc";
		}else{//一代机构
			sql = "select a.unno, a.txn_Detail "
					+ "from Hrt_Unno_Cost a,bill_purconfigure b "
					+ "where a.txn_Detail = b.valueinteger "
					+ "and a.status = 1 "
					+ "and b.type = 3 and b.cby = '1' and a.unno = :unno order by a.txn_Detail desc";
		}
		HashMap<String, Object> param = new HashMap<>();
		param.put("unno", wallet.getUnno());
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql, param);
		for(int i = 0;i<list.size();i++){
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("profitRuleName", "活动"+list.get(i)[1].toString());
			map1.put("profitRule", list.get(i)[1].toString());
			resultList.add(map1);
		}
		if(wallet.getFlag()!=1&&resultList.size()>1){
			Map<String, String> map = new HashMap<String, String>();
			map.put("profitRuleName", "全部");
			map.put("profitRule", "");
			resultList.add(map);
		}
		return resultList;
	}
}
