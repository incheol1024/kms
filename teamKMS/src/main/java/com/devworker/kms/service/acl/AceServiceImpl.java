package com.devworker.kms.service.acl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devworker.kms.dic.PermissionType;
import com.devworker.kms.dto.acl.AceDto;
import com.devworker.kms.repo.acl.AceRepo;

@Service("AceService")
public class AceServiceImpl implements AceService{
	@Autowired
	AclService service;
	@Autowired
	AceRepo repo;

	@Override
	public void addAce(AceDto ace) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAce(AceDto ace) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<AceDto> getAceList(AceDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AceDto getAce(AceDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkPermission(String aceId, PermissionType type) {
		// TODO Auto-generated method stub
		return false;
	}

}
