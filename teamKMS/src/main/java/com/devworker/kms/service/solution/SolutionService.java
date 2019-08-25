package com.devworker.kms.service.solution;

import com.devworker.kms.component.board.BoardComponent;
import com.devworker.kms.dic.PermissionType;
import com.devworker.kms.dto.common.BoardDetailDto;
import com.devworker.kms.dto.common.BoardDto;
import com.devworker.kms.dto.solution.SolutionBugDto;
import com.devworker.kms.dto.solution.SolutionDto;
import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.solution.SolutionBugDao;
import com.devworker.kms.exception.NotAllowException;
import com.devworker.kms.exception.NotExistException;
import com.devworker.kms.repo.solution.*;
import com.devworker.kms.util.AclUtil;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SolutionService {
	@Autowired
	SolutionRepo solutionRepo;
	@Autowired
	SolutionSiteRepo solutionSiteRepo;
	@Autowired
	SolutionPatchRepo solutionPatchRepo;
	@Autowired
	SolutionBugRepo solutionBugRepo;
	@Autowired
	SolutionBugRepoImpl solutionBugRepoImpl;
	@Autowired
	SolutionRepoImpl solutionRepoImpl;
	@Autowired
	BoardComponent boardComponent;

	//Get List
	public Page<BoardDto> getPageList(int menuId, Pageable pageable) {
		return solutionBugRepoImpl.getPageList(menuId, pageable).map(BoardDao::getBoardDto);
	}
	
	public Page<BoardDto> getPatchList(int menuId, Pageable pageable) {
		return solutionRepoImpl.getPageList(menuId, pageable).map(BoardDao::getBoardDto);
	}
	
	public Page<SolutionBugDto> getBugList(int menuId, Pageable pageable) {
		return solutionBugRepo.getBug(menuId, pageable).map(SolutionBugDao::toDto);
	}
	
	public Page<BoardDto> getSiteList(int menuId, Pageable pageable) {
		return solutionRepoImpl.getPageList(menuId, pageable).map(BoardDao::getBoardDto);
	}
	
	public Page<BoardDto> getManualList(int menuId, Pageable pageable) {
		return solutionRepoImpl.getPageList(menuId, pageable).map(BoardDao::getBoardDto);
	}
	
	//Register
	public long registerSolution(SolutionDto solutionDto) {
		long boardId = boardComponent.register(solutionDto.getBoardDetailDto(),PermissionType.CREATESOL);
		solutionDto.setBoardId(boardId);
		return solutionRepo.save(solutionDto.toDao()).getBoardId();
	}

	public long registerBug(SolutionBugDto solutionBugDto) {
		long boardId = boardComponent.register(solutionBugDto.getBoardDetailDto(),PermissionType.CREATESOL);
		solutionBugDto.setBoardId(boardId);
		solutionBugDto.setManager("ADMIN");
		solutionBugDto.setCompleted("N");
		return solutionBugRepo.save(solutionBugDto.toDao()).getBoardId();
	}
	
	//Edit
	public void editSolution(SolutionDto solutionDto) {
		System.out.println("solution id : " + solutionDto.getBoardId());
		System.out.println("solution id : " + solutionDto.getBoardDetailDto().getBoardId());
		
		BoardDetailDto dto = getSolutionById(solutionDto.getBoardId());
		boardComponent.edit(solutionDto.getBoardDetailDto(),dto,PermissionType.MODIFYSOL);
	}

	public void editSolutionBug(SolutionBugDto solutionBugDto) {
		solutionBugRepo.findById(solutionBugDto.getBoardId()).orElseThrow(() -> new NotExistException("Not Found SolutionBug"));
		SolutionBugDao dao = solutionBugDto.toDao();
		solutionBugRepo.save(dao);
		BoardDetailDto dto = getSolutionBugById(solutionBugDto.getBoardId());
		boardComponent.edit(solutionBugDto.getBoardDetailDto(),dto,PermissionType.MODIFYSOL);
	}
	
	//Delete
	public void deleteSolution(long id) {
		BoardDetailDto dto = getSolutionById(id);
		if(!AclUtil.checkPermission(dto.getUserId() , PermissionType.DELETESOL))
			throw new NotAllowException("You Have not Delete Solution Board Permission");
		solutionRepo.deleteById(id);
		boardComponent.delete(id);
	}

	public void deleteSolutionBug(long id) {
		BoardDetailDto dto = getSolutionBugById(id);
		if(!AclUtil.checkPermission(dto.getUserId() , PermissionType.DELETESOL))
			throw new NotAllowException("You Have not Delete SolutionBug Board Permission");
		solutionBugRepo.deleteById(id);
		boardComponent.delete(id);
	}
	
	//getter
	public BoardDetailDto getSolutionById(Long id) {
		solutionRepo.findById(id).orElseThrow(() -> new NotExistException("Solution Board Not Found"));
		return boardComponent.getBoard(id,PermissionType.MODIFYSOL);
	}
	public BoardDetailDto getSolutionBugById(Long id) {
		solutionBugRepo.findById(id).orElseThrow(() -> new NotExistException("SolutionBug Board Not Found"));
		return boardComponent.getBoard(id,PermissionType.MODIFYSOL);
	}
}
