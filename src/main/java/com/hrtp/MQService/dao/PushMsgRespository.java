package com.hrtp.MQService.dao;

import com.hrtp.MQService.entity.model.PushMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Clob;
import java.util.List;

/**
 * PushMsgRespository
 * description
 * create class by lxj 2019/1/11
 **/
public interface PushMsgRespository extends JpaRepository<PushMessage,String> {

    @Query(nativeQuery = true, value = "SELECT wm_concat(t.UNNO) FROM SYS_UNIT t WHERE t.UN_LVL=?1 ")
    List<Clob> getUnnoByLvl(String unLvl);
}
