package com.hrtp.salesAppService.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hrtp.salesAppService.entity.appEntity.BillAgentInfoEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.appEntity.TerminalInfoEntity;
import com.hrtp.salesAppService.service.MachineInfoService;
import com.hrtp.salesAppService.service.TerminalInfoService;

/**
 * <p>设备信息Controller</p>
 * @date 2018年8月24日
 */
@RestController
@RequestMapping("/app/terminalInfo")
public class TerminalInfoController {
	
	 private static Logger logger = LoggerFactory.getLogger(TerminalInfoController.class);

	    @Autowired
	    private TerminalInfoService terminalInfoService;

	    /**
	     * 使用mid查询设备信息
	     * @param TerminalInfoEntity
	     * @return RespEntity
	     */
	    @RequestMapping("/findTerminalInfo")
	    public RespEntity findTerminalInfo(@RequestBody TerminalInfoEntity terminalInfoEntity){
	    	if (logger.isInfoEnabled()) {
	            logger.info("查询设备信息请求参数{}",terminalInfoEntity);
	        }
	    	RespEntity rs = terminalInfoService.findTerminalInfo(terminalInfoEntity);
	        if (logger.isInfoEnabled()) {
	            logger.info("查询设备信息返回参数{}",rs);
	        }
	        return rs;
	    }
	    
	    /**
	     *  使用机构号查询设备信息
	     * @param TerminalInfoEntity
	     * @return RespEntity
	     */
	    @RequestMapping("/findTerminalInfoByUnno")
	    public RespEntity findTerminalInfoByUnno(@RequestBody TerminalInfoEntity terminalInfoEntity){
	    	if (logger.isInfoEnabled()) {
	            logger.info("查询设备信息请求参数{}",terminalInfoEntity.getUnNO());
	        }
	    	RespEntity rs = terminalInfoService.findTerminalInfoByUnno(terminalInfoEntity);
	        if (logger.isInfoEnabled()) {
	            logger.info("查询设备信息返回参数{}",rs);
	        }
	        return rs;
	    }
}
