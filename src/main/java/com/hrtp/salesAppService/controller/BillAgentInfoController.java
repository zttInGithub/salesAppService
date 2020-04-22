package com.hrtp.salesAppService.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hrtp.salesAppService.entity.appEntity.BillAgentInfoEntity;
import com.hrtp.salesAppService.entity.appEntity.BillAgentTxnInfoEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.service.BillAgentInfoService;
import com.hrtp.system.annotation.FileCheck;
import com.hrtp.system.costant.ReturnCode;
import com.hrtp.system.util.JsonUtils;

/**
 * MyInfoController
 * description
 * create by lxj 2018/8/21
 **/
@RestController
@RequestMapping("/app/myInfo")
public class BillAgentInfoController {
	private static Logger logger = LoggerFactory.getLogger(BillAgentInfoController.class);

	@Autowired
	private BillAgentInfoService billAgentInfoService;

	/**
	 * 获取基本信息
	 * @param reqsBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBaseInfo")
	public RespEntity getBaseInfo(@RequestBody BillAgentInfoEntity reqsBody){
		if (logger.isInfoEnabled()) {
			logger.info("获取基本信息==>参数{}",reqsBody);
		}
		RespEntity rs = billAgentInfoService.getBaseInfo(reqsBody);
		if (logger.isInfoEnabled()) {
			logger.info("返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 获取结算信息
	 * @param reqsBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getTxnInfo")
	public RespEntity getTxnInfo(@RequestBody BillAgentInfoEntity reqsBody){
		if (logger.isInfoEnabled()) {
			logger.info("获取结算信息==>参数{}",reqsBody);
		}
		RespEntity rs = billAgentInfoService.getTxnInfo(reqsBody);
		if (logger.isInfoEnabled()) {
			logger.info("返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 获取联系人信息
	 * @param reqsBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getContactInfo")
	public RespEntity getContactInfo(@RequestBody BillAgentInfoEntity reqsBody){
		if (logger.isInfoEnabled()) {
			logger.info("获取联系人信息==>参数{}",reqsBody);
		}
		RespEntity rs = billAgentInfoService.getContactInfo(reqsBody);
		if (logger.isInfoEnabled()) {
			logger.info("返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 修改基本信息
	 * @param reqsBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateBaseInfo")
	public RespEntity updateBaseInfo(@RequestBody BillAgentInfoEntity reqsBody){
		if (logger.isInfoEnabled()) {
			logger.info("修改基本信息==>参数{}",reqsBody);
		}
		RespEntity rs = billAgentInfoService.updateBaseInfo(reqsBody);
		if (logger.isInfoEnabled()) {
			logger.info("返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 修改结算信息
	 * @param request
	 * @return
	 */
	@FileCheck
	@ResponseBody
	@RequestMapping(value = "/updateTxnInfo")
	public String updateTxnInfo(HttpServletRequest request) throws Exception {
		BillAgentTxnInfoEntity reqEntity = JsonUtils.mulTipartRequest2BeanX(request,
				BillAgentTxnInfoEntity.class);
		if (logger.isInfoEnabled()) {
			logger.info("修改结算信息==>参数{}",reqEntity);
		}
		RespEntity rs = billAgentInfoService.updateTxnInfo(reqEntity);
		if (logger.isInfoEnabled()) {
			logger.info("返回参数{}",rs);
		}
		return rs.toString();
	}

	/**
	 * 修改联系人信息
	 * @param reqsBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateContactInfo")
	public RespEntity updateContactInfo(@RequestBody BillAgentInfoEntity reqsBody){
		if (logger.isInfoEnabled()) {
			logger.info("修改联系人信息==>参数{}",reqsBody);
		}
		RespEntity rs = billAgentInfoService.updateContactInfo(reqsBody);
		if (logger.isInfoEnabled()) {
			logger.info("返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 查询当前机构的下属机构
	 * @param reqsBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findUnitByUnno")
	public RespEntity findUnitByUnno(@RequestBody BillAgentInfoEntity reqsBody){
		if (logger.isInfoEnabled()) {
			logger.info("查询当前机构的下属机构参数{}",reqsBody);
		}
		RespEntity rs = billAgentInfoService.findUnitByUnno(reqsBody);
		if (logger.isInfoEnabled()) {
			logger.info("查询当前机构的下属机构返回参数{}",rs);
		}
		return rs;
	}

	//=====================PLUS===============================
	/**
	 * 添加下级机构
	 * @author yq
	 */
	@ResponseBody
	@RequestMapping("/addNewUnno")
	public RespEntity addNewUnit(@RequestBody BillAgentInfoEntity reqsBody){
		if (logger.isInfoEnabled()) {
			logger.info("添加下级机构的参数{}",reqsBody);
		}
		RespEntity rs = billAgentInfoService.addNewUnit(reqsBody);
		if (logger.isInfoEnabled()) {
			logger.info("添加下级机构返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 查询未激活下级机构
	 * @author yq
	 */
	@ResponseBody
	@RequestMapping("/queryNewUnno")
	public RespEntity queryNewUnit(@RequestBody BillAgentInfoEntity reqsBody){
		if (logger.isInfoEnabled()) {
			logger.info("查询下级未激活机构的参数{}",reqsBody);
		}
		RespEntity rs = billAgentInfoService.queryNewUnit(reqsBody);
		if (logger.isInfoEnabled()) {
			logger.info("查询下级未激活下级机构返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 下级机构激活
	 * @author yq
	 */
	@CrossOrigin
	@ResponseBody
	@RequestMapping("/updateNewUnno")
	public RespEntity updateNewUnit(@RequestBody BillAgentInfoEntity reqsBody){
		if (logger.isInfoEnabled()) {
			logger.info("激活下级机构的参数{}",reqsBody);
		}
		String flag = billAgentInfoService.checkMsgRand(reqsBody.getMobile(),reqsBody.getMsg());
		if(flag.equals("00")){
			RespEntity rs = new RespEntity("99","验证码已失效，请重新获取！");
			return rs;
		}else if(flag.equals("01")){
			RespEntity rs = new RespEntity("99","短信验证码不正确");
			return rs;
		}else{
			billAgentInfoService.removeMsgRand(reqsBody.getMobile());
			RespEntity rs = billAgentInfoService.updateNewUnit(reqsBody);
			if (logger.isInfoEnabled()) {
				logger.info("激活下级机构返回参数{}",rs);
			}
			return rs;
		}
	}

	/**
	 * 激活下发短信验证码
	 * @author yq
	 */
	@CrossOrigin
	@ResponseBody
	@RequestMapping("/sendMsg")
	public RespEntity sendMsg(@RequestBody BillAgentInfoEntity reqsBody){
		RespEntity rs = new RespEntity();
		if (logger.isInfoEnabled()) {
			logger.info("激活下级机构的参数{}",reqsBody);
		}
		if(reqsBody.getMobile().length()!=11){
			rs.setReturnCode(ReturnCode.FALT);
			rs.setReturnMsg("手机号码长度有误！");
		}else{
			boolean Flag = billAgentInfoService.checkUnno(reqsBody);
			if(Flag){
				String result = billAgentInfoService.queryMesRand(reqsBody.getMobile(),2);
				if(result.equals("0")){
					rs.setReturnCode(ReturnCode.FALT);
					rs.setReturnMsg("验证码发送失败！");
				}else{
					rs.setReturnCode(ReturnCode.SUCCESS);
					rs.setReturnMsg("验证码发送成功！");
				}
				if (logger.isInfoEnabled()) {
					logger.info("激活下级机构返回参数{}",rs);
				}
			}else{
				rs.setReturnCode(ReturnCode.FALT);
				rs.setReturnMsg("激活失败！请联系上一级机构确认！");
			}
		}
		return rs;
	}




}


