package com.devworker.kms.service.write;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devworker.kms.entity.board.BoardDao;
import com.devworker.kms.entity.solution.SolutionDao;
import com.devworker.kms.repo.board.BoardRepo;
import com.devworker.kms.repo.write.WriteRepo;
import com.devworker.kms.util.CommonUtil;

@Service
public class WriteService {
	@Autowired
	BoardRepo boardRepo;
	
	@Autowired
	WriteRepo writeRepo;
	
	public BoardDao register(BoardDao boardDao) {
		boardDao.setUserId(CommonUtil.getCurrentUser());
		boardDao.setRegDate(LocalDateTime.now());
		boardDao.setUpdDate(LocalDateTime.now());
		
//		boardDao.setBoardId(boardId);
		boardDao.setSubject("subject");
		boardDao.setContents("contents");
		
		boardDao.setHits(0);
		return boardRepo.save(boardDao);
	}

	public BoardDao edit(BoardDao boardDao) {
		return boardRepo.save(boardDao);
	}
	
	public BoardDao delete(BoardDao boardDao) {
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
