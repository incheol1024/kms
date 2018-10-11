package com.devworker.kms.repo;

import org.springframework.data.repository.CrudRepository;

import com.devworker.kms.dao.UserDto;

public interface UserRepo extends CrudRepository<UserDto, Integer>{

}
