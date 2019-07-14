package com.devworker.kms.repo.solution;

import com.devworker.kms.component.BoardComponent;
import com.devworker.kms.entity.common.BoardDao;
import org.jooq.*;
import org.jooq.generated.kms.tables.KmsBoard;
import org.jooq.generated.kms.tables.KmsSolution;
import org.jooq.types.UInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class SolutionRepoImpl {
    @Autowired
    DSLContext context;
    @Autowired
    BoardComponent boardComponent;

    private KmsSolution kmsSolution = KmsSolution.KMS_SOLUTION;

    public Page<BoardDao> getPageList(int menuId, Pageable pageable) {
        long totalCount = getCount(menuId);

        SelectConditionStep<Record6<UInteger, String, String, Date, Date, Integer>> record = boardComponent.select()
                .join(kmsSolution).on(boardComponent.boardIdEq(kmsSolution.BOARD_ID))
                .where(kmsSolution.MENU_ID.eq(UInteger.valueOf(menuId)));

        List list = boardComponent.paging(boardComponent.sorting(record, pageable), pageable).fetchInto(BoardDao.class);
        return new PageImpl<BoardDao>(list, pageable, totalCount);
    }

    private long getCount(int menuId) {
        return context.fetchCount(
                boardComponent.select()
                .join(kmsSolution).on(boardComponent.boardIdEq(kmsSolution.BOARD_ID))
                .where(kmsSolution.MENU_ID.eq(UInteger.valueOf(menuId)))
        );
    }
}