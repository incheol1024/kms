package com.devworker.kms.entity.common;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "KMS_BOARD")
public class BoardDao {

	@Id
	@Column( name = "board_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long boardId;

	@Column(name = "subject")
	String subject;

	@Column(name = "contents")
	String contents;

	@Column(name = "user_id")
	String userId;

	@Column(name = "reg_date")
	LocalDateTime regDate;

	@Column(name = "upd_date")
	LocalDateTime updDate;

	@Column(name = "hits")
	int hits;

	public BoardDao() {
	}
	public BoardDao(long boardId) {
		this.boardId = boardId;
	}

	public long getBoardId() {
		return boardId;
	}

	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public LocalDateTime getRegDate() {
		return regDate;
	}

	public void setRegDate(LocalDateTime regDate) {
		this.regDate = regDate;
	}

	public LocalDateTime getUpdDate() {
		return updDate;
	}

	public void setUpdDate(LocalDateTime updDate) {
		this.updDate = updDate;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits += hits;
	}

	@Override
	public String toString() {
		return "BoardDao [boardId=" + boardId + ", subject=" + subject + ", contents=" + contents + ", userId=" + userId
				+ ", regDate=" + regDate + ", updDate=" + updDate + ", hits=" + hits + "]";
	}

	
	
}
