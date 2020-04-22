package com.hrtp.salesAppService.dao;

import com.hrtp.salesAppService.entity.ormEntity.CheckRealtxnModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckRealtxnRepository extends JpaRepository<CheckRealtxnModel,Integer>{
}
