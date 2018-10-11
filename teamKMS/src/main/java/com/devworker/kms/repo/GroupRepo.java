package com.devworker.kms.repo;

import org.springframework.data.repository.CrudRepository;

import com.devworker.kms.dao.GroupDao;

public interface GroupRepo extends CrudRepository<GroupDao, Integer>{

}
