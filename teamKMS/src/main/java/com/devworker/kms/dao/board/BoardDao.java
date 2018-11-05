package com.devworker.kms.dao.board;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "TBL_BOARD")
public class BoardDao {

	@Id
	@Column( name = "board_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int boardId;

	@Column(name = "subject")
	String subject;

	@Column(name = "contents")
	String contents;

	@Column(name = "user_id")
	String userId;

	@Column(name = "reg_date")
	LocalDate regDate;

	@Column(name = "upd_date")
	LocalDate updDate;

	@Column(name = "hits")
	int hits;

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
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

	public LocalDate getRegDate() {
		return regDate;
	}

	public void setRegDate(LocalDate regDate) {
		this.regDate = regDate;
	}

	public LocalDate getUpdDate() {
		return updDate;
	}

	public void setUpdDate(LocalDate updDate) {
		this.updDate = updDate;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

}
