package com.devworker.kms.repo.common;

import org.jooq.*;
import org.jooq.generated.kms.tables.KmsBoard;
import org.jooq.types.UInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

@Repository
public class BoardRepoImpl {
    @Autowired
    DSLContext context;

    KmsBoard table = KmsBoard.KMS_BOARD;

    public SelectJoinStep<Record6<UInteger, String, String, Date, Date, Integer>> select() {
        return context.select(table.BOARD_ID, table.SUBJECT, table.USER_ID, table.REG_DATE, table.UPD_DATE, table.HITS)
                .from(table);
    }

    public SelectJoinStep<Record7<UInteger, String, String, Date, Date, Integer, String>> selectWithContent() {
        return context.select(table.BOARD_ID, table.SUBJECT, table.USER_ID, table.REG_DATE, table.UPD_DATE, table.HITS, table.CONTENTS)
                .from(table);
    }

    public SelectWithTiesAfterOffsetStep paging(SelectSeekStepN step, Pageable pageable){
        return step.limit((int) pageable.getOffset(), pageable.getPageSize());
    }

    public SelectSeekStepN sorting(SelectConditionStep<Record6<UInteger, String, String, Date, Date, Integer>> step, Pageable pageable){
        return step.orderBy(getSortFields(pageable.getSort()));
    }

    public Condition boardIdEq(TableField field){
        return table.BOARD_ID.eq(field);
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
            case "regDate" : return table.REG_DATE;
            case "subject" : return table.SUBJECT;
            default: return table.BOARD_ID;
        }
    }
}