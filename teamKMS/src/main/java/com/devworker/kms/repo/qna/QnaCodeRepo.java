package com.devworker.kms.repo.qna;

import java.util.List;
import java.util.Optional;

import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.qna.QnaCodeDaoId;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devworker.kms.entity.MenuDao;
import com.devworker.kms.entity.qna.QnaCodeDao;

public interface QnaCodeRepo extends JpaRepository<QnaCodeDao, QnaCodeDaoId> {

    List<QnaCodeDao> findByMenuId(MenuDao menuId);

    Optional<QnaCodeDao> findByBoardIdEquals(BoardDao boardDao);
}
