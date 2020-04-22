package com.hrtp.salesAppService.service;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hrtp.redis.JedisSource;
import com.hrtp.salesAppService.dao.BankCardRepository;
import com.hrtp.salesAppService.entity.appEntity.BankCardEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.ormEntity.BankCardModel;
import com.hrtp.system.common.GenericJpaDao;
import com.hrtp.system.costant.ReturnCode;
import com.hrtp.system.util.HttpXmlClient;

@Service
public class BankCardService {

	private static Logger log = LoggerFactory.getLogger(BankCardService.class);
	@Autowired
	private BankCardRepository bankCardRepository;

	//----------PLUS---------
	@Autowired
	private GenericJpaDao genericJpaDao;
	@Autowired
	private JedisSource jedis;
	@Value("${juheAppKey}")
	private String juheAppKey;
	@Value("${juheOpenId}")
	private String juheOpenId;
	@Value("${juheAddress}")
	private String juheAddress;
	@Value("${jxUrl}")
	private String jxUrl;
	@Value("${JXmiyao}")
	private String JXmiyao;
	@Value("${JXmerchno}")
	private String JXmerchno;

	public RespEntity findBankCardInfo(BankCardEntity re) {
		if(re.getCardBin()!=null&&!"".equals(re.getCardBin())) {
			BankCardModel cardModel = bankCardRepository.findBankCardInfo(re.getCardBin());
			return new RespEntity(ReturnCode.SUCCESS,"查询成功",cardModel);
		}
		return new RespEntity(ReturnCode.FALT,"查询失败");
	}

	/**
	 * 添加结算卡信息
	 * @author yq
	 */
	//查询如果认证通过，直接返回成功，不走三方
	public Integer queryTxnAuthCountYes(BankCardEntity matb) {
		HashMap<String,Object> map = new HashMap<String, Object>();
		String sql="select t.legalNum from bill_merauthinfo t where 1=1 "
				+ "and t.status='00' and t.respcd='2000' and t.bankAccName=:bankAccName "
				+ "and t.bankAccNo=:bankAccNo "
				+ "and t.legalNum=:legalNum  ";
		map.put("bankAccName",matb.getBankAccName());
		map.put("bankAccNo",matb.getBankAccNo());
		map.put("legalNum",matb.getIdCard());
		Integer count =genericJpaDao.getRowsCount(sql, map);
		return count;
	}

	//查询当天如果认证失败
	public Integer queryTxnAuthCountNoToDay(BankCardEntity matb) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		HashMap<String,Object> map = new HashMap<String, Object>();
		String sql="select t.status from bill_merauthinfo t where t.authType=:authType " +
				" and t.bankAccName=:bankAccName and t.bankAccNo=:bankAccNo and t.legalNum=:legalNum " +
				" and t.status='00' and t.respcd!='2000' " +
				" and t.cdate >=  to_date('"+df.format(new Date())+" 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		map.put("authType","TXN");
		map.put("bankAccName",matb.getBankAccName());
		map.put("bankAccNo",matb.getBankAccNo());
		map.put("legalNum",matb.getIdCard());
		Integer count = genericJpaDao.getRowsCount(sql, map);
		return count;
	}

	// 查询当天此机构交易验证失败次数
	public Integer queryTxnAuthCount(BankCardEntity matb) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		HashMap<String,Object> map = new HashMap<String, Object>();
		String sql="select t.unno from bill_merauthinfo t where t.authType=:authType " +
				" and t.unno=:unno " +
				" and t.status='00' and t.respcd!='2000' " +
				" and t.cdate >= to_date('"+df.format(new Date())+" 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		map.put("authType","TXN");
		map.put("unno",matb.getUnno());
		Integer count = genericJpaDao.getRowsCount(sql, map);
		return count;
	}

	//	认证上送通道      聚合认证/吉信认证
	public Map<String, String> addAuthInfoAll(BankCardEntity matb)throws Exception{
		Map<String, String> rtnMap = new HashMap<String, String>();
		//读取redis @author yq
		String mag_flag = "8";
		try {
			mag_flag = jedis.queryMapKey("AuthInfoType", "Type2").trim();
			log.info("认证通道读取redis参数，读取结果为" + mag_flag);
			if (mag_flag == null || "".equals(mag_flag.trim())) {
				log.info("从redis读取数据异常，使用默认吉信通道...");
			}
		} catch (Exception e) {
			log.info("请求redis出现异常，使用默认吉信通道...");
			log.error(e.toString());
		}
		if("11".equals(mag_flag)){
			// 聚合认证
			rtnMap = juheMerAuth(matb,rtnMap,mag_flag);
		}else{
			//吉信
			String sysseqnb = UUID.randomUUID().toString().replaceAll("-", "");
			String transcode = null;
			Map<String, String> map = new HashMap<String, String>();
			map.put("bankcard", matb.getBankAccNo());//银行卡号
			map.put("dsorderid", sysseqnb);//商户订单号
			map.put("idcard", matb.getIdCard());//证件号码
			map.put("idtype", "01");//证件类型
			map.put("merchno", JXmerchno);//商户号
			map.put("ordersn", sysseqnb);//流水号
			map.put("username", matb.getBankAccName());//姓名
			map.put("version", "0100");//版本号
			map.put("sceneCode", "02");//交易业务场景
			map.put("sCustomerName", "和融通-商户实名认证");//二级商户名称
			StringBuffer hrtStr =new StringBuffer();
			hrtStr.append("bankcard=").append(matb.getBankAccNo()).append("dsorderid=").append(sysseqnb).append("idcard=").append(matb.getIdCard()).append("idtype=01").append("merchno=").append(JXmerchno);
			map.put("transcode","106");//交易码
			transcode="106";
			hrtStr.append("ordersn=").append(sysseqnb)
			.append("sCustomerName=和融通-商户实名认证")
			.append("sceneCode=02")
			.append("transcode=").append(transcode).append("username=").append(matb.getBankAccName()).append("version=0100").append(JXmiyao);
			map.put("sign", DigestUtils.md5Hex(hrtStr.toString()));
			String info = JSON.toJSONString(map);
			log.info("请求吉信对接程序请求信息json:"+info);
			String result=HttpXmlClient.post(jxUrl, info);
			//解析返回结果
			JSONObject jsonObject = JSONObject.parseObject(result);
			try{
				String status =jsonObject.getString("returncode");
				String msg = null;
				String sql = "insert into bill_merauthinfo(bmatid,legalnum,bankaccname,"
						+ "bankaccno,status,respcd,respinfo,cdate,sysseqnb,"
						+ "authtype,unno) values (s_bill_merauthinfo.nextVal,'"+matb.getIdCard()+"',"
						+ "'"+matb.getBankAccName()+"','"+matb.getBankAccNo()+"','00',";
				if("0000".equals(status)){
					msg = "认证成功";
					rtnMap.put("msg", msg);
					rtnMap.put("status", "2");
					rtnMap.put("sessionId", sysseqnb);
					sql += "2000,";
				}else{
					msg =jsonObject.getString("errtext");
					rtnMap.put("msg", msg);
					rtnMap.put("status", "0");
					rtnMap.put("sessionId", sysseqnb);
					sql += "2001,";
				}
				sql += "'"+msg+"',sysdate,'"+sysseqnb+"','TXN','"+matb.getUnno()+"')";
				genericJpaDao.executeSql(sql);
				log.info("请求吉信对接程序返回信息：流水("+sysseqnb+");结果 ("+status+");信息("+msg+")");
			}catch(Exception e){
				log.info("请求吉信对接程序请求信息json解析失败");
				log.error(e.toString());
				rtnMap.put("msg", "认证失败");
				rtnMap.put("status", "0");
				rtnMap.put("sessionId", "");
			}
		}
		log.info("认证返回信息：type : "+"TXN"+" ; target : "+mag_flag+"; bankNo : "+matb.getBankAccNo()+" ; msg : "+rtnMap.get("msg"));
		return rtnMap;
	}

	/**
	 * 聚合三要素认证
	 * @param matb
	 * @param rtnMap
	 * @param mag_flag
	 */
	public Map<String, String> juheMerAuth(BankCardEntity matb,Map<String, String> rtnMap,String mag_flag){
		String sysseqnb = UUID.randomUUID().toString().replaceAll("-", "");
		Map<String, String> map = new HashMap<String, String>();
		//        key string 是 在个人中心->我的数据,接口名称上方查看
		//        realname string 是 姓名，需要utf8 Urlencode
		//        idcard string 是 身份证号码
		//        bankcard string 是 银行卡卡号
		//        uorderid string 否 用户订单号,不超过32位，要保证唯一
		//        isshow int 否 是否显示匹配信息,0默认不显示，1显示
		//        sign string 是 md5(openid+appkey+bankcard+realname+idcard),openid在个人中
		//        心查询
		map.put("key",juheAppKey);
		map.put("realname",matb.getBankAccName());
		map.put("idcard",matb.getIdCard());
		map.put("bankcard",matb.getBankAccNo());
		map.put("uorderid",sysseqnb);
		map.put("isshow","1");
		String md5Vlaue = MD5(juheOpenId+juheAppKey+matb.getBankAccNo()+matb.getBankAccName()+matb.getIdCard());
		// 聚合md5加密返回的16进制32位小写编码
		map.put("sign",md5Vlaue.toLowerCase());
		log.info("聚合认证请求参数:"+ com.alibaba.fastjson.JSONObject.toJSONString(map));
		String result = HttpXmlClient.post(juheAddress, map);
		com.alibaba.fastjson.JSONObject jsonResult=com.alibaba.fastjson.JSONObject.parseObject(result);
		log.info("聚合认证返回参数:"+jsonResult.toString());
		try{
			String msg = null;
			Integer error_code = jsonResult.getInteger("error_code");
			String reason = jsonResult.getString("reason");
			com.alibaba.fastjson.JSONObject content= jsonResult.getJSONObject("result");
			Integer res = content.getInteger("res");
			String sql = "insert into bill_merauthinfo(bmatid,legalnum,bankaccname,"
					+ "bankaccno,status,respcd,respinfo,cdate,sysseqnb,"
					+ "authtype,unno) values(s_bill_merauthinfo.nextVal,'"+matb.getIdCard()+"',"
					+ "'"+matb.getBankAccName()+"','"+matb.getBankAccNo()+"','00',";
			if(error_code==0){
				if(res==1){
					msg = "认证成功";
					rtnMap.put("msg", msg);
					rtnMap.put("status", "2");
					rtnMap.put("sessionId", sysseqnb);
					sql += "2000,";
				}else{
					msg =content.getString("message");
					rtnMap.put("msg", reason+"|"+msg);
					rtnMap.put("status", "0");
					rtnMap.put("sessionId", sysseqnb);
					sql += "2001,";
				}
			}else{
				msg =content.getString("message");
				rtnMap.put("msg", reason+"|"+msg);
				rtnMap.put("status", "0");
				rtnMap.put("sessionId", sysseqnb);
				sql += "2001,";
			}
			sql += "'"+msg+"',sysdate,'"+sysseqnb+"','TXN','"+matb.getUnno()+"')";
			genericJpaDao.executeSql(sql);
		}catch(Exception e){
			log.info("请求聚合认证失败!!!!");
			log.error(e.toString());
			rtnMap.put("msg", "认证失败");
			rtnMap.put("status", "0");
			rtnMap.put("sessionId", "");
		}
		return rtnMap;
	}

	/**
	 * 变更结算卡
	 * @param matb
	 * @author yq
	 */
	public int updateAgentUnitInfo(BankCardEntity matb){
		String sql = "update BILL_AGENTUNITINFO set BANKACCNO = '"+matb.getBankAccNo()+"',"
				+ "BANKACCNAME = '"+matb.getBankAccName()+"',legalnum = '"+matb.getIdCard()+"' ,"
				+ "bindCardStatus = 1,bankbranch = '"+matb.getbName()+"',officeAddressUpload = '"+matb.getUrl()+"' "
				+ "where unno = '"+matb.getUnno()+"' ";
		int i = genericJpaDao.executeSql(sql);
		return i;
	}

	/**
	 * 是否可以变更结算卡
	 * @param matb
	 * @author yq
	 */
	public boolean checkCardInfo(BankCardEntity matb){
		//判断父级机构是否为运营中心
		String sqlFather = "select to_char(un_lvl) un_lvl from sys_unit where unno = '"+ matb.getUnno().toString() +"' ";
		List<String> listFather = genericJpaDao.querySingleColumnByNativeSql(sqlFather);
		if(listFather.get(0).equals("1") || listFather.get(0).equals("2"))//20200417短期限制1代变更结算卡
			return false;
		return true;
	}

	/**
	 * 查询是否绑定结算卡
	 * @param matb
	 * @author yq
	 */
	public RespEntity queryCard(BankCardEntity re) {
		RespEntity rs = new RespEntity();
		Map<String, String> returnMap = new  HashMap<String, String>();
		String sql  = "select bindCardStatus,BANKACCNAME,legalnum,BANKACCNO,bankbranch,officeAddressUpload from BILL_AGENTUNITINFO where "
				+ "unno = :unno and mobilePhone is not null";
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("unno", re.getUnno());
		List<Object[]> list = genericJpaDao.queryByNativeSql(sql, map);
		if(list.size()<1){
			//机构号登录
			returnMap.put("couldPost", "1");
			returnMap.put("status", "");
		}else if(list.get(0)[0]==null){
			//未绑卡
			returnMap.put("couldPost", "0");
			returnMap.put("status", "1");
		}else{
			//已绑卡
			returnMap.put("couldPost", "0");
			returnMap.put("status", "0");
			returnMap.put("bankAccName", list.get(0)[1].toString());
			returnMap.put("IdCard", list.get(0)[2].toString());
			//卡信息中间加*
			String bankNo = list.get(0)[3].toString();
			String lastFour = bankNo.substring(bankNo.length()-4);
			returnMap.put("bankAccNo",bankNo.substring(0,6)+"******"+lastFour);
			returnMap.put("bankName", list.get(0)[4].toString());
			returnMap.put("show", lastFour);
			returnMap.put("url", list.get(0)[5].toString());
		}
		rs.setReturnBody(returnMap);
		rs.setReturnCode(ReturnCode.SUCCESS);
		rs.setReturnMsg("查询成功！");
		return rs;
	}

	//认证md5
	private String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes("UTF-8");
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
