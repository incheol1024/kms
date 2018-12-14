package com.devworker.kms.dto.acl;

import java.util.List;
import java.util.StringJoiner;

import com.devworker.kms.dao.acl.AclDao;
import com.devworker.kms.dic.PermissionType;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AclDto {

	private String aclId;	
	private String aclName;
	private List<PermissionType> permission;
	
	public String getAclId() {
		return aclId;
	}
	public void setAclId(String aclId) {
		this.aclId = aclId;
	}
	public String getAclName() {
		return aclName;
	}
	public void setAclName(String aclName) {
		this.aclName = aclName;
	}
	public List<PermissionType> getPermission() {
		return permission;
	}
	public void setPermission(List<PermissionType> permission) {
		this.permission = permission;
	}
	
	@JsonIgnore
	public AclDao getDao() {
		AclDao dao = new AclDao();
		dao.setAclId(aclId);
		dao.setAclName(aclName);
		StringJoiner join = new StringJoiner("|");
		for(PermissionType type : permission)
			join.add(type.toString());
		dao.setPermission(join.toString());
		return dao;
	}
}
