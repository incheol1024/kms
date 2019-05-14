package com.devworker.kms.board.controller;

import com.devworker.kms.component.CommentComponent;
import com.devworker.kms.controller.common.CommentController;
import com.devworker.kms.dto.common.CommentDto;
import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.common.CommentDao;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CommentComponent commentComponent;

    @Autowired
    CommentController commentController;

    @Autowired
    ObjectMapper objectMapper;

    private CommentDto commentDto;

    String pagebleJsonString;

    @Before
    public void setUp() {
        commentDto = new CommentDto();
        commentDto.setBoardId(40);
        commentDto.setCmtContents("test contents");
        commentDto.setCmtDate(LocalDateTime.now());

        pagebleJsonString = "{page:}";

    }

    @Test
    public void notNullObjectInjectedTest() throws Exception {
        assertThat(commentController).isNotNull();
        assertThat(commentComponent).isNotNull();
        assertThat(mvc).isNotNull();
        assertThat(objectMapper).isNotNull();
    }

    @Test
    @WithMockUser
    public void shouldReturnCommentDto() throws Exception {

        this.mvc.perform(get("/comment/list/40"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("comment")));

    }

    @Test
    @WithMockUser(username = "USER")
    public void pageReturnValueAssert() throws Exception {

        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders
                .get("/comment/list/40")
                .param("page", "1")
                .param("size", "3")
                .param("sort", "cmtId,asc"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(MockMvcResultHandlers.log()).andReturn();

        assertThat(mvcResult.getResponse().getContentType()).isEqualToIgnoringCase(MediaType.APPLICATION_JSON_UTF8_VALUE);

        String responseJson = mvcResult.getResponse().getContentAsString();

        assertThat(objectMapper.readTree(responseJson)).isNotNull();

        JsonNode jsonNode = objectMapper.readTree(responseJson);
        jsonNode.fields();
        Iterator<String> it = jsonNode.fieldNames();
        while (it.hasNext()) {
            System.out.println("json Field Name = " + it.next());
        }

    }

    @Test
    @WithMockUser(username = "USER")
    public void whatIsShapeOfJsonResponse() throws Exception {

        MvcResult mvcResult = this.mvc
                .perform(MockMvcRequestBuilders
                        .get("/comment/list/40")
                        .param("page", "1")
                        .param("size", "3")
                        .param("sort", "cmtId,asc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();


    }

    @Test
    @WithMockUser(username = "USER")
    public void shouldRetrunPageObject() {

        BoardDao boardDao = new BoardDao();
        boardDao.setBoardId(40);
        Page<CommentDao> page = commentController.listComment(boardDao, PageRequest.of(1,3));
        assertThat(page).isExactlyInstanceOf(PageImpl.class);
    }

}
