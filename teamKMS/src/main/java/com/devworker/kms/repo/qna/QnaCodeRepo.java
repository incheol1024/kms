package com.devworker.kms.repo.qna;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devworker.kms.entity.MenuDao;
import com.devworker.kms.entity.qna.QnaCodeDao;

public interface QnaCodeRepo extends JpaRepository<QnaCodeDao, String> {

	public List<QnaCodeDao> findByMenuId(MenuDao menuId);
	
}
