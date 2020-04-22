package com.hrtp.salesAppService.dao;

import com.hrtp.salesAppService.entity.ormEntity.UnnoNoticeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UnnoNoticeRepository
 * description
 * create by lxj 2018/8/24
 **/
@Repository
public interface UnnoNoticeRepository extends JpaRepository<UnnoNoticeModel, Integer> {
    @Query(nativeQuery = true,value = "select * from (select MSGTOPIC,MSGDESC,STATUS,MSGSENDDATE from " +
            "PG_UNNO_NOTICEID where status=?1 order by MSGSENDDATE desc) where rownum = 1 ")
    List<Object[]> findByStatusAndOrderByMsgsendDateDesc(Integer status);
}
