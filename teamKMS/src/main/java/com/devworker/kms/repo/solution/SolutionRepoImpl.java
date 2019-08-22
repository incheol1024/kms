package com.devworker.kms.repo.solution;

import com.devworker.kms.component.board.BoardComponent;
import com.devworker.kms.entity.common.BoardDao;
import org.jooq.DSLContext;
import org.jooq.generated.kms.tables.KmsSolution;
import org.jooq.types.UInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

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

        List list = boardComponent.getBoardListBuilder().join(kmsSolution,kmsSolution.BOARD_ID).where(kmsSolution.MENU_ID.eq(UInteger.valueOf(menuId)))
            .sorting(pageable.getSort()).paging(pageable).fetchInto(BoardDao.class);
        return new PageImpl<>(list, pageable, totalCount);
    }

    private long getCount(int menuId) {
        return context.fetchCount(
                boardComponent.getBoardListBuilder().join(kmsSolution,kmsSolution.BOARD_ID)
                        .where(kmsSolution.MENU_ID.eq(UInteger.valueOf(menuId))).getStep()
        );
    }
}