package com.devworker.kms.entity.acl;

import com.devworker.kms.dto.acl.AceDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "KMS_ACE")
public class AceDao {
    @Id
    @Column(name = "AclId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String aclId;

    @Column(name = "AceId")
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
    public AceDto getDto(){
        AceDto dto = new AceDto();
        dto.setAceId(aceId);
        dto.setAclId(aclId);
        return dto;
    }
}
