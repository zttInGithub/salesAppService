package com.hrtp.salesAppService.dao;

import com.hrtp.salesAppService.entity.ormEntity.BillAgentUnitTaskDetail1Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * BillAgentUnitTaskDetail1Repository
 * description TODO
 * create by lxj 2018/8/27
 **/
@Repository
public interface BillAgentUnitTaskDetail1Repository extends JpaRepository<BillAgentUnitTaskDetail1Model,
        CriteriaBuilder.In>{

}
