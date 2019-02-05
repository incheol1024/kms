package com.devworker.kms.service.solution;

<<<<<<< HEAD
=======
import java.time.LocalDate;
>>>>>>> branch 'master' of https://github.com/incheol1024/kns.git
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devworker.kms.dao.board.BoardDao;
import com.devworker.kms.dao.solution.SolutionDao;
import com.devworker.kms.repo.board.BoardRepo;
import com.devworker.kms.repo.solution.SolutionRepo;
import com.devworker.kms.util.CommonUtil;

@Service
public class SolutionService {

	private Logger logger = LoggerFactory.getLogger(SolutionService.class);
	
	@Autowired
	SolutionRepo solutionRepo;
	
	@Autowired
	BoardRepo boardRepo;
	
	public List<BoardDao> getFirstPageList() {
		return (List<BoardDao>) boardRepo.findAll();
	}

	public BoardDao registerSolution(BoardDao boardDao) {
		boardDao.setUserId(CommonUtil.getCurrentUser());
		boardDao.setRegDate(LocalDateTime.now());
		boardDao.setUpdDate(LocalDateTime.now());
		
//		boardDao.setBoardId(boardId);
		boardDao.setSubject("subject");
		boardDao.setContents("contents");
		
		boardDao.setHits(0);
		return boardRepo.save(boardDao);
	}

	public Optional<BoardDao> findById(Integer id) {
		return boardRepo.findById(id);
	}

	public SolutionDao findPostById(Integer id) {
		Optional<BoardDao> opBoardDao = boardRepo.findById(id);
		try {
			BoardDao boardDao = opBoardDao.get();
			
		} catch (NoSuchElementException e) {

		}

		return null;
	}
}
