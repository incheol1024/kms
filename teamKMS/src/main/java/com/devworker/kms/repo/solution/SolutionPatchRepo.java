package com.devworker.kms.repo.solution;

import com.devworker.kms.entity.solution.SolutionPatchDao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface SolutionPatchRepo  extends PagingAndSortingRepository<SolutionPatchDao, Long> {
	@Query ("select t from SolutionPatchDao t where t.menuId = :menuId")	
	Page<SolutionPatchDao> getPatch(@Param("menuId")long menuId, Pageable pageable);
}
