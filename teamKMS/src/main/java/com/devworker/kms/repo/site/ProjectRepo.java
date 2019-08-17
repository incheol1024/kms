package com.devworker.kms.repo.site;

import com.devworker.kms.entity.site.ProjectDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProjectRepo  extends PagingAndSortingRepository<ProjectDao, Integer> {
    @Query ("select t from ProjectDao t where t.siteId = :siteId" )
    Page<ProjectDao> getSiteProjects(@Param("siteId") int siteId, Pageable pageable);
}
