package com.devworker.kms.repo.board;

import org.springframework.data.repository.CrudRepository;
import com.devworker.kms.dao.board.CommentDao;

public interface CommentRepo extends CrudRepository<CommentDao, Integer> {

}
