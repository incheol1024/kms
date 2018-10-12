package com.devworker.kms.service;

import com.devworker.kms.dao.UserDao;

public interface UserService {
	public long countUser();
	public void addUser(UserDao dao);
	public void deleteUser(UserDao dao);
	public void updateUser(UserDao dao);
}
