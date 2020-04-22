package com.hrtp.salesAppService.dao;

import com.hrtp.salesAppService.entity.ormEntity.BillAgentUnitTaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BillAgentUnitTaskRepository
 * description TODO
 * create by lxj 2018/8/27
 **/
@Repository
public interface BillAgentUnitTaskRepository extends JpaRepository<BillAgentUnitTaskModel,Integer>{

    @Query(value = "select u from  BillAgentUnitTaskModel u where u.maintainType <>'D' and u.approveStatus in ('K'," +
            "'W') and unno = ?1")
    List<BillAgentUnitTaskModel> findByUnnoExsit(String unno);
}
