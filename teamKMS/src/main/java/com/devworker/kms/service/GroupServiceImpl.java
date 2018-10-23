package com.devworker.kms.service;

import java.util.ArrayList;
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
	public int addGroup(GroupDto dto) {
		GroupDao save = repo.save(dto.getDao());
		return save.getId();
	}
	
	@Override
	public void deleteGroup(int id) {
		repo.deleteById(id);
	}
	
	@Transactional
	@Override
	public void forceDeleteGroup(int id) {
		recurDelete(id);
		deleteGroup(id);
	}
	
	private void recurDelete(int id) {
		List<GroupDao> groupChild = repo.getGroupChild(id, new Sort(Direction.ASC,"name"));
		for(GroupDao sub : groupChild) {
			recurDelete(sub.getId());
			deleteGroup(sub.getId());
		}
	}
	
	@Override
	public void updateGroup(GroupDto dto) {
		if(repo.existsById(dto.getId()))
			repo.save(dto.getDao());
		else
			throw new NotExistException("group not existed");
	}
	
	

	@Override
	public List<GroupDto> getGroupChild(int id) {
		List<GroupDao> groupChild = repo.getGroupChild(id,new Sort(Direction.ASC,"name"));
		List<GroupDto> list = new ArrayList<>();
		for(GroupDao dao : groupChild) {
			list.add(dao.getDto());
		}
		return list;
	}

	@Override
	public GroupDao getGroup(int id) {
		Optional<GroupDao> group = repo.findById(id);
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
