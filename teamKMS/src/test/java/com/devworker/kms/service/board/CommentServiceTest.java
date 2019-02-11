package com.devworker.kms.service.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import com.devworker.kms.entity.board.BoardDao;
import com.devworker.kms.entity.board.CommentDao;
import com.devworker.kms.repo.board.CommentRepo;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest {

	@Mock
	CommentService commentService;

	// @Autowired
	@Mock
	CommentRepo commentRepo;

	private static CommentDao commentDao;
	private static BoardDao boardDao;
	
	@Before
	public void setUp() {
		//boardDao = mock(BoardDao.class);
		boardDao = new BoardDao();
		boardDao.setBoardId(1);
		commentDao = mock(CommentDao.class);
		commentDao.setBoardId(boardDao);
		commentDao.setCmtContents("Comment Service Test Data");
		commentDao.setCmtUserId("USER");
		commentDao.setCmtDate(LocalDateTime.now());
		commentDao.setCmtId(200);
		
	}

	@Test
	public void beanNotNullTest() {
		System.out.println("=========================");
		System.out.println(boardDao.toString());
		System.out.println(commentDao.toString());
		System.out.println("=========================");
		assertThat(commentRepo).isNotNull();
		assertThat(commentService).isNotNull();
		
	}

	
	@Test
	@WithMockUser(value = "USER")
	public void addCommentTest() throws Exception {
		
		when(commentService.addComment(commentDao)).thenReturn(commentDao);
		
	}

	@Test
	public void findByBoardIdTest() throws Exception {

		List<CommentDao> commentList = commentService.findByBoardId(boardDao);
		when(commentService.findByBoardId(boardDao)).thenReturn(commentList);
	
		assertThat(commentList).isNotNull().size().isLessThan(1);
	}

	@Test
	public void updateCommentTest() throws Exception {

	}

	@Test
	public void deleteCommentTest() {

	}

	@Test
	public void updateCommentLikeTest() {
/*
		int commentLikeNumber = commentDao.getCmtLike();
		CommentDao newComment = commentService.updateCommentLike(commentDao);
		assertThat(newComment).isNotNull();*/

	}

	@Test
	public void updateCommentUnLikeTest() {

	}


	}
