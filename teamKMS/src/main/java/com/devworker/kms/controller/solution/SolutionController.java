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
import com.devworker.kms.service.solution.SolutionService;

@RestController
@RequestMapping("/solution")
public class SolutionController {
	private Logger logger = LoggerFactory.getLogger(SolutionController.class);
	
	@Autowired
	SolutionService solutionService;
	
	@GetMapping("")
	public List<BoardDao> solutionHome(){
		List<BoardDao> list = solutionService.getFirstPageList();
		return list;
	}
	
	@PostMapping("/register")
	public BoardDao solutionRegiser(@Valid @RequestBody BoardDao boardDao) {
		System.out.println("1 boardid : " + boardDao.getBoardId());
		System.out.println("1 content : " + boardDao.getContents());
		System.out.println("1 subject : " + boardDao.getSubject());
		System.out.println("1 userid : " + boardDao.getUserId());
		System.out.println("1 regdate : " + boardDao.getRegDate());
		System.out.println("1 update : " + boardDao.getUpdDate());
		return solutionService.registerSolution(boardDao);
	}
	
	@PostMapping("/edit")
	public void solutionEdit(@RequestBody BoardDao boardDao) {
		System.out.println(" --test-- : " + boardDao.getBoardId());
//		solutionService.editSolution(boardDao);
	}
	
	@DeleteMapping("/{boardId}")
	public void solutionDelete(@PathVariable String boardId) {
		solutionService.deleteSolution(Integer.parseInt(boardId));
	}
	
	@GetMapping("/answer/{id}")
	public BoardDao getSolutionById(@PathVariable Integer id) {
		return solutionService.findById(id).get();
	}
}
