package com.hrtp.salesAppService.dao;


import com.hrtp.salesAppService.entity.ormEntity.MachineInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MachineInfoRepository extends JpaRepository<MachineInfoModel,Integer>{
	@Query("select new MachineInfoModel(bmaID,machineModel,machineType) from MachineInfoModel m where m" +
			".machineType<>?1")
	List<MachineInfoModel> findMachineInfo(String machineType);
}
