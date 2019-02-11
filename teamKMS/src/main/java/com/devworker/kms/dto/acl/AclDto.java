package com.devworker.kms.dto.acl;

import com.devworker.kms.entity.acl.AclDao;
import com.devworker.kms.dic.PermissionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

public class AclDto {
    @NotBlank
    @ApiModelProperty(notes = "권한의 ID. 이 값으로 데이터를 찾는다.",required = true)
    private String aclId;
    @NotBlank
    @ApiModelProperty(notes = "권한의 표기상의 이름",required = true)
    private String aclName;
    @ApiModelProperty(notes = "이 권한이 같은 허용 기능들의 종류",required = true)
    @NotNull
    private List<PermissionType> hasPermission;

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

    public List<PermissionType> getHasPermission() {
        return hasPermission;
    }

    public void setHasPermission(List<PermissionType> hasPermission) {
        this.hasPermission = hasPermission;
    }

    @JsonIgnore
    public AclDao getDao(){
        AclDao dao = new AclDao();
        dao.setAclId(aclId);
        dao.setAclName(aclName);
        dao.setHasPermission(hasPermission.stream().map(PermissionType::getName).collect(Collectors.joining(",")));
        return dao;
    }
}
