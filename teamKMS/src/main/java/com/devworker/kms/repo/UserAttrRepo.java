package com.devworker.kms.repo;

import com.devworker.kms.entity.UserAttrDao;
import com.devworker.kms.entity.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAttrRepo extends JpaRepository<UserAttrDao, Long> {

    Optional<UserAttrDao> findByUserIdEquals(UserDao userDao);
}
