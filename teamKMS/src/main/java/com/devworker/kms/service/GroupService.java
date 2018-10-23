package com.devworker.kms.service;

import java.util.List;

import com.devworker.kms.dao.GroupDao;
import com.devworker.kms.dto.GroupDto;

public interface GroupService {
	public long countGroup();
	public int addGroup(GroupDto dto);
	public void deleteGroup(int id);
	public void forceDeleteGroup(int id);
	public void updateGroup(GroupDto dto);
	public List<GroupDto> getGroupChild(int id);
	public GroupDao getGroup(int id);
	public GroupDto getAllGroupList();
}
