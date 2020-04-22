package com.hrtp.salesAppService.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.hrtp.salesAppService.entity.ormEntity.MerchManagerModel;

/**
 * <p>Description: </p>
 * @author zhq
 * @date 2018年8月24日
 */
@Repository
public interface MerchManagerRepository extends JpaRepository<MerchManagerModel,String>{
	/**
	 * <p>查询销售</p>
	 * @author zhq
	 */
	@Query(nativeQuery = true, value = "SELECT BUSID,UNNO,SALENAME FROM BILL_AGENTSALESINFO WHERE MAINTAINTYPE != 'D' and UNNO =?1 order by SALENAME asc")
	List<Map<String,String>> searchAgentSales(String unno);

	/**
	 * <p>查询行业编码</p>
	 * @author zhq
	 */
	@Query(nativeQuery = true, value = "SELECT MCCCODE,MCCNAME FROM BILL_MM_L_MCC M WHERE 1 = 1 ORDER BY M.ORDERINDEX")
	List<Map<String,String>> searchMCC();

	/**
	 * <p>小微商户报单查询终端信息</p>
	 * @author zhq
	 */
	@Query(nativeQuery = true, value = "select t.unno as UNNO from bill_terminalinfo t where t.sn=?1")
	List<Object[]> searchTerminalInfo(String sn);

	/**
	 * <p>查询商户号生成规则</p>
	 * @author zhq
	 */
	@Query(nativeQuery = true, value = "SELECT UPLOAD_PATH FROM SYS_PARAM WHERE TITLE=?1")
	List<Object[]> querySavePath(String title);

	/**
	 * <p>Description: </p>
	 * @author zhq
	 */
	@Query(nativeQuery = true, value = "select * from pg_hotmerch t where t.tname=?1 or t.bankAccNo=?2 or t.identificationnumber=?3")
	List<Object[]> queryIsHotMerch(String tname,String bandAccNo,String accNum);

	/**
	 * <p>Description: </p>
	 * @author zhq
	 */
	@Query(nativeQuery = true, value = "select t.MCCCODE,t.MCCNAME from BILL_MM_L_MCC t where t.MCCCODE=?1")
	List<Object[]> searchMCCById(String mccid);
	
	/**
	 * <p>Description: </p>
	 * @author zhq
	 */
	@Query(nativeQuery = true, value = "SELECT AREA_CODE,AREA_NAME FROM SYS_AREA A WHERE A.PROVINCE_CODE=?1 ORDER BY A.AREA_CODE ASC ")
	List<Map<String,String>> searchAreaCode(String code);
	
	@Query(nativeQuery = true,value="select unno from bill_merchantinfo where mid=?1")
	String findUnnoByMid(String mid);

	@Query("select m from MerchManagerModel m where m.mid=?1")
	MerchManagerModel findMerByMid(String mid);

	@Query("select m from MerchManagerModel m where unno =?1 and mid =?2")
	List<MerchManagerModel> findMerByUnnoAdnMid(String unno,String mid);
}
