
package com.devworker.kms.service;

import java.util.List;

import com.devworker.kms.dto.UserDto;

public interface UserService {
	public long countUser();
	public UserDto addUser(UserDto dto);
	public void deleteUser(String id);
	public void updateUser(UserDto dto);
	public UserDto getUser(String id);
	public List<UserDto> getGroupedUser(UserDto dto);
	public List<UserDto> getUserList();
	public long getCount();
}
