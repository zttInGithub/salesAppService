package com.hrtp.salesAppService.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hrtp.salesAppService.entity.appEntity.MerchManagerEntity;
import com.hrtp.salesAppService.entity.appEntity.MerchantUpdateTerEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.service.MachineInfoService;
import com.hrtp.salesAppService.service.MerchantUpdateTerService;
import com.hrtp.system.util.JsonUtils;

/**
 * <p>撤换机Controller</p>
 * @date 2018年8月24日
 */
@RestController
@RequestMapping("/app/merchantUpdateTer")
public class MerchantUpdateTerController {
	
	 private static Logger logger = LoggerFactory.getLogger(MerchantUpdateTerController.class);

	    @Autowired
	    private MerchantUpdateTerService merchantUpdateTerService;

	    /**
	     * @提交撤换机申请
	     * @param MerchantUpdateTerEntity
	     * @return RespEntity
	     * @throws Exception 
	     */
	    @RequestMapping("/saveMerchantUpdateTer")
	    public RespEntity findMachineInfo(HttpServletRequest request) throws Exception{
	    	MerchantUpdateTerEntity mute = JsonUtils.mulTipartRequest2BeanX(request,
	    			MerchantUpdateTerEntity.class);
	    	if (logger.isInfoEnabled()) {
	            logger.info("提交撤换机申请返回参数{}",mute);
	        }
	    	RespEntity rs = merchantUpdateTerService.saveMerchantUpdateTer(mute);
	        if (logger.isInfoEnabled()) {
	            logger.info("提交撤换机申请返回参数{}",rs);
	        }
	        return rs;
	    }
	    
	    /**
	     * @提交撤机申请
	     * @param MerchantUpdateTerEntity
	     * @return RespEntity
	     * @throws Exception 
	     */
	    @RequestMapping("/saveMerchantUpdateTer1")
	    public RespEntity findMachineInfo1(@RequestBody MerchantUpdateTerEntity mute) throws Exception{
	    	if (logger.isInfoEnabled()) {
	            logger.info("提交撤换机申请返回参数{}",mute);
	        }
	    	RespEntity rs = merchantUpdateTerService.saveMerchantUpdateTer(mute);
	        if (logger.isInfoEnabled()) {
	            logger.info("提交撤换机申请返回参数{}",rs);
	        }
	        return rs;
	    }
}
