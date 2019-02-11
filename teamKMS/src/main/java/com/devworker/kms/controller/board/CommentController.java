package com.devworker.kms.controller.board;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devworker.kms.entity.board.BoardDao;
import com.devworker.kms.entity.board.CommentDao;
import com.devworker.kms.dto.board.CommentAndFileTransactionDto;
import com.devworker.kms.service.board.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {

	private Logger logger = LoggerFactory.getLogger(CommentController.class);
	
	@Autowired
	CommentService commentService;

	@PostMapping("/add")
	public CommentDao addComment(@RequestBody CommentDao commentDao) throws Exception {
		return commentService.addComment(commentDao);
	}

	@PostMapping("/add/files/{boardId}")
	public CommentDao addComment(@PathVariable int boardId, @RequestBody CommentAndFileTransactionDto comFileDto) throws Exception {

		logger.debug("RequestBody Test = {}", comFileDto);
/*		logger.debug("PathVaiable boardId = {}", boardId);
		logger.debug("RequestBody CommentDao = {}", commentDao);
		logger.debug("FileTranctionDto = {}", fileTransactionDto);
*/		
		return null;
		//return commentService.addComment(boardId, cmtContents, fileTransactKey, fileCount);
	}

	@GetMapping("/list/{boardId}")
	public List<CommentDao> listComment(@PathVariable @RequestBody BoardDao boardId) throws Exception {
		return commentService.findByBoardId(boardId);
	}

	@PostMapping("/update")
	public CommentDao updateComment(@RequestBody CommentDao commentDao) throws Exception {
		return commentService.updateComment(commentDao);
	}

	@DeleteMapping("/delete")
	public Integer deleteComment(@RequestParam Integer cmtId) {
		commentService.deleteComment(cmtId);
		return cmtId;
	}

	@PostMapping("/{cmtId}/like")
	public CommentDao updateCommentLike(@PathVariable int cmtId) {
		return commentService.updateCommentLike(cmtId);
	}

	@PostMapping("/unlike")
	public CommentDao updateCommentUnlike(@RequestBody CommentDao commentDao) {
		return commentService.updateCommentUnLike(commentDao);

	}

}
