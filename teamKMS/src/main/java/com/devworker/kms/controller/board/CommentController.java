package com.devworker.kms.controller.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devworker.kms.dao.board.CommentDao;
import com.devworker.kms.service.board.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	CommentService commentService;

	@PostMapping("/add")
	public CommentDao addComment(@RequestBody CommentDao commentDao
	) throws Exception {
		return commentService.addComment(commentDao);
	}
	
	@GetMapping("/{boardId}")
	public List<CommentDao> listComment(@PathVariable Integer boardId) throws Exception {
		return commentService.findByBoardId(boardId);
	}
	
	@PostMapping("/update")
	public CommentDao updateComment(@RequestBody CommentDao commentDao) throws Exception {
		return commentService.updateComment(commentDao);
	}

	@PutMapping("/delete")
	public void deleteComment(@RequestBody CommentDao commentDao) {
		commentService.deleteComment(commentDao);
	}
	

}
