package com.hrtp.salesAppService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hrtp.salesAppService.dao.MerchManagerRepository;
import com.hrtp.salesAppService.dao.TerminalInfoRepository;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.appEntity.TerminalInfoEntity;
import com.hrtp.salesAppService.entity.ormEntity.TerminalInfoModel;
import com.hrtp.system.costant.ReturnCode;

@Service
public class TerminalInfoService {
	@Autowired
	private TerminalInfoRepository terminalInfoRepository;
	 @Autowired
	 private MerchManagerRepository merchManagerRepository;
	
	
	public RespEntity findTerminalInfo(TerminalInfoEntity terminalInfoEntity) {
		//先查询当前商户所属的机构
		String unno = merchManagerRepository.findUnnoByMid(terminalInfoEntity.getMid());
		List<TerminalInfoModel> list = terminalInfoRepository.findTerminalInfo(unno);
		return new RespEntity(ReturnCode.SUCCESS, "查询成功", list);
	}
	
	public RespEntity findTerminalInfoByUnno(TerminalInfoEntity terminalInfoEntity) {
		List<TerminalInfoModel> list = terminalInfoRepository.findTerminalInfo(terminalInfoEntity.getUnNO());
		return new RespEntity(ReturnCode.SUCCESS, "查询成功", list);
	}
}
