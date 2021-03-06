package com.devworker.kms.repo.solution;

import com.devworker.kms.entity.solution.SolutionBugDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface SolutionBugRepo extends PagingAndSortingRepository<SolutionBugDao, Long> {
	@Query ("select t from SolutionBugDao t where t.menuId = :menuId")	
    Page<SolutionBugDao> getBug(@Param("menuId")long menuId, Pageable pageable);
}
