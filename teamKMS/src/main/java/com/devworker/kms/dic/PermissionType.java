package com.devworker.kms.dic;

import com.devworker.kms.exception.NotExistException;

public class PermissionType {
    public static final PermissionType CREATEUSER = new PermissionType("1");
    public static final PermissionType DELETEUSER = new PermissionType("2");
    public static final PermissionType MODIFYUSER = new PermissionType("3");

    public static final PermissionType CREATEGROUP = new PermissionType("4");
    public static final PermissionType DELETEGROUP = new PermissionType("5");
    public static final PermissionType MODIFYGROUP = new PermissionType("6");

    public static final PermissionType CREATEPERMISSION = new PermissionType("7");
    public static final PermissionType DELETEPERMISSION = new PermissionType("8");
    public static final PermissionType MODIFYPERMISSION = new PermissionType("9");

    public static final PermissionType CREATEQNA = new PermissionType("10");
    public static final PermissionType DELETEQNA = new PermissionType("11");
    public static final PermissionType MODIFYQNA = new PermissionType("12");

    public static final PermissionType CREATESOL = new PermissionType("13");
    public static final PermissionType DELETESOL = new PermissionType("14");
    public static final PermissionType MODIFYSOL = new PermissionType("15");

    public static final PermissionType CREATESITE = new PermissionType("16");
    public static final PermissionType DELETESITE = new PermissionType("17");
    public static final PermissionType MODIFYSITE = new PermissionType("18");

    public static PermissionType valueOf(String value) {
        switch (value){
            case "1" : return CREATEUSER;
            case "2" : return DELETEUSER;
            case "3" : return MODIFYUSER;

            case "4" : return CREATEGROUP;
            case "5" : return DELETEGROUP;
            case "6" : return MODIFYGROUP;

            case "7" : return CREATEPERMISSION;
            case "8" : return DELETEPERMISSION;
            case "9" : return MODIFYPERMISSION;

            case "10" : return CREATEQNA;
            case "11" : return DELETEQNA;
            case "12" : return MODIFYQNA;

            case "13" : return CREATESOL;
            case "14" : return DELETESOL;
            case "15" : return MODIFYSOL;

            case "16" : return CREATESITE;
            case "17" : return DELETESITE;
            case "18" : return MODIFYSITE;
        }
        throw new NotExistException("PermissionType Not Founded" + value);
    }

    final private String name;

    private PermissionType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PermissionType){
            if(getName().equals(((PermissionType)obj).getName())) return true;
                return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(this.name);
    }
}
