package com.devworker.kms.service;

import com.devworker.kms.dao.GroupDao;
import com.devworker.kms.dic.PermissionType;
import com.devworker.kms.dto.GroupDto;
import com.devworker.kms.dto.UserDto;
import com.devworker.kms.exception.ChildFoundException;
import com.devworker.kms.exception.NotAllowException;
import com.devworker.kms.exception.NotExistException;
import com.devworker.kms.repo.GroupRepo;
import com.devworker.kms.util.AclUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("GroupService")
public class GroupServiceImpl implements GroupService{
	private final
	GroupRepo repo;
	private final
	UserService userService;

	@Autowired
	public GroupServiceImpl(GroupRepo repo,@Lazy UserService userService) {
		this.repo = repo;
		this.userService = userService;
	}

	
	@Override
	public int addGroup(GroupDto dto) {
		if(dto.getId() == dto.getParentId()) throw new NotAllowException("Parent Group and Current Group Same...");
		AclUtil.checkPermission(PermissionType.CREATEGROUP);
		GroupDao save = repo.save(dto.getDao());
		return save.getId();
	}
	
	@Override
	public void deleteGroup(int id) {
		if(id == 0) throw  new NotAllowException("ROOT CAN'T Be Deleted");
		AclUtil.checkPermission(PermissionType.DELETEGROUP);
		List<UserDto> groupedUser = userService.getGroupedUser(id);
		if(groupedUser.size() > 0) throw new ChildFoundException("Group Has Child. You Must Delete First");
		repo.deleteById(id);
	}
	

	
	@Override
	public void updateGroup(GroupDto dto) {
		if(dto.getId() == dto.getParentId()) throw new NotAllowException("Parent Group and Current Group Same...");
		if(dto.getId() == 0) throw  new NotAllowException("ROOT Can't be Modified");
		AclUtil.checkPermission(PermissionType.MODIFYGROUP);
		if(repo.existsById(dto.getId()))  repo.save(dto.getDao());
		else throw new NotExistException("group not existed");
	}

	@Transactional
	@Override
	public void forceDeleteGroup(int id) {
		recurDelete(id);
		deleteGroup(id);
	}

	private void recurDelete(int id) {
		List<GroupDao> groupChild = repo.getGroupChild(id, new Sort(Direction.ASC,"name"));
		groupChild.parallelStream().forEach(groupDao -> {
			recurDelete(groupDao.getId());
			deleteGroup(groupDao.getId());
		});
	}

	@Override
	public List<GroupDto> getGroupChild(int id) {
		List<GroupDao> groupChild = repo.getGroupChild(id,new Sort(Direction.ASC,"name"));
		List<GroupDto> list = new ArrayList<>();
		for (GroupDao groupDao : groupChild) {
			GroupDto dto = groupDao.getDto();
			list.add(dto);
		}
		return list;
	}

	@Override
	public GroupDto getGroup(int id) {
		if(id == 0) return GroupDto.ROOTGROUP;
		return repo.findById(id).orElseThrow(() -> new NotExistException("group not exist")).getDto();
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

	@Override
	public long countGroup() {
		return repo.count();
	}
}
