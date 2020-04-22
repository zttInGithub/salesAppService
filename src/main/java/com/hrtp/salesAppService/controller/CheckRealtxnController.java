package com.hrtp.salesAppService.controller;

import com.hrtp.salesAppService.entity.appEntity.CheckRealtxnEntity;
import com.hrtp.salesAppService.entity.appEntity.ReqTxnDetailDataEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.appEntity.TxnFenRunEntity;
import com.hrtp.salesAppService.service.CheckRealtxnService;
import com.hrtp.salesAppService.service.YesterDayDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/checkRealtxn")
public class CheckRealtxnController {
	
    private static final Logger logger = LoggerFactory.getLogger(CheckRealtxnController.class);
    
    @Autowired
    private CheckRealtxnService checkRealtxnService;
    
    @RequestMapping("/findRealtxn")
    @ResponseBody
    public RespEntity findRealtxn(@RequestBody CheckRealtxnEntity checkRealtxnEntity){
        if (logger.isInfoEnabled()) {
            logger.info("实时交易查询参数{}",checkRealtxnEntity);
        }
        RespEntity rs = checkRealtxnService.findRealtxn(checkRealtxnEntity);
        if (logger.isInfoEnabled()) {
            logger.info("返回参数{}",rs);
        }
        return rs;
    }
}
