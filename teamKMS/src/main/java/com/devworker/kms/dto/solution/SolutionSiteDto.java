package com.devworker.kms.dto.solution;

import com.devworker.kms.dto.common.BoardDetailDto;
import com.devworker.kms.entity.solution.SolutionSiteDao;

import javax.persistence.Column;
import javax.persistence.Id;

public class SolutionSiteDto {
    private long boardId;
    private long menuId;
    private int siteId;
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

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
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
	
    public SolutionSiteDao toDao(){
        SolutionSiteDao dao = new SolutionSiteDao();
        dao.setBoardId(boardId);
        dao.setMenuId(menuId);
        dao.setSiteId(siteId);
        dao.setVersion(version);
        return dao;
    }
    
}
