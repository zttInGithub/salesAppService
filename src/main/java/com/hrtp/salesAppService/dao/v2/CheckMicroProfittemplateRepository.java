package com.hrtp.salesAppService.dao.v2;

import com.hrtp.salesAppService.entity.ormEntity.v2.CheckMicroProfittemplateModel;
import com.hrtp.salesAppService.entity.ormEntity.v2.CheckMicroProfittemplateWModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface CheckMicroProfittemplateRepository
        extends JpaRepository<CheckMicroProfittemplateModel,Integer> {

    /**
     * 查询plus二代及二代以下分润模板
     * @param unno 机构号
     * @return
     */
    @Query(nativeQuery = true,value = " select k.* " +
            " from check_micro_profittemplate k,check_unit_profitemplate m " +
            " where m.aptid = k.aptid and k.mataintype!='D' and m.status=1 and k.merchanttype=6 and k.profitrule in (22,23) and m.status=1 and m.unno=:unno order by k.profitrule ")
    List<CheckMicroProfittemplateModel> findPlusProfitAllByUnno(@Param("unno") String unno);

    /**
     * 查询syt二代及二代以下分润模板
     * @param unno,rebateType
     * @return
     */
    @Query(nativeQuery = true,value = " select k.* " +
            " from check_micro_profittemplate k,check_unit_profitemplate m " +
            " where m.aptid = k.aptid and k.merchanttype=5 and k.profitrule = :rebateType and k.mataintype!='D' and m.status=1 and m.unno=:unno")
    List<CheckMicroProfittemplateModel> findSytProfitAllByUnno(@Param("unno") String unno,@Param("rebateType") Integer rebateType);

    /**
     * 查询syt二代及二代以下分润模板
     * @param unno
     * @return
     */
    @Query(nativeQuery = true,value = " select k.* " +
            " from check_micro_profittemplate k,check_unit_profitemplate m " +
            " where m.aptid = k.aptid and k.merchanttype=5 and k.mataintype!='D' and m.status=1 and m.unno=:unno ")
    List<CheckMicroProfittemplateModel> findAllSytProfitAllByUnno(@Param("unno") String unno);
    
    /**
     * 按机构与活动类型查询plus二代及二代以下分润模板
     * @param unno 机构号
     * @param rebateType 活动类型
     * @return
     */
    @Query(nativeQuery = true,value = " select k.* from check_micro_profittemplate k,check_unit_profitemplate m " +
            " where m.aptid = k.aptid and k.merchanttype=6 and k.profitrule in (22,23) and m.status=1 "
            + "and k.mataintype!='D' and m.unno=:unno and k.PROFITRULE=:rebateType")
    List<CheckMicroProfittemplateModel> findAllPlusProfitByUnnoAndRebateType(@Param("unno") String unno, @Param("rebateType") Integer rebateType);

    /**
     * 按机构与活动类型查询跑批钱包活动二代及二代以下分润模板
     * @param unno 机构号
     * @param rebateType 活动类型
     * @return
     */
    @Query(nativeQuery = true,value = " select k.* from check_micro_profittemplate k,check_unit_profitemplate m "
    		+ "where m.aptid = k.aptid and k.merchanttype=6 and k.profitrule = :rebateType "
    		+ "and m.status=1 and k.mataintype!='D' and m.unno=:unno")
    List<CheckMicroProfittemplateModel> findAllHuoDongProfitByUnnoAndRebateType(@Param("unno") String unno, @Param("rebateType") Integer rebateType);
    
    /**
     * 分配模板
     * @param unno 分配的机构号
     * @param aptId 模板id
     * @param userId 操作人
     * @return
     */
    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = "insert into check_unit_profitemplate (UNNO,aptid,matainuserid,status,mataindate)values (?1,?2,?3,1,sysdate)")
    Integer saveCheckUnitProfitemplate(String unno, Integer aptId, Integer userId);

    /**
     * 代还模板查询
     * @param unno
     * @return
     */
    @Query(nativeQuery = true,value = "select t.* from check_micro_profittemplate t, check_unit_profitemplate k" +
            " where t.aptid=k.aptid and t.merchanttype = 4 and t.mataintype != 'D' and k.status=1 and k.unno = ?1")
    CheckMicroProfittemplateModel findRepayRate(String unno);

    /**
     * mpos分润模板查询
     * @param unno
     * @return
     */
    @Query(nativeQuery = true,value = "select t.* from check_micro_profittemplate t, check_unit_profitemplate k " +
            " where t.aptid = k.aptid and t.mataintype != 'D' and t.merchanttype in (1, 2, 3) and k.status = 1 and k.unno=?1")
    List<CheckMicroProfittemplateModel> findAllSelfByUnno(String unno);

    /**
     * 按机构号与类型查询分润模板
     * @param merchanttype
     * @param unno
     * @return
     */
    @Query(nativeQuery = true,value = "select t.* from check_micro_profittemplate t, check_unit_profitemplate k " +
            " where t.aptid = k.aptid and t.mataintype != 'D' and t.merchanttype=?1 and k.status = 1 and k.unno=?2")
    CheckMicroProfittemplateModel findProfitfByUnnoAndMerchantType(Integer merchanttype,String unno);

    /**
     * 查询分润关联的信息
     * @param merchanttype
     * @param unno
     * @return
     */
    @Query(nativeQuery = true,value = "select t.matainuserid,t.aptid from check_unit_profitemplate t,check_micro_profittemplate k" +
            " where t.aptid=k.aptid and t.status=1 and k.mataintype!='D' and k.merchanttype=?1 and t.unno=?2")
    Map getProfitUnnoInfo(Integer merchanttype,String unno);

    /**
     * 查询Plus分润关联的信息
     * @param merchanttype
     * @param unno
     * @return
     */
    @Query(nativeQuery = true,value = "select t.matainuserid,t.aptid from check_unit_profitemplate t,check_micro_profittemplate k" +
            " where t.aptid=k.aptid and t.status=1 and k.mataintype!='D' and k.merchanttype=?1 and t.unno=?2 and k.profitrule=?3")
    Map getPlusProfitUnnoInfo(Integer merchanttype,String unno,Integer rebateType);

    /**
     * 修改机构模板关联表状态
     * @param unno
     * @param aptId
     * @return
     */
    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = "update check_unit_profitemplate t set t.status=0 where t.unno=?1 and t.aptid=?2 and t.status=1 ")
    Integer updateCheckUnitProfitemplate(String unno, Integer aptId);
    
}
