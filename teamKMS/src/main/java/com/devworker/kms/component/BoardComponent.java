package com.devworker.kms.component;

import com.devworker.kms.dto.common.BoardDetailDto;
import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.exception.NotExistException;
import com.devworker.kms.repo.common.BoardRepo;
import com.devworker.kms.repo.common.BoardRepoImpl;
import org.jooq.*;
import org.jooq.types.UInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Optional;

@Component
public class BoardComponent {
	@Autowired
	BoardRepo boardRepo;
	@Autowired
	BoardRepoImpl boardRepoImpl;

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


	public long register(BoardDetailDto dto) {
		return boardRepo.save(dto.toDao()).getBoardId();
	}

	public void edit(BoardDetailDto dto) {
		Optional<BoardDao> dao = boardRepo.findById(dto.getBoardId());
		dao.orElseThrow(() -> new NotExistException("Board Not Found"));
		boardRepo.save(dto.toDao());
	}
	
	public void delete(long id) {
		boardRepo.deleteById(id);
	}
	
	public BoardDetailDto getBoard(Long id)
	{
		return boardRepo.findById(id).orElseThrow(() -> new NotExistException("Board Not Found")).getBoardDetailDto();
	}

}
