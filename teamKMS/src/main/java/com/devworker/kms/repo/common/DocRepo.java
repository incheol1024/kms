package com.devworker.kms.repo.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.common.CommentDao;
import com.devworker.kms.entity.common.DocDao;

public interface DocRepo extends JpaRepository<DocDao, Long> {

//	List<DocDao> findByBoardId(BoardDao boardDao);

}
