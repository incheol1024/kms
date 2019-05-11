package com.devworker.kms.repo.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class BoardRepoImplTest {
    @Autowired
    BoardRepoImpl boardRepo;

    @Test
    public void test() {
        assertNotNull(boardRepo.test());
    }
}