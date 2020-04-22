package com.hrtp.salesAppService.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hrtp.salesAppService.entity.ormEntity.MerchantTaskDataModel;

/**
 * <p>Description: </p>
 * @author zhq
 * @date 2018年8月28日
 */
@Repository
public interface MerchTaskRepository extends JpaRepository<MerchantTaskDataModel,String>{

	/**
	 * <p>Description: </p>
	 * @author zhq
	 */
	@Query(nativeQuery = true, value = "select busid,user_id from BILL_AGENTSALESINFO where to_char(user_id) = ?1 and maintainType != 'D'")
	List<Object[]> queryAgentSale(String userId);

	/**
	 * <p>Description: </p>
	 * @author zhq
	 */
	@Query(nativeQuery = true, value = "select a.rname,a.legalperson,a.legaltype,a.accnum,a.raddr,a.contactperson,a.contactphone from BILL_MERCHANTINFO a where unno=?1 and mid=?2")
	Map<String,String> getmerInfo(String unno, String mid);

	/**
	 * <p>Description: </p>
	 * @author zhq
	 */
	@Query(nativeQuery = true, value = "SELECT a.rname,a.legalperson,a.legaltype,a.accnum,a.raddr,a.contactperson,a.contactphone FROM BILL_MERCHANTINFO a WHERE to_char(MAINTAINUSERID) = ?1 AND MAINTAINTYPE != 'D' AND APPROVESTATUS = 'Y' AND MID = ?2 ")
	Map<String,String> getmerInfo1(String busid, String mid);

	/**
	 * <p>Description: </p>
	 * @author zhq
	 */
	@Query(nativeQuery = true, value = "SELECT a.acctype,a.bankbranch,a.bankaccno,a.bankaccname,a.settlecycle,a.areatype,a.paybankid from BILL_MERCHANTINFO a where unno=?1 and mid=?2")
	Map<String, String> getmerAccInfo(String unno, String mid);

	/**
	 * <p>Description: </p>
	 * @author zhq
	 */
	@Query(nativeQuery = true, value = "SELECT a.acctype,a.bankbranch,a.bankaccno,a.bankaccname,a.settlecycle,a.areatype,a.paybankid FROM BILL_MERCHANTINFO a WHERE to_char(MAINTAINUSERID) = ?1 AND MAINTAINTYPE != 'D' AND APPROVESTATUS = 'Y' AND MID = ?2 ")
	Map<String, String> getmerAccInfo1(String busid, String mid);

	/**
	 * <p>Description: </p>
	 * @author zhq
	 */
	@Query(nativeQuery = true, value = "SELECT a.rname,a.bankfeerate,a.feeamt,a.creditbankrate,a.scanrate,a.scanrate1,a.scanrate2 from BILL_MERCHANTINFO a where unno=?1 and mid=?2")
	Map<String, String> getmerRateInfo(String unno, String mid);

	/**
	 * <p>Description: </p>
	 * @author zhq
	 */
	@Query(nativeQuery = true, value = "SELECT a.rname,a.bankfeerate,a.feeamt,a.creditbankrate,a.scanrate,a.scanrate1,a.scanrate2 FROM BILL_MERCHANTINFO a WHERE to_char(MAINTAINUSERID) = ?1 AND MAINTAINTYPE != 'D' AND APPROVESTATUS = 'Y' AND MID = ?2 ")
	Map<String, String> getmerRateInfo1(String busid, String mid);

	/**
	 * <p>判断是否存在待审批的工单</p>
	 * @author zhq
	 */
	@Query(nativeQuery = true, value = "select * from BILL_MERCHANTTASKDATA m where m.approveStatus='Z' and m.mid=?1  and m.type in(1,2,3)")
	List<Object[]> queryMid(String mid);

	/**
	 * <p>申请todo查询上级机构号</p>
	 * @author zhq
	 */
	@Query(nativeQuery = true, value = "SELECT t.UPPER_UNIT,t.UNNO FROM SYS_UNIT t WHERE t.UNNO=?1 ORDER BY t.UPPER_UNIT")
	List<Object[]> getFatherUnno(String unno);

	/**
	 * <p>Description: </p>
	 * @author zhq
	 */
	@Query("SELECT m from MerchantTaskDataModel m WHERE m.bmtkid = ?1")
	MerchantTaskDataModel getbyBmtkid(Integer bmtkid);


}
