package com.devworker.kms.repo;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.devworker.kms.dao.GroupDao;

public interface GroupRepo extends CrudRepository<GroupDao, Integer>{
	@Query("select t from GroupDao t where t.parentid = :group_parent")
	List<GroupDao> getGroupChild(@Param("group_parent") int id ,Sort s);


}
