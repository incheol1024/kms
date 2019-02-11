package com.devworker.kms.repo.board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devworker.kms.entity.board.BoardDao;
import com.devworker.kms.entity.board.CommentDao;

public interface CommentRepo extends JpaRepository<CommentDao, Integer> {

	List<CommentDao> findByBoardId(BoardDao boardObj);
}
