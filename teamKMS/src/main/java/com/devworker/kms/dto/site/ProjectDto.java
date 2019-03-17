package com.devworker.kms.dto.site;

import com.devworker.kms.entity.site.ProjectDao;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Date;

public class ProjectDto {
    private int siteId;
    private int projectId;
    private String name;
    private Date startDate;
    private Date endDate;
    private String manager;
    private String mangerName;

    public String getMangerName() {
        return mangerName;
    }

    public void setMangerName(String mangerName) {
        this.mangerName = mangerName;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    @JsonIgnore
    public ProjectDao getDao(){
        ProjectDao dao = new ProjectDao();
        dao.setSiteId(siteId);
        dao.setName(name);
        dao.setProjectId(projectId);
        dao.setEndDate(endDate);
        dao.setStartDate(startDate);
        dao.setManager(manager);
        return dao;
    }
}
