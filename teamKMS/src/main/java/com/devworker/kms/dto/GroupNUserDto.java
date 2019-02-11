package com.devworker.kms.dto;

import io.swagger.annotations.ApiModel;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "특정 그룹의 자식 그룹,유저를 가져올때만 쓰임")
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
