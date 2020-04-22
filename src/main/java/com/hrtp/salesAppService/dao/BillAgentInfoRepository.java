package com.hrtp.salesAppService.dao;

import com.hrtp.salesAppService.entity.ormEntity.BillAgentInfoModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * BillAgentInfoRepository
 * description
 * create by lxj 2018/8/21
 **/
@Repository
public interface BillAgentInfoRepository extends JpaRepository<BillAgentInfoModel,String> {
    @Query("select new BillAgentInfoModel(agentName,bAddr,bno,legalNum,legalPerson,legalType) from " +
            "BillAgentInfoModel u where u.buId = ?1 and u.unno=?2")
    BillAgentInfoModel findBaseInfo(String buId, String unno);
    @Query("select new BillAgentInfoModel(accType,areaType,bankAccName,bankAccNo,bankArea,bankBranch,bankType) from " +
            "BillAgentInfoModel u where u.buId = ?1 and u.unno=?2")
    BillAgentInfoModel findTxnInfo(String buId, String unno);
    @Query("select new BillAgentInfoModel(businessContact,businessContactsMail,businessContactsPhone,contact," +
            "contactTel,riskContact,riskContactPhone,riskContactMail) from BillAgentInfoModel u where u.buId = ?1 and u.unno=?2")
    BillAgentInfoModel findContactInfo(String buId, String unno);
    BillAgentInfoModel findByUnno(String unno);
    BillAgentInfoModel findByBuId(String buId);
    @Query(nativeQuery=true , value = "select un_lvl from sys_unit where unno=?1")
    Integer queryUnitLvlByUnno(String unnno);

    /**
     * 分页获取代理商信息
     * @param unnos
     * @param pageable
     * @return
     */
    @Query("select m from BillAgentInfoModel m where m.maintainType<>'D' and m.unno in (:unnos) order by m.maintainDate desc")
    Page<BillAgentInfoModel> findByRowsByUnnosAndPage(@Param("unnos") String[] unnos, Pageable pageable);

    /**
     * 按代理商名称模糊查询分页获取代理商信息
     * @param unnos
     * @param pageable
     * @return
     */
    @Query("select m from BillAgentInfoModel m where m.maintainType<>'D' and m.unno in (:unnos) and m.agentName like :agentName order by m.maintainDate desc")
    Page<BillAgentInfoModel> findByRowsByUnnosAndAgentNameAndPage(@Param("unnos") String[] unnos,@Param("agentName") String agentName, Pageable pageable);
}
