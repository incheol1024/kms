package com.devworker.kms.repo.common;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.common.CommentDao;

public interface CommentRepo extends PagingAndSortingRepository<CommentDao, Long> {

	List<CommentDao> findByBoardId(BoardDao boardObj);

	Page<CommentDao> findByBoardIdEquals(BoardDao boardId, Pageable pageable);
}
