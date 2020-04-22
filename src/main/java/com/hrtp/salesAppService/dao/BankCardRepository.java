package com.hrtp.salesAppService.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hrtp.salesAppService.entity.ormEntity.BankCardModel;
import com.hrtp.salesAppService.entity.ormEntity.BillAgentUnitTaskModel;

@Repository
public interface BankCardRepository extends JpaRepository<BillAgentUnitTaskModel,String>{
	@Query("select b from BankCardModel b where b.cardBin=?1")
	BankCardModel findBankCardInfo(String cardBin);
}
