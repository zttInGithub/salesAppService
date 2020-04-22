package com.hrtp.salesAppService.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hrtp.salesAppService.controller.v2.BaseController;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.appEntity.SaleEntity;
import com.hrtp.salesAppService.service.SalesService;
import com.hrtp.system.costant.ReturnCode;

@RestController
@RequestMapping("/app/sales")
public class SalesController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(SalesController.class);

	@Autowired
	private SalesService salesService;

	//销售查询
	@RequestMapping("/queryRegister")
	@ResponseBody
	public RespEntity queryRegister(@RequestBody SaleEntity reqsBody) {
		logger.info("收到请求=====参数：{}", reqsBody);
		if(checkParamsHashNull(reqsBody.getBusId())){
			return new RespEntity(ReturnCode.FALT,"缺少请求参数");
		}
		RespEntity rs = salesService.queryRegister(reqsBody.getBusId());
		logger.info("返回参数{}", rs);
		return rs;
	}

	//注册、激活查询(包含单独查询和汇总查询)
	@RequestMapping("/queryUsedOrActive")
	@ResponseBody
	public RespEntity queryUsedOrActive(@RequestBody SaleEntity reqsBody) {
		logger.info("收到请求=====参数：{}", reqsBody);
		if(checkParamsHashNull(reqsBody.getFlag(),reqsBody.getPage(),reqsBody.getBusId())){
			return new RespEntity(ReturnCode.FALT,"缺少请求参数");
		}
		if(reqsBody.getBusId1()==null && reqsBody.getIsAll().equals("false")){
			return new RespEntity(ReturnCode.FALT,"缺少请求参数");
		}
		RespEntity rs = salesService.queryUsedOrActive(reqsBody);
		logger.info("返回参数{}", rs);
		return rs;
	}

	//返回不同产品对应的活动类型
	@RequestMapping("/queryProductAndRebateType")
	@ResponseBody
	public RespEntity queryProductAndRebateType(@RequestBody SaleEntity reqsBody) {
		if(checkParamsHashNull(reqsBody.getBusId())){
			return new RespEntity(ReturnCode.FALT,"缺少请求参数");
		}
		logger.info("收到请求=====参数：{}", reqsBody);
		RespEntity rs = salesService.queryProductAndRebateType(reqsBody);
		logger.info("返回参数{}", rs);
		return rs;
	}

	//返回交易类型
	@RequestMapping("/queryProduct")
	@ResponseBody
	public RespEntity queryProduct(@RequestBody SaleEntity reqsBody) {
		if(checkParamsHashNull(reqsBody.getBusId())){
			return new RespEntity(ReturnCode.FALT,"缺少请求参数");
		}
		logger.info("收到请求=====参数：{}", reqsBody);
		RespEntity rs = salesService.queryProduct(reqsBody);
		logger.info("返回参数{}", rs);
		return rs;
	}

	//联系方式
	@RequestMapping("/queryUnnoInfo")
	@ResponseBody
	public RespEntity queryUnnoInfo(@RequestBody SaleEntity reqsBody) {
		if(checkParamsHashNull(reqsBody.getBusId(),reqsBody.getUnno())){
			return new RespEntity(ReturnCode.FALT,"缺少请求参数");
		}
		logger.info("收到请求=====参数：{}", reqsBody);
		RespEntity rs = salesService.queryUnnoInfo(reqsBody);
		logger.info("返回参数{}", rs);
		return rs;
	}
	
}
