package com.hrtp.salesAppService.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hrtp.salesAppService.entity.ormEntity.MerchantBankCardModel;

/**
 * <p>Description: </p>
 * @author zhq
 * @date 2018年8月27日
 */
@Repository
public interface MerchantBankRepository extends JpaRepository<MerchantBankCardModel,String>{

}
