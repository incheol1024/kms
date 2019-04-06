package com.devworker.kms.controller.board;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devworker.kms.entity.board.BoardDao;
import com.devworker.kms.service.board.WriteService;


@RestController
@RequestMapping("/write")
public class WriteController {
	@Autowired
	WriteService writeService;
	
	@PostMapping("/register")
	public BoardDao Regiser(@RequestBody BoardDao boardDao) {
		return writeService.register(boardDao);
	}
	
	@PostMapping("/edit")
	public void edit(@Valid @RequestBody BoardDao boardDao) {
		writeService.edit(boardDao);
	}
	
	@DeleteMapping("/{boardId}")
	public void delete(@PathVariable String boardId) {
		writeService.delete(Integer.parseInt(boardId));
	}
}