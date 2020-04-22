package com.hrtp.salesAppService.dao;

import com.hrtp.salesAppService.entity.ormEntity.UnnoCashBackModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UnnoCashBackRepository
 * description
 * create by lxj 2018/8/23
 **/
@Repository
public interface UnnoCashBackRepository extends JpaRepository<UnnoCashBackModel, Integer> {
    /**
     * 昨日返现及汇总
     * @param unno
     * @param yesterday
     * @param firstday
     * @return
     */
    @Query(nativeQuery = true, value = " select nvl(sum(c1.cashbackamt), 0), nvl(MIN ((select c2.cashbackamt from " +
            "pg_unnocashback_sum c2 where c2.unno = c1.unno and c2.cashbackday = ?2)), 0) from " +
            "pg_unnocashback_sum c1 where c1.unno = ?1 and c1.cashbackday between ?3 and ?2")
    List<Object[]> findByUnnoAndAndCashBackDay(String unno, String yesterday, String firstday);
}
