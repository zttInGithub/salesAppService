package com.hrtp.salesAppService.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hrtp.salesAppService.entity.appEntity.BackOrderEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.service.BackOrderService;
import com.hrtp.system.annotation.FileCheck;
import com.hrtp.system.util.JsonUtils;

@Controller
@RequestMapping("/app/backOrder")
public class BackOrderController {
	
	private static Logger logger = LoggerFactory.getLogger(BackOrderController.class);
	
	@Autowired
	private BackOrderService backOrderService;
	
	/**
     * 差错管理--------退单列表
     * @param terEntity		type:0未回复，1已回复/过期
     * @return
     */
	@ResponseBody
    @RequestMapping("/getBOrderList")
    public RespEntity getBOrderList(@RequestBody BackOrderEntity backOrderEntity){
        if (logger.isInfoEnabled()) {
            logger.info("退单列表--->收到请求,参数{}",backOrderEntity);
        }
        RespEntity rs = backOrderService.getBOrderList(backOrderEntity);
        if (logger.isInfoEnabled()) {
            logger.info("返回参数{}",rs);
        }
        return rs;
	}
	/**
     * 差错管理--------退单回复
     * @param backOrderEntity    
     * @return
     */
	@FileCheck
	@ResponseBody
    @RequestMapping("/updateBackOrderInfo")
	public RespEntity updateBackOrderInfo(HttpServletRequest request) throws Exception{
		BackOrderEntity backOrderEntity = JsonUtils.mulTipartRequest2BeanX(request,BackOrderEntity.class);
		 if (logger.isInfoEnabled()) {
	            logger.info("查询/调单回复--->收到请求,参数{}",backOrderEntity);
	        }
	        RespEntity rs = backOrderService.updateBackOrderInfo(backOrderEntity);
	        if (logger.isInfoEnabled()) {
	            logger.info("返回参数{}",rs);
	        }
	        return rs;
	}
}
