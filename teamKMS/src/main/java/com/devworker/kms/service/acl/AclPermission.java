package com.devworker.kms.service.acl;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.devworker.kms.dic.PermissionType;

@Retention(RUNTIME)
@Target(METHOD)
public @interface AclPermission {
	public PermissionType type();
}
