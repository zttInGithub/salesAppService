package com.hrtp.salesAppService.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.appEntity.WalletEntity;
import com.hrtp.salesAppService.service.WalletService;
import com.hrtp.system.costant.ReturnCode;

/**
 * 展业宝APP钱包Controller
 * @author yq
 */
@RestController
@RequestMapping("/app/wallet")
public class WalletController {

	private static final Logger log = LoggerFactory.getLogger(WalletController.class);

	@Autowired
	private WalletService walletService;

	/**
	 * 查询钱包首页数据
	 */
	@RequestMapping("/getHomePage")
	@ResponseBody
	public RespEntity getHomePage(@RequestBody WalletEntity wallet){
		if (log.isInfoEnabled()) {
			log.info("查询钱包首页==>请求参数{}",wallet);
		}
		Map<String, String> map = walletService.getHomePage(wallet);
		RespEntity rs = new RespEntity();
		rs.setReturnBody(map);
		rs.setReturnCode(ReturnCode.SUCCESS);
		rs.setReturnMsg("查询成功！");
		if (log.isInfoEnabled()) {
			log.info("查询钱包首页==>返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 查询钱包支出明细
	 */
	@RequestMapping("/queryCashHis")
	@ResponseBody
	public RespEntity queryCashHis(@RequestBody WalletEntity wallet){
		if (log.isInfoEnabled()) {
			log.info("查询钱包支出明细==>请求参数{}",wallet);
		}
		RespEntity rs = walletService.queryCashHis(wallet);
		if (log.isInfoEnabled()) {
			log.info("查询钱包支出明细==>返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 查询钱包收入明细 --老版本
	 */
	@RequestMapping("/queryAddProfit")
	@ResponseBody
	public RespEntity queryAddProfit(@RequestBody WalletEntity wallet){
		if (log.isInfoEnabled()) {
			log.info("查询钱包收入明细==>请求参数{}",wallet);
		}
		RespEntity rs = walletService.queryAddProfit(wallet);
		if (log.isInfoEnabled()) {
			log.info("查询钱包收入明细==>返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 钱包余额提现
	 */
	@RequestMapping("/cashAmt")
	@ResponseBody
	public RespEntity cashAmt(@RequestBody WalletEntity wallet){
		if (log.isInfoEnabled()) {
			log.info("钱包提现==>请求参数{}",wallet);
		}
		if (wallet.getUnLvl().equals("1")) {
			return new RespEntity(ReturnCode.FALT, "您目前为运营中心账号登录，为了保障您的权益，不可以进行该操作！");
		}
		RespEntity rs = walletService.updateCashAmt(wallet);
		if (log.isInfoEnabled()) {
			log.info("钱包提现==>返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 查询钱包收入明细下级代理贡献明细   --老版本
	 */
	@RequestMapping("/queryAddProfitDownUnit")
	@ResponseBody
	public RespEntity queryAddProfitDownUnit(@RequestBody WalletEntity wallet){
		if (log.isInfoEnabled()) {
			log.info("收入明细下级代理贡献查询==>请求参数{}",wallet);
		}
		RespEntity rs = walletService.queryAddProfitDownUnit(wallet);
		if (log.isInfoEnabled()) {
			log.info("收入明细下级代理贡献查询==>返回参数{}",rs);
		}
		return rs;
	}


	/**
	 * 查询钱包收入明细下级代理贡献产品交易类型明细 --老版本
	 */
	@RequestMapping("/queryAddProfitBy")
	@ResponseBody
	public RespEntity queryAddProfitBy(@RequestBody WalletEntity wallet){
		if (log.isInfoEnabled()) {
			log.info("收入明细下级代理交易类型明细查询==>请求参数{}",wallet);
		}
		RespEntity rs = walletService.queryAddProfitBy(wallet);
		if (log.isInfoEnabled()) {
			log.info("收入明细下级代理交易类型明细查询==>返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 2.5版本查询钱包收入明细
	 */
	@RequestMapping("/queryAddProfitFor2_5")
	@ResponseBody
	public RespEntity queryAddProfitFor2_5(@RequestBody WalletEntity wallet){
		if (log.isInfoEnabled()) {
			log.info("查询钱包收入明细==>请求参数{}",wallet);
		}
		RespEntity rs = walletService.queryAddProfitFor2_5(wallet);
		if (log.isInfoEnabled()) {
			log.info("查询钱包收入明细==>返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 2.5版本查询钱包收入明细下级代理贡献明细
	 */
	@RequestMapping("/queryAddProfitDownUnitFor2_5")
	@ResponseBody
	public RespEntity queryAddProfitDownUnitFor2_5(@RequestBody WalletEntity wallet){
		if (log.isInfoEnabled()) {
			log.info("收入明细下级代理贡献查询==>请求参数{}",wallet);
		}
		if(wallet.getRebateType()==null||wallet.getRebateType().equals(""))
			return new RespEntity(ReturnCode.FALT,"请联系上级代理进行设置。");
		if(!walletService.checkRebateType(wallet.getRebateType()))
			return new RespEntity(ReturnCode.FALT,"请上传指定活动类型");
		RespEntity rs = walletService.queryAddProfitDownUnitFor2_5(wallet);
		if (log.isInfoEnabled()) {
			log.info("收入明细下级代理贡献查询==>返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 查询钱包收入明细下级代理贡献产品交易类型明细
	 */
	@RequestMapping("/queryAddProfitByFor2_5")
	@ResponseBody
	public RespEntity queryAddProfitByFor2_5(@RequestBody WalletEntity wallet){
		return new RespEntity(ReturnCode.FALT,"请升级到最新版本的APP进行查看。");
	}

	/**
	 * 查询钱包收入明细下级代理贡献产品交易类型明细
	 */
	@RequestMapping("/queryAddProfitByFor2_5_1")
	@ResponseBody
	public RespEntity queryAddProfitByFor2_5_1(@RequestBody WalletEntity wallet){
		if (log.isInfoEnabled()) {
			log.info("收入明细下级代理交易类型明细查询==>请求参数{}",wallet);
		}
		if(wallet.getRebateType()==null||wallet.getRebateType().equals(""))
			return new RespEntity(ReturnCode.FALT,"请联系上级代理进行设置。");
		if(!walletService.checkRebateType(wallet.getRebateType()))
			return new RespEntity(ReturnCode.FALT,"请上传指定活动类型");
		RespEntity rs = walletService.queryAddProfitByFor2_5(wallet);
		if (log.isInfoEnabled()) {
			log.info("收入明细下级代理交易类型明细查询==>返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 2.5版本查询下级代理钱包开关状态
	 */
	@RequestMapping("/queryWalletSwitchFor2_5")
	@ResponseBody
	public RespEntity queryWalletSwitchFor2_5(@RequestBody WalletEntity wallet){
		if (log.isInfoEnabled()) {
			log.info("查询下级代理钱包开关状态==>请求参数{}",wallet);
		}
		if(wallet.getRebateType()==null||wallet.getRebateType().equals(""))
			return new RespEntity(ReturnCode.FALT,"请联系上级代理进行设置。");
		if(!walletService.checkRebateType(wallet.getRebateType()))
			return new RespEntity(ReturnCode.FALT,"请上传指定活动类型");
		RespEntity rs = walletService.queryWalletSwitchFor2_5(wallet);
		if (log.isInfoEnabled()) {
			log.info("查询下级代理钱包开关状态==>返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 2.5版本查询下级代理钱包开关状态修改
	 */
	@RequestMapping("/updateWalletSwitchFor2_5")
	@ResponseBody
	public RespEntity updateWalletSwitchFor2_5(@RequestBody WalletEntity wallet){
		if (log.isInfoEnabled()) {
			log.info("下级代理钱包开关状态修改==>请求参数{}",wallet);
		}
		if(wallet.getRebateType()==null||wallet.getRebateType().equals(""))
			return new RespEntity(ReturnCode.FALT,"请上传活动类型参数");
		if(!walletService.checkRebateType(wallet.getRebateType()))
			return new RespEntity(ReturnCode.FALT,"请上传指定活动类型");
		try{
			RespEntity rs = walletService.updateWalletSwitchFor2_5(wallet);
			if (log.isInfoEnabled()) {
				log.info("下级代理钱包开关状态修改==>返回参数{}",rs);
			}
			return rs;
		}catch(Exception e){
			log.info(wallet.getUnno()+":活动"+wallet.getRebateType()+"状态变更失败");
			return new RespEntity(ReturnCode.FALT, "状态变更失败");
		}
	}



}
