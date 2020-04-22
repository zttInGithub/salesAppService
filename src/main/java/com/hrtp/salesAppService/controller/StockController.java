package com.hrtp.salesAppService.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.appEntity.StockEntity;
import com.hrtp.salesAppService.service.StockService;

@Controller
@RequestMapping("/app/stock")
public class StockController {
	
	private static Logger logger = LoggerFactory.getLogger(StockController.class);
	
	@Autowired
	private StockService stockService;
	
	/**
     * 库存管理
     * @param stockEntity
     * @return
     */
	@ResponseBody
    @RequestMapping("/getStockInfo")
    public RespEntity getStockInfo(@RequestBody StockEntity stockEntity){
        if (logger.isInfoEnabled()) {
            logger.info("库存管理--->收到请求,参数{}",stockEntity);
        }
        RespEntity rs = stockService.getStockInfo(stockEntity);
        if (logger.isInfoEnabled()) {
            logger.info("返回参数{}",rs);
        }
        return rs;
    }
}
