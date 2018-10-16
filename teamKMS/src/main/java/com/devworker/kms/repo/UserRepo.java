package com.devworker.kms.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.devworker.kms.dao.UserDao;

public interface UserRepo extends PagingAndSortingRepository<UserDao, Integer>{
	@Query("select t from UserDao t where t.groupId = :user_group")
	Page<UserDao> getGroupedUser(@Param("user_group") int groupId, Pageable p, Sort s);
}
