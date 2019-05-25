package com.devworker.kms.repo.common;

import com.devworker.kms.entity.common.BoardDao;
import org.jooq.*;
import org.jooq.generated.kms.tables.KmsBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardRepoImpl {
    @Autowired
    DSLContext context;
    KmsBoard table = KmsBoard.KMS_BOARD;

    public Page<BoardDao> findAll(Pageable pageable) {
        List<BoardDao> list =
                context.select(table.BOARD_ID, table.SUBJECT, table.USER_ID, table.REG_DATE, table.UPD_DATE, table.HITS)
                        .from(table)
                        .limit((int) pageable.getOffset(), pageable.getPageSize())
                        .fetchInto(BoardDao.class);
        return new PageImpl(list, pageable, findCountByLikeExpression(null));
    }

    public Page<BoardDao> findAll(List<Long> pageList, Pageable pageable) {
        List<BoardDao> list =
                context.select(table.BOARD_ID, table.SUBJECT, table.USER_ID, table.REG_DATE, table.UPD_DATE, table.HITS)
                        .from(table)
                        .where(table.BOARD_ID.in(pageList))
                        .fetchInto(BoardDao.class);
        return new PageImpl(list, pageable, findCountByLikeExpression(table.BOARD_ID.in(pageList)));
    }

    private long findCountByLikeExpression(Condition condition) {
        if(condition == null)
            return context.fetchCount(context.select().from(table));
        return context.fetchCount(
                context.select().from(table).where(condition));
    }

}