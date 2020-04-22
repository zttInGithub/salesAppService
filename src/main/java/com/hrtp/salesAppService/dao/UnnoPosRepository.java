package com.hrtp.salesAppService.dao;

import com.hrtp.salesAppService.entity.ormEntity.UnnoPosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UnnoPosRepository
 * description TODO
 * create by lxj 2018/8/22
 **/
@Repository
public interface UnnoPosRepository extends JpaRepository<UnnoPosModel, Integer> {
    /**
     * 传统POSlist
     *
     * @param unno
     * @param txnType
     * @param beginDate
     * @param endDate
     * @return
     */
    @Query(value = "select new UnnoPosModel (coalesce(sum (u.txnAmt),0),coalesce(sum (u.txnProfit),0),coalesce(sum(u" +
            ".txnCount),0),u.pType) from UnnoPosModel u where u.unno = ?1 and u.txnType = ?2 and " +
            "u.txnDay between ?3 and ?4 group by u.pType ")
    List<UnnoPosModel> findByUnnoAndPTypeAndTxnDayBetween(String unno, Integer txnType, String beginDate, String
            endDate);

    /**提现明细查询
     * @param unno
     * @param beginDate
     * @param endDate
     * @return
     */
    @Query(value = "select new UnnoPosModel (coalesce(sum (u.txnAmt),0),coalesce(sum (u.txnCount),0),coalesce(sum(u" +
            ".cashProfit),0),u.pType) from UnnoPosModel u where u.unno = ?1 and u.txnType = 5 and " +
            "u.txnDay between ?2 and ?3 group by u.pType ")
    List<UnnoPosModel> findByUnnoAndPTypeAndTxnDayBetween(String unno, String beginDate, String endDate);

    /**
     * POS分润汇总
     *
     * @param unno
     * @param txnType
     * @param beginDate
     * @param endDate
     * @return
     */
    @Query("select coalesce(sum(u.txnProfit),0),coalesce(sum(u.txnAmt),0),coalesce(sum(u.txnCount),0) " +
            "from UnnoPosModel u where u.unno = ?1 and u.txnType=?2 and u.txnDay between ?3 and ?4")
    List<Object[]> findSumByUnnoAndPTypeAndTxnDayBetween(String unno, Integer txnType, String beginDate, String
            endDate);

    /**
     * 提现汇总
     * @param unno
     * @param beginDate
     * @param endDate
     * @return
     */
    @Query("select coalesce(sum(u.cashProfit),0),coalesce(sum(u.txnAmt),0),coalesce(sum(u.txnCount),0) " +
            "from UnnoPosModel u where u.unno = ?1 and u.txnType=5 and u.txnDay between ?2 and ?3")
    List<Object[]> findSumByUnnoAndPTypeAndTxnDayBetween(String unno, String beginDate, String endDate);
}
