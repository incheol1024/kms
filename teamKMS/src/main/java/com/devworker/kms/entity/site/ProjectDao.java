package com.devworker.kms.entity.site;

import com.devworker.kms.dto.site.ProjectDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "KMS_PROJECT")
public class ProjectDao {
    @Column(name = "SITE_ID")
    private int siteId;

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "PROJECT_ID")
    private int projectId;

    @Column(name = "PROJECT_NAME")
    private String name;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "MANAGER")
    private String manager;
    @Column(name = "step")
    private int step;
    @Column(name = "grade")
    private int grade;

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

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @JsonIgnore
    public ProjectDto getDto(){
        ProjectDto dto = new ProjectDto();
        dto.setEndDate(endDate);
        dto.setManager(manager);
        dto.setName(name);
        dto.setStartDate(startDate);
        dto.setSiteId(siteId);
        dto.setProjectId(projectId);
        dto.setGrade(grade);
        dto.setStep(step);
        return dto;
    }
}
