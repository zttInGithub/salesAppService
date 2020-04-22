package com.hrtp.salesAppService.controller;

import com.hrtp.salesAppService.entity.appEntity.ReqTxnDetailDataEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.appEntity.TxnFenRunEntity;
import com.hrtp.salesAppService.service.YesterDayDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * YesterDayDataController
 * description 昨日数据汇总
 * create by lxj 2018/8/23
 **/
@RestController
@RequestMapping("/app/yDayTxnData")
public class YesterDayDataController {
    private static final Logger logger = LoggerFactory.getLogger(YesterDayDataController.class);
    @Autowired
    private YesterDayDataService yesterDayDataService;
    @RequestMapping("/getTxnTotalData")
    @ResponseBody
    public RespEntity getTxnTotalData(@RequestBody TxnFenRunEntity txnFenRunEntity){
        if (logger.isInfoEnabled()) {
            logger.info("交易汇总==>参数{}",txnFenRunEntity);
        }
        RespEntity rs = yesterDayDataService.getTxnTotalData(txnFenRunEntity);
        if (logger.isInfoEnabled()) {
            logger.info("返回参数{}",rs);
        }
        return rs;
    }

    @RequestMapping("/getTxnDetailData")
    @ResponseBody
    public RespEntity getTxnDetailData(@RequestBody ReqTxnDetailDataEntity txnDetailDataEntity){
        if (logger.isInfoEnabled()) {
            logger.info("商户交易明细==>参数{}",txnDetailDataEntity);
        }
        RespEntity rs = yesterDayDataService.getTxnDetailData(txnDetailDataEntity);
        if (logger.isInfoEnabled()) {
            logger.info("返回参数{}",rs);
        }
        return rs;
    }
}
