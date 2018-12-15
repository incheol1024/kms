package com.devworker.kms.dao.acl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.devworker.kms.dto.acl.AceDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "KMS_ACE")
public class AceDao {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@Column(name="AclId")
	private String aclId;
	@Column(name="AceId")
	private String aceId;
	
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
	public AceDto getDto() {
		AceDto dto = new AceDto();
		dto.setAceId(aceId);
		dto.setAclId(aclId);
		return dto;
	}
}
