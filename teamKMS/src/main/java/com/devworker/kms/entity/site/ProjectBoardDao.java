package com.devworker.kms.entity.site;

import com.devworker.kms.dto.site.ProjectBoardDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "KMS_PROJECT_BOARD")
public class ProjectBoardDao {
    @Column(name = "PROJECT_ID")
    private int projectId;

    @Id
    @Column(name = "BOARD_ID")
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
    public ProjectBoardDto getDto(){
        ProjectBoardDto dto = new ProjectBoardDto();
        dto.setBoardId(boardId);
        dto.setProjectId(projectId);
        return dto;
    }
}
