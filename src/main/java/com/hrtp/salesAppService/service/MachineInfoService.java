package com.hrtp.salesAppService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hrtp.salesAppService.dao.MachineInfoRepository;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.ormEntity.MachineInfoModel;
import com.hrtp.system.costant.ReturnCode;

@Service
public class MachineInfoService {
	@Autowired
	private MachineInfoRepository machineInfoRepository;
	
	
	public RespEntity findMachineInfo() {
		List<MachineInfoModel> list = machineInfoRepository.findMachineInfo("4");
		return new RespEntity(ReturnCode.SUCCESS, "查询成功", list);
	}
	
}
