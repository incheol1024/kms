package com.devworker.kms.service.board;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devworker.kms.dao.UserDao;
import com.devworker.kms.dao.board.CommentDao;
import com.devworker.kms.repo.UserRepo;
import com.devworker.kms.repo.board.CommentRepo;
import com.devworker.kms.repo.board.DocRepo;
import com.devworker.kms.util.CommonUtil;

/**
 * @author Hwang In Cheol
 */

@Service
public class CommentService {

	@Autowired
	CommentRepo commentRepo;

	@Autowired
	DocRepo docRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	FileHandler fileHandler;

	public CommentDao addComment(CommentDao commentDao) throws Exception {
		String userId = CommonUtil.getCurrentUser();
		if (userId == null)
			throw new Exception();

		Optional<UserDao> optionalUserDao = userRepo.findById(userId);
		commentDao.setCmtUserId(optionalUserDao.get().getName());
		return commentRepo.save(commentDao);
	}

	public List<CommentDao> findByBoardId(Integer boardId) throws Exception {
		return commentRepo.findByBoardId(boardId);
	}

	public CommentDao updateComment(CommentDao commentDao) throws Exception {

		if (commentRepo.findById(commentDao.getCmtId()) == null)
			throw new Exception();

		CommentDao newCommentDao = commentRepo.save(commentDao);
		if (newCommentDao == null)
			throw new Exception();

		return newCommentDao;
	}

	public void deleteComment(CommentDao commentDao) {
		commentRepo.delete(commentDao);
	}

}
