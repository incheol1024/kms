package com.devworker.kms.service.solution;

import com.devworker.kms.component.BoardComponent;
import com.devworker.kms.dto.common.BoardDetailDto;
import com.devworker.kms.dto.solution.SolutionDto;
import com.devworker.kms.entity.solution.SolutionDao;
import com.devworker.kms.exception.NotExistException;
import com.devworker.kms.repo.solution.SolutionRepo;
import com.devworker.kms.repo.solution.SolutionRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

	public Page<SolutionDto> getPageList(int menuId, Pageable pageable) {
		List<SolutionDao> pageList = solutionRepoImpl.getPageList(menuId, pageable);
		List<Long> collect = pageList.stream().map(solutionDao -> solutionDao.getBoardId()).collect(Collectors.toList());
		return boardComponent.getPages(collect, pageable).map(
				boardDto -> {
					SolutionDto dto = new SolutionDto();
					dto.setBoardId(boardDto.getBoardId());
					dto.setMenuId(menuId);
					dto.setDto(boardDto);
					return dto;
				}
		);
	}
	
	public long registerSolution(SolutionDto solutionDto, BoardDetailDto boardDetailDto) {
		long boardId = boardComponent.register(boardDetailDto);
		solutionDto.setBoardId(boardId);
		return solutionRepo.save(solutionDto.toDao()).getBoardId();
	}
		
	public void editSolution(SolutionDto solutionDto, BoardDetailDto boardDetailDto) {
		Optional<SolutionDao> solutionDao = solutionRepo.findById(solutionDto.getBoardId());
		solutionDao.orElseThrow(() -> new NotExistException("Solution Board Not Found"));
		boardComponent.edit(boardDetailDto);
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
