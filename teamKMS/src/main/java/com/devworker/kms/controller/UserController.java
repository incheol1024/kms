package com.devworker.kms.controller;

import com.devworker.kms.dto.UserDto;
import com.devworker.kms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@Valid
public class UserController {
	@Autowired
	UserService service;	
	
	@PutMapping
	public void addUser(@Valid  @RequestBody UserDto dto) {
		service.addUser(dto);
	}
	
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable String userId) {
		service.deleteUser(userId);
	}
	
	@PostMapping
	public void updateUser(@Valid  @RequestBody UserDto dto) {
		service.updateUser(dto);
	}
	
	@GetMapping("/{userId}")
	public UserDto getUser(@PathVariable String userId) {
		return service.getUser(userId);
	}
	
	@GetMapping("/group/{groupId}")
	public Page<UserDto> getGroupedUser(@PathVariable int groupId, Pageable pageable) {
		return service.getGroupedUser(groupId,pageable);
	}

	@GetMapping
	public Page<UserDto> getUserListPaging(Pageable pageable)
	{
		return  service.getUserListPage(pageable);
	}
	
	@GetMapping("/total")
	public long getTotalUser() {
		return service.getCount();
	}
	
}
