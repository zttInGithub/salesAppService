package com.hrtp.salesAppService.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hrtp.salesAppService.entity.appEntity.MerchantTerminalInfoEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.service.MerchantTerminalInfoService;

/**
 * <p>商户设备中间信息Controller</p>
 * @date 2018年8月24日
 */
@RestController
@RequestMapping("/app/merTerInfo")
public class MerchantTerminalInfoController {
	
	 private static Logger logger = LoggerFactory.getLogger(MerchantTerminalInfoController.class);

	    @Autowired
	    private MerchantTerminalInfoService merchantTerminalInfoService;

	    /**
	     * 增机申请/挂终端
	     * @param TerminalInfoEntity
	     * @return RespEntity
	     */
	    @RequestMapping("/saveMerTerInfo")
	    public RespEntity saveMerTerInfo(@RequestBody MerchantTerminalInfoEntity mtie){
	    	if (logger.isInfoEnabled()) {
	            logger.info("增机申请/挂终端请求参数{}",mtie);
	        }
	    	RespEntity rs = merchantTerminalInfoService.saveMerTerInfo(mtie);
	        if (logger.isInfoEnabled()) {
	            logger.info("增机申/挂终端请返回参数{}",rs);
	        }
	        return rs;
	    }
	    
	    /**
	     * 根据mid查询已绑定终端
	     * @param TerminalInfoEntity
	     * @return RespEntity
	     */
	    @RequestMapping("/findMerTerInfo")
	    public RespEntity findTerInfo(@RequestBody MerchantTerminalInfoEntity mtie){
	    	if (logger.isInfoEnabled()) {
	            logger.info("根据mid查询已绑定终端请求参数{}",mtie);
	        }
	    	RespEntity rs = merchantTerminalInfoService.findTerInfo(mtie);
	        if (logger.isInfoEnabled()) {
	            logger.info("根据mid查询已绑定终端返回参数{}",rs);
	        }
	        return rs;
	    }
}
