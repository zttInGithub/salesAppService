package com.hrtp.salesAppService.dao;

import com.hrtp.salesAppService.entity.ormEntity.MistakeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * MistakeRepository
 * description
 * sl
 **/
@Repository
public interface MistakeRepository extends JpaRepository<MistakeModel,String> {
	
//	@Query("select new MistakeModel(dpId,rname,raddr,contactPhone,contactPerson,agRemarks,orderUpload) "
//			+ "from MistakeModel u where u.dpId = ?1 and u.orderType = ?2")
//	MistakeModel queryMistakeByDpIdAndType(String dpId,String orderType);
}
