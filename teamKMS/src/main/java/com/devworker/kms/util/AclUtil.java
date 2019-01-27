package com.devworker.kms.util;

import com.devworker.kms.dic.PermissionType;
import com.devworker.kms.dto.GroupDto;
import com.devworker.kms.dto.UserDto;
import com.devworker.kms.dto.acl.AceDto;
import com.devworker.kms.dto.acl.AclDto;
import com.devworker.kms.exception.NotAllowException;
import com.devworker.kms.service.GroupService;
import com.devworker.kms.service.UserService;
import com.devworker.kms.service.acl.AceService;
import com.devworker.kms.service.acl.AclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AclUtil {
    private AclUtil(){}
    static AclService aclService;
    static AceService aceService;
    static UserService userService;
    static GroupService groupService;

    @Autowired
    public void setAclService(AclService acl){
        aclService = acl;
    }

    @Autowired
    public void setUserService(UserService user){
        userService = user;
    }

    @Autowired
    public void setGroupService(GroupService group){
        groupService = group;
    }

    @Autowired
    public void setAceService(AceService ace){
        aceService = ace;
    }

    public static boolean checkPermission(String ownerId, PermissionType type){
        String current = CommonUtil.getCurrentUser();
        if(current.equals(ownerId)) return true;
        if(checkInner(current,type)) return true;
        UserDto user = userService.getUser(current);
        GroupDto group = groupService.getGroup(user.getGroupId());
        while(group != null) {
            if(checkInner(String.valueOf(group.getId()),type)) return true;
            group = groupService.getGroup(group.getParentid());
        }
        throw new NotAllowException("You Have Not Permission.");
    }

    public static boolean checkPermission(PermissionType type){ return checkPermission("",type);}

        private static boolean checkInner(String aceId, PermissionType type){
        List<AceDto> aceList;
        if((aceList = aceService.getAceByAceId(aceId)) != null){
            for(AceDto sub : aceList){
                AclDto acl = aclService.getAcl(sub.getAclId());
                if(acl.getHasPermission().contains(type)) return true;
            }
        }
        return false;
    }
}
