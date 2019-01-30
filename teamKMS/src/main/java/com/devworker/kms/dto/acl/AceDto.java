package com.devworker.kms.dto.acl;

import com.devworker.kms.dao.acl.AceDao;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotBlank;

public class AceDto {
    @NotBlank
    private String aclId;
    @NotBlank
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
    public AceDao getDao(){
        AceDao dao = new AceDao();
        dao.setAceId(aceId);
        dao.setAclId(aclId);
        return dao;
    }
}
