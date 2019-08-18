package com.devworker.kms.repo.site;

import com.devworker.kms.component.board.BoardComponent;
import com.devworker.kms.entity.common.BoardDao;
import org.jooq.DSLContext;
import org.jooq.Record6;
import org.jooq.SelectConditionStep;
import org.jooq.generated.kms.tables.KmsProjectBoard;
import org.jooq.types.UInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class ProjectBoardRepoImpl {
    @Autowired
    DSLContext context;
    @Autowired
    BoardComponent boardComponent;

    private KmsProjectBoard kmsProjectBoard = KmsProjectBoard.KMS_PROJECT_BOARD;

    public Page<BoardDao> getPageList(int projectId, Pageable pageable) {
        long totalCount = getCount(projectId);

        List list =  boardComponent.getBoardListBuilder()
                .join(kmsProjectBoard,kmsProjectBoard.BOARD_ID)
                .where(kmsProjectBoard.PROJECT_ID.eq(UInteger.valueOf(projectId)))
                .sorting(pageable)
                .paging(pageable)
                .fetchInto(BoardDao.class);
        return new PageImpl(list, pageable, totalCount);
    }

    private long getCount(int projectId) {
        return context.fetchCount(
                boardComponent.getBoardListBuilder()
                .join(kmsProjectBoard,kmsProjectBoard.BOARD_ID)
                .where(kmsProjectBoard.PROJECT_ID.eq(UInteger.valueOf(projectId))).getStep()
        );
    }
}