package com.devworker.kms.dto.common;

import com.devworker.kms.entity.common.CommentDao;
import com.devworker.kms.repo.common.CommentRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CommentDtoTest {

    @Autowired
    CommentRepo commentRepo;

    @Test
    public void assertCommentDtoSerialized() {

        Optional<CommentDao> optionalCommentDao = commentRepo.findById(370L);

        CommentDao commentDao = optionalCommentDao.get();
        System.out.println(commentDao.toString());


        CommentDto commentDto = new CommentDto(commentDao);
    }

}
