package com.devworker.kms.repo.board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devworker.kms.dao.MenuDao;
import com.devworker.kms.dao.board.QnaCodeDao;

public interface QnaCodeRepo extends JpaRepository<QnaCodeDao, String> {

	public List<QnaCodeDao> findByMenuId(MenuDao menuId);
	
}
