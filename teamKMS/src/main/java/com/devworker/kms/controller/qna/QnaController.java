package com.devworker.kms.controller.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.devworker.kms.service.qna.QnaService;

@Controller
@RequestMapping("/qna")
public class QnaController {

	@Autowired
	QnaService qnaService;
	
	/*
	 * QnA 홈 컨트롤러 입니다. 
	 * URL : /qna
	 * 
	*/
	@GetMapping("")
	public String qnaHome(Model model) {
		model.addAttribute("boardList", qnaService.getFirstPageList());
		return "/qna/qnahome.html";
	}

	/*
	 * QnA 홈에서 search 버튼을 클릭했을때의 컨트롤러입니다.  URL /qna/search
	*/
	@GetMapping("/search")
	public String qnaSearchResult() {
		System.out.println("/qna/search");
		return "/qna/qnasearch.html";
	}

	/*
	 * 질문하기 화면으로 들어가는 컨트롤러입니다. URL /qna/ask
	*/
	@GetMapping("/ask")
	public String qnaAsk() {
		return "";
	}

	/*
	 *  검색 결과에서 원하는 답변을 클릭했을 때 페이지로 들어가는 컨트롤러입니다. /qna/{number}
	*/
/*
	@GetMapping("{number}")
	public String qnaChooseResult(@PathVariable long id) {
		return "";
	}
	
*/	
}
