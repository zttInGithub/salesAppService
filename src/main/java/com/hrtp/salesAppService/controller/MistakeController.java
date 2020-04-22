package com.hrtp.salesAppService.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hrtp.salesAppService.entity.appEntity.MistakeEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.service.MistakeService;
import com.hrtp.system.annotation.FileCheck;
import com.hrtp.system.util.JsonUtils;

@Controller
@RequestMapping("/app/mistake")
public class MistakeController {

	private static Logger logger = LoggerFactory.getLogger(MistakeController.class);

	@Autowired
	private MistakeService mistakeService;

	/**
	 * 差错管理--------查询/调单列表/反欺诈
	 * @param terEntity    orderType:3查询；2调单;@YQ 202004新增4反欺诈   type:0未回复，1已回复/过期
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getQueryList")
	public RespEntity getQueryList(@RequestBody MistakeEntity mistakeEntity){
		if (logger.isInfoEnabled()) {
			logger.info("查询/调单列表--->收到请求,参数{}",mistakeEntity);
		}
		RespEntity rs = mistakeService.getQueryList(mistakeEntity);
		if (logger.isInfoEnabled()) {
			logger.info("返回参数{}",rs);
		}
		return rs;
	}
	/**
	 * 差错管理--------查询回复
	 * @param terEntity    orderType:3查询；2调单
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateMistake3Info")
	public RespEntity updateMistake3Info(@RequestBody MistakeEntity mistakeEntity){
		if (logger.isInfoEnabled()) {
			logger.info("查询回复--->收到请求,参数{}",mistakeEntity);
		}
		RespEntity rs = mistakeService.updateMistake3Info(mistakeEntity);
		if (logger.isInfoEnabled()) {
			logger.info("返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 差错管理--------调单回复/欺诈交易回复
	 * @param terEntity    orderType:3查询；2调单;4欺诈
	 * @return
	 */
	@FileCheck
	@ResponseBody
	@RequestMapping("/updateMistake2Info")
	public RespEntity updateMistake2Info(HttpServletRequest request) throws Exception{
		MistakeEntity mistakeEntity = JsonUtils.mulTipartRequest2BeanX(request,MistakeEntity.class);
		if (logger.isInfoEnabled()) {
			logger.info("调单回复--->收到请求,参数{}",mistakeEntity.toString());
		}
		RespEntity rs = mistakeService.updateMistake2Info(mistakeEntity);
		if (logger.isInfoEnabled()) {
			logger.info("返回参数{}",rs);
		}
		return rs;
	}

}
