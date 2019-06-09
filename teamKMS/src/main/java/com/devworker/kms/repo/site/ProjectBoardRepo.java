package com.devworker.kms.repo.site;

import com.devworker.kms.entity.site.ProjectBoardDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProjectBoardRepo  extends PagingAndSortingRepository<ProjectBoardDao, Long> {
    @Query("select t from ProjectBoardDao t where t.projectId = :projectId")
    Page<ProjectBoardDao> getSiteProjects(@Param("projectId") int projectId,Pageable pageable);
    @Query("select t from ProjectBoardDao t where t.boardId =:boardId")
    Optional<ProjectBoardDao> findByBoardId(@Param("boardId") Long id);
}
