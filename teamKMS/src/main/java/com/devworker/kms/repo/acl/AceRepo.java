package com.devworker.kms.repo.acl;

import com.devworker.kms.dao.acl.AceDao;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AceRepo extends PagingAndSortingRepository<AceDao,String> {
    @Query("select t from AceDao t where t.aclId = :aclId")
    Page<AceDao> findByAclId(@Param("aclId") String aclId, Pageable pageable);

    @Query("select t from AceDao t where t.aceId = :aceId")
    List<AceDao> findByAceId(@Param("aceId") String aceId);
}
