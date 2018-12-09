package com.devworker.kms.repo.acl;

import org.springframework.data.repository.CrudRepository;

import com.devworker.kms.dao.acl.AceDao;

public interface AceRepo extends CrudRepository<AceDao, String>{

}
