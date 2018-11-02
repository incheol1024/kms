package com.devworker.kms.controller.qna;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.devworker.kms.service.qna.QnaService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QnaControllerTest {

	@Autowired
	QnaService qnaService;
	
	
	@Test
	public void qnaHomeTest() throws Exception {
		QnaController controller = new QnaController();
		MockMvc mock = MockMvcBuilders.standaloneSetup(controller).build();
		mock.perform(get("/qna")).andExpect(view().name("qna"));

	}

	@Test
	public void qnaSearchTest() throws Exception {
		QnaController controller = new QnaController();
		MockMvc mock = MockMvcBuilders.standaloneSetup(controller).build();
		mock.perform(get("/search")).andExpect(view().name(""));

	}

	@Test
	public void qnaAskTest() throws Exception {
		QnaController controller = new QnaController();
		MockMvc mock = MockMvcBuilders.standaloneSetup(controller).build();
		mock.perform(get("/qna")).andExpect(view().name(""));

	}

	@Test
	public void qnaChooseTest() throws Exception {
		QnaController controller = new QnaController();
		MockMvc mock = MockMvcBuilders.standaloneSetup(controller).build();
		mock.perform(get("/qna")).andExpect(view().name(""));

	}

	@After(value = "")
	public void tearDown() {

	}

}
