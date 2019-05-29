package com.devworker.kms.repo.solution;

import com.devworker.kms.entity.solution.SolutionDao;
import org.jooq.DSLContext;
import org.jooq.generated.kms.tables.KmsSolution;
import org.jooq.types.UInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SolutionRepoImpl {
    @Autowired
    DSLContext context;

    KmsSolution table = KmsSolution.KMS_SOLUTION;

    public List<SolutionDao> getPageList(int menuId, Pageable pageable) {
        return context.select(table.MENU_ID, table.BOARD_ID)
                .from(table)
                .where(table.MENU_ID.eq(UInteger.valueOf(menuId)))
                .limit(pageable.getPageSize())
                .offset((int)pageable.getOffset())
                .fetchInto(SolutionDao.class);
    }
}