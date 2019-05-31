package com.devworker.kms.controller.common;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.devworker.kms.repo.common.CommentRepo;
import com.devworker.kms.dto.common.BoardDto;
import com.devworker.kms.dto.common.CommentAndFileTransactionDto;
import com.devworker.kms.dto.common.CommentDto;
import com.devworker.kms.component.CommentComponent;

@RestController
@RequestMapping("/comment")
public class CommentController {

	private Logger logger = LoggerFactory.getLogger(CommentController.class);

	@Autowired
	CommentComponent commentComponent;
	
	@Autowired
	CommentRepo commentRepo;

	@PostMapping("/add")
	public CommentDto addComment(@RequestBody CommentDto commentDto) throws Exception {

		logger.debug("{}", commentDto);
		return commentComponent.addComment(commentDto);
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
	/*
	 * @PostMapping("/add/files") public CommentDto addComment(@RequestBody
	 * CommentAndFileTransactionDto comFileDto) throws Exception {
	 * 
	 * return commentService.addComment(comFileDto.getBoardId(),
	 * comFileDto.getCmtContents(), comFileDto.getFileTransactKey(),
	 * comFileDto.getFileCount()); }
	 */

	@PostMapping("/add/files")
	public CommentDto addCommentAndFile(@RequestBody CommentDto commentDto) throws Exception {
		return commentComponent.addCommentAndFile(commentDto);
	}

	/*
	 * @GetMapping("/list/{boardId}") public List<CommentDto>
	 * listComment(@PathVariable @RequestBody BoardDao boardId) throws Exception {
	 * 
	 * logger.debug("CommentDto {}", commentService.findByBoardId(boardId)); return
	 * commentService.findByBoardId(boardId); }
	 */	
	@GetMapping("/list/{boardId}")
	public Page<CommentDto> listComment(@PathVariable BoardDao boardId, Pageable pageable) {
		//return commentService.findByBoardId(boardId);

		System.out.println(pageable);
		return commentComponent.findCommentsByPage(boardId, pageable);
//		return null;
	}

	@PostMapping("/update")
	public CommentDto updateComment(@RequestBody CommentDao commentDao) throws Exception {
		return commentComponent.updateComment(commentDao);
	}

	@DeleteMapping("/delete")
	public Integer deleteComment(@RequestParam Integer cmtId) throws Exception {
		commentComponent.deleteComment(cmtId);
		return cmtId;
	}

	@PostMapping("/like/{cmtId}")
	public CommentDao updateCommentLike(@PathVariable int cmtId) {
		return commentComponent.updateCommentLike(cmtId);
	}
	/*
	 * @PostMapping("/unlike") public CommentDao updateCommentUnlike(@RequestBody
	 * CommentDao commentDao) { return
	 * commentService.updateCommentUnLike(commentDao);
	 * 
	 * }
	 */
}
