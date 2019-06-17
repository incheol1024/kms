package com.devworker.kms.repo.common;

import com.devworker.kms.component.BoardComponent;
import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.repo.qna.QnaRepoImpl;
import com.devworker.kms.repo.solution.SolutionRepoImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class QnaRepoTest {

    @Autowired
    BoardComponent boardComponent;

    @Test
    public void nullTest() {

        assertThat(boardComponent).isNotNull();
    }

    @Test
    public void getPageTest() {

        PageRequest pageRequest = PageRequest.of(1, 5, Sort.Direction.DESC, "boardId");
        QnaRepoImpl qnaRepo = new QnaRepoImpl();

        Page<BoardDao> boardDaoPage = qnaRepo.getPage(5, pageRequest);

        boardDaoPage.forEach(System.out::println);

    }

    @Test
    public void otherTest( ) {
        PageRequest pageRequest = PageRequest.of(1, 5, Sort.Direction.DESC, "boardId");

        SolutionRepoImpl solutionRepo = new SolutionRepoImpl();
        solutionRepo.getPageList(2, pageRequest);


    }
}
