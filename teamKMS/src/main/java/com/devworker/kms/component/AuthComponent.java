package com.devworker.kms.component;

import com.devworker.kms.dto.UserDto;
import com.devworker.kms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthComponent {
	@Autowired
	UserService service;
	@Autowired
	PasswordEncoder encoder;

	public UserDto auth(String userId, String pass) {
		UserDto dbUser = service.getUser(userId);
		if(!encoder.matches(pass, dbUser.getPassword()))
			throw new AuthenticationCredentialsNotFoundException("not correct Password");
		return dbUser;
	}
}
