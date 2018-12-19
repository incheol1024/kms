package com.devworker.kms.service.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.devworker.kms.dao.UserDao;
import com.devworker.kms.dao.board.BoardDao;
import com.devworker.kms.dao.board.CommentDao;
import com.devworker.kms.exception.NotExistException;
import com.devworker.kms.repo.board.CommentRepo;
import com.devworker.kms.util.CommonUtil;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CommentServiceTest {

	@Autowired
	CommentService commentService; 

	@Autowired
	CommentRepo commentRepo;
	
	public static CommentDao commentDao;
	public static BoardDao boardDao;
	
	@Before
	public void setUp() {
		commentDao = commentRepo.getOne(157);
	}
	
	@Test
	public void addCommentTest() throws Exception {
	
	}

	@Test
	public void findByBoardIdTest() throws Exception {
	
	}

	@Test
	public void updateCommentTest() throws Exception {

	}

	@Test
	public void deleteCommentTest() {
	
	}

	@Test
	public void updateCommentLikeTest() {
		
		int commentLikeNumber = commentDao.getCmtLike();
		CommentDao newComment = commentService.updateCommentLike(commentDao);
		assertThat(newComment).isNotNull();
	
	}
	
	@Test
	public void updateCommentUnLikeTest(CommentDao commentDao) {
	
	}

}
