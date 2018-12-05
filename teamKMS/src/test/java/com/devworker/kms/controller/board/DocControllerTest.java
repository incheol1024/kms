package com.devworker.kms.controller.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.devworker.kms.service.board.FileHandlerImplLocal;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@RunWith(value = SpringRunner.class)
@AutoConfigureMockMvc
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class DocControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	FileHandlerImplLocal fileHandler;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void _beanNotNullTest() {

		assertNotNull(mockMvc);
		assertNotNull(fileHandler);

	}

	@WithMockUser(username = "USER")
	@Test
	public void a_uploadCommentFileTest() throws Exception {

		MockMultipartFile mockFile = new MockMultipartFile("multiPartFile", "C:\\Users\\Incheol\\Pictures\\test.bmp",
				"text/plain", "testsetestset".getBytes());

		MockMultipartFile mockFile1 = new MockMultipartFile("multiPartFile", "cdcdcd".getBytes());

		List<MockMultipartFile> testFiles = new ArrayList<MockMultipartFile>();

		testFiles.add(mockFile);
		testFiles.add(mockFile1);

		this.mockMvc.perform(multipart("/file/upload/comment").file(mockFile1).param("boardId", "1")
				.param("cmtId", "125").contentType(MediaType.MULTIPART_FORM_DATA)).andExpect(status().isOk())
				.andDo(print());

	}

	@WithMockUser(username = "USER")
	@Test
	public void b_listFileTest() throws UnsupportedEncodingException, Exception {

		String resultString = this.mockMvc.perform(get("/file/list/1").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn().getResponse().getContentAsString();
		
		JSONParser jsonParser = new JSONParser();
		JSONArray jsonArray = (JSONArray)jsonParser.parse(resultString);
		
		for(int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = (JSONObject)jsonArray.get(i);
			
			assertThat(jsonObject.get("boardId")).isNotNull().isInstanceOf(Long.class);
			assertThat(jsonObject.get("docId")).isNotNull().isInstanceOf(Long.class);
			assertThat(jsonObject.get("docPath")).isNotNull().isInstanceOf(String.class);
			assertThat(jsonObject.get("docUserId")).isNotNull().isInstanceOf(String.class);
			assertThat(jsonObject.get("docSize")).isNotNull().isInstanceOf(Long.class);
			
		}
	}

	@Test
	public void b_uploadBoardFileTest() {

	}

	@Test
	public void c_downloadTest() {

	}

}
