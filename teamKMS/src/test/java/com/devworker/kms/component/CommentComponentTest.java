package com.devworker.kms.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.devworker.kms.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;

import com.devworker.kms.component.CommentComponent;
import com.devworker.kms.dto.common.CommentDto;
import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.common.CommentDao;
import com.devworker.kms.repo.common.CommentRepo;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.stream.events.Comment;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CommentComponentTest {

    @MockBean
    CommentRepo commentRepo;

    @MockBean
    DocComponent docComponent;

    @MockBean
    UserService userService;

    @MockBean
    BoardComponent boardComponent;

    @Autowired
    CommentComponent commentComponent;

    @Mock
    private CommentDao commentDao;

    @Mock
    private CommentDto commentDto;

    @Before
    public void setUp() {

        commentDto = mock(CommentDto.class);
        given(commentDto.getCmtContents()).willReturn("mockito Contents");
        given(commentDto.getCmtId()).willReturn(200L);
        given(commentDto.getBoardId()).willReturn(40L);

        commentDao = mock(CommentDao.class);
        given(commentDao.getCmtContents()).willReturn("mockito Contents");
        given(commentDao.getCmtId()).willReturn(200L);
        given(commentDao.getBoardId()).willReturn(new BoardDao(40L));
        given(commentDao.getCommentDto()).willReturn(commentDto);
    }

    @Test
    public void beanNotNullTest() {
        assertThat(commentRepo).isNotNull();
        assertThat(commentComponent).isNotNull();
        assertThat(docComponent).isNotNull();
        assertThat(userService).isNotNull();
        assertThat(boardComponent).isNotNull();
    }


    @Test
    @WithMockUser(value = "USER")
    public void addCommentTest() {
        given(commentRepo.save(any(CommentDao.class))).willReturn(commentDao);
        when(commentComponent.addComment(commentDao.getCommentDto())).thenReturn(commentDto);
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

        Page convertedPage = page.map((commentDao) -> {
            return new CommentDto((CommentDao) commentDao);
        });

        System.out.println(convertedPage.getContent());
    }

    @Test
    public void assertPageExpectedCommentDoc() {

        BoardDao boardDao = new BoardDao();
        boardDao.setBoardId(40);

        List<CommentDao> commentDaos = commentRepo.findByBoardId(boardDao);
        commentDaos.stream().forEach(commentDao -> System.out.println(commentDao.getDocDaos()));


    }

}
