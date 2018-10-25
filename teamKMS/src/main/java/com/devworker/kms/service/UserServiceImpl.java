package com.devworker.kms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devworker.kms.dao.UserDao;
import com.devworker.kms.dto.UserDto;
import com.devworker.kms.exception.NotExistException;
import com.devworker.kms.repo.UserRepo;

@Service("UserService")
public class UserServiceImpl implements UserService{
	@Autowired
	UserRepo repo;
	@Autowired
	GroupService service;
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	@Cacheable(key = "'userCount'", value="userCache")
	public long countUser() {
		return repo.count();
	}
	
	@CacheEvict(key = "'userCount'", value="userCache")
	public UserDto addUser(UserDto dto) {
		if(repo.existsById(dto.getId()))
			throw new DuplicateKeyException("User Id already Has in Server");
		dto.setPassword(encoder.encode(dto.getPassword()));
		String groupName = service.getGroup(dto.getGroupId()).getName();
		dto.setGroupName(groupName);
		repo.save(dto.getDao());
		return dto;
	}
	
	@CacheEvict(key = "'userCount'", value="userCache")
	public void deleteUser(String id) {
		repo.deleteById(id);
	}
	
	public void updateUser(UserDto dto) {
		UserDto dbUser = getUser(dto.getId());
		if(dto.getPassword() != dbUser.getPassword() &&
			!encoder.matches(dto.getPassword(), dbUser.getPassword()))
				dto.setPassword(encoder.encode(dto.getPassword()));
		repo.save(dto.getDao());
	}

	@Override	
	public UserDto getUser(String id) {
		Optional<UserDao> user = repo.findById(id);
		if(user.isPresent()) {
			UserDto dto = user.get().getDto();
			String groupName = service.getGroup(dto.getGroupId()).getName();
			dto.setGroupName(groupName);
			return dto;
		}			
		else
			throw new NotExistException("User not exist");
	}
	
	@Override
	public List<UserDto> getGroupedUser(int id) {
		List<UserDao> daoList = repo.getGroupedUser(id,new Sort(Direction.ASC, "name"));
		return  getDaoToDtoList(daoList,false);
	}

	@Override
	public List<UserDto> getUserList() {
		List<UserDao> daoList = repo.findAll();
		return getDaoToDtoList(daoList,true);
	}
	
	private List<UserDto> getDaoToDtoList(List<UserDao> daoList,boolean addGroupName){
		List<UserDto> dtoList = new ArrayList<>();
		for(UserDao dao : daoList) {
			UserDto dto = dao.getDto();
			if(addGroupName) {
				String groupName = service.getGroup(dao.getGroupId()).getName();
				dto.setGroupName(groupName);
			}			
			dtoList.add(dto);
		}
		return dtoList;
	}

	@Override
	public long getCount() {
		return repo.count();
	}
}
