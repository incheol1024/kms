package com.devworker.kms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.devworker.kms.dto.acl.AceDto;
import com.devworker.kms.dto.acl.AclDto;
import com.devworker.kms.service.acl.AceService;
import com.devworker.kms.service.acl.AclService;

@RestController
public class AclController {
	@Autowired
	AceService aceService;
	@Autowired
	AclService aclService;
	
	public String createAcl(AclDto acl) {
		return aclService.createAcl(acl);
	}
	
	public AclDto getAcl(AclDto acl) {
		return aclService.getAcl(acl);
	}
	
	public void deleteAcl(AclDto acl) {
		aclService.delete(acl);
	}
	
	public List<AclDto> getAclList(){
		return aclService.getAclList();
	}
	
	public void updateAcl(AclDto acl) {
		aclService.updateAcl(acl);
	}
	
	public void addAce(AceDto ace) {
		aceService.addAce(ace);
	}
	
	public void removeAce(AceDto ace) {
		aceService.removeAce(ace);
	}
	
	public List<AceDto> getAceList(AceDto dto){
		return aceService.getAceList(dto);
	}
}
