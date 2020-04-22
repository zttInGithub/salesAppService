package com.hrtp.salesAppService.dao;


import com.hrtp.salesAppService.entity.ormEntity.MerchantTerminalInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MerchantTerminalInfoRepository extends JpaRepository<MerchantTerminalInfoModel,Integer>{
	@Query("select t from MerchantTerminalInfoModel t where t.mid=?1 and t.approveStatus='Y' and t.maintainType<>'D'")
	List<MerchantTerminalInfoModel> findTerInfo(String mid);

	@Query("select t from MerchantTerminalInfoModel t where t.sn=?1 and t.maintainType <> 'D'")
	MerchantTerminalInfoModel findBySnAndMaintainType(String sn);
}
