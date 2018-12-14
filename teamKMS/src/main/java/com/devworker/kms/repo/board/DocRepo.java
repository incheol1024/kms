package com.devworker.kms.repo.board;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

<<<<<<< HEAD
import com.devworker.kms.dao.board.BoardDao;
=======
>>>>>>> branch 'master' of https://github.com/incheol1024/kns.git
import com.devworker.kms.dao.board.CommentDao;
import com.devworker.kms.dao.board.DocDao;

public interface DocRepo extends CrudRepository<DocDao, Integer> {

<<<<<<< HEAD
	List<DocDao> findByBoardId(BoardDao boardObj);
=======
	List<DocDao> findByBoardId(int boardId);
>>>>>>> branch 'master' of https://github.com/incheol1024/kns.git
	
}
