package com.devworker.kms.dto.solution;

import com.devworker.kms.entity.solution.SolutionPatchDao;

import javax.persistence.Column;
import javax.persistence.Id;

public class SolutionPatchDto {
    @Id
    @Column(name = "board_id")
    private
    long boardId;

    @Column(name = "menu_id")
    private
    long menuId;

    @Column(name = "impotance")
    private String impotance;

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

    public String getImpotance() {
        return impotance;
    }

    public void setImpotance(String impotance) {
        this.impotance = impotance;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public SolutionPatchDao toDao(){
        SolutionPatchDao dao = new SolutionPatchDao();
        dao.setBoardId(boardId);
        dao.setImpotance(impotance);
        dao.setMenuId(menuId);
        dao.setVersion(version);
        return dao;
    }
}
