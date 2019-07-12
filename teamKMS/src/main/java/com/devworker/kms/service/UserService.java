
package com.devworker.kms.service;

import com.devworker.kms.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
	UserDto addUser(UserDto dto);
	void deleteUser(String id);
	void updateUser(UserDto dto);
	UserDto getUser(String id);
	Page<UserDto> getGroupedUser(int id, Pageable pageable);
	Page<UserDto> getUserList(Pageable pageable);
	Page<UserDto> getUserListPage(Pageable pageable);
	long getCount();
    long addAvatar(List<MultipartFile> multiPartFiles);
}
