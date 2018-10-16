package com.devworker.kms.controller.qna;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


public class QnaControllerTest {

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
