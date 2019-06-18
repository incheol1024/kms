package com.devworker.kms.controller.qna;

import com.devworker.kms.entity.common.BoardDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.entity.ContentType;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.awt.*;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class QnaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private static int MENU_JAVA;
    private static int MENU_CPP;
    private static int MENU_PYTHON;
    private static int MENU_CSHARP;
    private static int MENU_OTHER;

    @Before
    public void setUp() {
        MENU_JAVA = 4;
        MENU_CPP = 5;
        MENU_PYTHON = 6;
        MENU_CSHARP = 7;
        MENU_OTHER = 8;
    }


    @Test
    public void notNullTest() {
        Assertions.assertThat(this.mockMvc).isNotNull();
        Assertions.assertThat(this.objectMapper).isNotNull();
    }


    @Test
    @WithMockUser(value = "USER")
    public void findQnaPageTest() throws Exception {

        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/qna/5")
                                .param("page", "0")
                                .param("size", "5")
                                .param("sort", "boardId,DESC")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.content", Matchers.hasSize(5)))
                .andExpect(jsonPath("$.content[0].boardId", Matchers.greaterThan(180)))
                .andExpect(jsonPath("$.content[0].subject", Matchers.is(Matchers.containsString("a"))))
                .andExpect(jsonPath("$.content[0].userId", Matchers.is("USER")))
                .andExpect(jsonPath("$.content[0].regDate", Matchers.lessThanOrEqualTo(new Date().toString())))
                .andExpect(jsonPath("$.content[0].updDate", Matchers.lessThanOrEqualTo(new Date().toString())))
                .andExpect(jsonPath("$.content[0].hits", Matchers.greaterThanOrEqualTo(0)))
                .andDo(print())
                .andReturn();
    }

    @Test
    @WithMockUser(value = "USER")
    public void createQuestionTest() throws Exception {

//        subject: this.subject
//        contents: this.$refs.questionEditor.getText()

        BoardDao boardDao = new BoardDao();
        boardDao.setContents("contents Testing");
        boardDao.setSubject("subject Testing");

        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/qna/register/" + MENU_JAVA)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(this.toJsonString(boardDao))
                )
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful())
//                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.boardId", Matchers.greaterThan(180)))
                .andExpect(jsonPath("$.subject", Matchers.is(Matchers.equalTo(boardDao.getSubject()))))
                .andExpect(jsonPath("$.userId", Matchers.is(Matchers.equalTo("USER"))))
                .andExpect(jsonPath("$.regDate", Matchers.lessThanOrEqualTo(new Date().toString())))
                .andExpect(jsonPath("$.updDate", Matchers.lessThanOrEqualTo(new Date().toString())))
                .andExpect(jsonPath("$.hits", Matchers.greaterThanOrEqualTo(0)))
                .andDo(print())
                .andReturn();

    }

    @Test
    public void getQnaByIdTest() throws Exception {

    }

    @Test
    public void qnaChooseTest() throws Exception {

    }


    private <T> String toJsonString(T... values) {

        String jsonString = "";
        for(T value : values) {
            try {
                jsonString += objectMapper.writeValueAsString(value);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return jsonString;
    }

}
