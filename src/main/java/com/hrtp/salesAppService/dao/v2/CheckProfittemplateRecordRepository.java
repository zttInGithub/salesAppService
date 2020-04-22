package com.hrtp.salesAppService.dao.v2;

import com.hrtp.salesAppService.entity.ormEntity.v2.CheckProfittemplateRecordModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * CheckProfittemplateRecordRepository
 * <p>
 * This is description
 * </p>
 *
 * @author xuegangliu 2019/07/10
 * @since 1.8
 **/
public interface CheckProfittemplateRecordRepository extends JpaRepository<CheckProfittemplateRecordModel, Integer> {

    /**
     * 按机构号活动类型查询模板
     * @param unno 机构号
     * @param rebate 活动类型
     * @return
     */
    CheckProfittemplateRecordModel findByUnnoEqualsAndRebateType(String unno, Integer rebate);

    /**
     * 按机构号与活动类型查询Plus三个月内的分润模板记录
     * @param unno 机构号
     * @param startDate 时间
     * @param rebateType 活动类型
     * @return
     */
    @Query("select t from CheckProfittemplateRecordModel t where t.agentId='10006' and t.unno=?1 and t.startDate>=?2 and t.rebateType=?3 order by t.startDate desc")
    List<CheckProfittemplateRecordModel> findAllSelfPlusUnnoAndStartDate(String unno, Date startDate, Integer rebateType);


    /**
     * 按产品类型及活动类型查询分润模板信息
     * @param agentId
     * @param unno
     * @param startDate
     * @param rebateType
     * @return
     */
    @Query("select t from CheckProfittemplateRecordModel t where t.agentId=?1 and t.unno=?2 and t.startDate>=?3 and t.rebateType=?4 order by t.startDate desc")
    List<CheckProfittemplateRecordModel> findAllSelfUnnoAndStartDate(String agentId,String unno, Date startDate, Integer rebateType);

    /**
     * 按产品类型查询分润模板信息
     * @param agentId
     * @param unno
     * @param startDate
     * @return
     */
    @Query("select t from CheckProfittemplateRecordModel t where t.agentId=?1 and t.unno=?2 and t.startDate>=?3 order by t.startDate desc")
    List<CheckProfittemplateRecordModel> findAllSelfUnnoAgentIdAndStartDate(String agentId,String unno, Date startDate);
}
