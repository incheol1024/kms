package com.devworker.kms.repo.acl;

import com.devworker.kms.dao.acl.AceDao;
import com.devworker.kms.dto.acl.AceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AceRepo extends PagingAndSortingRepository<AceDao,String> {
    @Query("select t from AceDao t where t.aclId = :aclId")
    Page<AceDao> findByAclId(String aclId, Pageable pageable);

    @Query("select t from AceDao t where t.aceId = :aceId")
    List<AceDao> findByAceId(String aceId);
}
