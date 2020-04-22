package com.hrtp.salesAppService.controller;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hrtp.salesAppService.entity.appEntity.MerchantTwoUploadEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.service.MerchantTwoUploadService;
import com.hrtp.system.annotation.FileCheck;
import com.hrtp.system.util.JsonUtils;

/**
 * <p>资料二次上传Controller</p>
 * @date 2018年8月24日
 */
@RestController
@RequestMapping("/app/merTwoUpload")
public class MerchantTwoUploadController {
	
	 private static Logger logger = LoggerFactory.getLogger(MerchantTwoUploadController.class);

	    @Autowired
	    private MerchantTwoUploadService merchantTwoUploadService;

	    /**
	     * 资料二次上传
	     * @param mtue
	     * @return RespEntity
	     */
	    @FileCheck
	    @ResponseBody
	    @RequestMapping("/saveMerTwoUpload")
	    public RespEntity saveMerTwoUpload(HttpServletRequest request) throws Exception {
	    	MerchantTwoUploadEntity mtEntity = JsonUtils.mulTipartRequest2BeanX(request,
	    			MerchantTwoUploadEntity.class);
	        if (logger.isInfoEnabled()) {
	            logger.info("接收请求，参数{}",mtEntity);
	        }
	    	RespEntity rs = merchantTwoUploadService.saveMerchantTwoUploadInfo(mtEntity);
	        if (logger.isInfoEnabled()) {
	            logger.info("资料二次上传返回参数{}",rs);
	        }
	        return rs;
	    }
}
