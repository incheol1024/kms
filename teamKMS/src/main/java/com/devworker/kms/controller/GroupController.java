package com.devworker.kms.controller;

import com.devworker.kms.dto.GroupDto;
import com.devworker.kms.dto.GroupNUserDto;
import com.devworker.kms.service.GroupService;
import com.devworker.kms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {
	private final
	GroupService groupService;
	private final
	UserService userService;

	@Autowired
	public GroupController(GroupService groupService, UserService userService) {
		this.groupService = groupService;
		this.userService = userService;
	}

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
	public GroupDto getGroup(@PathVariable int groupId) {
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
