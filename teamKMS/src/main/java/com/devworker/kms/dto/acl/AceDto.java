package com.devworker.kms.dto.acl;

import com.devworker.kms.entity.acl.AceDao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

public class AceDto {
    @NotBlank
    @ApiModelProperty(notes = "권한의 ID. 존재하는 ACL에 대해서만 가능하다",required = true)
    private String aclId;
    @ApiModelProperty(notes = "유저 또는 그룹의 ID",required = true)
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
