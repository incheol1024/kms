package com.devworker.kms.repo.common;

import static org.assertj.core.api.Assertions.assertThat;

import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.common.DocDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class BoardRepoTest {

	@Autowired
	BoardRepo boardRepo;

	@Autowired
	DocRepo docRepo;
	
	@Test
	public void findByIdTest() {
		assertThat(boardRepo.findById(5L).get()).isNotNull();
	}

	@Test
	public void docJointableTest() {

		BoardDao boardDao = new BoardDao();
		boardDao.setSubject("zztest");
		boardDao.setContents("test");
		boardDao.setRegDate(LocalDateTime.now());
		boardDao.setUpdDate(LocalDateTime.now());
		boardDao.setUserId("USER");


		DocDao docDao = docRepo.findById(293L).orElseThrow(IllegalArgumentException::new);
		boardDao.addDoc(docDao);

		boardDao = boardRepo.save(boardDao);

		assertThat(boardDao).isNotNull();

	}

}
