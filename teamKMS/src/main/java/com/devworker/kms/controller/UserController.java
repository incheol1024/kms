package com.devworker.kms.controller;

import com.devworker.kms.dto.UserDto;
import com.devworker.kms.dto.base.BasePageResDto;
import com.devworker.kms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
	@Autowired
	UserService service;	
	
	@PostMapping("/addUser")
	public void addUser(@RequestBody UserDto dto) {
		service.addUser(dto);
	}
	
	@PostMapping("/deleteUser")
	public void deleteUser(@RequestBody UserDto dto) {
		service.deleteUser(dto.getId());
	}
	
	@PostMapping("/updateUser")
	public void updateUser(@RequestBody UserDto dto) {
		service.updateUser(dto);
	}
	
	@PostMapping("/getUser")
	public UserDto getUser(@RequestBody UserDto dto) {
		return service.getUser(dto.getId());
	}
	
	@PostMapping("/getGroupedUser")
	public List<UserDto> getGroupedUser(@RequestBody UserDto dto) {
		return service.getGroupedUser(dto.getGroupId());
	}
	
	@PostMapping("/getUserList")
	public List<UserDto> getUserList() {
		return service.getUserList();
	}

	@GetMapping("/getUserListPage")
	public BasePageResDto<UserDto> getUserListPaging(Pageable pageable)
	{
		BasePageResDto<UserDto> ret = service.getUserListPage(pageable);
		return ret;
	}
	
	@GetMapping("/getTotalUser")
	public long getTotalUser() {
		return service.getCount();
	}
	
}
