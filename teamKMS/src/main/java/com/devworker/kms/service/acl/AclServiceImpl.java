package com.devworker.kms.service.acl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devworker.kms.dic.PermissionType;
import com.devworker.kms.dto.acl.AclDto;
import com.devworker.kms.repo.acl.AclRepo;

@Service("AclService")
public class AclServiceImpl implements AclService{
	@Autowired
	AclRepo repo;

	@Override
	public String createAcl(AclDto acl) {
		return null;
	}

	@Override
	public AclDto getAcl(AclDto acl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(AclDto acl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<AclDto> getAclList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateAcl(AclDto acl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkPermission(String aclId, PermissionType type) {
		// TODO Auto-generated method stub
		return false;
	}

}
