package com.devworker.kms.service.solution;

import com.devworker.kms.component.BoardComponent;
import com.devworker.kms.dto.common.BoardDto;
import com.devworker.kms.dto.solution.SolutionDto;
import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.solution.SolutionDao;
import com.devworker.kms.exception.NotExistException;
import com.devworker.kms.repo.solution.SolutionRepo;
import com.devworker.kms.repo.solution.SolutionRepoImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SolutionService {
	
	@Autowired
	SolutionRepo solutionRepo;
	
	@Autowired
	SolutionRepoImpl solutionRepoImpl;
	
	@Autowired
	BoardComponent boardcomponenet;

	public Page<BoardDto> getPageList(String solution, Pageable pageable) {
		Page<BoardDao> boardDao = null;
		List<BoardDao> list = new ArrayList<BoardDao>();
	    
		if(solution.equals("1")) {
			solution = "ECM";
			list.addAll(solutionRepoImpl.getSolutionList(solution, pageable));
			boardDao = new PageImpl<BoardDao>(list, pageable, list.size());
		}
		else if(solution.equals("2")) {
			solution = "EDM";
			list.addAll(solutionRepoImpl.getSolutionList(solution, pageable));
			boardDao = new PageImpl<BoardDao>(list, pageable, list.size());
		}
		else {
			list.addAll(solutionRepoImpl.getetcList(pageable));
			boardDao = new PageImpl<BoardDao>(list, pageable, list.size());
		}
		Page<BoardDto> boardDto = BoardDtotoDao(boardDao);
		return boardDto;
	}
	
	public SolutionDao registerSolution(SolutionDto solutionDto) {
		return solutionRepo.save(solutionDto.toDao());
	}
		
	public SolutionDao editSolution(SolutionDto solutionDto) {
		Optional<SolutionDao> solutionDao = solutionRepo.findById(solutionDto.getBoardId());
		solutionDao.orElseThrow(() -> new NotExistException("Board Not Found"));
		return solutionRepo.save(solutionDto.toDao());
	}

	public void deleteSolution(long id) {
		solutionRepo.deleteById(id);
	}

	public Optional<SolutionDao> findById(Long id) {
		BoardDao boardDao = boardcomponenet.getBoard(id);
		boardDao.setHits(boardDao.getHits()+1);
		Optional<SolutionDao> solutionDao = solutionRepo.findById(id);

		return solutionDao;
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
		BoardDao boardDao = boardcomponenet.getBoard(id);
		Optional<SolutionDao> S_dao = solutionRepo.findById(id);
		SolutionDao solutionDao = S_dao.get();
		S_dao.orElseThrow(()-> new NotExistException("Solution not Found"));
		return S_dao;
	}
	
	private Page<BoardDto> BoardDtotoDao(Page<BoardDao> boardDao){
		
		return boardDao.map(BoardDao::getDto);
	}
}
