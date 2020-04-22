package com.hrtp.salesAppService.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hrtp.salesAppService.entity.ormEntity.PushMessage;

/**
 * MessageRepository
 * description TODO
 * create by lxj 2018/8/30
 **/
@Repository
public interface MessageRepository extends JpaRepository<PushMessage, String> {

    @Override
    Page<PushMessage> findAll(Pageable pageable);

    @Query("select u from PushMessage u where (u.agentId is null or u.agentId=:unno) and u.status <> 2 order by u.time" +
            " desc")
    Page<PushMessage> findByRowsAndPage(@Param("unno") String unno,Pageable pageable);
    @Modifying
    @Query("update PushMessage o set o.status=?1 where o.id=?2")
	Integer updateStatusById(Integer status,String id);
}
