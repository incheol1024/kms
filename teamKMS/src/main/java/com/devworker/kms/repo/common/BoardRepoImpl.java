package com.devworker.kms.repo.common;

import org.jooq.generated.kms.tables.KmsBoard;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.types.UInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.devworker.kms.entity.common.BoardDao;

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