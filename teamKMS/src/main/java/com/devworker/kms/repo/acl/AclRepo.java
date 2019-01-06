package com.devworker.kms.repo.acl;

import org.springframework.data.repository.CrudRepository;

import com.devworker.kms.dao.acl.AclDao;

import java.util.List;

public interface AclRepo extends CrudRepository<AclDao, String>{

    @Override
    List<AclDao> findAll();
}
