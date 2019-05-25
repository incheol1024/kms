package com.devworker.kms.controller.solution;

import javax.validation.Valid;

import com.devworker.kms.dto.common.BoardDetailDto;
import com.devworker.kms.dto.solution.SolutionDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.devworker.kms.service.solution.SolutionService;

@RestController
@RequestMapping("/solution")
public class SolutionController {
	@Autowired
	SolutionService solutionService;

	@GetMapping("/{menuId}")
	public Page<SolutionDto> getList(@PathVariable int menuId, Pageable pageable){
		return solutionService.getPageList(menuId, pageable);
	}

	@GetMapping("/{menuId}/{id}")
	public BoardDetailDto getSolutionById(@PathVariable Long id, @PathVariable String menuId) {
		return solutionService.getSolutionById(id);
	}
	
	@PostMapping
	public long solutionRegister(@Valid @RequestBody SolutionDto solutionDto,
								 @Valid @RequestBody BoardDetailDto boardDetailDto) {
		return solutionService.registerSolution(solutionDto,boardDetailDto);
	}
	
	@PutMapping
	public void solutionEdit(@Valid @RequestBody SolutionDto solutionDto,
							 @Valid @RequestBody BoardDetailDto boardDetailDto) {
		solutionService.editSolution(solutionDto,boardDetailDto);
	}
	
	@DeleteMapping("/{boardId}")
	public void solutionDelete(@PathVariable int boardId) {
		solutionService.deleteSolution(boardId);
	}

}
