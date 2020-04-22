package com.hrtp.salesAppService.dao;

import com.hrtp.salesAppService.entity.ormEntity.UnnoTxnFenRunModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UnnoTxnFenRunRepository
 * description
 * create by lxj 2018/8/23
 **/
@Repository
public interface UnnoTxnFenRunRepository extends JpaRepository<UnnoTxnFenRunModel, Integer> {

    @Query("select new UnnoTxnFenRunModel (coalesce(sum(u.txnAmt),0),coalesce(sum (u.txnCount),0),coalesce(sum(u" +
            ".txnProfit + u.cashProfit),0) ) from UnnoTxnFenRunModel u  where u.unno=?1 and u.txnDay = ?2")
    UnnoTxnFenRunModel findByUnnoAndTxnDay(String unno, String yesterday);

    @Query("select coalesce(sum(u.txnProfit + u.cashProfit),0) from UnnoTxnFenRunModel u where u.unno = ?1 and u" +
            ".txnDay between ?2 and ?3")
    List<Double> findSumByUnno(String unno, String begainDate, String endDate);

    @Query("select coalesce(sum(u.txnAmt),0),coalesce(sum(u.txnCount),0) from UnnoTxnFenRunModel u where u.unno = ?1 and u" +
            ".txnDay between ?2 and ?3")
    List<Object[]> findByUnnoAndTxnDayBetween(String unno, String beginDate, String endDate);
}
