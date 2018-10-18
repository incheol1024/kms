package com.devworker.kms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devworker.kms.dao.GroupDao;
import com.devworker.kms.dto.GroupDto;
import com.devworker.kms.service.GroupService;

@RestController
public class GroupController {
	@Autowired
	GroupService service;
	
	@PostMapping("/addGroup")
	public void addGroup(GroupDao dao) {
		service.addGroup(dao);
	}
	
	@PostMapping("/deleteGroup")
	public void deleteGroup(GroupDao dao) {
		service.deleteGroup(dao);
	}
	
	@PostMapping("/updateGroup")
	public void updateGroup(GroupDao dao) {
		service.updateGroup(dao);
	}
	
	@PostMapping("/getGroup")
	public GroupDao getGroup(GroupDao dao) {
		return service.getGroup(dao);
	}
	
	@PostMapping("/getGroupChild")
	public List<GroupDao> getGroupChild(GroupDao dao) {
		return service.getGroupChild(dao);
	}
	
	@PostMapping("/getAllGroupList")
	public GroupDto getAllGroupList() {
		return service.getAllGroupList();
	}

}
