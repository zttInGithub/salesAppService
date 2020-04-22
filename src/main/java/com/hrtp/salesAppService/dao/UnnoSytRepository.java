package com.hrtp.salesAppService.dao;

import com.hrtp.salesAppService.entity.ormEntity.UnnoSytModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UnnoSytRepository
 * description
 * create class by lxj 2019/3/29
 **/
@Repository
public interface UnnoSytRepository extends JpaRepository<UnnoSytModel, Integer> {

	/**收银台分润
	 * @param unno
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@Query("select new UnnoSytModel (coalesce(sum (u.txnAmt),0),coalesce(sum (u.txnProfit),0),u.txnType,coalesce(sum" +
			" (u.cashProfit),0)) from UnnoSytModel u where u.unno = ?1 and u" +
			".txnDay between ?2 and ?3 group by u.txnType ")
	List<UnnoSytModel> findByUnnoAndTxnDayBetween(String unno, String beginDate, String endDate);

	/**
	 * 汇总查询
	 * @param unno
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@Query("select coalesce(sum(u.txnProfit),0),coalesce(sum(u.cashProfit),0),coalesce(sum(u.txnAmt),0)," +
			"coalesce(sum(u.txnCount),0) from UnnoSytModel u where u.unno = ?1 and u.txnDay between ?2 and ?3")
	List<Object[]> findSumByUnno(String unno, String beginDate, String endDate);
}