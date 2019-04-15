package com.devworker.kms.board.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.devworker.kms.component.CommentComponent;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	CommentComponent commentService;

	@Test
	public void addCommentTest() {
	
	}

}
