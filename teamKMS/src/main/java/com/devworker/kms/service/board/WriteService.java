package com.devworker.kms.service.board;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devworker.kms.entity.board.BoardDao;
import com.devworker.kms.entity.solution.SolutionDao;
import com.devworker.kms.repo.board.BoardRepo;
import com.devworker.kms.util.CommonUtil;

@Service
public class WriteService {
	@Autowired
	BoardRepo boardRepo;
	
	public List<BoardDao> getFirstPageList() {
		return (List<BoardDao>) boardRepo.findAll();
	}
	
	public BoardDao register(BoardDao boardDao) {
		boardDao.setUserId(CommonUtil.getCurrentUser());
		boardDao.setRegDate(LocalDateTime.now());
		boardDao.setUpdDate(LocalDateTime.now());
		boardDao.setHits(0);
		
		return boardRepo.save(boardDao);
	}

	public BoardDao edit(BoardDao boardDao) {
		Optional<BoardDao> bd2 = boardRepo.findById(boardDao.getBoardId());	
		BoardDao bd = bd2.get();
		boardDao.setUserId(CommonUtil.getCurrentUser());
		boardDao.setRegDate(bd.getRegDate()); //수정
		boardDao.setUpdDate(LocalDateTime.now());
		boardDao.setHits(bd.getHits()); //수정
		return boardRepo.save(boardDao);
	}
	
	public void delete(long id) {
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
