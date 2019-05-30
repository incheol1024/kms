package com.devworker.kms.util;

import com.devworker.kms.dic.PermissionType;
import com.devworker.kms.dto.GroupDto;
import com.devworker.kms.dto.UserDto;
import com.devworker.kms.dto.acl.AceDto;
import com.devworker.kms.dto.acl.AclDto;
import com.devworker.kms.service.GroupService;
import com.devworker.kms.service.UserService;
import com.devworker.kms.service.acl.AceService;
import com.devworker.kms.service.acl.AclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AclUtil {
    private AclUtil() {
    }

    private static AclService aclService;
    private static AceService aceService;
    private static UserService userService;
    private static GroupService groupService;

    @Autowired
    public void setAclService(AclService acl) {
        aclService = acl;
    }

    @Autowired
    public void setUserService(UserService user) {
        userService = user;
    }

    @Autowired
    public void setGroupService(GroupService group) {
        groupService = group;
    }

    @Autowired
    public void setAceService(AceService ace) {
        aceService = ace;
    }

    public static boolean checkPermission(String ownerId, PermissionType type) {
        String current = CommonUtil.getCurrentUser();
        if (current.equals(ownerId)) return true;
        if (checkInner(current, type)) return true;

        UserDto user = userService.getUser(current);
        GroupDto group = groupService.getGroup(user.getGroupId());
        while (group != null && group.getParentId() != -1) {
            if (checkInner(String.valueOf(group.getId()), type)) return true;
            group = groupService.getGroup(group.getParentId());
        }
        return false;
    }

    public static boolean checkPermission(PermissionType type) {
        return checkPermission("", type);
    }

    private static boolean checkInner(String aceId, PermissionType type) {
        List<AceDto> aceList = Optional.ofNullable(aceService.getAceByAceId(aceId)).orElse(new ArrayList<>());
        for (AceDto sub : aceList) {
            AclDto acl = aclService.getAcl(sub.getAclId());
            for(PermissionType check : acl.getHasPermission()){
                if(check.getValue() == type.getValue() && check.isHas())
                    return true;
            }
        }
        return false;
    }
}
