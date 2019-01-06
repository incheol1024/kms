package com.devworker.kms.controller;

import java.util.List;

import com.devworker.kms.service.acl.AclPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devworker.kms.dao.GroupDao;
import com.devworker.kms.dto.GroupDto;
import com.devworker.kms.dto.GroupNUserDto;
import com.devworker.kms.service.GroupService;
import com.devworker.kms.service.UserService;

@RestController
public class GroupController {
	@Autowired
	GroupService groupService;
	@Autowired 
	UserService userService;
	
	@PostMapping("/addGroup")
	public int addGroup(@RequestBody GroupDto dto) {
		return groupService.addGroup(dto);
	}
	
	@PostMapping("/deleteGroup")
	public void deleteGroup(@RequestBody GroupDto dto) {
		groupService.deleteGroup(dto.getId());
	}
	
	@PostMapping("/updateGroup")
	public void updateGroup(@RequestBody GroupDto dto) {
		groupService.updateGroup(dto);
	}
	
	@PostMapping("/getGroup")
	public GroupDao getGroup(@RequestBody GroupDto dto) {
		return groupService.getGroup(dto.getId());
	}
	
	@PostMapping("/getGroupChild")
	public List<GroupDto> getGroupChild(@RequestBody GroupDto dto) {
		return groupService.getGroupChild(dto.getId());
	}
	
	@PostMapping("/getAllGroupList")
	public GroupDto getAllGroupList() {
		return groupService.getAllGroupList();
	}

	
	@PostMapping("/getAllChildList")
	public GroupNUserDto getAllChildList(@RequestBody GroupDto dto) {
		GroupNUserDto groupNUserDto = new GroupNUserDto();
		groupNUserDto.setGroupList(groupService.getGroupChild(dto.getId()));
		groupNUserDto.setUserList(userService.getGroupedUser(dto.getId()));
		return groupNUserDto;
	}
}
