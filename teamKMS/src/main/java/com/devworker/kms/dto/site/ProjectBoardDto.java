package com.devworker.kms.dto.site;

import com.devworker.kms.entity.site.ProjectBoardDao;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProjectBoardDto {
    private int projectId;
    private long boardId;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    @JsonIgnore
    public ProjectBoardDao getDao(){
        ProjectBoardDao dao = new ProjectBoardDao();
        dao.setProjectId(projectId);
        dao.setBoardId(boardId);
        return dao;
    }
}
