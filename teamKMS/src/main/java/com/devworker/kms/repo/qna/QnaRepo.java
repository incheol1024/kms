package com.devworker.kms.repo.qna;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devworker.kms.dao.qna.QnaDao;

public interface QnaRepo extends JpaRepository<QnaDao, Long> {

}
