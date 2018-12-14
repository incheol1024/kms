package com.devworker.kms.dto.acl;

import com.devworker.kms.dao.acl.AceDao;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AceDto {
	private String aclId;
	private String aceId;
	private String aceName;
	
	public String getAceName() {
		return aceName;
	}
	public void setAceName(String aceName) {
		this.aceName = aceName;
	}
	public String getAclId() {
		return aclId;
	}
	public void setAclId(String aclId) {
		this.aclId = aclId;
	}
	public String getAceId() {
		return aceId;
	}
	public void setAceId(String aceId) {
		this.aceId = aceId;
	}
	
	@JsonIgnore
	public AceDao getDao() {
		AceDao dao = new AceDao();
		dao.setAceId(aceId);
		dao.setAclId(aclId);
		return dao;
	}
}
