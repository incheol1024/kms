package com.devworker.kms.service.board.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.devworker.kms.dao.board.CommentDao;
import com.devworker.kms.repo.board.CommentRepo;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CommentRepoTest {


	@Autowired
	CommentRepo commentRepo;
	
	@Test
	public void commentRepo_추가쿼리메소드_테스트() {
		
		assertThat(commentRepo.findByBoardId(1));
		
		Iterator<CommentDao> it = commentRepo.findByBoardId(1).iterator();
				
		while(it.hasNext()) {
			System.out.println(it.next().toString());
		}
		assertThat(commentRepo.findByBoardId(1).size());
	}

}
