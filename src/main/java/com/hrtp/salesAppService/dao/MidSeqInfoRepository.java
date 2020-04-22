package com.hrtp.salesAppService.dao;
import com.hrtp.salesAppService.entity.ormEntity.MIDSeqInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <p>Description: </p>
 * @author zhq
 * @date 2018年8月27日
 */
@Repository
public interface MidSeqInfoRepository extends JpaRepository<MIDSeqInfoModel,Integer>{
	
	/**
	 * <p>Description: </p>
	 * @author zhq
	 * new MIDSeqInfoModel(seqNo) 只需要使用这一个参数 需要有参构造
	 * SELECT m from MIDSeqInfoModel 需要使用所有参数 service需要封装
	 */
	//@Query("SELECT new MIDSeqInfoModel(seqNo) from MIDSeqInfoModel m WHERE m.areaCode = ?1 AND m.mccid = ?2")
	@Query("SELECT m from MIDSeqInfoModel m WHERE m.areaCode = ?1 AND m.mccid = ?2")
	List<MIDSeqInfoModel> findByAreaCodeAndMccid(String areaCode, String mccid);
	
}