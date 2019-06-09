package com.devworker.kms.component;

import com.devworker.kms.dic.PermissionType;
import com.devworker.kms.dto.FtsDto;
import com.devworker.kms.dto.common.BoardDetailDto;
import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.exception.NotAllowException;
import com.devworker.kms.exception.NotExistException;
import com.devworker.kms.repo.common.BoardRepo;
import com.devworker.kms.repo.common.BoardRepoImpl;
import com.devworker.kms.service.FTSService;
import com.devworker.kms.util.AclUtil;
import com.devworker.kms.util.CommonUtil;
import org.jooq.*;
import org.jooq.types.UInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class BoardComponent {
	@Autowired
	BoardRepo boardRepo;
	@Autowired
	BoardRepoImpl boardRepoImpl;
	@Autowired
	FTSService ftsService;

	public SelectJoinStep<Record6<UInteger, String, String, Date, Date, Integer>> select(){
		return boardRepoImpl.select();
	}

	public SelectJoinStep<Record7<UInteger, String, String, Date, Date, Integer, String>> selectWithContent(){
		return boardRepoImpl.selectWithContent();
	}

	public SelectWithTiesAfterOffsetStep paging(SelectSeekStepN step, Pageable pageable){
		return boardRepoImpl.paging(step, pageable);
	}

	public SelectSeekStepN sorting(SelectConditionStep<Record6<UInteger, String, String, Date, Date, Integer>> step, Pageable pageable){
		return boardRepoImpl.sorting(step, pageable);
	}

	public Condition boardIdEq(TableField field){
		return boardRepoImpl.boardIdEq(field);
	}


	public long register(BoardDetailDto dto,PermissionType type) {
		if(!AclUtil.checkPermission(type))
			throw new NotAllowException("You Have not Board Permission." + type.getValue());
		dto.setRegDate(LocalDateTime.now());
		dto.setUpdDate(LocalDateTime.now());
		dto.setUserId(CommonUtil.getCurrentUser());

		long boardId = boardRepo.save(dto.toDao()).getBoardId();
		FtsDto fts = new FtsDto();
		fts.setId(boardId);
		fts.setWord(dto.getContents());
		fts.setName(dto.getSubject());
		fts.setUser(dto.getUserId());
		ftsService.save(fts);
		return boardId;
	}

	public void edit(BoardDetailDto newer, BoardDetailDto old,PermissionType type) {
		boardRepo.findById(old.getBoardId()).orElseThrow(() -> new NotExistException("Board Not Found"));
		if(!AclUtil.checkPermission(type))
			throw new NotAllowException("You Have not Board Permission." + type.getValue());
		old.setUpdDate(LocalDateTime.now());
		old.setContents(newer.getContents());
		old.setSubject(newer.getSubject());

		FtsDto fts = new FtsDto();
		fts.setId(old.getBoardId());
		fts.setWord(old.getContents());
		fts.setName(old.getSubject());
		fts.setUser(old.getUserId());
		ftsService.save(fts);

		boardRepo.save(old.toDao());
	}
	
	public void delete(long id) {
		ftsService.delete(id);
		boardRepo.deleteById(id);
	}
	
	public BoardDetailDto getBoard(Long id)
	{
		BoardDetailDto dto = boardRepo.findById(id).orElseThrow(() -> new NotExistException("Board Not Found")).getBoardDetailDto();
		dto.setReadOnly(!AclUtil.checkPermission(dto.getUserId() , PermissionType.MODIFYSOL));
		if(!dto.getUserId().equals(CommonUtil.getCurrentUser())){
			dto.setHits(dto.getHits() +1);
			boardRepo.save(dto.toDao());
		}
		return dto;
	}

}
