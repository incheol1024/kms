package com.devworker.kms.controller.qna;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devworker.kms.dao.board.BoardDao;
import com.devworker.kms.dao.qna.QnaDao;
import com.devworker.kms.service.qna.QnaService;

import io.netty.handler.codec.http.HttpRequest;

@RestController
@RequestMapping("/qna")
public class QnaController {

	private Logger logger = LoggerFactory.getLogger(QnaController.class);

	@Autowired
	QnaService qnaService;

	@GetMapping("")
	public List<BoardDao> qnaHome() {
		List<BoardDao> list = qnaService.getFirstPageList();
		return list;
	}
	
	@PostMapping("/register") 
	public BoardDao qnaRegiser(@RequestBody BoardDao boardDao) {
		return qnaService.registerQuestion(boardDao);
	}

	@GetMapping("/answer/{id}")
	public BoardDao getQnaById(@PathVariable Integer id) {
		return qnaService.findById(id).get();
	}
	
	@GetMapping("/search")
	public String qnaSearchResult() {
		System.out.println("qna test ");
		qnaService.QnaTest();
		return "/qna/qnasearch.html";
	}

	

}
