package com.hrtp.salesAppService.service;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Service;

@Service
public class ProcService {

	@PersistenceContext
	EntityManager entityManager;


	public String  callStore(String StoreName,String inParam) {

		StoredProcedureQuery query = entityManager
			    .createStoredProcedureQuery(StoreName)
			    .registerStoredProcedureParameter(1, String.class, 
			        ParameterMode.IN)
			    .registerStoredProcedureParameter(2, String.class, 
			        ParameterMode.OUT)
			    .setParameter(1, inParam);

			query.execute();
			String commentCount = (String) query.getOutputParameterValue(2);
		   return commentCount;
		   

	}
}
