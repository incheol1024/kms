package com.devworker.kms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devworker.kms.dao.GroupDao;
import com.devworker.kms.dto.GroupDto;
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
	public int addGroup(GroupDao dao) {
		GroupDao save = repo.save(dao);
		return save.getId();
	}
	
	@Override
	public void deleteGroup(GroupDao dao) {
		repo.delete(dao);
	}
	
	@Transactional
	@Override
	public void forceDeleteGroup(GroupDao dao) {
		recurDelete(dao);
		deleteGroup(dao);
	}
	
	private void recurDelete(GroupDao dao) {
		List<GroupDao> groupChild = repo.getGroupChild(dao.getId(), new Sort(Direction.ASC,"name"));
		for(GroupDao sub : groupChild) {
			recurDelete(sub);
			deleteGroup(sub);
		}
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

	@Override
	public GroupDto getAllGroupList() {
		GroupDto dto = new GroupDto();
		dto.setId(0);
		dto.setName("Root");
		recurTreeMaker(dto);
		return dto;
	}
	
	private void recurTreeMaker(GroupDto dto) {
		List<GroupDao> groupChild = repo.getGroupChild(dto.getId(), new Sort(Direction.ASC,"name"));
		for(GroupDao dao : groupChild) {
			GroupDto sub = new GroupDto();
			sub.setId(dao.getId());
			sub.setName(dao.getName());
			dto.getChildren().add(sub);
			recurTreeMaker(sub);
		}
	}

	
}
