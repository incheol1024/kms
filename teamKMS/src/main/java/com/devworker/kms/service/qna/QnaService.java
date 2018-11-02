package com.devworker.kms.service.qna;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devworker.kms.dto.qna.QnaDto;
import com.devworker.kms.repo.qna.QnaRepo;
import com.devworker.kms.util.CommonUtil;

@Service
public class QnaService {

	private Logger logger = LoggerFactory.getLogger(QnaService.class);

	@Autowired
	QnaRepo qnaRepo;

	public List<QnaDto> getFirstPageList() {
		return qnaRepo.findAll();
	}

	public QnaDto registerQuestion(QnaDto question) {
		int ret = 0;
		logger.debug(CommonUtil.getCurrentUser());

		question.setUserName(CommonUtil.getCurrentUser());
		System.out.println("last QanDto : " + question.toString());

		return qnaRepo.save(question);

	}

	public QnaDto findById(Long id) {
		return qnaRepo.getOne(id);
	}
}
