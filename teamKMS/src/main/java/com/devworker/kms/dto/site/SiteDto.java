package com.devworker.kms.dto.site;

import com.devworker.kms.entity.site.SiteDao;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class SiteDto {
    private int menuId;
    private int siteId;
    private String name;

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public SiteDao getDao(){
        SiteDao dao = new SiteDao();
        dao.setSiteId(siteId);
        dao.setMenuId(menuId);
        dao.setName(name);
        return dao;
    }
}
