package com.hrtp.salesAppService.dao;

import com.hrtp.salesAppService.entity.ormEntity.UnnoRepayModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UnnoRepayRepository
 * description
 * create by lxj 2018/8/22
 **/
@Repository
public interface UnnoRepayRepository extends JpaRepository <UnnoRepayModel,Integer>{

    /**
     * 查询明细
     * @param unno
     * @param begainDate
     * @param endDate
     * @return
     */
    List<UnnoRepayModel> findByUnnoAndTxndayBetween(String unno, String begainDate, String endDate);

    /**
     * 查询汇总
     * @param unno
     * @param begainDate
     * @param endDate
     * @return
     */
    @Query(nativeQuery = true,value="select nvl(sum(TXNPROFIT),0),nvl(sum(TXNCOUNT),0),nvl(sum(TXNAMT),0) from " +
            "PG_UNNOREPAY_FENRUN  where unno = ?1 and txnday between ?2 and ?3")
    List<Object[]> findSumByUnno(String unno,String begainDate,String endDate);
}
