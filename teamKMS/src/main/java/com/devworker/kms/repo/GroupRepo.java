package com.devworker.kms.repo;

import com.devworker.kms.dao.GroupDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface GroupRepo extends PagingAndSortingRepository<GroupDao, Integer> {
	@Query("select t from GroupDao t where t.parentId = :group_parent")
	Page<GroupDao> getGroupChild(@Param("group_parent") int id , Pageable pageable);

	@Query("select t from GroupDao t where t.parentId is not null")
	Page<GroupDao> findAll(Pageable pageable);
}
