package com.devworker.kms.repo.acl;

import org.springframework.data.repository.CrudRepository;

import com.devworker.kms.dao.acl.AclDao;

public interface AclRepo extends CrudRepository<AclDao, String>{

}
