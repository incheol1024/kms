package com.devworker.kms.service.acl;

import java.util.List;

import com.devworker.kms.dic.PermissionType;
import com.devworker.kms.dto.acl.AclDto;

public interface AclService {

	String createAcl(AclDto acl);

	AclDto getAcl(AclDto acl);

	void delete(AclDto acl);

	List<AclDto> getAclList();

	void updateAcl(AclDto acl);
	
	boolean checkPermission(String aclId, PermissionType type);

}
