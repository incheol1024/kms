package com.devworker.kms.service.acl;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devworker.kms.dao.GroupDao;
import com.devworker.kms.dic.PermissionType;
import com.devworker.kms.dto.UserDto;
import com.devworker.kms.service.GroupService;
import com.devworker.kms.service.UserService;
import com.devworker.kms.util.CommonUtil;

@Aspect
@Component
public class AclAspect {
	@Autowired
	AclService aclService;
	@Autowired
	AceService aceService;
	@Autowired 
	UserService userService;
	@Autowired
	GroupService groupService;
	
	@Before("@annotation(AclPermission)")
	public void checkPermission(JoinPoint joinPoint) {
		String currentUser = CommonUtil.getCurrentUser();
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
	    Method method = signature.getMethod();
	    AclPermission myAnnotation = method.getAnnotation(AclPermission.class);
	    PermissionType targetType = myAnnotation.type();
	    UserDto user = userService.getUser(currentUser);
	    GroupDao group = groupService.getGroup(user.getGroupId());
	    
	    
	    
		//getCurrentGroup Id
		//getAcl 
		//getFunctionName
		//check while author
		//checkPermission
	}
}
