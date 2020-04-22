package com.hrtp.salesAppService.dao;


import com.hrtp.salesAppService.entity.ormEntity.PushMessage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface PushMessageRepository extends JpaRepository<PushMessage,String>{
	
}
