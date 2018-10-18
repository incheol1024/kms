package com.devworker.kms.repo;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.devworker.kms.dao.UserDao;

public interface UserRepo extends PagingAndSortingRepository<UserDao, String>{
	@Query("select t from UserDao t where t.groupId = :user_group")
	List<UserDao> getGroupedUser(@Param("user_group") int groupId,Sort s);
	
	@Query("select t from UserDao t where t.id like :searchText or t.name like :searchText")
	List<UserDao> searchUser(@Param("searchText") String text, Sort s);
	
	@Override
    List<UserDao> findAll();
}
