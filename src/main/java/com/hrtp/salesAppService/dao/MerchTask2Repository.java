package com.hrtp.salesAppService.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hrtp.salesAppService.entity.ormEntity.MerchantTaskDetail2Model;

/**
 * <p>Description: </p>
 * @author zhq
 * @date 2018年8月29日
 */
@Repository
public interface MerchTask2Repository extends JpaRepository<MerchantTaskDetail2Model,Integer>{
	
	@Query("SELECT m from MerchantTaskDetail2Model m WHERE m.bmtkid = ?1")
	MerchantTaskDetail2Model getbyBmtkid(Integer bmtkid);
}
