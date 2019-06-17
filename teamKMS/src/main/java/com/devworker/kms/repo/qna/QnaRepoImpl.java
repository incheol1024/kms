package com.devworker.kms.repo.qna;

import com.devworker.kms.component.BoardComponent;
import com.devworker.kms.entity.common.BoardDao;
import org.jooq.DSLContext;
import org.jooq.Record6;
import org.jooq.SelectConditionStep;
import org.jooq.generated.kms.tables.KmsQnaCode;
import org.jooq.types.UInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class QnaRepoImpl {

    @Autowired
    DSLContext context;

    @Autowired
    BoardComponent boardComponent;

    private KmsQnaCode kmsQnaCode = new KmsQnaCode();

    public Page<BoardDao> getPage(int menuId, Pageable pageable) {
        long totalCount = getCount(menuId);
        SelectConditionStep<Record6<UInteger, String, String, java.sql.Date, Date, Integer>> record =
                boardComponent.select()
                        .join(kmsQnaCode)
                        .on(boardComponent.boardIdEq(kmsQnaCode.BOARD_ID))
                        .where(kmsQnaCode.MENU_ID.eq(UInteger.valueOf(menuId)));

        List list = boardComponent.paging(boardComponent.sorting(record, pageable), pageable).fetchInto(BoardDao.class);

        return new PageImpl<>(list, pageable, totalCount);
    }

    private long getCount(int menuId) {
        return context
                .fetchCount(
                boardComponent
                        .select()
                        .join(kmsQnaCode)
                        .on(boardComponent.boardIdEq(kmsQnaCode.BOARD_ID))
                        .where(kmsQnaCode.MENU_ID.eq(UInteger.valueOf(menuId)))
        );
    }

}
