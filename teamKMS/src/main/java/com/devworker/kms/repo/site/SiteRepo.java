package com.devworker.kms.repo.site;

import com.devworker.kms.entity.site.SiteDao;
import org.springframework.data.repository.PagingAndSortingRepository;

interface SiteRepo extends PagingAndSortingRepository<SiteDao, Integer> {
}
