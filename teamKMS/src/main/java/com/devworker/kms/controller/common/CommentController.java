package com.devworker.kms.controller.common;

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

import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.common.CommentDao;
import com.devworker.kms.dto.common.CommentAndFileTransactionDto;
import com.devworker.kms.dto.common.CommentDto;
import com.devworker.kms.component.CommentComponent;

@RestController
@RequestMapping("/comment")
public class CommentController {

	private Logger logger = LoggerFactory.getLogger(CommentController.class);

	@Autowired
	CommentComponent commentService;

	@PostMapping("/add")
	public CommentDao addComment(@RequestBody CommentDto commentDto) throws Exception {
		
		logger.debug("{}", commentDto);
		return commentService.addComment(commentDto);
	}
	/*
	 * @PostMapping("/add/files") public CommentDao addComment(@RequestBody
	 * CommentAndFileTransactionDto comFileDto) throws Exception {
	 * 
	 * logger.debug("RequestBody Test = {}", comFileDto);
	 * 
	 * logger.debug("PathVaiable boardId = {}", boardId);
	 * logger.debug("RequestBody CommentDao = {}", commentDao);
	 * logger.debug("FileTranctionDto = {}", fileTransactionDto);
	 * 
	 * 
	 * return commentService.addComment(comFileDto.getBoardId(),
	 * comFileDto.getCmtContents(), comFileDto.getFileTransactKey(),
	 * comFileDto.getFileCount());
	 * 
	 * }
	 * 
	 */

	@PostMapping("/add/files")
	public CommentDto addComment(@RequestBody CommentAndFileTransactionDto comFileDto) throws Exception {
		return commentService.addComment(comFileDto.getBoardId(), comFileDto.getCmtContents(),
				comFileDto.getFileTransactKey(), comFileDto.getFileCount());
	}

	@GetMapping("/list/{boardId}")
	public List<CommentDto> listComment(@PathVariable @RequestBody BoardDao boardId) throws Exception {
		
		logger.debug("CommentDto {}", commentService.findByBoardId(boardId));
		return commentService.findByBoardId(boardId);
	}

	@PostMapping("/update")
	public CommentDao updateComment(@RequestBody CommentDao commentDao) throws Exception {
		return commentService.updateComment(commentDao);
	}

	@DeleteMapping("/delete")
	public Integer deleteComment(@RequestParam Integer cmtId) throws Exception {
		commentService.deleteComment(cmtId);
		return cmtId;
	}

	@PostMapping("/like/{cmtId}")
	public CommentDao updateCommentLike(@PathVariable int cmtId) {
		return commentService.updateCommentLike(cmtId);
	}
/*
	@PostMapping("/unlike")
	public CommentDao updateCommentUnlike(@RequestBody CommentDao commentDao) {
		return commentService.updateCommentUnLike(commentDao);

	}
*/
}
