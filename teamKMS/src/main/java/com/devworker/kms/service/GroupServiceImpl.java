package com.devworker.kms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.devworker.kms.dao.GroupDao;
import com.devworker.kms.exception.NotExistException;
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
		else
			throw new NotExistException("group not existed");
	}

	@Override
	public List<GroupDao> getGroupChild(GroupDao dao) {
		return repo.getGroupChild(dao.getId(),new Sort(Direction.ASC,"name"));
	}

	@Override
	public GroupDao getGroup(GroupDao dao) {
		Optional<GroupDao> group = repo.findById(dao.getId());
		if(group.isPresent())
			return group.get();
		else
			throw new NotExistException("group not exist");
	}
}
