package com.devworker.kms.repo.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class BoardRepoTest {

	@Autowired
	BoardRepo boardRepo;
	
	
	@Test
	public void findByIdTest() {
		assertThat(boardRepo.findById(5L).get()).isNotNull();
	}

}
