package com.devworker.kms.service.solution;

import com.devworker.kms.dto.solution.SolutionDto;
import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.solution.SolutionDao;
import com.devworker.kms.exception.NotExistException;
import com.devworker.kms.repo.common.BoardRepo;
import com.devworker.kms.repo.solution.SolutionRepo;
import com.devworker.kms.util.CommonUtil;

import org.hibernate.Session;
import org.hibernate.query.Query;
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
	
	public List<BoardDao> getFirstPageList(String solution) {
		List<BoardDao> list = null;
		if(solution.equals("1")) {
			solution = "ECM";
			list = solutionRepo.getSolutionList(solution);
		}
		else if(solution.equals("2")) {
			solution = "EDM";
			list = solutionRepo.getSolutionList(solution);
		}
		else {
			list = solutionRepo.getetcList();
		}
		
		return list;
	}

	public SolutionDao registerSolution(SolutionDto dto) {
		return solutionRepo.save(dto.toDao());
	}
		
	public SolutionDao editSolution(SolutionDto dto) {
		Optional<SolutionDao> dao = solutionRepo.findById(dto.getBoardId());
		dao.orElseThrow(() -> new NotExistException("Board Not Found"));
		return solutionRepo.save(dto.toDao());
	}

	public void deleteSolution(long id) {
		solutionRepo.deleteById(id);
	}

	public Optional<SolutionDao> findById(Long id) {
		boardRepo.findById(id);
		return solutionRepo.findById(id);
	}
/*
	public SolutionDao findPostById(Long id) {
		Optional<BoardDao> opBoardDao = boardRepo.findById(id);
		try {
			BoardDao boardDao = opBoardDao.get();
			
		} catch (NoSuchElementException e) {

		}

		return null;
	}
	*/
	public Optional<SolutionDao> editById(Long id) {
		Optional<BoardDao> B_dao = boardRepo.findById(id);
		BoardDao boardDao = B_dao.get();
		Optional<SolutionDao> S_dao = solutionRepo.findById(id);
		SolutionDao solutionDao = S_dao.get();
		S_dao.orElseThrow(()-> new NotExistException("Solution not Found"));
		return S_dao;
	}
}
