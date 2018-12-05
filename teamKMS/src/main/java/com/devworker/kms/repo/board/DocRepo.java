package com.devworker.kms.repo.board;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.devworker.kms.dao.board.BoardDao;
import com.devworker.kms.dao.board.CommentDao;
import com.devworker.kms.dao.board.DocDao;

public interface DocRepo extends CrudRepository<DocDao, Integer> {

	List<DocDao> findByBoardId(BoardDao boardObj);
	
}
