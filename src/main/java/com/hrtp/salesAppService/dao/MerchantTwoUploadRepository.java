package com.hrtp.salesAppService.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.hrtp.salesAppService.entity.ormEntity.MerchantTwoUploadModel;

@Repository
public interface MerchantTwoUploadRepository extends JpaRepository<MerchantTwoUploadModel,Integer> {
	
	@Query("select m from MerchantTwoUploadModel m where m.approveStatus in('W','K') and m.mid =?1   ")
	List<MerchantTwoUploadModel> findByMidExsit(String mid);
}
