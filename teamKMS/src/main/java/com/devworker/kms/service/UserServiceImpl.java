package com.devworker.kms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devworker.kms.repo.UserRepo;

@Service("UserService")
public class UserServiceImpl implements UserService{
	@Autowired
	UserRepo repo;
	
	@Override
	public long countUser() {
		return repo.count();
	}
}
