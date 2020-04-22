package com.hrtp.salesAppService.dao;

import com.hrtp.salesAppService.entity.ormEntity.SysParamModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * SysParamRepository
 * description TODO
 * create by lxj 2018/8/27
 **/
@Repository
public interface SysParamRepository extends JpaRepository<SysParamModel,String>{

}
