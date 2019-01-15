package com.devworker.kms.dao.menu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "KMS_MenuRight")
public class MenuRightDao {
    @Id
    @Column(name = "menu_id")
    private int id;
    @Column(name="sid")
    private String sid;
    @Column(name="HasPermission")
    private String hasPermission;


}
