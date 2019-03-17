package com.devworker.kms.entity.site;

import com.devworker.kms.dto.site.SiteDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "KMS_SITE")
public class SiteDao {
    @Column(name = "MENU_ID")
    private int menuId;

    @Id
    @Column(name = "SITE_ID")
    private int siteId;

    @Column(name = "SITE_NAME")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    @JsonIgnore
    public SiteDto getDto(){
        SiteDto dto = new SiteDto();
        dto.setMenuId(menuId);
        dto.setName(name);
        dto.setSiteId(siteId);
        return dto;
    }
}
