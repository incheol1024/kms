package com.devworker.kms.entity.acl;

import com.devworker.kms.dic.PermissionType;
import com.devworker.kms.dto.acl.AclDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.stream.Collectors;

@Entity
@Table(name = "KMS_ACL")
public class AclDao {
    @Id
    @Column(name = "AclId")
    private String aclId;

    @Column(name = "AclName")
    private String aclName;

    @Column(name = "HasPermission")
    private String hasPermission;

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

    public String getHasPermission() {
        return hasPermission;
    }

    public void setHasPermission(String hasPermission) {
        this.hasPermission = hasPermission;
    }

    @JsonIgnore
    public AclDto getDto() {
        AclDto dto = new AclDto();
        dto.setAclId(aclId);
        dto.setAclName(aclName);
        String[] split = hasPermission.split(",");
        dto.setHasPermission(Arrays.stream(split).map(PermissionType::valueOf).collect(Collectors.toList()));
        return dto;
    }
}
