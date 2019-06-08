package com.devworker.kms.repo.solution;

import com.devworker.kms.entity.common.BoardDao;
import org.jooq.DSLContext;
import org.jooq.SortField;
import org.jooq.TableField;
import org.jooq.generated.kms.tables.KmsBoard;
import org.jooq.generated.kms.tables.KmsSolution;
import org.jooq.types.UInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class SolutionRepoImpl {
    @Autowired
    DSLContext context;

    private KmsSolution kmsSolution = KmsSolution.KMS_SOLUTION;
    private KmsBoard kmsBoard = KmsBoard.KMS_BOARD;

    public Page<BoardDao> getPageList(int menuId, Pageable pageable) {
        long totalCount = getCount(menuId);

        List<BoardDao> boardDaos = context.select(kmsBoard.BOARD_ID, kmsBoard.SUBJECT, kmsBoard.USER_ID, kmsBoard.REG_DATE, kmsBoard.UPD_DATE, kmsBoard.HITS)
                .from(kmsBoard)
                .join(kmsSolution).on(kmsBoard.BOARD_ID.eq(kmsSolution.BOARD_ID))
                .where(kmsSolution.MENU_ID.eq(UInteger.valueOf(menuId)))
                .orderBy(getSortFields(pageable.getSort()))
                .limit(pageable.getPageSize())
                .offset((int) pageable.getOffset())
                .fetchInto(BoardDao.class);

        return new PageImpl(boardDaos, pageable, totalCount);
    }

    private long getCount(int menuId) {
        return context.fetchCount(context.select(kmsSolution.MENU_ID, kmsSolution.BOARD_ID)
                .from(kmsBoard)
                .join(kmsSolution).on(kmsBoard.BOARD_ID.eq(kmsSolution.BOARD_ID))
                .where(kmsSolution.MENU_ID.eq(UInteger.valueOf(menuId))));
    }

    private Collection<SortField<?>> getSortFields(Sort sortSpecification) {
        Collection<SortField<?>> querySortFields = new ArrayList<>();
        for (Sort.Order specifiedField : sortSpecification) {
            String sortFieldName = specifiedField.getProperty();
            Sort.Direction sortDirection = specifiedField.getDirection();
            TableField tableField = getTableField(sortFieldName);
            SortField<?> querySortField = sortDirection == Sort.Direction.ASC ? tableField.asc() : tableField.desc();
            querySortFields.add(querySortField);
        }
        return querySortFields;
    }

    private TableField getTableField(String sortFieldName) {
        switch (sortFieldName){
            case "regDate" : return kmsBoard.REG_DATE;
            case "subject" : return kmsBoard.SUBJECT;
            default: return kmsBoard.BOARD_ID;
        }
    }
}