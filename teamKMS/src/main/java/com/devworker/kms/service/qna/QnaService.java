package com.devworker.kms.service.qna;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devworker.kms.dao.MenuDao;
import com.devworker.kms.dao.board.BoardDao;
import com.devworker.kms.dao.board.CommentDao;
import com.devworker.kms.dao.board.DocDao;
import com.devworker.kms.dao.board.QnaCodeDao;
import com.devworker.kms.dao.qna.QnaDao;
import com.devworker.kms.repo.MenuRepo;
import com.devworker.kms.repo.board.BoardRepo;
import com.devworker.kms.repo.board.CommentRepo;
import com.devworker.kms.repo.board.DocRepo;
import com.devworker.kms.repo.board.QnaCodeRepo;
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
	
	@Autowired
	QnaCodeRepo qnaCodeRepo;
	
	@Autowired
	MenuRepo menuRepo;

	/**
	 * @param menuId
	 * @return List<BoardDao> is return value, there is difference depending on menuId 
	 * 페이징 처리 구현이 되면 변경되어야 하는 코드입니다.
	 *
	 */
	public List<BoardDao> getFirstPageList(int menuId) {
		MenuDao menuDao = menuRepo.findById(menuId).get();
		List<QnaCodeDao> qnaList = qnaCodeRepo.findByMenuId(menuDao);
		List<BoardDao> boardList = new ArrayList<BoardDao>();
		for(QnaCodeDao qna : qnaList) {
			boardList.add(qna.getBoardId());
		}
		
		return boardList;
	}

	public BoardDao registerQuestion(BoardDao boardDao) {
		boardDao.setUserId(CommonUtil.getCurrentUser());
		return boardRepo.save(boardDao);
	}

	public Optional<BoardDao> findById(Integer id) {
		return boardRepo.findById(id);
	}

	public QnaDao findPostById(Integer id) {

		Optional<BoardDao> opBoardDao = boardRepo.findById(id);
			BoardDao boardDao = opBoardDao.get();
		return null;
	}

	public int AnswerQna(CommentDao commentDao, BoardDao boardId) {
		commentRepo.save(commentDao);
		return 0;

	}

}
