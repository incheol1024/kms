package com.devworker.kms.dao.board;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.devworker.kms.dao.MenuDao;

@Entity
@Table(name = "KMS_QNA_CODE")
public class QnaCodeDao {

	@JoinColumn(name = "menu_id")
	private MenuDao menuId;

	@JoinColumn(name = "board_id")
	private BoardDao boardId;

	public MenuDao getMenuId() {
		return menuId;
	}

	public void setMenuId(MenuDao menuId) {
		this.menuId = menuId;
	}

	public BoardDao getBoardId() {
		return boardId;
	}

	public void setBoardId(BoardDao boardId) {
		this.boardId = boardId;
	}

}
