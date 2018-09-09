package com.devworker.kms;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class SecurityProvider implements AuthenticationProvider {
	@Autowired(required = true)
    private HttpServletRequest request;
	
	@Override
	public Authentication authenticate(Authentication authentication)  {
		String id = request.getParameter("username");
		String password = request.getParameter("password");
		try {
			List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
			grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
			return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(),grantedAuths);
		} 
		catch (Exception e) {
			throw new AuthenticationServiceException(e.getMessage());
		} 
	}

	@Override
	public boolean supports(Class<?> authentication) {
		 return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
