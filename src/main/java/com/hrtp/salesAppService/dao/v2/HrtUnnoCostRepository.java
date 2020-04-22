package com.hrtp.salesAppService.dao.v2;

import com.hrtp.salesAppService.entity.ormEntity.v2.HrtUnnoCostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface HrtUnnoCostRepository extends JpaRepository<HrtUnnoCostModel,Integer> {

    /**
     * 查询一代模板
     * @param unno
     * @param status
     * @return
     */
    @Query("select t from HrtUnnoCostModel t where t.unno=?1 and t.status=?2 and t.txnDetail<20")
    List<HrtUnnoCostModel> findAllByUnnoEqualsAndStatusEquals(String unno,Integer status);

    /**
     * 一代成本查询
     * @param unno
     * @param machineType
     * @param txnType
     * @param txnDetail
     * @return
     */
    @Query("select t from HrtUnnoCostModel t where t.unno=?1 and t.machineType=?2 and t.txnType=?3 and t.txnDetail=?4 and t.status=1")
    HrtUnnoCostModel findYiDaiCommon(String unno,Integer machineType,Integer txnType,Integer txnDetail);

    /**
     * 查询(PLUS活动大于21)运营中心/一代成本信息
     * @param unno 机构号
     * @return
     */
    @Query("select t from HrtUnnoCostModel t where t.unno=?1 and t.machineType=1 and t.txnType=1  and t.txnDetail in (22,23) and t.status=1 order by t.txnDetail ")
    List<HrtUnnoCostModel> findPlusHrtCostAllByUnno(String unno);

    /**
     * 修改代理商提现比例与提现开关
     * @param cashRatio
     * @param cashSwitch
     */
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update bill_agentunitinfo t set t.cashratio=?1,t.cashswitch=?2 where t.buid=?3 and t.unno=?4")
    void updateSelfCashRatioAndCashSwitch(BigDecimal cashRatio,Integer cashSwitch,Integer buid,String unno);

    /**
     * 按机构号与活动类型查询一代成本信息
     * @param unno
     * @param rebate
     * @return
     */
    @Query("select t from HrtUnnoCostModel t where t.unno=?1 and t.machineType=1 and t.txnType=1  and t.txnDetail=?2 and t.status=1")
    List<HrtUnnoCostModel> findAllHrtCostByUnnoAndRebateType(String unno, Integer rebate);
    
    /**
     * 查询一代钱包跑批活动类型成本
     * @param unno
     * @return
     */
    @Query("select t from HrtUnnoCostModel t where t.status = 1 and t.unno = ?1 and t.machineType=1 and t.txnType=1 and t.txnDetail = ?2 ")
    List<HrtUnnoCostModel> findAllHrtCostByUnnoAndHuodongType(String unno, Integer rebate);
    
    @Query("select t from HrtUnnoCostModel t where t.unno=?1 and t.machineType=1 and t.txnType=1  and t.txnDetail = ?2 and t.status=1")
    List<HrtUnnoCostModel> findSelfSytAllByUnnoAndTxnDetailEquals(String unno, Integer rebateType);

    /**
     * 查询机构级别信息
     * @param unno
     * @return (0:总公司,1:运营中心,2:一代,2以后为二代之后)
     */
    @Query(nativeQuery = true,value = "select s.un_lvl from sys_unit s where s.unno=:unno")
    Integer findSelfUnnoLvlByUnno(@Param("unno") String unno);

    /**
     * 下级代理机构列表查询
     * @param unno
     * @return
     */
    @Query(nativeQuery = true,value = "select s.unno from sys_unit s where s.upper_unit=:unno")
    List<String> findAllSubUnnoByUnno(@Param("unno") String unno);

    /**
     * 查询子代理商机构
     * @param upper 机构号
     * @return
     */
    @Query(nativeQuery = true,value = "select m.buid,m.agentname,m.unno,s.un_lvl,u.user_id" +
            "  from bill_agentunitinfo m,sys_unit s,sys_unit_user su,sys_user u" +
            " where m.unno = s.unno and s.upper_unit = :upper and su.unno = s.unno " +
            " and u.user_id = su.user_id and u.user_lvl = 0 and m.maintaintype!='D'")
    List<Map> findSelfUnnoListByUpper(@Param("upper") String upper);

    /**
     * 上级机构信息获取
     * @param unno
     * @return
     */
    @Query(nativeQuery = true,value = "select m.buid,m.agentname,m.unno,s.un_lvl" +
            "  from bill_agentunitinfo m,sys_unit s where m.unno = s.upper_unit and s.unno = :unno")
    Map findSelfUpperUnnoByUnno(@Param("unno") String unno);

    /**
     * 更加机构号,活动类型，状态查询成本设置信息
     * @param unno 机构号
     * @param txnDetail 活动
     * @param status 状态
     * @return
     */
    HrtUnnoCostModel findByUnnoEqualsAndTxnDetailEqualsAndStatusEquals(String unno, Integer txnDetail, Integer status);
}
