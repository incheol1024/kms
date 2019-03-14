package com.devworker.kms.entity.acl;

import com.devworker.kms.dto.acl.AceDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "KMS_ACE")
@IdClass(AceDao.AceDaoId.class)
public class AceDao implements Serializable {
    @Id
    @Column(name = "AclId")
    private String aclId;

    @Id
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

    public static class AceDaoId implements Serializable {
        private String aclId;
        private String aceId;

        public AceDaoId(){}

        public AceDaoId(String aclId, String aceId) {
            this.aclId = aclId;
            this.aceId = aceId;
        }
    }
}
