package com.devworker.kms.repo.common;

import com.devworker.kms.entity.UserAttrDao;
import com.devworker.kms.entity.UserDao;
import com.devworker.kms.repo.UserAttrRepo;
import com.devworker.kms.repo.UserRepo;
import com.devworker.kms.util.CommonUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
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
    private UserRepo userRepo;

    @Autowired
    private UserAttrRepo userAttrRepo;

    @Autowired
    private TestEntityManager testEntityManager;

    private UserDao userDao;

    private UserAttrDao userAttrDao;

    @Rule
    public ExpectedException exceptionRule;

    @Before
    public void setUp() {

        userDao = userRepo
                .findById("USER").orElseGet(() -> {
                    UserDao userDao = new UserDao();
                    userDao.setId("ADMIN");
                    userDao.setName("ADMIN");
                    return userDao;
                });

        userAttrDao = new UserAttrDao();
        userAttrDao.setUserId(userDao);


        exceptionRule = ExpectedException.none();
    }

    @Test
    public void notNullTest() {

        assertThat(userAttrRepo).isNotNull();
        assertThat(userRepo).isNotNull();
        assertThat(testEntityManager).isNotNull();
        assertThat(userDao).isNotNull();
    }


    /******************************
     * UserAttrRepo Select Test
     ******************************/
    @Test
    @WithMockUser(value = "USER")
    public void findById() {

        Optional<UserDao> optionalUserDao = userRepo.findById(CommonUtil.getCurrentUser());

        UserDao userDao = optionalUserDao.get();
        userAttrRepo.findById(1L);
    }


    @Test
    @WithMockUser(value = "USER")
    public void findByUserIdEquals() {

        Optional<UserAttrDao> optionalUserAttrDao = userAttrRepo.findByUserIdEquals(userDao);
        UserAttrDao actual = optionalUserAttrDao.orElseThrow(RuntimeException::new);

        assertThat(actual)
                .isNotNull()
                .matches((assertActual) -> assertActual.getUserId().getId().equals(userDao.getId()));
    }


    /******************************
     * UserAttrRepo Insert Test
     ******************************/
    @Test
    @WithMockUser(value = "USER")
    public void createUserAttrTest() {

        UserAttrDao actaul = userAttrRepo.save(userAttrDao);

        assertThat(actaul).isNotNull().matches((actualUserAttr) ->
            userAttrDao.getUserId().equals(actualUserAttr.getUserId())
        );


    }

    @Test(expected = DataIntegrityViolationException.class)
    @WithMockUser(value = "USER")
    public void createUserAttrUniqueUserIdTest() {

        exceptionRule.expect(DataIntegrityViolationException.class);
        userAttrRepo.save(userAttrDao);
    }

    /******************************
     * UserAttrRepo Update Test
     ******************************/
    @Test
    @WithMockUser(value = "USER")
    public void updateUserAttr() {

        UserAttrDao newUserAttrDao = new UserAttrDao();
        newUserAttrDao.setUserId(userDao);
        newUserAttrDao.setUserAvatar(true);

        newUserAttrDao = userAttrRepo.save(newUserAttrDao);

        assertThat(newUserAttrDao).isNotNull();

    }

    /******************************
     * UserAttrRepo Delete Test
     ******************************/
    @Test
    @WithMockUser(value = "USER")
    public void deleteUserAttr() {

        userAttrRepo.delete(userAttrDao);
    }


}
