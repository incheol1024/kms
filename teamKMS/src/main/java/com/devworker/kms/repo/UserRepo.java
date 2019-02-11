package com.devworker.kms.repo;

import com.devworker.kms.entity.UserDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends PagingAndSortingRepository<UserDao, String>{
	@Query("select t from UserDao t where t.groupId = :user_group")
	Page<UserDao> getGroupedUser(@Param("user_group") int groupId, Pageable s);
	
	@Query("select t from UserDao t where t.id like :searchText or t.name like :searchText")
	Page<UserDao> searchUser(@Param("searchText") String text, Pageable pageable);
	
	@Override
    Page<UserDao> findAll(Pageable pageable);
}
