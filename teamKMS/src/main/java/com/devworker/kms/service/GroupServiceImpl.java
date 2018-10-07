package com.devworker.kms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devworker.kms.repo.GroupRepo;

@Service("GroupService")
public class GroupServiceImpl implements GroupService{
	@Autowired
	GroupRepo repo;
	
	public long countGroup() {
		return repo.count();
	}
}
