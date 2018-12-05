package com.devworker.kms.service.board.repo;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.devworker.kms.dao.board.DocDao;
import com.devworker.kms.repo.board.DocRepo;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DocRepoTest {

	@Autowired
	DocRepo docRepo;
	
	@Test
	public void _beanNotNulltest() {
		assertNotNull(docRepo);
	}
	
	
	@Test
	public void a_findByboardId_Test() {
		
		List<DocDao> list = docRepo.findByBoardId(1);
		
		System.out.println(list.size());
		
		assertNotNull(list);
		
	}

}
