package com.devworker.kms.repo.board;

import org.springframework.data.repository.CrudRepository;

import com.devworker.kms.dao.board.BoardDao;

public interface BoardRepo extends CrudRepository<BoardDao, Integer> {

}
