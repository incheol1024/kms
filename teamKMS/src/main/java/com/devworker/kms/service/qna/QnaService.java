package com.devworker.kms.service.qna;

import java.time.LocalDateTime;

import java.util.Optional;

import javax.transaction.Transactional;

import com.devworker.kms.component.board.BoardComponent;
import com.devworker.kms.dic.PermissionType;
import com.devworker.kms.dto.common.BoardDetailDto;
import com.devworker.kms.dto.common.BoardDto;
import com.devworker.kms.exception.NotExistException;
import com.devworker.kms.repo.qna.QnaRepoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.qna.QnaCodeDao;
import com.devworker.kms.repo.MenuRepo;
import com.devworker.kms.repo.common.BoardRepo;
import com.devworker.kms.repo.qna.QnaCodeRepo;
import com.devworker.kms.util.CommonUtil;

@Service
public class QnaService {

	private Logger logger = LoggerFactory.getLogger(QnaService.class);
	
	@Autowired
	BoardRepo boardRepo;

	@Autowired
	QnaCodeRepo qnaCodeRepo;
	
	@Autowired
	MenuRepo menuRepo;

	@Autowired
	QnaRepoImpl qnaRepo;

	@Autowired
	BoardComponent boardComponent;

/**
 * Qna paging Service code
 * @param menuId
 * @param pageable
*/
	public Page<BoardDto> getQnaPage(int menuId, Pageable pageable) {
		Page<BoardDao> boardDaoPage = qnaRepo.getPage(menuId, pageable);
		return boardDaoPage.map((boardDao)-> boardDao.getBoardDto());
	}

/**
 *
*/
	@Transactional
	public BoardDetailDto createQuestion(BoardDao boardDao, int menuId) {
		boardDao.setUserId(CommonUtil.getCurrentUser());
		boardDao.setRegDate(LocalDateTime.now());
		boardDao.setUpdDate(LocalDateTime.now());

		BoardDao savedBoardDao = boardRepo.save(boardDao);

		QnaCodeDao qnaCodeDao = new QnaCodeDao();
		qnaCodeDao.setBoardId(boardRepo.save(savedBoardDao));
		qnaCodeDao.setMenuId(menuRepo.findById(menuId).get());
		qnaCodeRepo.save(qnaCodeDao);
		return boardComponent.getBoard(boardDao.getBoardId(), PermissionType.CREATEQNA);
	}

	public BoardDetailDto findById(Long id) {
//		BoardDao boardDao = boardRepo.findById(id).get();
//		boardDao.setHits(1);
		Optional<QnaCodeDao> qnaCodeDaoOptional = qnaCodeRepo.findByBoardIdEquals(new BoardDao(id));
		qnaCodeDaoOptional.orElseThrow(() -> new NotExistException("Not Exist Qna board id=" + id));
		return boardComponent.getBoard(id, PermissionType.MODIFYQNA);
	}


    public void deleteQuestion(long id) {
		BoardDao boardDao = new BoardDao();
		boardDao.setBoardId(id);
		boardRepo.delete(boardDao);
    }
}
