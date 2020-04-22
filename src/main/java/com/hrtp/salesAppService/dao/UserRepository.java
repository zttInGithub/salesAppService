package com.hrtp.salesAppService.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrtp.salesAppService.entity.ormEntity.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {
	 @Query("select u from User u where u.loginName=?1 and u.pwd=?2")
	 User findByUserNameAndPassword(String userName,String password);
	 User findByLoginName(String loginName);
}
