package com.hrtp.salesAppService.dao;

import com.hrtp.salesAppService.entity.ormEntity.UnnoMerInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UnnoMerInfoRepository
 * description TODO
 * create by lxj 2018/8/23
 **/
@Repository
public interface UnnoMerInfoRepository extends JpaRepository<UnnoMerInfoModel,Integer>{

    UnnoMerInfoModel findByUnnoAndMerDay(String unno,String merDay);

    List<UnnoMerInfoModel> findByUnnoAndMerDayBetweenOrderByMerDayDesc(String unno,String begainDate,String endDate);
}
