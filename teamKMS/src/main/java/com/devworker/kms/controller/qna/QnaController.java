package com.devworker.kms.controller.qna;

import java.util.List;
import java.util.Map;

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

import com.devworker.kms.dto.qna.QnaDto;
import com.devworker.kms.service.qna.QnaService;

import io.netty.handler.codec.http.HttpRequest;

@RestController
@RequestMapping("/qna")
public class QnaController {

	private Logger logger = LoggerFactory.getLogger(QnaController.class);

	@Autowired
	QnaService qnaService;

	@GetMapping("")
	public List<QnaDto> qnaHome() {
		List<QnaDto> list = qnaService.getFirstPageList();
		return list;
	}
	
	@PostMapping("/register") 
	public QnaDto qnaRegiser(@RequestBody QnaDto qnaDto) {
		return qnaService.registerQuestion(qnaDto);
	}

	@GetMapping("/answer/{id}")
	public QnaDto getQnaById(@PathVariable long id) {
		System.out.println("getQnaById() called");
		return qnaService.findById(id);
	}
	
	@GetMapping("/search")
	public String qnaSearchResult() {
		return "/qna/qnasearch.html";
	}


}
