package com.devworker.kms.repo.board;

import org.springframework.data.repository.CrudRepository;

import com.devworker.kms.dao.board.DocDao;

public interface DocRepo extends CrudRepository<DocDao, Integer> {

}
