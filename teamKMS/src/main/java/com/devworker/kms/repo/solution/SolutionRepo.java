package com.devworker.kms.repo.solution;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.solution.SolutionDao;

public interface SolutionRepo extends CrudRepository<SolutionDao, Long>{

	@Query("select b from BoardDao b, SolutionDao s where s.boardId = b.boardId and s.solution like :solution%")
	Page<BoardDao> getSolutionList(@Param("solution") String solution, Pageable pageable);
	

	@Query("select b from BoardDao b, SolutionDao s where s.boardId = b.boardId and s.solution != 'ECM' and s.solution != 'EDM'")
	Page<BoardDao> getetcList(Pageable pageable);
}
