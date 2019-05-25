package com.devworker.kms.util;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class CommonUtil {
	private CommonUtil() {}
	private static final String ANONYMOUS = "Anonymous";

	private static ObjectMapper objectMapper = new ObjectMapper();
	public static String getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    return authentication.getName();
		}
		return ANONYMOUS;
	}

	public static Pageable getPage(int size){
		return PageRequest.of(0,size);
	}

	public static <T> String toJson(T t)  {
		try {
			return objectMapper.writeValueAsString(t);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
