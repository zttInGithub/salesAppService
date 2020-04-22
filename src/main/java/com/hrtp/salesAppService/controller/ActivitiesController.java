package com.hrtp.salesAppService.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.appEntity.WalletEntity;
import com.hrtp.salesAppService.service.ActivitiesService;
import com.hrtp.system.costant.ReturnCode;
/**
 * 关于活动类型拆分
 * @author YQ
 */
@RestController
@RequestMapping("/app/activities")
public class ActivitiesController {
	
	private static final Logger log = LoggerFactory.getLogger(ActivitiesController.class);

	@Autowired
	private ActivitiesService activitiesService;

	/**
	 * 查询app展示所有活动类型
	 */
	@RequestMapping("/getActivities")
	@ResponseBody
	public RespEntity getHomePage(@RequestBody WalletEntity wallet){
		if (log.isInfoEnabled()) {
			log.info("查询app展示所有活动类型==>请求参数{}",wallet);
		}
		List<Map<String, String>> resultList = activitiesService.getActivities(wallet);
		RespEntity rs = new RespEntity();
		rs.setReturnBody(resultList);
		rs.setReturnCode(ReturnCode.SUCCESS);
		rs.setReturnMsg("查询成功！");
		if (log.isInfoEnabled()) {
			log.info("查询app展示所有活动类型==>返回参数{}",rs);
		}
		return rs;
	}
}
