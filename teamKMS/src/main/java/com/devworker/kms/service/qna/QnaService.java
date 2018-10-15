package com.devworker.kms.service.qna;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devworker.kms.dto.qna.QnaDto;
import com.devworker.kms.repo.qna.QnaRepo;

@Service
public class QnaService {

	@Autowired
	QnaRepo qnaRepo;

	public List<QnaDto> getFirstPageList() {
		return qnaRepo.findAll();
	}
}
