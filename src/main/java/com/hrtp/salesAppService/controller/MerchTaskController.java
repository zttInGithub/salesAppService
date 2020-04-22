package com.hrtp.salesAppService.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hrtp.salesAppService.entity.appEntity.MerchManagerEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.service.MerchTaskService;
import com.hrtp.system.annotation.FileCheck;
import com.hrtp.system.util.JsonUtils;

/**
 * <p>工单申请Controller</p>
 * @date 2018年8月28日
 */
@RestController
@RequestMapping("/app/merchTask")
public class MerchTaskController {
	
	 private static Logger logger = LoggerFactory.getLogger(MerchTaskController.class);

	    @Autowired
	    private MerchTaskService merchTaskService;
	    
	    /**
	     * 基本信息变更根据mid查询
	     * @author zhq
	     */
	    @RequestMapping("/getmerInfo")
	    @ResponseBody
	    public RespEntity getmerInfo(@RequestBody MerchManagerEntity merchManagerEntity){
	        if (logger.isInfoEnabled()) {
	            logger.info("基本信息变更查询mid请求参数{}",merchManagerEntity);
	        }
	        RespEntity rs = merchTaskService.getmerInfo(merchManagerEntity);
	        if (logger.isInfoEnabled()) {
	            logger.info("基本信息变更查询mid返回参数{}",rs);
	        }
	        return rs;
	    }
	    /**
	     * 账户信息变更根据mid查询
	     * @author zhq
	     */
	    @RequestMapping("/getmerAccInfo")
	    @ResponseBody
	    public RespEntity getmerAccInfo(@RequestBody MerchManagerEntity merchManagerEntity){
	    	if (logger.isInfoEnabled()) {
	    		logger.info("账户信息变更查询mid请求参数{}",merchManagerEntity);
	    	}
	    	RespEntity rs = merchTaskService.getmerAccInfo(merchManagerEntity);
	    	if (logger.isInfoEnabled()) {
	    		logger.info("账户信息变更查询mid返回参数{}",rs);
	    	}
	    	return rs;
	    }
	    /**
	     * 费率信息变更根据mid查询
	     * @author zhq
	     */
	    @RequestMapping("/getmerRateInfo")
	    @ResponseBody
	    public RespEntity getmerRateInfo(@RequestBody MerchManagerEntity merchManagerEntity){
	    	if (logger.isInfoEnabled()) {
	    		logger.info("费率信息变更查询mid请求参数{}",merchManagerEntity);
	    	}
	    	RespEntity rs = merchTaskService.getmerRateInfo(merchManagerEntity);
	    	if (logger.isInfoEnabled()) {
	    		logger.info("费率信息变更查询mid返回参数{}",rs);
	    	}
	    	return rs;
	    }
	    
	    /**
	     * 费率信息变更申请
	     * @author zhq
	     */
	    @RequestMapping("/upmerRateInfo")
	    @ResponseBody
	    public RespEntity upmerRateInfo(@RequestBody MerchManagerEntity merchManagerEntity){
	    	if (logger.isInfoEnabled()) {
	    		logger.info("费率信息变更申请请求参数{}",merchManagerEntity);
	    	}
	    	RespEntity rs = merchTaskService.upmerRateInfo(merchManagerEntity);
	    	if (logger.isInfoEnabled()) {
	    		logger.info("费率信息变更申请返回参数{}",rs);
	    	}
	    	return rs;
	    }
	    
	    /**
	     * @基本信息变更申请
	     * @throws Exception 
	     */
	    @FileCheck
	    @RequestMapping("/upmerBasicInfo")
	    @ResponseBody
	    public RespEntity upmerBasicInfo(HttpServletRequest request) throws Exception{
	    	MerchManagerEntity merchManagerEntity = JsonUtils.mulTipartRequest2BeanX(request,
	    			MerchManagerEntity.class);
	    	if (logger.isInfoEnabled()) {
	    		logger.info("基本信息变更申请请求参数{}",merchManagerEntity);
	    	}
	    	RespEntity rs = merchTaskService.upmerBasicInfo(merchManagerEntity);
	    	if (logger.isInfoEnabled()) {
	    		logger.info("基本信息变更申请返回参数{}",rs);
	    	}
	    	return rs;
	    }
	    
	    /**
	     * @账户信息变更申请
	     * @throws Exception 
	     */
	    @FileCheck
	    @RequestMapping("/upmerAccountInfo")
	    @ResponseBody
	    public RespEntity upmerAcountInfo(HttpServletRequest request) throws Exception{
	    	MerchManagerEntity merchManagerEntity = JsonUtils.mulTipartRequest2BeanX(request,
	    			MerchManagerEntity.class);
	    	if (logger.isInfoEnabled()) {
	    		logger.info("基本信息变更申请请求参数{}",merchManagerEntity);
	    	}
	    	RespEntity rs = merchTaskService.upmerAcountInfo(merchManagerEntity);
	    	if (logger.isInfoEnabled()) {
	    		logger.info("基本信息变更申请返回参数{}",rs);
	    	}
	    	return rs;
	    }
	    
	    /**
	     * 查询sn交易量
	     * @author zhq
	     */
	    @RequestMapping("/querySnSumsamt")
	    @ResponseBody
	    public RespEntity querySnSumsamt(@RequestBody MerchManagerEntity merchManagerEntity){
	    	if (logger.isInfoEnabled()) {
	    		logger.info("查询sn交易量请求参数{}",merchManagerEntity);
	    	}
	    	RespEntity rs = merchTaskService.querySnSumsamt(merchManagerEntity);
	    	if (logger.isInfoEnabled()) {
	    		logger.info("查询sn交易量返回参数{}",rs);
	    	}
	    	return rs;
	    }
}
