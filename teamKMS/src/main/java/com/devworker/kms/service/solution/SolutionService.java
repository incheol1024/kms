package com.devworker.kms.service.solution;

import com.devworker.kms.component.BoardComponent;
import com.devworker.kms.dic.PermissionType;
import com.devworker.kms.dto.FtsDto;
import com.devworker.kms.dto.common.BoardDetailDto;
import com.devworker.kms.dto.common.BoardDto;
import com.devworker.kms.dto.solution.SolutionDto;
import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.solution.SolutionDao;
import com.devworker.kms.exception.NotAllowException;
import com.devworker.kms.exception.NotExistException;
import com.devworker.kms.repo.solution.SolutionRepo;
import com.devworker.kms.repo.solution.SolutionRepoImpl;
import com.devworker.kms.service.FTSService;
import com.devworker.kms.util.AclUtil;
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
		return solutionRepoImpl.getPageList(menuId, pageable).map(BoardDao::getBoardDto);
	}
	
	public long registerSolution(SolutionDto solutionDto) {
		long boardId = boardComponent.register(solutionDto.getBoardDetailDto(),PermissionType.CREATESOL);
		solutionDto.setBoardId(boardId);
		return solutionRepo.save(solutionDto.toDao()).getBoardId();
	}
		
	public void editSolution(SolutionDto solutionDto) {
		BoardDetailDto dto = getSolutionById(solutionDto.getBoardId());
		boardComponent.edit(solutionDto.getBoardDetailDto(),dto,PermissionType.MODIFYSOL);
	}

	public void deleteSolution(long id) {
		BoardDetailDto dto = getSolutionById(id);
		if(!AclUtil.checkPermission(dto.getUserId() , PermissionType.DELETESOL))
			throw new NotAllowException("You Have not Delete Solution Board Permission");
		solutionRepo.deleteById(id);
		boardComponent.delete(id);
	}

	public BoardDetailDto getSolutionById(Long id) {
		solutionRepo.findById(id).orElseThrow(() -> new NotExistException("Solution Board Not Found"));
		return boardComponent.getBoard(id,PermissionType.MODIFYSOL);
	}
}
