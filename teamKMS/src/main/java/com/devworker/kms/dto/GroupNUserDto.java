package com.devworker.kms.dto;

import java.util.ArrayList;
import java.util.List;

public class GroupNUserDto {
	private List<GroupDto> groupList = new ArrayList<>();
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
