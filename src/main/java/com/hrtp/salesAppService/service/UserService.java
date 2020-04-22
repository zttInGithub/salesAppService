package com.hrtp.salesAppService.service;

import com.argorse.security.core.hash.MD5Wrapper;
import com.hrtp.salesAppService.dao.UserRepository;
import com.hrtp.salesAppService.dao.UserDao;
import com.hrtp.salesAppService.entity.appEntity.BillAgentInfoEntity;
import com.hrtp.salesAppService.entity.appEntity.LoginReqEntity;
import com.hrtp.salesAppService.entity.appEntity.LoginRespEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.exception.AppException;
import com.hrtp.system.costant.ReturnCode;
import com.hrtp.system.util.AppVersion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * LoginService
 * description
 * create by lxj 2018/8/20
 **/
@Service
public class UserService {
	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserDao userDao;
	public RespEntity doLogin(LoginReqEntity reqBody){
		LoginRespEntity respEntity = new LoginRespEntity();
		RespEntity rs = new RespEntity();
		//判断是不是业务员登录
		Map<String,String> checkMap = checkSale(reqBody);
		String sql = "select t3.buid,t3.unno,t1.user_name,t1.user_id,t1.user_lvl,t3.agent_type,t3.cashratio,t3.cashswitch,t4.un_lvl from sys_user t1 left " +
				"join sys_unit_user t2 on t1.user_id = t2.user_id left join bill_agentunitinfo t3 on t2.unno = t3.unno left join sys_unit t4 on t3.unno = t4.unno " +
				" where t1.login_name = :loginName and t1.pwd = :password and t1.status <> :status ";
		if(reqBody.getAppVersion() ==null || AppVersion.is_version_great_than(reqBody.getAppVersion(),"2.2.6") || //2.2.6之前的版本不可以请求业务员接口
				(!checkMap.get("flag").equals("00") && !AppVersion.is_version_great_than(reqBody.getAppVersion(),"2.2.6"))){
			sql += "and t1.user_lvl = 0 ";
		}
		//+"and substr (t3.unno,3,1) not in (0,1) " ;
		HashMap<String, Object> param = Optional.ofNullable(reqBody).map(user -> {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("loginName", user.getLoginName());
			String pwdEnc = "";
			try {
				pwdEnc = MD5Wrapper.encryptMD5ToString(user.getPassword());
			} catch (Exception e) {
				throw new AppException(e);
			}
			map.put("password", pwdEnc);
			map.put("status",-1);
			return map;
		}).orElseGet(()->{
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("loginName","");
			map.put("password","");
			map.put("status",-1);
			return map;
		});

		return Optional.ofNullable(userDao.querySingleRowByNativeSql(sql,param)).map(map -> {
			logger.info("登录成功");			
			if(checkMap.get("flag").equals("00")){
				respEntity.setIsSale(1);
				respEntity.setUserName(checkMap.get("salename"));
				respEntity.setPhone(checkMap.get("phone").equals("")?"":checkMap.get("phone").substring(0, 3)+"****"+checkMap.get("phone").substring(checkMap.get("phone").length()-4, checkMap.get("phone").length()));
				respEntity.setIsManager(Integer.parseInt(checkMap.get("isManager")));
				respEntity.setIsLeader(Integer.parseInt(checkMap.get("isLeader")));
				respEntity.setGroup(checkMap.get("salesGroup"));
				respEntity.setUserId(Integer.parseInt(checkMap.get("userId")));
			}else{
				try{
					respEntity.setIsSale(0);
					respEntity.setBuId((map.get("0")+""));
					respEntity.setUnno(map.get("1")+"");
					respEntity.setUserName(map.get("2")+"");
					respEntity.setUserId(Integer.parseInt(map.get("3")+""));
					respEntity.setUserLvl(Integer.parseInt(map.get("4")+""));
					respEntity.setAgentType(Integer.parseInt(map.get("5")+""));
					respEntity.setCashRatio(map.get("6")+"");
					respEntity.setCashSwitch(Integer.parseInt(map.get("7")+""));
					respEntity.setUnLvl(Integer.parseInt(map.get("8")+""));
				}catch(NumberFormatException e){
					logger.info("登录失败:机构信息不完整");
					rs.setReturnMsg("登录失败：机构信息不完整！");
					rs.setReturnCode(ReturnCode.FALT);
				}
			}
			rs.setReturnCode(ReturnCode.SUCCESS);
			rs.setReturnMsg("登录成功");
			rs.setReturnBody(respEntity);
			return rs;
		}).orElseGet(() -> {
			logger.info("登录失败");
			rs.setReturnMsg("登录失败");
			rs.setReturnCode(ReturnCode.FALT);
			return rs;
		});
	}

	/**
	 * 接收手机号，返回机构UserId和机构名称
	 * @author yq
	 */
	public RespEntity returnUser(LoginReqEntity reqBody){
		RespEntity rs = new RespEntity();
		if(reqBody.getMobile()==null||"".equals(reqBody.getMobile())||reqBody.getMobile().length()!=11){
			rs.setReturnCode(ReturnCode.FALT);
			rs.setReturnMsg("手机号码格式有误！");
			return rs;
		}
		String sql = "select b.user_id,a.agentName from BILL_AGENTUNITINFO a,sys_user b,SYS_UNIT_USER c "
				+ " where a.unno = c.unno and c.user_id = b.user_id and b.user_lvl = '0' "
				+ " and mobilePhone = '"+reqBody.getMobile()+"' and b.status <> '-1'  ";
		List<Object[]> list = userDao.queryByNativeSql(sql);
		if(list.size()<1){
			rs.setReturnMsg("该手机号未注册或状态异常！");
			rs.setReturnCode(ReturnCode.FALT);
		}else{
			List<Map<String, Object>> list1 = new ArrayList<Map<String,Object>>();
			for(int i = 0;i < list.size();i++){
				Map<String, Object> map = new HashMap<String,Object>();
				map.put("userId", list.get(i)[0].toString());
				map.put("unName", list.get(i)[1].toString());
				list1.add(map);
			}
			rs.setReturnCode(ReturnCode.SUCCESS);
			rs.setReturnMsg("查询成功");
			rs.setReturnBody(list1);
		}
		return rs;
	}

	/**
	 * 接收UserId和密码登录
	 * @author yq
	 */
	public RespEntity returnStatus(LoginReqEntity reqBody){
		LoginRespEntity respEntity = new LoginRespEntity();
		RespEntity rs = new RespEntity();
		String password = reqBody.getPassword();
		String userId = reqBody.getUserId();
		HashMap<String , Object> param = new HashMap<String,Object>();
		param.put("password", password);
		param.put("userId", userId);
		String sql = "select t3.buid,t3.unno,t3.agentName,t1.user_id,t4.un_lvl,t3.agent_type,t3.cashratio,t3.cashswitch,t1.user_lvl from sys_user t1 left " +
				"join sys_unit_user t2 on t1.user_id = t2.user_id left join bill_agentunitinfo t3 on t2.unno = t3.unno left join "+
				"sys_unit t4 on t3.unno = t4.unno " +
				"where t1.pwd = :password and t1.user_id = :userId ";
		return Optional.ofNullable(userDao.querySingleRowByNativeSql(sql,param)).map(result -> {
			logger.info("登录成功");
			respEntity.setBuId((result.get("0").toString()));
			respEntity.setUnno(result.get("1").toString());
			respEntity.setUserName(result.get("2").toString());
			respEntity.setUserId(Integer.parseInt(result.get("3").toString()));
			respEntity.setUnLvl(Integer.parseInt(result.get("4").toString()));
			respEntity.setAgentType(Integer.parseInt(result.get("5").toString()));
			respEntity.setCashRatio(result.get("6").toString());
			respEntity.setCashSwitch(Integer.parseInt(result.get("7").toString()));
			respEntity.setUserLvl(Integer.parseInt(result.get("8").toString()+""));
			rs.setReturnCode(ReturnCode.SUCCESS);
			rs.setReturnMsg("登录成功");
			rs.setReturnBody(respEntity);
			return rs;
		}).orElseGet(() -> {
			logger.info("登录失败");
			rs.setReturnMsg("登录失败");
			rs.setReturnCode(ReturnCode.FALT);
			return rs;
		});
	}

	/**
	 * 变更密码
	 * @author yq
	 */
	public RespEntity updatePassword(LoginReqEntity reqBody){
		RespEntity rs = new RespEntity();
		if(reqBody.getPassword().equals("")||reqBody.getUserId().equals("")||reqBody.getNewPassword().equals("")){
			rs.setReturnMsg("密码参数不可为空！");
			rs.setReturnCode(ReturnCode.FALT);
			return rs;
		}
		if(reqBody.getPassword().equals(reqBody.getNewPassword())){
			rs.setReturnMsg("新密码和原密码相同！");
			rs.setReturnCode(ReturnCode.FALT);
			return rs;
		}
		HashMap<String, Object> param1 = new HashMap<String,Object>();
		param1.put("userId", reqBody.getUserId());
		param1.put("password", reqBody.getPassword());
		String sql = "select t3.buid,t3.unno,t1.user_name,t1.login_name,t1.user_id,t4.un_lvl,t3.agent_type from sys_user t1 left " +
				"join sys_unit_user t2 on t1.user_id = t2.user_id left join bill_agentunitinfo t3 on t2.unno = t3.unno left join "+
				"sys_unit t4 on t3.unno = t4.unno " +
				"where t1.pwd = :password and t1.user_id = :userId ";
		return Optional.ofNullable(userDao.querySingleRowByNativeSql(sql,param1)).map(result -> {
			String updateSql = "update sys_user set pwd = '"+reqBody.getNewPassword()+"' where user_Id = "+reqBody.getUserId()+" and pwd = '"+reqBody.getPassword()+"' ";
			int a = userDao.executeSql(updateSql);
			if(a>0){
				rs.setReturnCode(ReturnCode.SUCCESS);
				rs.setReturnMsg("密码修改成功！");
				return rs;
			}else{
				rs.setReturnMsg("密码修改失败！");
				rs.setReturnCode(ReturnCode.FALT);
				return rs;
			}
		}).orElseGet(() -> {
			logger.info("原密码错误");
			rs.setReturnMsg("原密码错误！");
			rs.setReturnCode(ReturnCode.FALT);
			return rs;
		});
	}


	/**
	 * 重置密码
	 * @author yq
	 */
	public RespEntity updateNeWPassword(LoginReqEntity reqBody){
		RespEntity rs = new RespEntity();
		if(reqBody.getSign()==null||"".equals(reqBody.getSign())){
			rs.setReturnMsg("密码重置失败！");
			rs.setReturnCode(ReturnCode.FALT);
			return rs;
		}
		String pwd = "";
		try {
			pwd = MD5Wrapper.encryptMD5ToString(reqBody.getUserId()+reqBody.getPassword()+"ellelcooep93kdkldkcmdklelmwiixkc");
		} catch (Exception e) {
			rs.setReturnMsg("密码重置失败！");
			rs.setReturnCode(ReturnCode.FALT);
			return rs;
		}
		if(!pwd.equals(reqBody.getSign())){
			rs.setReturnMsg("由于安全原因，您的密码不能被重置！");
			rs.setReturnCode(ReturnCode.FALT);
			return rs;
		}                 
		String updateSql = "update sys_user set pwd = '"+reqBody.getPassword()+"' where user_Id = "+reqBody.getUserId();
		int a = userDao.executeSql(updateSql);
		if(a>0){
			rs.setReturnCode(ReturnCode.SUCCESS);
			rs.setReturnMsg("密码重置成功！");
			return rs;
		}else{
			rs.setReturnMsg("密码重置失败！");
			rs.setReturnCode(ReturnCode.FALT);
			return rs;
		}
	}

	/**
	 * 变更密码确认当前机构是否符合发短信的标准
	 * @return boolean
	 */
	public Boolean checkSendMsg(BillAgentInfoEntity reqsBody){
		boolean flag = false;
		String sql = "select t1.user_Id from (select user_id from sys_user where user_id = :userId)t1 "
				+ "left join sys_unit_user t2 "
				+ "on t1.user_id = t2.user_id left join bill_agentunitinfo t3 on "
				+ "t2.unno = t3.unno where t3.mobilePhone = :mobile ";
		HashMap<String, Object> map = new HashMap<String ,Object>();
		map.put("userId", reqsBody.getUserId());
		map.put("mobile", reqsBody.getMobile());
		List<Object[]> list = userDao.queryByNativeSql(sql, map);
		if(list.size()>0){
			return true;
		}
		return flag;
	}

	/**
	 * 绑定结算卡确认当前机构是否符合发短信的标准
	 * @return boolean
	 */
	public Boolean checkSendMsgForProfit(BillAgentInfoEntity reqsBody){
		boolean flag = false;
		String sql = "select t1.user_Id，t2.unno from (select user_id from sys_user where user_id = :userId)t1 "
				+ "left join sys_unit_user t2 "
				+ "on t1.user_id = t2.user_id left join bill_agentunitinfo t3 on "
				+ "t2.unno = t3.unno where t3.mobilePhone = :mobile ";
		HashMap<String, Object> map = new HashMap<String ,Object>();
		map.put("userId", reqsBody.getUserId());
		map.put("mobile", reqsBody.getMobile());
		List<Object[]> list = userDao.queryByNativeSql(sql, map);
		if(list.size()>0){
			//判断父级机构是否为运营中心
			String sqlFather = "select to_char(un_lvl) un_lvl from sys_unit where unno = '"+ list.get(0)[1].toString() +"' ";
			List<String> listFather = userDao.querySingleColumnByNativeSql(sqlFather);
			if(listFather.get(0).equals("1"))
				return false;
			return true;
		}
		return flag;
	}
	
	//判断是否为业务员
	private Map<String,String> checkSale(LoginReqEntity reqBody){
		String sql = "select a.salename,nvl(a.isManager,0),nvl(a.isLeader,0),a.salesGroup,a.phone,a.busId "
				+ "from bill_agentsalesinfo a,sys_user b "
				+ "where a.user_id = b.user_id "
				+ "and b.login_name = :loginName and b.pwd = :pwd  "
				+ "and a.salesGroup is not null ";
		HashMap<String, Object> map = new HashMap<String ,Object>();
		map.put("loginName", reqBody.getLoginName());
		String pwd = "";
		try {
			pwd = MD5Wrapper.encryptMD5ToString(reqBody.getPassword());
		} catch (Exception e) {
			throw new AppException(e);
		}
		map.put("pwd", pwd);
		List<Object[]> list = userDao.queryByNativeSql(sql, map);
		Map<String,String> resultMap = new HashMap<String,String>();
		if(list.size()>0){
			resultMap.put("salename", list.get(0)[0]==null?"":list.get(0)[0].toString());
			resultMap.put("isManager", list.get(0)[1]==null?"":list.get(0)[1].toString());
			resultMap.put("isLeader", list.get(0)[2]==null?"":list.get(0)[2].toString());
			resultMap.put("salesGroup", list.get(0)[3]==null?"":list.get(0)[3].toString());
			resultMap.put("phone", list.get(0)[4]==null?"":list.get(0)[4].toString());
			resultMap.put("userId", list.get(0)[5]==null?"":list.get(0)[5].toString());
			resultMap.put("flag", "00");
		}else{
			resultMap.put("flag", "99");
		}
		return resultMap;
	}


}
