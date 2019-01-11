
package com.devworker.kms.service;

import java.util.List;

import com.devworker.kms.dto.UserDto;

public interface UserService {
	UserDto addUser(UserDto dto);
	void deleteUser(String id);
	void updateUser(UserDto dto);
	UserDto getUser(String id);
	List<UserDto> getGroupedUser(int id);
	List<UserDto> getUserList();
	long getCount();
}
