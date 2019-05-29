package com.devworker.kms.service.solution;

import com.devworker.kms.component.BoardComponent;
import com.devworker.kms.dto.common.BoardDetailDto;
import com.devworker.kms.dto.common.BoardDto;
import com.devworker.kms.dto.solution.SolutionDto;
import com.devworker.kms.entity.solution.SolutionDao;
import com.devworker.kms.exception.NotExistException;
import com.devworker.kms.repo.solution.SolutionRepo;
import com.devworker.kms.repo.solution.SolutionRepoImpl;
import com.devworker.kms.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SolutionService {

	@Autowired
	SolutionRepo solutionRepo;
	
	@Autowired
	SolutionRepoImpl solutionRepoImpl;
	
	@Autowired
	BoardComponent boardComponent;

	public Page<BoardDto> getPageList(int menuId, Pageable pageable) {
		List<SolutionDao> pageList = solutionRepoImpl.getPageList(menuId, pageable);
		List<Long> collect = pageList.stream().map(SolutionDao::getBoardId).collect(Collectors.toList());
		return boardComponent.getPages(collect, pageable);
	}
	
	public long registerSolution(SolutionDto solutionDto) {
		solutionDto.getBoardDetailDto().setRegDate(LocalDateTime.now());
		solutionDto.getBoardDetailDto().setUpdDate(LocalDateTime.now());
		solutionDto.getBoardDetailDto().setUserId(CommonUtil.getCurrentUser());
		long boardId = boardComponent.register(solutionDto.getBoardDetailDto());
		solutionDto.setBoardId(boardId);
		return solutionRepo.save(solutionDto.toDao()).getBoardId();
	}
		
	public void editSolution(SolutionDto solutionDto) {
		Optional<SolutionDao> solutionDao = solutionRepo.findById(solutionDto.getBoardId());
		solutionDao.orElseThrow(() -> new NotExistException("Solution Board Not Found"));
		solutionDto.getBoardDetailDto().setUpdDate(LocalDateTime.now());
		boardComponent.edit(solutionDto.getBoardDetailDto());
	}

	public void deleteSolution(long id) {
		solutionRepo.deleteById(id);
	}

	public BoardDetailDto getSolutionById(Long id) {
		Optional<SolutionDao> solutionDao = solutionRepo.findById(id);
		solutionDao.orElseThrow(() -> new NotExistException("Solution Board Not Found"));
		return boardComponent.getBoard(id);
	}
}
