package com.hrtp.salesAppService.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.appEntity.SaleEntity;
import com.hrtp.system.common.GenericJpaDao;
import com.hrtp.system.costant.ReturnCode;

/**
 * 业务员服务
 * yq 2020/2
 */
@Service
public class SalesService {

	private static Logger log = LoggerFactory.getLogger(SalesService.class);

	@Autowired
	private GenericJpaDao genericJpaDao;


	/**
	 * 注册、激活查询（经理、组长页）
	 */
	public RespEntity queryRegister(String busId){
		Map<String,String> result = checkSale(busId);
		//总返回
		Map<String,Object> returnMap = new HashMap<String,Object>();
		//下拉框
		List<Map<String,String>> xialaList = new LinkedList<Map<String,String>>();
		//组集合
		List<Object> groupList = new ArrayList<Object>();
		if(result.get("isSale").equals("00")){
			if(result.get("isManager").equals("1")){//是否为经理，查询所有组员
				//下拉框
				Map<String,String> map1 = new HashMap<String,String>();
				map1.put("remark", "总汇总");map1.put("value", "0");
				xialaList.add(map1);
				String allGroupSql = " select minfo1,minfo2 from sys_configure where groupname = 'SalesGroup' order by minfo2 ";
				List<Object[]> allGroupList = genericJpaDao.queryByNativeSql(allGroupSql);
				for(int i = 0;i < allGroupList.size();i++){
					Map<String,String> map2 = new HashMap<String,String>();
					map2.put("remark", allGroupList.get(i)[0]+"汇总");map2.put("value", ""+allGroupList.get(i)[1]);
					xialaList.add(map2);
				}
				//组
				for(int i = 0;i < allGroupList.size();i++){
					Map<String,Object> group = new HashMap<String,Object>();
					group.put("name", allGroupList.get(i)[0]);
					group.put("value", groupList(allGroupList.get(i)[1].toString()));
					groupList.add(group);
				}
			}else if(result.get("isLeader").equals("1")){//是否为组长，查询当前组
				String groupName = returnGroup(result.get("salesGroup"));
				//下拉框
				Map<String,String> map1 = new HashMap<String,String>();
				map1.put("remark", groupName + "汇总");map1.put("value", result.get("salesGroup"));
				xialaList.add(map1);
				//组
				Map<String,Object> group = new HashMap<String,Object>();
				group.put("name", groupName);
				group.put("value", groupList(result.get("salesGroup")));
				groupList.add(group);
			}else{
				return new RespEntity(ReturnCode.FALT,"当前业务员级别为组员");
			}
			returnMap.put("xiala", xialaList);
			returnMap.put("group", groupList);
			return new RespEntity(ReturnCode.SUCCESS,"查询成功",returnMap);
		}else{
			return new RespEntity(ReturnCode.FALT,"当前登录用户不是业务员");
		}
	}

	/**
	 * 注册、激活查询
	 */
	public RespEntity queryUsedOrActive(SaleEntity reqsBody){
		Map<String,String> result = checkSale(reqsBody.getBusId());
		if(!result.get("isSale").equals("00"))
			return new RespEntity(ReturnCode.FALT,"当前登录用户不是业务员");
		//判断是否为汇总数据
		if(!reqsBody.getIsAll().equals("false") && !result.get("isManager").equals("1")){
			if(reqsBody.getIsAll().equals("0"))
				return new RespEntity(ReturnCode.FALT,"当前登录用户没有总汇总权限");
			if(!result.get("isLeader").equals("1"))
				return new RespEntity(ReturnCode.FALT,"当前登录用户没有汇总权限");
			if(result.get("isLeader").equals("1") && !result.get("salesGroup").equals(reqsBody.getIsAll()))
				return new RespEntity(ReturnCode.FALT,"当前登录用户没有该组权限");
		}
		String sql = querySql(reqsBody);
		List<Object[]> list = genericJpaDao.queryByNativeSqlWithPageAndRows(sql,reqsBody.getPage(),20,new HashMap<>());
		List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
		for(int i = 0;i < list.size();i++){
			Map<String,String> map = new HashMap<>();
			map.put("unName", list.get(i)[1]==null?"":list.get(i)[1].toString());//机构名称
			map.put("unno", list.get(i)[0]==null?"":list.get(i)[0].toString());//机构号
			map.put("count", list.get(i)[2]==null?"":list.get(i)[2].toString());//数量
			resultList.add(map);
		}
		//查询总条数
		String countSql = "select flag,sum(useCount) from ("+sql+") group by flag ";
		List<Object[]> countList = genericJpaDao.queryByNativeSql(countSql);
		String counts = countList.size()>0?countList.get(0)[1].toString():"0";
		Map<String,Object> returnMap = new HashMap<>();
		returnMap.put("counts", counts);
		returnMap.put("list", resultList);
		return new RespEntity(ReturnCode.SUCCESS,"查询成功",returnMap);
	}

	/**
	 * 返回产品类型对应的活动类型
	 */
	public RespEntity queryProductAndRebateType(SaleEntity reqsBody){
		//判断是否为业务员
		Map<String,String> result = checkSale(reqsBody.getBusId());
		if(!result.get("isSale").equals("00"))
			return new RespEntity(ReturnCode.FALT,"当前登录用户不是业务员");
		String sql = "select rebateType,productType from bill_producttypeinrebatetype where productType in('自定义政策','会员宝','会员宝PLUS','收银台') ";
		switch(reqsBody.getTxnType()){
		case "1":
			sql += " and productType in('自定义政策','会员宝') ";
			break;
		case "2":
			sql += " and productType = '会员宝PLUS' ";
			break;
		case "3":
			sql += " and productType = '收银台' ";
			break;
		}
		sql += "order by rebateType"; 
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql);
		List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
		for(int i = 0;i < list.size();i++){
			Map<String,String> map = new HashMap<>();
			map.put("name", list.get(i)[0]==null?"":"活动"+list.get(i)[0]);
			map.put("value", list.get(i)[0]==null?"":list.get(i)[0].toString());
			resultList.add(map);
		}
		return new RespEntity(ReturnCode.SUCCESS,"查询成功",resultList);
	}

	/**
	 * 返回产品类型
	 */
	public RespEntity queryProduct(SaleEntity reqsBody){
		//判断是否为业务员
		Map<String,String> result = checkSale(reqsBody.getBusId());
		if(!result.get("isSale").equals("00"))
			return new RespEntity(ReturnCode.FALT,"当前登录用户不是业务员");
		List<Map<String,String>> list = new ArrayList<>();
		Map<String,String> map = new HashMap<>();
		map.put("name", "全部");
		map.put("values","");
		list.add(map);
		Map<String,String> map1 = new HashMap<>();
		map1.put("name", "MPOS");
		map1.put("values","1");
		list.add(map1);
		Map<String,String> map2 = new HashMap<>();
		map2.put("name", "会员宝PLUS");
		map2.put("values","2");
		list.add(map2);
		Map<String,String> map3 = new HashMap<>();
		map3.put("name", "收银台");
		map3.put("values","3");
		list.add(map3);
		return new RespEntity(ReturnCode.SUCCESS,"查询成功",list);
	}

	/**
	 * 联系方式
	 */
	public RespEntity queryUnnoInfo(SaleEntity reqsBody){
		//判断是否为业务员
		Map<String,String> result = checkSale(reqsBody.getBusId());
		if(!result.get("isSale").equals("00"))
			return new RespEntity(ReturnCode.FALT,"当前登录用户不是业务员");
		//判断当前机构是否归属当前销售下
		String saleSql = "select * from bill_sale_unno where remark1 = :busid and unno = :unno ";
		HashMap<String,Object> saleMap = new HashMap<>();
		saleMap.put("unno", reqsBody.getUnno());
		saleMap.put("busid", reqsBody.getBusId());
		List<Object[]> saleList = genericJpaDao.queryByNativeSql(saleSql,saleMap);
		if(saleList.size()<1)
			return new RespEntity(ReturnCode.FALT,"上送机构不归属当前登录用户下");
		String sql = " select unno,agentName,contact,contactphone from bill_agentunitinfo where unno = :unno ";
		HashMap<String,Object> map = new HashMap<>();
		map.put("unno", reqsBody.getUnno());
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql,map);
		Map<String,String> returnMap = new HashMap<>();
		if(list.size()<1)
			return new RespEntity(ReturnCode.SUCCESS,"查询成功",returnMap);
		returnMap.put("unno", list.get(0)[0]==null?"":list.get(0)[0].toString());
		returnMap.put("unName", list.get(0)[1]==null?"":list.get(0)[1].toString());
		returnMap.put("name", list.get(0)[2]==null?"":list.get(0)[2].toString());
		returnMap.put("phone", list.get(0)[3]==null?"":list.get(0)[3].toString());
		return new RespEntity(ReturnCode.SUCCESS,"查询成功",returnMap);
	}	

	//拼接sql
	private String querySql(SaleEntity reqsBody){
		String sql = ""; 
		if(reqsBody.getUnName() != null && !reqsBody.getUnName().equals(""))
			sql += "select * from (";
		sql += "select pp,"
				+ "(select ka.un_name from sys_unit ka where ka.unno = pp) un_name,";
		if(reqsBody.getFlag().equals("0")){//注册查询
			sql += "sum(decode(nvl(status,1), 2, 1, 0)) useCount ";
		}else{
			sql += "sum(case when activaday is null then 0 else 1 end) useCount ";
		}
		sql	+= ",1 as flag from (select s.unno pp,b.status,b.activaday "
				+ "from (select a.*,"
				+ "(case when a.yunying in('j91000', '991000') then a.yidai "
				+ "else yunying end) pp from (select (select s.unno from sys_unit s "
				+ "where s.un_lvl = 1 start with s.unno = t.unno "
				+ "connect by s.unno = prior s.upper_unit) yunying,(select s.unno from sys_unit s "
				+ "where s.un_lvl = 2 start with s.unno = t.unno "
				+ "connect by s.unno = prior s.upper_unit) yidai,t.unno,"
				+ "t.rebatetype,t.activaday,t.status from bill_terminalinfo t where 1 = 1 ";
		if(reqsBody.getStartDate()==null||reqsBody.getStartDate().equals("")||
				reqsBody.getEndDate()==null||reqsBody.getEndDate().equals("")){
			//默认查询昨天数据
			if(reqsBody.getFlag().equals("0")){//注册查询
				sql += "and t.usedconfirmdate >= to_date(to_char(sysdate-1,'yyyymmdd'),'yyyymmdd') "
						+ "and t.usedconfirmdate < to_date(to_char(sysdate,'yyyymmdd'),'yyyymmdd') ";
			}else{//激活查询
				sql += "and t.activaday = to_char(sysdate-1,'yyyymmdd') ";
			}	
		}else{
			//指定日期查询
			if(reqsBody.getFlag().equals("0")){//注册查询
				sql += "and t.usedconfirmdate >= to_date('" +reqsBody.getStartDate().trim()+ "','yyyymmdd') "
						+ "and t.usedconfirmdate < to_date('" +reqsBody.getEndDate().trim()+ "','yyyymmdd')+INTERVAL '1' DAY ";
			}else{//注册查询
				sql += "and t.activaday between '" +reqsBody.getStartDate().trim()+ "' and '" +reqsBody.getEndDate().trim()+ "' ";
			}	
		}
		if(reqsBody.getTxnType() != null && !reqsBody.getTxnType().equals("") 
				&& (reqsBody.getRebateType()==null||reqsBody.getRebateType().equals(""))){
			switch(reqsBody.getTxnType()){
			case "1"://秒到
				sql += " and rebateType in(select rebateType from bill_producttypeinrebatetype where productType in('自定义政策'，'会员宝')) ";
				break;
			case "2"://PLUS
				sql += " and rebateType in(select rebateType from bill_producttypeinrebatetype where productType = '会员宝PLUS') ";
				break;
			case "3"://收银台
				sql += " and rebateType in(select rebateType from bill_producttypeinrebatetype where productType = '收银台') ";
				break;
			}
		}else if(reqsBody.getRebateType() != null && !reqsBody.getRebateType().equals("")){
			sql += " and rebateType = '" +reqsBody.getRebateType()+ "' ";
		}
		sql += ") a) b ";
		if(!reqsBody.getIsAll().equals("0")){//不是总汇总查询
			sql += "right join bill_sale_unno s on b.pp = s.unno ";
		}else{
			sql += "right join (select unno from sys_unit where "
					+ "(un_lvl = 2 and upper_unit in ('j91000', '991000')) or "
					+ "(un_lvl = 1 and upper_unit not in ('j91000', '991000'))) s on b.pp = s.unno ";
		}
		sql += "where 1 = 1 ";
		if(!reqsBody.getIsAll().equals("false") && !reqsBody.getIsAll().equals("0")){//分组汇总
			sql += "and s.remark1 in(select busid from bill_agentsalesInfo "
					+ "where salesgroup = '" +reqsBody.getIsAll()+ "') ";
		}else if(reqsBody.getIsAll().equals("0")){//总汇总查询 2.2.7版本变更为查询全部一代
			//			sql += "and s.remark1 in(select busid from bill_agentsalesInfo "
			//					+ "where salesgroup is not null ) ";
		}else{//业务员查询
			sql += "and s.remark1 = '"+reqsBody.getBusId1()+"' ";
		}
		sql += ") c where 1 = 1 ";
		if(reqsBody.getUnno() != null && !reqsBody.getUnno().equals(""))
			sql += " and pp = '" +reqsBody.getUnno().trim()+ "' ";
		sql	+= "group by pp ";
		if(reqsBody.getUnName() != null && !reqsBody.getUnName().equals(""))
			sql += ") where un_name like '%"+reqsBody.getUnName().trim()+"%' ";
		sql += " order by useCount desc ";
		return sql;
	}

	//根据组返回组员信息
	private List<Map<String,String>> groupList(String groupId){
		String sql = "select busId,saleName from bill_agentsalesinfo where salesgroup = :groupId order by isLeader ";
		HashMap<String, Object> map = new HashMap<String ,Object>();
		map.put("groupId", groupId);
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql, map);
		List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
		for(int i = 0;i < list.size();i++){
			Map<String,String> resultMap = new HashMap<String,String>();
			resultMap.put("name", list.get(i)[1].toString());
			resultMap.put("busId", list.get(i)[0].toString());
			resultList.add(resultMap);
		}
		return resultList;
	}

	//根据组返回组名
	private String returnGroup(String groupId){
		String sql = "select minfo1,minfo2 from sys_configure where groupname = 'SalesGroup' AND minfo2 = :groupId ";
		HashMap<String, Object> map = new HashMap<String ,Object>();
		map.put("groupId", groupId);
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql, map);
		return list.size()<1?"":list.get(0)[0].toString();
	}

	//根据userId判断是否为业务员，是业务员返回相关信息
	private Map<String,String> checkSale(String busId){
		String sql = "select salename,nvl(isManager,0),nvl(isLeader,0),salesGroup,busId "
				+ "from bill_agentsalesinfo where busid = "+busId
				+ " and salesGroup is not null ";
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql);
		Map<String,String> resultMap = new HashMap<String,String>();
		if(list.size()>0){
			resultMap.put("salename", list.get(0)[0]==null?"":list.get(0)[0].toString());
			resultMap.put("isManager", list.get(0)[1]==null?"":list.get(0)[1].toString());
			resultMap.put("isLeader", list.get(0)[2]==null?"":list.get(0)[2].toString());
			resultMap.put("salesGroup", list.get(0)[3]==null?"":list.get(0)[3].toString());
			resultMap.put("busId", list.get(0)[4]==null?"":list.get(0)[4].toString());
			resultMap.put("isSale", "00");
		}else{
			resultMap.put("isSale", "99");
		}
		return resultMap;
	}

}

