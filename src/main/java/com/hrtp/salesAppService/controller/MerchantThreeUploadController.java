package com.hrtp.salesAppService.controller;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hrtp.salesAppService.entity.appEntity.MerchantThreeUploadEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.service.MerchantThreeUploadService;
import com.hrtp.system.util.JsonUtils;

/**
 * <p>云闪付资料上传Controller</p>
 * @date 2018年8月29日
 * sl
 */
@RestController
@RequestMapping("/app/merThreeUpload")
public class MerchantThreeUploadController {
	
	 private static Logger logger = LoggerFactory.getLogger(MerchantThreeUploadController.class);

	    @Autowired
	    private MerchantThreeUploadService merchantThreeUploadService;

	    /**
	     * 云闪付资料上传
	     * @param mtue
	     * @return RespEntity
	     */
	    @ResponseBody
	    @RequestMapping("/saveMerThreeUpload")
	    public RespEntity saveMerTwoUpload(HttpServletRequest request) throws Exception {
	    	MerchantThreeUploadEntity mtEntity = JsonUtils.mulTipartRequest2BeanX(request,
	    			MerchantThreeUploadEntity.class);
	        if (logger.isInfoEnabled()) {
	            logger.info("接收请求，参数{}",mtEntity);
	        }
	    	RespEntity rs = merchantThreeUploadService.saveMerchantThreeUploadInfo(mtEntity);
	        if (logger.isInfoEnabled()) {
	            logger.info("云闪付资料上传返回参数{}",rs);
	        }
	        return rs;
	    }
}
