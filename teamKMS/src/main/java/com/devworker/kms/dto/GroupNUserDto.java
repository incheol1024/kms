package com.devworker.kms.dto;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class GroupNUserDto {
	@NotNull
	private List<GroupDto> groupList = new ArrayList<>();
	@NotNull
	private List<UserDto> userList = new ArrayList<>();
	
	public List<GroupDto> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<GroupDto> groupList) {
		this.groupList = groupList;
	}
	public List<UserDto> getUserList() {
		return userList;
	}
	public void setUserList(List<UserDto> userList) {
		this.userList = userList;
	}
}
