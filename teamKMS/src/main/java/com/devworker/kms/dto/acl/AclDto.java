package com.devworker.kms.dto.acl;

import com.devworker.kms.dao.acl.AclDao;
import com.devworker.kms.dic.PermissionType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class AclDto {
    private String aclId;
    private String aclName;
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
        dao.setHasPermission(hasPermission.stream().map(permissionType -> permissionType.name()).collect(Collectors.joining(",")));
        return dao;
    }
}
