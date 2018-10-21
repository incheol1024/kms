package com.devworker.kms.service;

import java.util.List;

import com.devworker.kms.dao.GroupDao;
import com.devworker.kms.dto.GroupDto;

public interface GroupService {
	public long countGroup();
	public int addGroup(GroupDao dao);
	public void deleteGroup(GroupDao dao);
	public void forceDeleteGroup(GroupDao dao);
	public void updateGroup(GroupDao dao);
	public List<GroupDao> getGroupChild(GroupDao dao);
	public GroupDao getGroup(int id);
	public GroupDto getAllGroupList();
	
}
