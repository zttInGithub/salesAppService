package com.hrtp.salesAppService.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hrtp.salesAppService.entity.ormEntity.MerchantTaskDetail1Model;

/**
 * <p>Description: </p>
 * @author zhq
 * @date 2018年8月29日
 */
@Repository
public interface MerchTask1Repository extends JpaRepository<MerchantTaskDetail1Model,Integer>{
	
	@Query("SELECT m from MerchantTaskDetail1Model m WHERE m.bmtkid = ?1")
	MerchantTaskDetail1Model getbyBmtkid(Integer bmtkid);
}
