package com.devworker.kms.repo.solution;

import java.util.List;
import org.jooq.DSLContext;
import org.jooq.generated.kms.tables.KmsBoard;	
import org.jooq.generated.kms.tables.KmsSolution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devworker.kms.entity.common.BoardDao;

@Repository
public class SolutionRepoImpl {
    @Autowired
    DSLContext context;
    
    public List<BoardDao> getSolutionList(@Param("solution") String solution , Pageable pageable) {
    	return (List<BoardDao>) context.select(KmsBoard.KMS_BOARD.BOARD_ID, KmsBoard.KMS_BOARD.SUBJECT, KmsBoard.KMS_BOARD.USER_ID, KmsBoard.KMS_BOARD.HITS, KmsBoard.KMS_BOARD.HITS, KmsBoard.KMS_BOARD.REG_DATE)
    			.from(KmsBoard.KMS_BOARD, KmsSolution.KMS_SOLUTION )
    			.where(KmsBoard.KMS_BOARD.BOARD_ID.eq(KmsSolution.KMS_SOLUTION.BOARD_ID))
    			.and(KmsSolution.KMS_SOLUTION.SOLUTION.like(solution, '%'))
    			.fetchInto(BoardDao.class);
    }
    
    public List<BoardDao> getetcList(Pageable pageable) {
    	return (List<BoardDao>) context.select(KmsBoard.KMS_BOARD.BOARD_ID, KmsBoard.KMS_BOARD.SUBJECT, KmsBoard.KMS_BOARD.USER_ID, KmsBoard.KMS_BOARD.HITS, KmsBoard.KMS_BOARD.HITS, KmsBoard.KMS_BOARD.REG_DATE)
    			.from(KmsBoard.KMS_BOARD, KmsSolution.KMS_SOLUTION )
    			.where(KmsBoard.KMS_BOARD.BOARD_ID.eq(KmsSolution.KMS_SOLUTION.BOARD_ID))
    			.and(KmsSolution.KMS_SOLUTION.SOLUTION.notLike("ECM"))
    			.and(KmsSolution.KMS_SOLUTION.SOLUTION.notLike("EDM"))
    			.fetchInto(BoardDao.class);
    }
    
    
}