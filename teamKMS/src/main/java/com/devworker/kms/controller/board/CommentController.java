package com.devworker.kms.controller.board;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.devworker.kms.dao.board.CommentDao;
import com.devworker.kms.dto.board.CommentDto;
import com.devworker.kms.service.board.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	CommentService commentService;

	@PostMapping("/add")
	public CommentDto addComment(
			@ModelAttribute CommentDto commentDto
//			@RequestPart String boardId, @RequestPart String cmtContents,
//			@RequestPart MultipartFile file
			) throws IllegalStateException, IOException {

		commentService.addComment(commentDto);
		return commentService.addComment(commentDto);
	}

	@PostMapping("/delete")
	public String updateComment(@RequestBody CommentDao commentDao) {

		//commentService.updateComment(commentDao);
		return null;

	}
	
}
