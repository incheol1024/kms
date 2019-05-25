package com.devworker.kms.controller.site;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import com.devworker.kms.dto.common.BoardDetailDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.devworker.kms.dto.common.BoardDto;
import com.devworker.kms.service.site.SiteService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SiteControllerTest {
	

	private MockMvc mock;
	
	@Autowired
	SiteService siteService;
	@Autowired
	SiteController siteController;
	
	@Before
	public void setUp() {
		this.mock = MockMvcBuilders.standaloneSetup(new SiteController()).build();
	}

	@Test
	public void site_AddBoardTest() {
		int siteId=9;
		int projectId=17;


		BoardDetailDto boardDto=new BoardDetailDto();
		boardDto.setSubject("0428Test");
		boardDto.setContents("TTTTTTTTTTTT");
		boardDto.setUserId("USER");
		boardDto.setRegDate(LocalDateTime.now());
		boardDto.setUpdDate(LocalDateTime.now());
		
		siteService.addBoard(boardDto, siteId, projectId);
		String res = null;
//		solutionService.registerSolution(boardDao, res);
	}
	@Test
	public void getboardTest() throws Exception {
		this.mock.perform(get("/site/9/17?page=0&size=10")).andDo(print()).andExpect(status().isOk())
		.andExpect(content().string(containsString("board")));
		
	}
	
	
}