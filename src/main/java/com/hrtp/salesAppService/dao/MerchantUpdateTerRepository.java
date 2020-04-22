package com.hrtp.salesAppService.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hrtp.salesAppService.entity.ormEntity.MerchantUpdateTerModel;


@Repository
public interface MerchantUpdateTerRepository extends JpaRepository<MerchantUpdateTerModel,Integer>{
	@Query("select count(t.bmutID) from MerchantUpdateTerModel t where t.approveType='W' and t.mid=?1")
	Integer findCountByApproveType(String mid);
}
