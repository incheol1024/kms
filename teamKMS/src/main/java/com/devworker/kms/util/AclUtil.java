package com.devworker.kms.util;

import org.springframework.stereotype.Component;

@Component
public class AclUtil {
    private AclUtil(){}

    public static <T> boolean checkPermission(String userId, T t){

        return true;
    }
}
