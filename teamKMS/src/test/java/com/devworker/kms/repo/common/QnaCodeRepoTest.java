package com.devworker.kms.repo.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.devworker.kms.entity.MenuDao;
import com.devworker.kms.repo.qna.QnaCodeRepo;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.devworker.kms.entity.qna.QnaCodeDao;
import com.devworker.kms.repo.MenuRepo;

@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QnaCodeRepoTest {

	@Autowired
	QnaCodeRepo qnaCodeRepo;

	@Autowired
	BoardRepo boardRepo;

	@Autowired
	MenuRepo menuRepo;

	QnaCodeDao qnaCodeDao;
	MenuDao menuDao;
	static long boardId = 40;
	static int menuId = 5;

	@Before
	public void setUp() {

		qnaCodeDao = new QnaCodeDao();
		qnaCodeDao.setMenuId(menuDao);

//		menuDao = new MenuDao();
//		menuDao.setId(5);

		menuDao = menuRepo.findById(5).get();
	}


	@Test
	public void findByMenuId() {

		List<QnaCodeDao> qnaCodeDaos = qnaCodeRepo.findByMenuId(menuDao);
		qnaCodeDaos.forEach(System.out::println);

	}

	@Test
	public void _쿼리메소드테스트() {
		List<QnaCodeDao> list = qnaCodeRepo.findByMenuId(menuRepo.findById(menuId).get());
		assertThat(list.size()).isNotNull().isGreaterThan(0);
	}

	@Test
	public void A_QnaCodeCreate() {
		QnaCodeDao newQnaCodeDao = qnaCodeRepo.save(qnaCodeDao);
		assertThat(newQnaCodeDao).isNotNull();
		assertThat(newQnaCodeDao.getBoardId().getBoardId()).isEqualTo(boardId);
		assertThat(newQnaCodeDao.getMenuId().getId()).isEqualTo(menuId);
	}

	@Test
	public void B_QnaCodeRead() {
		// 신규 추가 쿼리 메소드로 대체함 - MenuId 로만 조회가 일어납니다.
	}

	@Test
	public void C_QnaCodeUpdate() {
		// QnaCode가 업데이트 되어서는 안됩니다. 매핑 테이블이기 때문에, 참조 테이블의 키 값 업데이트시 cascade 되어 DB내부적으로만
		// 업데이트해야함.
	}

	@Test
	public void D_QnaCodeDelete() {
		// 업데이트와 동일한 이유로 DB내부적으로만 삭제 해야함.
	}
}
