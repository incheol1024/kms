package com.devworker.kms.repo.common;

import com.devworker.kms.entity.common.BoardDao;
import org.jooq.DSLContext;
import org.jooq.generated.kms.tables.KmsBoard;
import org.jooq.generated.kms.tables.KmsComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardRepoImpl {
    @Autowired
    DSLContext context;
    
    public List<BoardDao> test() {
    	return context.select(KmsBoard.KMS_BOARD.BOARD_ID)
    			.from(KmsBoard.KMS_BOARD) 
    			.fetchInto(BoardDao.class);
    }
}