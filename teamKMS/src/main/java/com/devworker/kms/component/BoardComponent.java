package com.devworker.kms.component;

import com.devworker.kms.dto.common.BoardDetailDto;
import com.devworker.kms.dto.common.BoardDto;
import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.exception.NotExistException;
import com.devworker.kms.repo.common.BoardRepo;
import com.devworker.kms.repo.common.BoardRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BoardComponent {
	@Autowired
	BoardRepo boardRepo;
	@Autowired
	BoardRepoImpl boardRepoImpl;
	
	public Page<BoardDto> getPages(Pageable pageable)
	{
		return boardRepoImpl.findAll(pageable).map(BoardDao::getBoardDto);
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
