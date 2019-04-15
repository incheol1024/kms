package com.devworker.kms.repo.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.common.CommentDao;

public interface CommentRepo extends JpaRepository<CommentDao, Integer> {

	List<CommentDao> findByBoardId(BoardDao boardObj);
}
