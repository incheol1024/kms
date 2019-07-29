package com.devworker.kms.repo.solution;

import com.devworker.kms.dto.solution.SolutionBugDto;
import com.devworker.kms.entity.solution.SolutionBugDao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface SolutionBugRepo extends PagingAndSortingRepository<SolutionBugDao, Integer> {
//	@Query ("select b from BoardDao b, SolutionBugDao t where b.board_id = t.board_id")
//	@Query ("select b from BoardDao b")	
	@Query ("select t from SolutionBugDao t")	
    Page<SolutionBugDao> getBug(@Param("menuId")int menuId, Pageable pageable);
}
