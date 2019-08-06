package com.devworker.kms.repo.solution;

import java.sql.Date;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record6;
import org.jooq.Record9;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.types.UInteger;

import org.jooq.*;
import org.jooq.generated.kms.tables.KmsBoard;
import org.jooq.generated.kms.tables.KmsSolutionBug;
import org.jooq.generated.kms.tables.KmsSolution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.devworker.kms.component.BoardComponent;
import com.devworker.kms.entity.common.BoardDao;

@Repository
public class SolutionBugRepoImpl{
	@Autowired
    DSLContext context;
    @Autowired
    BoardComponent boardComponent;

    private KmsSolution kmsSolution = KmsSolution.KMS_SOLUTION;
    private KmsSolutionBug kmsSolutionBug = KmsSolutionBug.KMS_SOLUTION_BUG;
   
    public Page<BoardDao> getPageList(int menuId, Pageable pageable) {
        long totalCount = getCount(menuId);

        SelectConditionStep<Record6<UInteger, String, String, Date, Date, Integer>> record = boardComponent.select()
                .join(kmsSolution).on(boardComponent.boardIdEq(kmsSolution.BOARD_ID))
                .join(kmsSolutionBug).on(boardComponent.boardIdEq(kmsSolutionBug.BOARD_ID))
                .where(kmsSolution.MENU_ID.eq(UInteger.valueOf(menuId)))
                .and(boardComponent.boardIdEq(kmsSolutionBug.BOARD_ID))
                ;

        List list = boardComponent.paging(boardComponent.sorting(record, pageable), pageable).fetchInto(BoardDao.class);
        return new PageImpl<BoardDao>(list, pageable, totalCount);
    }
    
    private long getCount(int menuId) {
        return context.fetchCount(
                boardComponent.select()
                .join(kmsSolution).on(boardComponent.boardIdEq(kmsSolution.BOARD_ID))
                .join(kmsSolutionBug).on(boardComponent.boardIdEq(kmsSolutionBug.BOARD_ID))
                .where(kmsSolution.MENU_ID.eq(UInteger.valueOf(menuId)))
                .and(boardComponent.boardIdEq(kmsSolutionBug.BOARD_ID))
        );
    }
}

