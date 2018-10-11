package com.devworker.kms.repo;

import org.springframework.data.repository.CrudRepository;

import com.devworker.kms.dao.UserDao;

public interface UserRepo extends CrudRepository<UserDao, Integer>{

}
