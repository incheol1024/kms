package com.devworker.kms.controller;

import com.devworker.kms.dto.GroupDto;
import com.devworker.kms.dto.GroupNUserDto;
import com.devworker.kms.service.GroupService;
import com.devworker.kms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
	public int addGroup(@Valid @RequestBody GroupDto dto) {
		return groupService.addGroup(dto);
	}
	
	@DeleteMapping("{groupId}")
	public void deleteGroup(@PathVariable int groupId) {
		groupService.deleteGroup(groupId);
	}
	
	@PostMapping
	public void updateGroup(@Valid @RequestBody GroupDto dto) {
		groupService.updateGroup(dto);
	}
	
	@GetMapping("{groupId}")
	public GroupDto getGroup(@PathVariable int groupId) {
		return groupService.getGroup(groupId);
	}
	
	@GetMapping("/childGroup/{groupId}")
	public Page<GroupDto> getGroupChild(@PathVariable int groupId, Pageable pageable) {
		return groupService.getGroupChild(groupId,pageable);
	}
	
	@GetMapping
	public GroupDto getAllGroupList() {
		return groupService.getAllGroupTree();
	}

	@GetMapping("/list")
	public Page<GroupDto> getAllGroupTree(Pageable pageable){
		return groupService.getAllGroupList(pageable);
	}

	@GetMapping("/child/{groupId}")
	public GroupNUserDto getAllChildList(@PathVariable int groupId,Pageable pageable) {
		GroupNUserDto groupNUserDto = new GroupNUserDto();
		groupNUserDto.setGroupList(groupService.getGroupChild(groupId, pageable));
		groupNUserDto.setUserList(userService.getGroupedUser(groupId, pageable));
		return groupNUserDto;
	}
}
