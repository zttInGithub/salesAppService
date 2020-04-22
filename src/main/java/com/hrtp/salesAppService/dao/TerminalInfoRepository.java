package com.hrtp.salesAppService.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hrtp.salesAppService.entity.ormEntity.MachineInfoModel;
import com.hrtp.salesAppService.entity.ormEntity.TerminalInfoModel;


@Repository
public interface TerminalInfoRepository extends JpaRepository<TerminalInfoModel,Integer>{
	@Query("select new TerminalInfoModel(btID,termID,keyType) from TerminalInfoModel t WHERE t.unno = ?1 and t.allotConfirmDate is not null and t.usedConfirmDate is null and maintainType != 'D' and ism35 !=1 ")
	List<TerminalInfoModel> findTerminalInfo(String unno);
	
	@Query("select t from TerminalInfoModel t WHERE t.termID=?1 ")
	TerminalInfoModel findTerminalInfoByTermID(String termID);
}
