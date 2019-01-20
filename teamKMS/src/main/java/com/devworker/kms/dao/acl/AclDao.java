package com.devworker.kms.dao.acl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "KMS_ACL")
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
}
