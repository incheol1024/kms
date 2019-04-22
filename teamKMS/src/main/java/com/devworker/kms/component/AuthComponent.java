package com.devworker.kms.component;

import com.devworker.kms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.devworker.kms.entity.UserDao;
import com.devworker.kms.dto.UserDto;

@Component
public class AuthComponent {
	@Autowired
	UserService service;
	@Autowired
	PasswordEncoder encoder;

	public UserDto auth(UserDao dao) {
		UserDto dbUser = service.getUser(dao.getId());
		if(!encoder.matches(dao.getPassword(), dbUser.getPassword()))
			throw new AuthenticationCredentialsNotFoundException("not correct Password");
		return dbUser;
	}
}
