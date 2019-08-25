package com.devworker.kms.entity.solution;


import com.devworker.kms.dto.solution.SolutionBugDto;
import com.devworker.kms.entity.common.BoardDao;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KMS_Solution_BUG")
public class SolutionBugDao {
    @Id
    @Column(name = "board_id")
    private
    long boardId;
    
    @OneToOne
	@JoinColumn(name = "board_id", referencedColumnName = "board_id")
	private BoardDao boardDao;

    @Column(name = "menu_id")
    private
    long menuId;

    @Column(name = "manager")
    private String manager;

    @Column(name = "completed")
    private String completed;

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

    @JsonIgnore
    public SolutionBugDto toDto(){
        SolutionBugDto dto = new SolutionBugDto();
        dto.setDetailBoard(boardDao.getBoardDetailDto());
        dto.setBoardId(boardId);
        dto.setCompleted(completed);
        dto.setManager(manager);
        dto.setMenuId(menuId);
        return dto;
    }
}
