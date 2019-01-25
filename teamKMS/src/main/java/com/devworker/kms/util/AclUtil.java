package com.devworker.kms.util;

import com.devworker.kms.service.acl.AceService;
import com.devworker.kms.service.acl.AclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AclUtil {
    private AclUtil(){}
    static AclService aclService;
    static AceService aceService;

    @Autowired
    public void setAclService(AclService acl){
        aclService = acl;
    }

    @Autowired
    public void setAceService(AceService ace){
        aceService = ace;
    }

    public static boolean checkPermission(String ownerId, String url){
        if(CommonUtil.getCurrentUser().equals(ownerId))
            return true;
        //url check
        //get aceList
        //find acl
        //user check
        //group check by recursive
        return false;
    }
}
