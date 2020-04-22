package com.hrtp.salesAppService.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hrtp.salesAppService.entity.appEntity.BankCardEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.service.BankCardService;
import com.hrtp.system.costant.ReturnCode;

/**
 * 结算卡绑定Controller
 * @date 2019年7月
 */
@RestController
@RequestMapping("/app/bankCard")
public class BankCardController {

	private static Logger logger = LoggerFactory.getLogger(BankCardController.class);

	@Autowired
	private BankCardService bankCardService;

	/**
	 * 查询卡片信息
	 * @param null
	 * @return RespEntity
	 */
	@RequestMapping("/findBankCardInfo")
	public RespEntity findMachineInfo(@RequestBody BankCardEntity re){
		if (logger.isInfoEnabled()) {
			logger.info("查询卡片信息请求参数{}",re);
		}
		RespEntity rs = bankCardService.findBankCardInfo(re);
		if (logger.isInfoEnabled()) {
			logger.info("查询卡片信息返回参数{}",rs);
		}
		return rs;
	}

	//=============================PLUS=======================================
	/**
	 * 修改结算卡信息
	 */
	@RequestMapping("/updateCardInfo")
	public RespEntity updateCardInfo(@RequestBody BankCardEntity matb){
		if (logger.isInfoEnabled()) {
			logger.info("修改结算卡信息请求参数{}",matb);
		}
		boolean flag = bankCardService.checkCardInfo(matb);
		if(!flag)
			return new RespEntity(ReturnCode.FALT, "您目前为运营中心/一代账号登录，为了保障您的权益，请登录平台进行操作！");
		RespEntity rs = new RespEntity();
		int a = bankCardService.updateAgentUnitInfo(matb);
		if(a>0){
			rs.setReturnCode(ReturnCode.SUCCESS);
			rs.setReturnMsg("结算卡修改成功");
		}else{
			rs.setReturnCode(ReturnCode.FALT);
			rs.setReturnMsg("结算卡修改失败");
		}
		if (logger.isInfoEnabled()) {
			logger.info("修改结算卡信息返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 查询是否绑定结算卡
	 * @author yq
	 * @return RespEntity
	 */
	@RequestMapping("/queryCard")
	public RespEntity queryCard(@RequestBody BankCardEntity re){
		if (logger.isInfoEnabled()) {
			logger.info("查询结算卡信息请求参数{}",re);
		}
		RespEntity rs = bankCardService.queryCard(re);
		if (logger.isInfoEnabled()) {
			logger.info("查询结算卡信息返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 添加卡片信息
	 */
	@RequestMapping("/addCardInfo")
	public RespEntity addCardInfo(@RequestBody BankCardEntity re){
		if (logger.isInfoEnabled()) {
			logger.info("添加结算卡请求参数{}",re);
		}
		boolean flag = bankCardService.checkCardInfo(re);
		if(!flag)
			return new RespEntity(ReturnCode.FALT, "您目前为运营中心/一代账号登录，为了保障您的权益，请登录平台进行操作！");
		String result = addTxnAuthInfo(re);
		RespEntity rs = new RespEntity();
		if(result.equals("SUCCESS")){
			rs.setReturnCode(ReturnCode.SUCCESS);
			rs.setReturnMsg("结算卡添加成功！");
		}else if(result.equals("Fail")){
			rs.setReturnCode(ReturnCode.FALT);
			rs.setReturnMsg("结算卡添加失败！");
		}else{
			rs.setReturnCode(ReturnCode.FALT);
			rs.setReturnMsg(result);
		}
		if (logger.isInfoEnabled()) {
			logger.info("添加结算卡请求返回参数{}",rs);
		}
		return rs;
	}

	//三要素认证
	private String addTxnAuthInfo(BankCardEntity matb){
		logger.info("认证接口信息：银行卡号:"+matb.getBankAccNo()+";持卡人姓名:"+matb.getBankAccName()+";身份证号:"+matb.getIdCard());
		//查询如果认证通过，直接返回成功
		Integer flag1 = bankCardService.queryTxnAuthCountYes(matb);
		if(flag1<1){
			// 查询当天此机构交易验证失败次数
			Integer count=bankCardService.queryTxnAuthCount(matb);
			if(count<3){
				//查询交易认证：如果当天同卡同身份证同人认证失败，返回失败
				Integer flag2 = bankCardService.queryTxnAuthCountNoToDay(matb);
				if(flag2<1){
					try{
						matb.setIdCard(matb.getIdCard().toUpperCase());
						Map<String ,String>  map=bankCardService.addAuthInfoAll(matb);
						if("2".equals(map.get("status"))){
							//成功
							//修改结算卡信息
							int a = bankCardService.updateAgentUnitInfo(matb);
							if(a>0){
								return "SUCCESS";
							}else{
								return "FAIL";
							}
						}else{
							return "结算卡实名认证失败！请更换其他银行卡!";
						}
					} catch (Exception e) {
						logger.error(e.toString());
						return "FAIL";
					}
				}else{
					return "结算卡实名认证失败！请更换其他银行卡!";
				}
			}else{
				return "今日认证失败次数超过三次，请明天再试。";
			}
		}else{
			int b = bankCardService.updateAgentUnitInfo(matb);
			if(b>0){
				return "SUCCESS";
			}else{
				return "FAIL";
			}
		}
	}



}
