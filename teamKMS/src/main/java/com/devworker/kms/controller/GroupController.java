package com.devworker.kms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.devworker.kms.dao.GroupDao;
import com.devworker.kms.dto.GroupDto;
import com.devworker.kms.dto.GroupNUserDto;
import com.devworker.kms.service.GroupService;
import com.devworker.kms.service.UserService;

@RestController
@RequestMapping("group")
public class GroupController {
	@Autowired
	GroupService groupService;
	@Autowired 
	UserService userService;

	@PutMapping
	public int addGroup(@RequestBody GroupDto dto) {
		return groupService.addGroup(dto);
	}
	
	@DeleteMapping("{groupId}")
	public void deleteGroup(@PathVariable int groupId) {
		groupService.deleteGroup(groupId);
	}
	
	@PostMapping
	public void updateGroup(@RequestBody GroupDto dto) {
		groupService.updateGroup(dto);
	}
	
	@GetMapping("{groupId}")
	public GroupDao getGroup(@PathVariable int groupId) {
		return groupService.getGroup(groupId);
	}
	
	@GetMapping("/childGroup/{groupId}")
	public List<GroupDto> getGroupChild(@PathVariable int groupId) {
		return groupService.getGroupChild(groupId);
	}
	
	@GetMapping
	public GroupDto getAllGroupList() {
		return groupService.getAllGroupList();
	}

	@GetMapping("/child/{groupId}")
	public GroupNUserDto getAllChildList(@PathVariable int groupId) {
		GroupNUserDto groupNUserDto = new GroupNUserDto();
		groupNUserDto.setGroupList(groupService.getGroupChild(groupId));
		groupNUserDto.setUserList(userService.getGroupedUser(groupId));
		return groupNUserDto;
	}
}
