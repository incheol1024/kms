package com.devworker.kms.repo.board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devworker.kms.entity.board.BoardDao;
import com.devworker.kms.entity.board.DocDao;

public interface DocRepo extends JpaRepository<DocDao, Integer> {

	List<DocDao> findByBoardId(BoardDao boardDao);

}
