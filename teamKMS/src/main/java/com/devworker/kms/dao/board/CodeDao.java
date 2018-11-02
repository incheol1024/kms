package com.devworker.kms.dao.board;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "TBL_BOARD")
public class CodeDao {

	@ManyToOne
	@JoinColumn(name = "board_id", referencedColumnName = "board_id")
	@Column(name = "board_id")
	BoardDao boardID;

	@Column(name = "code1")
	int code1;

	@Column(name = "code2")
	int code2;

	@Column(name = "code3")
	int code3;

	@Column(name = "code4")
	int code4;

	public BoardDao getBoardID() {
		return boardID;
	}

	public void setBoardID(BoardDao boardID) {
		this.boardID = boardID;
	}

	public int getCode1() {
		return code1;
	}

	public void setCode1(int code1) {
		this.code1 = code1;
	}

	public int getCode2() {
		return code2;
	}

	public void setCode2(int code2) {
		this.code2 = code2;
	}

	public int getCode3() {
		return code3;
	}

	public void setCode3(int code3) {
		this.code3 = code3;
	}

	public int getCode4() {
		return code4;
	}

	public void setCode4(int code4) {
		this.code4 = code4;
	}

}
