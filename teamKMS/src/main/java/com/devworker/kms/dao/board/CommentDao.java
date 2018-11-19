package com.devworker.kms.dao.board;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.web.multipart.MultipartFile;

import com.devworker.kms.dto.board.CommentDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "KMS_COMMENT")
public class CommentDao {

//	@ManyToOne
//	@JoinColumn(name="boardId",foreignKey = @ForeignKey(name = "FK_TBL_DOC_TBL_BOARD"))

	@Column(name = "board_id")
	int boardId;

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
	
	public CommentDao() {
		this.cmtDate = LocalDateTime.now();
	}
	
	public CommentDao(CommentDto commentDto) {
		this.boardId = commentDto.getBoardId();
		this.cmtContents = commentDto.getCmtContents();
		this.cmtUserId = commentDto.getCmtUserId();
		this.cmtDate = LocalDateTime.now();
	}


	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
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

	public void setCmtDate(LocalDate cmtDate) {
		this.cmtDate = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "CommentDao [boardId=" + boardId + ", cmtId=" + cmtId + ", cmtContents=" + cmtContents + ", cmtUserId="
				+ cmtUserId + ", cmtDate=" + cmtDate + "]";
	}


}
