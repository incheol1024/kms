package com.devworker.kms.component.board;

import org.jooq.*;
import org.jooq.generated.kms.tables.KmsBoard;
import org.jooq.impl.TableImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Collection;

public class BoardBuilder {
    private Select step;
    private KmsBoard board = KmsBoard.KMS_BOARD;

    BoardBuilder(SelectJoinStep select) {
        this.step = select;
    }

    public Select getStep(){
        return step;
    }

    public BoardBuilder join(TableImpl table, TableField field) {
        step = ((SelectJoinStep) step).join(table).on(board.BOARD_ID.eq(field));
        return this;
    }

    public BoardBuilder where(Condition condition) {
        if (step instanceof SelectJoinStep)
            step = ((SelectJoinStep) step).where(condition);
        else if (step instanceof SelectWhereStep)
            step = ((SelectWhereStep) step).where(condition);
        return this;
    }

    public BoardBuilder sorting(Sort sort) {
        return sorting(sort,null);
    }

    public BoardBuilder sorting(Sort sort,TableField field) {
        if (step instanceof SelectJoinStep)
            step = ((SelectJoinStep) step).orderBy(getSortFields(sort,field));
        else if (step instanceof SelectWhereStep)
            step = ((SelectWhereStep) step).orderBy(getSortFields(sort,field));
        return this;
    }

    public SelectWithTiesAfterOffsetStep paging(Pageable pageable) {
        if (step instanceof SelectJoinStep)
            return ((SelectJoinStep) step).limit((int) pageable.getOffset(), pageable.getPageSize());
        else if (step instanceof SelectWhereStep)
            return ((SelectWhereStep) step).limit((int) pageable.getOffset(), pageable.getPageSize());
        else
            return ((SelectSeekStepN) step).limit((int) pageable.getOffset(), pageable.getPageSize());
    }

    private Collection<SortField<?>> getSortFields(Sort sortSpecification, TableField field) {
        Collection<SortField<?>> querySortFields = new ArrayList<>();
        for (Sort.Order specifiedField : sortSpecification) {
            String sortFieldName = specifiedField.getProperty();
            Sort.Direction sortDirection = specifiedField.getDirection();
            TableField target = field;
            if(target == null)
                target = getTableField(sortFieldName);
            SortField<?> querySortField = sortDirection == Sort.Direction.ASC ? target.asc() : target.desc();
            querySortFields.add(querySortField);
        }
        return querySortFields;
    }

    private TableField getTableField(String sortFieldName) {
        switch (sortFieldName) {
            case "regDate":
                return board.REG_DATE;
            case "subject":
                return board.SUBJECT;
            default:
                return board.BOARD_ID;
        }
    }
}
