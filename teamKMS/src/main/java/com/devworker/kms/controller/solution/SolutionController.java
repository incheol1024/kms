package com.devworker.kms.controller.solution;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devworker.kms.entity.board.BoardDao;
import com.devworker.kms.service.board.WriteService;
import com.devworker.kms.service.solution.SolutionService;

@RestController
@RequestMapping("/solution")
public class SolutionController {
	private Logger logger = LoggerFactory.getLogger(SolutionController.class);
	
	@Autowired
	SolutionService solutionService;

	@Autowired
	WriteService writeService;
	
	@GetMapping("")
	public List<BoardDao> solutionHome(){
		List<BoardDao> list = solutionService.getFirstPageList();
		return list;
	}
	
	@PostMapping("/register")
	public BoardDao solutionRegiser(@Valid @RequestBody BoardDao boardDao) {
		return writeService.register(boardDao);
	}
	
	@PostMapping("/edit")
	public BoardDao solutionEdit(@Valid @RequestBody BoardDao boardDao) {
		return writeService.edit(boardDao);
	}
	
	@DeleteMapping("/{boardId}")
	public void solutionDelete(@PathVariable String boardId) {
		writeService.delete(Integer.parseInt(boardId));
	}
	
	@GetMapping("/answer/{id}")
	public BoardDao getSolutionById(@PathVariable Long id) {
		System.out.println(" -- answerid : " + id);
		return solutionService.findById(id).get();
	}
}
