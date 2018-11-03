package com.devworker.kms.dao.board;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "TBL_COMMENT")
public class CommentDao {

	@JoinColumn(name = "board_id", referencedColumnName = "board_id")
	@Column(name = "board_id")
	BoardDao boardId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cmt_id")
	int cmtId;

	@Column(name = "cmt_contents")
	String cmtContents;

	@Column(name = "cmt_user_id")
	String cmtUserId;

	@Column(name = "cmt_date")
	LocalDate cmtDate;

	public BoardDao getBoardId() {
		return boardId;
	}

	public void setBoardId(BoardDao boardId) {
		this.boardId = boardId;
	}

	public int getCmtId() {
		return cmtId;
	}

	public void setCmtId(int cmtId) {
		this.cmtId = cmtId;
	}

	public String getCmtContents() {
		return cmtContents;
	}

	public void setCmtContents(String cmtContents) {
		this.cmtContents = cmtContents;
	}

	public String getCmtUserId() {
		return cmtUserId;
	}

	public void setCmtUserId(String cmtUserId) {
		this.cmtUserId = cmtUserId;
	}

	public LocalDate getCmtDate() {
		return cmtDate;
	}

	public void setCmtDate(LocalDate cmtDate) {
		this.cmtDate = cmtDate;
	}

}
