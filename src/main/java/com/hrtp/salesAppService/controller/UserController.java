package com.hrtp.salesAppService.controller;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hrtp.salesAppService.entity.appEntity.BillAgentInfoEntity;
import com.hrtp.salesAppService.entity.appEntity.LoginReqEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.service.BillAgentInfoService;
import com.hrtp.salesAppService.service.UserService;
import com.hrtp.system.costant.ConstantKey;
import com.hrtp.system.costant.ReturnCode;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/app")
public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private BillAgentInfoService billAgentInfoService;

	@RequestMapping("/doLogin")
	@ResponseBody
	public RespEntity doLogin(@RequestBody LoginReqEntity reqBody, HttpServletResponse response) {
		logger.info("收到请求=====doLogin,参数：{}", reqBody);
		RespEntity rs = userService.doLogin(reqBody);
		if (rs.getReturnCode().equals(ReturnCode.SUCCESS)) {
			String subject = reqBody.getLoginName();
			String token = Jwts.builder().setSubject(subject).setExpiration(new Date(System.currentTimeMillis() +
					12 * 60 * 60 * 1000)).signWith(SignatureAlgorithm.HS512, ConstantKey.SIGNING_KEY).compact();
			response.addHeader("Authorization", "Bearer " + token);
		}
		logger.info("返回参数{}", rs);
		return rs;
	}

	/**
	 * 登录
	 * 接收手机号，返回机构UserId和机构名称
	 * @author yq
	 */
	@RequestMapping("/login/returnUser")
	@ResponseBody
	public RespEntity returnUser(@RequestBody(required=false) LoginReqEntity reqBody){
		logger.info("收到登录手机号请求=====,参数：{}", reqBody);
		RespEntity rs = userService.returnUser(reqBody);
		return rs;
	}


	/**
	 * 登录
	 * 传回userId和密码，登录
	 * @author yq
	 */
	@RequestMapping("/login/returnStatus")
	@ResponseBody
	public RespEntity returnStatus(@RequestBody(required=false) LoginReqEntity reqBody, HttpServletResponse response) {
		logger.info("收到登录密码请求=====,参数：{}", reqBody);
		RespEntity rs = userService.returnStatus(reqBody);
		if (rs.getReturnCode().equals(ReturnCode.SUCCESS)) {
			String subject = reqBody.getUserId() + String.valueOf(Math.random()).substring(2,7)  ;
			String token = Jwts.builder().setSubject(subject).setExpiration(new Date(System.currentTimeMillis() +
					12 * 60 * 60 * 1000)).signWith(SignatureAlgorithm.HS512, ConstantKey.SIGNING_KEY).compact();
			response.addHeader("Authorization", "Bearer " + token);
		}
		return rs;
	}


	/**
	 * 修改密码
	 * 传回userId和原密码，新密码
	 * @author yq
	 */
	@RequestMapping("/login/updatePassword")
	@ResponseBody
	public RespEntity updatePassword(@RequestBody(required=false) LoginReqEntity reqBody) {
		logger.info("收到修改密码请求=====,参数：{}", reqBody);
		RespEntity rs = userService.updatePassword(reqBody);
		logger.info("收到修改密码响应=====,参数：{}", rs);
		return rs;
	}

	/**
	 * 重置密码发送短验
	 * @author yq
	 */
	@ResponseBody
	@RequestMapping("/sendPasswordMsg")
	public RespEntity sendMsg(@RequestBody BillAgentInfoEntity reqsBody){
		RespEntity rs = new RespEntity();
		if (logger.isInfoEnabled()) {
			logger.info("重置密码发送短验参数{}",reqsBody);
		}
		if(reqsBody.getMobile().length()!=11){
			rs.setReturnCode(ReturnCode.FALT);
			rs.setReturnMsg("手机号码长度有误！");
		}else{
			//确认是否userId和mobile匹配
			boolean flag = userService.checkSendMsg(reqsBody);
			if(!flag){
				rs.setReturnCode(ReturnCode.FALT);
				rs.setReturnMsg("当前机构和手机号码不匹配，请确认！");
				return rs;
			}
			String result = billAgentInfoService.queryMesRand(reqsBody.getMobile(),1);
			if(result.equals("0")){
				rs.setReturnCode(ReturnCode.FALT);
				rs.setReturnMsg("验证码发送失败！");
			}else{
				rs.setReturnCode(ReturnCode.SUCCESS);
				rs.setReturnMsg("验证码发送成功！");
			}
			if (logger.isInfoEnabled()) {
				logger.info("重置密码返回参数{}",rs);
			}
		}
		return rs;
	}		

	/**
	 * 验证短验+重置密码
	 * @author yq
	 */
	@ResponseBody
	@RequestMapping("/checkPasswordMsg")
	public RespEntity checkPassWordMsg(@RequestBody LoginReqEntity reqsBody){
		RespEntity rs = new RespEntity();
		if (logger.isInfoEnabled()) {
			logger.info("重置密码校验短信验证码请求参数{}",reqsBody);
		}
		if(reqsBody.getMobile().length()!=11){
			rs.setReturnCode(ReturnCode.FALT);
			rs.setReturnMsg("手机号码长度有误！");
		}else{
			String flag = billAgentInfoService.checkMsgRand(reqsBody.getMobile(), reqsBody.getMsg());
			if(flag.equals("00")){
				rs = new RespEntity("99","验证码已失效，请重新获取！");
			}else if(flag.equals("01")){
				rs = new RespEntity("99","短信验证码不正确");
			}else{
				logger.info("校验短信验证码成功，进入重置密码...");
				billAgentInfoService.removeMsgRand(reqsBody.getMobile());
				rs = newPassword(reqsBody);
			}
		}
		if (logger.isInfoEnabled()) {
			logger.info("重置密码校验短信验证码响应参数{}",rs);
		}
		return rs;
	}	


	/**
	 * 重置密码
	 * @author yq
	 */
	private RespEntity newPassword(@RequestBody(required=false) LoginReqEntity reqBody){
		logger.info("重置密码的参数{}",reqBody);
		RespEntity rs = userService.updateNeWPassword(reqBody);
		logger.info("重置密码的返回参数{}",rs);
		return rs;
	}
	
	/**
	 * 结算卡绑定-发送短验
	 * @author yq
	 */
	@ResponseBody
	@RequestMapping("/sendMsgForProfit")
	public RespEntity sendMsgForProfit(@RequestBody BillAgentInfoEntity reqsBody){
		RespEntity rs = new RespEntity();
		if (logger.isInfoEnabled()) {
			logger.info("绑定结算卡发送短验参数{}",reqsBody);
		}
		if(reqsBody.getMobile().length()!=11){
			rs.setReturnCode(ReturnCode.FALT);
			rs.setReturnMsg("手机号码长度有误！");
		}else{
			//确认是否userId和mobile匹配+是否是运营中心
			boolean flag = userService.checkSendMsgForProfit(reqsBody);
			if(!flag){
				rs.setReturnCode(ReturnCode.FALT);
				rs.setReturnMsg("您目前为运营中心账号登录，为了保障您的权益，请登录平台进行操作！");
				return rs;
			}
			String result = billAgentInfoService.queryMesRand(reqsBody.getMobile(),3);
			if(result.equals("0")){
				rs.setReturnCode(ReturnCode.FALT);
				rs.setReturnMsg("验证码发送失败！");
			}else{
				rs.setReturnCode(ReturnCode.SUCCESS);
				rs.setReturnMsg("验证码发送成功！");
			}
			if (logger.isInfoEnabled()) {
				logger.info("绑定结算卡发送短验证返回参数{}",rs);
			}
		}
		return rs;
	}	
	
	/**
	 * 短信认证分润卡验证短验
	 * @author yq
	 */
	@ResponseBody
	@RequestMapping("/checkMsg")
	public RespEntity checkMsg(@RequestBody LoginReqEntity reqsBody){
		RespEntity rs = new RespEntity();
		if (logger.isInfoEnabled()) {
			logger.info("校验短信验证码请求参数{}",reqsBody);
		}
		if(reqsBody.getMobile().length()!=11){
			rs.setReturnCode(ReturnCode.FALT);
			rs.setReturnMsg("手机号码长度有误！");
		}else{
			String flag = billAgentInfoService.checkMsgRand(reqsBody.getMobile(), reqsBody.getMsg());
			if(flag.equals("00")){
				rs = new RespEntity("99","验证码已失效，请重新获取！");
			}else if(flag.equals("01")){
				rs = new RespEntity("99","短信验证码不正确");
			}else{
				billAgentInfoService.removeMsgRand(reqsBody.getMobile());
				rs = new RespEntity("00","验证码验证成功！");
			}
		}
		if (logger.isInfoEnabled()) {
			logger.info("校验短信验证码响应参数{}",rs);
		}
		return rs;
	}	

}