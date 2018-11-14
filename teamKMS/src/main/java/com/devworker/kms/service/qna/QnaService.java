package com.devworker.kms.service.qna;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devworker.kms.dao.board.BoardDao;
import com.devworker.kms.dao.board.CommentDao;
import com.devworker.kms.dao.board.DocDao;
import com.devworker.kms.dao.qna.QnaDao;
import com.devworker.kms.repo.board.BoardRepo;
import com.devworker.kms.repo.board.CommentRepo;
import com.devworker.kms.repo.board.DocRepo;
import com.devworker.kms.repo.qna.QnaRepo;
import com.devworker.kms.util.CommonUtil;

@Service
public class QnaService {

	private Logger logger = LoggerFactory.getLogger(QnaService.class);

	@Autowired
	QnaRepo qnaRepo;

	@Autowired
	BoardRepo boardRepo;

	@Autowired
	CommentRepo commentRepo;

	@Autowired
	DocRepo docRepo;

	public List<BoardDao> getFirstPageList() {
		return (List<BoardDao>) boardRepo.findAll();
	}

	public BoardDao registerQuestion(BoardDao boardDao) {
		boardDao.setUserId(CommonUtil.getCurrentUser());
		return boardRepo.save(boardDao);
	}

	public Optional<BoardDao> findById(Integer id) {
		return boardRepo.findById(id);
	}

	public QnaDao findPostById(Integer id) {
		// return boardRepo.findById(id);

		Optional<BoardDao> opBoardDao = boardRepo.findById(id);
		try {
			BoardDao boardDao = opBoardDao.get();
			
		} catch (NoSuchElementException e) {

		}

		return null;
	}

	public int AnswerQna(CommentDao commentDao, BoardDao boardId) {
		commentRepo.save(commentDao);
		return 0;

	}

	public void QnaTest() {
		BoardDao board = new BoardDao();
		board.setContents("aaaaaaaaaaa");
		board.setSubject("aaaaaaaa");
		board.setUserId("aaaasaaa");

		if (null == boardRepo.save(board)) {
			System.out.println("save is failed");
		} else {
			System.out.println(" save is success");
		}

	}
}
