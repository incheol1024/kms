package com.devworker.kms.service.qna;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devworker.kms.entity.MenuDao;
import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.common.CommentDao;
import com.devworker.kms.entity.qna.QnaCodeDao;
import com.devworker.kms.entity.qna.QnaDao;
import com.devworker.kms.repo.MenuRepo;
import com.devworker.kms.repo.common.BoardRepo;
import com.devworker.kms.repo.common.CommentRepo;
import com.devworker.kms.repo.common.DocRepo;
import com.devworker.kms.repo.qna.QnaCodeRepo;
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
		
		List<QnaCodeDao> qnaCodeList = qnaCodeRepo.findByMenuId(menuDao);
	
		List<BoardDao> boardList = new ArrayList<>();

		if(!qnaCodeList.isEmpty())
			logger.debug("boardDao.toString() {}" + qnaCodeList.get(0).toString());
		for(QnaCodeDao qna : qnaCodeList) {
			logger.debug("QnaCOdeDao 조회 BoardDao 리스트 {}", qna.getBoardId());
			boardList.add(qna.getBoardId());
		}
		
		return boardList;
		
		//return null;
	}

	@Transactional
	public BoardDao createQuestion(BoardDao boardDao, int menuId) {
		boardDao.setUserId(CommonUtil.getCurrentUser());
		boardDao.setRegDate(LocalDateTime.now());
		boardDao.setUpdDate(LocalDateTime.now());
		BoardDao savedBoardDao = boardRepo.save(boardDao);
		MenuDao menuDao = menuRepo.findById(menuId).get();
		QnaCodeDao qnaCodeDao = new QnaCodeDao();
		qnaCodeDao.setBoardId(boardDao);
		qnaCodeDao.setMenuId(menuDao);
		qnaCodeRepo.save(qnaCodeDao);
		return savedBoardDao;
	}

	public BoardDao findById(Long id) {
		BoardDao boardDao = boardRepo.findById(id).get();
		boardDao.setHits(1);
		return boardRepo.save(boardDao);
	}

	public QnaDao findPostById(Long id) {

		Optional<BoardDao> opBoardDao = boardRepo.findById(id);
			BoardDao boardDao = opBoardDao.get();
		return null;
	}

	public int AnswerQna(CommentDao commentDao, BoardDao boardId) {
		commentRepo.save(commentDao);
		return 0;

	}

}
