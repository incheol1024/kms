package com.devworker.kms.controller.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.hamcrest.CoreMatchers;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.devworker.kms.dao.board.CommentDao;
import com.devworker.kms.repo.board.CommentRepo;
import com.devworker.kms.service.board.CommentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class CommentControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	CommentService commentService;

	@Autowired
	CommentRepo commentRepo;

	private static int cmtId;

	@Test
	public void _beanNotNullTest() {
		assertNotNull(mvc);
		assertNotNull(objectMapper);
		assertNotNull(commentService);
		assertNotNull(commentRepo);
	}

	@WithMockUser(value = "USER")
	@Test
	public void a_addCommentTest() throws Exception {
		CommentDao comment = new CommentDao();
		comment.setBoardId(1);
		comment.setCmtContents("test contents");
		comment.setCmtUserId("USER");

		ResultActions actions = this.mvc.perform(post("/comment/add").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(comment))).andExpect(status().isOk()).andDo(print());

		String resultString = actions.andReturn().getResponse().getContentAsString();
		JsonParser jsonParser = new JsonParser();
		JsonObject jObject = (JsonObject) jsonParser.parse(resultString);
		JsonElement jElement = jObject.get("cmtId");
		cmtId = jElement.getAsInt();

		assertNotNull(resultString);

	}

	@WithMockUser(value = "USER")
	@Test
	public void b_listCommentTest() throws UnsupportedEncodingException, Exception {

		String resultString = this.mvc.perform(get("/comment/1")).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString();

		JsonParser jsonParser = new JsonParser();
		JsonArray jsonArray = (JsonArray) jsonParser.parse(resultString);

		assertThat(jsonArray.size()).isGreaterThanOrEqualTo(1);

	}

	@WithMockUser(value = "USER")
	@Test
	public void c_updateCommentTest() throws Exception {
		CommentDao comment = new CommentDao();
		comment.setCmtId(cmtId);
		comment.setCmtContents("update test data");

		String resultString = this.mvc
				.perform(post("/comment/update").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(comment)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject) jsonParser.parse(resultString);
		JsonElement jsonElement = jsonObject.get("cmtContents");
		String responseContents = jsonElement.getAsString();

		assertEquals(comment.getCmtContents(), responseContents);

	}

	@WithMockUser(value = "USER")
	@Test
	public void d_deleteCommentTest() throws JsonProcessingException, Exception {

		String resultString = this.mvc
				.perform(delete("/comment/delete").contentType(MediaType.APPLICATION_JSON).param("cmtId",
						String.valueOf(cmtId)))
				.andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();

		assertThat(cmtId, CoreMatchers.is(Integer.valueOf(resultString)));
	}

}
