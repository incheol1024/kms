package com.devworker.kms.entity.solution;

import com.devworker.kms.dto.solution.SolutionPatchDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "KMS_Solution_Patch")
public class SolutionPatchDao {
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

    public SolutionPatchDto toDto(){
        SolutionPatchDto dto = new SolutionPatchDto();
        dto.setBoardId(boardId);
        dto.setImpotance(impotance);
        dto.setMenuId(menuId);
        dto.setVersion(version);
        return dto;
    }
}
