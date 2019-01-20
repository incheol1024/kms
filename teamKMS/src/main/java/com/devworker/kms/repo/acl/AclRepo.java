package com.devworker.kms.repo.acl;

import com.devworker.kms.dao.acl.AclDao;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AclRepo extends PagingAndSortingRepository<AclDao, String> {
}
