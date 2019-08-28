package com.devworker.kms.repo.solution;

import com.devworker.kms.entity.solution.SolutionSiteDao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface SolutionSiteRepo  extends PagingAndSortingRepository<SolutionSiteDao, Long> {
	@Query ("select t from SolutionSiteDao t where t.menuId = :menuId")	
	Page<SolutionSiteDao> getSite(@Param("menuId")long menuId, Pageable pageable);

}
