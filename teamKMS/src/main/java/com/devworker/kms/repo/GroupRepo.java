package com.devworker.kms.repo;

import com.devworker.kms.dao.GroupDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface GroupRepo extends CrudRepository<GroupDao, Integer>{
	@Query("select t from GroupDao t where t.parentId = :group_parent")
	Page<GroupDao> getGroupChild(@Param("group_parent") int id , Pageable pageable);


}
