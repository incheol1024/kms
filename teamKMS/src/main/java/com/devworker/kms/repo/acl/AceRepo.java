package com.devworker.kms.repo.acl;

import com.devworker.kms.dao.acl.AceDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AceRepo extends PagingAndSortingRepository<AceDao,String> {
    Page<AceDao> findByAclId(String aclId, Pageable pageable);
}
