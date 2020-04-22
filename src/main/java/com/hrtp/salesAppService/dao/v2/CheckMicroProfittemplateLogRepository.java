package com.hrtp.salesAppService.dao.v2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hrtp.salesAppService.entity.ormEntity.v2.CheckMicroProfitTemplateLogModel;
import com.hrtp.salesAppService.entity.ormEntity.v2.CheckMicroProfittemplateWModel;

public interface CheckMicroProfittemplateLogRepository extends JpaRepository<CheckMicroProfitTemplateLogModel,Integer> {

	/**
	 * 按机构与活动类型查询plus二代及以下分润模板
	 * @param unno 机构号
	 * @param rebateType 活动类型
	 * @return
	 */
	@Query(nativeQuery = true,
			value = "select * from check_micro_profittemplate_log t where merchanttype=6 and profitrule = :rebateType and "
					+ "status = 1 and unno= :unno order by startDate desc ")
	List<CheckMicroProfitTemplateLogModel> findPlusProfitHistoryByUnnoAndRebateType(@Param("unno") String unno, @Param("rebateType") Integer rebateType);

	/**
	 * 查询syt二代及二代以下历史生效分润模板
	 * @param unno
	 * @return
	 */
	@Query(nativeQuery = true,value = " select * from check_micro_profittemplate_log where merchanttype=5 and profitrule = :rebateType and "
			+ "status=1 and unno=:unno order by startDate desc ")
	List<CheckMicroProfitTemplateLogModel> findSytProfitHistoryByUnno(@Param("unno") String unno, @Param("rebateType") Integer rebateType);

	/**
	 * 秒到二代及以下历史生效分润模板查询
	 * @param unno
	 * @return
	 */
	@Query(nativeQuery = true,value = "select * from check_micro_profittemplate_log where mataintype != 'D' and "
			+ "merchanttype in (1, 2, 3) and status = 1 and unno=:unno order by startDate desc ")
	List<CheckMicroProfitTemplateLogModel> findMposProfitHistoryByUnno(@Param("unno") String unno);

	/**
	 * 按机构与活动类型查询跑批钱包活动二代及以下分润模板
	 * @param unno 机构号
	 * @param rebateType 活动类型
	 * @return
	 */
	@Query(nativeQuery = true,
			value = "select * from check_micro_profittemplate_log t where merchanttype=6 and profitrule = :rebateType and "
					+ "status = 1 and unno= :unno order by startDate desc ")
	List<CheckMicroProfitTemplateLogModel> findHuoDongProfitHistoryByUnnoAndRebateType(@Param("unno") String unno, @Param("rebateType") Integer rebateType);

}
