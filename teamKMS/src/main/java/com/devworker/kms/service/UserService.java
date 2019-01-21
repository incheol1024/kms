
package com.devworker.kms.service;

import com.devworker.kms.dto.UserDto;
import com.devworker.kms.dto.base.BasePageResDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
	UserDto addUser(UserDto dto);
	void deleteUser(String id);
	void updateUser(UserDto dto);
	UserDto getUser(String id);
	List<UserDto> getGroupedUser(int id);
	List<UserDto> getUserList();
	long getCount();

    BasePageResDto<UserDto> getUserListPage(Pageable pageable);
}
