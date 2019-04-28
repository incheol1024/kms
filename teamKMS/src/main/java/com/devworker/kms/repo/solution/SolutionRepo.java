package com.devworker.kms.repo.solution;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.solution.SolutionDao;

public interface SolutionRepo extends CrudRepository<SolutionDao, Long>{

	@Query("select b from BoardDao b, SolutionDao s where s.board_id = b.boardId and s.solution like :solution%")
	List<BoardDao> getSolutionList(@Param("solution") String solution);
	

	@Query("select b from BoardDao b, SolutionDao s where s.board_id = b.boardId and s.solution != 'ECM' and s.solution != 'EDM'")
	List<BoardDao> getetcList();
}
