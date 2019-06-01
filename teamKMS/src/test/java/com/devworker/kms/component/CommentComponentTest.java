package com.devworker.kms.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;

import com.devworker.kms.component.CommentComponent;
import com.devworker.kms.dto.common.CommentDto;
import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.common.CommentDao;
import com.devworker.kms.repo.common.CommentRepo;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.stream.events.Comment;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CommentComponentTest {

    //	@Mock
//	CommentComponent commentService;
//
//	// @Autowired
    @Autowired
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
        //commentDao.setCmtDate(LocalDateTime.now());
        commentDao.setCmtId(200);

    }

    @Test
    public void beanNotNullTest() {
//		System.out.println("=========================");
//		System.out.println(boardDao.toString());
//		System.out.println(commentDao.toString());
//		System.out.println("=========================");
//		assertThat(commentRepo).isNotNull();
//		assertThat(commentService).isNotNull();

    }


    @Test
    @WithMockUser(value = "USER")
    public void addCommentTest() throws Exception {

        //when(commentService.addComment(commentDto)).thenReturn(commentDao);

    }

    @Test
    public void findByBoardIdTest() throws Exception {

//		List<CommentDto> commentList = commentService.findByBoardId(boardDao);
//		when(commentService.findByBoardId(boardDao)).thenReturn(commentList);
//
//		assertThat(commentList).isNotNull().size().isLessThan(1);
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
    public void assertConvertPageContent() {
        BoardDao boardDao = new BoardDao();
        boardDao.setBoardId(40);

        List<CommentDao> commentDaos = commentRepo.findByBoardId(boardDao);
        //CommentDao commentDao = commentRepo.findByBoardId(boardDao).get(0);
        String iniContent = "aaa";
        PageImpl page = new PageImpl(commentDaos);

        System.out.println(page.getContent());

        Page convertedPage = page.map( (commentDao) -> {
            return new CommentDto((CommentDao) commentDao);
        });

        System.out.println(convertedPage.getContent());
    }

    @Test
    public void assertPageExpectedCommentDoc() {

        BoardDao boardDao = new BoardDao();
        boardDao.setBoardId(40);

        List<CommentDao> commentDaos = commentRepo.findByBoardId(boardDao);
        commentDaos.stream().forEach( commentDao -> System.out.println(commentDao.getDocDaos()));






    }

}
