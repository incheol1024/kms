package com.devworker.kms.entity.qna;

import java.io.Serializable;

public class QnaCodeDaoId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int menuId;
	private long boardId;

	public QnaCodeDaoId() {
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public long getBoardId() {
		return boardId;
	}

	public void setBoardId(long boardId) {
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
		return this.menuId + Long.numberOfLeadingZeros(this.boardId) + Long.numberOfTrailingZeros(this.boardId);
	}
}
