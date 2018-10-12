package com.devworker.kms.service;

import com.devworker.kms.dao.GroupDao;

public interface GroupService {
	public long countGroup();
	public void addGroup(GroupDao dao);
	public void deleteGroup(GroupDao dao);
	public void updateGroup(GroupDao dao);
	
}
