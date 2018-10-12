package com.devworker.kms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devworker.kms.dao.UserDao;
import com.devworker.kms.repo.UserRepo;

@Service("UserService")
public class UserServiceImpl implements UserService{
	@Autowired
	UserRepo repo;
	
	@Override
	public long countUser() {
		return repo.count();
	}
	
	public void addUser(UserDao dao) {
		repo.save(dao);
	}
	
	public void deleteUser(UserDao dao) {
		repo.delete(dao);
	}
	
	public void updateUser(UserDao dao) {
		if(repo.existsById(dao.getId()))
			repo.save(dao);
	}
}
