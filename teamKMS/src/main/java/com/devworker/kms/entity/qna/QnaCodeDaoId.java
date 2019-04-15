package com.devworker.kms.entity.qna;

import java.io.Serializable;

public class QnaCodeDaoId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int menuId;
	private int boardId;

	public QnaCodeDaoId() {
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj instanceof QnaCodeDaoId) {
			QnaCodeDaoId newQnaCodeDaoId = (QnaCodeDaoId) obj;
			return this.getMenuId() == newQnaCodeDaoId.getMenuId() && this.getBoardId() == newQnaCodeDaoId.getBoardId();
		}

		return false;
	}

	@Override
	public int hashCode() {
		return this.menuId + Integer.numberOfLeadingZeros(this.boardId) + Integer.numberOfTrailingZeros(this.boardId);
	}
}
