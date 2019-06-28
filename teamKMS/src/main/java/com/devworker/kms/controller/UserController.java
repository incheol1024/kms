package com.devworker.kms.controller;

import com.devworker.kms.dto.UserDto;
import com.devworker.kms.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Valid
public class UserController {
	@Autowired
	UserService service;

	@ApiOperation (value = "신규 유저 생성")
	@PutMapping
	public void addUser(@Valid  @RequestBody UserDto dto) {
		service.addUser(dto);
	}

	@ApiOperation (value = "기존 유저 삭제")
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable String userId) {
		service.deleteUser(userId);
	}

	@ApiOperation (value = "유저 정보 변경")
	@PostMapping
	public void updateUser(@Valid  @RequestBody UserDto dto) {
		service.updateUser(dto);
	}

	@ApiOperation (value = "유저 한명 정보 가져오기")
	@GetMapping("/{userId}")
	public UserDto getUser(@PathVariable String userId) {
		return service.getUser(userId);
	}

	@ApiOperation (value = "특정 그룹에 속하는 유저 가져오기")
	@GetMapping("/group/{groupId}")
	public Page<UserDto> getGroupedUser(@PathVariable int groupId, Pageable pageable) {
		return service.getGroupedUser(groupId,pageable);
	}

	@ApiOperation (value = "유저 목록 가져오기")
	@GetMapping
	public Page<UserDto> getUserListPaging(Pageable pageable)
	{
		return  service.getUserListPage(pageable);
	}

	@ApiOperation (value = "전체 유저 수 가져오기")
	@GetMapping("/total")
	public long getTotalUser() {
		return service.getCount();
	}

	@ApiOperation(value	= "사용자 아바타 이미지 업로드")
	@PostMapping("/avatar")
	public String addAvatar(){
		return service.addAvatar();
	}
}
