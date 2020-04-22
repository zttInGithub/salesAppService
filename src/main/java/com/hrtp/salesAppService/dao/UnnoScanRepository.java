package com.hrtp.salesAppService.dao;

import com.hrtp.salesAppService.entity.ormEntity.UnnoScanModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UnnoScanRepository
 * description 扫码分润
 * create by lxj 2018/8/22
 **/
@Repository
public interface UnnoScanRepository extends JpaRepository<UnnoScanModel,Integer>{

    /**
     * 扫码明细
     * @param unno
     * @param beginDate
     * @param endDate
     * @return
     */
    @Query("select new UnnoScanModel (sum (u.txnAmt),sum (u.txnProfit),sum(u.cashProfit),u.txnType) from UnnoScanModel " +
            "u where u.unno = ?1 and u.txnDay between ?2 and ?3 group by u.txnType ")
    List<UnnoScanModel> findByUnnoAndTxnDayBetween (String unno, String beginDate, String endDate);

    /**
     * 汇总查询
     * @param unno
     * @param beginDate
     * @param endDate
     * @return
     */
    @Query("select coalesce(sum(u.txnProfit),0),coalesce(sum(u.cashProfit),0),coalesce(sum(u.txnAmt),0)," +
            "coalesce(sum(u.txnCount),0) from UnnoScanModel u where u.unno = ?1 and u.txnDay between ?2 and ?3")
    List<Object[]> findSumByUnno(String unno, String beginDate, String endDate);
}
