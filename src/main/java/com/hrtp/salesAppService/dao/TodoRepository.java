package com.hrtp.salesAppService.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hrtp.salesAppService.entity.ormEntity.TodoModel;

/**
 * <p>Description: </p>
 * @author zhq
 * @date 2018年8月29日
 */
public interface TodoRepository extends JpaRepository<TodoModel,Integer>{
	@Query("select t from TodoModel t where t.batchJobNo = ?1 and t.status = ?2 and t.bizType = ?3")
	List<TodoModel> queryTodo(String batchJobNo,Integer status,String bizType);
}
