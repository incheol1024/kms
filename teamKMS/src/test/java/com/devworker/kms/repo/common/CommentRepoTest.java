package com.devworker.kms.repo.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.*;

import com.devworker.kms.entity.common.DocDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.common.CommentDao;

@SpringBootTest
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Import(value = DataLayerTestConfg.class)
@RunWith(SpringRunner.class)
public class CommentRepoTest {

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    DocRepo docRepo;

    @Test
    public void commentRepo_추가쿼리메소드_테스트() {

        BoardDao board = new BoardDao();
        board.setBoardId(1);

        assertThat(commentRepo.findByBoardId(board));

        Iterator<CommentDao> it = commentRepo.findByBoardId(board).iterator();

        while (it.hasNext()) {
            System.out.println(it.next().toString());
        }
        assertThat(commentRepo.findByBoardId(board).size());
    }


    @Test
    public void commentRepo_업데이트_테스트() {

        Optional<CommentDao> opComment = commentRepo.findById(157L);
        CommentDao comment = opComment.get();

        comment.setCmtContents("변경 코멘트...");

        commentRepo.save(comment);

    }

    @Test
    public void commentRepo_BoarId_객체연관관계_테스트() {

        CommentDao comment = new CommentDao();
        BoardDao boardDao = new BoardDao();
        boardDao.setBoardId(1);
        comment.setBoardId(boardDao);
        comment.setCmtContents("aabbcc");
        //comment.setCmtDate(LocalDateTime.now());
        comment.setCmtUserId("USER");

//        CommentDao savedComment = commentRepo.save(comment);
//
//        assertThat(savedComment).isNotNull()
//                .isExactlyInstanceOf(commentRepo.findById(savedComment.getCmtId()).get().getClass());

    }

    @Test
    public void commentRepo_좋아요_테스트() {
		/*
		CommentDao comment = commentRepo.findById(162).get();
		int oriLikeNumber = comment.getCmtLike();
		comment.setCmtLike(1);
		
		CommentDao savedComment = commentRepo.save(comment);
		
		assertThat(savedComment).isNotNull();
		assertThat(oriLikeNumber + 1).isEqualByComparingTo(savedComment.getCmtLike());
		*/
    }

    @Test
    public void commentRepo_oneToMany_Test() {

        BoardDao boardDao = new BoardDao();
        boardDao.setBoardId(40);
        List<CommentDao> commentDaos = commentRepo.findByBoardId(boardDao);
        commentDaos.stream()
                .forEach(commentDao -> commentDao.getDocDaos().stream()
                        .forEach(docDao -> System.out.println(docDao.getDocId())));
    }


    @Test
    public void boardId_FetchTypeLazy() {
        BoardDao boardDao = new BoardDao();
        boardDao.setBoardId(40L);
        commentRepo.findByBoardId(boardDao);

    }

    @Test
    public void docId_innerJoinTest() {
        BoardDao boardDao = new BoardDao();
        boardDao.setBoardId(40L);
        commentRepo.findByBoardId(boardDao);

    }

    @Test
    public void pagingQueryTest() {

        BoardDao boardDao = new BoardDao();
        boardDao.setBoardId(40L);
        Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
//        commentRepo.findAll(firstPageWithTwoElements);
        Page<CommentDao> commentDaoPage = commentRepo.findByBoardIdEquals(boardDao, firstPageWithTwoElements);

        commentDaoPage.stream().forEach(commentDao -> {
            System.out.println(commentDao);
        });
    }

    @Test
    public void temporalTest() {


        CommentDao commentDao = new CommentDao();
        commentDao.setCmtUserId("USER");
        commentDao.setCmtContents("aaa");

        BoardDao boardDao = new BoardDao();
        boardDao.setBoardId(40L);
        commentDao.setBoardId(boardDao);
        commentDao.setCmtDate(new Date());

        commentRepo.save(commentDao);
    }

    @Test
    public void docJointableTest() {

        CommentDao commentDao = new CommentDao();
        commentDao.setCmtDate(new Date());
        commentDao.setCmtContents("joinTableTest");
        commentDao.setCmtUserId("USER");
        commentDao.setBoardId(new BoardDao(190L));


        DocDao docDao = docRepo.findById(293L).orElseThrow(IllegalArgumentException::new);
        commentDao.addDoc(docDao);
        commentRepo.save(commentDao);
    }
}
