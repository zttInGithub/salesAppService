package com.hrtp.salesAppService.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.service.MachineInfoService;

/**
 * <p>设备型号Controller</p>
 * @date 2018年8月24日
 */
@RestController
@RequestMapping("/app/machineInfo")
public class MachineInfoController {
	
	 private static Logger logger = LoggerFactory.getLogger(MachineInfoController.class);

	    @Autowired
	    private MachineInfoService machineInfoService;

	    /**
	     * 查询设备型号
	     * @param null
	     * @return RespEntity
	     */
	    @RequestMapping("/findMachineInfo")
	    public RespEntity findMachineInfo(){
	    	RespEntity rs = machineInfoService.findMachineInfo();
	        if (logger.isInfoEnabled()) {
	            logger.info("查询设备型号返回参数{}",rs);
	        }
	        return rs;
	    }
}
