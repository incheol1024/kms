package com.devworker.kms.repo.board;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devworker.kms.entity.board.BoardDao;

public interface BoardRepo extends JpaRepository<BoardDao, Long> {

}
