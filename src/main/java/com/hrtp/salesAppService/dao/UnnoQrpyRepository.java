package com.hrtp.salesAppService.dao;

import com.hrtp.salesAppService.entity.ormEntity.UnnoQrpyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UnnoQrpyRepository
 * description TODO
 * create by lxj 2018/8/22
 **/
@Repository
public interface UnnoQrpyRepository extends JpaRepository<UnnoQrpyModel, Integer> {

    /**
     * 快捷list
     *
     * @param unno
     * @param beginDate
     * @param endDate
     * @return
     */
    @Query("select new UnnoQrpyModel (coalesce(sum (u.txnAmt),0),coalesce(sum (u.txnProfit),0),coalesce(sum(u" +
            ".casheProfit),0),u.txnType) from UnnoQrpyModel u where u.unno = ?1 and u.txnDay between ?2 and ?3 group " +
            "by u.txnType ")
    List<UnnoQrpyModel> findByUnnoAndTxnDayBetween(String unno, String beginDate, String endDate);

    /**
     * 快捷汇总
     *
     * @param unno
     * @param beginDate
     * @param endDate
     * @return
     */
    @Query("select coalesce(sum(u.txnProfit),0),coalesce(sum(u.casheProfit),0),coalesce(sum(u.txnAmt),0),coalesce" +
            "(sum(u.txnCount),0) from UnnoQrpyModel u  where u.unno = ?1 and u.txnDay between ?2 and ?3")
    List<Object[]> findSumByUnno(String unno, String beginDate, String endDate);
}
