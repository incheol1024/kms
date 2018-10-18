package com.devworker.kms.service;

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
import com.devworker.kms.exception.NotExistException;
import com.devworker.kms.repo.UserRepo;

@Service("UserService")
public class UserServiceImpl implements UserService{
	@Autowired
	UserRepo repo;
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	@Cacheable(key = "'userCount'", value="userCache")
	public long countUser() {
		return repo.count();
	}
	
	@CacheEvict(key = "'userCount'", value="userCache")
	public void addUser(UserDao dao) {
		if(repo.existsById(dao.getId()))
			throw new DuplicateKeyException("User Id already Has in Server");
		dao.setPassword(encoder.encode(dao.getPassword()));
		repo.save(dao);
	}
	
	@CacheEvict(key = "'userCount'", value="userCache")
	public void deleteUser(UserDao dao) {
		repo.delete(dao);
	}
	
	public void updateUser(UserDao dao) {
		UserDao dbUser = getUser(dao);
		if(!encoder.matches(dao.getPassword(), dbUser.getPassword()))
			dao.setPassword(encoder.encode(dao.getPassword()));
		repo.save(dao);
	}

	@Override	
	public UserDao getUser(UserDao dao) {
		Optional<UserDao> user = repo.findById(dao.getId());
		if(user.isPresent())
			return user.get();
		else
			throw new NotExistException("User not exist");
	}
	
	@Override
	public List<UserDao> getGroupedUser(UserDao dao) {
		return repo.getGroupedUser(dao.getGroupId(),new Sort(Direction.ASC, "name"));
	}

	@Override
	public List<UserDao> getUserList() {
		return repo.findAll();
	}

	@Override
	public long getCount() {
		return repo.count();
	}
}
