package com.devworker.kms.controller.qna;

import com.devworker.kms.dto.common.BoardDto;
import com.devworker.kms.entity.MenuDao;
import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.qna.QnaCodeDao;
import com.devworker.kms.repo.common.BoardRepo;
import com.devworker.kms.repo.qna.QnaCodeRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class QnaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    BoardRepo boardRepo;

    @Autowired
    QnaCodeRepo qnaCodeRepo;

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
    @WithMockUser(username = "USER")
    public void getQnaByIdTest() throws Exception {

        for (int menuId = MENU_JAVA; menuId <= MENU_OTHER; menuId++) {

            BoardDto randomBoardDto = randomBoardDto(menuId);

            this.mockMvc
                    .perform(
                            get("/qna/answer/" + randomBoardDto.getBoardId())
                                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    )
                    .andExpect(status().isOk())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(jsonPath("$.boardId", Matchers.equalTo((int) randomBoardDto.getBoardId())))
                    .andExpect(jsonPath("$.subject", Matchers.equalTo(randomBoardDto.getSubject())))
                    .andExpect(jsonPath("$.userId", Matchers.equalTo(randomBoardDto.getUserId())))
                    .andExpect(jsonPath("$.regDate", Matchers.equalTo(randomBoardDto.getRegDate().format(DateTimeFormatter.ofPattern("YYYY-MM-dd hh:mm:ss")))))
                    .andExpect(jsonPath("$.updDate", Matchers.equalTo(randomBoardDto.getUpdDate().format(DateTimeFormatter.ofPattern("YYYY-MM-dd hh:mm:ss")))))
                    .andExpect(jsonPath("$.hits", Matchers.equalTo(randomBoardDto.getHits() + 1)))
                    .andDo(print())
                    .andReturn();
        }

    }

    @Test
    @WithMockUser(username = "USER")
    public void deleteQuestionTest() throws Exception {

        BoardDto randomBoardDto = this.randomBoardDto(MENU_JAVA);

            this.mockMvc
                    .perform(
                            delete("/qna/delete/" + randomBoardDto.getBoardId())
                                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    )
                    .andExpect(status().isOk())
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print())
                    .andReturn();

    }

    private BoardDto randomBoardDto(int menuId) {

        MenuDao menuDao = new MenuDao();
        menuDao.setId(MENU_JAVA);
        List<QnaCodeDao> qnaCodeDaoList = qnaCodeRepo.findByMenuId(menuDao);
        int randomIndex = new Random().nextInt(qnaCodeDaoList.size());
        return qnaCodeDaoList
                        .get(randomIndex)
                        .getBoardId()
                        .getBoardDto();
    }


    private <T> String toJsonString(T... values) {

        String jsonString = "";
        for (T value : values) {
            try {
                jsonString += objectMapper.writeValueAsString(value);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return jsonString;
    }

}
