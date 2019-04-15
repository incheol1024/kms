package com.devworker.kms.repo.common;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devworker.kms.entity.common.BoardDao;

public interface BoardRepo extends JpaRepository<BoardDao, Long> {

}
