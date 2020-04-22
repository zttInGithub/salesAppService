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
import com.hrtp.salesAppService.entity.appEntity.MerchantInfoEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.service.MerchManagerService;
import com.hrtp.system.annotation.FileCheck;
import com.hrtp.system.util.JsonUtils;

/**
 * <p>商户入网管理Controller</p>
 * @date 2018年8月24日
 */
@RestController
@RequestMapping("/app/merchManager")
public class MerchManagerController {
	
	 private static Logger logger = LoggerFactory.getLogger(MerchManagerController.class);

	    @Autowired
	    private MerchManagerService merchManagerService;
	    
	    /**
		 * <p>查询行业编码 </p>
		 * @author zhq
		 */
	    @RequestMapping("/searchMCC")
	    @ResponseBody
	    public RespEntity searchMCC(){
	        RespEntity rs = merchManagerService.searchMCC();
	        if (logger.isInfoEnabled()) {
	            logger.info("查询行业编码返回参数{}",rs);
	        }
	        return rs;
	    }
	    /**
		 * <p>查询销售 </p>
		 * @author zhq
		 */
	    @RequestMapping("/searchAgentSales")
	    @ResponseBody
	    public RespEntity searchAgentSales(@RequestBody MerchManagerEntity merchManagerEntity){
	    	RespEntity rs = merchManagerService.searchAgentSales(merchManagerEntity);
	    	if (logger.isInfoEnabled()) {
	    		logger.info("查询销售返回参数{}",rs);
	    	}
	    	return rs;
	    }
	    
	    /**
	     * <p>查询市级code</p>
	     * @author zhq
	     * @RequestParam GET方式接参
	     * @RequestBody POST方式接参 将参数封装在对象中
	     */
	    @RequestMapping("/searchAreaCode")
	    @ResponseBody
	    public RespEntity searchAreaCode(@RequestBody MerchManagerEntity merchManagerEntity){
	    	RespEntity rs = merchManagerService.searchAreaCode(merchManagerEntity);
	    	if (logger.isInfoEnabled()) {
	    		logger.info("查询市级code返回参数{}",rs);
	    	}
	    	return rs;
	    }
	    
	    /**
	     * 商户入网签约
	     * @author zhq
	     */
	    @FileCheck
	    @ResponseBody
	    @RequestMapping("/microMerch")
	    public RespEntity microMerch(HttpServletRequest request) throws Exception{
	    	MerchManagerEntity merchManagerEntity = JsonUtils.mulTipartRequest2BeanX(request,
	    			MerchManagerEntity.class);
	        if (logger.isInfoEnabled()) {
	            logger.info("商户入网签约请求参数{}",merchManagerEntity);
	        }
	        RespEntity rs = merchManagerService.update(merchManagerEntity);
	        if (logger.isInfoEnabled()) {
	            logger.info("商户入网签约返回参数{}",rs);
	        }
	        return rs;
	    }
	    
	    /**
	     * 查询商户信息
	     * @param repayEntity
	     * @return RespEntity
	     */
	    @RequestMapping("/findMerchantInfo")
	    @ResponseBody
	    public RespEntity findMerchantInfo(@RequestBody MerchantInfoEntity repayEntity){
	    	 if (logger.isInfoEnabled()) {
	    		 logger.info("查询商户信息请求参数{}",repayEntity);
	    	 }
	    	 RespEntity rs = merchManagerService.findMerchantInfo(repayEntity);
	    	 if (logger.isInfoEnabled()) {
	    		 logger.info("查询商户信息返回参数{}",rs);
	    	 }
	    	 return rs;
	    }
	    
	    /**
	     * 根据MID查询商户信息
	     * @param repayEntity
	     * @return RespEntity
	     */
	    @RequestMapping("/findMerByMid")
	    @ResponseBody
	    public RespEntity findMerByMid(@RequestBody MerchManagerEntity repayEntity){
	    	 if (logger.isInfoEnabled()) {
	    		 logger.info("根据MID查询商户信息请求参数{}",repayEntity);
	    	 }
	    	 RespEntity rs = merchManagerService.findMerByMid(repayEntity);
	    	 if (logger.isInfoEnabled()) {
	    		 logger.info("根据MID查询商户信息返回参数{}",rs);
	    	 }
	    	 return rs;
	    }
}
