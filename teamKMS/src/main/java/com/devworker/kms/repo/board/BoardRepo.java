package com.devworker.kms.repo.board;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devworker.kms.dao.board.BoardDao;

public interface BoardRepo extends JpaRepository<BoardDao, Integer> {

}
