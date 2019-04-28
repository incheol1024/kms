
package com.devworker.kms.controller.solution;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.service.solution.SolutionService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SolutionControllerTest {
	@Autowired
	SolutionService solutionService;

	@Test
	public void Solution_getPageTest() {
		/*List<BoardDao> list = solutionService.getFirstPageList();

		System.out.println(list.size());

		assertNotNull(list);*/
	}

	@Test
	public void Solution_registerTest() {
		BoardDao boardDao = new BoardDao();
		String res = null;
//		solutionService.registerSolution(boardDao, res);

	}

}