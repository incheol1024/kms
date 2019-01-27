package com.devworker.kms.service;

import java.util.List;

import com.devworker.kms.dao.GroupDao;
import com.devworker.kms.dto.GroupDto;

public interface GroupService {
	long countGroup();
	int addGroup(GroupDto dto);
	void deleteGroup(int id);
	void forceDeleteGroup(int id);
	void updateGroup(GroupDto dto);
	List<GroupDto> getGroupChild(int id);
	GroupDto getGroup(int id);
	GroupDto getAllGroupList();
}
