package com.devworker.kms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devworker.kms.dao.UserDao;
import com.devworker.kms.service.UserService;

@RestController
public class UserController {
	@Autowired
	UserService service;	
	
	@PostMapping("/addUser")
	public void addUser(UserDao dao) {
		service.addUser(dao);
	}
	
	@PostMapping("/deleteUser")
	public void deleteUser(UserDao dao) {
		service.deleteUser(dao);
	}
	
	@PostMapping("/updateUser")
	public void updateUser(UserDao dao) {
		service.updateUser(dao);
	}
	
	@PostMapping("/getUser")
	public UserDao getUser(UserDao dao) {
		return service.getUser(dao);
	}
	
	@PostMapping("/getGroupedUser")
	public Page<UserDao> getGroupedUser(UserDao dao,Pageable p) {
		return service.getGroupedUser(dao, p);
	}
	
	@PostMapping("/getUserList")
	public Page<UserDao> getUserList(Pageable p) {
		return service.getUserList(p);
	}
	
	@GetMapping("/getTotalUser")
	public long getUserList() {
		return service.getCount();
	}
	
}
