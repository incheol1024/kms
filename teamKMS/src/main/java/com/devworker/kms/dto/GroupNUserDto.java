package com.devworker.kms.dto;

import org.springframework.data.domain.Page;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class GroupNUserDto {
	@NotNull
	private Page<GroupDto> groupList;
	@NotNull
	private Page<UserDto> userList;

	public Page<GroupDto> getGroupList() {
		return groupList;
	}
	public void setGroupList(Page<GroupDto> groupList) {
		this.groupList = groupList;
	}
	public Page<UserDto> getUserList() {
		return userList;
	}
	public void setUserList(Page<UserDto> userList) {
		this.userList = userList;
	}
}
