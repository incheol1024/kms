package com.devworker.kms.service.board.repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.devworker.kms.dao.board.BoardDao;
import com.devworker.kms.dao.board.CommentDao;
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
		
		BoardDao board = new BoardDao();
		board.setBoardId(1);
		
		List<DocDao> list = docRepo.findByBoardId(board);
		
		System.out.println(list.size());
		
		assertNotNull(list);
		
	}
	
	@Test
	public void boardId_commentId_객체연관관계_테스트() {
		
		BoardDao board = new BoardDao();
		board.setBoardId(1);
		
		CommentDao comment = new CommentDao();
		comment.setBoardId(board);
		comment.setCmtId(157);
			
		DocDao doc = new DocDao();
		doc.setBoardId(board);
		doc.setCommentId(comment);
		doc.setDocPath("testPath");
		doc.setDocSize(5);
		doc.setDocUserId("USER");
		
		assertThat(docRepo.save(doc)).isNotNull().isEqualTo(doc);
		
	}

}
