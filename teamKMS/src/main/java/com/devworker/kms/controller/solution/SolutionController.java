package com.devworker.kms.controller.solution;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devworker.kms.dao.board.BoardDao;
import com.devworker.kms.service.solution.SolutionService;

@RestController
@RequestMapping("/solution")
public class SolutionController {
	private Logger logger = LoggerFactory.getLogger(SolutionController.class);
	
	SolutionService solutionService;
	
	@GetMapping("")
	public List<BoardDao> solutionHome(){
		List<BoardDao> list = solutionService.getFirstPageList();
		return list;
	}
	
	@GetMapping("/answer/{id}")
	public BoardDao getQnaById(@PathVariable Integer id) {
		return solutionService.findById(id).get();
	}
}
