package com.devworker.kms.controller.write;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devworker.kms.entity.board.BoardDao;
import com.devworker.kms.service.write.WriteService;


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
	public BoardDao edit(@RequestBody BoardDao boardDao) {
		return writeService.edit(boardDao);
	}
	
	@PostMapping("/delete")
	public BoardDao delete(@RequestBody BoardDao boardDao) {
		return writeService.delete(boardDao);
	}
}
