package com.devworker.kms.repo.solution;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jooq.types.UInteger;

import org.jooq.*;
import org.jooq.generated.kms.tables.KmsBoard;
import org.jooq.generated.kms.tables.KmsSolutionBug;
import org.jooq.generated.kms.tables.KmsSolution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.devworker.kms.component.BoardComponent;
import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.solution.SolutionBugDao;

@Repository
public class SolutionBugRepoImpl{
	@Autowired
    DSLContext context;
    @Autowired
    BoardComponent boardComponent;

    private KmsBoard kmsBoard = KmsBoard.KMS_BOARD;
    private KmsSolution kmsSolution = KmsSolution.KMS_SOLUTION;
    private KmsSolutionBug kmsSolutionBug = KmsSolutionBug.KMS_SOLUTION_BUG;
    
    public Page<BoardDao> getPageList(int menuId, Pageable pageable) {
        long totalCount = getCount(menuId);

        SelectConditionStep<Record8<UInteger, String, String, String, String, Date, Date, Integer>> record = 
       		 context.select(kmsBoard.BOARD_ID, kmsBoard.SUBJECT, kmsBoard.USER_ID, 
       				 kmsSolutionBug.MANAGER, kmsSolutionBug.COMPLETED,
       				 kmsBoard.REG_DATE, kmsBoard.UPD_DATE, kmsBoard.HITS )
       		 	.from(kmsSolutionBug)
       		 	.join(kmsBoard).on(kmsSolutionBug.BOARD_ID.eq(kmsBoard.BOARD_ID))
       		 	.where(kmsSolutionBug.MENU_ID.eq(UInteger.valueOf(menuId)));

       List list = this.paging(this.sorting(record, pageable), pageable).fetchInto(BoardDao.class);
       
       return new PageImpl<BoardDao>(list, pageable, totalCount);
    }
    
    public Page<SolutionBugDao> getPageBugList(int menuId, Pageable pageable) {
        long totalCount = getCount(menuId);

        SelectConditionStep<Record8<UInteger, String, String, String, String, Date, Date, Integer>> record = 
       		 context.select(kmsBoard.BOARD_ID, kmsBoard.SUBJECT, kmsBoard.USER_ID, 
       				 kmsSolutionBug.MANAGER, kmsSolutionBug.COMPLETED,
       				 kmsBoard.REG_DATE, kmsBoard.UPD_DATE, kmsBoard.HITS )
       		 	.from(kmsSolutionBug)
       		 	.join(kmsBoard).on(kmsSolutionBug.BOARD_ID.eq(kmsBoard.BOARD_ID))
       		 	.where(kmsSolutionBug.MENU_ID.eq(UInteger.valueOf(menuId)));

       List list = this.paging(this.sorting(record, pageable), pageable).fetchInto(SolutionBugDao.class);
       
       return new PageImpl<SolutionBugDao>(list, pageable, totalCount);
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
    
    public SelectWithTiesAfterOffsetStep paging(SelectSeekStepN step, Pageable pageable){
        return step.limit((int) pageable.getOffset(), pageable.getPageSize());
    }
    
    public SelectSeekStepN sorting(SelectConditionStep<Record8<UInteger, String, String, String, String, Date, Date, Integer>> step, Pageable pageable){
        return step.orderBy(getSortFields(pageable.getSort()));
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

