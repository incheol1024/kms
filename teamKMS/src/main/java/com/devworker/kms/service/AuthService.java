package com.devworker.kms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devworker.kms.dao.UserDao;

@Service
public class AuthService{
	@Autowired
	UserService service;
	@Autowired
	PasswordEncoder encoder;

	public UserDao auth(UserDao dao) {
		UserDao dbUser = service.getUser(dao);
		if(!encoder.matches(dao.getPassword(), dbUser.getPassword()))
			throw new AuthenticationCredentialsNotFoundException("not correct Password");
		return dbUser;
	}
}