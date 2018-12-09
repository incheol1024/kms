package com.devworker.kms.dao.acl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.devworker.kms.dic.PermissionType;
import com.devworker.kms.dto.acl.AclDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "KMS_ACL")
public class AclDao {
	@Id
	@Column(name = "AclId")
	private String aclId;
	@Column(name = "AclName")
	private String aclName;
	@Column(name = "HasPermission")
	private String permission;
	
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
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	@JsonIgnore
	public AclDto getDto() {
		AclDto dto = new AclDto();		
		dto.setAclId(aclId);
		dto.setAclName(aclName);
		List<PermissionType> list = new ArrayList<>();
		for(String s : permission.split("|")) {
			list.add(PermissionType.valueOf(s));
		}
		dto.setPermission(list);
		return dto;
	}
}
