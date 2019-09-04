package com.devworker.kms.controller.solution;

import javax.validation.Valid;

import com.devworker.kms.dto.common.BoardDetailDto;
import com.devworker.kms.dto.common.BoardDto;
import com.devworker.kms.dto.solution.SolutionBugDto;
import com.devworker.kms.dto.solution.SolutionDto;
import com.devworker.kms.dto.solution.SolutionPatchDto;
import com.devworker.kms.dto.solution.SolutionSiteDto;

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

	//Get Page List
	@GetMapping("/{menuId}") // {menuId}/manual 로 변경
	public Page<BoardDto> getList(@PathVariable Long menuId, Pageable pageable){
		return solutionService.getPageList(menuId, pageable);
	}

	@GetMapping("/{menuId}/bug")
	public Page<SolutionBugDto> getBugList(@PathVariable Long menuId, Pageable pageable){
		return solutionService.getBugList(menuId, pageable);
	}
		
	@GetMapping("/{menuId}/site")
	public Page<SolutionSiteDto> getSiteList(@PathVariable Long menuId, Pageable pageable){
		return solutionService.getSiteList(menuId, pageable);
	}
	
	@GetMapping("/{menuId}/patch")
	public Page<SolutionPatchDto> getPatchList(@PathVariable Long menuId, Pageable pageable){
		return solutionService.getPatchList(menuId, pageable);
	}
	
	@GetMapping("/{menuId}/{id}")
	public BoardDetailDto getSolutionById(@PathVariable Long menuId, @PathVariable Long id) {
		return solutionService.getSolutionById(id);
	}
	
	//Write Page
	@PostMapping("/write") // /write/manual로 변경
	public long solutionRegister(@Valid @RequestBody SolutionDto solutionDto) {
		return solutionService.registerSolution(solutionDto);
	}
	
	@PostMapping("/write/bug")
	public long solutionBugRegister(@Valid @RequestBody SolutionBugDto solutionBugDto) {
		return solutionService.registerBug(solutionBugDto);
	}
	
	@PostMapping("/write/site")
	public long solutionSiteRegister(@Valid @RequestBody SolutionSiteDto solutionSiteDto) {
		return solutionService.registerSite(solutionSiteDto);
	}
	
	@PostMapping("/write/patch")
	public long solutionPatchRegister(@Valid @RequestBody SolutionPatchDto solutionPatchDto) {
		return solutionService.registerManual(solutionPatchDto);
	}
	
	//Edit Page
	@PutMapping // /patch로 변경
	public void solutionEdit(@Valid @RequestBody SolutionDto solutionDto) {
		solutionService.editSolution(solutionDto);
	}
	
	@PutMapping("/bug")
	public void solutionBugEdit(@Valid @RequestBody SolutionBugDto solutionBugDto) {
		solutionService.editSolutionBug(solutionBugDto);
	}
	
	@PutMapping("/site")
	public void solutionSiteEdit(@Valid @RequestBody SolutionSiteDto solutionSiteDto) {
		solutionService.editSolutionSite(solutionSiteDto);
	}
	
	@PutMapping("/manual")
	public void solutionPatchEdit(@Valid @RequestBody SolutionPatchDto solutionPatchDto) {
		solutionService.editSolutionPatch(solutionPatchDto);
	}
	
	//Delete Page
	@DeleteMapping("/{boardId}") // /patch/boardid 로 변경
	public void solutionDelete(@PathVariable int boardId) {
		solutionService.deleteSolution(boardId);
	}

	@DeleteMapping("/bug/{boardId}")
	public void solutionBugDelete(@PathVariable int boardId) {
		solutionService.deleteSolutionBug(boardId);
	}
	
	@DeleteMapping("/site/{boardId}")
	public void solutionSiteDelete(@PathVariable int boardId) {
		solutionService.deleteSolutionSite(boardId);
	}
	
	@DeleteMapping("/patch/{boardId}")
	public void solutionPatchDelete(@PathVariable int boardId) {
		solutionService.deleteSolutionPatch(boardId);
	}
}
