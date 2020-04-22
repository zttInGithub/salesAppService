package com.hrtp.salesAppService.dao;

import com.hrtp.salesAppService.entity.ormEntity.BackOrderModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * BackOrderRepository
 * description
 * sl
 **/
@Repository
public interface BackOrderRepository extends JpaRepository<BackOrderModel,String> {
	
}
