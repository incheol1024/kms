package com.devworker.kms.controller.solution;

import javax.validation.Valid;

import com.devworker.kms.dto.common.BoardDetailDto;
import com.devworker.kms.dto.common.BoardDto;
import com.devworker.kms.dto.solution.SolutionBugDto;
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

	@GetMapping("/{menuId}") // /patch/{menuId} 로 변경
	public Page<BoardDto> getPatchList(@PathVariable int menuId, Pageable pageable){
		return solutionService.getPageList(menuId, pageable);
	}

	@GetMapping("/bug/{menuId}")
	public Page<SolutionBugDto> getBugList(@PathVariable int menuId, Pageable pageable){
		SolutionBugDto solutionbugdto = new SolutionBugDto();
		solutionbugdto.getBoardDetailDto();
		return solutionService.getBugList(menuId, pageable);
	}
		
	@GetMapping("/site/{menuId}")
	public Page<BoardDto> getSiteList(@PathVariable int menuId, Pageable pageable){
		return solutionService.getPageList(menuId, pageable);
	}
	
	@GetMapping("/manual/{menuId}")
	public Page<BoardDto> getManualList(@PathVariable int menuId, Pageable pageable){
		return solutionService.getPageList(menuId, pageable);
	}
	
	@GetMapping("/{menuId}/{id}")
	public BoardDetailDto getSolutionById(@PathVariable String menuId, @PathVariable Long id) {
		return solutionService.getSolutionById(id);
	}
	
	@PostMapping("/write")
	public long solutionRegister(@Valid @RequestBody SolutionDto solutionDto) {
		return solutionService.registerSolution(solutionDto);
	}
	
	@PostMapping("/write/bug")
	public long solutionBugRegister(@Valid @RequestBody SolutionBugDto solutionBugDto) {
		return solutionService.registerBug(solutionBugDto);
	}
	
	@PutMapping
	public void solutionEdit(@Valid @RequestBody SolutionDto solutionDto) {
		solutionService.editSolution(solutionDto);
	}
	
	@DeleteMapping("/{boardId}")
	public void solutionDelete(@PathVariable int boardId) {
		solutionService.deleteSolution(boardId);
	}

}
