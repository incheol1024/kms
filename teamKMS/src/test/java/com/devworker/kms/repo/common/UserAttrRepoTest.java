package com.devworker.kms.repo.common;

import com.devworker.kms.entity.UserDao;
import com.devworker.kms.repo.UserAttrRepo;
import com.devworker.kms.repo.UserRepo;
import com.devworker.kms.util.CommonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserAttrRepoTest {

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserAttrRepo userAttrRepo;

    @Test
    @WithMockUser(username = "USER")
    public void findById() {

        Optional<UserDao> optionalUserDao = userRepo.findById(CommonUtil.getCurrentUser());

        UserDao userDao =optionalUserDao.get();
        userAttrRepo.findById(1L);
    }
}
