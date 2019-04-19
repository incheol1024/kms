package com.devworker.kms.component;

import com.devworker.kms.dto.common.BoardDto;
import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.exception.NotExistException;
import com.devworker.kms.repo.common.BoardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BoardComponent {
	@Autowired
	BoardRepo boardRepo;
	
	public Page<BoardDao> getPages(Pageable pageable)
	{
		return boardRepo.findAll(pageable);
	}

	
	public BoardDao register(BoardDto dto) {
		return boardRepo.save(dto.toDao());
	}

	public BoardDao edit(BoardDto dto) {
		Optional<BoardDao> dao = boardRepo.findById(dto.getBoardId());
		dao.orElseThrow(() -> new NotExistException("Board Not Found"));
		return boardRepo.save(dto.toDao());
	}
	
	public void delete(long id) {
		boardRepo.deleteById(id);
	}
	
	public BoardDao getBoard(Long id)
	{
		return boardRepo.findById(id).orElseThrow(() -> new NotExistException("Board Not Found"));
	}
}
