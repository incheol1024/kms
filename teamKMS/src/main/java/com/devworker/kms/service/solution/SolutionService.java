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

	@Autowired
	FTSService ftsService;

	public Page<BoardDto> getPageList(int menuId, Pageable pageable) {
		return solutionRepoImpl.getPageList(menuId, pageable).map(BoardDao::getBoardDto);
	}
	
	public long registerSolution(SolutionDto solutionDto) {
		if(!AclUtil.checkPermission(PermissionType.CREATESOL))
			throw new NotAllowException("You Have not Create Solution Board Permission");
		solutionDto.getBoardDetailDto().setRegDate(LocalDateTime.now());
		solutionDto.getBoardDetailDto().setUpdDate(LocalDateTime.now());
		solutionDto.getBoardDetailDto().setUserId(CommonUtil.getCurrentUser());
		long boardId = boardComponent.register(solutionDto.getBoardDetailDto());
		solutionDto.setBoardId(boardId);
		FtsDto fts = new FtsDto();
		fts.setId(boardId);
		fts.setWord(solutionDto.getBoardDetailDto().getContents());
		fts.setName(solutionDto.getBoardDetailDto().getSubject());
		fts.setUser(solutionDto.getBoardDetailDto().getUserId());
		fts.setCategory(solutionDto.getMenuId());
		ftsService.save(fts);
		return solutionRepo.save(solutionDto.toDao()).getBoardId();
	}
		
	public void editSolution(SolutionDto solutionDto) {
		BoardDetailDto dto = getSolutionById(solutionDto.getBoardId());
		if(!AclUtil.checkPermission(dto.getUserId() , PermissionType.MODIFYSOL))
			throw new NotAllowException("You Have not Modify Solution Board Permission");
		dto.setUpdDate(LocalDateTime.now());
		dto.setContents(solutionDto.getBoardDetailDto().getContents());
		FtsDto fts = new FtsDto();
		fts.setId(solutionDto.getBoardId());
		fts.setWord(solutionDto.getBoardDetailDto().getContents());
		fts.setName(solutionDto.getBoardDetailDto().getSubject());
		fts.setUser(solutionDto.getBoardDetailDto().getUserId());
		fts.setCategory(solutionDto.getMenuId());
		boardComponent.edit(solutionDto.getBoardDetailDto());
	}

	public void deleteSolution(long id) {
		BoardDetailDto dto = getSolutionById(id);
		if(!AclUtil.checkPermission(dto.getUserId() , PermissionType.DELETESOL))
			throw new NotAllowException("You Have not Delete Solution Board Permission");
		ftsService.delete(id);
		solutionRepo.deleteById(id);
	}

	public BoardDetailDto getSolutionById(Long id) {
		Optional<SolutionDao> solutionDao = solutionRepo.findById(id);
		solutionDao.orElseThrow(() -> new NotExistException("Solution Board Not Found"));
		BoardDetailDto dto = boardComponent.getBoard(id);
		dto.setReadOnly(!AclUtil.checkPermission(dto.getUserId() , PermissionType.MODIFYSOL));
		dto.setHits(dto.getHits() +1);
		boardComponent.edit(dto);
		return dto;
	}
}
