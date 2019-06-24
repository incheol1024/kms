package com.devworker.kms.repo.common;

import com.devworker.kms.entity.UserDao;
import com.devworker.kms.repo.UserAttrRepo;
import com.devworker.kms.repo.UserRepo;
import com.devworker.kms.util.CommonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = DataLayerTestConfg.class)
public class UserAttrRepoTest {

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserAttrRepo userAttrRepo;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void notNullTest() {

        assertThat(userAttrRepo).isNotNull();
        assertThat(userRepo).isNotNull();
        assertThat(testEntityManager).isNotNull();
    }

    @Test
    @WithMockUser(username = "USER")
    public void findById() {

        Optional<UserDao> optionalUserDao = userRepo.findById(CommonUtil.getCurrentUser());

        UserDao userDao = optionalUserDao.get();
        userAttrRepo.findById(1L);
    }




}
