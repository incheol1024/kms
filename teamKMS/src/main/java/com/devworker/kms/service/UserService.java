package com.devworker.kms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.devworker.kms.dao.UserDao;

public interface UserService {
	public long countUser();
	public void addUser(UserDao dao);
	public void deleteUser(UserDao dao);
	public void updateUser(UserDao dao);
	public UserDao getUser(UserDao dao);
	public Page<UserDao> getGroupedUser(UserDao dao,Pageable p);
	public Page<UserDao> getUserList(Pageable p);
	public long getCount();
}
