package com.devworker.kms.controller.qna;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devworker.kms.dto.qna.QnaDto;
import com.devworker.kms.service.qna.QnaService;

@RestController
@RequestMapping("/qna")
public class QnaController {

	@Autowired
	QnaService qnaService;

	@GetMapping("")
	public List<QnaDto> qnaHome() {
		List<QnaDto> list = qnaService.getFirstPageList();
		return  list;
	}

	@GetMapping("/search")
	public String qnaSearchResult() {
		System.out.println("/qna/search");
		return "/qna/qnasearch.html";
	}

	@GetMapping("/ask")
	public String qnaAsk() {
		return "";
	}

	@GetMapping("{number}")
	public String qnaChooseResult(@PathVariable long id) {
		return "";
	}
		
}
