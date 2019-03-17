package com.devworker.kms.repo.site;

import com.devworker.kms.entity.site.ProjectDao;
import org.springframework.data.repository.PagingAndSortingRepository;

interface ProjectRepo  extends PagingAndSortingRepository<ProjectDao, Integer> {
}
