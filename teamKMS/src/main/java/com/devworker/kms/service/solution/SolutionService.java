package com.devworker.kms.service.solution;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.devworker.kms.dao.board.BoardDao;
import com.devworker.kms.dao.qna.QnaDao;
import com.devworker.kms.dao.solution.SolutionDao;
import com.devworker.kms.repo.board.BoardRepo;
import com.devworker.kms.repo.solution.SolutionRepo;
import com.devworker.kms.util.CommonUtil;

@Service
public class SolutionService {

	private Logger logger = LoggerFactory.getLogger(SolutionService.class);
	
	SolutionRepo solutionRepo;
	
	BoardRepo boardRepo;
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

	public SolutionDao findPostById(Integer id) {
		// return boardRepo.findById(id);

		Optional<BoardDao> opBoardDao = boardRepo.findById(id);
		try {
			BoardDao boardDao = opBoardDao.get();
			
		} catch (NoSuchElementException e) {

		}

		return null;
	}
}
