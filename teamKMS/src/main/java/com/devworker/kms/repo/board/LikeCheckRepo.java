package com.devworker.kms.repo.board;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devworker.kms.dao.board.LikeCheckDao;

public interface LikeCheckRepo extends JpaRepository<LikeCheckDao, Integer>{

	
}
