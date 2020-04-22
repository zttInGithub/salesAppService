package com.hrtp.salesAppService.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hrtp.salesAppService.entity.ormEntity.MerchantTaskDetail3Model;

/**
 * <p>Description: </p>
 * @author zhq
 * @date 2018年8月29日
 */
@Repository
public interface MerchTask3Repository extends JpaRepository<MerchantTaskDetail3Model,String>{
	
	@Query("SELECT m from MerchantTaskDetail3Model m WHERE m.bmtkid = ?1")
	MerchantTaskDetail3Model getbyBmtkid(Integer bmtkid);
}
