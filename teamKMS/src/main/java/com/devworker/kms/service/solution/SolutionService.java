package com.devworker.kms.service.solution;

import com.devworker.kms.dto.solution.SolutionDto;
import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.solution.SolutionDao;
import com.devworker.kms.repo.common.BoardRepo;
import com.devworker.kms.repo.solution.SolutionRepo;
import com.devworker.kms.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

	public SolutionDao registerSolution(SolutionDto dto) {
		return solutionRepo.save(dto.toDao());
	}
	
	public BoardDao editSolution(BoardDao boardDao) {
		Optional<BoardDao> bd2 = boardRepo.findById(boardDao.getBoardId());	
		BoardDao bd = bd2.get();
		boardDao.setUserId(CommonUtil.getCurrentUser());
		boardDao.setRegDate(bd.getRegDate()); 
		boardDao.setUpdDate(LocalDateTime.now());
		boardDao.setHits(bd.getHits()); 
		return boardRepo.save(boardDao);
	}

	public void deleteSolution(long id) {
		boardRepo.deleteById(id);
	}

	public Optional<BoardDao> findById(Long id) {
		return boardRepo.findById(id);
	}

	public SolutionDao findPostById(Long id) {
		Optional<BoardDao> opBoardDao = boardRepo.findById(id);
		try {
			BoardDao boardDao = opBoardDao.get();
			
		} catch (NoSuchElementException e) {

		}

		return null;
	}
}
