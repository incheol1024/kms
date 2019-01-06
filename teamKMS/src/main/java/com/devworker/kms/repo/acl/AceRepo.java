package com.devworker.kms.repo.acl;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.devworker.kms.dao.acl.AceDao;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AceRepo extends CrudRepository<AceDao, Long>{
    @Query("select t from AceDao t where t.aceId = :ace_Id ")
    List<AceDao> getAceList(@Param("ace_Id") String aceId);

    @Query("select t from AceDao t where t.aceId = :ace_Id and t.aclId = :acl_Id")
    AceDao getAce(@Param("acl_Id") String aclId,@Param("ace_Id") String aceId);
}
