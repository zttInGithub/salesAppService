package com.hrtp.salesAppService.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hrtp.salesAppService.entity.ormEntity.AppVersionModel;

public interface AppVersionRepository extends JpaRepository<AppVersionModel, String> {

	@Query("select o from AppVersionModel o where o.os=?1 and rownum =1")
	AppVersionModel getByOsAndRownum(String os);
	
}
