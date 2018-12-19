package com.devworker.kms.dao.board;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KMS_COMMENT")
public class CommentDao {

	@ManyToOne
	@JoinColumn(name = "board_id")
	// @Column(name = "board_id")
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
	LocalDateTime cmtDate;

	@Column(name = "cmt_like")
	int cmtLike;

	@Column(name = "cmt_unlike")
	int cmtUnlike;

	public CommentDao() {
		this.cmtDate = LocalDateTime.now();
	}

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

	public LocalDateTime getCmtDate() {
		return cmtDate;
	}

	public void setCmtDate(LocalDateTime cmtDate) {
		this.cmtDate = cmtDate;
	}

	public int getCmtLike() {
		return cmtLike;
	}

	public void setCmtLike(int cmtLike) {
		this.cmtLike += cmtLike;
	}

	public int getCmtUnlike() {
		return cmtUnlike;
	}

	public void setCmtUnlike(int cmtUnlike) {
		this.cmtUnlike += cmtUnlike;
	}

	@Override
	public String toString() {
		return "CommentDao [boardId=" + boardId + ", cmtId=" + cmtId + ", cmtContents="
				+ cmtContents + ", cmtUserId=" + cmtUserId + ", cmtDate=" + cmtDate
				+ ", cmtLike=" + cmtLike + ", cmtUnlike=" + cmtUnlike + ", getBoardId()="
				+ getBoardId() + ", getCmtId()=" + getCmtId() + ", getCmtContents()="
				+ getCmtContents() + ", getCmtUserId()=" + getCmtUserId()
				+ ", getCmtDate()=" + getCmtDate() + ", getCmtLike()=" + getCmtLike()
				+ ", getCmtUnlike()=" + getCmtUnlike() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
