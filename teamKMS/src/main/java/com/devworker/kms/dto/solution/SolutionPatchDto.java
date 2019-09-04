package com.devworker.kms.dto.solution;

import com.devworker.kms.dto.common.BoardDetailDto;
import com.devworker.kms.entity.solution.SolutionPatchDao;

import javax.persistence.Column;
import javax.persistence.Id;

public class SolutionPatchDto {
    private long boardId;
    private long menuId;
    private String importance;
    private String version;
    private BoardDetailDto boardDetailDto;
    
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

    public String getImportance() {
        return importance;
    }

    public void setImportance(String impotance) {
        this.importance = impotance;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

	public BoardDetailDto getBoardDetailDto() {
		return boardDetailDto;
	}

    public void setDetailBoard(BoardDetailDto boardDetailDto) {
    	this.boardDetailDto = boardDetailDto;
	}
	
    public SolutionPatchDao toDao(){
        SolutionPatchDao dao = new SolutionPatchDao();
        dao.setBoardId(boardId);
        dao.setImportance(importance);
        dao.setMenuId(menuId);
        dao.setVersion(version);
        return dao;
    }
}
