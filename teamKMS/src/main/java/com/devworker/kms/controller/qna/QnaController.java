package com.devworker.kms.controller.qna;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.service.qna.QnaService;

@RestController
@RequestMapping("/qna")
public class QnaController {

	private Logger logger = LoggerFactory.getLogger(QnaController.class);

	@Autowired
	QnaService qnaService;

	@GetMapping("/{menuId}")
	public List<BoardDao> qnaHome(@PathVariable int menuId) {
		return qnaService.getFirstPageList(menuId);
	}

	@PostMapping("/register/{menuId}")
	public String createQuestion(@RequestBody BoardDao boardDao, @PathVariable int menuId) {
		logger.debug("BoardDao message Converting {} ", boardDao);
		logger.debug("PathVariable data binding {} ", menuId);
		qnaService.createQuestion(boardDao, menuId);
		return boardDao.toString();
	}

	@GetMapping("/answer/{id}")
	public BoardDao getQnaById(@PathVariable Long id) {
		return qnaService.findById(id);
	}

}
