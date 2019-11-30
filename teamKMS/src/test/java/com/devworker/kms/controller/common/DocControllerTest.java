package com.devworker.kms.controller.common;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.devworker.kms.component.FileHandlerImplLocal;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(value = SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class DocControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	FileHandlerImplLocal fileHandler;

	@Autowired
	ObjectMapper objectMapper;



	private MockMultipartFile mockMultipartFile;

	@Before
	public void setUp() throws IOException {
		Path path = Paths.get("D:/app/filetest.txt");
		String name = "multiPartFile";
		String fileName = path.getFileName().toString();
		String contentType = "text/plain";
		InputStream inputStream = Files.newInputStream(path, StandardOpenOption.READ);
		mockMultipartFile = new MockMultipartFile(name, fileName, contentType, inputStream);
		System.out.println("=============mockMultiPartFileSize========="  + mockMultipartFile.getSize());
	}


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
	public void uploadFileTest() throws Exception {

		FileInputStream fileInputStream = new FileInputStream(new File("D:/app/test.png"));
		MockMultipartFile mockMultipartFile = new MockMultipartFile("fileTest", fileInputStream);

		this.mockMvc
				.perform(MockMvcRequestBuilders.multipart("/file/upload/comment").file(mockMultipartFile)
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.MULTIPART_FORM_DATA)
						.param("boardId", "1"))
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("fileTransactKey", Is.is("").matches("")).isString())
				.andExpect(MockMvcResultMatchers.jsonPath("fileCount", Is.is(0).matches(0)).isNumber())
				.andDo(MockMvcResultHandlers.print()).andReturn();

		this.mockMvc
				.perform(MockMvcRequestBuilders.multipart("/file/upload/comment").content("testString")
						.accept(MediaType.APPLICATION_JSON_UTF8).contentType(MediaType.MULTIPART_FORM_DATA)
						.param("boardId", "1"))
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("fileTransactKey", Is.isA(String.class)))
				.andExpect(MockMvcResultMatchers.jsonPath("fileCount", Is.is(0))).andDo(MockMvcResultHandlers.print())
				.andReturn();

	}

	@WithMockUser(username = "USER")
	@Test
	public void b_listFileTest() throws UnsupportedEncodingException, Exception {

		String resultString = this.mockMvc.perform(get("/file/list/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		JSONParser jsonParser = new JSONParser();
		JSONArray jsonArray = (JSONArray) jsonParser.parse(resultString);

		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);

			assertThat(jsonObject.get("boardId")).isNotNull().isInstanceOf(Long.class);
			assertThat(jsonObject.get("docId")).isNotNull().isInstanceOf(Long.class);
			assertThat(jsonObject.get("docPath")).isNotNull().isInstanceOf(String.class);
			assertThat(jsonObject.get("docUserId")).isNotNull().isInstanceOf(String.class);
			assertThat(jsonObject.get("docSize")).isNotNull().isInstanceOf(Long.class);

		}

	}

	@WithMockUser(username = "USER")
	@Test
	public void c_downloadTest() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/file/download/256"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(
						MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_OCTET_STREAM))
				.andDo(MockMvcResultHandlers.print());

	}


	@WithMockUser(username = "USER")
	@Test
	public void uploadFileForCheckingFileSize() throws Exception {

///upload/comment

        Path path = Paths.get("D:/app/filetest.txt");
        String name = "multiPartFile";
        String fileName = path.getFileName().toString();
        String contentType = "text/plain";
        InputStream inputStream = Files.newInputStream(path, StandardOpenOption.READ);
        mockMultipartFile = new MockMultipartFile(name, fileName, contentType, inputStream);

		this.mockMvc
				.perform(MockMvcRequestBuilders.multipart("/file/upload/comment").file(mockMultipartFile)
                .file("multiPartFile", "aaa".getBytes()))
                .andExpect(MockMvcResultMatchers.status().is(200))
				.andDo(print());

	}

}
