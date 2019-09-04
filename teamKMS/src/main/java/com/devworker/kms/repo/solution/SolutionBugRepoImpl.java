package com.devworker.kms.repo.solution;

import com.devworker.kms.component.board.BoardComponent;
import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.solution.SolutionBugDao;
import org.jooq.DSLContext;
import org.jooq.generated.kms.tables.KmsSolution;
import org.jooq.generated.kms.tables.KmsSolutionBug;
import org.jooq.types.UInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SolutionBugRepoImpl{
	@Autowired
    DSLContext context;
    @Autowired
    BoardComponent boardComponent;

    private KmsSolution kmsSolution = KmsSolution.KMS_SOLUTION;
    private KmsSolutionBug kmsSolutionBug = KmsSolutionBug.KMS_SOLUTION_BUG;
    
    public Page<BoardDao> getPageList(Long menuId, Pageable pageable) {
        long totalCount = getCount(menuId);

        List list = boardComponent.getBoardListBuilder()
                .join(kmsSolution,kmsSolution.BOARD_ID)
                .where(kmsSolution.MENU_ID.eq(UInteger.valueOf(menuId)))
                .sorting(pageable.getSort())
                .paging(pageable)
                .fetchInto(BoardDao.class);
       return new PageImpl<>(list, pageable, totalCount);
    }
    
    public Page<SolutionBugDao> getPageBugList(Long menuId, Pageable pageable) {
        long totalCount = getBugCount(menuId);

        List list = boardComponent.getBoardListBuilder(kmsSolutionBug.MANAGER, kmsSolutionBug.COMPLETED)
                .join(kmsSolutionBug,kmsSolutionBug.BOARD_ID)
                .where(kmsSolutionBug.MENU_ID.eq(UInteger.valueOf(menuId)))
                .sorting(pageable.getSort())
                .paging(pageable)
                .fetchInto(SolutionBugDao.class);
       return new PageImpl<>(list, pageable, totalCount);
    }
    
    private long getCount(Long menuId) {
        return context.fetchCount(
                boardComponent.getBoardListBuilder()
                        .join(kmsSolution,kmsSolution.BOARD_ID)
                        .where(kmsSolution.MENU_ID.eq(UInteger.valueOf(menuId))).getStep()
        );
    }

    private long getBugCount(Long menuId){
        return context.fetchCount(
                boardComponent.getBoardListBuilder(kmsSolutionBug.MANAGER, kmsSolutionBug.COMPLETED)
                        .join(kmsSolutionBug,kmsSolutionBug.BOARD_ID)
                        .where(kmsSolutionBug.MENU_ID.eq(UInteger.valueOf(menuId))).getStep()
        );
    }
}

