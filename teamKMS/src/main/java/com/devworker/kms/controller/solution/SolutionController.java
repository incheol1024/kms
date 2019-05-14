package com.devworker.kms.controller.solution;

import javax.validation.Valid;

import com.devworker.kms.dto.common.BoardDto;
import com.devworker.kms.dto.solution.SolutionDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.solution.SolutionDao;
import com.devworker.kms.component.BoardComponent;
import com.devworker.kms.service.solution.SolutionService;
import com.devworker.kms.util.CommonUtil;

@RestController
@RequestMapping("/solution")
public class SolutionController {
	
	@Autowired
	SolutionService solutionService;

	@Autowired
	BoardComponent boardComponent;
	
	@GetMapping("/{menuId}")
	public Page<BoardDto> solutionHome(@PathVariable String menuId, Pageable pageable){
		Page<BoardDto> list = solutionService.getPageList(menuId, pageable);
		return list;
	}
	
	@PostMapping("/register")
	public BoardDao solutionRegiser(@Valid @RequestBody BoardDto boardDto, @Valid @RequestBody SolutionDto solutionDto) {
		boardDto.setUserId(CommonUtil.getCurrentUser());
		BoardDao boardDao = boardComponent.register(boardDto);
		solutionDto.setBoardId(boardDao.getBoardId());
		solutionService.registerSolution(solutionDto);
		return boardDao;
	}
	
	@PostMapping("/edit")
	public BoardDao solutionEdit(@Valid @RequestBody BoardDto boardDto, @Valid @RequestBody SolutionDto solutionDto) {
		solutionService.editSolution(solutionDto);
		BoardDao boardDao = boardComponent.edit(boardDto);
		return boardDao;
	}
	
	@DeleteMapping("/{boardId}")
	public void solutionDelete(@PathVariable String boardId) {
		boardComponent.delete(Integer.parseInt(boardId));
	}
	
	@GetMapping("/answer/{id}")
	public SolutionDao getSolutionById(@PathVariable Long id) {
		SolutionDao solutionDao = null;
		if(id != 0) {
			solutionDao = solutionService.findById(id).get();
		}
		return solutionDao;
	}
}
