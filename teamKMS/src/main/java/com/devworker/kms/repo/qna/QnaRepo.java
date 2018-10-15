package com.devworker.kms.repo.qna;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devworker.kms.dto.qna.QnaDto;

public interface QnaRepo extends JpaRepository<QnaDto, Long> {

}
