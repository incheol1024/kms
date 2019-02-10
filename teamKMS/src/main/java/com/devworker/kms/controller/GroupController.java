package com.devworker.kms.controller;

import com.devworker.kms.dto.GroupDto;
import com.devworker.kms.dto.GroupNUserDto;
import com.devworker.kms.service.GroupService;
import com.devworker.kms.service.UserService;
import io.swagger.annotations.ApiOperation;
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

	@ApiOperation (value = "신규 그룹 추가")
	@PutMapping
	public int addGroup(@Valid @RequestBody GroupDto dto) {
		return groupService.addGroup(dto);
	}

	@ApiOperation (value = "특정 그룹 삭제")
	@DeleteMapping("{groupId}")
	public void deleteGroup(@PathVariable int groupId) {
		groupService.deleteGroup(groupId);
	}

	@ApiOperation (value = "그룹 정보 변경")
	@PostMapping
	public void updateGroup(@Valid @RequestBody GroupDto dto) {
		groupService.updateGroup(dto);
	}

	@ApiOperation (value = "한 개 그룹정보 가져오기")
	@GetMapping("{groupId}")
	public GroupDto getGroup(@PathVariable int groupId) {
		return groupService.getGroup(groupId);
	}

	@ApiOperation (value = "그룹의 자식 목록 가져오기")
	@GetMapping("/childGroup/{groupId}")
	public Page<GroupDto> getGroupChild(@PathVariable int groupId, Pageable pageable) {
		return groupService.getGroupChild(groupId,pageable);
	}

	@ApiOperation (value = "전체 그룹 목록을 트리 형태로 가져오기")
	@GetMapping
	public GroupDto getAllGroupTrees() {
		return groupService.getAllGroupTree();
	}

	@ApiOperation (value = "그룹 목록을 리스트 형태로 가져오기")
	@GetMapping("/list")
	public Page<GroupDto> getAllGroupList(Pageable pageable){
		return groupService.getAllGroupList(pageable);
	}

	@ApiOperation (value = "현재 그룹에 속하는 모든 유저 및 자식 그룹 가져오기")
	@GetMapping("/child/{groupId}")
	public GroupNUserDto getAllChildList(@PathVariable int groupId,Pageable pageable) {
		GroupNUserDto groupNUserDto = new GroupNUserDto();
		groupNUserDto.setGroupList(groupService.getGroupChild(groupId, pageable));
		groupNUserDto.setUserList(userService.getGroupedUser(groupId, pageable));
		return groupNUserDto;
	}
}
