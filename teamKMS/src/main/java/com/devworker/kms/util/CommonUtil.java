package com.devworker.kms.util;

import com.devworker.kms.aspect.ExecutionTimeLogging;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class CommonUtil {
	private CommonUtil() {}
	public static final String ANONYMOUS = "Anonymous";

	private static ObjectMapper objectMapper = new ObjectMapper();
	public static String getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
		    return authentication.getName();
		}
		return ANONYMOUS;
	}

	public static Pageable getPage(int size){
		return PageRequest.of(0,size);
	}

	@ExecutionTimeLogging
	public static <T> String toJson(T t)  {
		String jsonString = "";
		try {
			jsonString = objectMapper.writeValueAsString(t);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
