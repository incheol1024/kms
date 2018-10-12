package com.devworker.kms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devworker.kms.dao.GroupDao;
import com.devworker.kms.repo.GroupRepo;

@Service("GroupService")
public class GroupServiceImpl implements GroupService{
	@Autowired
	GroupRepo repo;
	
	@Override
	public long countGroup() {
		return repo.count();
	}
	
	@Override
	public void addGroup(GroupDao dao) {
		repo.save(dao);
	}
	
	@Override
	public void deleteGroup(GroupDao dao) {
		repo.delete(dao);
	}
	
	@Override
	public void updateGroup(GroupDao dao) {
		if(repo.existsById(dao.getId()))
			repo.save(dao);
	}
}
