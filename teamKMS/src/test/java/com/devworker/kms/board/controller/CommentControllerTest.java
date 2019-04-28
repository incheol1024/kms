package com.devworker.kms.board.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.devworker.kms.component.CommentComponent;
import com.devworker.kms.controller.common.CommentController;
import com.devworker.kms.dto.common.CommentDto;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	CommentComponent commentService;

	@Autowired
	CommentController commentController;
	
	
	static CommentDto commentDto;
	@Before
	public void setUp() {
		commentDto = new CommentDto();
		commentDto.setBoardId(40);
		commentDto.setCmtContents("test contents");
		commentDto.setCmtDate(LocalDateTime.now());
		
	}
	@Test
	public void notNullCommentController() throws Exception {
		assertThat(commentController).isNotNull();
	}
	
	@Test
	@WithMockUser
	public void shouldReturnCommentDto() throws Exception {
		
		this.mvc.perform(get("/comment/list/40")).andDo(print()).andExpect(status().isOk())
		.andExpect(content().string(containsString("comment")));
	}

}
