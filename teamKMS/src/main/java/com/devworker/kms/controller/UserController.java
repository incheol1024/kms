package com.devworker.kms.controller;

import com.devworker.kms.dto.UserDto;
import com.devworker.kms.dto.base.BasePageResDto;
import com.devworker.kms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService service;	
	
	@PutMapping
	public void addUser(@RequestBody UserDto dto) {
		service.addUser(dto);
	}
	
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable String userId) {
		service.deleteUser(userId);
	}
	
	@PostMapping
	public void updateUser(@RequestBody UserDto dto) {
		service.updateUser(dto);
	}
	
	@GetMapping("/{userId}")
	public UserDto getUser(@PathVariable String userId) {
		return service.getUser(userId);
	}
	
	@GetMapping("/group/{groupId}")
	public List<UserDto> getGroupedUser(@PathVariable int groupId) {
		return service.getGroupedUser(groupId);
	}

	@GetMapping
	public BasePageResDto<UserDto> getUserListPaging(Pageable pageable)
	{
		BasePageResDto<UserDto> ret = service.getUserListPage(pageable);
		return ret;
	}
	
	@GetMapping("/total")
	public long getTotalUser() {
		return service.getCount();
	}
	
}
