package com.devworker.kms.entity.solution;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "KMS_Solution")
public class SolutionDao {
	@Id
	@Column(name = "board_id")
	long boardId;
	
	@Column(name = "menu_id")
	long menuId;

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
}
