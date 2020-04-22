package com.hrtp.salesAppService.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hrtp.salesAppService.entity.ormEntity.MerchantThreeUploadModel;

@Repository
public interface MerchantThreeUploadRepository extends JpaRepository<MerchantThreeUploadModel,Integer> {
	
	@Query("select m from MerchantThreeUploadModel m where m.approveStatus = 'W' and m.mid =?1   ")
	List<MerchantThreeUploadModel> findByMidExsit(String mid);
}
