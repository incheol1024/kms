package com.devworker.kms.dic;

import com.devworker.kms.exception.NotExistException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class PermissionType {
    public static final List<PermissionType> DEFAULTLIST = new ArrayList<>();

    public static final PermissionType CREATEUSER = new PermissionType("CREATE-USER",1,true);
    public static final PermissionType CREATEGROUP = new PermissionType("CREATE-GROUP",2,true);
    public static final PermissionType CREATEPERMISSION = new PermissionType("CREATE-PERMISSION",3,true);
    public static final PermissionType CREATEQNA = new PermissionType("CREATE-QNA",4,true);
    public static final PermissionType CREATESOL = new PermissionType("CREATE-SOLUTION",5,true);
    public static final PermissionType CREATESITE = new PermissionType("CREATE-SITE",6,true);

    public static final PermissionType DELETEUSER = new PermissionType("DELETE-USER",7,true);
    public static final PermissionType DELETEGROUP = new PermissionType("DELETE-GROUP",8,true);
    public static final PermissionType DELETEPERMISSION = new PermissionType("DELETE-PERMISSION",9,true);
    public static final PermissionType DELETEQNA = new PermissionType("DELETE-QNA",10,true);
    public static final PermissionType DELETESOL = new PermissionType("DELETE-SOLUTION",11,true);
    public static final PermissionType DELETESITE = new PermissionType("DELETE-SITE",12,true);

    public static final PermissionType MODIFYUSER = new PermissionType("MODIFY-USER",13,true);
    public static final PermissionType MODIFYGROUP = new PermissionType("MODIFY-GROUP",14,true);
    public static final PermissionType MODIFYPERMISSION = new PermissionType("MODIFY-PERMISSION",15,true);
    public static final PermissionType MODIFYQNA = new PermissionType("MODIFY-QNA",16,true);
    public static final PermissionType MODIFYSOL = new PermissionType("MODIFY-SOLUTION",17,true);
    public static final PermissionType MODIFYSITE = new PermissionType("MODIFY-SITE",18,true);

    public static PermissionType valueOf(int value) {
        switch (value) {
            case 1:
                return CREATEUSER;
            case 2:
                return DELETEUSER;
            case 3:
                return MODIFYUSER;

            case 4:
                return CREATEGROUP;
            case 5:
                return DELETEGROUP;
            case 6:
                return MODIFYGROUP;

            case 7:
                return CREATEPERMISSION;
            case 8:
                return DELETEPERMISSION;
            case 9:
                return MODIFYPERMISSION;

            case 10:
                return CREATEQNA;
            case 11:
                return DELETEQNA;
            case 12:
                return MODIFYQNA;

            case 13:
                return CREATESOL;
            case 14:
                return DELETESOL;
            case 15:
                return MODIFYSOL;

            case 16:
                return CREATESITE;
            case 17:
                return DELETESITE;
            case 18:
                return MODIFYSITE;
        }
        throw new NotExistException("PermissionType Not Founded" + value);
    }

    static {
        DEFAULTLIST.add(new PermissionType("CREATE-USER",1,false));
        DEFAULTLIST.add(new PermissionType("CREATE-GROUP",2,false));
        DEFAULTLIST.add(new PermissionType("CREATE-PERMISSION",3,false));
        DEFAULTLIST.add(new PermissionType("CREATE-QNA",4,false));
        DEFAULTLIST.add(new PermissionType("CREATE-SOLUTION",5,false));
        DEFAULTLIST.add(new PermissionType("CREATE-SITE",6,false));

        DEFAULTLIST.add(new PermissionType("DELETE-USER",7,false));
        DEFAULTLIST.add(new PermissionType("DELETE-GROUP",8,false));
        DEFAULTLIST.add(new PermissionType("DELETE-PERMISSION",9,false));
        DEFAULTLIST.add(new PermissionType("DELETE-QNA",10,false));
        DEFAULTLIST.add(new PermissionType("DELETE-SOLUTION",11,false));
        DEFAULTLIST.add(new PermissionType("DELETE-SITE",12,false));

        DEFAULTLIST.add(new PermissionType("MODIFY-USER",13,false));
        DEFAULTLIST.add(new PermissionType("MODIFY-GROUP",14,false));
        DEFAULTLIST.add(new PermissionType("MODIFY-PERMISSION",15,false));
        DEFAULTLIST.add(new PermissionType("MODIFY-QNA",16,false));
        DEFAULTLIST.add(new PermissionType("MODIFY-SOLUTION",17,false));
        DEFAULTLIST.add(new PermissionType("MODIFY-SITE",18,false));
    }

    @JsonProperty("name")
    final private String name;
    final private int value;
    private boolean has;

    private PermissionType(String name, int value, boolean has) {
        this.name = name;
        this.value = value;
        this.has = has;
    }


    public boolean isHas() {
        return has;
    }

    public void setHas(boolean has) {
        this.has = has;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PermissionType)
            return this.value == ((PermissionType) obj).value;
        return false;
    }

    @Override
    public int hashCode() {
        return value;
    }

    public PermissionType copy() {
        return new PermissionType(name,value,has);
    }
}
