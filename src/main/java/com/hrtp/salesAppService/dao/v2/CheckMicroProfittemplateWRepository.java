package com.hrtp.salesAppService.dao.v2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hrtp.salesAppService.entity.ormEntity.v2.CheckMicroProfittemplateWModel;

public interface CheckMicroProfittemplateWRepository extends JpaRepository<CheckMicroProfittemplateWModel,Integer> {
	
	/**
     * 按机构与活动类型，修改时间查询plus二代及二代以下分润模板变更表
     * @param unno 机构号
     * @param rebateType 活动类型
     * @param date 查询年月
     * @return
     */
    @Query(nativeQuery = true,
    		value = "select t.* from check_micro_profittemplate_W t, check_unit_profitemplate k " +
            " where t.aptid = k.aptid and t.mataintype != 'D' and t.profitrule = :rebateType and " + 
            "k.status = 1 and k.unno= :unno and to_char(t.mataindate,'yyyymm') = :mataindate ")
    List<CheckMicroProfittemplateWModel> findNextMonthPlusProfitByUnnoAndRebateType(@Param("unno") String unno, @Param("rebateType") Integer rebateType,
    		@Param("mataindate") String mataindate);
    
    /**
     * 按机构与活动类型，修改时间查询跑批钱包活动二代及二代以下分润模板变更表
     * @param unno 机构号
     * @param rebateType 活动类型
     * @param date 查询年月
     * @return
     */
    @Query(nativeQuery = true,
    		value = "select t.* from check_micro_profittemplate_W t, check_unit_profitemplate k " +
            " where t.aptid = k.aptid and t.mataintype != 'D' and t.profitrule = :rebateType and " + 
            "k.status = 1 and k.unno= :unno and to_char(t.mataindate,'yyyymm') = :mataindate ")
    List<CheckMicroProfittemplateWModel> findNextMonthHuoDongProfitByUnnoAndRebateType(@Param("unno") String unno, @Param("rebateType") Integer rebateType,
    		@Param("mataindate") String mataindate);
    
    /**
     * 查询syt二代及二代以下下月生效分润模板
     * @param unno
     * @return
     */
    @Query(nativeQuery = true,value = " select k.* " +
            " from check_micro_profittemplate_w k,check_unit_profitemplate m " +
            " where m.aptid = k.aptid and k.merchanttype=5 and k.profitrule = :rebateType and k.mataintype!='D' and m.status=1 and m.unno=:unno and to_char(k.mataindate,'yyyymm') = :mataindate ")
    List<CheckMicroProfittemplateWModel> findSytProfitNextMonthByUnno(@Param("unno") String unno,@Param("mataindate") String mataindate,@Param("rebateType") Integer rebateType);
    
    /**
     * 秒到二代及以下下月生效分润模板查询
     * @param unno
     * @return
     */
    @Query(nativeQuery = true,value = "select t.* from check_micro_profittemplate_w t, check_unit_profitemplate k " +
            " where t.aptid = k.aptid and t.mataintype != 'D' and t.merchanttype in (1, 2, 3) and k.status = 1 and k.unno=:unno and to_char(t.mataindate,'yyyymm') = :mataindate")
    List<CheckMicroProfittemplateWModel> findMposNextMonthByUnno(@Param("unno") String unno,@Param("mataindate") String mataindate);
    
	
}
