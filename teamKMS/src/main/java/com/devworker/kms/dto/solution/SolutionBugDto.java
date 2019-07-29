package com.devworker.kms.dto.solution;

import com.devworker.kms.dto.common.BoardDetailDto;
import com.devworker.kms.entity.solution.SolutionBugDao;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class SolutionBugDto {
    private
    long boardId;

    private
    long menuId;

    private BoardDetailDto boardDetailDto;
    private String manager;

    private String completed;

    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public BoardDetailDto getBoardDetailDto() {
        return boardDetailDto;
    }
    
    @JsonIgnore
    public SolutionBugDao toDao(){
        SolutionBugDao dao = new SolutionBugDao();
        dao.setBoardId(boardId);
        dao.setCompleted(completed);
        dao.setManager(manager);
        dao.setMenuId(menuId);
        return dao;
    }
}
