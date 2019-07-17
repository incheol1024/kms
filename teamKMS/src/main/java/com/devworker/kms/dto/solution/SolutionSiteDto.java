package com.devworker.kms.dto.solution;

import com.devworker.kms.entity.solution.SolutionSiteDao;

import javax.persistence.Column;
import javax.persistence.Id;

public class SolutionSiteDto {
    @Id
    @Column(name = "board_id")
    private
    long boardId;

    @Column(name = "menu_id")
    private
    long menuId;

    @Column(name = "site_id")
    private int siteId;

    @Column(name = "version")
    private String version;

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

    public SolutionSiteDao toDao(){
        SolutionSiteDao dao = new SolutionSiteDao();
        dao.setBoardId(boardId);
        dao.setMenuId(menuId);
        dao.setSiteId(siteId);
        dao.setVersion(version);
        return dao;
    }
}
