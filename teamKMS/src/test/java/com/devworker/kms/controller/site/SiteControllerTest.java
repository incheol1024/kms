package com.devworker.kms.controller.site;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.devworker.kms.dto.common.BoardDto;
import com.devworker.kms.service.site.SiteService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SiteControllerTest {
	
	@Autowired
	SiteService siteService;

	@Test
	public void site_AddBoardTest() {
		int siteId=9;
		int projectId=17;
		
		
		BoardDto boardDto=new BoardDto();
		boardDto.setSubject("0428Test");
		boardDto.setContents("TTTTTTTTTTTT");
		boardDto.setUserId("USER");
		boardDto.setRegDate(LocalDateTime.now());
		boardDto.setUpdDate(LocalDateTime.now());
		
		siteService.addBoard(boardDto, siteId, projectId);
		String res = null;
//		solutionService.registerSolution(boardDao, res);

	}
}