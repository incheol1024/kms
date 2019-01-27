package com.devworker.kms.service;

import com.devworker.kms.dao.UserDao;
import com.devworker.kms.dto.UserDto;
import com.devworker.kms.dto.base.BasePageResDto;
import com.devworker.kms.exception.NotExistException;
import com.devworker.kms.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("UserService")
public class
UserServiceImpl implements UserService{
	@Autowired
	UserRepo repo;
	@Autowired
	GroupService service;
	@Autowired
	PasswordEncoder encoder;

	@Override
	@Cacheable(key = "'userCount'", value="userCache")
	public long getCount() {
		return repo.count();
	}

	@Override
	public BasePageResDto<UserDto> getUserListPage(Pageable pageable) {
		Page<UserDao> page = repo.findAll(pageable);
		BasePageResDto<UserDto> ret = new BasePageResDto<>();
		ret.setList(page.stream().map(userDao -> userDao.getDto()).map(userDto -> {
			userDto.setGroupName(service.getGroup(userDto.getGroupId()).getName());
			return userDto;
		}).collect(Collectors.toList()));
		ret.setTotal(page.getTotalElements());
		return ret;
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
		return repo.findById(id).map(userDao -> userDao.getDto()).map(userDto -> {
					userDto.setGroupName(service.getGroup(userDto.getGroupId()).getName());
					return userDto;
				}).orElseThrow(() -> new NotExistException("User not exist"));
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
		return daoList.parallelStream().map(userDao -> userDao.getDto())
				.map(userDto -> { if(addGroupName)
					userDto.setGroupName(service.getGroup(userDto.getGroupId()).getName());
					return userDto;
		}).collect(Collectors.toList());
	}


}
