package com.devworker.kms.board.controller;

import com.devworker.kms.component.CommentComponent;
import com.devworker.kms.controller.common.CommentController;
import com.devworker.kms.dto.common.CommentDto;
import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.common.CommentDao;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import springfox.documentation.spring.web.json.Json;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;

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
//        commentDto = new CommentDto();
//        commentDto.setBoardId(40);
//        commentDto.setCmtContents("test contents");
//        commentDto.setCmtDate(LocalDateTime.now());
//
//        pagebleJsonString = "{page:}";

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
    public void assertPageReturnValue() throws Exception {

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
//        Page<CommentDao> page = commentController.listComment(boardDao, PageRequest.of(1, 3));
//        assertThat(page).isExactlyInstanceOf(PageImpl.class);
    }

    @Test
    @WithMockUser(username = "USER")
    public void assertPageImplJsonValue() throws Exception {

        MvcResult mvcResult = this.mvc
                .perform(MockMvcRequestBuilders.get("/comment/list/40")
                        .param("page", "1")
                        .param("size", "3")
                        .param("sort", "cmtId,asc")
                )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        assertThat(objectMapper).isNotNull();
        String resopnseJsonString = mvcResult.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(resopnseJsonString);
        Iterator<Map.Entry<String, JsonNode>> it = jsonNode.fields();

        while(it.hasNext()) {
            Map.Entry entry = it.next();
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    @Test
    @WithMockUser(username = "USER")
    public void testMapJson() throws Exception {

        this.mvc.perform(get("/comment/test"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                ;

    }

}
