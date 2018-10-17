package com.devworker.kms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<UserDao> getGroupedUser(UserDao dao) {
		return service.getGroupedUser(dao);
	}
	
	@PostMapping("/getUserList")
	public List<UserDao> getUserList() {
		return service.getUserList();
	}
	
	@GetMapping("/getTotalUser")
	public long getTotalUser() {
		return service.getCount();
	}
	
}
