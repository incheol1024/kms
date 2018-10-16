package com.devworker.kms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.devworker.kms.dao.UserDao;

public interface UserRepo extends CrudRepository<UserDao, Integer>{
	@Query("select t from UserDao t where t.groupId = :user_group")
	List<UserDao> getGroupedUser(@Param("user_group") int groupId);
}
