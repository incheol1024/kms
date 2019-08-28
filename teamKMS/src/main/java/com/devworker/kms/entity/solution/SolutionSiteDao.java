package com.devworker.kms.entity.solution;

import com.devworker.kms.dto.solution.SolutionSiteDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "KMS_Solution_SITE")
public class SolutionSiteDao {
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

    public SolutionSiteDto toDto(){
        SolutionSiteDto dto = new SolutionSiteDto();
        dto.setBoardId(boardId);
        dto.setMenuId(menuId);
        dto.setSiteId(siteId);
        dto.setVersion(version);
        return dto;
    }
}
