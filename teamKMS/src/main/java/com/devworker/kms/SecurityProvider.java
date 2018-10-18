package com.devworker.kms;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.devworker.kms.dao.UserDao;
import com.devworker.kms.service.AuthService;

@Component
public class SecurityProvider implements AuthenticationProvider {
	@Autowired
	AuthService service;

	@Override
	public Authentication authenticate(Authentication authentication) {
		if (authentication.getName().isEmpty() || authentication.getCredentials().toString().isEmpty())
			throw new AuthenticationServiceException("id or Password wrong");
		try {
			UserDao dao = new UserDao();
			dao.setId(authentication.getName());
			dao.setPassword(authentication.getCredentials().toString());
			dao = service.auth(dao);
			List<GrantedAuthority> grantedAuths = new ArrayList<>();
			grantedAuths.add(new SimpleGrantedAuthority(dao.getType()));
			return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(),
					grantedAuths);
		} catch (Exception e) {
			throw new AuthenticationServiceException(e.getMessage());
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
