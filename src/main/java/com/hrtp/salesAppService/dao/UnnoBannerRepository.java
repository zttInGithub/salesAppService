package com.hrtp.salesAppService.dao;

import com.hrtp.salesAppService.entity.ormEntity.UnnoBannerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UnnoBannerRepository
 * description
 * create by lxj 2018/8/28
 **/
@Repository
public interface UnnoBannerRepository extends JpaRepository<UnnoBannerModel,Integer>{
    @Query("select u from UnnoBannerModel u where u.enabled = 1 and u.validTime > current_date order by u.imageOrder asc ")
    List<UnnoBannerModel> findList();
}
