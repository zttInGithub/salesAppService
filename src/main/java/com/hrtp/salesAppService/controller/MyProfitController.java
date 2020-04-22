package com.hrtp.salesAppService.controller;

import com.hrtp.salesAppService.entity.appEntity.*;
import com.hrtp.salesAppService.service.MyProfitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MyProfitController
 * description
 * create by lxj 2018/8/22
 **/
@RestController
@RequestMapping("/app/myProfit")
public class MyProfitController {
    private static Logger logger = LoggerFactory.getLogger(MyProfitController.class);

    @Autowired
    private MyProfitService myProfitService;

    /**
     * 代还分润
     * @param repayEntity
     * @return
     */
    @RequestMapping("/getCreditCardList")
    public RespEntity getCreditCardList(@RequestBody UnnoRepayEntity repayEntity){
        if (logger.isInfoEnabled()) {
            logger.info("信用卡还款分润==>参数{}",repayEntity);
        }
        RespEntity rs = myProfitService.getCreditCardList(repayEntity);
        if (logger.isInfoEnabled()) {
            logger.info("返回参数{}",rs);
        }
        return rs;
    }

    /**
     * 扫码分润
     * @param scanEntity
     * @return
     */
    @RequestMapping("/getQRCodeList")
    public RespEntity getQRCodeList(@RequestBody UnnoScanEntity scanEntity){
        if (logger.isInfoEnabled()) {
            logger.info("扫码分润==>参数{}",scanEntity);
        }
        RespEntity rs = myProfitService.getQRCodeList(scanEntity);
        if (logger.isInfoEnabled()) {
            logger.info("返回参数{}",rs);
        }
        return rs;
    }

    /**
     * 快捷分润
     * @param qrpyEntity
     * @return
     */
    @RequestMapping("/getQuickPayList")
    public RespEntity getQuickPayList(@RequestBody UnnoQrpyEntity qrpyEntity){
        if (logger.isInfoEnabled()) {
            logger.info("快捷分润==>参数{}",qrpyEntity);
        }
        RespEntity rs = myProfitService.getQuickPayList(qrpyEntity);
        if (logger.isInfoEnabled()) {
            logger.info("返回参数{}",rs);
        }
        return rs;
    }

    /**
     * 传统POS分润
     * @param posEntity
     * @return
     */
    @RequestMapping("/getTraditionPosList")
    public RespEntity getTraditionPosList(@RequestBody UnnoPosEntity posEntity){
        if (logger.isInfoEnabled()) {
            logger.info("传统分润==>参数{}",posEntity);
        }
        RespEntity rs = myProfitService.getTraditionPosList(posEntity);
        if (logger.isInfoEnabled()) {
            logger.info("返回参数{}",rs);
        }
        return rs;
    }

    /**
     * 秒到分润
     * @param mdaoEntity
     * @return
     */
    @RequestMapping("/getMiaoDaoList")
    public RespEntity getMiaoDaoList(@RequestBody UnnoMdaoEntity mdaoEntity){
        if (logger.isInfoEnabled()) {
            logger.info("秒到分润==>参数{}",mdaoEntity);
        }
        RespEntity rs = myProfitService.getMiaoDaoList(mdaoEntity);
        if (logger.isInfoEnabled()) {
            logger.info("返回参数{}",rs);
        }
        return rs;
    }

	/**
	 * 收银台分润
	 * @param mdaoEntity
	 * @return RespEntity
	 */
	@RequestMapping("/getSytList")
	public RespEntity getSytList(@RequestBody UnnoMdaoEntity mdaoEntity){
		if (logger.isInfoEnabled()) {
			logger.info("收银台分润==>参数{}",mdaoEntity);
		}
		RespEntity rs = myProfitService.getSytList(mdaoEntity);
		if (logger.isInfoEnabled()) {
			logger.info("返回参数{}",rs);
		}
		return rs;
	}

    /**
     * 首页获取昨日分润
     * @param txnEntity
     * @return
     */
    @RequestMapping("/getYesterdayFenrun")
    public RespEntity getYesterdayFenrun(@RequestBody UnnoTxnFenRunEntity txnEntity){
        if (logger.isInfoEnabled()) {
            logger.info("首页昨日商户分润==>参数{}",txnEntity);
        }
        RespEntity rs = myProfitService.getYesterdayFenrun(txnEntity);
        if (logger.isInfoEnabled()) {
            logger.info("返回参数,{}",rs);
        }
        return rs;
    }

    /**
     * 首页获取返现
     * @param cashEntity
     * @return
     */
    @RequestMapping("/getCashAmtList")
    public RespEntity getCashAmtList(@RequestBody UnnoCashBackEntity cashEntity){
        if (logger.isInfoEnabled()) {
            logger.info("首页返现==>参数{}",cashEntity);
        }
        RespEntity rs = myProfitService.getCashAmtList(cashEntity);
        if (logger.isInfoEnabled()) {
            logger.info("返回参数,{}",rs);
        }
        return rs;
    }

    /**
     * 首页获取商户数据
     * @param merEntity
     * @return
     */
    @RequestMapping("/getYesterDayMerList")
    public RespEntity getYesterDayMerList(@RequestBody UnnoMerInfoEntity merEntity){
        if (logger.isInfoEnabled()) {
            logger.info("首页商户数据==>参数{}",merEntity);
        }
        RespEntity rs = myProfitService.getYesterDayMerList(merEntity);
        if (logger.isInfoEnabled()) {
            logger.info("返回参数,{}",rs);
        }
        return rs;
    }
    /**
     * 首页获取分润计算日期
     * @param merEntity
     * @return
     */
    @RequestMapping("/getProfitDate")
    public RespEntity getProfitDate(){
    	logger.info("获取分润跑入日期");
        RespEntity rs = myProfitService.getProfitDate();
        if (logger.isInfoEnabled()) {
            logger.info("返回参数,{}",rs);
        }
        return rs;
    }
}
