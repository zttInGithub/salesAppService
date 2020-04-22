package com.hrtp.salesAppService.dao;

import com.hrtp.salesAppService.entity.ormEntity.UnnoMdaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UnnoMdaoRepository
 * description TODO
 * create by lxj 2018/8/22
 **/
@Repository
public interface UnnoMdaoRepository extends JpaRepository<UnnoMdaoModel, Integer> {
    /**
     * 秒到明细
     *
     * @param unno
     * @param beginDate
     * @param endDate
     * @return
     */
    @Query("select new UnnoMdaoModel (coalesce(sum (u.txnAmt),0),coalesce(sum (u.txnProfit),0),u.txnType,coalesce(sum" +
            " (u.cashProfit),0)) from UnnoMdaoModel u where u.unno = ?1 and u" +
            ".txnDay between ?2 and ?3 group by u.txnType ")
    List<UnnoMdaoModel> findByUnnoAndTxnDayBetween(String unno, String beginDate, String endDate);

    /**
     * 秒到汇总
     *
     * @param unno
     * @param beginDate
     * @param endDate
     * @return
     */
    @Query("select coalesce(sum(u.txnProfit),0),coalesce(sum(u.cashProfit),0),coalesce(sum(u.txnAmt),0)," +
            "coalesce(sum(u.txnCount),0) from UnnoMdaoModel u where u.unno = ?1 and u.txnDay between ?2 and ?3")
    List<Object[]> findSumByUnno(String unno, String beginDate, String endDate);
}
